/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoAlumno;
import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.propiedades.ParamConfig;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class BuscarAlumnoCedulaPeriodoController extends AbstractController {
    private DaoConexion daoConexion;
    
    public BuscarAlumnoCedulaPeriodoController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "academico/registroControl/recordNotas";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoAlumno daoAlumno = new DaoAlumno((String) request.getParameter("0"));
        String boton = "<input name=\"action\" type=\"submit\" id=\"buscar\" value=\"Buscar\" />";

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {        
            
            if (request.getParameter("cedula")!=null && !"".equals(request.getParameter("cedula"))) {
                daoAlumno= new DaoAlumno((String) request.getParameter("cedula"));
            }
 
     
            if ("POST".equals(request.getMethod())) {                                
                //Si preciona el botón consultar, buscar o salir                    
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Buscar": {
                    
                            //daoAlumno.setCedula(request.getParameter("cedula"));
                            if (daoAlumno.BuscarDatosAlumno(daoConexion.ConexionBD())) {
                                String nombreAlumno = daoAlumno.getApellidos() + " " + daoAlumno.getNombres();
                                misession.setAttribute("nombreAlumnoSession", nombreAlumno);   
                                misession.setAttribute("cedulaAlumnoSession", request.getParameter("cedula"));
                    
                                //System.out.println("especialidad-->"+request.getParameter("especialidad"));
                                    
                                if (daoAlumno.getEspecialidad()!=null && !"lbElige".equals(daoAlumno.getEspecialidad()) && (!"lbSinEspecialidad".equals(daoAlumno.getEspecialidad()))) {
                                    daoAlumno.setEspecialidad(request.getParameter("especialidad").substring(2, request.getParameter("especialidad").length()));
                                    boton = "<input name=\"action\" type=\"submit\" id=\"consultar\" value=\"Consultar\" />";            
                                } 
                            }
                            
                            break;
                        }                            
                            
                        case "Consultar": {
                            //Emitir Record de Notas                            
                            
                            ServletOutputStream out = response.getOutputStream();
                            String plantilla=ParamConfig.getString("reporte.archivoReporteRecordNotas");  
                            File theFile = new File(plantilla);
                            JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                        
                            Map<String, Object> parametros = new HashMap();
                            parametros.put("CEDULA", new Integer(daoAlumno.getCedula()));
                            //parametros.put("LOGO", new String(ParamConfig.getString("reporte.archivoImagenLogo")));
                            
                            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, daoConexion.ConexionBD());
                        
                            JRPdfExporter exporter = new JRPdfExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                            exporter.exportReport();                            
                            
                            //Reiniciar las variables de sesion
                            misession.setAttribute("nombreAlumnoSession", "");   
                            misession.setAttribute("cedulaAlumnoSession", "");
                            break;
                        }                            

                        case "Salir": {
                            mensaje = "";
                            pagina="principal/principal";
                            break;
                        }
                    }

                }                
            }
        }
        
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }
    
}
