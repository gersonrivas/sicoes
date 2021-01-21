/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.vista;

import java.util.Map;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.view.InternalResourceView;

/**
 *
 * @author gerson
 */
public class JstlView extends InternalResourceView {
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
 
        // Determine the path for the request dispatcher.
        String dispatcherPath = prepareForRendering(request, response);
 
        // set original view being asked for as a request parameter
        //request.setAttribute("partial", dispatcherPath.substring(dispatcherPath.lastIndexOf("/") + 1));
        request.setAttribute("partial", dispatcherPath);
        
        // force everything to be template.jsp
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/template.jsp");
        requestDispatcher.include(request, response);
        }
}
 
