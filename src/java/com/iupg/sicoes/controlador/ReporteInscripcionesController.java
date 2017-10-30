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
import com.lowagie.text.pdf.PdfWriter;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
import java.io.File;
import java.util.HashMap;
import java.util.List;
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
public class ReporteInscripcionesController extends AbstractController {
private DaoConexion daoConexion;

public ReporteInscripcionesController() {
}

@Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception, ConfigurationException {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "principal/reporteInscripciones";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoAlumno daoAlumno = new DaoAlumno((String) misession.getAttribute("cedulaUsuSession"));

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
                            
            if ("POST".equals(request.getMethod())) {
                //Si preciona el botón consultar
                    
                if (request.getParameter("action").equals("Consultar")) {
                    //aqui voy
                    if ((!"lbElige".equals(request.getParameter("sede")))&&(!"lbSinSede".equals(request.getParameter("sede")))&&     (!"lbElige".equals(request.getParameter("periodo")))&&(!"lbSinInscripcion".equals(request.getParameter("periodo")))) {
                        ServletOutputStream out = response.getOutputStream();
                        //String plantilla="/home/gerson/NetBeansProjects/sicoes/src/java/com/iupg/sicoes/vista/reporteInsc.jasper";
                        String listado=ParamConfig.getString("reporte.archivoReporteInscripciones");
                        File theFile = new File(listado);
                        JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                        
                        Map<String, Object> parametros = new HashMap();
                        parametros.put("SEDE", new String(request.getParameter("sede").substring(2, request.getParameter("sede").length())));
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
                // else del POST                        
                //Buscar el período inscrito Administrativamente y academicamente
                String opcionesSede = daoAlumno.BuscarSede(daoConexion.ConexionBD());
                String opcionesPeriodo = daoAlumno.BuscarPeriodos(daoConexion.ConexionBD());
                misession.setAttribute("sedeReporteInscritosSession", opcionesSede);                
                misession.setAttribute("periodoReporteInscritosSession", opcionesPeriodo);                
            }

        }
       
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;        
    }
}