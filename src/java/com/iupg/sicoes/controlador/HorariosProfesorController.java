/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGeneral;
import com.iupg.sicoes.modelo.DaoProfesor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class HorariosProfesorController extends AbstractController {
    private DaoConexion daoConexion;
    
    public HorariosProfesorController() {
        
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "academico/registroControl/horariosProfesor";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        String cedulaProf;
        String nombreApellido="";
        
        if (request.getParameter("profesor")==null || "lbElige".equals(request.getParameter("profesor")) || "lbSinProfesor".equals(request.getParameter("profesor"))) {
            cedulaProf="0";
        } else {
            cedulaProf=request.getParameter("profesor").substring(2, request.getParameter("profesor").length());
        }

        DaoProfesor daoProfesor = new DaoProfesor(cedulaProf);      
        DaoGeneral daoGeneral = new DaoGeneral();
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos del Profesor            
            daoConexion = new DaoConexion();
            String botones="";
            
            // Para guardar la variable de sesión recibida del JSP            
            if (request.getParameter("idMatEspAulSecTurSession")==null) {                
                daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                daoGeneral.setPeriodo(misession.getAttribute("periodoSeleccionadoSession").toString());
                daoProfesor.setPeriodo(misession.getAttribute("periodoSeleccionadoSession").toString());
            } else {
                daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(request.getParameter("idMatEspAulSecTurSession")));
                daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(request.getParameter("idMatEspAulSecTurSession")));
                misession.setAttribute("idMatEspAulSecTurSession", request.getParameter("idMatEspAulSecTurSession"));
                daoGeneral.setPeriodo(request.getParameter("periodoSeleccionadoSession"));
                daoProfesor.setPeriodo(request.getParameter("periodoSeleccionadoSession"));
            }
            
            //Para desplegar la tabla de horarios
            String tablaDetalle = daoProfesor.BuscarHorariosProfesor(daoConexion.ConexionBD());
            misession.setAttribute("detalleHorariosProfesorSession", tablaDetalle);
            
            if (tablaDetalle.isEmpty()) {
                //Validando que haya seleccionado un profesor de la lista
                
                if (request.getParameter("cedula")!=null && !"".equals(request.getParameter("cedula"))) {
                    //daoProfesor.setCedula(request.getParameter("cedula"));
                    //daoGeneral.setCedula(request.getParameter("cedula"));
                    cedulaProf=request.getParameter("cedula");
                } else {                    
                    if (request.getParameter("nombreApellido")!=null && !"".equals(request.getParameter("nombreApellido"))) {
                        nombreApellido=request.getParameter("nombreApellido");
                    } else {                
                        if (request.getParameter("profesor")!=null && !"lbElige".equals(request.getParameter("profesor")) && (!"lbSinProfesor".equals(request.getParameter("profesor")))) {
                            //daoProfesor = new DaoProfesor(cedulaProf);
                            //daoProfesor.setCedula(cedulaProf);
                            daoGeneral.setCedula(cedulaProf);
                            daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                            daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                        }
                    }
                }
                
                //Inicilizo variable de sesion para el manejo del filtro de profesores.
                String datosProfesor = daoProfesor.BuscarDatosProfesorHorarios(daoConexion.ConexionBD());
                daoProfesor = new DaoProfesor(cedulaProf);
                daoProfesor.setNombres(nombreApellido);
                String opcionesProfesor = daoProfesor.BuscarProfesores(daoConexion.ConexionBD());
                misession.setAttribute("datosProfesorSession", datosProfesor+"<BR>"+opcionesProfesor);
                misession.setAttribute("profesorSeleccionadoSession", request.getParameter("profesor"));
                    
                //misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />"); 
                    
            } else {                    
                String datosProfesor = daoProfesor.BuscarDatosProfesorHorarios(daoConexion.ConexionBD());
                misession.setAttribute("datosProfesorSession", datosProfesor);
                
                String[] datos = datosProfesor.split(" ");    
                
                daoProfesor.setCedula(datos[2]);
                daoGeneral.setCedula(datos[2]);
               
            }
                        
            if (request.getParameter("dia")!=null) {
                daoGeneral.setDia(request.getParameter("dia").substring(2, request.getParameter("dia").length()));
            }
            if (request.getParameter("hora_ini")!=null) {
                daoGeneral.setHora_ini(request.getParameter("hora_ini").substring(2, request.getParameter("hora_ini").length()));
            }
            if (request.getParameter("hora_fin")!=null) {
                daoGeneral.setHora_fin(request.getParameter("hora_fin").substring(2, request.getParameter("hora_fin").length()));
            }

            if ("POST".equals(request.getMethod())) {                    
                                
                //Si preciona el botón adicionar, eliminar o salir                    
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Eliminar": 
                            mensaje = daoGeneral.EliminarHorariosProfesor(daoConexion.ConexionBD());
                            break;
                        case "Adicionar":
                            //Para adicionar horarios unificados en todas las especilidades
                            if (!daoGeneral.HayChoqueHorarioProfesor(daoConexion.ConexionBD())) {
                                if (request.getParameter("unificada")==null) {
                                    mensaje = daoGeneral.AdicionarHorarioProfesor(daoConexion.ConexionBD());
                                } else {
                                    mensaje = daoGeneral.AdicionarAsignaturaSeccionAulasUnificadas(daoConexion.ConexionBD());
                                    mensaje = mensaje + daoGeneral.AdicionarHorarioProfesorUnificado(daoConexion.ConexionBD());                            
                                }
                            } else {
                                mensaje = "Horario coincide para este profesor.";
                            }
                            //mensaje = daoGeneral.AdicionarHorarioProfesor(daoConexion.ConexionBD());                            
                            daoGeneral.setDia(null);
                            daoGeneral.setHora_ini(null);
                            daoGeneral.setHora_fin(null);
                            daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                            daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                            break;
                        case "Retornar": {
                            mensaje = "";
                            pagina="academico/registroControl/asignaturaSeccionAula";
                            break;
                        }
                    }
                    
                    //Refrescar datos
                    tablaDetalle = daoProfesor.BuscarHorariosProfesor(daoConexion.ConexionBD());
                    misession.setAttribute("detalleHorariosProfesorSession", tablaDetalle);

                }                
            }
            
            //Validando la selección de los filtros
            if (daoGeneral.getDia()!=null && !"lbElige".equals(daoGeneral.getDia()) && (!"SinDia".equals(daoGeneral.getDia())) &&
                daoGeneral.getHora_ini()!=null && !"lbElige".equals(daoGeneral.getHora_ini()) && (!"SinHora".equals(daoGeneral.getHora_ini())) &&
                daoGeneral.getHora_fin()!=null && !"lbElige".equals(daoGeneral.getHora_fin()) && (!"SinHora".equals(daoGeneral.getHora_fin())) ) {
                
                if ((!misession.getAttribute("datosProfesorSession").toString().contains("Sin Profesor")) && tablaDetalle.isEmpty() &&
                     request.getParameter("profesor")!=null && !"lbElige".equals(request.getParameter("profesor")) && (!"lbSinProfesor".equals(request.getParameter("profesor")))) {
                        botones = botones + "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />";                    
                }
                if (!tablaDetalle.isEmpty() ) {
                    botones = botones + "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />";                    
                }
            } 
            //Validando si la tabla de horarios de profesores está vacia
            if (!tablaDetalle.isEmpty() ) {
                botones = botones + "<input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" />";   
            }

            String opcionesDia = daoGeneral.BuscarDias(daoConexion.ConexionBD());
            misession.setAttribute("diasHorarioSession", opcionesDia);
            misession.setAttribute("diaSeleccionadoSession", request.getParameter("dia"));
                
            String opcionesHoraIni = daoGeneral.BuscarHorasInicio(daoConexion.ConexionBD());
            misession.setAttribute("horasInicioSession", opcionesHoraIni);
            misession.setAttribute("horaIniSeleccionadoSession", request.getParameter("hora_ini"));
                
            String opcionesHoraFin = daoGeneral.BuscarHorasFin(daoConexion.ConexionBD());
            misession.setAttribute("horasFinSession", opcionesHoraFin);
            misession.setAttribute("horaFinSeleccionadoSession", request.getParameter("hora_fin"));
        
            String opcionesUnificado = daoGeneral.BuscarSeccionesUnificadas(daoConexion.ConexionBD());
            misession.setAttribute("horarioUnificadoSession", opcionesUnificado);
            //misession.setAttribute("horarioUnificadoSession", request.getParameter("unificado"));
            
            misession.setAttribute("botonSession", botones);                        
        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }   
}