/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoUsuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.iupg.sicoes.modelo.ReiniciaContrasena;

/**
 *
 * @author gerson
 */
public class ActivarController extends AbstractController {
    private DaoConexion daoConexion;
    
    public ActivarController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        String mensaje = "";
        String pagina = "inicio/activar";

        DaoUsuario daoUsuario;
        ReiniciaContrasena reiniciaContrasena;
        
        if ("POST".equals(request.getMethod())) {            
            daoConexion = new DaoConexion();
            daoUsuario = new DaoUsuario(request.getParameter("login"), request.getParameter("password"), null);
            //daoUsuario.set request.getParameter("login");
            reiniciaContrasena = new ReiniciaContrasena(request.getParameter("login"), request.getParameter("password"), request.getParameter("password1"), request.getParameter("password2"));
                
            //Validar los campos            
            mensaje = reiniciaContrasena.validaCampos();
            if (mensaje.isEmpty()) {
                if (!daoUsuario.CuentaUsuarioPorCorreoContrasena(daoConexion.ConexionBD())) {
                    mensaje = "<td align=\"right\">" +
                              "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                              "</td>" +
                              "<td>La cuenta o contraseña actual no coincide con la registrada.</td>";
                    //pagina = "inicio/activar";
                } else {
                    mensaje = reiniciaContrasena.validaCampos();
                    if (mensaje.isEmpty()) {                    
                        if (daoUsuario.getEstatus().equals("A")) {
                            mensaje = "<td align=\"right\">" +
                                      "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                      "</td>" +
                                      "<td>La cuenta está Activa.</td>";
                            pagina = "inicio/login";
                        } else {
                            if (daoUsuario.getEstatus().equals("I")) {
                                daoUsuario.activarUsuario(daoConexion.ConexionBD(), reiniciaContrasena.getLogin(), reiniciaContrasena.getPassword(), reiniciaContrasena.getPassword1());                                    
                                mensaje = "<td align=\"right\">" +
                                          "<img src=\"/sicoes/recursos/img/info_1.gif\"/>" +
                                          "</td>" +
                                          "<td>La cuenta fue activada satisfactoriamente.</td>";
                                pagina = "inicio/login";
                            } else {
                                mensaje = "<td align=\"right\">" +
                                          "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                          "</td>" +
                                          "<td>La cuenta no puede ser activada, debe dirigirse al Departamento de Registro y Control.</td>";                                    
                                pagina = "inicio/login";
                            }
                        }                        
                    } else {
                        mensaje = "<td align=\"right\">" +
                                  "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                  "</td>" +
                                  "<td>"+mensaje+"</td>";
                    }
                }                    
            } else {
                mensaje = "<td align=\"right\">" +
                          "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                          "</td>" +
                          "<td>"+mensaje+"</td>";                                
            }
        } 
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        //modelAndView.addObject("login", request.getParameter("email"));
        return modelAndView;
    }
    
}
