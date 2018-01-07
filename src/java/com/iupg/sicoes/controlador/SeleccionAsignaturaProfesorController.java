/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoProfesor;

import com.iupg.sicoes.modelo.DaoAlumno;
import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.Validador;
import com.iupg.sicoes.propiedades.ParamConfig;
import com.iupg.sicoes.servicio.EmailService;
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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class SeleccionAsignaturaProfesorController extends AbstractController {
    private DaoConexion daoConexion;
    
    public SeleccionAsignaturaProfesorController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "academico/profesor/seleccionAsignaturaProfesor";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoProfesor daoProfesor = new DaoProfesor((String) misession.getAttribute("cedulaUsuSession"));
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
                
                //Buscar el período inscrito Administrativamente            
                if ("POST".equals(request.getMethod())) {                    
                    // Para el llenado de los demás combos
                    if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {
                        daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                        String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosProfesorSession", opcionesPeriodos);
                        misession.setAttribute("periodoSeleccionadoProfesorSession", request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));

                        daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                        String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                        misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                        misession.setAttribute("materiaSeleccionadaProfesorSession", request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                        
                        
                        daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));
                        String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                        misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);
                        misession.setAttribute("seccionSeleccionadaProfesorSession", request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));                        
                    }
                    
                    //Si preciona el botón consultar                    
                    String accion = request.getParameter("action");
                    
                    if (accion != null) {
                        if (accion.equals("Cargar")) {
                            //Validar lo seleccionado
                            if ((!"lbElige".equals(request.getParameter("materia")))&&(!"lbSinMateria".equals(request.getParameter("materia"))) && (!"lbElige".equals(request.getParameter("seccion")))&&(!"lbSinSeccion".equals(request.getParameter("seccion")))) {
                                //Página de carga 
                                
                                String encabezado = daoProfesor.BuscarCabeceraEvaluacion(daoConexion.ConexionBD());
                                misession.setAttribute("encabezadoEvaluacionSession", encabezado);
                                
                                String cuerpo = daoProfesor.BuscarDetalleEvaluacion(daoConexion.ConexionBD(),"readonly");
                                misession.setAttribute("detalleEvaluacionSession", cuerpo);
                                
                                mensaje = "";
                                pagina = "academico/profesor/cargaNotasProfesor"; 
                            }
                        } else { // Si presiona Botón Salir
                            mensaje = "";
                            pagina="principal/principal";
                        }
                    }
                } else {                    
                    // else del POST                        
                    
                    //pagina ="principal/incripcion";
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