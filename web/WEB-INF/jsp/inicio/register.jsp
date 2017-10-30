<%-- 
    Document   : register
    Created on : 09/01/2015, 09:35:54 PM
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
            <title>Registro de Usuario</title>
            
            <script type="text/javascript">
                $('#formulario').bind('change keyup', function() {
                if($(this).validate().checkForm()) {
                    $('#boton_enviar').attr('disabled', false);
                } else {
                    $('#boton_enviar').attr('disabled', true);
                } });
            </script>
            
        </head>
        <body onload="startTimer(<%= (session.getMaxInactiveInterval() - 30) %>)">  
            <center>
            <h1>Registro del Usuario</h1>
            
            <p> </p>
            <p> </p>
            <p> <img src="<%=request.getContextPath()%>/recursos/img/logoTexto.png" height="100"/></p>       
            <p></p>
            <p></p>
            
            
            <p><a href="login.do">Ir al Inicio</a></p>
            
                    
            <form id="formulario" action="register.do" method="post" autocomplete="off" type="reset">
                <table>
                    <tr align=left>
                        <td>
                            Cédula o Pasaporte:
                        </td>
                        <td>
                            <input type="text" name="cedula" value="" maxlength="9" onkeypress="return checkIt(event);" onfocus="clearThis(this)" required >
                        </td>
                    </tr>
                    <tr align=left>
                        <td>
                            Correo Electrónico:
                        </td>
                        <td>
                            <!--input type="text" name="email1" id="campoConfirm" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"-->
                            <input type="email" name="email1" id="campoConfirm" value="" maxlength="60" onfocus="clearThis(this)" required />
                        </td>
                    </tr>
                    
                    <tr align=left>
                        <td>
                            Confirmar Correo Electrónico:
                        </td>
                        <td>
                            <input type="email" name="email2" oninput="checkConfirm(this)" maxlength="60">
                        </td>
                    </tr>

                    <tr>
                        <td align=right>
                            <!--input type="submit" value="Regresar"-->
                        </td>
                        <td>
                            <!--input type="submit" value="Registrar" onclick="this.disabled=true;"-->
                            <input type="submit" name="boton_enviar" id="boton_enviar" value="Registrar" onClick="this.disabled=true; this.value='Registrando…'; this.form.submit();">                            
                            <!--onClick="this.disabled=true; this.value='Registrando…'; this.form.submit();"/-->
                            <!--onClick="this.disabled=true; this.value='Enviando…'; "-->
                        </td>
                    </tr>
                    <tr>
                        ${mensajeError}
                        <div id="sessionTimer"></div>
                        
                    </tr>
                </table>
            </form> 
            </center>
        </body>
    </html>
<!--/t:if-->