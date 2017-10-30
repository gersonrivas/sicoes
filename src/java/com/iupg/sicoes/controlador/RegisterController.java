/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.iupg.sicoes.modelo.Register;
import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoUsuario;
import com.iupg.sicoes.servicio.EmailService;
import com.iupg.sicoes.servicio.GenerarContrasena;
import com.iupg.sicoes.servicio.ParamCorreo;
import java.sql.ResultSet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author gerson
 */
public class RegisterController extends AbstractController {
    private DaoConexion daoConexion;
    private ResultSet rs;
    
    @Override
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String pagina="inicio/register";
        HttpSession misession= request.getSession();
        String mensaje="";
        Register registro;
        DaoUsuario daoUsuario;
        GenerarContrasena generarContrasena = null;
        String contrasenaUsu = generarContrasena.getPassword(generarContrasena.MINUSCULAS+generarContrasena.MAYUSCULAS+generarContrasena.ESPECIALES,10);
        //usuario = (Usuario) misession.getAttribute("nombreUsu");
        
        if (misession.getAttribute("usuarioSession")==null) {
            if ("POST".equals(request.getMethod())) {
                registro = new Register(request.getParameter("email1").toLowerCase(),request.getParameter("cedula"), request.getParameter("email2").toLowerCase());
                
                // Validar los datos de entrada
                mensaje = registro.validaCampos();
                if (mensaje.isEmpty()) {
                    daoConexion = new DaoConexion();                   
                    
                    // Verificar si el usuario está registrado por cédula o email
                    daoUsuario = new DaoUsuario(registro.getLogin(), null, registro.getCedula());
                    if (daoUsuario.CuentaUsuarioPorCedulaCorreo(daoConexion.ConexionBD(),"OR")) {
                        mensaje = "<td align=\"right\">" +
                                  "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                  "</td>" +
                                  "<td>Usuario ya está registrado.</td>";
                    } else {
                        rs = daoUsuario.cedulaExite(daoConexion.ConexionBD());
                        if (rs.next()) {
                            daoUsuario.registarUsuario(daoConexion.ConexionBD(), registro.getLogin(), contrasenaUsu, rs.getString("categ_usu"),rs.getString("id"));
                            //System.out.println(rs.getString("categ_usu"));
                            pagina="inicio/login";
                            mensaje = "<td align=\"right\">" +
                                      "<img src=\"/sicoes/recursos/img/info_1.gif\"/>" +
                                      "</td>" +
                                      "<td>Usuario Registrado Satisfatoriamente. Debe ingresar con la contraseña enviada al correo, luego hacer el cambio de la misma.</td>";
                            
                            try {

                                // Agregado
                                ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamCorreo.class);
                                ParamCorreo pv = ctx.getBean(ParamCorreo.class);                                
                                                                                           
                                // Definición de variables del correo.
                                String toAddr = registro.getLogin();
                                String fromAddr = pv.getCorreo_remitente();
                                String subject = "Registro de Usuario IUPG";                               
            
                                String body = "<html><img src='cid:"+pv.getCorreo_imgSrc()+"'>" +
                                        "<body>" +
                                        "<br>" + 
                                        "<br>" +
                                        "<p>";
                                
                                if ("M".equals(rs.getString("sexo"))){
                                    body = body + "Estimado, ";
                                } else {
                                    body = body + "Estimada, ";
                                }
                                body = body + rs.getString("nombre") + 
                                ":</p>" +
                                "<p>Su cuenta de acceso al Sistema de Control de Estudios <b>SICOES</b>, ha sido registrada satisfactoriamente. </p>" +
                                "<p>Para acceder deberá ingresar con su cuenta registrada y contraseña suministrada a continuación:</p>" +
                                "<br>" +
                                "<p>     Usuario: <b>" + registro.getLogin() + "</b></p> " +
                                "<p>     Contraseña: <b>" + contrasenaUsu + "</b></p> " +
                                "<br>" +
                                "<p><u>Nota:</u>  Con los datos suministrados, deberá  ingresar al  Sistema  y  activar el acceso cambiando la</p>" +
                                "<p>contraseña.</p>" +
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

                                //Enviar correo
                /*
                                ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring-Mail.xml");
    	 
                                MailMail mm = (MailMail) context.getBean("mailMail");
                           
                                mm.sendMail("controlestudios@iupg.net.ve",
                                registro.getEmail1(),
                                "Registro de Usuario IUPG", 
                                "Estimado(a), " +rs.getString("nombre") + ": \n\n " +
                                    "Su cuenta de acceso al Sistema de Control de Estudios SICOES, ha sido registrada satisfactoriamente. \n\n " +
                                    "Para acceder deberá ingresar el usuario y contraseña suministradas: \n\n" + 
                                    "     Usuario: " + registro.getEmail1() + " \n\n " +
                                    "     Contraseña: " + registro.getPassword1() + " \n\n " +
                                    "Deberá ingresar al Sistema y hacer el cambio de contraseña correspondiente.");
                                //Fin enviar
                        */
                            } catch (Exception ex) {
                                pagina="inicio/register";                   
                                mensaje = "<td align=\"right\">" +
                                      "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                      "</td>" +
                                      "<td>Error en el envío de correo.</td>";

                                System.out.println(ex.getMessage());
                            }
                            
                        } else {                            
                            mensaje = "<td align=\"right\">" +
                                      "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                                      "</td>" +
                                      "<td>Número de Cédula o Pasaporte no Registrado. <br>Favor dirigirse al Departamento de Registro y Control.</td>";
                        }                        
                    }
                } else {                    
                    mensaje = "<td align=\"right\">" +
                              "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                              "</td>" +
                              "<td>"+mensaje+"</td>";
                }
            }
        }
                
        ModelAndView modelAndView = new ModelAndView(pagina);
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }
}
