<%-- 
    Document   : configurarPeriodo
    Created on : 25/09/2020, 09:42:37 PM
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
        
        
        <style>
            body {
    /*background: darkgrey;    */
    
}

.contenedor{
    /*width: 1080px;
    margin: auto;*/
    /*background: black;*/
    /*color: bisque;
    padding: 20px 15px 50px 50px;
    border-radius: 10px;
    box-shadow: 0 10px 10px 0px rgba(0, 0, 0, 0.8);*/
    
}

.contenedor .titulo{
    font-size: 3.5ex;
    font-weight: bold;
    margin-left: 10px;
    margin-bottom: 10px;
}

#pestanas {
    float: top;
    font-size: 3ex;
    font-weight: bold;
}

#pestanas ul{
    margin-left: 0px; 

}


#pestanas li{
    list-style-type: none;
    float: left;
    text-align: center;
    margin: 0px 2px -2px -0px;
    /*background: darkgrey;*/
    background: #006699;
    /*background: #FFFFFF;*/
    /*background: #E1EEF4;*/
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    border: 2px solid #000000;
    /*border-bottom: dimgray;*/
    padding: 0px 20px 0px 20px;
    
}

#pestanas a:link{
    text-decoration: none;
    color: #000000;
    /*background: #E1EEF4;*/
    
    /*color: bisque;*/
   
    
}

#contenidopestanas{
    clear: left;  
    /*background: dimgray;*/
    background: #E1EEF4;
    /*background: #006699;*/
    
    /*padding: 20px 0px 20px 20px;*/
    border-radius: 5px;
    border-top-left-radius: 0px;
    /*border: 2px solid bisque;*/
    border: 2px solid #000000;
    /*width: 1025px;*/
    
}

        </style>
        
        
<script>        
        // Dadas la division que contiene todas las pestañas y la de la pestaña que se 
// quiere mostrar, la funcion oculta todas las pestañas a excepcion de esa.
function cambiarPestanna(pestannas,pestanna) {
    
    // Obtiene los elementos con los identificadores pasados.
    pestanna = document.getElementById(pestanna.id);
    listaPestannas = document.getElementById(pestannas.id);
    
    // Obtiene las divisiones que tienen el contenido de las pestañas.
    cpestanna = document.getElementById('c'+pestanna.id);
    listacPestannas = document.getElementById('contenido'+pestannas.id);
    
    i=0;
    // Recorre la lista ocultando todas las pestañas y restaurando el fondo 
    // y el padding de las pestañas.
    while (typeof listacPestannas.getElementsByTagName('div')[i] != 'undefined'){
        $(document).ready(function(){
            $(listacPestannas.getElementsByTagName('div')[i]).css('display','none');
            $(listaPestannas.getElementsByTagName('li')[i]).css('background','');
            $(listaPestannas.getElementsByTagName('li')[i]).css('padding-bottom','');
        });
        i += 1;
    }

    $(document).ready(function(){
        // Muestra el contenido de la pestaña pasada como parametro a la funcion,
        // cambia el color de la pestaña y aumenta el padding para que tape el  
        // borde superior del contenido que esta juesto debajo y se vea de este 
        // modo que esta seleccionada.
        $(cpestanna).css('display','');
        //$(pestanna).css('background','dimgray');
        $(pestanna).css('background','#E1EEF4');
        $(pestanna).css('padding-bottom','2px');
        
    });

}

</script>
        
        
    </head>
    <body onload="startTimer(<%= (session.getMaxInactiveInterval() - 30) %>)">  
        
        
                <br></br>        
        
        <p align="center">Usuario: ${usuarioSession}</p>
        <!--div id="page-wrap" style="border: 1px solid" align="center"-->
        <!--div id="page-wrap" th:replace="frag_inicial.jsp :: footer"-->
        
        

                
                
                
            <div id="page-wrap" style="border: 2px solid rgb(204, 204, 204);" align="center">
				
            <form name="nombre_form" id="id_form" method="post" action="configurarPeriodo.do">
                
                <div class="datagrid">
                    <!-- Primera parte de la tabla -->                    
                     
                    <table border="1">
                        <tr>
                            <th colspan="2" align="center"><label for="lbperiodo">Datos del Período</label></th>
                            
                        </tr>
                        
                        <tr>
                            <th colspan="2" align="center"> 
                                Período
                                ${periodosSession}
                            </th>
                        </tr>

                        <tr>
                            <th colspan="2" align="center">                               
                                
                        
                                <div class="contenedor">
                                <div class="titulo"> </div>
                                <div id="pestanas">
                                    
                                    

                                    <style>
                                        ul.navega li {
                                        display: inline;
                                        }
                                    </style>
                                    <ul id=lista class="navega">
                                    
                                        <li id="pestana1"><a href='javascript:cambiarPestanna(pestanas,pestana1);'>Académico</a></li>
                                        <li id="pestana2"><a href='javascript:cambiarPestanna(pestanas,pestana2);'>Administrativo</a></li>
                                        
                                    </ul>
                                </div>

                                
                                
                                
                                <body onload="javascript:cambiarPestanna(pestanas,pestana1);">

                                <div id="contenidopestanas">
                            
                            
                            <table>
                                
                                <div id="cpestana1">                                    
                                    Contenido de la pestaña 1
                                    ${detallePeriodoAcademicoSession}
                                </div>
                                <div id="cpestana2">
                                    Contenido de la pestaña 2
                                    ${detallePeriodoAdministrativoSession}
                                </div>    
                            </table>
                                
                                    
                                

                            </th>                             
                        </tr>   
                        
                    </table>
                        
                    <!-- Tabla detalle -->    
                    <table border="1">
                        <thead>
                            <tr>
                                <th></th>
                            </tr>
                        </thead>
                        <!-- Pagineo -->
                        <!--tfoot><tr><td colspan="4"><div id="paging"><ul><li><a href="#"><span>Anterior</span></a></li><li><a href="#" class="active"><span>1</span></a></li><li><a href="#"><span>2</span></a></li><li><a href="#"><span>3</span></a></li><li><a href="#"><span>4</span></a></li><li><a href="#"><span>5</span></a></li><li><a href="#"><span>Siguiente</span></a></li></ul></div></tr></tfoot-->
                        <!-- Botonera y mensajes -->
                        <tfoot>
                            <tr>
                                <td colspan="5" align="center" id="tdmsg">  
                                    <div id="paging" align="right">
                                        
                                        ${mensajeError}
                                        <div id="sessionTimer"></div>
                                        
                                        <% Boolean validado = (Boolean) session.getAttribute( "validadoSession" );
                                           if(validado!=null && !validado.booleanValue()) {
                                                // Mostramos mensaje boton guardar
                                                out.println("<input name=\"action\" type=\"submit\" id=\"guardar\" value=\"Guardar\"  />" );
                                           }
                                        %>
                                        
                                        ${botonSession}
                                        
                                        <input name="action" type="submit" id="salir" value="Salir" />
                                    </div>
                                </td>
                            </tr>
                        </tfoot>

                        <!-- Contenido de la Tabla -->
                        <tbody>
                            
                            ${incripcionAlumnoSession}

                        </tbody>
                    </table>
                            
                </div>                

                
                
        </div>        
                
                
                
        
        
        
        
            </form>				
        </div>
        
        
        
    </body>
</html>
