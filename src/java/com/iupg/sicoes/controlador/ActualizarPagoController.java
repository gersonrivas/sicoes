/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author gerson
 */
public class ActualizarPagoController extends AbstractController {
    private DaoConexion daoConexion;
    
    public ActualizarPagoController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "administrativo/actualizarPago";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        
        String botones="";
        String tablaDetalle = "";
                
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
            String today;
            if (request.getParameter("fecha") == null || request.getParameter("fecha").equalsIgnoreCase("")  ) {
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();           
                calendar.setTime(date);
                String year = String.format("%04d",calendar.get(Calendar.YEAR));
                String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                String day = String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));
                
                today = year + "-" + month + "-" + day;                
            } else {
                today = request.getParameter("fecha");
            }
                
                            
            tablaDetalle =  "<tr><td align=\"right\">Fecha:</td> <td align=\"left\">  <input type=\"date\" id=\"fecha\" name=\"fecha\" value=\"" + today +  "\" align=\"center\"/> </td> </tr>";

            tablaDetalle =  tablaDetalle + "<tr><td align=\"right\">Comprobate/Referencia</td> <td align=\"left\">  <input type=\"number\" id=\"comprobante\" name=\"comprobante\" value=\""+request.getParameter("comprobante")+"\" align=\"center\"/> </td> </tr>";
            tablaDetalle =  tablaDetalle + "<tr><td align=\"right\">Monto:</td> <td align=\"left\"> <input type=\"number\" id=\"monto\" name=\"monto\" value=\""+request.getParameter("monto")+"\" align=\"center\" placeholder=\"1.0\" step=\"0.01\" min=\"0\" max=\"99999999999\"/></td> </tr>";
            
            String optionsTipoPago =  "<select name=\"tipoPago\"  onchange=\"form.submit()\" id=\"tipoPago\" >" +                    
                                                "<option value=\"MAT\" >Matrícula Académica</option>" +
                                                "<option value=\"DOC\" >Solicitud de Documento</option>" +
                                                "<option value=\"OTR\" >Pagos Varios</option>" +                                                
                                                "</select>";
            
            optionsTipoPago = optionsTipoPago.replace(request.getParameter("tipoPago")+"\"", request.getParameter("tipoPago") + "\" selected");
            
            System.out.println(optionsTipoPago);
            
            tablaDetalle =  tablaDetalle + "<tr><td align=\"right\">Concepto de Pago:</td> <td align=\"left\">" + optionsTipoPago + "</td> </tr>";
            botones = "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />  ";
                    
            if ("POST".equals(request.getMethod())) {
                String accion = request.getParameter("action");
                    
                if (accion != null) {
                    //Validar los botones pulsados
                    switch (accion) {
                        case "Guardar":
                            botones = "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />  ";
                            mensaje = "Información Actualiada Satisfactoriamente.";
                            break;
                        case "Retornar": {
                            mensaje = "";
                            botones = "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />  <input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" /> ";
                            pagina = "administrativo/reportarPagosAdmon";
                            break;
                        }
                    }
                }
                            
                            
                            
            } else {                    
                // else del POST               
                //botones = botones + "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" /> &nbsp;";
            }
            //botones = botones + "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" /> &nbsp;";
            misession.setAttribute("detalleActualiacionPagoSession", tablaDetalle);
            misession.setAttribute("botonSession", botones);
        }
                
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;  
    }
    
}
