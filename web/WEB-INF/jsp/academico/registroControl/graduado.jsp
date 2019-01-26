<%-- 
    Document   : graduado
    Created on : 16/01/2019, 07:00:18 PM
    Author     : gerson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="/recursos/includes/estilos.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval() %>; url=expiracion.do" /> 
        
        <title>Sistema de Control de Estudios</title>
    </head>
    <body onload="startTimer(<%= (session.getMaxInactiveInterval() - 30) %>)">  
                        
        <br></br>        
        
        <p align="center">Usuario ${usuarioSession}</p>
        <!--div id="page-wrap" style="border: 1px solid" align="center"-->
        <!--div id="page-wrap" th:replace="frag_inicial.jsp :: footer"-->
        <div id="page-wrap" style="border: 2px solid rgb(204, 204, 204);" align="center">
				
            <form name="nombre_form" id="id_form" method="post" action="graduado.do">
                
                <div class="datagrid">
                    <!-- Primera parte de la tabla -->                    
                    <table border="1">
                        <tr>
                            <th colspan="3" align="center">Graduados</th>
                        </tr>
                    </table>
                            
                    <!-- Tabla detalle -->    
                    <table border="1">
                        <thead>
                            <tr align="center">
                                <th align="center">Sede</th>
                                <th colspan="2" align="center">Especialidad</th>
                                <th colspan="3" align="center">Fecha Graduación</th>
                            </tr>
                            
                            <tr align="center">
                                <td align="center">                                    
                                    ${sedeGraduadoSession}
                                </td>
                                <td colspan="2" align="center">
                                    ${especialidadGraduadoSession}
                                </td>
                                <td colspan="3" align="center">
                                    ${fechaGraduacionGraduadoSession}
                                </td>
                            </tr>
                            
                            
                        </thead>
                        <!-- Pagineo -->
                        <!--tfoot><tr><td colspan="4"><div id="paging"><ul><li><a href="#"><span>Anterior</span></a></li><li><a href="#" class="active"><span>1</span></a></li><li><a href="#"><span>2</span></a></li><li><a href="#"><span>3</span></a></li><li><a href="#"><span>4</span></a></li><li><a href="#"><span>5</span></a></li><li><a href="#"><span>Siguiente</span></a></li></ul></div></tr></tfoot-->
                        <!-- Botonera y mensajes -->
                        <tfoot>
                            <tr>                                
                                <td colspan="8" align="center" id="tdmsg">  
                                    <div id="paging" align="right">        
                                        ${mensajeError}
                                        <div id="sessionTimer"></div>
                                        
                                        ${botonSession}                                        
                                        <input name="action" type="submit" id="retornar" value="Retornar" />
                                    </div>
                                </td>
                            </tr>
                        </tfoot>

                        <!-- Contenido de la Tabla -->
                        <!-- Contenido de la Tabla -->
                        <tbody>
                            
                            ${listadoGraduadosSession}

                        </tbody>
                    </table>


                            
                </div>                
            </form>				
        </div>                    
    </body>
    
</html>
