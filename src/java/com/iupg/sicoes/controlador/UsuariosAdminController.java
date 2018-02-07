/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGeneral;
import com.iupg.sicoes.modelo.DaoProfesor;
import com.iupg.sicoes.modelo.DaoUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class UsuariosAdminController extends AbstractController {
    private DaoConexion daoConexion;
    
    public UsuariosAdminController() {
        
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "sistema/usuariosAdmin";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        //String cedulaProf;
        //String nombreApellido="";
        
        //if (request.getParameter("profesor")==null || "lbElige".equals(request.getParameter("profesor")) || "lbSinProfesor".equals(request.getParameter("profesor"))) {
        //    cedulaProf="0";
        //} else {
        //    cedulaProf=request.getParameter("profesor").substring(2, request.getParameter("profesor").length());
        //}

        //DaoProfesor daoProfesor = new DaoProfesor(cedulaProf);      
        DaoUsuario daoUsuario = new DaoUsuario(" ", " ", " ");
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos del Profesor            
            daoConexion = new DaoConexion();
            String botones="";
            
            // Para guardar la variable de sesión recibida del JSP
            //if (request.getParameter("idMatEspAulSecTurSession")==null) {                
                //daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                //daoGeneral.setPeriodo(misession.getAttribute("periodoSeleccionadoSession").toString());
            //} else {
                //daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(request.getParameter("idMatEspAulSecTurSession")));
                //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(request.getParameter("idMatEspAulSecTurSession")));
                //misession.setAttribute("idMatEspAulSecTurSession", request.getParameter("idMatEspAulSecTurSession"));
                //daoGeneral.setPeriodo(request.getParameter("periodoSeleccionadoSession"));
            //}
            
            



            //Para desplegar la tabla de horarios
            String tablaDetalle = daoUsuario.BuscarListaUsuarios(daoConexion.ConexionBD());
            misession.setAttribute("detalleListaUsuariosSession", tablaDetalle);
            
            
            
            
            
            /*if (tablaDetalle.isEmpty()) {
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
            */            
            //Guardando los valores selccionados
            if (request.getParameter("categoria")!=null) {
                daoUsuario.setTipoUsu(request.getParameter("categoria").substring(2, request.getParameter("categoria").length()));
            }
            if (request.getParameter("estatus")!=null) {
                daoUsuario.setEstatus(request.getParameter("estatus").substring(2, request.getParameter("estatus").length()));
            }

            
            //if (request.getParameter("hora_fin")!=null) {
            //    daoGeneral.setHora_fin(request.getParameter("hora_fin").substring(2, request.getParameter("hora_fin").length()));
            //}

            if ("POST".equals(request.getMethod())) {                                
                //Si preciona el botón adicionar, eliminar o salir                    
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Eliminar": 
                            //mensaje = daoGeneral.EliminarHorariosProfesor(daoConexion.ConexionBD());
                            break;
                        case "Adicionar":
                            //Para adicionar horarios unificados en todas las especilidades
                            /*if (!daoGeneral.HayChoqueHorarioProfesor(daoConexion.ConexionBD())) {
                                if (request.getParameter("unificada")==null) {
                                    mensaje = daoGeneral.AdicionarHorarioProfesor(daoConexion.ConexionBD());
                                } else {
                                    mensaje = daoGeneral.AdicionarAsignaturaSeccionAulasUnificadas(daoConexion.ConexionBD());
                                    mensaje = mensaje + daoGeneral.AdicionarHorarioProfesorUnificado(daoConexion.ConexionBD());                            
                                }
                            } else {
                                mensaje = "Horario coincide para este profesor.";
                            }*/
                            //mensaje = daoGeneral.AdicionarHorarioProfesor(daoConexion.ConexionBD());                            
                            //daoGeneral.setDia(null);
                            //daoGeneral.setHora_ini(null);
                            //daoGeneral.setHora_fin(null);
                            //daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                            //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                            break;
                        case "Salir": {
                            mensaje = "";
                            pagina="principal/principal";
                            break;
                        }
                    }
                    
                    //Refrescar datos
                    //tablaDetalle = daoUsuario.BuscarListaUsuarios(daoConexion.ConexionBD());
                    //misession.setAttribute("detalleListaUsuariosSession", tablaDetalle);
                    
                    //String opcionesCategoria = daoUsuario.buscarCategoriasUsuario(daoConexion.ConexionBD());
                    //misession.setAttribute("categoriaUsuariosSession", opcionesCategoria);
                    //misession.setAttribute("categoriaSeleccionadaSession", request.getParameter("categoria"));
                }                
            }
            
            //Validando la selección de los filtros
            if (daoUsuario.getTipoUsu()!=null && !"lbElige".equals(daoUsuario.getTipoUsu()) && (!"SinCategoria".equals(daoUsuario.getTipoUsu()))) {
                //&&
            //    daoGeneral.getHora_ini()!=null && !"lbElige".equals(daoGeneral.getHora_ini()) && (!"SinHora".equals(daoGeneral.getHora_ini())) &&
            //    daoGeneral.getHora_fin()!=null && !"lbElige".equals(daoGeneral.getHora_fin()) && (!"SinHora".equals(daoGeneral.getHora_fin())) ) {
                
                //if ((!misession.getAttribute("datosProfesorSession").toString().contains("Sin Profesor")) && tablaDetalle.isEmpty() &&
                //     request.getParameter("profesor")!=null && !"lbElige".equals(request.getParameter("profesor")) && (!"lbSinProfesor".equals(request.getParameter("profesor")))) {
                //        botones = botones + "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />";                    
                //}
                //if (!tablaDetalle.isEmpty() ) {
                //    botones = botones + "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />";                    
                //}
            //} 
            //Validando si la tabla de horarios de profesores está vacia
            //if (!tablaDetalle.isEmpty() ) {
            //    botones = botones + "<input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" />";   
            }

            String opcionesCategoria = daoUsuario.buscarCategoriasUsuario(daoConexion.ConexionBD());
            misession.setAttribute("categoriaUsuariosSession", opcionesCategoria);
            misession.setAttribute("categoriaSeleccionadaSession", request.getParameter("categoria"));
                            
            String opcionesEstatus = daoUsuario.buscarEstatusUsuario();
            misession.setAttribute("estatusUsuariosSession", opcionesEstatus);
            misession.setAttribute("estatusSeleccionadoSession", request.getParameter("estatus"));
            
            tablaDetalle = daoUsuario.BuscarListaUsuarios(daoConexion.ConexionBD());
            misession.setAttribute("detalleListaUsuariosSession", tablaDetalle);

            
            misession.setAttribute("botonSession", botones);                        
        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }   
}