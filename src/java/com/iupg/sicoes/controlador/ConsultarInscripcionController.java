/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoAlumno;
import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.propiedades.ParamConfig;
import com.iupg.sicoes.servicio.EmailService;
import com.iupg.sicoes.servicio.ParamCorreo;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
*
*@author gerson
*/
public class ConsultarInscripcionController extends AbstractController {
private DaoConexion daoConexion;

public ConsultarInscripcionController() {
}

@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception, ConfigurationException {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "academico/registroControl/consultarInscripcion";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoAlumno daoAlumno = new DaoAlumno((String) misession.getAttribute("cedulaUsuSession"));
        String[] materias = request.getParameterValues("materia");
        String materiasSeleccionadas = "";
        String seccionesSeleccionadas = "";
        String horariosMaterias = "";
        boolean validado = false;
        
        //Para obtener parámetros de las propiedades del correo
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamCorreo.class);
        ParamCorreo pv = ctx.getBean(ParamCorreo.class);
        
        //Para obtener los parámetros de configuración del servicio de correo
        String confFile = "classpath:com/iupg/sicoes/propiedades/mailProperties.xml";                                
        ConfigurableApplicationContext context
                  = new ClassPathXmlApplicationContext(confFile);

        EmailService emailService = (EmailService) context.getBean("emailService");
        
        String rutaPDFAdjunto = pv.getCorreo_rutaArcAdj()+"/planillas";

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos complementarios del Alumno
            daoConexion = new DaoConexion();                                    
            if (daoAlumno.BuscarDatosAlumno(daoConexion.ConexionBD())) {
                misession.setAttribute("gerenciaSession", daoAlumno.getEspecialidad());
                misession.setAttribute("turnoSession", daoAlumno.getTurno());
                
                //Buscar el período inscrito Administrativamente            
                if ("POST".equals(request.getMethod())) {
                    //Si preciona el botón consultar
                    String accion = request.getParameter("action");    
                    if (accion != null) {
                        if (request.getParameter("action").equals("Consultar")) {
                            //aqui voy
                            if ((!"lbElige".equals(request.getParameter("periodo")))&&(!"lbSinInscripcion".equals(request.getParameter("periodo")))) {
                                ServletOutputStream out = response.getOutputStream();
                                String plantilla=ParamConfig.getString("reporte.archivoPlanillaInscripcion");  
                                File theFile = new File(plantilla);
                                JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                        
                                Map<String, Object> parametros = new HashMap();
                                parametros.put("CEDULA", new Integer(daoAlumno.getCedula()));
                                parametros.put("PERIODO", new String(request.getParameter("periodo").substring(2, request.getParameter("periodo").length())));
                                parametros.put("LOGO", new String(ParamConfig.getString("reporte.archivoImagenLogo")));
                            
                                JasperPrint print = JasperFillManager.fillReport(reporte, parametros, daoConexion.ConexionBD());
                        
                                JRPdfExporter exporter = new JRPdfExporter();
                                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                                exporter.exportReport();
                            }
                        } else { // Si presiona Botón Salir
                            mensaje = "";
                            pagina="principal/principal";
                        }
                    
                    } else {
                        daoAlumno.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                        String opcionesPeriodo = daoAlumno.BuscarPeriodosInscritos(daoConexion.ConexionBD());
                        misession.setAttribute("incripcionAlumnoSession", opcionesPeriodo);    
                    }
                    
                } else {                    
                    // else del POST                        
                    //Buscar el período inscrito Administrativamente y academicamente
                    //daoAlumno.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                    daoAlumno.setPeriodo("");
                    String opcionesPeriodo = daoAlumno.BuscarPeriodosInscritos(daoConexion.ConexionBD());
                    misession.setAttribute("incripcionAlumnoSession", opcionesPeriodo);
                }
            } else {
                //pagina ="principal/incripcion";                           
                mensaje = "Error Buscando datos Asociados al Usuario.";
            } 
        }
        
        ((AnnotationConfigApplicationContext)ctx).close();
        
        misession.setAttribute("validadoSession", validado);
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;        
    }
}