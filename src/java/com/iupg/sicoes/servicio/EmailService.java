/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.servicio;

/**
 * Esta clase envía correos con archivos adjuntos y dentro del html con los parámetros del bean emailService.
 * @author: Gerson Rivas
 * @version: 1.0
 * 
 */
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

 
@Service("emailService")
public class EmailService {
    @Autowired
    // Campos de la Clase.
    private JavaMailSender mailSender;
    private SimpleMailMessage simpleMailMessage;
    
	
    /**
    * Método para asignar asignar el contenido del mensaje del correo.
    * @param mailSender define el contenido del mensaje.
    */
    
    public void setMailSender(JavaMailSender mailSender) {
	this.mailSender = mailSender;
    }
    
    /**
     * Método para asignar el contenido de un mensaje simple.
     * @param simpleMailMessage define el contenido del mensaje simple. 
     */
	
    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }
     
    /**
     * Método para enviar el mensaje de correo.
     * @param to contiene el identificador de correo destino.
     * @param from contiene el identificador de correo del remitente.
     * @param sub contiene el asunto del mensaje.
     * @param msgBody contiene el cuerpo del mensaje en formato html.
     * @param imgCabecera contiene la ruta y nombre del archivo que irá en el en encabezado.
     * @param imgSrc contiene el nombre que se le dará al archivo adjunto y el que se insertará en la cabecera.
     * @param fileAtt conteiene la ruta y nombre del archivo que se adjunta. 
     */
    public void sendEmail(String to,String from,String sub,String msgBody,String imgCabecera, String imgSrc, String fileAtt){
         
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(sub);
            helper.setText(msgBody,true);
            if (imgCabecera!=null) {
                FileSystemResource res = new FileSystemResource(new File(imgCabecera));
                helper.addInline(imgSrc, res);
            }
            if (fileAtt!=null) {
                //Deberá recibir un arreglo para adjuntar varios archivos
                FileSystemResource file = new FileSystemResource(new File(fileAtt));
                helper.addAttachment(fileAtt.substring(fileAtt.lastIndexOf("/")+1,fileAtt.length()), file);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (MailException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
    }
}
