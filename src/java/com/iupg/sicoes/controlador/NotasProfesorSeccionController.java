/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoProfesor;
import com.iupg.sicoes.modelo.DaoUsuario;

import com.iupg.sicoes.modelo.DaoConexion;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 * 
 */
public class NotasProfesorSeccionController extends AbstractController {
    private DaoConexion daoConexion;
    
    public NotasProfesorSeccionController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "academico/registroControl/seleccionProfesorAsignaturaSeccion";
        HttpSession misession= request.getSession(); 

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos del Profesor
            daoConexion = new DaoConexion();
            
            DaoProfesor daoProfesor = new DaoProfesor((String) misession.getAttribute("cedulaProfesorSession"));
        
            daoProfesor.setPeriodo((String) misession.getAttribute("periodoSeleccionadoProfesorSession"));
            daoProfesor.setAsignatura((String) misession.getAttribute("materiaSeleccionadaProfesorSession"));
            daoProfesor.setSeccion((String) misession.getAttribute("seccionSeleccionadaProfesorSession"));
            DaoUsuario daoUsuario = new DaoUsuario("", "", daoProfesor.getCedula());
            daoUsuario.setIdUsu((Integer) misession.getAttribute("idUsuarioSession"));

            if (daoProfesor.BuscarDatosProfesor(daoConexion.ConexionBD())) {
                
                //Buscar el período inscrito Administrativamente                        
                if ("POST".equals(request.getMethod())) {                                        
                    // Verificando el botón presionado
                    String accion = request.getParameter("action");
                    
                    if (accion != null) {
                        if (accion.equals("Guardar")) {
                            
                            String[] cedulas = request.getParameterValues("cedulas");
                            String mensajeValida = "";
                            String sqlInsertUpdate = "";

                            //Revisando las Notas por cada Alumno
                            if (cedulas!=null && cedulas.length>0) {                                
                                for(int i=0; i<cedulas.length; i++) {
                                    //System.out.println("Para la cedula:-->"+cedulas[i]);
                                    //if (mensaje.equals("")||mensaje.isEmpty()) {
                                    String[] notas = request.getParameterValues(cedulas[i]);
                                    String notas_cargadas = "";
                                    //Recorrer todas las notas cargadas por cada Alumno
                                    for(int j=0; j<notas.length; j++) {
                                        if (notas[j].equals("")||notas[j].isEmpty())
                                            notas[j]=" ";
                                        //Para guardar sólo las notas cargadas
                                        if (notas_cargadas.equals("")||notas_cargadas.isEmpty()) {
                                            notas_cargadas = notas[j];
                                        } else {
                                            notas_cargadas = notas_cargadas + ";" + notas[j];
                                        } 
                                    }
                                                                            
                                    // VAlidar las ponderaciones
                                    mensajeValida = daoProfesor.ValidarNotas(daoConexion.ConexionBD(),cedulas[i],notas_cargadas);
 
                                    if (mensajeValida=="") {
                                        // Guardar las materias
                                        //mensaje = daoProfesor.ValidarNotas(daoConexion.ConexionBD(),cedulas[i],notas_cargadas);
                                        sqlInsertUpdate = sqlInsertUpdate + daoProfesor.ConstruirInsertUpdate(daoConexion.ConexionBD(),cedulas[i],notas_cargadas,daoUsuario.getIdUsu());                                            
                                    } else {
                                        mensaje =  mensaje + mensajeValida;
                                        mensajeValida = "";
                                    }

                                }                                 
                            }                         
                                                        
                            //validado = true;
                            if (!mensaje.equals("")&&mensaje!="")
                                mensaje = "Atención: la " + mensaje + " sobrepasa la ponderción establecida. Se guardarán sólo los datos válidos.";
                            
                            if (!sqlInsertUpdate.trim().equals("")) 
                            mensaje = mensaje + daoProfesor.GuardarNotas(daoConexion.ConexionBD(), sqlInsertUpdate);                            
                            
                            if (mensaje==""||mensaje.equals("")) {
                                mensaje = mensaje + " La información ha sido guardada satisfactoriamente y enviada vía correo electrónico.";                                
                                
                                //Emitir Actas de Notas y Enviar
                                
                                    
                                //Enviar mensaje de finalización del proceso.
                                misession.setAttribute("incripcionAlumnoSession", 
                                        "<tr><td><img src=\"/sicoes/recursos/img/info_1.gif\"/></td><td colspan=\"4\" align=\"center\"> El retiro de Asignatura(s) fue procesada satisfactoriamente.  <br> La planilla de inscripción será enviada a su correo electrónico, el cual <br> deberá imprimir y validarla ante el Departamento de Registro y Control.</td></tr>");
                                //validado = true;
                                //Fin Enviar por Correo
                                
                                // Fin Aqui
                                
                            }                            
                 
                            // Armar el cuerpo de la tabla para refrescar lo guardado
                            String nuevoCuerpo = daoProfesor.BuscarDetalleEvaluacion(daoConexion.ConexionBD());
                            misession.setAttribute("detalleEvaluacionSession", nuevoCuerpo);   
                            
                            pagina = "academico/profesor/cargaNotasProfesor";                             
                            
                        } else { // Si presiona Botón Salir
                            mensaje = "";
                            pagina="academico/profesor/notasProfesorSeccion";
                        }                    
                    }                    
                } else {                    
                    // else del POST                        
                    
                    //pagina ="principal/incripcion";
                    // Construir la tabla para la carga de notas
                    String encabezado = daoProfesor.BuscarCabeceraEvaluacion(daoConexion.ConexionBD());
                    misession.setAttribute("encabezadoEvaluacionSession", encabezado);                    
                    
                    String cuerpo = daoProfesor.BuscarDetalleEvaluacion(daoConexion.ConexionBD());
                    misession.setAttribute("detalleEvaluacionSession", cuerpo);                    

                }
            } else {
                //pagina ="principal/incripcion";                           
                mensaje = "Error Buscando datos Asociados al Usuario.";
            } 
        }        

        //((AnnotationConfigApplicationContext)ctx).close();
        
        //misession.setAttribute("validadoSession", validado);
        
        //daoConexion.Desconectar();
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;        
    }
}