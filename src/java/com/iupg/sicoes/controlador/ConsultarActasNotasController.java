/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoProfesor;
import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGeneral;
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
public class ConsultarActasNotasController extends AbstractController {
private DaoConexion daoConexion;

public ConsultarActasNotasController() {
}

@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception, ConfigurationException {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "academico/profesor/consultarActasNotas";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoProfesor daoProfesor = new DaoProfesor((String) misession.getAttribute("cedulaUsuSession"));
        //DaoGeneral daoGeneral = new DaoGeneral();
        
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
            //Buscar los datos complementarios del Profesor
            daoConexion = new DaoConexion();                                    
            if (daoProfesor.BuscarDatosProfesor(daoConexion.ConexionBD())) {
                //misession.setAttribute("gerenciaSession", daoAlumno.getEspecialidad());
                //misession.setAttribute("turnoSession", daoAlumno.getTurno());
                
                //Buscar el período inscrito Administrativamente            
                if ("POST".equals(request.getMethod())) {                    
                    // Para el llenado de los demás combos
                    if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {
                            daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                            String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                            misession.setAttribute("periodosProfesorSession", opcionesPeriodos);

                            daoProfesor.setSede(request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                            String opcionesSedes = daoProfesor.BuscarSedes(daoConexion.ConexionBD());
                            misession.setAttribute("sedesSession", opcionesSedes);
                            misession.setAttribute("sedeSeleccionadaSession", request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                                
                            daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                            String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                            misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                                                
                            daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));
                            String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                            misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);
                        
                    }                        
                        
                    //Si preciona el botón consultar                    
                    String accion = request.getParameter("action");
                    
                    if (accion != null) {
                        if (accion.equals("Consultar")) {
                            //aqui voy
                            if ((!"lbElige".equals(request.getParameter("materia")))&&(!"lbSinMateria".equals(request.getParameter("materia"))) && (!"lbElige".equals(request.getParameter("seccion")))&&(!"lbSinSeccion".equals(request.getParameter("seccion")))) {
                                ServletOutputStream out = response.getOutputStream();
                                String plantilla=ParamConfig.getString("reporte.archivoReporteActasNotas");  
                                File theFile = new File(plantilla);
                                JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                        
                                Map<String, Object> parametros = new HashMap();
                                parametros.put("CEDULA", new Integer(daoProfesor.getCedula()));
                                parametros.put("PERIODO", new String(daoProfesor.getPeriodo()));
                                parametros.put("SEDE", new String(daoProfesor.getSede()));
                                parametros.put("MATERIA", new String(daoProfesor.getAsignatura()));
                                parametros.put("SECCION", new String(daoProfesor.getSeccion()));
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
                    }
                } else {                    
                    // else del POST                        
                    
                    //pagina ="principal/incripcion";
                    String opcionesSedes = daoProfesor.BuscarSedes(daoConexion.ConexionBD());
                    misession.setAttribute("sedesSession", opcionesSedes);

                    String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                    misession.setAttribute("periodosProfesorSession", opcionesPeriodos);

                    String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                    misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                        
                    String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                    misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);
                    
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