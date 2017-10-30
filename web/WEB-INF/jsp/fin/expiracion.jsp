<%-- 
    Document   : expiracion
    Created on : 11/01/2016, 04:11:01 PM
    Author     : gerson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/recursos/includes/estilos.jsp" %>
        <title>Fin de Sessión</title>
    </head>
    <body onload="nobackbutton();">
        <h1 align="center">Ha expirado la sesión del usuario</h1>
        <div id="page-wrap">
            <table id="fin" style="width:800px" border="0" align="center">
                <tr>
                    <td>
                        <img src="<%=request.getContextPath()%>/recursos/img/logoTexto.png" height="100"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <a href="login.do">Volver a iniciar --> </a>
                    </td>
                </tr>
            </table> 
        </div>
    </body>
</html>
