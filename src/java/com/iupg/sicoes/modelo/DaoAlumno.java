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
 * Clase que define los métodos para la manipulación de los Datos del Alumno.
 * @author Gerson Rivas
 * @version: 1.0
 * 
 */
public class DaoAlumno extends Alumno {
    public Connection conexion;
    
    /**
     * Constructor
     * @param cedula 
     */
    public DaoAlumno(String cedula) {
        super (cedula);
        this.cedula = cedula;
    }

    public boolean BuscarDatosAlumno(Connection conexion) throws SQLException {
        String sql;
        
        sql = "SELECT " +
            " alumno.\"Id\", " +
            " alumno.cedula, " +
            " alumno.apellidos, " +
            " alumno.nombres, " +
            " alumno.nacionalidad, " +
            " alumno.ident_correo, " +
            " alumno.tel_hab, " +
            " alumno.tel_ofc, " +
            " alumno.tel_cel, " +
            " alumno.edo_civil, " +
            " alumno.sexo, " +
            " alumno.fec_nac, " +
            " alumno.id_usu, " +
            " alumno.empresa, " +
            " alumno.sede,  " +
            " alumno.nro_nsi,  " +
            " alumno.lugar_nac,  " +
            " especialidad.\"Id\" AS id_espec, " +
            " especialidad.nomb_espec,  " +
            " alumno.direccion,  " +
            " turno.\"Id\" AS id_turno," +
            " turno.nomb_turno " +
            " FROM " +
            "  public.alumno " +
            " LEFT OUTER JOIN especialidad " +
            " ON (especialidad.\"Id\" = alumno.id_especial) " +
            " LEFT OUTER JOIN turno " +
            " ON (turno.\"Id\" = alumno.id_turno) " +
            "WHERE cedula = '"+ cedula +"';";
        
        //System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            
            this.setIdAlumno(Integer.parseInt(rs.getString("Id")));
            this.setApellidos(rs.getString("apellidos"));
            this.setNombres(rs.getString("nombres"));
            this.setNacionalidad(rs.getString("nacionalidad"));
            this.setIdent_correo(rs.getString("ident_correo"));
            this.setTel_hab(rs.getString("tel_hab"));
            this.setTel_ofc(rs.getString("tel_ofc"));
            this.setTel_cel(rs.getString("tel_cel"));
            this.setEdo_civil(rs.getString("edo_civil"));    
            this.setSexo(rs.getString("sexo"));
            this.setFec_nac(rs.getString("fec_nac")); 
            this.setEmpresa(rs.getString("empresa"));
            this.setSede(rs.getString("sede"));
            this.setNro_nsi(rs.getString("nro_nsi"));
            this.setLugar_nac(rs.getString("lugar_nac"));
            //this.setCodEspecialidad(Integer.parseInt(rs.getInt("id_espec")));
            this.setCodEspecialidad(rs.getString("id_espec"));
            this.setEspecialidad(rs.getString("nomb_espec"));
            this.setDireccion(rs.getString("direccion"));
            this.setTurno(rs.getString("nomb_turno"));
            //this.setCodTurno(Integer.valueOf(rs.getString("id_turno")));
            this.setCodTurno(rs.getString("id_turno"));
            //this.setPeriodo(rs.getString("periodo"));
                        
            return true;
        } else {
            return false;
        }
    }    
    
    /**
     * Método que busca la inscripción administrativa del alumno en el perído actual.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public boolean BuscarPeriodoInscriptoAdmin(Connection conexion) throws SQLException {
        String sql;
        
        sql = "SELECT inscripcion_alumno.\"Id\", cedula, periodo, inscripcion_alumno.estatus, id_usuario, fec_hor_act " +
             " FROM   public.inscripcion_alumno " +
             " INNER JOIN periodo USING (periodo) " +
             " WHERE cedula = '"+cedula+"' " + 
             " AND inscripcion_alumno.estatus = 'A' " +
             " AND (NOW()::date BETWEEN fec_ini_insc::date AND fec_fin_insc::date); "   ;        
             
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {            
            this.setPeriodo(rs.getString("periodo"));    
            //this.setNro_insc(Integer.parseInt(rs.getString("nro_insc")));
            return true;
        } else {
            return false;
        }
    }    
         
    /**
     * Método que busca el período inscripto del alumno en el rango de fecha, para el retiro y adición de Asignaturas.
     * @param conexion
     * @param estatus Estatus de la Inscripción, Ej: R=Inscripción con Retiro de Asignaturas, A=Inscripción Administrativa, C=Iscripción Académica, D=Inscripción con adición de Asignaturas. 
     * @return
     * @throws SQLException 
     */
    public boolean BuscarPeriodoInscriptoAcademico(Connection conexion, String estatus) throws SQLException {
        String sql;
        
        sql = "SELECT inscripcion_alumno.\"Id\", cedula, periodo, inscripcion_alumno.estatus, id_usuario, fec_hor_act " +
             " FROM   public.inscripcion_alumno " +
             " INNER JOIN periodo USING (periodo) " +
             " WHERE cedula = '"+cedula+"' " + 
             " AND inscripcion_alumno.estatus IN (" +estatus+ ") " +
             " AND (NOW()::date BETWEEN fec_ini_raa::date AND fec_fin_raa::date); "   ;        
                
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {            
            this.setPeriodo(rs.getString("periodo"));    
            //this.setNro_insc(Integer.parseInt(rs.getString("nro_insc")));
            return true;
        } else {
            return false;
        }
    }
   
    /**
     * Método que busca las materias pendientes por cursar del alumno.
     * @param conexion
     * @return
     * @throws SQLException 
     */    
    public String BuscarMateriasPorInscribir(Connection conexion)  throws SQLException {
        String sql;
        String listaMaterias = "";
        
        //Buscar las asignaturas que no estén aprobadas y que no prelen
        sql = " SELECT me.cod_mat, descrip_mat, me.uc, me.id_espec, ";

        if (codTurno.equals("4")) { //Sabatino
            sql = sql + " me.termino as sem_ter ";
        } else {
            sql = sql + " me.semestre as sem_ter ";
        }
        sql = sql + " FROM materia m, materia_espec me " +
              " WHERE m.cod_mat = me.cod_mat " +
              " AND me.id_espec = '"+codEspecialidad+"' " +
              " AND me.pensum_activo = 'S' " +
              " AND me.cod_mat IN (SELECT cod_mat FROM materia_espec me2 WHERE me2.id_espec = '"+codEspecialidad+"' AND me2.pensum_activo = 'S') " +
              " AND me.cod_mat NOT IN (SELECT cod_mat FROM alumno_materia WHERE cedula = '"+cedula+"' AND fun_nota_aprobada(1,nota))  " +
              " AND me.cod_mat NOT IN (SELECT mp.cod_mat_prela FROM alumno_materia am RIGHT OUTER JOIN materia_prelacion mp ON (mp.cod_mat = am.cod_mat AND am.cedula = '"+cedula+"' AND mp.id_espec = '"+codEspecialidad+"' AND fun_nota_aprobada(1,nota)) WHERE mp.id_espec = '"+codEspecialidad+"' AND cedula IS NULL) " +
                
              " AND me.cod_mat NOT IN (SELECT mpuc.cod_mat FROM materia_prelacion_uc mpuc WHERE mpuc.cod_mat = me.cod_mat AND mpuc.id_espec = me.id_espec AND mpuc.id_turno = '"+codTurno+"' AND fun_uc_aprobadas('"+cedula+"','"+codEspecialidad+"') < mpuc.uc_min_aprob) " +
              
              " ORDER BY sem_ter; ";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            listaMaterias = listaMaterias + "<tr><td align=\"center\"> <input type=\"checkbox\" name=\"materia\" id=\""+rs.getString("cod_mat")+"\" value=\""+rs.getString("cod_mat")+"\" onclick=\"deshabilitaSeccion(this);\">"+rs.getString("descrip_mat")+"</td><td align=\"center\">"+rs.getString("sem_ter")+"</td><td align=\"center\">"+BuscarSeccionAula(conexion, rs.getString("cod_mat"))+"</td><td align=\"center\"><label id=\"lb"+rs.getString("cod_mat")+"\" >Seleccione la sección.</label></td><td align=\"center\">"+rs.getString("uc")+"</td> </tr>";            
        }
        return listaMaterias;
    }
    
    /**
     * Método que busca las materias inscritas del alumno en un período.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarMateriasInscritas(Connection conexion)  throws SQLException {
        String sql;
        String listaMaterias = "";
        
        //Buscar las asignaturas inscritas
        sql = "SELECT me.cod_mat, descrip_mat, uc, me.id_espec, seccion, aula, dia||' '||to_char(MIN(hora_ini),'HH12:MI am')||' - '||to_char(MAX(hora_fin),'HH12:MI am') AS bloqueHorario, ph.id_mat_espec_aula_secc_tur, ";

        if (codTurno.equals("4")) { //Sabatino
            sql = sql + " me.termino as sem_ter ";
        } else {
            sql = sql + " me.semestre as sem_ter ";
        }
        sql = sql + "FROM inscripcion_alumno_detalle iad " +
            "INNER JOIN materia_espec_aula_seccion_turno meast ON (meast.\"Id\" = iad.id_mat_espec_aula_secc_tur) " +
            "INNER JOIN materia_espec me ON (me.cod_mat = iad.cod_mat and me.id_espec = meast.id_espec) " +
            "INNER JOIN materia m ON (m.cod_mat = me.cod_mat) " +
            "INNER JOIN profesor_horario ph ON (ph.id_mat_espec_aula_secc_tur = iad.id_mat_espec_aula_secc_tur) " +
            "INNER JOIN horario h ON (h.\"Id\" = ph.id_horario) " +
            "WHERE iad.cedula = '"+cedula+"' " +
            "AND meast.id_espec = '"+codEspecialidad+"' " +
            "AND iad.periodo = '"+periodo+"' " +
            "AND iad.estatus = 'I' " +
            "GROUP BY me.cod_mat, descrip_mat, uc, me.id_espec, sem_ter, seccion, dia, aula, ph.id_mat_espec_aula_secc_tur;";
         
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            listaMaterias = listaMaterias + "<tr><td> <input type=\"checkbox\" name=\"materia\" id=\""+rs.getString("cod_mat")+"\" value=\""+rs.getString("cod_mat")+"\" onclick=\"deshabilitaSeccion(this);\">"+rs.getString("descrip_mat")+"</td><td align=\"center\">"+rs.getString("sem_ter")+"</td><td align=\"center\">"+rs.getString("seccion")+"</td><td>"+rs.getString("bloqueHorario")+"</td><td align=\"center\">"+rs.getString("uc")+"</td> </tr>";            
        }
        return listaMaterias;
    }
    /**
     * Método para listar los horarios de las materias inscritas para poder valdarlos
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarHorariosMateriasInscritas(Connection conexion)  throws SQLException {
        String sql;
        String listaHorarios = "";
        
        //Buscar las asignaturas inscritas
        sql = "SELECT iad.cod_mat, dia||' '||to_char(MIN(hora_ini),'HH12:MI am')||' - '||to_char(MAX(hora_fin),'HH12:MI am') AS bloqueHorario ";
       
        sql = sql + "FROM inscripcion_alumno_detalle iad " +
            "INNER JOIN materia_espec_aula_seccion_turno meast ON (meast.\"Id\" = iad.id_mat_espec_aula_secc_tur) " +
            "INNER JOIN materia_espec me ON (me.cod_mat = iad.cod_mat and me.id_espec = meast.id_espec) " +
            "INNER JOIN materia m ON (m.cod_mat = me.cod_mat) " +
            "INNER JOIN profesor_horario ph ON (ph.id_mat_espec_aula_secc_tur = iad.id_mat_espec_aula_secc_tur) " +
            "INNER JOIN horario h ON (h.\"Id\" = ph.id_horario) " +
            "WHERE iad.cedula = '"+cedula+"' " +
            "AND meast.id_espec = '"+codEspecialidad+"' " +
            "AND iad.periodo = '"+periodo+"' " +
            "AND iad.estatus = 'I' " +
            "GROUP BY iad.cod_mat, dia;";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            if (listaHorarios=="") {
                listaHorarios = rs.getString("bloqueHorario");
            } else {
                listaHorarios = listaHorarios + "*" +rs.getString("bloqueHorario");
            }
            
        }
        return listaHorarios;
    }
    
    /**
     * Método que busca las materias que puede adicionar a la inscripción.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarMateriasPorAdicionar(Connection conexion)  throws SQLException {
        String sql;
        String listaMaterias = "";
        
        //Buscar las asignaturas que no estén aprobadas y que no prelen
        sql = " SELECT me.cod_mat, descrip_mat, me.uc, me.id_espec, ";

        if (codTurno.equals("4")) { //Sabatino
            sql = sql + " me.termino as sem_ter ";
        } else {
            sql = sql + " me.semestre as sem_ter ";
        }
        sql = sql + " FROM materia m, materia_espec me " +
              " WHERE m.cod_mat = me.cod_mat " +
              " AND me.id_espec = '"+codEspecialidad+"' " +
              " AND me.pensum_activo = 'S' " +
              " AND me.cod_mat IN (SELECT cod_mat FROM materia_espec me2 WHERE me2.id_espec = '"+codEspecialidad+"' AND me2.pensum_activo = 'S') " +
              " AND me.cod_mat NOT IN (SELECT cod_mat FROM alumno_materia WHERE cedula = '"+cedula+"' AND fun_nota_aprobada(1,nota))  " +
              " AND me.cod_mat NOT IN (SELECT mp.cod_mat_prela FROM alumno_materia am RIGHT OUTER JOIN materia_prelacion mp ON (mp.cod_mat = am.cod_mat AND am.cedula = '"+cedula+"' AND mp.id_espec = '"+codEspecialidad+"' AND fun_nota_aprobada(1,nota)) WHERE mp.id_espec = '"+codEspecialidad+"' AND cedula IS NULL) " +
                
              " AND me.cod_mat NOT IN (SELECT mpuc.cod_mat FROM materia_prelacion_uc mpuc WHERE mpuc.cod_mat = me.cod_mat AND mpuc.id_espec = me.id_espec AND mpuc.id_turno = '"+codTurno+"' AND fun_uc_aprobadas('"+cedula+"','"+codEspecialidad+"') < mpuc.uc_min_aprob) " +
                
              " AND me.cod_mat NOT IN (SELECT iad.cod_mat FROM inscripcion_alumno_detalle iad WHERE iad.cod_mat = me.cod_mat AND iad.cedula = '"+cedula+"' AND iad.periodo = '"+periodo+"') " +
              
              " ORDER BY sem_ter; ";
       
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            listaMaterias = listaMaterias + "<tr><td> <input type=\"checkbox\" name=\"materia\" id=\""+rs.getString("cod_mat")+"\" value=\""+rs.getString("cod_mat")+"\" onclick=\"deshabilitaSeccion(this);\">"+rs.getString("descrip_mat")+"</td><td align=\"center\">"+rs.getString("sem_ter")+"</td><td>"+BuscarSeccionAula(conexion, rs.getString("cod_mat"))+"</td><td><label id=\"lb"+rs.getString("cod_mat")+"\" >Seleccione la sección.</label></td><td align=\"center\">"+rs.getString("uc")+"</td> </tr>";            
        }
        return listaMaterias;
    }
    
    
    public String BuscarSeccionAula(Connection conexion, String codigoMateria)  throws SQLException {
        String sql;
        String selectSeccion = "";
        String optionsSeccion = "";
        int i=0;
        sql=" SELECT \"Id\", periodo, cod_mat, id_espec, seccion, aula, sede " +
            " FROM materia_espec_aula_seccion_turno " +
            " WHERE periodo = '"+periodo+"' " +
            " AND cod_mat = '"+codigoMateria+"' " +
            " AND id_espec = "+codEspecialidad+" " +
            " AND id_turno = "+codTurno+" " +
            " AND sede = '"+sede+"';";
        
        //System.out.println(sql);
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSeccion = "<select name=\"turno"+codigoMateria+"\" id=\"sec"+codigoMateria+"\" onclick=\"funSeccion(this)\" onchange=\"seleccionSeccion(this)\" disabled>"; 
        //listaSeccion = listaSeccion + "<option value=\"lb"+codigoMateria+"\" selected > ";
        optionsSeccion =  "<option value=\"lb"+codigoMateria+"|Seleccione la sección.\" selected >Elige";
        while (rs.next()) {
            i++;
            optionsSeccion = optionsSeccion + "<option value=\"lb"+codigoMateria+"|"+rs.getString("Id")+" / "+"Aula "+rs.getString("aula")+BuscarHorario(conexion, rs.getString("Id"))+"\" "+VerificarCapacidadAula(conexion, rs.getString("Id"))+rs.getString("seccion");
            //listaSeccion = listaSeccion + "<option value=\""+codigoMateria+i+"\"> "+rs.getString("seccion");            
        }
        if (i==0) {
            optionsSeccion =  "<option value=\"lb"+codigoMateria+"|No hay sección asignada.\" selected>Sin Asignar";
        }
        selectSeccion = selectSeccion + optionsSeccion + "</select>";
        
        return selectSeccion;
    }
    
    public String BuscarHorario(Connection conexion, String id_esp_sec_tur_aul) throws SQLException {
        String sql;
        String listaHorarios = "";

        sql = " SELECT dia||' '||to_char(MIN(hora_ini),'HH24:MI am')||' - '||to_char(MAX(hora_fin),'HH24:MI am') AS bloqueHorario " +
              " FROM horario h " +
              " INNER JOIN profesor_horario ph " +
              " ON (h.\"Id\" = ph.id_horario)  " +
              " WHERE id_mat_espec_aula_secc_tur = " + id_esp_sec_tur_aul +
              " GROUP BY dia;";
        
        //<select name=\"horario\"><option value=\"1\">Lun 7:00-7:45 7:45-8:30</option><option value=\"2\">Mie 7:00-7:45 7:45-8:30</option><option value=\"3\">vie 8:45-9:30 9:30-10:15</option></select>
        PreparedStatement ps;
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            //listaSeccion = listaSeccion + "<option value=\""+codigoMateria+i+"\"> "+rs.getString("seccion");
            listaHorarios = listaHorarios + "|" + rs.getString("bloqueHorario");
        }
        
        if (listaHorarios=="") {
            listaHorarios = " Cargando horario del profesor.";
        }

        return listaHorarios;        
    }

    public String VerificarCapacidadAula(Connection conexion, String id_esp_sec_tur_aul) throws SQLException {
        String sql;
        
        sql = " SELECT (capacidad - count(iad.cedula)) as disponibilidad " +
              " FROM aula " +
              " INNER JOIN materia_espec_aula_seccion_turno meast on (aula.aula=meast.aula) " +
              " LEFT OUTER JOIN inscripcion_alumno_detalle iad on (iad.id_mat_espec_aula_secc_tur = meast.\"Id\" AND iad.estatus <> 'R') " +
              " WHERE meast.periodo||meast.cod_mat||meast.seccion||meast.aula||meast.sede IN " +
                    "(SELECT periodo||cod_mat||seccion||aula||sede FROM materia_espec_aula_seccion_turno WHERE \"Id\" = " + id_esp_sec_tur_aul + ") "+
              " GROUP BY capacidad " +
              " HAVING (capacidad - count(iad.cedula)) >= 1;";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return ">";
        } else {
            return "disabled>Sin capacidad ";
        }
    }
    
    
    public String VerificarUCInscritas(Connection conexion, String listaMaterias, String id_espec)  throws SQLException {
        String sql;
        
        sql = "SELECT  SUM(uc) AS cantidadUC" +
            " FROM " +
            " materia_espec " +
            " WHERE cod_mat IN ("+listaMaterias+") " +
            " AND id_espec = "+id_espec+";";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getString("cantidadUC");
        } else {
            return "0";
        }
    }
    
    public String VerificarUCMateriasInscritas(Connection conexion)  throws SQLException {
        String sql;
        
        //Buscar las asignaturas inscritas
        sql = "SELECT SUM(uc) AS cantidadUC " +
            "FROM inscripcion_alumno_detalle iad " +
            "INNER JOIN materia_espec me ON (me.cod_mat = iad.cod_mat AND me.id_espec = '"+codEspecialidad+"') " +
            "WHERE iad.cedula = '"+cedula+"' " +
            "AND iad.periodo = '"+periodo+"' " +
            "AND iad.estatus = 'I'; ";
                 
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return rs.getString("cantidadUC");
        } else {
            return "0";
        }
    }
    
    
    public boolean ValidaRegla (Connection conexion, String parametro, String comparadorLogicoValor) throws SQLException {
        String sql;
        
        sql = " SELECT " +
              "  \"Id\", " +
              "  parametro, " +
              "  descripcion, " +
              "  valor, " +
              "  comparador " +
              " FROM " +
              "  reglas.inscripcion " +
              " WHERE parametro = '" + parametro +"' " +
              " AND " + comparadorLogicoValor+";";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            return true;
        } else {
            return false;
        }        
    }
    
    public void GuardarInscripcionAlumno(Connection conexion, String materias, String secciones) throws SQLException {
        String sql = "";
        String[] listaMaterias = materias.split(",");
        String[] listaSecciones = secciones.split(",");
        Date fecha = new Date();
        String fechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fecha);
        
        sql=" UPDATE inscripcion_alumno SET estatus = 'C', fec_hor_act = '{"+fechaHora+"}' WHERE cedula = "+cedula+" AND periodo = '"+periodo+"'; ";
        
        for(int i=0;i<listaMaterias.length;i++) {
            sql = sql + " INSERT INTO inscripcion_alumno_detalle (cedula, cod_mat, id_mat_espec_aula_secc_tur, periodo) VALUES ("+cedula+", "+listaMaterias[i]+", "+listaSecciones[i]+", '"+periodo+ "' ); ";
        }                
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.executeUpdate();
    }
    
    public void GuardarAdicionInscripcionAlumno(Connection conexion, String materias, String secciones) throws SQLException {
        String sql = "";
        String[] listaMaterias = materias.split(",");
        String[] listaSecciones = secciones.split(",");
        Date fecha = new Date();
        String fechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fecha);
        
        sql=" UPDATE inscripcion_alumno SET estatus = 'D', fec_hor_act = '{"+fechaHora+"}' WHERE cedula = "+cedula+" AND periodo = '"+periodo+"'; ";
        
        for(int i=0;i<listaMaterias.length;i++) {
            sql = sql + " INSERT INTO inscripcion_alumno_detalle (cedula, cod_mat, id_mat_espec_aula_secc_tur, periodo, fec_hor_act, estatus) VALUES ("+cedula+", "+listaMaterias[i]+", "+listaSecciones[i]+", '"+periodo+ "', '{"+fechaHora+"}', 'D'); ";
        }                
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.executeUpdate();
    }
    

    public String EliminarInscripcionAlumno(Connection conexion) throws SQLException {
        String sql = "";
        
        try {
            sql=" DELETE FROM inscripcion_alumno_detalle WHERE cedula = "+cedula+" AND periodo = '"+periodo+"'; ";
            sql=sql+" DELETE FROM inscripcion_alumno WHERE cedula = "+cedula+" AND periodo = '"+periodo+"'; ";
 
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            return "Inscripción Eliminada Satisfactoriamente.";
            
        } catch (Exception e) {
            conexion.close();
            return "Error: Eliminando la Inscripción -->"+e.getMessage();
        }
    }


    /**
     * Método para actualizar el estatus de las materias retiradas y adicionadas.
     * @param conexion
     * @param materias
     * @throws SQLException 
     */
    public void GuardarRetiroMateriasAlumno(Connection conexion, String materias) throws SQLException {
        String sql = "";
        //String[] listaSecciones = secciones.split(",");
        Date fecha = new Date();
        String fechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(fecha);
        
        sql=" UPDATE inscripcion_alumno SET estatus = 'R', fec_hor_act = '{"+fechaHora+"}' WHERE cedula = "+cedula+" AND periodo = '"+periodo+"'; ";
        
        sql = sql + " UPDATE inscripcion_alumno_detalle SET estatus = 'R' , fec_hor_act = '{"+fechaHora+"}' WHERE cedula = '"+cedula+"' AND cod_mat IN ("+materias+");";
                
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.executeUpdate();
    }
    
    public String BuscarPeriodosInscritos(Connection conexion)  throws SQLException {        
        String selectPeriodo = "";
        String optionsPeriodo = "";
        int i=0;
        
        selectPeriodo = "<select name=\"periodo\"  onchange=\"form.submit()\" id=\"periodo\" >"; 
        if (cedula!="" && cedula!=null) { 
            String sql = " SELECT DISTINCT " +
                " ia.periodo, " +
                " ia.estatus, " +
                " ia.cedula " +
                " FROM " +
                " public.inscripcion_alumno ia " +
                " INNER JOIN public.inscripcion_alumno_detalle iad " +
                " ON (ia.cedula = iad.cedula AND ia.periodo = iad.periodo) " +    
                " WHERE ia.cedula = '"+cedula+"'; ";
        
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();        
            
            optionsPeriodo =  "<option value=\"lbElige\" selected>Elige";
            while (rs.next()) {
                i++;
                if (periodo.equals(rs.getString("periodo"))) {
                    optionsPeriodo = optionsPeriodo + "<option value=\"lb"+rs.getString("periodo")+"\" selected>"+rs.getString("periodo");
                }
                else {    
                    optionsPeriodo = optionsPeriodo + "<option value=\"lb"+rs.getString("periodo")+"\" >"+rs.getString("periodo");
                }
            }
        }
        
        if (i==0) {
            optionsPeriodo =  "<option value=\"lbSinInscripcion\" selected>Sin Inscripción";
        }
        
        selectPeriodo = selectPeriodo + optionsPeriodo + "</select>";

        return selectPeriodo;
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
        
        selectPeriodo = "<select name=\"periodo\" id=\"periodo\" >"; 
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
        
        sql = " SELECT " +
            " sede.cod_sede, " +
            " sede.descrip_sede " +
            " FROM " +
            " public.sede; ";
        
        //System.out.println(sql);
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSeccion = "<select name=\"sede\" id=\"sede\" >"; 
        optionsSeccion =  "<option value=\"lbElige\" selected>Elige";
        while (rs.next()) {
            i++;
            optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("cod_sede")+"\" >"+rs.getString("descrip_sede");
        }
        if (i==0) {
            optionsSeccion =  "<option value=\"lbSinInscripcion\" selected>Sin Inscripción";
        }
        selectSeccion = selectSeccion + optionsSeccion + "</select>";

        return selectSeccion;
    }
    
    public String ConstruirMensajeUCPermitidas(Connection conexion, String parametro)  throws SQLException {
        String sql;
        String mensaje = "";
        
        sql = " SELECT " +
              "  \"Id\", " +
              "  parametro, " +
              "  descripcion, " +
              "  valor, " +
              "  comparador " +
              " FROM " +
              "  reglas.inscripcion " +
              " WHERE parametro = '" + parametro +"' ;";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            mensaje = "Seleccione las asignaturas cuya sumatoria de Unidades de Crédito \"UC.\" no sobrepase de "+rs.getString("valor")+" créditos.";
        }
        
        return mensaje;
    }
    
    public String BuscarCodigoValidacionTurno() {
        String codigoValidacion = "";
        //Turno Sabatino
        if (!codTurno.equals("4")) {
            codigoValidacion = "IA01";
        } else {
            codigoValidacion = "IA02";                                    
        }
        return codigoValidacion;
    }

    /**
     * Método para buscar las especialidades que ha cursado el alumno.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarEspecialidadesAlumno(Connection conexion)  throws SQLException {
        String sql;
        String selectEspecialidad = "";
        String optionsEspecialidades = "";
        int i=0;

        sql = "SELECT DISTINCT am.id_espec, e.nomb_espec " +
            "FROM alumno_materia am INNER JOIN " +
            "especialidad e ON (e.\"Id\"=am.id_espec) " +
            "WHERE cedula = " + cedula + ";";

        //System.out.println(sql);
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectEspecialidad = "<select name=\"especialidad\"  onchange=\"form.submit()\" id=\"especialidad\" >"; 
        //listaSeccion = listaSeccion + "<option value=\"lb"+codigoMateria+"\" selected > ";
        while (rs.next()) {
            i++;
            if (rs.getString("nomb_espec").equals(especialidad)) {
                optionsEspecialidades = optionsEspecialidades + "<option value=\"lb"+rs.getString("id_espec")+"\" selected >"+rs.getString("nomb_espec");
            } else {
                optionsEspecialidades = optionsEspecialidades + "<option value=\"lb"+rs.getString("id_espec")+"\"  >"+rs.getString("nomb_espec");
            }
        }
        
        if (especialidad=="" || especialidad==null) {
            optionsEspecialidades =  "<option value=\"lbElige\" selected>Elige" + optionsEspecialidades;
        } 
        if (i==0) {
            optionsEspecialidades =  "<option value=\"lbSinEspecialidad\" selected>Sin Especialidad";
        }
        selectEspecialidad = selectEspecialidad + optionsEspecialidades + "</select>";

        return selectEspecialidad;
    }
    


    /**
     * Método que busca la inscripción administrativa del alumno en el perído actual.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarPeriodoActualDelAlumno(Connection conexion) throws SQLException {
        String sql;
        String selectPeriodo = "";
        String optionsPeriodo = "";
        int i=0;
        
        selectPeriodo = "<select name=\"periodo\"  onchange=\"form.submit()\" id=\"periodo\" >"; 
        if (cedula!="" && cedula!=null) {
            sql = "SELECT " +
                  "p.periodo, " +
                  "p.fec_ini, " +
                  "p.fec_fin, " +
                  "p.descrip, " +
                  "p.monto_sem, " +
                  "p.id_grupo_horario, " +
                  "p.nro_cuotas, " +
                  "p.periodo_divisas, " +
                  "p.valor_divisa, " +
                  "t.nomb_turno, " +
                  "hg.descripcion, " +
                  "hg.nombre_lapso " +
                  "FROM " +
                  "public.alumno a " +
                  "INNER JOIN public.turno t ON (a.id_turno = t.\"Id\") " +
                  "INNER JOIN public.horario_grupo hg ON (t.id_grupo_horario = hg.\"Id\") " +
                  "INNER JOIN public.periodo p ON (p.id_grupo_horario = hg.\"Id\") " +
                  "WHERE " +
                  "(NOW()::date BETWEEN fec_ini_insc::date AND fec_fin::date) AND " +
                  "a.cedula = " + cedula + " AND " +
                  "p.estatus = 'A'; ";
             
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        

            optionsPeriodo =  "<option value=\"lbElige\" selected>Elige";
            while (rs.next()) {
                i++;
                if (periodo!=null) {
                    if (periodo.equals(rs.getString("periodo"))) {
                        optionsPeriodo = optionsPeriodo + "<option value=\""+rs.getString("periodo")+"\" selected>"+rs.getString("periodo");
                    }
                    else {    
                        optionsPeriodo = optionsPeriodo + "<option value=\""+rs.getString("periodo")+"\" >"+rs.getString("periodo");
                    }
                } else optionsPeriodo = optionsPeriodo + "<option value=\""+rs.getString("periodo")+"\" >"+rs.getString("periodo");
            }
        
        }        
        if (i==0) {
            optionsPeriodo =  "<option value=\"lbSinInscripcion\" selected>Sin Inscripción";
        }
        
        selectPeriodo = selectPeriodo + optionsPeriodo + "</select>";
        return selectPeriodo;        
    }        
    
    
     /**
     * Método que busca las materias pendientes por cursar del alumno.
     * @param conexion
     * @return
     * @throws SQLException 
     */    
    public String ListarPagosPeriodo(Connection conexion)  throws SQLException {
        String sql;
        String listaPagos = "";
        
        //Buscar las asignaturas que no estén aprobadas y que no prelen
        sql = "SELECT   rp.cedula,  " +
               "rp.periodo,  " +
               "rp.fecha_pago, " +
               "rp.fecha_reporte,  " +
               "rp.monto, " +
               "CASE WHEN rp.estatus='RE' THEN 'REPORTADO' " +
               "WHEN rp.estatus='CO' THEN 'CONCILIADO' " +
               "WHEN rp.estatus='AN' THEN 'ANULADO' " +
               "WHEN rp.estatus='DI' THEN 'DIFERIDO' " +
               "WHEN rp.estatus='DE' THEN 'DEBITADO' " +
               "WHEN rp.estatus='SO' THEN 'SOBRANTE' " +
               "END AS estatus, " +
               "CASE WHEN rp.tipo_pago='MAT' THEN 'PAGO DE MATRICULA' " +
               "WHEN rp.tipo_pago='DOC' THEN 'PAGO DE DOCUMENTO' " +
               "WHEN rp.tipo_pago='OTR' THEN 'OTROS PAGOS' " +
               "END AS tipo_pago, " +
               "rp.comprobante " +
               "FROM   reporte_pago rp  " +
              " WHERE rp.cedula = " + cedula +  " AND " +
              " rp.periodo = '" + periodo + "' " +
              " ORDER BY rp.fecha_reporte; ";                
                        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            listaPagos = listaPagos + "<tr><td align=\"center\"> <input type=\"checkbox\" name=\"fecha_pago\" id=\""+rs.getString("fecha_pago")+"\" align=\"center\" value=\""+rs.getString("fecha_pago")+"\" onclick=\"deshabilitaSeccion(this);\">"+rs.getString("fecha_pago")+"</td><td align=\"center\">"+rs.getString("comprobante")+"</td><td align=\"center\">"+rs.getString("monto")+"</td><td align=\"center\">"+rs.getString("fecha_reporte")+"</td><td align=\"center\">"+rs.getString("tipo_pago")+"</td><td align=\"center\">"+rs.getString("estatus")+"</td> </tr>";            
        }
        return listaPagos;
    }
    
}
