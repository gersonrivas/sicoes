/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gerson
 */
public class ExpiredController extends AbstractController {
    
    public ExpiredController() {
    }
    
    @Override
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        HttpSession misession = request.getSession();
        
        //Destruir la session
        misession.invalidate();
        
        ModelAndView modelAndView = new ModelAndView("fin/expiracion");        
        return modelAndView;
    }    
}
