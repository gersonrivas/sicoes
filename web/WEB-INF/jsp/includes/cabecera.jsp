<%-- 
    Document   : frag_inicial
    Created on : 29/04/2016, 08:57:47 AM
    Author     : gerson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>        
        
        <% String tipoUsuario = (String) session.getAttribute("tipoUsuSession");      
                
            if (tipoUsuario!=null) {  
                // Mostrando el Cintillo
                out.println("<div id=\"page-wrap\" style=\"border: 0px solid\" align=\"center\">");	
                out.println("<img src=\""+request.getContextPath()+"/recursos/img/cintillo.png\" />");	   	   
                out.println("</div>");  
               
                //Mostrando el menu       	
                out.println("<div id=\"page-wrap\" style=\"border: 1px solid\" align=\"center\">");
                out.println("<ul class=\"dropdown\">");
                
                //MENU PROCESO ACADEMICO
                out.println("<li><a href=\"#\">Proceso Académico</a>");
        	out.println("<ul class=\"sub_menu\">");                            
                // Proceso Académico para Alumnos
                if (tipoUsuario.equals("A")) {  
                    out.println("<li>");
                    out.println("<a href=\"#\">Inscripciones</a>"); 
                    out.println("<ul>");
                    out.println("<li><a href=\"inscripcion.do\" onclick=opcionMenu(\"opcion1\");>Crear Inscripción</a></li>");
                    out.println("<li><a href=\"consultarInscripcion.do\" onclick=opcionMenu('opcion1');>Consultar Inscripción</a></li>");
                    out.println("<li><a href=\"retiroInscripcion.do\" onclick=opcionMenu('opcion1');>Retiro de Asignatura</a></li>");
                    out.println("<li><a href=\"adicionInscripcion.do\" onclick=opcionMenu('opcion1');>Adición de Asignatura</a></li>");
                    out.println("</ul>");    
                    out.println("</li>");
                    
                    out.println("<li>");
                    out.println("<a href=\"#\">Solicitudes</a>");
                    out.println("<ul>");
                    out.println("<li><a href=\"construccion.do\" onclick=opcionMenu(\"opcion1\");>Constancia de Estudios</a></li>");
                    out.println("<li><a href=\"construccion.do\" onclick=opcionMenu('opcion1');>Constancia de Notas</a></li>");
                    out.println("</ul>");
                    out.println("</li>");
                }
                // Proceso Académico para Profesores
                if (tipoUsuario.equals("P")) { 
                    out.println("<li>");
                    out.println("<a href=\"#\">Notas</a>"); 
                    out.println("<ul>");                    
                    out.println("<li><a href=\"seleccionAsignaturaProfesor.do\">Cargar</a></li>");
                    out.println("<li><a href=\"consultarActasNotas.do\">Consultar Actas</a></li>");
                    out.println("</ul>");
                    out.println("</li>");                   
                    out.println("<li>");
                    out.println("<a href=\"#\">Horarios</a>"); 
                    out.println("<ul>");                    
                    out.println("<li><a href=\"construccion.do\">Consultar Horarios</a></li>");
                    out.println("</ul>");
                    out.println("</li>");
                }
                
                                
                // Proceso Académico para Analístas de Registro y Control y Super Usuario
                if (tipoUsuario.equals("R") || tipoUsuario.equals("S")) {  
                    out.println("<li>");
                    out.println("<a href=\"#\">Inscripciones</a>"); 
                    out.println("<ul>");
                    
                    if (tipoUsuario.equals("S")) out.println("<li><a href=\"buscarAlumnoCedulaPeriodoAdmin.do\" onclick=opcionMenu('opcion1');>Inscripción Alumno</a></li>");
                    out.println("<li><a href=\"construccion.do\" onclick=opcionMenu('opcion1');>Retiro de Asignatura</a></li>");
                    out.println("<li><a href=\"construccion.do\" onclick=opcionMenu('opcion1');>Adición de Asignatura</a></li>");                   
                    
                    out.println("</ul>");    
                    out.println("</li>");
                    
                    out.println("<li>");
                    out.println("<a href=\"#\">Solicitudes</a>");
                    out.println("<ul>");
                    out.println("<li><a href=\"construccion.do\" onclick=opcionMenu(\"opcion1\");>Constancia de Estudios</a></li>");
                    out.println("<li><a href=\"construccion.do\" onclick=opcionMenu('opcion1');>Constancia de Notas</a></li>");
                    out.println("</ul>");
                    out.println("</li>");
                    
                    out.println("<li>");
                    out.println("<a href=\"#\">Programación</a>"); 
                    out.println("<ul>");
                    out.println("<li><a href=\"asignaturaSeccionAula.do\" onclick=opcionMenu('opcion1');>Asignatura/Sección/Aula</a></li>");
                    out.println("</ul>");    
                    out.println("</li>");
                    
                    out.println("<li>");
                    out.println("<a href=\"#\">Registros</a>");
                    out.println("<ul>");
                    out.println("<li><a href=\"recordNotas.do\" onclick=opcionMenu(\"opcion1\");>Record de Notas</a></li>");
                    if (tipoUsuario.equals("S")) out.println("<li><a href=\"seleccionProfesorAsignaturaSeccion.do\" onclick=opcionMenu('opcion1');>Notas</a></li>");
                    out.println("</ul>");    
                    out.println("</li>");
                    
                    out.println("<li>");
                    out.println("<a href=\"#\">Graduados</a>"); 
                    out.println("<ul>");
                    out.println("<li><a href=\"asignaturaSeccionAula.do\" onclick=opcionMenu('opcion1');>Pasar a Grado</a></li>");

                    
                    
                    
                    out.println("</ul>");
                    out.println("</li>");
                    
                }
                
        	out.println("</ul>");
        	out.println("</li>");
                //FIN MENU ACADEMICO
                
                //MENU ADMINISTRATIVO
        	out.println("<li><a href=\"#\">Proceso Administrativo</a>");
        	out.println("<ul class=\"sub_menu\">");
                //Proceso Administrativo para Alumnos
                if (tipoUsuario.equals("A") || tipoUsuario.equals("S")) {  
                    out.println("<li><a href=\"construccion.do\"onclick=cambiarContenido('texto')>Inscripción</a></li>");
                    out.println("<li><a href=\"construccion.do\">Solicitudes</a></li>");
                }
        	out.println("</ul>");
        	out.println("</li>");
                
                //FIN MENU ADMINISTRATIVO
                
                if (tipoUsuario.equals("S")) {
                    out.println("<li><a href=\"#\">Administración del Sistema</a>");
                    out.println("<ul class=\"sub_menu\">");
                // Proceso Administración del Sistema para SuperUsuario
                //if (tipoUsuario.equals("S")) {
                    out.println(" <li>");
                    out.println("	<a href=\"usuariosAdmin.do\">Usuarios</a> ");
                    out.println(" </li> ");
                    out.println(" <li> ");
                    out.println("	<a href=\"construccion.do\">Respaldos</a> ");
                    out.println(" </li> ");
                    out.println(" <li> ");
                    out.println("	<a href=\"construccion.do\">Sincronización</a> ");
                    out.println(" </li> ");
                    out.println(" <li> ");
                    out.println("	<a href=\"#\">Reportes</a> ");
                    out.println("<ul>");
                    out.println(" <li> <a href=\"reporteInscripciones.do\">Inscripciones</a> </li> ");
                    out.println("</ul>");
                    out.println(" </li> ");
                
        	out.println("</ul>");
                }
        	out.println("<li><a href=\"#\">Ayuda</a>");
        	out.println("<ul class=\"sub_menu\">");
        	out.println("<li><a href=\"construccion.do\">Proceso Administrativo</a></li>");
        	out.println("<li><a href=\"construccion.do\">Proceso Académico</a></li>");
        	out.println("<li><a href=\"construccion.do\">Descargar Instructivo</a></li>");
        	out.println("<li><a href=\"construccion.do\">Video Tutorial</a></li>");
        	out.println("</ul>");        	
                
                out.println("<li><a href=\"expiracion.do\">Cerrar Sesión</a>");
        	//out.println("<ul class=\"sub_menu\">");
        	//out.println("<li><a href=\"expiracion.do\">Cerrar</a></li>");
                
                out.println("</li>");                
                out.println("</ul>");
                out.println("</div>");
               
           }
        %>
    </body>
</html>
