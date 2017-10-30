/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoUsuario;
import com.iupg.sicoes.modelo.Validador;
import com.iupg.sicoes.modelo.ReiniciaContrasena;

import com.iupg.sicoes.servicio.EmailService;
import com.iupg.sicoes.servicio.GenerarContrasena;

import com.iupg.sicoes.servicio.ParamCorreo;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class ReiniciaContrasenaController extends AbstractController {
    private DaoConexion daoConexion;
    private ResultSet rs;
    
    public ReiniciaContrasenaController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String pagina="inicio/reiniciar";
        HttpSession misession= request.getSession();
        String mensaje="";
        ReiniciaContrasena reinicia = null;
        DaoUsuario daoUsuario;
        Validador validador = null;
        GenerarContrasena generarContrasena = null;
        String contrasenaUsu = "";

        if (misession.getAttribute("usuarioSession")==null) {
            if ("POST".equals(request.getMethod())) {
                contrasenaUsu = generarContrasena.getPassword(generarContrasena.MINUSCULAS+generarContrasena.MAYUSCULAS+generarContrasena.ESPECIALES,10);
                daoUsuario = new DaoUsuario(request.getParameter("login").toLowerCase(), contrasenaUsu, request.getParameter("cedula"));
                //Validar los campos 
                if (daoUsuario.getLogin().isEmpty() || daoUsuario.getCedula().isEmpty()) {
                    mensaje = "El identificador de correo y la cédula son requeridos.";                    
                } else {
                    validador = new Validador();
                    if (!validador.ValidarEmail(daoUsuario.getLogin())) {
                        mensaje = "El correo electrónico, no es válido";
                    }                    
                }

                //Enviar Correo
                if (mensaje.isEmpty()) {
                    daoConexion = new DaoConexion();
                    // Verificar si el usuario está registrado por cédula y correo
                    if (daoUsuario.CuentaUsuarioPorCedulaCorreo(daoConexion.ConexionBD(),"AND")==true) {
                        
                        // Actualizar contraseña en la base de datos
                        daoUsuario.iniciarContrasenaUsuario(daoConexion.ConexionBD(), daoUsuario.getLogin(), daoUsuario.getPassword());
                        
                        try {                                
                                // Agregado
                                ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamCorreo.class);
                                ParamCorreo pv = ctx.getBean(ParamCorreo.class);                                
                                                                                           
                                // Definición de variables del correo.
                                String toAddr = daoUsuario.getLogin();
                                String fromAddr = pv.getCorreo_remitente();
                                String subject = "Reinicio de Contraseña para el Acceso al Sistema IUPG";
            
                                String body = "<html><img src='cid:"+pv.getCorreo_imgSrc()+"'>" +
                                        "<body>" +
                                        "<br>" + 
                                        "<br>" +
                                        "<p>";
                                
                                if ("M".equals(daoUsuario.getSexo())){
                                    body = body + "Estimado, ";
                                } else {
                                    body = body + "Estimada, ";
                                }
                                body = body + daoUsuario.getNombreUsu() + 
                                ":</p>" +
                                "<p>Su contraseña ha sido reiniciada satisfactoriamente. </p>" +
                                "<p>Para acceder deberá ingresar el usuario y contraseña suministrada a continuación:</p>" +
                                "<br>" +
                                "<p>     Usuario: <b>" + daoUsuario.getLogin() + "</b></p> " +
                                "<p>     Contraseña: <b>" + daoUsuario.getPassword() + "</b></p> " +
                                "<br>" +
                                "<p><u>Nota:</u>  Deberá ingresar al Sistema con la contraseña suministrada y hacer el cambio de la misma.</p>" +
                                "</body>" +
                                "</html>";
           
                                //StringBuilder sb = new StringBuilder();
                                String confFile = "classpath:com/iupg/sicoes/propiedades/mailProperties.xml";
                                
                                ConfigurableApplicationContext context
                                        = new ClassPathXmlApplicationContext(confFile);
                                //MailMail mm = (MailMail) context.getBean("mailMail");
                                EmailService emailService = (EmailService) context.getBean("emailService");

                                emailService.sendEmail(toAddr, fromAddr, subject, body, pv.getCorreo_imgCabecera(),pv.getCorreo_imgSrc(),null);

                                ((AnnotationConfigApplicationContext)ctx).close();
                                
                                pagina="inicio/login";
                                mensaje = "<td align=\"right\">" +
                                          "<img src=\"/sicoes/recursos/img/info_1.gif\"/>" +
                                          "</td>" +
                                          "<td>Su contraseña fue reiniciada satisfactoriamente. Debe ingresar con la contraseña enviada al correo, luego hacer el cambio de la misma.</td>";
                            } catch (Exception ex) {
                                pagina="inicio/reiniciar";
                                mensaje = "<td align=\"right\">" +
                                          "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                          "</td>" +
                                          "<td>Error en el envío de correo.</td>";
                                System.out.println(ex.getMessage());
                            }
                        } else {
                            pagina="inicio/reiniciar";
                            mensaje = "<td align=\"right\">" +
                                      "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                      "</td>" +
                                      "<td>Cuenta de Usuario no Registrada.</td>";
                        }
                    } else {
                        pagina="inicio/reiniciar";
                        mensaje = "<td align=\"right\">" +
                        "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                        "</td>" +
                        "<td>"+mensaje+"</td>";
                    }
                
                
            }            
        } else {
            pagina="inicio/reiniciar";
            mensaje = "<td align=\"right\">" +
                      "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                      "</td>" +
                      "<td>Su sesión está activa. Si desea cambiar la contraseña debe elegir el cambio de contraseña desde el menú principal.</td>";
        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }
    
}
