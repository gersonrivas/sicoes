/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.controlador;

import com.iupg.sicoes.modelo.DaoConexion;
import com.iupg.sicoes.modelo.DaoGeneral;
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
public class ConfigurarPeriodoController extends AbstractController {
    private DaoConexion daoConexion;
    DaoGeneral daoGeneral = new DaoGeneral();
    
    public ConfigurarPeriodoController() {
    }
    
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //throw new UnsupportedOperationException("Not yet implemented");
        
        String mensaje = "";
        String pagina = "sistema/configurarPeriodo";
        HttpSession misession= request.getSession(); 
        daoConexion = new DaoConexion();
        
        //DaoGeneral daoGeneral = new DaoGeneral();      
        String opcionesPeriodos = "";
        String tablaDetalleAcademico = "";
        String tablaDetalleAdministrativo = "";        
            
        if (misession.getAttribute("usuarioSession")==null) {
            mensaje = "expiró la sessión";
            pagina ="fin/expiracion";
        } else { 
            
            mensaje = "";
            
            if ("POST".equals(request.getMethod())) {
                                
                if (request.getParameter("periodo") != null && !request.getParameter("periodo").equalsIgnoreCase("")  &&  !request.getParameter("periodo").equalsIgnoreCase("lbElige") && !request.getParameter("periodo").equalsIgnoreCase("lbSinPeriodo")) {
                        
                    if (daoGeneral.getPeriodo()==null) {
                                                       
                        daoGeneral.setPeriodo(request.getParameter("periodo"));
                                    
                        opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosSession", opcionesPeriodos);
                        misession.setAttribute("periodoSeleccionadoSession", request.getParameter("periodo"));

                        //Mostrar los datos Académicos detalle en la tabla
                        tablaDetalleAcademico = daoGeneral.BuscarDatosPeriodoAcademico(daoConexion.ConexionBD());
                        misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                        //Mostrar los datos Administrativos detalle en la tabla
                        tablaDetalleAdministrativo = daoGeneral.BuscarDatosPeriodoAdministrativo(daoConexion.ConexionBD());
                        misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);                                
                        
                       misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />   <input name=\"action\" type=\"submit\" id=\"cancelar\" value=\"Cancelar\" />  <input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" /> "); 
                                    
                    } else {
                        if (daoGeneral.getPeriodo().equals(request.getParameter("periodo"))) {

                        tablaDetalleAcademico =  "<tr><td align=\"right\">Fecha Inicio:</td> <td align=\"left\">  <input type=\"date\" id=\"fec_ini\" name=\"fec_ini\" value=\"" + request.getParameter("fec_ini") +  "\" align=\"center\"/> </td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin\" name=\"fec_fin\" value=\"" +  request.getParameter("fec_fin")  +   "\" align=\"center\"/></td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Descripción:</td> <td align=\"left\"> <input type=\"text\" id=\"descrip\" name=\"descrip\" value=\"" + request.getParameter("descrip") +"\" align=\"center\"/> </td> </tr>";
            
                        String optionsEstatusPeriodo =  "<select name=\"estatus\"  onchange=\"form.submit()\" id=\"estatus\" >" +                    
                                                        "<option value=\"lbA\" selected >Abierto</option>" +
                                                        "<option value=\"lbC\" >Cerrado</option>" +
                                                        "<option value=\"lbP\" >Próximo</option>" +
                                                        "</select>";
                                                        
                        optionsEstatusPeriodo = optionsEstatusPeriodo.replace(request.getParameter("estatus") + "\"", request.getParameter("estatus") + "\" selected");
                        
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Estatus:</td> <td align=\"left\"> " + optionsEstatusPeriodo + " </td> </tr>";
            
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_insc\" name=\"fec_ini_insc\" value=\"" + request.getParameter("fec_ini_insc") + "\" align=\"center\"/></td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_insc\" name=\"fec_fin_insc\" value=\"" + request.getParameter("fec_fin_insc") + "\" align=\"center\"/></td> </tr>";
            
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_raa\" name=\"fec_ini_raa\" value=\"" + request.getParameter("fec_ini_raa") + "\" align=\"center\"/></td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_raa\" name=\"fec_fin_raa\" value=\"" + request.getParameter("fec_fin_raa") + "\" align=\"center\"/></td> </tr>";
                                                                                
                        daoGeneral.setId_grupo_horario(Integer.parseInt(request.getParameter("id_grupo_horario")));
                                        
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Horario del Período:</td> <td align=\"left\"> "+daoGeneral.BuscarDatosHorariosPeriodo(daoConexion.ConexionBD())+"</td> </tr>";
                    
                        misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);             
                                        
                        //Agregar Administración
                        tablaDetalleAdministrativo =  "<tr><td align=\"right\">Costo Período:</td> <td align=\"left\"> <input type=\"number\" id=\"monto_sem\" name=\"monto_sem\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"" + request.getParameter("monto_sem") + "\" align=\"center\"/></td> </tr>";
                        tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Cantidad de Cuotas:</td> <td align=\"left\"> <input type=\"number\" id=\"nro_cuotas\" name=\"nro_cuotas\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"99\" step=\"1\" value=\"" + request.getParameter("nro_cuotas") + "\" align=\"center\"/></td> </tr>";
                            
                        String optionsDivisasPeriodo =  "<select name=\"periodo_divisas\"  onchange=\"form.submit()\" id=\"periodo_divisas\" name=\"periodo_divisas\">" +
                                                        "<option value=\"lbN\" selected>No</option>" +
                                                        "<option value=\"lbS\" >Si</option>" +
                                                        "</select>";
          
                        optionsDivisasPeriodo = optionsDivisasPeriodo.replace(request.getParameter("periodo_divisas") + "\"", request.getParameter("periodo_divisas") + "\" selected");                      
                                                      
                        tablaDetalleAdministrativo = tablaDetalleAdministrativo + "<tr><td  align=\"right\">Período en Divisas:</td> <td align=\"left\">"+ optionsDivisasPeriodo + "</td> </tr>";
                        tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Valor de la Divisa:</td> <td align=\"left\"> <input type=\"number\" id=\"valor_divisa\" name=\"valor_divisa\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"" + request.getParameter("valor_divisa") + "\" align=\"center\"/></td> </tr>";
                            
                        misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);
                        misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />   <input name=\"action\" type=\"submit\" id=\"cancelar\" value=\"Cancelar\" />   ");
                                        
                    } else {
                        daoGeneral.setPeriodo(request.getParameter("periodo"));
                        daoGeneral.setEstatus("");
                        daoGeneral.setPeriodo_divisas("");
                        daoGeneral.setId_grupo_horario(0);
                                    
                        if (!daoGeneral.PeriodoExiste(daoConexion.ConexionBD())) {
                            opcionesPeriodos = "<input type=\"text\" id=\"periodo\" name=\"periodo\" maxlength=\"10\" size=\"10\" value=\""+request.getParameter("periodo")+"\">";
                            misession.setAttribute("periodosSession", opcionesPeriodos);
                            misession.setAttribute("periodoSeleccionadoSession", request.getParameter("periodo"));

                            tablaDetalleAcademico =  "<tr><td align=\"right\">Fecha Inicio:</td> <td align=\"left\">  <input type=\"date\" id=\"fec_ini\" name=\"fec_ini\" value=\"" + request.getParameter("fec_ini") +  "\" align=\"center\"/> </td> </tr>";
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin\" name=\"fec_fin\" value=\"" +  request.getParameter("fec_fin")  +   "\" align=\"center\"/></td> </tr>";
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Descripción:</td> <td align=\"left\"> <input type=\"text\" id=\"descrip\" name=\"descrip\" value=\"" + request.getParameter("descrip") +"\" align=\"center\"/> </td> </tr>";
            
                            String optionsEstatusPeriodo =  "<select name=\"estatus\"  onchange=\"form.submit()\" id=\"estatus\" >" +                    
                                                            "<option value=\"lbA\" selected >Abierto</option>" +
                                                            "<option value=\"lbC\" >Cerrado</option>" +
                                                            "<option value=\"lbP\" >Próximo</option>" +
                                                            "</select>";
                            
                            //System.out.println(request.getParameter("estatus"));
                            optionsEstatusPeriodo = optionsEstatusPeriodo.replace(request.getParameter("estatus") + "\"", request.getParameter("estatus") + "\" selected");
                        
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Estatus:</td> <td align=\"left\"> " + optionsEstatusPeriodo + " </td> </tr>";
            
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_insc\" name=\"fec_ini_insc\" value=\"" + request.getParameter("fec_ini_insc") + "\" align=\"center\"/></td> </tr>";
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_insc\" name=\"fec_fin_insc\" value=\"" + request.getParameter("fec_fin_insc") + "\" align=\"center\"/></td> </tr>";
            
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_raa\" name=\"fec_ini_raa\" value=\"" + request.getParameter("fec_ini_raa") + "\" align=\"center\"/></td> </tr>";
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_raa\" name=\"fec_fin_raa\" value=\"" + request.getParameter("fec_fin_raa") + "\" align=\"center\"/></td> </tr>";
                            
                            daoGeneral.setId_grupo_horario(Integer.parseInt(request.getParameter("id_grupo_horario")));
                                
                            tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Horario del Período:</td> <td align=\"left\"> "+daoGeneral.BuscarDatosHorariosPeriodo(daoConexion.ConexionBD())+"</td> </tr>";
                                
                            misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);
                                
                            //Agregar Administración
                            tablaDetalleAdministrativo =  "<tr><td align=\"right\">Costo Período:</td> <td align=\"left\"> <input type=\"number\" id=\"monto_sem\" name=\"monto_sem\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"" + request.getParameter("monto_sem") + "\" align=\"center\"/></td> </tr>";
                            tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Cantidad de Cuotas:</td> <td align=\"left\"> <input type=\"number\" id=\"nro_cuotas\" name=\"nro_cuotas\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"99\" step=\"1\" value=\"" + request.getParameter("nro_cuotas") + "\" align=\"center\"/></td> </tr>";
                            
                            String optionsDivisasPeriodo =  "<select name=\"periodo_divisas\"  onchange=\"form.submit()\" id=\"periodo_divisas\" name=\"periodo_divisas\">" +
                                                            "<option value=\"lbN\" >No</option>" +
                                                            "<option value=\"lbS\" >Si</option>" +
                                                            "</select>";
          
                            optionsDivisasPeriodo = optionsDivisasPeriodo.replace(request.getParameter("periodo_divisas") + "\"", request.getParameter("periodo_divisas") + "\" selected");                      
                                                      
                            tablaDetalleAdministrativo = tablaDetalleAdministrativo + "<tr><td  align=\"right\">Período en Divisas:</td> <td align=\"left\">"+ optionsDivisasPeriodo + "</td> </tr>";
                            tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Valor de la Divisa:</td> <td align=\"left\"> <input type=\"number\" id=\"valor_divisa\" name=\"valor_divisa\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"" + request.getParameter("valor_divisa") + "\" align=\"center\"/></td> </tr>";

                            misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);                                
                            misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />   <input name=\"action\" type=\"submit\" id=\"cancelar\" value=\"Cancelar\" />   ");

                        } else {
                            opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                            misession.setAttribute("periodosSession", opcionesPeriodos);
                            misession.setAttribute("periodoSeleccionadoSession", request.getParameter("periodo"));
                                    
                            //Mostrar los datos Académicos detalle en la tabla
                            tablaDetalleAcademico = daoGeneral.BuscarDatosPeriodoAcademico(daoConexion.ConexionBD());
                            misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                            //Mostrar los datos Administrativos detalle en la tabla
                            tablaDetalleAdministrativo = daoGeneral.BuscarDatosPeriodoAdministrativo(daoConexion.ConexionBD());
                            misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);                            
                                        
                            misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />   <input name=\"action\" type=\"submit\" id=\"cancelar\" value=\"Cancelar\" />  <input name=\"action\" type=\"submit\" id=\"eliminar\" value=\"Eliminar\" /> ");

                        }
                    }
                }          
                        
                mensaje = "";
            } else {
            
            }
                
                
            //Si preciona el botón adicionar, eliminar o salir                    
            String accion = request.getParameter("action");
                
            if (accion != null) {
                //Validar los botones pulsados
                switch (accion) {
                    case "Eliminar": 
                        mensaje = daoGeneral.EliminarPeriodo(daoConexion.ConexionBD());
                        //if (mensaje.isEmpty()) {
                        //    mensaje = daoGeneral.EliminarSeccionesFiltradas(daoConexion.ConexionBD());
                        //}                        
                        daoGeneral.setPeriodo("");
                        opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                        tablaDetalleAcademico = "";
                        misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                        //Mostrar los datos Administrativos detalle en la tabla
                        tablaDetalleAdministrativo = "";
                        misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);

                        misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");                        
                        break;
                        
                    case "Adicionar":                         
                        daoGeneral.setPeriodo("");                            
                        opcionesPeriodos = "<input type=\"text\" id=\"periodo\" name=\"periodo\" maxlength=\"10\" size=\"10\">";
                        misession.setAttribute("periodosSession", opcionesPeriodos);                        
                        
                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();           
                        calendar.setTime(date);
                        String year = String.format("%04d",calendar.get(Calendar.YEAR));
                        String month = String.format("%02d",calendar.get(Calendar.MONTH)+1);
                        String day = String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));
                
                        String today = year + "-" + month + "-" + day;            
                            
                        tablaDetalleAcademico =  "<tr><td align=\"right\">Fecha Inicio:</td> <td align=\"left\">  <input type=\"date\" id=\"fec_ini\" name=\"fec_ini\" value=\"" + today +  "\" align=\"center\"/> </td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin\" name=\"fec_fin\" value=\"" + today +   "\" align=\"center\"/></td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Descripción:</td> <td align=\"left\"> <input type=\"text\" id=\"descrip\" name=\"descrip\" value=\"\" align=\"center\"/> </td> </tr>";
            
                        String optionsEstatusPeriodo =  "<select name=\"estatus\"  onchange=\"form.submit()\" id=\"estatus\" >" +                    
                                                        "<option value=\"lbA\" selected >Abierto</option>" +
                                                        "<option value=\"lbC\" >Cerrado</option>" +
                                                        "<option value=\"lbP\" >Próximo</option>" +
                                                        "</select>";
                        
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Estatus:</td> <td align=\"left\"> " + optionsEstatusPeriodo + " </td> </tr>";
            
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_insc\" name=\"fec_ini_insc\" value=\"" + today + "\" align=\"center\"/></td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_insc\" name=\"fec_fin_insc\" value=\"" + today +  "\" align=\"center\"/></td> </tr>";
            
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Inicio Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_raa\" name=\"fec_ini_raa\" value=\""  + today +  "\" align=\"center\"/></td> </tr>";
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Fecha Fin Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_raa\" name=\"fec_fin_raa\" value=\""  + today +  "\" align=\"center\"/></td> </tr>";
                            
                        tablaDetalleAcademico =  tablaDetalleAcademico + "<tr><td align=\"right\">Horario del Período:</td> <td align=\"left\"> "+daoGeneral.BuscarDatosHorariosPeriodo(daoConexion.ConexionBD())+"</td> </tr>";
                            
                        misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);             
                        
                        tablaDetalleAdministrativo =  "<tr><td align=\"right\">Costo Período:</td> <td align=\"left\"> <input type=\"number\" id=\"monto_sem\" name=\"monto_sem\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"0\" align=\"center\"/></td> </tr>";
                        tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Cantidad de Cuotas:</td> <td align=\"left\"> <input type=\"number\" id=\"nro_cuotas\" name=\"nro_cuotas\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"99\" step=\"1\" value=\"0\" align=\"center\"/></td> </tr>";
            
                        String optionsDivisasPeriodo =  "<select name=\"periodo_divisas\"  onchange=\"form.submit()\" id=\"periodo_divisas\" name=\"periodo_divisas\">" +
                                                        "<option value=\"lbN\" selected>No</option>" +
                                                        "<option value=\"lbS\" >Si</option>" +
                                                        "</select>";
          
                        tablaDetalleAdministrativo = tablaDetalleAdministrativo + "<tr><td  align=\"right\">Período en Divisas:</td> <td align=\"left\">"+ optionsDivisasPeriodo + "</td> </tr>";
                        tablaDetalleAdministrativo =  tablaDetalleAdministrativo + "<tr><td align=\"right\">Valor de la Divisa:</td> <td align=\"left\"> <input type=\"number\" id=\"valor_divisa\" name=\"valor_divisa\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"0\" align=\"center\"/></td> </tr>";
  
                        misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);
                            
                        misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\" />   <input name=\"action\" type=\"submit\" id=\"cancelar\" value=\"Cancelar\" />   ");
                        
                        mensaje = "";
                        break;
                            
                    case "Editar": 
                        mensaje = daoGeneral.AdicionarSeccionFiltrada(daoConexion.ConexionBD());
                        break;    
                            
                    case "Guardar":                            
                        daoGeneral.setPeriodo(request.getParameter("periodo"));
                        daoGeneral.setFec_ini(request.getParameter("fec_ini"));
                        daoGeneral.setFec_fin(request.getParameter("fec_fin"));
                        daoGeneral.setDescrip(request.getParameter("descrip"));
                        daoGeneral.setEstatus(request.getParameter("estatus").substring(2, 3));
                        daoGeneral.setFec_ini_insc(request.getParameter("fec_ini_insc"));
                        daoGeneral.setFec_fin_insc(request.getParameter("fec_fin_insc"));
                        daoGeneral.setFec_ini_raa(request.getParameter("fec_ini_raa"));
                        daoGeneral.setFec_fin_raa(request.getParameter("fec_fin_raa"));
                        daoGeneral.setId_grupo_horario(Integer.parseInt(request.getParameter("id_grupo_horario")));
                            
                        daoGeneral.setMonto_sem(Double.parseDouble(request.getParameter("monto_sem")));
                        daoGeneral.setNro_cuotas(Integer.parseInt(request.getParameter("nro_cuotas")));
                        daoGeneral.setValor_divisa(Double.parseDouble(request.getParameter("valor_divisa")));
                        daoGeneral.setPeriodo_divisas(request.getParameter("periodo_divisas").substring(2, 3));

                        if (daoGeneral.PeriodoExiste(daoConexion.ConexionBD())) {
                            mensaje = daoGeneral.ActualizarPeriodo(daoConexion.ConexionBD());
                        } else {
                            mensaje = daoGeneral.AdicionarPeriodo(daoConexion.ConexionBD());
                        }
                            
                        daoGeneral.setPeriodo("");
                        opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                        tablaDetalleAcademico = "";
                        misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                        //Mostrar los datos Administrativos detalle en la tabla
                        tablaDetalleAdministrativo = "";
                        misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);
                        misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");
                        break;
                            
                    case "Cancelar":                             
                        daoGeneral.setPeriodo("");
                        opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                        tablaDetalleAcademico = "";
                        misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                        //Mostrar los datos Administrativos detalle en la tabla
                        tablaDetalleAdministrativo = "";
                        misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);

                        misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");
                            
                        mensaje = "";
                        break;    

                    case "Salir":                       
                        daoGeneral.setPeriodo("");
                        opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                        misession.setAttribute("periodosSession", opcionesPeriodos);
                                 
                        tablaDetalleAcademico = "";
                        misession.setAttribute("detallePeriodoAcademicoSession", tablaDetalleAcademico);

                        //Mostrar los datos Administrativos detalle en la tabla
                        tablaDetalleAdministrativo = "";
                        misession.setAttribute("detallePeriodoAdministrativoSession", tablaDetalleAdministrativo);
                            
                        mensaje = "";
                            
                        pagina="principal/principal";
                        break;                    
                    }
                }
            
            } else {                
                //Inicilizo variable de sesion para el manejo del filtro del período.
                opcionesPeriodos = daoGeneral.BuscarPeriodos(daoConexion.ConexionBD());
                misession.setAttribute("periodosSession", opcionesPeriodos);                
                misession.setAttribute("botonSession", "<input name=\"action\" type=\"submit\" id=\"adicionar\" value=\"Adicionar\" />");                
            }
                
        }
        
        ModelAndView modelAndView = new ModelAndView(pagina);        
        modelAndView.addObject("mensajeError", mensaje);
        misession.setAttribute("mensajeError", mensaje);
        return modelAndView;  
    }
}
