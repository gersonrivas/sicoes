/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoAlumno;
import com.iupg.sicoes.modelo.DaoConexion;
//import com.iupg.sicoes.modelo.Validador;
import com.iupg.sicoes.servicio.EmailService;
import com.iupg.sicoes.servicio.ParamCorreo;
import com.iupg.sicoes.propiedades.ParamConfig;

import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
//import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
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
 * Clase controlador del proceso de retiro y adición de Asignaturas.
 * @author gerson
 * @version 1.0
 */
public class RetiroInscripcionController extends AbstractController {
    private DaoConexion daoConexion;
    
    public RetiroInscripcionController() {
    }
    
    @Override
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "principal/retiroInscripcion";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        DaoAlumno daoAlumno = new DaoAlumno((String) misession.getAttribute("cedulaUsuSession"));
        String[] materias = request.getParameterValues("materia");
        String materiasSeleccionadas = "";
        String horariosMaterias = "";
        boolean validado = false;
        
        //Para obtener parámetros de las propiedades del correo
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamCorreo.class);
        ParamCorreo pv = ctx.getBean(ParamCorreo.class);
        
        //Para obtener los parámetros de configuración del servicio de correo
        String confFile = "classpath:com/iupg/sicoes/propiedades/mailProperties.xml";                                
        ConfigurableApplicationContext context
                  = new ClassPathXmlApplicationContext(confFile);

        EmailService emailService = (EmailService) context.getBean("emailService");
        
        String rutaPDFAdjunto = pv.getCorreo_rutaArcAdj()+System.getProperty("file.separator")+"planillas";

        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            //Buscar los datos complementarios del Alumno
            daoConexion = new DaoConexion();                                    
            if (daoAlumno.BuscarDatosAlumno(daoConexion.ConexionBD())) {
                misession.setAttribute("gerenciaSession", daoAlumno.getEspecialidad());
                misession.setAttribute("turnoSession", daoAlumno.getTurno());
                
                //Variable Para validar el Turno
                //String codigoValidacionTurno = daoAlumno.BuscarCodigoValidacionTurno();
                
                //Buscar el período inscrito Academicamente            
                if ("POST".equals(request.getMethod())) {
                    //Si preciona el botón guardar                    
                    String action = request.getParameter("action").toString();
                     if (request.getParameter("action").equals("Guardar")) {
                        //Buscar el período inscrito Academicamente                        
                        if (daoAlumno.BuscarPeriodoInscriptoAcademico(daoConexion.ConexionBD(),"'C'")) {
                            //pagina ="principal/incripcion";
                            misession.setAttribute("periodoSession", daoAlumno.getPeriodo());
                            //Buscar materias a inscribir
                            //misession.setAttribute("incripcionAlumnoSession", daoAlumno.BuscarMateriasPorInscribir(daoConexion.ConexionBD()));
                        } else {
                            misession.setAttribute("incripcionAlumnoSession", 
                                "<tr><td><img src=\"/sicoes/recursos/img/error_1.gif\"/></td><td colspan=\"4\" align=\"center\"> Debe estar inscrito(a) académicamente, <br> posiblemente está realizando esta operación fuera de la fecha establecida o <br> ya realizó el retiro de asignatura(s) correspondiente.</td></tr>");
                        }
                        // Barrer las materias seleccionadas por el usuario
                        
                        try {                            
                            if(materias!=null && materias.length>0) {                                
                                // For para encadenar todos los valores del select. (Materias y Secciones)
                                for(int i=0; i<materias.length; i++) {
                                    
                                    // Para encadenar las asignaturas y secciones.
                                    if (i==0) {
                                        materiasSeleccionadas = "'"+materias[i]+"'";                                        
                                    } else {
                                        materiasSeleccionadas = materiasSeleccionadas + ", '"+materias[i]+"'";                                        
                                    }                                                                         
                                
                                }
                            }
                               
                            // Actualizar materias Retiradas
                            daoAlumno.GuardarRetiroMateriasAlumno(daoConexion.ConexionBD(), materiasSeleccionadas);                                 
                                
                            // Limpiar variable de session donde muestra la cantidad de UC permitidas
                            misession.setAttribute("UCPermitidasSession", "");
                            // Exportar Planilla
                            String plantilla=ParamConfig.getString("reporte.archivoPlanillaInscripcion");                                    
                            String planillaInscAdj = rutaPDFAdjunto + System.getProperty("file.separator")+"Insc_" + daoAlumno.getCedula()+"_"+daoAlumno.getPeriodo()+".pdf";
                            File theFile = new File(plantilla);
                            JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                                    
                            Map<String, Object> parametros = new HashMap();
                            parametros.put("CEDULA", new Integer(daoAlumno.getCedula()));
                            parametros.put("PERIODO", new String(daoAlumno.getPeriodo()));
                            parametros.put("LOGO", new String(ParamConfig.getString("reporte.archivoImagenLogo")));
                                    
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
                            String subject = "Retiro de Asignatura(s) "+daoAlumno.getPeriodo();
            
                            String body = "<html><img src='cid:"+pv.getCorreo_imgSrc()+"'>" +
                                        "<body>" +
                                        "<br>" + 
                                        "<br>" +
                                        "<p>";
                                
                            if ("M".equals(daoAlumno.getSexo())){
                                body = body + "Estimado, ";
                            } else {
                                body = body + "Estimada, ";
                            }
                            
                            body = body + daoAlumno.getNombres() + ", " + daoAlumno.getApellidos() +
                                    ":</p>" +
                                    "<p>Su solicitud de retiro de asignaturas  correspondiente al período académico <u>"+daoAlumno.getPeriodo()+"</u>, ha sido procesada satisfactoriamente. </p>" +
                                    "<p>Esta solicitud tiene la validez necesaria ante la institución, en caso que desee validarla formalmente, debe dirigirse al Departamento de Registro y Control.</p>" +
                                    "<p><u>Requisito:</u> Planilla de Inscripción impresa. </p>" +
                                    "</body>" +
                                    "</html>";
           
                            //Envia
                            emailService.sendEmail(toAddr, fromAddr, subject, body, pv.getCorreo_imgCabecera(),pv.getCorreo_imgSrc(),planillaInscAdj);
                                    
                            //Enviar mensaje de finalización del proceso.
                            misession.setAttribute("incripcionAlumnoSession", 
                                        "<tr><td><img src=\"/sicoes/recursos/img/info_1.gif\"/></td><td colspan=\"4\" align=\"center\"> El retiro de Asignatura(s) fue procesada satisfactoriamente.  <br> La planilla de inscripción será enviada a su correo electrónico, el cual <br> deberá imprimir y validarla ante el Departamento de Registro y Control.</td></tr>");
                            validado = true;
                            //Fin Enviar por Correo                                         
                        
                        } catch (Exception ex) {
                            pagina="principal/retiroInscripcion";                   
                            mensaje = "<tr><td align=\"right\">" +
                              "<img src=\"/sicoes/recursos/img/error_1.gif\"/>" +
                              "</td>" +
                              "<td>Error en el envío de correo.</td></tr>";
                            validado = true;            
                            System.out.println(ex.getMessage());
                        } 
                        
                    } else { // Si presiona Botón Salir
                        mensaje = "";
                        pagina="principal/principal";
                    }
                } else {                    
                    // else del POST
                    // Para validar que solo haya una inscripción Académica previamente
                    if (daoAlumno.BuscarPeriodoInscriptoAcademico(daoConexion.ConexionBD(),"'C'")) {
                        //pagina ="principal/incripcion";
                        misession.setAttribute("periodoSession", daoAlumno.getPeriodo());

                        //Buscar materias Inscritas                        
                        misession.setAttribute("incripcionAlumnoSession", daoAlumno.BuscarMateriasInscritas(daoConexion.ConexionBD()));
                        
                        //Buscar UC permitidas
                        //Validar Unidades de Crédito sean las estipuladas en las reglas

                        misession.setAttribute("UCPermitidasSession", "Seleccione la(s) Asignatura(s) que desee retirar");
                    } else {
                        misession.setAttribute("incripcionAlumnoSession", 
                            "<tr><td><img src=\"/sicoes/recursos/img/error_1.gif\"/></td><td colspan=\"4\" align=\"center\"> Debe estar inscrito(a) académicamente, favor diríjase al Departamento de Registro y Control,  <br> posiblemente está realizando esta operación fuera de la fecha establecida o <br> ya realizó el retiro de asignatura(s) correspondiente.</td></tr>");
                        validado = true;
                    }
                }
            } else {
                //pagina ="principal/incripcion";                           
                mensaje = "Error Buscando datos Asociados al Usuario.";
            } 
        
        }
        
        ((AnnotationConfigApplicationContext)ctx).close();
        
        misession.setAttribute("validadoSession", validado);
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;        
        
    }
    
}
