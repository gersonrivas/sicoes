/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author gerson
 */
public class DaoGraduados extends Graduados {
    public Connection conexion;
    
    /**
     * Constructor
     * @param cedula 
     */
    public DaoGraduados(String cedula) {
        super (cedula);
        this.cedula = cedula;
    }
    
    public boolean BuscarDatosGraduado(Connection conexion) throws SQLException {
        String sql;
        
        sql = "SELECT " +
            " graduado.\"Id\", " +
            " graduado.cedula, " +
            " alumno.apellidos, " +
            " alumno.nombres, " +
            " graduado.id_especial, " +
            " especialidad.nomb_espec, " +                
            " graduado.id_turno, " +
            " turno.nomb_turno, " +
            " graduado.fec_graduacion, " +
            " graduado.libro, " +
            " graduado.tomo, " +
            " graduado.folio, " +
            " graduado.numero, " +
            " graduado.resolucion, " +
            " graduado.fec_acta, " +
            " graduado.fec_firma, " +
            " graduado.reg_grado, " +
            " graduado.aranceles, " +
            " graduado.ident_correo, " +
            " graduado.sede " +
            " FROM " +
            " public.graduado " +
            " INNER JOIN public.alumno " +
            " ON (alumno.cedula = graduado.cedula) " +
            " LEFT OUTER JOIN especialidad " +
            " ON (especialidad.\"Id\" = graduado.id_especial) " +
            " LEFT OUTER JOIN turno " +
            " ON (turno.\"Id\" = graduado.id_turno) " +
            "WHERE cedula = '"+ cedula +"';";
        
        //System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            
            this.setIdGraduado(Integer.parseInt(rs.getString("Id")));
            this.setApellidos(rs.getString("apellidos"));
            this.setNombres(rs.getString("nombres"));
            this.setCodEspecialidad(rs.getString("id_espec"));
            this.setEspecialidad(rs.getString("nomb_espec"));           
            this.setTurno(rs.getString("nomb_turno"));
            this.setCodTurno(rs.getString("id_turno"));
            this.setFecGraduacion(rs.getString("fec_graduacion"));
            this.setLibro(rs.getString("libro"));
            this.setTomo(rs.getString("tomo"));
            this.setFolio(rs.getString("folio"));    
            this.setNumero(rs.getString("numero"));
            this.setResolucion(rs.getString("resolucion")); 
            this.setFecActa(rs.getString("fec_acta"));
            this.setFecFirma(rs.getString("fec_firma"));
            this.setRegGrado(rs.getString("reg_grado"));
            this.setAranceles(rs.getString("aranceles"));
            this.setIdentCorreo(rs.getString("ident_correo"));
            this.setSede(rs.getString("sede"));
                        
            return true;
        } else {
            return false;
        }
    }


    public String ListarGraduados(Connection conexion) throws SQLException {
        String sql;
        String listado = "";
        
        sql = "SELECT " +
            " graduado.\"Id\", " +
            " graduado.cedula, " +
            " alumno.apellidos, " +
            " alumno.nombres, " +
            " graduado.id_especial, " +
            " especialidad.nomb_espec, " +                
            " graduado.id_turno, " +
            " turno.nomb_turno, " +
            " graduado.fec_graduacion, " +
            " graduado.libro, " +
            " graduado.tomo, " +
            " graduado.folio, " +
            " graduado.numero, " +
            " graduado.resolucion, " +
            " graduado.fec_acta, " +
            " graduado.fec_firma, " +
            " graduado.reg_grado, " +
            " graduado.aranceles, " +
            " graduado.ident_correo, " +
            " graduado.sede " +
            " FROM " +
            " public.graduado " +
            " INNER JOIN public.alumno " +
            " ON (alumno.cedula = graduado.cedula) " +
            " LEFT OUTER JOIN especialidad " +
            " ON (especialidad.\"Id\" = graduado.id_especial) " +
            " LEFT OUTER JOIN turno " +
            " ON (turno.\"Id\" = graduado.id_turno) " +
            " WHERE graduado.sede = '"+ sede +"' " +
            " AND graduado.id_especial = '"+ codEspecialidad +"' " +
            " AND graduado.fec_graduacion = '"+ fecGraduacion + "' ;";
        
        System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        listado = "<tr align=\"center\"> " +
                                "<th align=\"center\"> " +                                    
                                "   <b> CEDULA </b>" +
                                "</th> " +
                                "<th align=\"center\"> " +
                                "   <b> APELLIDOS </b> " +
                                "</th> " +
                                "<th align=\"center\"> " +
                                "  <b>  NOMBRES </b> " +
                                "</th> " +
                                " <th align=\"center\"> " +
                                "   <b> ESPECIALIDAD </b> " +
                                "</th> " +
                                "<th align=\"center\"> " +
                                "  <b> TURNO </b> " +
                                "</th> " +
                                "<th align=\"center\"> " +
                                "  <b> DETALLE </b> " +
                                "</th> " +
                            "</tr>";
        
        if (rs.next()) {
            
            listado = listado + "<tr align=\"center\">" +
                                    "<td align=\"center\"> " +
                                        rs.getString("cedula") +
                                    "</td>" +
                                    "<td align=\"center\"> " +
                                        rs.getString("apellidos") +
                                    "</td>" +
                                    "<td align=\"center\"> " +
                                        rs.getString("nombres") +
                                    "</td>" +
                                    "<td align=\"center\"> " +
                                        rs.getString("nomb_espec") +
                                    "</td>" +
                                    "<td align=\"center\"> " +
                                        rs.getString("nomb_turno") +
                                    "</td>" +
                                    "<td align=\"center\"> " +
                                        "Ver" +
                                    "</td>" +
                                "</tr>";
                    
                        
        }
        return listado;
    }

    
    public String BuscarPeriodos(Connection conexion)  throws SQLException {
        String sql;
        String selectPeriodo = "";
        String optionsPeriodo = "";
        int i=0;
        
        sql = " SELECT DISTINCT " +
            " inscripcion_alumno.periodo " +
            " FROM " +
            " public.inscripcion_alumno; ";
        
        //System.out.println(sql);
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectPeriodo = "<select name=\"periodo\" onchange=\"form.submit()\" id=\"periodo\" >"; 
        optionsPeriodo =  "<option value=\"lbElige\" selected>Elige";
        while (rs.next()) {
            i++;
            optionsPeriodo = optionsPeriodo + "<option value=\"lb"+rs.getString("periodo")+"\" >"+rs.getString("periodo");
        }
        if (i==0) {
            optionsPeriodo =  "<option value=\"lbSinInscripcion\" selected>Sin Inscripción";
        }
        selectPeriodo = selectPeriodo + optionsPeriodo + "</select>";

        return selectPeriodo;
    }
    
    public String BuscarSede(Connection conexion)  throws SQLException {
        String sql;
        String selectSeccion = "";
        String optionsSeccion = "";
        int i=0;
        
        sql = " SELECT DISTINCT " +
            " sede.cod_sede, " +
            " sede.descrip_sede " +
            " FROM " +
            " public.graduado INNER JOIN public.sede " +
            " ON (sede.cod_sede = graduado.sede);";
        
        //System.out.println(sql);
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSeccion = "<select name=\"sede\" id=\"sede\" >"; 
        selectSeccion = "<select name=\"sede\" onchange=\"form.submit()\" id=\"sede\" >"; 
        
        //optionsSeccion =  "<option value=\"lbElige\" selected>Elige";        
        
        while (rs.next()) {
            i++;
            if (rs.getString("cod_sede").equals(sede)) {
                optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("cod_sede")+"\" selected >"+rs.getString("descrip_sede");
            } else {
                optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("cod_sede")+"\"  >"+rs.getString("descrip_sede");
            }
            //optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("cod_sede")+"\" >"+rs.getString("descrip_sede");
        }
        
        if (sede=="" || sede==null) {
            optionsSeccion =  "<option value=\"lbElige\" selected>Elige" + optionsSeccion;
        } 
        if (i==0) {
            optionsSeccion =  "<option value=\"lbSinSede\" selected>Sin Sede";
        }        
        
        selectSeccion = selectSeccion + optionsSeccion + "</select>";

        return selectSeccion;
    }
    
    public String BuscarEspecialidad(Connection conexion)  throws SQLException {
        String sql;
        String selectSeccion = "";
        String optionsSeccion = "";
        int i=0;
        
        sql = " SELECT DISTINCT " +
            " especialidad.\"Id\", " +
            " especialidad.nomb_espec " +
            " FROM " +
            " public.especialidad INNER JOIN public.graduado " +
            " ON (especialidad.\"Id\" = graduado.id_especial) " +
            " WHERE graduado.sede = '" + sede + "' " +
            " ORDER BY especialidad.nomb_espec;";            
        
        //System.out.println(sql);
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSeccion = "<select name=\"especialidad\" onchange=\"form.submit()\" id=\"especialidad\" >"; 
        optionsSeccion =  "<option value=\"lb0\" selected >Elige";
        while (rs.next()) {
            i++;
            if (rs.getString("Id").equals(codEspecialidad)) {
                optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("Id")+"\" selected >"+rs.getString("nomb_espec");
            } else {
                optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("Id")+"\"  >"+rs.getString("nomb_espec");
            }
            
            //optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("Id").toString()+"\" >"+rs.getString("nomb_espec");
        }

        if (codEspecialidad=="" || codEspecialidad==null) {
            optionsSeccion =  "<option value=\"lb0\" selected>Elige" + optionsSeccion;
        } 
        if (i==0) {
            optionsSeccion =  "<option value=\"lb0\" selected>Sin Especialidad";
        }

        selectSeccion = selectSeccion + optionsSeccion + "</select>";

        return selectSeccion;
    }

    public String BuscarFechaGraduacion(Connection conexion)  throws SQLException {
        String sql;
        String selectSeccion = "";
        String optionsSeccion = "";
        int i=0;
                
        sql = " SELECT DISTINCT " +
            " graduado.fec_graduacion " +
            " FROM " +
            " public.graduado " +
            " WHERE graduado.sede = '" + sede + "' " +
            " AND graduado.id_especial = " + codEspecialidad + " " +
            " ORDER BY graduado.fec_graduacion;";
        
        //System.out.println(sql);
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSeccion = "<select name=\"fecha_graduacion\" onchange=\"form.submit()\" id=\"fec_graduacion\" >"; 
        optionsSeccion =  "<option value=\"lbElige\" selected>Elige";
        while (rs.next()) {
            i++;
            if (rs.getString("fec_graduacion").equals(fecGraduacion)) {
                optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("fec_graduacion")+"\" selected >"+rs.getString("fec_graduacion");
            } else {
                optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("fec_graduacion")+"\"  >"+rs.getString("fec_graduacion");
            }
            
            //optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("fec_graduacion")+"\" >"+rs.getString("fec_graduacion");
        }
        if (i==0) {
            optionsSeccion =  "<option value=\"lb0\" selected>Sin Fecha de Graduación";
        }
        selectSeccion = selectSeccion + optionsSeccion + "</select>";

        return selectSeccion;
    }
}
