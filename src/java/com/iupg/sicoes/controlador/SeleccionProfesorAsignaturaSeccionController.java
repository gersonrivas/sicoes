/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;
import com.iupg.sicoes.modelo.DaoProfesor;

import com.iupg.sicoes.modelo.DaoConexion;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

                    if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {
                        daoProfesor.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                    //}
                    //if (!"lbElige".equals(request.getParameter("materia")) && (!"lbSinAsignatura".equals(request.getParameter("materia")))) {
                        daoProfesor.setAsignatura(request.getParameter("materia").substring(2, request.getParameter("materia").length()));


                    //}
                    
                    //if (!"lbElige".equals(request.getParameter("seccion")) && (!"lbSinSeccion".equals(request.getParameter("seccion")))) {
                        daoProfesor.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));    
                        
                        misession.setAttribute("botonSession","<input name=\"action\" type=\"submit\" id=\"cargar\" value=\"Cargar\" />");
                    }

                    String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                    misession.setAttribute("periodosProfesorSession", opcionesPeriodos);
                    misession.setAttribute("periodoSeleccionadoProfesorSession", request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));                       

                    String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                    misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                    misession.setAttribute("materiaSeleccionadaProfesorSession", request.getParameter("materia").substring(2, request.getParameter("materia").length()));
                        
                    String opcionesSecciones = daoProfesor.BuscarSeccionesMateriasAsignadas(daoConexion.ConexionBD());
                    misession.setAttribute("seccionesMateriaProfesorSession", opcionesSecciones);
                    misession.setAttribute("seccionSeleccionadaProfesorSession", request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));
 
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
                        String opcionesPeriodos = daoProfesor.BuscarPeriodosActivos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosProfesorSession", opcionesPeriodos);

                        String opcionesMaterias = daoProfesor.BuscarMateriasAsignadas(daoConexion.ConexionBD());
                        misession.setAttribute("materiasProfesorSession", opcionesMaterias);
                        
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