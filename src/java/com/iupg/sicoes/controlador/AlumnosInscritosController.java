/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGeneral;
import com.iupg.sicoes.modelo.DaoProfesor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class AlumnosInscritosController extends AbstractController {
    private DaoConexion daoConexion;
    
    public AlumnosInscritosController() {
        
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "academico/registroControl/alumnosInscritos";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        
        String cedulaProf;
        //String nombreApellido="";
        
        if (request.getParameter("profesor")==null || "lbElige".equals(request.getParameter("profesor")) || "lbSinProfesor".equals(request.getParameter("profesor"))) {
            cedulaProf="0";
        } else {
            cedulaProf=request.getParameter("profesor").substring(2, request.getParameter("profesor").length());
        }

        DaoProfesor daoProfesor = new DaoProfesor(cedulaProf);      
        DaoGeneral daoGeneral = new DaoGeneral();
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos del Profesor            
            daoConexion = new DaoConexion();
            String botones="";
            
            // Para guardar la variable de sesión recibida del JSP            
            if (request.getParameter("idMatEspAulSecTurSession")==null) {                
                daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(misession.getAttribute("idMatEspAulSecTurSession").toString()));
                //daoGeneral.setPeriodo(misession.getAttribute("periodoSeleccionadoSession").toString());
                daoProfesor.setPeriodo(misession.getAttribute("periodoSeleccionadoSession").toString());
            } else {
                daoProfesor.setIdMatEspAulSecTur(Integer.valueOf(request.getParameter("idMatEspAulSecTurSession")));
                //daoGeneral.setIdMatEspAulSecTur(Integer.valueOf(request.getParameter("idMatEspAulSecTurSession")));
                misession.setAttribute("idMatEspAulSecTurSession", request.getParameter("idMatEspAulSecTurSession"));
                //daoGeneral.setPeriodo(request.getParameter("periodoSeleccionadoSession"));
                //daoProfesor.setPeriodo(request.getParameter("periodoSeleccionadoSession"));
            }
            
            //Para desplegar la tabla de Alumnos Inscritos
            String tablaDetalle = daoProfesor.BuscarAlumnosInscritos(daoConexion.ConexionBD());
            misession.setAttribute("detalleAlumnosInscritosSession", tablaDetalle);
            
            if (tablaDetalle.isEmpty()) {
                //Validando que haya seleccionado un profesor de la lista
                                
                    
            } else {                    
               
            }
                        
            //if (request.getParameter("dia")!=null) {
            //    daoGeneral.setDia(request.getParameter("dia").substring(2, request.getParameter("dia").length()));
            //}
            //if (request.getParameter("hora_ini")!=null) {
            //    daoGeneral.setHora_ini(request.getParameter("hora_ini").substring(2, request.getParameter("hora_ini").length()));
            //}
            //if (request.getParameter("hora_fin")!=null) {
            //    daoGeneral.setHora_fin(request.getParameter("hora_fin").substring(2, request.getParameter("hora_fin").length()));
            //}

            if ("POST".equals(request.getMethod())) {                    
                                
                //Si preciona el botón adicionar, eliminar o salir                    
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                    case "Retornar": {
                            mensaje = "";
                            pagina="academico/registroControl/asignaturaSeccionAula";
                            break;
                        }
                    }
                    
                    //Refrescar datos
                    tablaDetalle = daoProfesor.BuscarAlumnosInscritos(daoConexion.ConexionBD());
                    misession.setAttribute("detalleAlumnosInscritosSession", tablaDetalle);

                }                
            }
            
            //botones = botones + "<input name=\"action\" type=\"submit\" id=\"retornar\" value=\"retornar\" />";   
            botones = botones + "";   

            //misession.setAttribute("horarioUnificadoSession", request.getParameter("unificado"));
            
            misession.setAttribute("botonSession", botones);                        
        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;
    }   
}