/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;






import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGraduados;


import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class GraduadoController extends AbstractController {
    private DaoConexion daoConexion;
    
    public GraduadoController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, Exception, ConfigurationException {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "academico/registroControl/graduado";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoGraduados daoGraduados = new DaoGraduados((String) request.getParameter("0"));
       

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
                      
            //Buscar los datos complementarios del Alumno
            daoConexion = new DaoConexion();                                    
            
            //Buscar el período inscrito Administrativamente            
            if ("POST".equals(request.getMethod())) {
                    
                
                System.out.println(request.getParameter("idCedulaGraduadoSession"));
                
                //Si preciona el botón consultar
                String accion = request.getParameter("action");    
                if (accion == null) {
                    
                    if (!"lbElige".equals(request.getParameter("sede")) && (!"lbSinSede".equals(request.getParameter("sede")))) {
                        daoGraduados.setSede(request.getParameter("sede").substring(2, request.getParameter("sede").length()));
                        String opcionesSede = daoGraduados.BuscarSede(daoConexion.ConexionBD());
                        misession.setAttribute("sedeGraduadoSession", opcionesSede);
                        misession.setAttribute("sedeSeleccionadaSession", request.getParameter("sede"));
                    
                        //if (!"lbElige".equals(request.getParameter("especialidad")) && (!"lbSinEspecialidad".equals(request.getParameter("especialidad")))) {
                        daoGraduados.setCodEspecialidad(request.getParameter("especialidad").substring(2, request.getParameter("especialidad").length()));
                        String opcionesEspecialidad = daoGraduados.BuscarEspecialidad(daoConexion.ConexionBD());
                        misession.setAttribute("especialidadGraduadoSession", opcionesEspecialidad);
                        misession.setAttribute("especialidadSeleccionadaSession", request.getParameter("especialidad"));                            
                        //}
                        daoGraduados.setFecGraduacion(request.getParameter("fecha_graduacion").substring(2, request.getParameter("fecha_graduacion").length()));
                        String opcionesFechaGraduacion = daoGraduados.BuscarFechaGraduacion(daoConexion.ConexionBD());
                        misession.setAttribute("fechaGraduacionGraduadoSession", opcionesFechaGraduacion);
                        misession.setAttribute("fechaGraduadoSeleccionadaSession", request.getParameter("fecha_graduacion"));                            
                        
                    }
                    
                    if (!"lbElige".equals(request.getParameter("fecha_graduacion")) && (!"lb0".equals(request.getParameter("fecha_graduacion")))) {
                        String listadoGraduados = daoGraduados.ListarGraduados(daoConexion.ConexionBD());
                        misession.setAttribute("listadoGraduadosSession", listadoGraduados);
                    } else {
                        misession.setAttribute("listadoGraduadosSession", "");
                    }
                    
                } else { // Si presiona Botón Salir
                    mensaje = "";
                    pagina="principal/principal";
                }
                    
            } else {                    
                // else del POST        
                    
                String opcionesSede = daoGraduados.BuscarSede(daoConexion.ConexionBD());
                misession.setAttribute("sedeGraduadoSession", opcionesSede);
                misession.setAttribute("sedeSeleccionadaSession", request.getParameter("sede"));
 
                String opcionesEspecialidad = daoGraduados.BuscarEspecialidad(daoConexion.ConexionBD());
                misession.setAttribute("especialidadGraduadoSession", opcionesEspecialidad);
                misession.setAttribute("especialidadSeleccionadaSession", request.getParameter("especialidad"));
 
                String opcionesFechaGraduacion = daoGraduados.BuscarFechaGraduacion(daoConexion.ConexionBD());
                misession.setAttribute("fechaGraduacionGraduadoSession", opcionesFechaGraduacion);
                misession.setAttribute("fechaGraduadoSeleccionadaSession", request.getParameter("fec_graduacion"));
                  
                pagina = "academico/registroControl/graduado";
                //String opcionesPeriodos = daoGraduados.BuscarPeriodos(daoConexion.ConexionBD());
                //misession.setAttribute("periodosGraduadosSession", opcionesPeriodos);
            }                
                
            //} else {
            //    pagina = "principal/principal";                           
            //    mensaje = "Error Buscando datos Asociados al Usuario.";
            //} 
        }
        
        
        
        
        
        
        //((AnnotationConfigApplicationContext)ctx).close();
        
        //misession.setAttribute("validadoSession", validado);
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;        
    }
    
    
}
