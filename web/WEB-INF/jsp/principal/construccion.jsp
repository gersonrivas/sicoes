<%-- 
    Document   : principal
    Created on : 08/02/2016, 07:00:18 PM
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

        <p align="center">Bienvenido(a) ${usuarioSession}</p>
        <!--div id="page-wrap" style="border: 1px solid" align="center"-->
        <!--div id="page-wrap" th:replace="frag_inicial.jsp :: footer"-->
        <div id="page-wrap" style="border: 2px solid rgb(204, 204, 204);" align="center">
				
            <form name="nombre_form" id="id_form" method="post" action="principal.do">
		<table id="id_table" summary="" border="1" style="width:100%">
                    <tr>
                        <!--p>${usuarioSession}</p-->
                    </tr>
                    <tr>
                        <br></br> 
                        <br></br>                     
                    </tr>
                    <tr>
                        <img src="<%=request.getContextPath()%>/recursos/img/construccion.jpeg" height="100"/>
                    </tr>
                    <tr>         
                        <br></br>
                        <br></br>
                        <br></br>
                    </tr>
		</table>
            </form>				
        </div>
                    
    </body>
    
</html>
