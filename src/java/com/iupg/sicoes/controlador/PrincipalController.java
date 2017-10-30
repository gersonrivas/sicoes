/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.Usuario;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpSessionRequiredException;

/**
 *
 * @author gerson
 */

public class PrincipalController extends AbstractController {
    private static final long serialVersionUID = 1L;
        
    /*
    public PrincipalController() {
    }
   
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */
 
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, Exception {
        
        String pagina;
        HttpSession misession= request.getSession();
        String mensaje;
        //usuario = (Usuario) misession.getAttribute("nombreUsu");
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            if ("POST".equals(request.getMethod())) {
                mensaje = "";
                pagina ="principal/principal";
            } else {
                mensaje = "";
                pagina ="principal/principal";
            }
        }
            
        ModelAndView modelAndView = new ModelAndView(pagina);
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;

    }
}
