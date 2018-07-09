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
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Clase que define los métodos para la manipulación de los Datos del Profesor.
 * @author Gerson Rivas
 * @version: 1.0
 * 
 */
public class DaoProfesor extends Profesor {
    public Connection conexion;
    
    /**
     * Constructor
     * @param cedula 
     */
    public DaoProfesor(String cedula) {
        super (cedula);
        this.cedula = cedula;
    }

    public boolean BuscarDatosProfesor(Connection conexion) throws SQLException {
        String sql;
        
        sql = "SELECT " +
            "profesor.\"Id\", " +
            "profesor.cedula, " +
            "profesor.apellidos, " +
            "profesor.nombres, " +
            "profesor.ident_correo, " +
            "profesor.sexo, " +
            "profesor.tel_hab, " +
            "profesor.tel_ofc, " +
            "profesor.fec_ing, " +
            "profesor.direccion, " +
            "profesor.fec_nac, " +
            "profesor.nacionalidad, " +
            "profesor.tel_cel " +
            "FROM " +
            "public.profesor " +
            "WHERE cedula = '"+ cedula +"';";
        
        //System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            
            this.setIdProfesor(Integer.parseInt(rs.getString("Id")));
            this.setApellidos(rs.getString("apellidos"));
            this.setNombres(rs.getString("nombres"));
            this.setNacionalidad(rs.getString("nacionalidad"));
            this.setIdent_correo(rs.getString("ident_correo"));
            this.setTel_hab(rs.getString("tel_hab"));
            this.setTel_ofc(rs.getString("tel_ofc"));
            this.setTel_cel(rs.getString("tel_cel"));
            this.setSexo(rs.getString("sexo"));
            this.setFec_nac(rs.getString("fec_nac")); 
            this.setFec_ing(rs.getString("fec_ing"));
            //this.setCodEspecialidad(Integer.parseInt(rs.getInt("id_espec")));
            this.setDireccion(rs.getString("direccion"));
                        
            return true;
        } else {
            return false;
        }
    }    
    
    /**
     * Método que busca el perído actual.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarPeriodosActivos(Connection conexion) throws SQLException {
        String sql;
        String selectPeriodo = "";
        String optionsPeriodo = "";
        int i=0;
        
        sql = "SELECT DISTINCT " +
              "meast.periodo " +
              "FROM " +
              "public.profesor_horario ph " +
              "INNER JOIN " +
            "  public.materia_espec_aula_seccion_turno meast \n" +
            "ON (meast.\"Id\" = ph.id_mat_espec_aula_secc_tur)  \n" +
            "INNER JOIN " +
            "public.periodo p " +
            "ON  (meast.periodo = p.periodo) " +
            "WHERE " +
            "ph.cedula = '"+cedula +"' " +
            "AND p.estatus = 'A' " +
            "AND (NOW()::date BETWEEN fec_ini_insc::date AND fec_fin::date) ; ";        
        
        System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectPeriodo = "<select name=\"periodo\"  onchange=\"form.submit()\" id=\"periodo\" >"; 
        
        while (rs.next()) {
            i++;
            if (rs.getString("periodo").equals(periodo)) {
                optionsPeriodo = optionsPeriodo + "<option value=\"lb"+rs.getString("periodo")+"\" selected >"+rs.getString("periodo");
            } else {
                optionsPeriodo = optionsPeriodo + "<option value=\"lb"+rs.getString("periodo")+"\"  >"+rs.getString("periodo");
            }
        }
        
        if (periodo=="" || periodo==null) {
            optionsPeriodo =  "<option value=\"lbElige\" selected>Elige" + optionsPeriodo;
        } 
        if (i==0) {
            optionsPeriodo =  "<option value=\"lbSinPeriodo\" selected>Sin Período";
        }
        selectPeriodo = selectPeriodo + optionsPeriodo + "</select>";

        return selectPeriodo;
    }    
    
    public String BuscarSedes(Connection conexion)  throws SQLException {
        String sql;
        String selectSede = "";
        String optionsSede = "";
        boolean existe=false;
        int i=0;
                
        sql = "SELECT DISTINCT descrip_sede, cod_sede FROM materia_espec_aula_seccion_turno " +
            "INNER JOIN profesor_horario " +
            "ON (profesor_horario.id_mat_espec_aula_secc_tur = materia_espec_aula_seccion_turno.\"Id\") " +
            "INNER JOIN sede ON (cod_sede=sede) " +
            "WHERE profesor_horario.cedula = '"+cedula+"' " +
            "AND materia_espec_aula_seccion_turno.periodo = '" + periodo + "';";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSede = "<select name=\"sede\"  onchange=\"form.submit()\" id=\"sede\" >"; 

        while (rs.next()) {
            i++;
            if (rs.getString("cod_sede").equals(sede)) {
                optionsSede = optionsSede + "<option value=\"lb"+rs.getString("cod_sede")+"\" selected >"+rs.getString("descrip_sede");
                existe=true;
            } else {
                optionsSede = optionsSede + "<option value=\"lb"+rs.getString("cod_sede")+"\" >"+rs.getString("descrip_sede");
            }   
        }
        if (!existe || sede=="" || sede==null) {
            optionsSede =  "<option value=\"lbElige\" selected>Elige" + optionsSede;
        }
        if (i==0) {
            optionsSede =  "<option value=\"lbSinSede\" selected>Sin Sede";
        }
        selectSede = selectSede + optionsSede + "</select>";

        return selectSede;
    }
        
    public String BuscarMateriasAsignadas(Connection conexion)  throws SQLException {
        String sql;
        String selectMateria = "";
        String optionsMateria = "";
        boolean existe=false;
        int i=0;
        
        sql = " SELECT DISTINCT " +
            "  materia_espec_aula_seccion_turno.cod_mat, " +
            "  materia.descrip_mat " +
            "FROM " +
            "  public.profesor_horario " + 
            "  INNER JOIN public.materia_espec_aula_seccion_turno " + 
                " ON (profesor_horario.id_mat_espec_aula_secc_tur = materia_espec_aula_seccion_turno.\"Id\") " +
                " INNER JOIN public.materia " +
            " ON (materia_espec_aula_seccion_turno.cod_mat = materia.cod_mat) " +
            " WHERE profesor_horario.cedula = '"+cedula+"' AND " + 
            " materia_espec_aula_seccion_turno.periodo = '" + periodo + "' AND " +
            " materia_espec_aula_seccion_turno.sede = '" + sede + "';";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectMateria = "<select name=\"materia\"  onchange=\"form.submit()\" id=\"materia\" >"; 

        while (rs.next()) {
            i++;
            if (rs.getString("cod_mat").equals(asignatura)) {
                optionsMateria = optionsMateria + "<option value=\"lb"+rs.getString("cod_mat")+"\" selected >"+rs.getString("cod_mat");
                existe=true;
            } else {
                optionsMateria = optionsMateria + "<option value=\"lb"+rs.getString("cod_mat")+"\" >"+rs.getString("cod_mat");
            }   
        }
        if (!existe || asignatura=="" || asignatura==null) {
            optionsMateria =  "<option value=\"lbElige\" selected>Elige" + optionsMateria;
        }
        if (i==0) {
            optionsMateria =  "<option value=\"lbSinMateria\" selected>Sin Asignatura";
        }
        selectMateria = selectMateria + optionsMateria + "</select>";

        return selectMateria;
    }
    
    public String BuscarSeccionesMateriasAsignadas(Connection conexion)  throws SQLException {
        String sql;
        String selectSeccion = "";
        String optionsSeccion = "";
        int i=0;
        
        sql = " SELECT DISTINCT meast.seccion " +
            "FROM " +
            "  public.profesor_horario ph " +
            "INNER JOIN " +
            "  public.materia_espec_aula_seccion_turno meast " +
            "ON (meast.\"Id\" = ph.id_mat_espec_aula_secc_tur)  " +
            "INNER JOIN " +
            "  public.periodo p " +
            "ON  (meast.periodo = p.periodo) " +
            "WHERE ph.cedula = '" + cedula + "' " +
            "AND p.estatus = 'A' " +
            "AND p.periodo = '"+periodo+"' " +
            "AND meast.cod_mat = '"+asignatura+"'" +
            "AND (NOW()::date BETWEEN fec_ini_insc::date AND fec_fin::date) ;";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSeccion = "<select name=\"seccion\" id=\"seccion\" >"; 
        optionsSeccion =  "<option value=\"lbElige\" selected>Elige";
        while (rs.next()) {
            i++;
            optionsSeccion = optionsSeccion + "<option value=\"lb"+rs.getString("seccion")+"\" >"+rs.getString("seccion");
        }
        if (i==0) {
            optionsSeccion =  "<option value=\"lbSinSeccion\" selected>Sin Sección";
        }
        selectSeccion = selectSeccion + optionsSeccion + "</select>";

        return selectSeccion;
    }

    public String BuscarCabeceraEvaluacion(Connection conexion)  throws SQLException, Exception {
        String sql;

        String cabecera = "";

        sql = " SELECT DISTINCT dist_pond, cabe_pond " +
            "FROM " +
            "  public.materia_espec_aula_seccion_turno meast " +
            "INNER JOIN " +
            "  public.datos_academicos da " +
            "ON (da.\"Id\" = meast.id_datos_academ)  " + 
            "INNER JOIN " +
            "  public.profesor_horario ph " +
            "ON (meast.\"Id\" = ph.id_mat_espec_aula_secc_tur)  " + 
            "INNER JOIN " +
            "  public.periodo p " +
            "ON  (meast.periodo = p.periodo) " +                
            "WHERE ph.cedula = '" + cedula + "' " +
            "AND p.estatus = 'A' " +
            "AND p.periodo = '"+periodo+"' " +
            "AND meast.cod_mat = '"+asignatura+"' " +
            "AND (NOW()::date BETWEEN fec_ini_insc::date AND fec_fin::date) ;";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {            
            String[] paramCabe = rs.getString("cabe_pond").split(";");
            String[] paramDist = rs.getString("dist_pond").split(";");
            
            cabecera = "<tr align=\"center\">";
            cabecera = cabecera + "<th rowspan=\"2\" align=\"center\">Cédula</th>";
            cabecera = cabecera + "<th rowspan=\"2\" align=\"center\">Apellidos y Nombres</th>";
            cabecera = cabecera + "<th colspan=\""+paramCabe.length+"\" align=\"center\">Evaluación</th>";
            cabecera = cabecera + "<th rowspan=\"2\" align=\"center\">Def.</th> ";
            cabecera = cabecera + "</tr>";
            cabecera = cabecera + "<tr>";

            for (int i=0;i<paramCabe.length;i++) {                
                cabecera = cabecera + "<th align=\"center\">"+paramDist[i]+" ptos<br>"+paramCabe[i]+"</br> </th>";
            }
            cabecera = cabecera + "</tr>";            
        }
        conexion.close();
        return cabecera;
    }
    
    
    public String BuscarDetalleEvaluacion(Connection conexion, String permisoModificar)  throws SQLException, Exception {
        String sql;

        String cuerpo = "";

        sql = "SELECT DISTINCT " +
            "iad.\"Id\", " +
            "iad.cedula, " +
            "a.apellidos || ', ' || a.nombres AS apell_nomb, " +
            "iad.periodo, " +
            "pn.id_inscripcion_alumno_detalle, " +
            "meast.seccion, " +
            "p.estatus, " +
            "p.fec_ini_insc, " +
            "p.fec_fin, " +
            "pn.notas, " +                
            "da.cabe_pond, " +
            "da.dist_pond, " +                
            "pn.definitiva " +
                
            "FROM " +
            "materia_espec_aula_seccion_turno meast " +
            "INNER JOIN inscripcion_alumno_detalle iad " +
            "ON (iad.id_mat_espec_aula_secc_tur = meast.\"Id\" AND iad.periodo = meast.periodo AND iad.estatus <> 'R') " +
            "INNER JOIN alumno a " +
            "ON (iad.cedula = a.cedula) " +                
            "INNER JOIN profesor_horario ph " +
            "ON (ph.id_mat_espec_aula_secc_tur = meast.\"Id\") " +                

            "INNER JOIN datos_academicos da " +
            "ON (da.\"Id\" = meast.id_datos_academ) " +                          
                
            "INNER JOIN periodo p " +
            "ON (p.periodo = iad.periodo) " +
            "LEFT OUTER JOIN profesor_notas pn " +
            "ON (pn.id_inscripcion_alumno_detalle = iad.\"Id\") " +
            "WHERE ph.cedula = '" + cedula + "' " +
            "AND p.estatus = 'A' " +
            "AND p.periodo = '"+periodo+"' " +
            "AND meast.cod_mat = '"+asignatura+"' " +
            "AND meast.seccion = '"+seccion+"' " +
            "AND meast.sede = '"+sede+"' " +
            "AND (NOW()::date BETWEEN fec_ini_insc::date AND fec_fin::date) " +
            "ORDER BY iad.cedula;";
        //System.out.println(sql);
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        
        while (rs.next()) {           
            if (rs.getString("definitiva")==null||rs.getString("definitiva").equals("")) {
                cuerpo = cuerpo + "<tr><td> <input type=\"text\" name=\"cedulas\" id=\""+rs.getString("Id")+"\" value=\""+rs.getString("cedula")+"\" size=\"7\" readonly ></td><td> <input type=\"text\" name=\"nombresApellidos\" value=\""+rs.getString("apell_nomb") +"\" size=\"30\" readonly > </td>";                
                String[] paramCabe = rs.getString("dist_pond").split(";");
                //Para generar los cuadros de texto de la distribución de las notas
                for (int i=0;i<paramCabe.length;i++) {                
                    cuerpo = cuerpo + "<td align=\"center\"> "
                            + "<input type=\"text\" name=\"" + rs.getString("cedula") + "\" value=\"\" size=\"2\" align=\"center\" onkeypress=\"return checkDecimal(event);\" maxlength=\"4\"/> "+
                            "</td>";
                }
                cuerpo = cuerpo + "<td align=\"center\">  <input type=\"text\" name=\"definitivas\" value=\"--\" size=\"1\" readonly > </td>";
            } else {
                cuerpo = cuerpo + "<tr><td> <input type=\"text\" name=\"cedulas\" value=\""+rs.getString("cedula")+"\" size=\"7\" readonly ></td><td> <input type=\"text\" name=\"nombresApellidos\" value=\""+rs.getString("apell_nomb") +"\" size=\"30\" readonly > </td>";
                String[] paramNotas = rs.getString("notas").split(";");
                for (int i=0;i<paramNotas.length;i++) {
                    
                    if (paramNotas[i].isEmpty()||paramNotas[i].trim().equals(""))
                         cuerpo = cuerpo + "<td align=\"center\"> <input type=\"text\" name=\"" +rs.getString("cedula")+ "\" value=\"\" size=\"2\" align=\"center\" onkeypress=\"return checkDecimal(event);\" maxlength=\"4\" /> </td>";
                    else cuerpo = cuerpo + "<td align=\"center\"> <input type=\"text\" name=\"" +rs.getString("cedula")+ "\" value=\""+ paramNotas[i].trim() + "\" size=\"2\" align=\"center\" "+permisoModificar+" /> </td>";                    
                }
                cuerpo = cuerpo + "<td align=\"center\">  <input type=\"text\" name=\"definitivas\" value=\""+rs.getString("definitiva")+"\" size=\"1\" readonly > </td> ";
            }
            cuerpo = cuerpo + " </tr>";
        }
        conexion.close();
        return cuerpo;
    }
    
    public String ValidarNotas(Connection conexion, String cedula_alum, String notas_alum)  throws SQLException, Exception {
        String sql;
        String mensaje = "";

        sql = "SELECT DISTINCT " +
            "iad.\"Id\", " +
            "iad.cedula, " +
            "iad.periodo, " +                
            "pn.notas, " +
            "pn.definitiva, " +
            "meast.seccion, " +                
            "da.cabe_pond, " +
            "da.dist_pond " +                
            "FROM " +
            "materia_espec_aula_seccion_turno meast " +
            "INNER JOIN inscripcion_alumno_detalle iad " +
            "ON (iad.id_mat_espec_aula_secc_tur = meast.\"Id\" AND iad.periodo = meast.periodo) " +                
            "INNER JOIN profesor_horario ph " +
            "ON (ph.id_mat_espec_aula_secc_tur = meast.\"Id\") " + 
            "INNER JOIN datos_academicos da " +
            "ON (da.\"Id\" = meast.id_datos_academ) " +                                          
            "INNER JOIN periodo p " +
            "ON (p.periodo = iad.periodo) " +                
            "LEFT OUTER JOIN profesor_notas pn " +
            "ON (pn.id_inscripcion_alumno_detalle = iad.\"Id\") " +                 
            "WHERE ph.cedula = '" + cedula + "' " +
            "AND p.estatus = 'A' " +
            "AND p.periodo = '"+periodo+"' " +
            "AND meast.cod_mat = '"+asignatura+"' " +
            "AND meast.seccion = '"+seccion+"' " +
            "AND iad.cedula = '"+cedula_alum+"' " +
            "AND (NOW()::date BETWEEN fec_ini_insc::date AND fec_fin::date) " +
            "ORDER BY iad.cedula;";

        //System.out.println(sql);

        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
                
        if (rs.next()) {
            //Para asegurar que el registro ya esté guardado
            
            String[] paramDist = rs.getString("dist_pond").split(";");
            String[] notas_dist = notas_alum.split(";");
                
            //System.out.print(paramDist.length + "==" + notas_dist.length);
                
            if (paramDist.length==notas_dist.length) {
                for (int i=0;i<paramDist.length;i++) {
                    if (!paramDist[i].equals("-")) {
                        if (!notas_dist[i].isEmpty()&&!notas_dist[i].equals(" ")) {
                            if ((Double.parseDouble(notas_dist[i])) > (Double.parseDouble(paramDist[i]))) {
                                mensaje = mensaje + " cédula "+cedula_alum +" evaluación "+ (i+1) +",";
                            }                                                                
                        } 
                    } 
                }                    
                                        
            } else {
                mensaje = "Hay notas vacías, favor consulte con el administrador del sistema. ";                            
            }            
        }          
        conexion.close();
        return mensaje; 
    }
    


    public String ConstruirInsertUpdate(Connection conexion, String cedula_alum, String notas_alum, Integer id_usu)  throws SQLException, Exception {
        
        String sql = "";
        String sqlInsertUpdate = "";
        
        double definitiva = 0;
        DecimalFormat df = new DecimalFormat("#0");

        sql = "SELECT DISTINCT " +
            "iad.\"Id\", " +
            "iad.cedula, " +
            "iad.periodo, " +                
            "pn.notas, " +
            "pn.definitiva, " +
            "meast.seccion, " +                
            "da.cabe_pond, " +
            "da.dist_pond " +                
            "FROM " +
            "materia_espec_aula_seccion_turno meast " +
            "INNER JOIN inscripcion_alumno_detalle iad " +
            "ON (iad.id_mat_espec_aula_secc_tur = meast.\"Id\" AND iad.periodo = meast.periodo) " +                
            "INNER JOIN profesor_horario ph " +
            "ON (ph.id_mat_espec_aula_secc_tur = meast.\"Id\") " + 
            "INNER JOIN datos_academicos da " +
            "ON (da.\"Id\" = meast.id_datos_academ) " +                                          
            "INNER JOIN periodo p " +
            "ON (p.periodo = iad.periodo) " +                
            "LEFT OUTER JOIN profesor_notas pn " +
            "ON (pn.id_inscripcion_alumno_detalle = iad.\"Id\") " +                 
            "WHERE ph.cedula = '" + cedula + "' " +
            "AND p.estatus = 'A' " +
            "AND p.periodo = '"+periodo+"' " +
            "AND meast.cod_mat = '"+asignatura+"' " +
            "AND meast.seccion = '"+seccion+"' " +
            "AND meast.sede = '"+sede+"' " +
            "AND iad.cedula = '"+cedula_alum+"' " +
            "AND (NOW()::date BETWEEN fec_ini_insc::date AND fec_fin::date) " +
            "ORDER BY iad.cedula;";

        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
                
        if (rs.next()) {
            //Calcular la definitiva
            
            String[] paramDist = rs.getString("dist_pond").split(";");
            String[] notas_dist = notas_alum.split(";");
            
            if (paramDist.length==notas_dist.length) {
                for (int i=0;i<paramDist.length;i++) {
                    if (!paramDist[i].equals("-")) {
                        if (!notas_dist[i].isEmpty()&&!notas_dist[i].equals(" ")) {
                            definitiva = definitiva + Double.parseDouble(notas_dist[i]);
                        } 
                    }
                }
            }       
            
            //Para asegurar que el registro ya esté guardado       
            if (rs.getString("definitiva")==null) {
                sqlInsertUpdate = "INSERT INTO profesor_notas (id_inscripcion_alumno_detalle, notas, definitiva, id_usuario) VALUES (";
                sqlInsertUpdate = sqlInsertUpdate + "'" + rs.getString("Id") + "', '" + notas_alum + "','" + Math.round(definitiva+0.05) + "','" + id_usu + "'); ";                            
                                                                
            } else if (!notas_alum.trim().equals(rs.getString("notas").trim())) {                                
                sqlInsertUpdate = "UPDATE profesor_notas " + 
                "SET  notas = '" + notas_alum + "', definitiva = '" + Math.round(definitiva+0.05) + "', id_usuario = '" + id_usu + "' " +
                "WHERE id_inscripcion_alumno_detalle = '" + rs.getString("Id") + "'; ";
            }
                        
        }          
        conexion.close();
        return sqlInsertUpdate; 
    }

    public String GuardarNotas(Connection conexion, String sqlInsertUpdate)  throws SQLException, Exception {
        try {
            PreparedStatement ps = conexion.prepareStatement(sqlInsertUpdate);
            int cantidadRegistros = ps.executeUpdate();
            System.out.println("Cantidad egistros:-->"+cantidadRegistros);
            conexion.close();
            return "";
        } catch (SQLException e) {
            conexion.close();
            return e.getMessage();
        }
    }
    
    public String BuscarProfesores(Connection conexion) throws SQLException {
        
        String selectProfesores = "";
        String optionsProfesores = "";
        boolean existe=false;
        int i=0;        
        
        selectProfesores = "Profesor: <select name=\"profesor\"  onchange=\"form.submit()\" id=\"profesor\" >"; 
        
        String sql;            
            
        sql = "SELECT " +
              "cedula, apellidos, nombres " +
              "FROM profesor " + 
              "WHERE 1=1 ";
        if (this.cedula!=null && !this.cedula.equals("0")) {
            sql = sql + "AND cedula = " + this.cedula + " ";
        }
        if (nombres!=null && nombres!="") {
            sql = sql + "AND  lower(nombres ||' '|| apellidos) LIKE lower('%" + nombres + "%') ";
        }              
        sql = sql + "ORDER BY cedula;";
            
        //System.out.println(sql);
            
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();    
            
        while (rs.next()) {
            i++;
            if (rs.getString("cedula").equals(cedula)) {
                optionsProfesores = optionsProfesores + "<option value=\"lb"+rs.getString("cedula")+"\" selected >"+rs.getString("cedula")+" "+rs.getString("nombres")+" "+rs.getString("apellidos");
                existe=true;
            } else {
                 optionsProfesores = optionsProfesores + "<option value=\"lb"+rs.getString("cedula")+"\" >"+rs.getString("cedula")+" "+rs.getString("nombres")+" "+rs.getString("apellidos");
            }   
        }        

        if (!existe || cedula=="" || cedula==null) {
            optionsProfesores =  "<option value=\"lbElige\" selected>Elige" + optionsProfesores;
        }
        if (i==0) {
            optionsProfesores =  "<option value=\"lbSinProfesor\" selected>Sin Profesor";
        }
        selectProfesores = selectProfesores + optionsProfesores + "</select> " +
                            "Cédula: <input type=\"text\" name=\"cedula\" value=\"\" maxlength=\"9\" onkeypress=\"return checkIt(event);\" > " +
                            "Nombre y Apellido: <input type=\"text\" name=\"nombreApellido\" value=\"\" maxlength=\"60\" > " + 
                            "<input name=\"action\" type=\"submit\" id=\"buscar\" value=\"Buscar\" />";

        conexion.close();
        return selectProfesores;  
    }
        
    
    /**
     * Método que busca los horarios de un profesor.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarHorariosProfesor(Connection conexion)  throws SQLException {
        String sql;
        String listaHorarios = "";
        
        //Buscar las asignaturas inscritas

            sql = "SELECT p.cedula, p.apellidos||', '||p.nombres AS apelnomb, meast.cod_mat, meast.seccion, " +
              "meast.id_turno, meast.aula, h.dia, h.id_turno, " +
              "MIN(h.hora_ini) AS hora_ini, MAX(h.hora_fin) AS hora_fin " +
              "FROM profesor_horario ph " + 
              "INNER JOIN materia_espec_aula_seccion_turno  meast " +
              "ON (meast.\"Id\"=ph.id_mat_espec_aula_secc_tur) " + 
              "INNER JOIN horario h ON (h.\"Id\"=ph.id_horario) INNER JOIN " +
              "profesor p ON (p.cedula=ph.cedula) " +
              "WHERE meast.\"Id\" = " + idMatEspAulSecTur + " " +
              "GROUP BY p.cedula, p.apellidos, p.nombres, meast.cod_mat, meast.seccion, " +
              "meast.id_turno, meast.aula, h.dia, h.id_turno; ";

        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            listaHorarios = listaHorarios +   "<td align=\"center\">"+rs.getString("dia")+"</td>"+
                                              "<td align=\"center\">"+rs.getString("hora_ini")+"</td>"+
                                              "<td align=\"center\">"+rs.getString("hora_fin")+"</td></tr>";            
        }

        conexion.close();
        return listaHorarios;
    }
    
    /**
     * Método que busca los horarios de un profesor.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarDatosProfesorHorarios(Connection conexion)  throws SQLException {
        String sql;
        String datosProfesor = "";
        
        //Buscar las asignaturas inscritas

            sql = "SELECT COALESCE(p.cedula, 0) AS cedula , COALESCE(p.apellidos||', '||p.nombres, 'Sin Asignar') AS apelnomb, meast.cod_mat, meast.seccion, e.nomb_espec, " +
              "meast.id_turno, meast.aula " +
              "FROM profesor_horario ph " + 
              "RIGHT OUTER JOIN materia_espec_aula_seccion_turno  meast " +
              "ON (meast.\"Id\"=ph.id_mat_espec_aula_secc_tur) " + 
              "LEFT OUTER JOIN profesor p ON (p.cedula=ph.cedula) " +
              "RIGHT OUTER JOIN especialidad e ON (meast.id_espec=e.\"Id\") " +
              "WHERE meast.\"Id\" = " + idMatEspAulSecTur + "; ";
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            cedula=rs.getString("cedula");            
            datosProfesor=" Cédula: " + rs.getString("cedula") + " | Nombres y Apellidos: " + rs.getString("apelnomb") + " | Especialidad: " + rs.getString("nomb_espec") + " | Asignatura: " + rs.getString("cod_mat") + " | Sección: " + rs.getString("seccion")+ " | Aula: " + rs.getString("aula");
        }

        conexion.close();
        return datosProfesor;
    }
    
    
   
    
    
    
    
    
}
