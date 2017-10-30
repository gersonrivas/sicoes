/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGeneral;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class AsignaturaSeccionAulaController extends AbstractController {
    private DaoConexion daoConexion;
    
    public AsignaturaSeccionAulaController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
                
        String mensaje = "";
        String pagina = "academico/registroControl/asignaturaSeccionAula";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        // Para Usar los métodos de la clase profesor
        DaoGeneral daoGeneral = new DaoGeneral();      
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos complementarios del Profesor
            daoConexion = new DaoConexion();                                    
            
            
            if ("POST".equals(request.getMethod())) {                    
                // Para el llenado de los demás combos
                if (!"lbElige".equals(request.getParameter("sede")) && (!"lbSinSede".equals(request.getParameter("sede")))) {
                    daoGeneral.setSede(request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                    String opcionesSedes = daoGeneral.BuscarSede(daoConexion.ConexionBD());
                    misession.setAttribute("sedesSession", opcionesSedes);
                    misession.setAttribute("sedeSeleccionadaSession", request.getParameter("sede").substring(2, request.getParameter("sede").length()));

                    if (!"lbElige".equals(request.getParameter("periodo")) && (!"lbSinPeriodo".equals(request.getParameter("periodo")))) {                        
                        daoGeneral.setPeriodo(request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));
                        daoGeneral.BuscarTipoLapsoPeriodo(daoConexion.ConexionBD());
                        String opcionesPeriodos = daoGeneral.BuscarPeriodosActivos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosSession", opcionesPeriodos);
                        misession.setAttribute("periodoSeleccionadoSession", request.getParameter("periodo").substring(2, request.getParameter("periodo").length()));                                              
                        
                        daoGeneral.setSemestreTrimestre(request.getParameter("lapso").substring(2, request.getParameter("lapso").length()));
                        String opcionesLapsos = daoGeneral.BuscarLapsosAsignaturas(daoConexion.ConexionBD());
                        misession.setAttribute("lapsosSession", opcionesLapsos);
                        misession.setAttribute("lapsoSeleccionadoSession", request.getParameter("lapso").substring(2, request.getParameter("lapso").length()));

                        if (!"lbElige".equals(request.getParameter("lapso")) && (!"lbSinLapso".equals(request.getParameter("lapso")))) { 
                            daoGeneral.setEspecialidad(request.getParameter("especialidad").substring(2, request.getParameter("especialidad").length()));
                            String opcionesEspecialidades = daoGeneral.BuscarEspecialidades(daoConexion.ConexionBD());
                            misession.setAttribute("especialidadesSession", opcionesEspecialidades);
                            misession.setAttribute("especialidadSeleccionadaSession", request.getParameter("especialidad").substring(2, request.getParameter("especialidad").length()));
                            
                            if (!"lbElige".equals(request.getParameter("especialidad")) && (!"lbSinEspecialidad".equals(request.getParameter("especialidad")))) { 
                            
                                daoGeneral.setAsignatura(request.getParameter("asignatura").substring(2, request.getParameter("asignatura").length()));
                                String opcionesAsignaturas = daoGeneral.BuscarAsignaturasPorLapso(daoConexion.ConexionBD());
                                misession.setAttribute("asignaturasSession", opcionesAsignaturas);
                                misession.setAttribute("asignaturaSeleccionadaSession", request.getParameter("asignatura").substring(2, request.getParameter("asignatura").length()));
                             
                                if (!"lbElige".equals(request.getParameter("asignatura")) && (!"lbSinAsignatura".equals(request.getParameter("asignatura")))) { 
                                    daoGeneral.setTurno(request.getParameter("turno").substring(2, request.getParameter("turno").length()));
                                    String opcionesTurno = daoGeneral.BuscarTurnos(daoConexion.ConexionBD());
                                    misession.setAttribute("turnosSession", opcionesTurno);
                                    misession.setAttribute("turnoSeleccionadoSession", request.getParameter("turno").substring(2, request.getParameter("turno").length()));

                                    daoGeneral.setSeccion(request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));
                                    String opcionesSeccion = daoGeneral.BuscarSecciones();
                                    misession.setAttribute("seccionesSession", opcionesSeccion);
                                    misession.setAttribute("seccionSeleccionadaSession", request.getParameter("seccion").substring(2, request.getParameter("seccion").length()));

                                    daoGeneral.setAula(request.getParameter("aula").substring(2, request.getParameter("aula").length()));
                                    String opcionesAula = daoGeneral.BuscarAulas(daoConexion.ConexionBD());
                                    misession.setAttribute("aulasSession", opcionesAula);
                                    misession.setAttribute("aulaSeleccionadaSession", request.getParameter("aula").substring(2, request.getParameter("aula").length()));
                                                               
                                }
                            }
                        }
                    }
                                               
                }

                //Si preciona el botón adicionar, eliminar o salir                    
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Eliminar": 
                            mensaje = daoGeneral.ValidarExistenciaHorarioProfesores(daoConexion.ConexionBD());
                            if (mensaje.isEmpty()) {
                                mensaje = daoGeneral.EliminarSeccionesFiltradas(daoConexion.ConexionBD());
                            }                        
                            break;
                        case "Adicionar": 
                            mensaje = daoGeneral.AdicionarSeccionFiltrada(daoConexion.ConexionBD());                            
                            break;
                        case "Salir": {
                            mensaje = "";
                            pagina="principal/principal";
                            break;
                        }
                    }
                }
                
                //Mostrar los datos detalle en la tabla
                String tablaDetalle = daoGeneral.BuscarSeccionesFiltradas(daoConexion.ConexionBD());
                misession.setAttribute("detalleAulasSession", tablaDetalle);
                                    
                //Para desplegar el boton de adicionar o eliminar
                if (daoGeneral.getAula()!=null && daoGeneral.getAula()!=null) {
                    if (tablaDetalle.isEmpty() && !daoGeneral.getAula().equals("Elige") && !daoGeneral.getAula().equals("SinAula")) {
                        misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");                                        
                    } else {
                        if (tablaDetalle.isEmpty()) {
                            misession.setAttribute("botonSession","");
                        } else {
                            misession.setAttribute("botonSession","<input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" />");
                        }
                                        
                    }
                }                
            } else {                    
                // else del POST                        
                    
                //Inicilizo variable de sesion para el manejo del filtro de la sede.
                String opcionesSede = daoGeneral.BuscarSede(daoConexion.ConexionBD());
                misession.setAttribute("sedesSession", opcionesSede);
                    
                //Inicilizo variable de sesion para el manejo del filtro del período.
                String opcionesPeriodos = daoGeneral.BuscarPeriodosActivos(daoConexion.ConexionBD());
                misession.setAttribute("periodosSession", opcionesPeriodos);

                //Inicilizo variable de sesion para el manejo del filtro de la sección.
                String opcionesLapsos = daoGeneral.BuscarLapsosAsignaturas(daoConexion.ConexionBD());
                misession.setAttribute("lapsosSession", opcionesLapsos);                        

                //Inicilizo variable de sesion para el manejo del filtro de la especialidad.
                String opcionesEspecialidades = daoGeneral.BuscarEspecialidades(daoConexion.ConexionBD());
                misession.setAttribute("especialidadesSession", opcionesEspecialidades);  
                    
                //Inicilizo variable de sesion para el manejo del filtro de las asignaturas.
                String opcionesAsignaturas = daoGeneral.BuscarAsignaturasPorLapso(daoConexion.ConexionBD());
                misession.setAttribute("asignaturasSession", opcionesAsignaturas);   
                   
                //Inicilizo variable de sesion para el manejo del filtro de los turnos.
                String opcionesTurnos = daoGeneral.BuscarTurnos(daoConexion.ConexionBD());
                misession.setAttribute("turnosSession", opcionesTurnos);   

                //Inicilizo variable de sesion para el manejo del filtro de las secciones.
                String opcionesSecciones = daoGeneral.BuscarSecciones();
                misession.setAttribute("seccionesSession", opcionesSecciones);   

                //Inicilizo variable de sesion para el manejo del filtro de las aulas.
                String opcionesAulas = daoGeneral.BuscarAulas(daoConexion.ConexionBD());
                misession.setAttribute("aulasSession", opcionesAulas);   
                    
                //Inicializando variable de seccion del detalle de la tabla de filtrado.
                misession.setAttribute("detalleAulasSession", "");
                
                //Inicializando variable del boton.
                misession.setAttribute("botonSession","");

            }
        }
                
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;  
    }
    
}
