<%-- 
    Document   : consultarInscripcion
    Created on : 03/05/2016, 07:00:18 PM
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
        <div id="page-wrap" style="border: 2px solid rgb(204, 204, 204); " align="center" >
				
            <form name="nombre_form" id="id_form" method="post" action="asignaturaSeccionAula.do">
                
                <div class="datagrid">
                    <!-- Primera parte de la tabla -->                    
                     
                    <table border="1">
                        <tr>
                            <th align="center">Asignaturas, Secciones y Aulas</th>
                        </tr>
                    </table>
                        
                    <!-- Tabla detalle -->    
                    <table border="1">
                        <thead>
                            <tr align="center">
                                <th align="center">Sede</th>
                                <th align="center">Período</th>
                                <th align="center">Lapso</th>
                                <th align="center">Especialidad</th>
                                <th align="center">Asignatura</th>
                                <th align="center">Turno</th>
                                <th align="center">Sección</th>
                                <th align="center">Aula</th>
                                <th align="center">Inscritos</th>
                            </tr>
                        </thead>
                        <!-- Pagineo -->
                        <!--tfoot><tr><td colspan="4"><div id="paging"><ul><li><a href="#"><span>Anterior</span></a></li><li><a href="#" class="active"><span>1</span></a></li><li><a href="#"><span>2</span></a></li><li><a href="#"><span>3</span></a></li><li><a href="#"><span>4</span></a></li><li><a href="#"><span>5</span></a></li><li><a href="#"><span>Siguiente</span></a></li></ul></div></tr></tfoot-->
                        <!-- Botonera y mensajes -->
                        <tfoot>
                            <tr>                                
                                <td colspan="9" align="center" id="tdmsg">  
                                    <div id="paging" align="right">        
                                        ${mensajeError}
                                        <div id="sessionTimer"></div>
                                        
                                        ${botonSession}                                        
                                        <input name="action" type="submit" id="salir" value="Salir" />
                                    </div>
                                </td>
                            </tr>
                        </tfoot>

                        <!-- Contenido de la Tabla -->
                        <tbody>
                            
                            <tr align="center">
                                <td align="center">
                                    ${sedesSession}
                                </td>
                                <td align="center">
                                    ${periodosSession}
                                </td>
                                <td align="center">
                                    ${lapsosSession}
                                </td>
                                <td align="center">
                                    ${especialidadesSession}
                                </td>
                                <td align="center">
                                    ${asignaturasSession}
                                </td>
                                <td align="center">
                                    ${turnosSession}
                                </td>
                                <td align="center">
                                    ${seccionesSession}
                                </td>
                                <td align="center">
                                    ${aulasSession}
                                </td>
                                <td align="center">
                                    
                                </td>
                            </tr>
                            
                            ${detalleAulasSession}

                        </tbody>
                    </table>
                            
                </div>                
            </form>				
        </div>                    
    </body>
    
</html>
