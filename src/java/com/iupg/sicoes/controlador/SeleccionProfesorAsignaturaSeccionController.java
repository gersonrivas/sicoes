/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;
import com.iupg.sicoes.modelo.DaoProfesor;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.propiedades.ParamConfig;
import com.iupg.sicoes.servicio.ParamCorreo;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class SeleccionProfesorAsignaturaSeccionController extends AbstractController {
    private DaoConexion daoConexion;
    
    public SeleccionProfesorAsignaturaSeccionController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "academico/registroControl/seleccionProfesorAsignaturaSeccion";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamCorreo.class);
        ParamCorreo pv = ctx.getBean(ParamCorreo.class);
        String rutaPDFAdjunto = pv.getCorreo_rutaArcAdj()+System.getProperty("file.separator")+"actas_notas";
        
        boolean validado = false;
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos complementarios del Profesor
            //if (misession.getAttribute("cedula")!=null) {
            if (request.getParameter("cedula")!=null) {                
                misession.setAttribute("cedulaProfesorSession", request.getParameter("cedula"));                
                //
                DaoProfesor daoProfesor = new DaoProfesor((String) request.getParameter("cedula"));        
                
                if (daoProfesor.BuscarDatosProfesor(daoConexion.ConexionBD())) {
                    misession.setAttribute("nombreProfesorSession",daoProfesor.getApellidos()+" "+daoProfesor.getNombres());

                    if ("POST".equals(request.getMethod())) {
                    
                    
                    //daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                    
                    if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {
                       daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                    
                    
                        //if (!"lbElige".equals(request.getParameter("sede")) && (!"lbSinSede".equals(request.getParameter("sede")))) {
                            daoProfesor.setSede(request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                        

                            //if (!"lbElige".equals(request.getParameter("materia")) && (!"lbSinAsignatura".equals(request.getParameter("materia")))) {
                                daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                                                
                                //if (!"lbElige".equals(request.getParameter("seccion")) && (!"lbSinSeccion".equals(request.getParameter("seccion")))) {
                                    daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));    
                        
                                    if (!"lbElige".equals(request.getParameter("sede")) && (!"lbSinSede".equals(request.getParameter("sede")))) {
                                        if (!"lbElige".equals(request.getParameter("materia")) && (!"lbSinAsignatura".equals(request.getParameter("materia")))) {
                                            if (!"lbElige".equals(request.getParameter("seccion")) && (!"lbSinSeccion".equals(request.getParameter("seccion")))) {                                               
                                                misession.setAttribute("botonSession","<input name=\"action\" type=\"submit\" id=\"cargar\" value=\"Cargar\" />");                                                
                                            }
                                        }
                                    }
                                    
                                    
                        
                                //}
                            //}
                        //}

                    }
                    
                    
                    String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                    misession.setAttribute("periodosProfesorSession", opcionesPeriodos);
                    misession.setAttribute("periodoSeleccionadoProfesorSession", request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));                                 
                    
                    
                    
                    String opcionesSedes = daoProfesor.BuscarSedes(daoConexion.ConexionBD());
                    misession.setAttribute("sedesSession", opcionesSedes);
                    misession.setAttribute("sedeSeleccionadaSession", request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                    //daoProfesor.setSede(request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                    
                    //daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                    String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                    misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                    misession.setAttribute("materiaSeleccionadaProfesorSession", request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                        
                    //daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));
                    String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                    misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);
                    misession.setAttribute("seccionSeleccionadaProfesorSession", request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));

                    
                    if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {
                       daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                    
                    
                        //if (!"lbElige".equals(request.getParameter("sede")) && (!"lbSinSede".equals(request.getParameter("sede")))) {
                            daoProfesor.setSede(request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                        

                            //if (!"lbElige".equals(request.getParameter("materia")) && (!"lbSinAsignatura".equals(request.getParameter("materia")))) {
                                daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                                                
                                //if (!"lbElige".equals(request.getParameter("seccion")) && (!"lbSinSeccion".equals(request.getParameter("seccion")))) {
                                    daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));    
                                    
                                    String tipoUsu = (String)misession.getAttribute("tipoUsuSession");
                                    if (tipoUsu.equals("S")) {
                                        misession.setAttribute("botonSession","<input name=\"action\" type=\"submit\" id=\"cargar\" value=\"Cargar\" />");
                                    } else {
                                        misession.setAttribute("botonSession","<input name=\"action\" type=\"submit\" id=\"imprimir\" value=\"Imprimir\" />");
                                    }                                    
                        
                                //}
                            //}
                        //}

                    }
                    
                    
                    //Si preciona el botón buscar, cargar o modificar
                        String accion = request.getParameter("action");
                    
                        if (accion != null) {
                            //Validar los botones pulsados
                            switch (accion) {
                                case "Buscar":
                                    //mensaje = daoGeneral.ValidarExistenciaHorarioProfesores(daoConexion.ConexionBD());
                                    //misession.setAttribute("botonSession","");
                                    if (mensaje.isEmpty()) {
                                        //mensaje = daoGeneral.EliminarSeccionesFiltradas(daoConexion.ConexionBD());
                                    }                        
                                    break;
                                
                                case "Cargar": 
                                    
                                    if ((!"lbElige".equals(request.getParameter("materia")))&&(!"lbSinMateria".equals(request.getParameter("materia"))) && (!"lbElige".equals(request.getParameter("seccion")))&&(!"lbSinSeccion".equals(request.getParameter("seccion")))) {
                                        //Página de carga 
                                
                                        String encabezado = daoProfesor.BuscarCabeceraEvaluacion(daoConexion.ConexionBD());
                                        misession.setAttribute("encabezadoEvaluacionSession", encabezado);
                                
                                        String cuerpo = daoProfesor.BuscarDetalleEvaluacion(daoConexion.ConexionBD(),"");
                                        misession.setAttribute("detalleEvaluacionSession", cuerpo);
                                
                                        mensaje = "";
                                        pagina = "academico/registroControl/cargaNotasProfesorAdmin"; 
                                    }
                                    
                                    
                                    //mensaje = daoGeneral.AdicionarSeccionFiltrada(daoConexion.ConexionBD());
                                    break;
                                case "Modificar": 
                                    //mensaje = daoGeneral.AdicionarSeccionFiltrada(daoConexion.ConexionBD());                            
                                    break;
                                case "Imprimir": 
                                    //mensaje = daoGeneral.AdicionarSeccionFiltrada(daoConexion.ConexionBD());                            
                                    // Exportar Acta de Notas
                                    ServletOutputStream out = response.getOutputStream();
                                    String actasNotas=ParamConfig.getString("reporte.archivoReporteActasNotasCargada");                                    
                                    String planillaInscAdj = rutaPDFAdjunto + System.getProperty("file.separator")+"Acta_Notas_" + daoProfesor.getCedula()+"_"+daoProfesor.getPeriodo()+"_"+daoProfesor.getAsignatura()+"_"+daoProfesor.getSeccion()+".pdf";
                                    File theFile = new File(actasNotas);
                                    JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                                    
                                    Map<String, Object> parametros = new HashMap();
                                    parametros.put("CEDULA", new Integer(daoProfesor.getCedula()));
                                    parametros.put("PERIODO", new String(daoProfesor.getPeriodo()));
                                    parametros.put("SEDE", new String(daoProfesor.getSede()));
                                    parametros.put("SECCION", new String(daoProfesor.getSeccion()));
                                    parametros.put("LOGO", new String(ParamConfig.getString("reporte.archivoImagenLogo")));
                                    parametros.put("MATERIA", new String(daoProfesor.getAsignatura()));
                                    
                                    JasperPrint print = JasperFillManager.fillReport(reporte, parametros, daoConexion.ConexionBD());

                                    JRPdfExporter exporter = new JRPdfExporter(); 
                                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
                                    exporter.exportReport();
                                    
                                    

                                    break;
                                case "Salir": {
                                    mensaje = "";
                                    pagina="principal/principal";
                                    break;
                                }
                            }
                        }



                        //Para el llenado de los demás combos
                        //Buscar el período inscrito Administrativamente
                        //if ("lbElige".equals(request.getParameter("periodo")) && ("lbSinPeriodo".equals(request.getParameter("periodo")))) {
                            //daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                            //String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                            //misession.setAttribute("periodosProfesorSession", opcionesPeriodos);
                            //misession.setAttribute("periodoSeleccionadoProfesorSession", request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                        
                            //if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {
                                //daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                                //String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                                //misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                                //misession.setAttribute("materiaSeleccionadaProfesorSession", request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                            
                                //if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {                                                
                                    //daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));
                                    //String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                                    //misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);
                                    //misession.setAttribute("seccionSeleccionadaProfesorSession", request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));                        
                                //}
                            //}
                        //}
                        
                        
                        
                        



                    } else {                    
                        // else del POST                        
                    


                        //Por borrar

                        //pagina ="principal/incripcion";
                        daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                        String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosProfesorSession", opcionesPeriodos);

                        daoProfesor.setSede(request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                        String opcionesSedes = daoProfesor.BuscarSedes(daoConexion.ConexionBD());
                        misession.setAttribute("sedesSession", opcionesSedes);

                        daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                        String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                        misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                        
                        daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));
                        String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                        misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);                        
                                            
                        //Inicializando variable del boton.
                        //misession.setAttribute("botonSession","");
                    
                        //if (tablaDetalle.isEmpty()) {
                        //    misession.setAttribute("botonSession","");
                        //} else {
                        misession.setAttribute("botonSession","<input name=\"action\" type=\"submit\" id=\"buscar\" value=\"Buscar\" />");
                        //}
                
                    
                    }
                    
                    
                    
                    






                } else {
                    misession.setAttribute("nombreProfesorSession"," ");
                    mensaje="Cédula no Existe.";
                }






            } else {             
            
                DaoProfesor daoProfesor = new DaoProfesor("0");
                
                String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                misession.setAttribute("periodosProfesorSession", opcionesPeriodos);

                String opcionesSedes = daoProfesor.BuscarSedes(daoConexion.ConexionBD());
                misession.setAttribute("sedesSession", opcionesSedes);

                String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                        
                String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);
                    
                //Inicializando variable del boton.
                misession.setAttribute("botonSession","");
                    
                    //if (tablaDetalle.isEmpty()) {
                    //    misession.setAttribute("botonSession","");
                    //} else {
                        misession.setAttribute("botonSession","<input name=\"action\" type=\"submit\" id=\"buscar\" value=\"Buscar\" />");
                    //}
            }
        }        
        
        misession.setAttribute("validadoSession", validado);
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;        
    }
}