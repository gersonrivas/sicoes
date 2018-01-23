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
public class BuscarAlumnoCedulaPeriodoAdminController extends AbstractController {
    private DaoConexion daoConexion;
    
    public BuscarAlumnoCedulaPeriodoAdminController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "general/buscarAlumnoCedulaPeriodoAdmin";
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
                
                daoAlumno.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                String opcionesPeriodos = daoAlumno.BuscarPeriodosInscritos(daoConexion.ConexionBD());
                misession.setAttribute("periodosAlumnoSession", opcionesPeriodos);
                misession.setAttribute("periodoAlumnoSeleccionadoSession", request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                
                if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo"))) && !"lbSinInscripcion".equals(request.getParameter("periodo"))) {
                    boton = "<input name=\"action\" type=\"submit\" id=\"consultar\" value=\"Consultar\" />";
                    boton = boton + "<input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" />";
                }                
                
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Buscar": {                    
                            //daoAlumno.setCedula(request.getParameter("cedula"));
                            if (daoAlumno.BuscarDatosAlumno(daoConexion.ConexionBD())) {
                                String nombreAlumno = daoAlumno.getApellidos() + " " + daoAlumno.getNombres();
                                misession.setAttribute("nombreAlumnoSession", nombreAlumno);   
                                misession.setAttribute("cedulaAlumnoSession", request.getParameter("cedula"));
                            }                            
                            break;
                        }                            
                            
                        case "Consultar": {
                            //Emitir Record de Notas         
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
                                                        
                            //Reiniciar las variables de sesion
                            //misession.setAttribute("nombreAlumnoSession", "");   
                            //misession.setAttribute("cedulaAlumnoSession", "");
                            break;
                        }                            

                        case "Eliminar": {
                            mensaje = "Está seguro de Eliminar esta inscripción?";
                            boton = "<input name=\"action\" type=\"submit\" id=\"si\" value=\"Si\" />";
                            boton = boton + "<input name=\"action\" type=\"submit\" id=\"no\" value=\"No\" />";            

                            //pagina="principal/principal";
                            break;
                        }
                        
                        //Confirmar la eliminación de la Inscripción
                        case "Si": {
                            mensaje = daoAlumno.EliminarInscripcionAlumno(daoConexion.ConexionBD());
                            //pagina="principal/principal";
                            break;
                        }
                        
                        case "Salir": {
                            mensaje = "";
                            pagina="principal/principal";
                            break;
                        }
                    }

                }
                
            } else {
                
                String opcionesPeriodos = daoAlumno.BuscarPeriodosInscritos(daoConexion.ConexionBD());
                misession.setAttribute("periodosAlumnoSession", opcionesPeriodos);                                                
            }
            misession.setAttribute("botonSession",boton);
        }
        
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }
    
}
