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
public class EditarUsuarioAdminController extends AbstractController {
    private DaoConexion daoConexion;
    
    public EditarUsuarioAdminController() {
        
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "sistema/editarUsuarioAdmin";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        String cedulaProf;
        String nombreApellido="";
        
        /*
        if (request.getParameter("estatus")==null || "lbElige".equals(request.getParameter("estatus")) || "lbSinEstatus".equals(request.getParameter("estatus"))) {
            cedulaProf="0";
        } else {
            cedulaProf=request.getParameter("profesor").substring(2, request.getParameter("profesor").length());
        }
        */
        DaoUsuario daoUsuario = new DaoUsuario("","","");              
        //DaoGeneral daoGeneral = new DaoGeneral();
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos del Profesor            
            daoConexion = new DaoConexion();
            String botones = "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />";   
            botones = botones + "<input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" />";   
            boolean cambioContrasena=false;
            
            // Para guardar la variable de sesión recibida del JSP
            
            if (request.getParameter("idUsuarioAdminSession")==null) {                
                daoUsuario.setIdUsu(Integer.valueOf(misession.getAttribute("idUsuarioAdminSession").toString()));
                //daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));

            } else {
                daoUsuario.setIdUsu(Integer.valueOf(request.getParameter("idUsuarioAdminSession")));
                //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(request.getParameter("idMatEspAulSecTurSession")));
                misession.setAttribute("idUsuarioAdminSession", request.getParameter("idUsuarioAdminSession"));
                //daoGeneral.setPeriodo(request.getParameter("periodoSeleccionadoSession"));
            }
            
            daoUsuario.BuscarUsuarioPorId(daoConexion.ConexionBD());
            
            //Revisando la selección de los combosBox y valores
            if (request.getParameter("estatus")!=null) {
                daoUsuario.setEstatus(request.getParameter("estatus").substring(2, request.getParameter("estatus").length()));
            }
            if (request.getParameter("categoria")!=null) {
                daoUsuario.setTipoUsu(request.getParameter("categoria").substring(2, request.getParameter("categoria").length()));
            }
            
            
            if (request.getParameter("usuario")!=null) {
                daoUsuario.setLogin(request.getParameter("usuario"));
            }

            if (request.getParameter("contrasena")!=null) {
                if (!request.getParameter("contrasena").equals(daoUsuario.getPassword())) {
                    daoUsuario.setPassword(request.getParameter("contrasena"));
                    cambioContrasena=true;
                }                
            }

            if (request.getParameter("contrasena2")!=null) {
                if (!request.getParameter("contrasena2").equals(daoUsuario.getPassword2())) {
                    daoUsuario.setPassword2(request.getParameter("contrasena2"));
                }                
            }     
            
            //Para desplegar la tabla de horarios
            String tablaDetalle = daoUsuario.formAdminUsuario(daoConexion.ConexionBD());
            misession.setAttribute("detalleFormularioUsuarioSession", tablaDetalle);
            
            //Validando que haya seleccionado un profesor de la lista
                
            /*      

            if (request.getParameter("estatus")!=null && !"lbElige".equals(request.getParameter("estatus")) && (!"lbSinEstatus".equals(request.getParameter("estatus")))) {
                daoUsuario.setTipoUsu(misession.getAttribute("idMatEspAulSecTurSession")));
                daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
            }
                
                //Inicilizo variable de sesion para el manejo del filtro de profesores.
                String datosProfesor = daoProfesor.BuscarDatosProfesorHorarios(daoConexion.ConexionBD());
                daoProfesor = new DaoProfesor(cedulaProf);
                daoProfesor.setNombres(nombreApellido);
                String opcionesProfesor = daoProfesor.BuscarProfesores(daoConexion.ConexionBD());
                misession.setAttribute("datosProfesorSession", datosProfesor+"<BR>"+opcionesProfesor);
                misession.setAttribute("profesorSeleccionadoSession", request.getParameter("profesor"));
                    
                //misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />"); 
                    
            
            
            else {                    
                String datosProfesor = daoProfesor.BuscarDatosProfesorHorarios(daoConexion.ConexionBD());
                misession.setAttribute("datosProfesorSession", datosProfesor);
                
                String[] datos = datosProfesor.split(" ");    
                
                daoProfesor.setCedula(datos[2]);
                daoGeneral.setCedula(datos[2]);
               
            }
            */            

            
            if ("POST".equals(request.getMethod())) {                    
                                                
                //Si preciona el botón adicionar, eliminar o salir                    
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Eliminar": 
                            mensaje = "Está seguro de Eliminar este Usuario?";
                            botones = "<input name=\"action\" type=\"submit\" id=\"si\" value=\"Si\" />";
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"no\" value=\"No\" />";            
                            break;
                        
                        case "Si": //Confirma la Eliminación del Usuario
                            mensaje = daoUsuario.EliminarUsuario(daoConexion.ConexionBD());
                            break;   
                            
                        case "Guardar":
                            //Para adicionar horarios unificados en todas las especilidades
                            if (!daoUsuario.getPassword().equals(daoUsuario.getPassword2())) {
                                mensaje = "Confirmación de la contraseña no coincide.";
                            } else {
                                if (cambioContrasena) {
                                    daoUsuario.setPassword("md5('"+daoUsuario.getPassword()+"')");
                                    daoUsuario.setPassword2("md5('"+daoUsuario.getPassword2()+"')");
                                } else {
                                    daoUsuario.setPassword("'"+daoUsuario.getPassword()+"'");
                                    daoUsuario.setPassword2("'"+daoUsuario.getPassword2()+"'");
                                }
                                mensaje = daoUsuario.actualizarUsuario(daoConexion.ConexionBD());
                            }
 
                            //mensaje = daoGeneral.AdicionarHorarioProfesor(daoConexion.ConexionBD());                            
                            //daoGeneral.setDia(null);
                            //daoGeneral.setHora_ini(null);
                            //daoGeneral.setHora_fin(null);
                            //daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                            //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                            break;
                            
                            
                        case "Retornar": {
                            mensaje = "";
                            pagina="sistema/usuariosAdmin";
                            break;
                        }
                        case "Salir": {
                            mensaje = "";
                            pagina="principal/principal";
                            break;
                        }
                        
                    }
                    
                    //Refrescar datos
                    tablaDetalle = daoUsuario.formAdminUsuario(daoConexion.ConexionBD());
                    misession.setAttribute("detalleFormularioUsuarioSession", tablaDetalle);

                }                
            }
            /*
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
            */

            /*
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
*/            
            misession.setAttribute("botonSession", botones);                        
            


        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }   
}