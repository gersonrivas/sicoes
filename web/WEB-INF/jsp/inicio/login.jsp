<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@include file="/recursos/includes/include.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/recursos/includes/estilos.jsp" %>
        <title>Bienvenidos al Sistema de Control de Estudios</title>
    </head>

    <body>      
        <h1 align="center">Bienvenido al Sistema de Inscripciones</h1>
        
        <br>
        <br>
        <br>
        <br>
        
        <div id="page-wrap" align="center">
            <form action="login.do" method="post" autocomplete="off" type="reset">
            <table id="principal" style="width:800px" border="0" align=center>
                <tr>
                    <td width="45%">
                        <img src="<%=request.getContextPath()%>/recursos/img/logoTexto.png" height="100"/>
                    </td>
                    <td width="55%">
                        Ingrese el Usuario y contraseña.
                    </td>                    
                </tr>            
                <tr>                
                </tr>
                <tr>                
                </tr>
                <tr>
                    <!--form method="post" action="hi.iq/register.jsp" enctype="multipart/form-data"-->
                    <!--form action="login.do" method="post" autocomplete="off" type="reset"--> 
                        <td align="right">
                            Usuario:     
                        </td>                        
                        <td>
                            <input type="text" name="login" value="cuenta-correo" onfocus="clearThis(this)" maxlength="60" />
                        </td>
                </tr>
                <tr>
                        <td align="right">
                            Contraseña:
                        </td>
                        <td>
                            <!--input type="password" name="clave" value="" -->
                            <input type="password" name="password" value="" onfocus="clearThis(this)" maxlength="20"/>
                        </td>
                </tr>
                <tr>        
                    <td align="left">                              
                        <!--p><a href="register.do">Registrarse</a></p-->
                    </td>
                    <td>
                        <input type="submit" value="Ingresar"/>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <!--p><a href="reiniciar.do">Olvidó la Contraseña</a></p-->
                    </td>
                    <td align="center">
                    </td>
                </tr>
                
                <tr>
                    ${mensajeError}
                </tr>
            </table>
            </form>
        </div>
                    <!--p><a href="prueba.do">prueba</a></p-->
    </body>
</html>
                         