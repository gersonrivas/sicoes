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
        
        <p align="center">Alumno ${usuarioSession}</p>
        <!--div id="page-wrap" style="border: 1px solid" align="center"-->
        <!--div id="page-wrap" th:replace="frag_inicial.jsp :: footer"-->
        <div id="page-wrap" style="border: 2px solid rgb(204, 204, 204);" align="center">
				
            <form name="nombre_form" id="id_form" method="post" action="seleccionAsignaturaProfesor.do">
                
                <div class="datagrid">
                    <!-- Primera parte de la tabla -->                    
                     
                    <table border="1">
                        <tr>
                            <th align="center"><label for="lbcedula">Cédula / Pasaporte: ${cedulaUsuSession}</label></th>
                        </tr>
                    </table>
                        
                    <!-- Tabla detalle -->    
                    <table border="1">
                        <thead>
                            <tr align="center">
                                <th align="center">Período</th>
                                <th align="center">Asignatura</th>
                                <th align="center">Sección</th>                                
                            </tr>
                        </thead>
                        <!-- Pagineo -->
                        <!--tfoot><tr><td colspan="4"><div id="paging"><ul><li><a href="#"><span>Anterior</span></a></li><li><a href="#" class="active"><span>1</span></a></li><li><a href="#"><span>2</span></a></li><li><a href="#"><span>3</span></a></li><li><a href="#"><span>4</span></a></li><li><a href="#"><span>5</span></a></li><li><a href="#"><span>Siguiente</span></a></li></ul></div></tr></tfoot-->
                        <!-- Botonera y mensajes -->
                        <tfoot>
                            <tr>                                
                                <td colspan="3" align="center" id="tdmsg">  
                                    <div id="paging" align="right">        
                                        ${mensajeError}
                                        <div id="sessionTimer"></div>
                                        
                                        <input name="action" type="submit" id="consultar" value="Cargar" />
                                        <input name="action" type="submit" id="salir" value="Salir" />
                                    </div>
                                </td>
                            </tr>
                        </tfoot>

                        <!-- Contenido de la Tabla -->
                        <tbody>
                            
                            <tr align="center">
                                <td align="center">
                                    ${periodosProfesorSession}
                                </td>
                                <td align="center">
                                    ${materiasProfesorSession}
                                </td>
                                <td align="center">
                                    ${seccionesMateriaProfesorSession}
                                </td>
                            </tr>

                        </tbody>
                    </table>
                            
                </div>                
            </form>				
        </div>                    
    </body>
    
</html>

