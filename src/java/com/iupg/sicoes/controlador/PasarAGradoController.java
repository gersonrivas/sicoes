/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoAlumno;
import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGraduados;
import com.iupg.sicoes.modelo.DaoProfesor;
import com.iupg.sicoes.modelo.Validador;
import com.iupg.sicoes.servicio.EmailService;
import com.iupg.sicoes.servicio.ParamCorreo;
import com.iupg.sicoes.propiedades.ParamConfig;

import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
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
public class PasarAGradoController extends AbstractController {
private DaoConexion daoConexion;

public PasarAGradoController() {
}

@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception, ConfigurationException {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "registroControl/pasarAGrado";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoGraduados daoGraduados = new DaoGraduados((String) misession.getAttribute("cedulaUsuSession"));
        //Validador validaHorario = new Validador();
        //String[] materias = request.getParameterValues("materia");
        //String materiasSeleccionadas = "";
        //String seccionesSeleccionadas = "";
        //String horariosMaterias = "";
        //boolean validado = false;
        
        //Para obtener parámetros de las propiedades del correo
        //ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamCorreo.class);
        //ParamCorreo pv = ctx.getBean(ParamCorreo.class);
        
        //Para obtener los parámetros de configuración del servicio de correo
        //String confFile = "classpath:com/iupg/sicoes/propiedades/mailProperties.xml";                                
        //ConfigurableApplicationContext context
        //          = new ClassPathXmlApplicationContext(confFile);

        //EmailService emailService = (EmailService) context.getBean("emailService");
        
        //String rutaPDFAdjunto = pv.getCorreo_rutaArcAdj()+System.getProperty("file.separator")+"planillas";

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
                      
            //Buscar los datos complementarios del Alumno
            daoConexion = new DaoConexion();                                    
            //if (daoAlumno.BuscarDatosAlumno(daoConexion.ConexionBD())) {
            //if (daoGraduados.BuscarDatosGraduado(daoConexion.ConexionBD())) {
               
                //Buscar el período inscrito Administrativamente            
                if ("POST".equals(request.getMethod())) {
                    
                } else {                    
                    // else del POST                        
                    
                    pagina = "registroControl/pasarAGrado";
                    //String opcionesPeriodos = daoGraduados.BuscarPeriodos(daoConexion.ConexionBD());
                    //misession.setAttribute("periodosGraduadosSession", opcionesPeriodos);
                                            
                }              
                
            //} else {
            //    pagina = "principal/principal";                           
            //    mensaje = "Error Buscando datos Asociados al Usuario.";
            //} 
        }      
        
        
        //((AnnotationConfigApplicationContext)ctx).close();
        
        //misession.setAttribute("validadoSession", validado);
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;        
    }
}