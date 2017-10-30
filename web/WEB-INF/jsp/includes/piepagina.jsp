<%-- 
    Document   : piepagina
    Created on : 29/04/2016, 08:31:16 PM
    Author     : gerson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div id="page-wrap" style="border: 0px solid" align="center">
            <% String tipoUsuario = (String) session.getAttribute("tipoUsuSession"); 
            if (tipoUsuario!=null) {  
                out.println("<p><a href=\"http://iupg.com.ve\">IUPG</a> | <a href=\"expiracion.do\">Cerrar Sesión</a></p>");                
            } else {
                out.println("<p><a href=\"http://iupg.com.ve\">IUPG</a> | <a href=\"register.do\">Registrarse</a> | <a href=\"reiniciar.do\">Olvidó la Contraseña</a></p>");
            }
            %>
            
        </div>    
    </body>
</html>
