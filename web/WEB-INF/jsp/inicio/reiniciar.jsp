<%-- 
    Document   : activar
    Created on : 24/02/2016, 08:59:13 PM
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

        <title>Reinicio de la Contraseña</title>
                
    </head>
    <body onload="startTimer(<%= (session.getMaxInactiveInterval() - 30) %>)">  
        
        <% //String datoUno = request.getParameter("dato1");
           //String datoDos = request.getParameter("dato2"); %>
        <h1 align="center">Olvidó la Contraseña</h1>
        
        <div id="page-wrap" align="center">
            <p> </p>
            <p> </p>
            <p> <img src="<%=request.getContextPath()%>/recursos/img/logoTexto.png" height="100"/></p>       
            <p></p>
            <p></p>
            <p><a href="login.do">Ir al Inicio</a></p>
            <form name="formulario" action="reiniciar.do" method="post" autocomplete="off" type="reset">
            <!--form name="frmTest" action="" method="post" onsubmit="return validateForm(this);"--> 
                <table align="center">
                    <tr>
                        <td>
                            Correo Electrónico:
                        </td>
                        <td>
                            <input type="email" name="login" value="" onfocus="clearThis(this)" maxlength="60"/>
                        </td>
                    </tr>
                    
                    <tr align=left>
                        <td>
                            Cédula o Pasaporte:
                        </td>
                        <td>
                            <input type="text" name="cedula" value="" onkeypress="return checkIt(event);" onfocus="clearThis(this)" maxlength="9"/>
                        </td>
                    </tr>
                    
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" name="boton_enviar" id="boton_enviar" value="Enviar Contraseña" onClick=" this.form.submit(); this.disabled=true; this.value='Enviando…';"/>
                            
                            <!--onClick="this.disabled=true; this.value='Enviando…'; this.form.submit();"-->
                            <!--onclick="if(this.checked){this.form.submit_btn.disabled=false}else{this.form.submit_btn.disabled=true};this.blur();"-->
                            
                        </td>                        
                    </tr>
                    <tr>
                        ${mensajeError}
                        <div id="sessionTimer"></div>
                    </tr>
                    <!--tr><p><a href="login.do">Ir al Inicio</a></p></tr-->
                </table>                
            </form>

            <% //out.println(datoUno);
            //out.println(datoDos); %>
        </div>
    </body>
</html>
