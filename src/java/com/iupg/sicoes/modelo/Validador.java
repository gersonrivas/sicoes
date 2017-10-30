/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gerson
 */
public class Validador {
    
        public boolean isUsernameOrPasswordValid(String $cadena) {
            char[] cadena = $cadena.toLowerCase().toCharArray();
     
            //Compruebo la longitud            
            if (cadena.length <= 6) {
                return false;
            }
            for (int i = 0; i < cadena.length; i++) {
                //Compruebo que no existan caracteres especiales (solamento los que podrian ser usados para una inyeccion SQL o perjudicar en la consulta);
                if (cadena[i] == ' '
                        || cadena[i] == '"'
                        || cadena[i] == ','
                        || cadena[i] == '\\'
                        || cadena[i] == '\"'
                        || cadena[i] == ';'
                        || cadena[i] == '¡'
                        || cadena[i] == '!'
                        || cadena[i] == '|'
                        || cadena[i] == '-'
                        || cadena[i] == '<'
                        || cadena[i] == '{'
                        || cadena[i] == '}'
                        || cadena[i] == '['
                        || cadena[i] == ']') {
                    return false;
                }
            }
            return hayCaracteresEspeciaesExigidos(cadena);
        }
        
        public boolean hayCaracteresEspeciaesExigidos(char[] cadena) {
            for (int i = 0; i < cadena.length; i++) {
                 if (cadena[i] == '@'
                        || cadena[i] == '#'
                        || cadena[i] == '='
                        || cadena[i] == '?'
                        || cadena[i] == '+'
                        || cadena[i] == '*'
                        || cadena[i] == '_'
                        || cadena[i] == '%'
                        || cadena[i] == '/'
                        || cadena[i] == '.'
                        || cadena[i] == '>'
                        || cadena[i] == ':') {
                    return true;
                }
            }            
            return false;
        }
        
        public boolean ValidarEmail(String email) {
            Pattern p = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
            Matcher m = p.matcher(email);
        
            if (!m.find()) {
                return false;
            } else {
                return true;  
            }
        }

        /**
        * Método para validar el Choque de Materias en la Inscripción
        */
        public String ValidarChoqueHorarios(String CadenaHorarios) throws ParseException {
            String[] listaHorarios = CadenaHorarios.split("\\*");            
            String mensaje = "";
            try {
                //Recorrer todos los Horarios por Asignatura
                for(int i=0;i<listaHorarios.length;i++) {
                    DateFormat dateFormat = new SimpleDateFormat ("HH:mm");
                    String dia =  listaHorarios[i].substring(0, 3);
                    Date horaIni = dateFormat.parse(listaHorarios[i].substring(4, 12));
                    Date horaFin = dateFormat.parse(listaHorarios[i].substring(15, 23));

                    for(int j=0;j<listaHorarios.length;j++) {
                        String diaComp =  listaHorarios[j].substring(0, 3);                        
                        Date horaIniComp = dateFormat.parse(listaHorarios[j].substring(4, 12));
                        Date horaFinComp = dateFormat.parse(listaHorarios[j].substring(15, 23));
                        
                         if (dia.equals(diaComp)  && j!=i) {
                             if ((horaIni.compareTo(horaIniComp)<=0 && horaFin.compareTo(horaFinComp)>=0) || (horaIni.before(horaIniComp) && horaFin.after(horaIniComp))) {
                                mensaje = "<img src=\"/sicoes/recursos/img/error_1.gif\"/>Dos o más Asignaturas coinciden en el horario ("+listaHorarios[j]+"), favor verifique.";
                             }                             
                        }
                    }                               
                }
                return mensaje;
           } catch (ParseException parseException){
                parseException.printStackTrace();
                mensaje = "<img src=\"/sicoes/recursos/img/error_1.gif\"/>Error comparando Horario de Asignaturas, favor contacte al administrador del Sistema.";
                return mensaje;
            }
        }
}


