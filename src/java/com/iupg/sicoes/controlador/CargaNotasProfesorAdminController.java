/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoProfesor;
import com.iupg.sicoes.modelo.DaoUsuario;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.propiedades.ParamConfig;
import com.iupg.sicoes.servicio.EmailService;
import com.iupg.sicoes.servicio.ParamCorreo;
import com.lowagie.text.pdf.PdfWriter;
import static com.sun.faces.facelets.util.Path.context;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

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
public class CargaNotasProfesorAdminController extends AbstractController {
    private DaoConexion daoConexion;
    
    public CargaNotasProfesorAdminController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "academico/registroControl/seleccionProfesorAsignaturaSeccion";
        HttpSession misession= request.getSession(); 
                
        //boolean validado = false;
        //Para obtener parámetros de las propiedades del correo
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamCorreo.class);
        ParamCorreo pv = ctx.getBean(ParamCorreo.class);
        
        String rutaPDFAdjunto = pv.getCorreo_rutaArcAdj()+System.getProperty("file.separator")+"actas_notas";
        
        //Para obtener los parámetros de configuración del servicio de correo
        String confFile = "classpath:com/iupg/sicoes/propiedades/mailProperties.xml";                                
        ConfigurableApplicationContext context
                  = new ClassPathXmlApplicationContext(confFile);
        
        EmailService emailService = (EmailService) context.getBean("emailService");

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos complementarios del Profesor
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
                        
                        switch (accion) {
                            case "Buscar":
                                    //mensaje = daoGeneral.ValidarExistenciaHorarioProfesores(daoConexion.ConexionBD());
                                    //misession.setAttribute("botonSession","");
                                    if (mensaje.isEmpty()) {
                                        //mensaje = daoGeneral.EliminarSeccionesFiltradas(daoConexion.ConexionBD());
                                    }                        
                                    break;
                    
                                case "Guardar":
                                    
                                
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
                                
                                // Exportar Acta de Notas
                                String actasNotas=ParamConfig.getString("reporte.archivoReporteActasNotasCargada");                                    
                                String planillaInscAdj = rutaPDFAdjunto + System.getProperty("file.separator")+"Acta_Notas_" + daoProfesor.getCedula()+"_"+daoProfesor.getPeriodo()+"_"+daoProfesor.getAsignatura()+"_"+daoProfesor.getSeccion()+".pdf";
                                File theFile = new File(actasNotas);
                                JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                                    
                                Map<String, Object> parametros = new HashMap();
                                parametros.put("CEDULA", new Integer(daoProfesor.getCedula()));
                                parametros.put("PERIODO", new String(daoProfesor.getPeriodo()));
                                parametros.put("SECCION", new String(daoProfesor.getSeccion()));
                                parametros.put("LOGO", new String(ParamConfig.getString("reporte.archivoImagenLogo")));
                                parametros.put("MATERIA", new String(daoProfesor.getAsignatura()));
                                    
                                JasperPrint print = JasperFillManager.fillReport(reporte, parametros, daoConexion.ConexionBD());

                                JRPdfExporter exporter = new JRPdfExporter(); 
                                exporter.setExporterInput(new SimpleExporterInput(print)); 
                                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(planillaInscAdj)); 
                                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration(); 
                                configuration.setPermissions(PdfWriter.AllowCopy | PdfWriter.AllowPrinting); 
                                exporter.setConfiguration(configuration); 
                                exporter.exportReport(); 
                                // Fin Exportar
                                    
                                // Enviar por Correo        
  
                                // Definición de variables del correo.
                                String toAddr = (String) misession.getAttribute("loginSession");
                                String fromAddr = pv.getCorreo_remitente();
                                String subject = "Actas de Notas Período: "+daoProfesor.getPeriodo() + " Asignatura: "+daoProfesor.getAsignatura()+" Sección "+daoProfesor.getSeccion();
            
                                String body = "<html><img src='cid:"+pv.getCorreo_imgSrc()+"'>" +
                                        "<body>" +
                                        "<br>" + 
                                        "<br>" +
                                        "<p>";
                                
                                if ("M".equals(daoProfesor.getSexo())){
                                    body = body + "Estimado, Profesor ";
                                } else {
                                    body = body + "Estimada, Profesora ";
                                }
                            
                                body = body + daoProfesor.getNombres() + ", " + daoProfesor.getApellidos() +
                                    ":</p>" +
                                    "<p> Usted ha realizado la carga de notas correspondiente al período académico <u>"+daoProfesor.getPeriodo()+"</u>, asignatura <u>"+daoProfesor.getAsignatura()+"</u>, sección <u>"+daoProfesor.getSeccion()+"</u>. </p>" +
                                    "<p> El acta deberá ser enviada al Departamento de Registro y Control para su validación.</p>" +
                                    "<p><u>Requisito:</u> Acta de Notas impresa. </p>" +
                                    "</body>" +
                                    "</html>";
           
                                //Envia
                                emailService.sendEmail(toAddr, fromAddr, subject, body, pv.getCorreo_imgCabecera(),pv.getCorreo_imgSrc(),planillaInscAdj);
                                    
                                //Enviar mensaje de finalización del proceso.
                                misession.setAttribute("incripcionAlumnoSession", 
                                        "<tr><td><img src=\"/sicoes/recursos/img/info_1.gif\"/></td><td colspan=\"4\" align=\"center\"> El retiro de Asignatura(s) fue procesada satisfactoriamente.  <br> La planilla de inscripción será enviada a su correo electrónico, el cual <br> deberá imprimir y validarla ante el Departamento de Registro y Control.</td></tr>");
                                //validado = true;
                                //Fin Enviar por Correo
                                
                                // Fin Aqui
                                
                                
                                
                                
                                
                                
                            }                            
                 
                            // Armar el cuerpo de la tabla para refrescar lo guardado
                            String nuevoCuerpo = daoProfesor.BuscarDetalleEvaluacion(daoConexion.ConexionBD(),"");
                            misession.setAttribute("detalleEvaluacionSession", nuevoCuerpo);   
                            
                            pagina = "academico/registroControl/cargaNotasProfesorAdmin";                             
                            
                                
                          break;  
                        
                    case "Salir":
                            mensaje = "";
                            pagina="academico/registroControl/seleccionProfesorAsignaturaSeccion";                        
                            
                            break;
                        
                        
                    } 
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                 
                    }                    
                } else {                    
                    // else del POST                        
                    
                    //pagina ="principal/incripcion";
                    // Construir la tabla para la carga de notas
                    String encabezado = daoProfesor.BuscarCabeceraEvaluacion(daoConexion.ConexionBD());
                    misession.setAttribute("encabezadoEvaluacionSession", encabezado);                    
                    
                    String cuerpo = daoProfesor.BuscarDetalleEvaluacion(daoConexion.ConexionBD(),"");
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