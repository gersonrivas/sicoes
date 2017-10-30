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
        <title>Activación de Cuenta</title>
    </head>
    <body onload="startTimer(<%= (session.getMaxInactiveInterval() - 30) %>)">  
        
        <% //String datoUno = request.getParameter("dato1");
           //String datoDos = request.getParameter("dato2"); %>
        <h1 align="center">Cambio de Contraseña y Activación de Cuenta</h1>
        
        <div id="page-wrap" align="center">
            <p> </p>
            <p> </p>
            <p> <img src="<%=request.getContextPath()%>/recursos/img/logoTexto.png" height="100"/></p>       
            <p></p>
            <p><a href="login.do">Ir al Inicio</a></p>
            <p></p>
            <form action="activar.do" method="post" autocomplete="off" type="reset" > 
                <table align="center">
                    <tr>
                        <td>
                            Cuenta:
                        </td>
                        <td>
                            <input type="text" name="login" maxlength="60" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" value="${login}" onfocus="clearThis(this)" required />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Contraseña Suministrada:
                        </td>
                        <td>
                            <input type="password" name="password" value="" maxlength="20" onfocus="clearThis(this)" required/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Escriba Nueva Contraseña:
                        </td>
                        <td>
                            <!--input type="password" maxlength="20" pattern=".(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#=?+*_%/.>:]).{6,}" title="Debe contener al menos seis dígitos: uno en mayúscula, uno en minúscula, un número y un caracter especial @#=?+*_%/.>:]" name="password1" id="campoConfirm" value="" onfocus="clearThis(this)" required/-->
                            <!--input type="password" maxlength="20"  title="Debe contener al menos seis dígitos: uno en mayúscula, uno en minúscula, un número y un caracter especial @#=?+*_%/.>:]" name="password1" id="campoConfirm" value="" oninput="validarContrasena(this)" onfocus="clearThis(this)" required/-->
                            <input type="password" maxlength="20" title="Debe contener al menos ocho dígitos: uno en mayúscula, uno en minúscula, un número y un caracter especial @#=?+*_%/.>:]" name="password1" id="campoConfirm" value="" onfocus="clearThis(this)" oninput="validarContrasena(this)" required/>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            Confirme Contraseña:
                        </td>
                        <td>
                            <input type="password" maxlength="20" name="password2" value="" onfocus="clearThis(this)" oninput="checkConfirm(this)"/>
                        </td>
                    </tr>
                    
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" name="boton_enviar" id="boton_enviar" value="Activar" onClick="this.disabled=true; this.value='Activando…'; this.form.submit();"  />
                        </td>                        
                    </tr>
                    <tr>                        
                        ${mensajeError}
                        <div id="sessionTimer"></div>
                    </tr>
                </table>                
            </form>

            <% //out.println(datoUno);
            //out.println(datoDos); %>
        </div>
    </body>
</html>
