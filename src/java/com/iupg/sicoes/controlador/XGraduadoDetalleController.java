/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGeneral;
import com.iupg.sicoes.modelo.DaoGraduados;
import com.iupg.sicoes.propiedades.ParamConfig;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class XGraduadoDetalleController extends AbstractController {
    private DaoConexion daoConexion;
    
    public XGraduadoDetalleController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "academico/registroControl/xgraduadoDetalle";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();     
        
        String cedulaGraduado="0";
        DaoGraduados daoGraduados = new DaoGraduados((String) request.getParameter(cedulaGraduado));
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            
            //Buscar los datos complementarios del Graduado
            daoConexion = new DaoConexion();  
            String botones="";
            String tablaDetalle = "";
                        
            // Para guardar la variable de sesión recibida del JSP            
            if (request.getParameter("cedulaGraduadoSession")==null) {                
                daoGraduados.setCedula(misession.getAttribute("cedulaGraduadoSession").toString());

            } else {
                daoGraduados.setCedula(request.getParameter("cedulaGraduadoSession"));
            }
            misession.setAttribute("cedulaGraduadoSession", daoGraduados.getCedula());
                                    
            //Buscar los datos detalle del Graduado
            tablaDetalle = daoGraduados.BuscarDatosGraduado(daoConexion.ConexionBD(),"readonly");
            
            if ("POST".equals(request.getMethod())) {

                //Si preciona el botón adicionar, eliminar o salir                    
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Editar":
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />";
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"cancelar\" value=\"Cancelar\" />";                            
                            tablaDetalle = daoGraduados.BuscarDatosGraduado(daoConexion.ConexionBD(),"");
                            misession.setAttribute("detalleGraduadoSession", tablaDetalle);
                            break;
                        
                        case "Guardar":
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"editar\" value=\"Editar\" />";
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"actaGrado\" value=\"Acta de Grado\" />";
                            
                            String[] libro = request.getParameterValues("flibro");
                            String[] tomo = request.getParameterValues("ftomo");
                            String[] folio = request.getParameterValues("ffolio");
                            String[] numero = request.getParameterValues("fnumero");
                            String[] resolucion = request.getParameterValues("fresolucion");
                            String[] fecActa = request.getParameterValues("ffec_acta");
                            String[] fecFirma = request.getParameterValues("ffec_firma");
                            String[] regGrado = request.getParameterValues("freg_grado");
                            String[] aranceles = request.getParameterValues("faranceles");
                            
                            String regGradoSeleccionado = "false";
                            if (regGrado!=null) {
                                regGradoSeleccionado = "true";
                            } 
                            
                            String sql = "UPDATE graduado SET libro = '" + libro[0] + "', " +
                                         "tomo = '" + tomo[0] + "', " +
                                         "folio = '" + folio[0] + "', " +
                                         "numero = '" + numero[0] + "', " +
                                         "resolucion = '" + resolucion[0] + "', " +                            
                                         "fec_acta = '" + fecActa[0] + "', " +
                                         "fec_firma = '" + fecFirma[0] + "', " +
                                         "reg_grado = " + regGradoSeleccionado + ", " +
                                         "aranceles = '" + aranceles[0] + "' " +
                                         "WHERE cedula = '" + daoGraduados.getCedula() + "'; ";
                                    
                            mensaje = daoGraduados.GuardarGraduado(daoConexion.ConexionBD(), sql);
                            tablaDetalle = daoGraduados.BuscarDatosGraduado(daoConexion.ConexionBD(),"readonly");
                            break;
                            
                        case "Acta de Grado":
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"editar\" value=\"Editar\" />";
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"actaGrado\" value=\"Acta de Grado\" />";                            
                            
                            this.imprimirActa(response, daoGraduados.getCedula());
                            mensaje = "Imprimiendo.....";
                            break;

                        case "Cancelar":
                            //Para adicionar horarios unificados en todas las especilidades
                            //mensaje = daoGeneral.AdicionarHorarioProfesor(daoConexion.ConexionBD());                            
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"editar\" value=\"Editar\" />";
                            botones = botones + "<input name=\"action\" type=\"submit\" id=\"actaGrado\" value=\"Acta de Grado\" />";
                            mensaje = "Cancelado.....";
                            break;
                        case "Retornar": {
                            mensaje = "";
                            pagina = "academico/registroControl/graduado";
                            break;
                        }
                    }
                    //Refrescar datos
                    //tablaDetalle = daoProfesor.BuscarHorariosProfesor(daoConexion.ConexionBD());
                    //misession.setAttribute("detalleHorariosProfesorSession", tablaDetalle);
                }     
              
            } else {                    
                // else del POST               
                botones = botones + "<input name=\"action\" type=\"submit\" id=\"editar\" value=\"Editar\" />";
                botones = botones + "<input name=\"action\" type=\"submit\" id=\"actaGrado\" value=\"Acta de Grado\" />";
            }
            //Para construir la tabla
            misession.setAttribute("detalleGraduadoSession", tablaDetalle);
            //Para armar la botonera
            misession.setAttribute("botonSession", botones);   
        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;           
    }
    
    public void imprimirActa( HttpServletResponse response, String cedulaGraduado) throws JRException, IOException, SQLException {

        try {

        System.out.println("Entrando A ImprimirActa.....>......");
            
        ServletOutputStream out = response.getOutputStream();
        String plantilla=ParamConfig.getString("reporte.archivoReporteActasGrado");  
        File theFile = new File(plantilla);
        JasperReport reporte = (JasperReport) JRLoader.loadObject(theFile);
                        

        DaoGraduados daoGraduados = new DaoGraduados(cedulaGraduado);
        DaoGeneral daoGeneral = new DaoGeneral();
        
        if (daoGraduados.BuscarDatosGraduado(daoConexion.ConexionBD())) {
            
            //NumberFormat formatter = NumberFormat.getInstance(new Locale("en_VE"));
            String textoInicial = daoGeneral.reporte(daoConexion.ConexionBD(), "CE01", "texto1");            
            textoInicial = textoInicial.replace("<valor1>", daoGraduados.getCedula());
            textoInicial = textoInicial.replace("<valor2>", daoGraduados.getApellidos() + " " + daoGraduados.getNombres());
            textoInicial = textoInicial.replace("<valor3>", daoGraduados.getCedula());
            textoInicial = textoInicial.replace("<valor4>", daoGraduados.getEspecialidad());
            
            Date fecha = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);  
            
            String textoFinal = daoGeneral.reporte(daoConexion.ConexionBD(), "CE01", "texto2");            
            textoFinal = textoFinal.replace("<valor1>", "103");
            //textoFinal = textoFinal.replace("<valor2>", "20,00");
            textoFinal = textoFinal.replace("<valor3>", daoGeneral.numerosLetras(daoConexion.ConexionBD(), cal.get(Calendar.DAY_OF_MONTH)));
            textoFinal = textoFinal.replace("<valor4>", daoGeneral.mesLetras(daoConexion.ConexionBD(), cal.get(Calendar.MONTH)));
            textoFinal = textoFinal.replace("<valor5>", daoGeneral.numerosLetras(daoConexion.ConexionBD(), cal.get(Calendar.YEAR)));
            
            Map<String, Object> parametros = new HashMap();
            parametros.put("TEXTO1", new String(textoInicial));
            parametros.put("CEDULA", new String(daoGraduados.getCedula()));
            parametros.put("ESPECIALIDAD", new String(daoGraduados.getCodEspecialidad()));
            parametros.put("TEXTO2", new String(textoFinal));
            parametros.put("LOGO", new String(ParamConfig.getString("reporte.archivoImagenLogo")));
            parametros.put("FIRMA_1", new String(daoGeneral.reporte(daoConexion.ConexionBD(), "CE01", "firma_1")));
            parametros.put("CARGO_1", new String(daoGeneral.reporte(daoConexion.ConexionBD(), "CE01", "cargo_1")));
                            
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, daoConexion.ConexionBD());
                        
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();                            
            // Fin Exportar
            
            
            
        } 
        
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
            
                                    
    }
    
    
}
