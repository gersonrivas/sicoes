/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoAlumno;
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
public class ReportarPagosController extends AbstractController {
    private DaoConexion daoConexion;
    
    public ReportarPagosController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        String mensaje = "";
        String pagina = "administrativo/reportarPagosAdmon";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion(); 
        
        String opcionesPeriodos = "";
        String botones = "";
        String tablaDetalle = "";
        
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else {
             //Buscar los datos complementarios del Alumno
            daoConexion = new DaoConexion();   
            DaoAlumno daoAlumno = new DaoAlumno((String) misession.getAttribute("cedulaUsuSession"));            
            
            // Iniciar Variables de Session
            misession.setAttribute("notificacionPagosSession", "");
            misession.setAttribute("botonSession", "");

            
            if (daoAlumno.BuscarDatosAlumno(daoConexion.ConexionBD())) {
                misession.setAttribute("gerenciaSession", daoAlumno.getEspecialidad());
                misession.setAttribute("turnoSession", daoAlumno.getTurno());
                    
                

                if ("POST".equals(request.getMethod())) {
                
                    if (request.getParameter("periodo") != null && !request.getParameter("periodo").equalsIgnoreCase("")  &&  !request.getParameter("periodo").equalsIgnoreCase("lbElige") && !request.getParameter("periodo").equalsIgnoreCase("lbSinPeriodo")) {                    
                    
                        daoAlumno.setPeriodo(request.getParameter("periodo"));

                        String listaDePagos = daoAlumno.ListarPagosPeriodo(daoConexion.ConexionBD());
                        if (listaDePagos.isEmpty()) {
                            
                            botones = "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />  ";
                        } else {
                            
                            botones = "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />  <input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" /> ";
                        }
                        misession.setAttribute("notificacionPagosSession", listaDePagos);
                        
                        
                    }

                
                    //Si preciona el botón adicionar, eliminar o salir                    
                    String accion = request.getParameter("action");
                
                    if (accion != null) {
                        //Validar los botones pulsados
                        switch (accion) {
                            case "Eliminar": 
                                //mensaje = daoGeneral.EliminarPeriodo(daoConexion.ConexionBD());
                                //if (mensaje.isEmpty()) {
                                //    mensaje = daoGeneral.EliminarSeccionesFiltradas(daoConexion.ConexionBD());
                                //}                        
                                //daoGeneral.setPeriodo("");
                                //opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                                //misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                                //tablaDetalleAcademico = "";
                                //misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                                //Mostrar los datos Administrativos detalle en la tabla
                                //tablaDetalleAdministrativo = "";
                                //misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);

                                //misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");                        
                                break;
                        
                            case "Adicionar":    
                                pagina = "administrativo/actualizarPago";
                                
                                Calendar calendar = Calendar.getInstance();
                                Date date = calendar.getTime();           
                                calendar.setTime(date);
                                String year = String.format("%04d",calendar.get(Calendar.YEAR));
                                String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                                String day = String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));
                
                                String today = year + "-" + month + "-" + day;            
                            
                                tablaDetalle =  "<tr><td align=\"right\">Fecha:</td> <td align=\"left\">  <input type=\"date\" id=\"fecha\" name=\"fecha\" value=\"" + today +  "\" align=\"center\"/> </td> </tr>";

                                tablaDetalle =  tablaDetalle + "<tr><td align=\"right\">Comprobate/Referencia</td> <td align=\"left\">  <input type=\"number\" id=\"comprobante\" name=\"comprobante\" value=\"\" align=\"center\"/> </td> </tr>";
                                tablaDetalle =  tablaDetalle + "<tr><td align=\"right\">Monto:</td> <td align=\"left\"> <input type=\"number\" id=\"monto\" name=\"monto\" value=\"0.00\" align=\"center\" placeholder=\"1.0\" step=\"0.01\" min=\"0\" max=\"99999999999\"/></td> </tr>";
            
                                String optionsTipoPago =  "<select name=\"tipoPago\"  onchange=\"form.submit()\" id=\"tipoPago\" >" +                    
                                                "<option value=\"MAT\" >Matrícula Académica</option>" +
                                                "<option value=\"DOC\" >Solicitud de Documento</option>" +
                                                "<option value=\"OTR\" >Pagos Varios</option>" +                                                
                                                "</select>";
            
                                tablaDetalle =  tablaDetalle + "<tr><td align=\"right\">Concepto de Pago:</td> <td align=\"left\">" + optionsTipoPago + "</td> </tr>";
                
                                
                                botones = "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />  ";
                                //botones="";
 
                                
                                //daoGeneral.setPeriodo("");                            
                                //opcionesPeriodos = "<input type=\"text\" id=\"periodo\" name=\"periodo\" maxlength=\"10\" size=\"10\">";
                                //misession.setAttribute("periodosSession", opcionesPeriodos);                        
                        
                                //Calendar calendar = Calendar.getInstance();
                                //Date date = calendar.getTime();           
                                //calendar.setTime(date);
                                //String year = String.format("%04d",calendar.get(Calendar.YEAR));
                                //String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                                //String day = String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));
                
                                //String today = year + "-" + month + "-" + day;            
                            
                                //tablaDetalleAcademico =  "<tr><td align=\"right\">Fecha Inicio:</td> <td align=\"left\">  <input type=\"date\" id=\"fec_ini\" name=\"fec_ini\" value=\"" + today +  "\" align=\"center\"/> </td> </tr>";
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin\" name=\"fec_fin\" value=\"" + today +   "\" align=\"center\"/></td> </tr>";
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Descripción:</td> <td align=\"left\"> <input type=\"text\" id=\"descrip\" name=\"descrip\" value=\"\" align=\"center\"/> </td> </tr>";
            
                                //String optionsEstatusPeriodo =  "<select name=\"estatus\"  onchange=\"form.submit()\" id=\"estatus\" >" +                    
                                //                        "<option value=\"lbA\" selected >Abierto</option>" +
                                //                        "<option value=\"lbC\" >Cerrado</option>" +
                                //                        "<option value=\"lbP\" >Próximo</option>" +
                                //                        "</select>";
                        
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Estatus:</td> <td align=\"left\"> " + optionsEstatusPeriodo + " </td> </tr>";
            
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_insc\" name=\"fec_ini_insc\" value=\"" + today + "\" align=\"center\"/></td> </tr>";
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_insc\" name=\"fec_fin_insc\" value=\"" + today +  "\" align=\"center\"/></td> </tr>";
            
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_raa\" name=\"fec_ini_raa\" value=\""  + today +  "\" align=\"center\"/></td> </tr>";
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_raa\" name=\"fec_fin_raa\" value=\""  + today +  "\" align=\"center\"/></td> </tr>";
                            
                                //tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Horario del Período:</td> <td align=\"left\"> "+daoGeneral.BuscarDatosHorariosPeriodo(daoConexion.ConexionBD())+"</td> </tr>";
                            
                                //misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);             
                        
                                //tablaDetalleAdministrativo =  "<tr><td align=\"right\">Costo Período:</td> <td align=\"left\"> <input type=\"number\" id=\"monto_sem\" name=\"monto_sem\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"0\" align=\"center\"/></td> </tr>";
                                //tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Cantidad de Cuotas:</td> <td align=\"left\"> <input type=\"number\" id=\"nro_cuotas\" name=\"nro_cuotas\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"99\" step=\"1\" value=\"0\" align=\"center\"/></td> </tr>";
            
                                //String optionsDivisasPeriodo =  "<select name=\"periodo_divisas\"  onchange=\"form.submit()\" id=\"periodo_divisas\" name=\"periodo_divisas\">" +
                                //                        "<option value=\"lbN\" selected>No</option>" +
                                //                        "<option value=\"lbS\" >Si</option>" +
                                 //                       "</select>";
          
                                //tablaDetalleAdministrativo = tablaDetalleAdministrativo + "<tr><td  align=\"right\">Período en Divisas:</td> <td align=\"left\">"+ optionsDivisasPeriodo + "</td> </tr>";
                                //tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Valor de la Divisa:</td> <td align=\"left\"> <input type=\"number\" id=\"valor_divisa\" name=\"valor_divisa\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"0\" align=\"center\"/></td> </tr>";
  
                                //misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);
                            
                                //misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />   <input name=\"action\" type=\"submit\" id=\"cancelar\" value=\"Cancelar\" />   ");
                        
                                //mensaje = "";
                                break;
                            
                            case "Editar": 
                                //mensaje = daoGeneral.AdicionarSeccionFiltrada(daoConexion.ConexionBD());
                                break;    
                            
                            case "Guardar":                            
                                //daoGeneral.setPeriodo(request.getParameter("periodo"));
                                //daoGeneral.setFec_ini(request.getParameter("fec_ini"));
                                //daoGeneral.setFec_fin(request.getParameter("fec_fin"));
                                //daoGeneral.setDescrip(request.getParameter("descrip"));
                                //daoGeneral.setEstatus(request.getParameter("estatus").substring(2, 3));
                                //daoGeneral.setFec_ini_insc(request.getParameter("fec_ini_insc"));
                                //daoGeneral.setFec_fin_insc(request.getParameter("fec_fin_insc"));
                                //daoGeneral.setFec_ini_raa(request.getParameter("fec_ini_raa"));
                                //daoGeneral.setFec_fin_raa(request.getParameter("fec_fin_raa"));
                                //daoGeneral.setId_grupo_horario(Integer.parseInt(request.getParameter("id_grupo_horario")));
                            
                                //daoGeneral.setMonto_sem(Double.parseDouble(request.getParameter("monto_sem")));
                                //daoGeneral.setNro_cuotas(Integer.parseInt(request.getParameter("nro_cuotas")));
                                //daoGeneral.setValor_divisa(Double.parseDouble(request.getParameter("valor_divisa")));
                                //daoGeneral.setPeriodo_divisas(request.getParameter("periodo_divisas").substring(2, 3));

                                //if (daoGeneral.PeriodoExiste(daoConexion.ConexionBD())) {
                                //    mensaje = daoGeneral.ActualizarPeriodo(daoConexion.ConexionBD());
                                //} else {
                                //    mensaje = daoGeneral.AdicionarPeriodo(daoConexion.ConexionBD());
                                //}
                            
                                //daoGeneral.setPeriodo("");
                                //opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                                //misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                                //tablaDetalleAcademico = "";
                                //misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                                //Mostrar los datos Administrativos detalle en la tabla
                                //tablaDetalleAdministrativo = "";
                                //misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);
                                //misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");
                                break;
                            
                            case "Cancelar":                             
                                daoAlumno.setPeriodo("");
                                opcionesPeriodos = daoAlumno.BuscarPeriodoActualDelAlumno(daoConexion.ConexionBD());
                                misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                                //tablaDetalleAcademico = "";
                                //misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                                //Mostrar los datos Administrativos detalle en la tabla
                                //tablaDetalleAdministrativo = "";
                                //misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);

                                misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");
                            
                                mensaje = "";
                                break;    

                            case "Salir":                       
                                daoAlumno.setPeriodo("");
                                opcionesPeriodos = daoAlumno.BuscarPeriodoActualDelAlumno(daoConexion.ConexionBD());
                                misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                                //tablaDetalleAcademico = "";
                                //misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                                //Mostrar los datos Administrativos detalle en la tabla
                                //tablaDetalleAdministrativo = "";
                                //misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);
                            
                                mensaje = "";
                            
                                pagina="principal/principal";
                                break;                    
                            }   
                        }
                    
                    
                    
                }   
                misession.setAttribute("botonSession", botones);
                misession.setAttribute("periodosSession", daoAlumno.BuscarPeriodoActualDelAlumno(daoConexion.ConexionBD()));
                misession.setAttribute("periodoSession", daoAlumno.getPeriodo());
                
                misession.setAttribute("detalleActualiacionPagoSession", tablaDetalle);

            }
                
                    
        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;   
    }
    
}
