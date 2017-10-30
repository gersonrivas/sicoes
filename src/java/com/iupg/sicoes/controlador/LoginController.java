/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoAlumno;
import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.iupg.sicoes.modelo.Validador;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 * Controlador del Logeo de usuario
 * @author gerson
 */
public class LoginController extends AbstractController {
    private DaoConexion daoConexion;
    private static final long serialVersionUID = 1L;
    
    /*
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
        String mensaje = "";
        String pagina = "inicio/login";
        HttpSession misession= request.getSession();
        DaoUsuario daoUsuario;

        Validador validador = new Validador();
        
        //Variables controladoras de sesión
        misession.setAttribute("tipoUsuSession", null);
        misession.setAttribute("usuarioSession",null);
                        
        if ("POST".equals(request.getMethod())) {
            // Validar si existe sesion activa
            if (misession.getAttribute("usuarioSession")==null) {
                daoUsuario = new DaoUsuario(request.getParameter("login").toLowerCase(), request.getParameter("password"),null);
                //Validar los campos 
                if (daoUsuario.getLogin().isEmpty() || daoUsuario.getPassword().isEmpty()) {
                    mensaje = "El usuario y contraseña, son requeridos";
                } else {
                    if (!validador.ValidarEmail(daoUsuario.getLogin())) {
                        mensaje = "El campo usuario, debe ser una dirección de correo válida";                    
                    }
                }
                //Validar que exista el usuario
                if (mensaje.isEmpty()) {
                    daoConexion = new DaoConexion();
                    // Validar que la cuenta exista
                    if (daoUsuario.CuentaUsuarioRegistrada(daoConexion.ConexionBD())==false) {
                        mensaje = "Usuario no registrado.";
                        // Validar Si la cuenta del usuario existe por cuenta de correo y contraseña.
                    } else {
                        if (daoUsuario.CuentaUsuarioPorCorreoContrasena(daoConexion.ConexionBD())==false) {   
                            mensaje = "Usuario o contraseña no válida.";
                        } else {
                            if (daoUsuario.getEstatus().equals("A")) {                            
                                // Asignar variables se sesion
                                misession.setAttribute("usuarioSession", daoUsuario.getNombreUsu());
                                misession.setAttribute("tipoUsuSession", daoUsuario.getTipoUsu());
                                misession.setAttribute("cedulaUsuSession", daoUsuario.getCedula());
                                misession.setAttribute("idUsuarioSession", daoUsuario.getIdUsu());
                                misession.setAttribute("loginSession", request.getParameter("login"));
                                misession.setAttribute("tiempoSession", daoUsuario.getTiempoSesion());
                                //Para extraer datos del Alumno
                                //Definiendo el tiempo de Sesión en segundos
                                misession.setMaxInactiveInterval(daoUsuario.getTiempoSesion()*60);
                            
                                pagina="principal/principal";
                                mensaje = "";
                            } else {
                                if (daoUsuario.getEstatus().equals("I")) {                                
                                                              
                                    misession.setAttribute("login", daoUsuario.getLogin());
                                    misession.setAttribute("password", daoUsuario.getPassword());                                
                                                            
                                    pagina="inicio/activar";
                                    mensaje = "";
                                } else {                                
                                    mensaje = "Usuario no está activo, debe contactar al Departamento de Registro y Control.";
                                }
                            }
                        }
                    }
                    daoConexion.Desconectar();                
                }
                // Dar el formato al mensaje de salida
                if (!mensaje.isEmpty()) {
                    mensaje = "<td align=\"right\">" +
                    "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                    "</td>" +
                    "<td>"+mensaje+"</td>";                
                }
            } else { //Usuario con la sesion activa
                mensaje = "";
                pagina="principal/principal";
            } 
        }
        
            
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }    
}
