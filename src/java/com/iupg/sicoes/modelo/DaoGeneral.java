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
import java.util.Calendar;

/**
 * 
 * @author gerson
 */
public class DaoGeneral extends General {
    public Connection conexion;
    
    public DaoGeneral() {
        super();
    }
    
    /**
     * Método que devuelve las sedes en un string para jsp.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarSede(Connection conexion) throws SQLException {
        String sql;
        String selectSede = "";
        String optionsSede = "";
        int i=0;
        
        sql = "SELECT " +
              "cod_sede, descrip_sede " +
              "FROM " +
              "public.sede "; 

        //System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectSede = "<select name=\"sede\" onchange=\"form.submit()\" id=\"sede\" >"; 
        
        while (rs.next()) {
            i++;
            if (rs.getString("cod_sede").equals(sede)) {
                optionsSede = optionsSede + "<option value=\"lb"+rs.getString("cod_sede")+"\" selected >"+rs.getString("descrip_sede");
            } else {
                optionsSede = optionsSede + "<option value=\"lb"+rs.getString("cod_sede")+"\"  >"+rs.getString("descrip_sede");
            }
        }
        
        if (sede=="" || sede==null) {
            optionsSede =  "<option value=\"lbElige\" selected>Elige" + optionsSede;
        } 
        if (i==0) {
            optionsSede =  "<option value=\"lbSinSede\" selected>Sin Sede";
        }
        selectSede = selectSede + optionsSede + "</select>";
        
        return selectSede;
    }
    
     /**
     * Método para buscar los períodos activos devolviéndolos en un combo jsp, para cualquier usuario
     * @param conexion
     * @return
     * @throws SQLException 
     */
    
    public String BuscarPeriodosActivos(Connection conexion) throws SQLException {
        String sql;
        String selectPeriodo = "";
        String optionsPeriodo = "";
        int i=0;
        
        sql = "SELECT " +
              "p.periodo " +
              "FROM " +
              "public.periodo p " +
            "WHERE p.estatus = 'A' " +
            "AND (NOW()::date BETWEEN p.fec_ini_insc::date AND p.fec_fin::date) ; ";        
        
        //System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectPeriodo = "<select name=\"periodo\" onchange=\"form.submit()\" id=\"periodo\" >"; 
        
        while (rs.next()) {
            i++;
            if (rs.getString("periodo").equals(periodo)) {
                optionsPeriodo = optionsPeriodo + "<option value=\"lb"+rs.getString("periodo")+"\" selected >"+rs.getString("periodo");
            } else {
                optionsPeriodo = optionsPeriodo + "<option value=\"lb"+rs.getString("periodo")+"\"  >"+rs.getString("periodo");
            }
        }
        
        if (periodo=="" || periodo==null || periodo=="Elige") {
            optionsPeriodo =  "<option value=\"lbElige\" selected>Elige" + optionsPeriodo;
        } 
        if (i==0) {
            optionsPeriodo =  "<option value=\"lbSinPeriodo\" selected>Sin Período";
        }
        selectPeriodo = selectPeriodo + optionsPeriodo + "</select>";

        return selectPeriodo;
    }   
    

     /**
     * Método para buscar los períodos activos devolviéndolos en un combo jsp, para cualquier usuario
     * @param conexion
     * @return
     * @throws SQLException 
     */
    
    public String BuscarPeriodos(Connection conexion) throws SQLException {
        String sql;
        String selectPeriodo = "";
        String optionsPeriodo = "";
        int i=0;
        
        sql = "SELECT " +
              "p.periodo " +
              "FROM " +
              "public.periodo p " +
              "ORDER BY \"Id\" DESC;";
        
        //System.out.println(sql);        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        selectPeriodo = "<select name=\"periodo\" onchange=\"form.submit()\" id=\"periodo\" >"; 
        
        while (rs.next()) {
            i++;
            if (rs.getString("periodo").equals(periodo)) {
                optionsPeriodo = optionsPeriodo + "<option value=\""+rs.getString("periodo")+"\" selected >"+rs.getString("periodo");
            } else {
                optionsPeriodo = optionsPeriodo + "<option value=\""+rs.getString("periodo")+"\"  >"+rs.getString("periodo");
            }
        }
        
        
        if (periodo == null || periodo.equalsIgnoreCase("") || periodo.equalsIgnoreCase("Elige")) {
            optionsPeriodo =  "<option value=\"lbElige\" selected>Elige" + optionsPeriodo;
        } 
        if (i==0) {
            optionsPeriodo =  "<option value=\"lbSinPeriodo\" selected>Sin Período";
        }
        selectPeriodo = selectPeriodo + optionsPeriodo + "</select>";

        return selectPeriodo;
    }   


    /**
     * Método que calcula los trimestres y semestres del período.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public void BuscarTipoLapsoPeriodo(Connection conexion)  throws SQLException {
        String sql;
        //String tipoLapsoPeriodo = "";
        
        sql = "SELECT " +
              "  hg.nombre_lapso " +
              "FROM " +
              "  public.horario_grupo hg INNER JOIN " +
              "  public.periodo p ON (p.id_grupo_horario=hg.\"Id\") " +
              "WHERE  " +
              "  periodo = '"+periodo+"';";        
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            tipoLapsoPeriodo=rs.getString("nombre_lapso");
        } else {
            tipoLapsoPeriodo="";
        }

        //return tipoLapsoPeriodo;
    } 
    
    /**
     * Método para la búsqueda de las Asignaturas, devueltas en un combo jsp
     * @param conexion
     * @return
     * @throws SQLException 
     **/     
    public String BuscarLapsosAsignaturas(Connection conexion)  throws SQLException {
        
        String selectLapso = "";
        String optionsLapso = "";
        boolean existe=false;
        int i=0;        
        
        selectLapso = "<select name=\"lapso\"  onchange=\"form.submit()\" id=\"lapso\" >"; 
        
        if (tipoLapsoPeriodo!="" && tipoLapsoPeriodo!=null) {     
            String sql;
            sql = "SELECT DISTINCT " + tipoLapsoPeriodo +
            " FROM " +
            "  materia_espec " +
            "ORDER BY "+tipoLapsoPeriodo+";";
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            //System.out.println(sql);
            
            while (rs.next()) {
                i++;
                if (rs.getString(tipoLapsoPeriodo).equals(semestreTrimestre)) {
                    optionsLapso = optionsLapso + "<option value=\"lb"+rs.getString(tipoLapsoPeriodo)+"\" selected >"+rs.getString(tipoLapsoPeriodo);
                    existe=true;
                } else {
                    optionsLapso = optionsLapso + "<option value=\"lb"+rs.getString(tipoLapsoPeriodo)+"\" >"+rs.getString(tipoLapsoPeriodo);
                }   
            }
            
        }

        if (!existe || tipoLapsoPeriodo=="" || tipoLapsoPeriodo==null) {
            optionsLapso =  "<option value=\"lbElige\" selected>Elige" + optionsLapso;
        }
        if (i==0) {
            optionsLapso =  "<option value=\"lbSinLapso\" selected>Sin Lapso";
        }
        selectLapso = selectLapso + optionsLapso + "</select>";

        return selectLapso;
    }


    /**
     * Método para buscar las asignaturas de un Lapso (Trimestre,Semestre, etc).
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarEspecialidades(Connection conexion) throws SQLException {
        
        String selectEspecialidades = "";
        String optionsEspecialidades = "";
        boolean existe=false;
        int i=0;        
        
        selectEspecialidades = "<select name=\"especialidad\"  onchange=\"form.submit()\" id=\"especialidad\" >"; 
        
        if (tipoLapsoPeriodo!="" && tipoLapsoPeriodo!=null && !semestreTrimestre.equals("SinLapso")) {     
            String sql;
            
            sql = "SELECT DISTINCT e.\"Id\", e.nomb_espec " +
                  "FROM public.materia_espec me INNER JOIN " +
                  "especialidad e ON (e.\"Id\"=me.id_espec) " +
                  "WHERE " + tipoLapsoPeriodo + "= "+semestreTrimestre+" " +
                  "AND me.pensum_activo = 'S' " +
                  "ORDER BY e.\"Id\";";
            
            //System.out.println(sql);
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            while (rs.next()) {
                i++;
                if (rs.getString("Id").equals(especialidad)) {
                    optionsEspecialidades = optionsEspecialidades + "<option value=\"lb"+rs.getString("Id")+"\" selected >"+rs.getString("nomb_espec");
                    existe=true;
                } else {
                    optionsEspecialidades = optionsEspecialidades + "<option value=\"lb"+rs.getString("Id")+"\" >"+rs.getString("nomb_espec");
                }   
            }
            
        }

        if (!existe || especialidad=="" || especialidad==null) {
            optionsEspecialidades =  "<option value=\"lbElige\" selected>Elige" + optionsEspecialidades;
        }
        if (i==0) {
            optionsEspecialidades =  "<option value=\"lbSinEspecialidad\" selected>Sin Especialidad";
        }
        selectEspecialidades = selectEspecialidades + optionsEspecialidades + "</select>";

        return selectEspecialidades;  
    }


    
    /**
     * Método para buscar las asignaturas de un Lapso (Trimestre,Semestre, etc).
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarAsignaturasPorLapso(Connection conexion) throws SQLException {
        
        String selectAsignaturas = "";
        String optionsAsignaturas = "";
        boolean existe=false;
        int i=0;        
        
        selectAsignaturas = "<select name=\"asignatura\"  onchange=\"form.submit()\" id=\"asignatura\" >"; 
        
        if (tipoLapsoPeriodo!="" && tipoLapsoPeriodo!=null && asignatura!="" && asignatura!=null && !semestreTrimestre.equals("SinLapso") && !especialidad.equals("SinEspecialidad") && !especialidad.equals("Elige")) {     
            String sql;
            sql = "SELECT " +
                  "materia_espec.cod_mat " +
                  "FROM " +
                  "public.materia_espec " +
                  "WHERE " + tipoLapsoPeriodo + "= "+semestreTrimestre+" " +    
                  "AND id_espec = " + especialidad + " " +
                  "AND materia_espec.pensum_activo = 'S';";
            
            //System.out.println(sql);
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            while (rs.next()) {
                i++;
                if (rs.getString("cod_mat").equals(asignatura)) {
                    optionsAsignaturas = optionsAsignaturas + "<option value=\"lb"+rs.getString("cod_mat")+"\" selected >"+rs.getString("cod_mat");
                    existe=true;
                } else {
                    optionsAsignaturas = optionsAsignaturas + "<option value=\"lb"+rs.getString("cod_mat")+"\" >"+rs.getString("cod_mat");
                }   
            }
            
        }

        if (!existe || asignatura=="" || asignatura==null) {
            optionsAsignaturas =  "<option value=\"lbElige\" selected>Elige" + optionsAsignaturas;
        }
        if (i==0) {
            optionsAsignaturas =  "<option value=\"lbSinAsignatura\" selected>Sin Asignatura";
        }
        selectAsignaturas = selectAsignaturas + optionsAsignaturas + "</select>";

        return selectAsignaturas;  
    }
    
    public String BuscarTurnos(Connection conexion) throws SQLException {
        
        String selectTurnos = "";
        String optionsTurnos = "";
        boolean existe=false;
        int i=0;        
        
        selectTurnos = "<select name=\"turno\"  onchange=\"form.submit()\" id=\"turno\" >"; 
        
        if (tipoLapsoPeriodo!="" && tipoLapsoPeriodo!=null && asignatura!="" && asignatura!=null && !semestreTrimestre.equals("SinLapso") && !especialidad.equals("SinEspecialidad")) {     
            String sql;            
            sql = "SELECT " +
                  "  turno.nomb_turno, " +
                  "  turno.inicial, " +
                  "  turno.\"Id\" " +
                  "FROM " +
                  "  public.turno;";
            
            //System.out.println(sql);
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            while (rs.next()) {
                i++;
                if (rs.getString("Id").equals(turno)) {
                    optionsTurnos = optionsTurnos + "<option value=\"lb"+rs.getString("Id")+"\" selected >"+rs.getString("nomb_turno");
                    existe=true;
                } else {
                    optionsTurnos = optionsTurnos + "<option value=\"lb"+rs.getString("Id")+"\" >"+rs.getString("nomb_turno");
                }   
            }
            
        }

        if (!existe || turno=="" || turno==null) {
            optionsTurnos =  "<option value=\"lbElige\" selected>Elige" + optionsTurnos;
        }
        if (i==0) {
            optionsTurnos =  "<option value=\"lbSinTurno\" selected>Sin Turno";
        }
        selectTurnos = selectTurnos + optionsTurnos + "</select>";

        return selectTurnos;  
    }
    
    public String BuscarSecciones() throws SQLException {
        
        String selectSecciones = "";
        String optionsSecciones = "";
        boolean existe=false;
        int i=0;        
        
        // ejemplo de arreglo
        String [] secciones = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","V","W","X","Y","Z"};
        
        selectSecciones = "<select name=\"seccion\"  onchange=\"form.submit()\" id=\"seccion\" >"; 
        
        if (tipoLapsoPeriodo!="" && tipoLapsoPeriodo!=null && asignatura!="" && asignatura!=null && !semestreTrimestre.equals("SinLapso") && !especialidad.equals("SinEspecialidad")) {     
            
            //for normal indicamos una variable i la que almacenara el indice del arreglo el cual vamos a iterar y se incrementara en uno.
            for(int j=0;j<secciones.length ; j++)
            {                
                if  (seccion.equals(secciones[j].toString())) {
                    optionsSecciones = optionsSecciones + "<option value=\"lb"+secciones[j].toString()+"\" selected >"+secciones[j].toString();
                    existe=true;
                } else {
                    optionsSecciones = optionsSecciones + "<option value=\"lb"+secciones[j].toString()+"\" >"+secciones[j].toString();
                }
                i=j;
            }
        }

        if (!existe || seccion=="" || seccion==null) {
            optionsSecciones =  "<option value=\"lbElige\" selected>Elige" + optionsSecciones;
        }
        if (i==0) {
            optionsSecciones =  "<option value=\"lbSinSeccion\" selected>Sin Sección";
        }
        selectSecciones = selectSecciones + optionsSecciones + "</select>";

        return selectSecciones;
    }
    
    public String BuscarAulas(Connection conexion) throws SQLException {
        
        String selectAulas = "";
        String optionsAulas = "";
        boolean existe=false;
        int i=0;        
        
        selectAulas = "<select name=\"aula\"  onchange=\"form.submit()\" id=\"aula\" >"; 
        
        if (sede!="" && tipoLapsoPeriodo!="" && tipoLapsoPeriodo!=null && asignatura!="" && asignatura!=null && !semestreTrimestre.equals("SinLapso") && !especialidad.equals("SinEspecialidad")) {     
            String sql;            
            
            sql = "SELECT " +
                  "aula.aula, " +
                  "aula.sede " +
                  "FROM " +
                  "public.aula " +
                  "WHERE sede = '"+sede+"'";
            
            //System.out.println(sql);
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            while (rs.next()) {
                i++;
                if (rs.getString("aula").equals(aula)) {
                    optionsAulas = optionsAulas + "<option value=\"lb"+rs.getString("aula")+"\" selected >"+rs.getString("aula");
                    existe=true;
                } else {
                    optionsAulas = optionsAulas + "<option value=\"lb"+rs.getString("aula")+"\" >"+rs.getString("aula");
                }   
            }
            
        }

        if (!existe || aula=="" || aula==null) {
            optionsAulas =  "<option value=\"lbElige\" selected>Elige" + optionsAulas;
        }
        if (i==0) {
            optionsAulas =  "<option value=\"lbSinAula\" selected>Sin Aula";
        }
        selectAulas = selectAulas + optionsAulas + "</select>";

        conexion.close();
        return selectAulas;  
    }


    /**
     * Método que busca las secciones creadas en un período.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarSeccionesFiltradas(Connection conexion)  throws SQLException {
        String sql;
        String listaSecciones = "";
        
        //Buscar las asignaturas inscritas

        sql = "SELECT " +
              "meast.\"Id\", " +
              "meast.periodo, " +
              "meast.cod_mat, " +
              "meast.id_espec, " +
              "meast.seccion, " +
              "meast.id_turno, " +
              "meast.aula, " +
              "meast.sede, " +
              "t.nomb_turno, " +
              "t.inicial, " +
              "(SELECT COUNT(cedula) FROM inscripcion_alumno_detalle WHERE id_mat_espec_aula_secc_tur=meast.\"Id\") AS inscritos, " +
              "e.nomb_espec";
        if (tipoLapsoPeriodo!=null && !tipoLapsoPeriodo.equals("") && !tipoLapsoPeriodo.equals("SinLapso")){
            sql = sql + ", me."+tipoLapsoPeriodo + " AS lapso ";
        }
        sql = sql +
              " FROM " +
              "public.materia_espec_aula_seccion_turno meast " +
              "INNER JOIN public.turno t ON (meast.id_turno=t.\"Id\") " +
              "INNER JOIN public.especialidad e ON (meast.id_espec=e.\"Id\") " +
              "INNER JOIN public.materia_espec me ON (meast.cod_mat=me.cod_mat AND meast.id_espec=me.id_espec) " +
              "WHERE meast.sede = '" + sede + "' " +
              "AND meast.periodo = '" + periodo + "' ";
        
        //System.out.println("sql--->"+sql);

        if (tipoLapsoPeriodo!=null && !tipoLapsoPeriodo.equals("") && !tipoLapsoPeriodo.equals("SinLapso") && !semestreTrimestre.equals("SinLapso") && !semestreTrimestre.equals("Elige")) {
            sql = sql + "AND me."+ tipoLapsoPeriodo + " = " + semestreTrimestre + " ";
        }

        if (especialidad!=null && !especialidad.equals("SinEspecialidad") && !especialidad.equals("Elige")) {
            sql = sql + "AND meast.id_espec = " + especialidad + " ";
        }
                
        if (asignatura!=null && !asignatura.equals("SinAsignatura") && !asignatura.equals("Elige")) { 
            sql = sql + "AND meast.cod_mat = '" + asignatura + "' ";
        }

        if (turno!=null && !turno.equals("SinTurno") && !turno.equals("Elige")) { 
            sql = sql + "AND t.\"Id\" = " + turno + " ";
        }
        
        if (seccion!=null && !seccion.equals("SinSeccion") && !seccion.equals("Elige")) { 
            sql = sql + "AND meast.seccion = '" + semestreTrimestre + "'||t.inicial||'" + seccion + "' ";
        }

        if (aula!=null && !aula.equals("SinAula") && !aula.equals("Elige")) { 
            sql = sql + "AND meast.aula = '" + aula + "' ";
        }        
        
        sql = sql + ";";
        
        //System.out.println("sql=====>"+sql);         
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            listaSecciones = listaSecciones + "<tr><td align=\"center\">"+ rs.getString("sede") + "</td>"+
                                              "<td align=\"center\">"+rs.getString("periodo")+"</td>";
            if (tipoLapsoPeriodo!=null && !tipoLapsoPeriodo.equals("") && !tipoLapsoPeriodo.equals("SinLapso")){
                listaSecciones = listaSecciones + "<td align=\"center\">"+rs.getString("lapso")+"</td>";
            } else {
                listaSecciones = listaSecciones + "<td align=\"center\">"+semestreTrimestre+"</td>";
            }
            listaSecciones = listaSecciones + "<td>"+rs.getString("nomb_espec")+"</td>"+
                                              "<td align=\"center\">"+rs.getString("cod_mat")+"</td>"+
                                              "<td align=\"center\">"+rs.getString("nomb_turno")+"</td>"+ 
                                              "<td align=\"center\"><a href=\"horariosProfesor.do?idMatEspAulSecTurSession="+rs.getString("Id")+"&periodoSeleccionadoSession="+rs.getString("periodo")+"\">"+rs.getString("seccion")+"</a></td>"+ 
                                              "<td align=\"center\">"+rs.getString("aula")+"</td>"+
                                              "<td align=\"center\"><a href=\"alumnosInscritos.do?idMatEspAulSecTurSession="+rs.getString("Id")+"\">"+rs.getString("inscritos")+"</a></td></tr>";
        }

        conexion.close();
        return listaSecciones;
    }
    
    /**
     * Método para eliminar los registros filtrados de Secciones, Aulas y Asignaturas.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String EliminarSeccionesFiltradas(Connection conexion)  throws SQLException {
        String sql;        
        
        try {
              sql = "DELETE " +
              "FROM " +
              "public.materia_espec_aula_seccion_turno meast " +
              "WHERE meast.sede = '" + sede + "' " +
              "AND meast.periodo = '" + periodo + "' ";

            if (especialidad!=null && !especialidad.equals("SinEspecialidad") && !especialidad.equals("Elige")) {
                sql = sql + "AND meast.id_espec = " + especialidad + " ";
            }
                
            if (!asignatura.equals("SinAsignatura") && asignatura!=null) { 
                sql = sql + "AND meast.cod_mat = '" + asignatura + "' ";
            }

            if (!turno.equals("SinTurno") && turno!=null && !turno.equals("Elige")) { 
                sql = sql + "AND meast.id_turno = " + turno + " ";
            }
        
            if (!seccion.equals("SinSeccion") && seccion!=null && !seccion.equals("Elige")) { 
                sql = sql + "AND meast.seccion = '" + semestreTrimestre + "'||(SELECT t.inicial FROM turno t WHERE \"Id\"="+turno+")||'" + seccion + "' ";
            }

            if (!aula.equals("SinAula") && aula!=null && !aula.equals("Elige")) { 
                sql = sql + "AND meast.aula = '" + aula + "' ";
            }        
        
            sql = sql + ";";
            
            //System.out.println("sql-->"+sql);
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            conexion.close();
            return "Registro(s) Eliminado(s).";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }
    
    public String ValidarExistenciaHorarioProfesores(Connection conexion) throws SQLException {
        String sql;
        String mensaje="";
        
        sql = "SELECT " +
              " ph.cedula " +
              "FROM " +
              "  public.profesor_horario ph " +
              "WHERE ph.id_mat_espec_aula_secc_tur " +
              "IN ( SELECT meast.\"Id\" " +
                    "FROM " +
                    "public.materia_espec_aula_seccion_turno meast " +
                    "INNER JOIN turno t ON (t.\"Id\"=meast.id_turno) " +
                    "WHERE meast.sede = '" + sede + "' " +
                    "AND meast.periodo = '" + periodo + "' ";
        
        if (especialidad!=null && !especialidad.equals("SinEspecialidad") && !especialidad.equals("Elige")) {
            sql = sql + "AND meast.id_espec = " + especialidad + " ";
        }
                
        if (!asignatura.equals("SinAsignatura") && asignatura!=null) { 
            sql = sql + "AND meast.cod_mat  = '" + asignatura + "' ";
        }

        if (!turno.equals("SinTurno") && turno!=null && !turno.equals("Elige")) { 
            sql = sql + "AND meast.id_turno = " + turno + " ";
        }
        
        if (!seccion.equals("SinSeccion") && seccion!=null && !seccion.equals("Elige")) { 
            sql = sql + "AND meast.seccion = '" + semestreTrimestre + "'||t.inicial||'" + seccion + "' ";
        }

        if (!aula.equals("SinAula") && aula!=null && !aula.equals("Elige")) { 
            sql = sql + "AND meast.aula = '" + aula + "' ";
        }        
        
        sql = sql + ");";
        
        System.out.println("sql-->"+sql);
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            mensaje = "Existe horarios de profesores cargados en las secciones seleccionadas.";
        }
        conexion.close();
        return mensaje;
    }

    /**
     * Método para adicionar el registro de Secciones, Aulas y Asignaturas.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String AdicionarSeccionFiltrada(Connection conexion)  throws SQLException {
        String sql;
        
        try {
              sql = "INSERT " +                    
                    "INTO materia_espec_aula_seccion_turno " +
                    "(sede, periodo, cod_mat, id_espec, seccion, id_turno, aula, id_datos_academ) " +    
                    "VALUES " +
                    "('" + sede + "', '" + periodo + "', '" + asignatura + "', " + especialidad + ", '" + semestreTrimestre + "'||(SELECT inicial FROM turno WHERE \"Id\"="+turno+")||'" +seccion + "', " + turno + ", '" + aula + "', 1);";
            
              System.out.println("INSERT---->"+sql);
              PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            conexion.close();
            return "Registro Guardado.";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }
    
     /**
     * Método para adicionar registros al combo de días de los horarios.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarDias(Connection conexion) throws SQLException {        
        String selectDias = "";
        String optionsDias = "";
        boolean existe=false;
        int i=0;        
        
        selectDias = "<select name=\"dia\"  onchange=\"form.submit()\" id=\"dia\" >"; 
        
        String sql;            
        sql = "SELECT " +
              " DISTINCT h.dia " +
              "FROM " +
              "  public.horario h " +
              "WHERE id_turno = (SELECT id_turno " + 
                                "FROM materia_espec_aula_seccion_turno " + 
                                "WHERE \"Id\"="+idMatEspAulSecTur+");";
            
            //System.out.println(sql);
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            while (rs.next()) {
                i++;
                if (rs.getString("dia").equals(dia)) {
                    optionsDias = optionsDias + "<option value=\"lb"+rs.getString("dia")+"\" selected >"+rs.getString("dia");
                    existe=true;
                } else {
                    optionsDias = optionsDias + "<option value=\"lb"+rs.getString("dia")+"\" >"+rs.getString("dia");
                }   
            }
            

        if (!existe || dia=="" || dia==null) {
            optionsDias =  "<option value=\"lbElige\" selected>Elige" + optionsDias;
        }
        if (i==0) {
            optionsDias =  "<option value=\"lbSinDia\" selected>Sin Día";
        }
        selectDias = selectDias + optionsDias + "</select>";

        return selectDias;  
    }
    
     /**
     * Método para adicionar registros al combo de las Horas Inicio.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarHorasInicio(Connection conexion) throws SQLException {        
        String selectHoras = "";
        String optionsHoras = "";
        boolean existe=false;
        int i=0;        
        
        selectHoras = "<select name=\"hora_ini\"  onchange=\"form.submit()\" id=\"hora_ini\" >"; 
        
        if (dia!=null) {
            String sql;            
            sql = "SELECT " +
              " DISTINCT h.hora_ini " +
              "FROM " +
              "  public.horario h " +
              "WHERE id_turno = (SELECT id_turno " + 
                                "FROM materia_espec_aula_seccion_turno " + 
                                "WHERE \"Id\"="+idMatEspAulSecTur+") " +
              "AND h.\"Id\" NOT IN (SELECT id_horario FROM profesor_horario " +
                                    "WHERE id_mat_espec_aula_secc_tur = "+idMatEspAulSecTur+") " +
              "AND dia = '" + dia + "' " +
              "ORDER BY hora_ini;";
            
            System.out.println(sql);
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            while (rs.next()) {
                i++;
                if (rs.getString("hora_ini").equals(hora_ini)) {
                    optionsHoras = optionsHoras + "<option value=\"lb"+rs.getString("hora_ini")+"\" selected >"+rs.getString("hora_ini");
                    existe=true;
                } else {
                    optionsHoras = optionsHoras + "<option value=\"lb"+rs.getString("hora_ini")+"\" >"+rs.getString("hora_ini");
                }   
            }
            
        }
        if (!existe || hora_ini=="" || hora_ini==null) {
            optionsHoras =  "<option value=\"lbElige\" selected>Elige" + optionsHoras;
        }
        if (i==0) {
            optionsHoras =  "<option value=\"lbSinHora\" selected>Sin Hora";
        }
        selectHoras = selectHoras + optionsHoras + "</select>";

        return selectHoras;  
    }
    
     /**
     * Método para adicionar registros al combo de las Horas Inicio.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarHorasFin(Connection conexion) throws SQLException {        
        String selectHoras = "";
        String optionsHoras = "";
        boolean existe=false;
        int i=0;        
        
        selectHoras = "<select name=\"hora_fin\"  onchange=\"form.submit()\" id=\"hora_fin\" >"; 
        
        String sql;            
        
        if (hora_ini!=null && !"Elige".equals(hora_ini) && !"SinHora".equals(hora_ini)) {
            sql = "SELECT " +
              " DISTINCT h.hora_fin " +
              "FROM " +
              "  public.horario h " +
              "WHERE id_turno = (SELECT id_turno " + 
                                "FROM materia_espec_aula_seccion_turno " + 
                                "WHERE \"Id\"="+idMatEspAulSecTur+")" + 
                "AND hora_fin > '" + hora_ini + "' " +
                "ORDER BY hora_fin;";
            
            //System.out.println(sql);
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();    
            
            while (rs.next()) {
                i++;
                if (rs.getString("hora_fin").equals(hora_fin)) {
                    optionsHoras = optionsHoras + "<option value=\"lb"+rs.getString("hora_fin")+"\" selected >"+rs.getString("hora_fin");
                    existe=true;
                } else {
                    optionsHoras = optionsHoras + "<option value=\"lb"+rs.getString("hora_fin")+"\" >"+rs.getString("hora_fin");
                }   
            }
        }    

        if (!existe || hora_fin=="" || hora_fin==null) {
            optionsHoras =  "<option value=\"lbElige\" selected>Elige" + optionsHoras;
        }
        if (i==0) {
            optionsHoras =  "<option value=\"lbSinHora\" selected>Sin Hora";
        }
        selectHoras = selectHoras + optionsHoras + "</select>";

        return selectHoras;  
    }
    
     /**
     * Método para eliminar registros de horarios de Profesores.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String EliminarHorariosProfesor(Connection conexion)  throws SQLException {
        String sql;        
        
        try {
              sql = "DELETE " +
              "FROM " +
              "public.profesor_horario ph " +
              "WHERE ph.id_mat_espec_aula_secc_tur = " + idMatEspAulSecTur + " ";
            
            //System.out.println("sql-->"+sql);
            
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            conexion.close();
            return "Registro(s) Eliminado(s).";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }
    
    /**
     * Método para validar si hay choque de horario del profesor
     * 
     */
    public boolean HayChoqueHorarioProfesor(Connection conexion)  throws SQLException {
        boolean respuesta = false;
        String sql = "SELECT ph.cedula FROM profesor_horario ph " +
                  "INNER JOIN horario h ON (ph.id_horario=h.\"Id\") " +
                  "INNER JOIN materia_espec_aula_seccion_turno  meast ON (meast.\"Id\"=ph.id_mat_espec_aula_secc_tur) " +
                  "WHERE ph.cedula = " + cedula +
                  " AND h.dia = '" + dia + "'" +
                  " AND (hora_ini BETWEEN '"+hora_ini+"' AND '"+hora_fin+"' OR hora_fin BETWEEN '"+hora_ini+"' AND '"+hora_fin+"')" +
                  " AND meast.periodo = '"+periodo+"';";
            
        PreparedStatement ps;
        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        conexion.close();
        if (rs.next()) respuesta = true;
              
        return respuesta;
    }
    
     /**
     * Método para adicionar el registro Horarios del profesor.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String AdicionarHorarioProfesor(Connection conexion)  throws SQLException {
        String sql;
        
        try {
            sql = "INSERT " +                    
                    "INTO profesor_horario " +
                    "(cedula, id_mat_espec_aula_secc_tur, id_horario) " +    
                    "(SELECT '"+cedula+"', '"+idMatEspAulSecTur+"',\"Id\" " +
                      "FROM horario " +
                      "WHERE dia = '" + dia + "' " +
                      "AND hora_ini BETWEEN '"+hora_ini+"' AND '"+hora_fin+"');";
            
            //System.out.println("INSERT---->"+sql);
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            conexion.close();
            return "Registro Guardado.";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }

    
    /**
     * Método para buscar las secciones unificades y mostrar checkbox.
     * @param conexion
     * @return
     * @throws SQLException 
     */    
    public String BuscarSeccionesUnificadas(Connection conexion)  throws SQLException {
        String sql;
        String listaUnificada = "";
        
        //Buscar las asignaturas unificadas en todas las especialidades
        sql = "SELECT meast.id_espec, e.\"Id\" from especialidad e " +
              "LEFT OUTER JOIN  materia_espec_aula_seccion_turno meast ON (meast.id_espec = e.\"Id\" " +
              "AND sede = '"+sede+"' " +
              "AND periodo = '" + periodo + "' " +
              "AND cod_mat = '" + asignatura + "' " +
              "AND seccion = '" + seccion + "' " +
              "AND aula = '" + aula +"' ) " +
              "WHERE id_espec IS NULL " +
              "ORDER BY id_espec;";
        
        sql = "SELECT COUNT(*), meast.cod_mat " +
            "FROM materia_espec_aula_seccion_turno meast " +
            "FULL OUTER JOIN especialidad e ON (e.\"Id\"=meast.id_espec) " +
            "WHERE periodo || meast.cod_mat || seccion || aula || sede || id_datos_academ = " +
            "			(SELECT periodo || cod_mat || seccion || aula || sede || id_datos_academ " +
            "			FROM materia_espec_aula_seccion_turno " +
            "			WHERE \"Id\" = " + idMatEspAulSecTur + ") " +
            "GROUP BY meast.cod_mat " +
            "HAVING COUNT(*) < (SELECT COUNT(*) FROM materia_espec  WHERE cod_mat = meast.cod_mat)";
                
                
                
        System.out.println("sql="+sql);         
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            listaUnificada =  "<input type=\"checkbox\" name=\"unificada\" value=\"unificada\" >Unificada";
        } else {
            listaUnificada =  "<input type=\"checkbox\" name=\"unificada\" value=\"unificada\" checked >Unificada";
        }
        return listaUnificada;
    }
    
     /**
     * Método para adicionar las asignaturas, secciones y aulas a todas las especialidades.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String AdicionarAsignaturaSeccionAulasUnificadas(Connection conexion)  throws SQLException {
        String sql;
        
        try {            
            sql = "INSERT INTO materia_espec_aula_seccion_turno " +
                  "(periodo, cod_mat, id_espec, seccion, id_turno, aula, sede, id_datos_academ ) " +

                  "(SELECT periodo, meast.cod_mat, me.id_espec, seccion, id_turno, aula, sede, id_datos_academ " +
                  "	FROM materia_espec_aula_seccion_turno meast " +
                  "	FULL OUTER JOIN  materia_espec me ON ( meast.cod_mat = me.cod_mat) " +
                  "	WHERE meast.\"Id\" = "+ idMatEspAulSecTur +" " +
                  "	AND me.id_espec not IN (SELECT id_espec FROM materia_espec_aula_seccion_turno " +
                  "	WHERE periodo || cod_mat || seccion || aula || sede || id_datos_academ = " +
                  "	(SELECT periodo || cod_mat || seccion || aula || sede || id_datos_academ FROM materia_espec_aula_seccion_turno WHERE  \"Id\" ="+ idMatEspAulSecTur +")));";
                    
                        
            //System.out.println("INSERT 1---->"+sql);
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
                    
            conexion.close();
            return "Registro Guardado.";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }
    
    
     /**
     * Método para adicionar las asignaturas, secciones y aulas a todas las especialidades.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String AdicionarHorarioProfesorUnificado(Connection conexion)  throws SQLException {
        String sql;
        
        try {                        
            sql = "INSERT " +                    
                  "INTO profesor_horario " +
                  "(cedula, id_mat_espec_aula_secc_tur, id_horario) " +    
                  "(SELECT '"+cedula+"', meast.\"Id\", h.\"Id\" FROM materia_espec_aula_seccion_turno meast " +
                  "INNER JOIN horario h ON (h.id_turno = meast.id_turno) " +
                  "WHERE periodo || cod_mat || seccion || aula || sede || id_datos_academ = " +
                  "(SELECT periodo || cod_mat || seccion || aula || sede || id_datos_academ " +
                  "FROM materia_espec_aula_seccion_turno " +
                  "WHERE \"Id\" = "+idMatEspAulSecTur+") AND " +
                  "dia = '" + dia + "' " +
                  "AND hora_ini BETWEEN '"+hora_ini+"' AND '"+hora_fin+"');";
            
            //System.out.println("INSERT 2---->"+sql);
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();            
        
            conexion.close();
            return "Registro Guardado.";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }
    
    /**
     * Método para buscar el la tabla los campos de los reportes
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String reporte(Connection conexion,String codigoReporte, String campo)  throws SQLException {
        String sql;       
        
        try {
            sql = "SELECT " + campo + 
                  " FROM reporte " +
                  " WHERE cod_rep = '" + codigoReporte + "'";
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        
            String valor = "";
            if (rs.next()) {
                valor = rs.getString(campo).toString();
            }
            return valor;
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
    }
 
    
    /**
     * Método para buscar el la tabla los campos de los reportes
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String numerosLetras(Connection conexion, int numero)  throws SQLException {
        String sql;       
        
        try {
            sql = "SELECT num_letra " + 
                  " FROM numero_letras " +
                  " WHERE numero = " + numero ;
            
            PreparedStatement ps;        
            ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        
            String valor = "";
            if (rs.next()) {
                valor = rs.getString("num_letra").toString();
            }
            return valor;
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
    }
    
    /**
     * Método para Convertir el me en Letra
     * @param conexion
     * @param numero
     * @return
     * @throws SQLException 
     */
    public String mesLetras(Connection conexion, int mes)  throws SQLException {
        String nombreMes;
        switch(mes) {
            case 1:
               nombreMes = "Enero";
            case 2:
               nombreMes = "Febrero";
               break; 
            case 3:
               nombreMes = "Marzo";
            case 4:
               nombreMes = "Abril";
            case 5:
               nombreMes = "Mayo";
            case 6:
               nombreMes = "Junio";
            case 7:
               nombreMes = "Julio";
               break; 
            case 8:
               nombreMes = "Agosto";
               break; 
            case 9:
               nombreMes = "Septiembre";
               break; 
            case 10:
               nombreMes = "Octubre";
               break; 
            case 11:
               nombreMes = "Noviembre";
               break; 
            case 12:
               nombreMes = "Diciembre";
               break; 
            default: 
                nombreMes = "Mes Inválido";
                break; 
               
        }
        return nombreMes;
        
    }

    
    
     /**
     * Método que busca las secciones creadas en un período.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarDatosPeriodoAcademico(Connection conexion)  throws SQLException {
        String sql;
        String datosPeriodo = "";
        String optionsEstatusPeriodo = "";
        
        //Buscar las asignaturas inscritas

        sql = "SELECT " +
              "p.\"Id\", " +
              "p.periodo, " +
              "p.fec_ini, " +
              "p.fec_fin, " +
              "p.descrip, " +
              "p.estatus, " +
              "p.fec_ini_insc, " +
              "p.fec_fin_insc, " +
              "p.fec_ini_raa, " +
              "p.fec_fin_raa, " +
              "p.id_grupo_horario " +
              "FROM periodo p " + 
              "WHERE p.periodo = '" + periodo + "';";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {            
            datosPeriodo =  "<tr><td align=\"right\">Fecha Inicio:</td> <td align=\"left\">  <input type=\"date\" id=\"fec_ini\" name=\"fec_ini\" value=\"" + rs.getString("fec_ini").toString().substring(0,10) +"\" align=\"center\"/> </td> </tr>";
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Fecha Fin:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin\" name=\"fec_fin\" value=\"" + rs.getString("fec_fin").toString().substring(0,10) + "\" align=\"center\"/></td> </tr>";
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Descripción:</td> <td align=\"left\"> <input type=\"text\" id=\"descrip\" name=\"descrip\" value=\"" + rs.getString("descrip") + "\" align=\"center\"/> </td> </tr>";
            
            optionsEstatusPeriodo =  "<select name=\"estatus\"  onchange=\"form.submit()\" id=\"hora_ini\" >" +                    
                                     "<option value=\"lbA\" >Abierto</option>" +
                                     "<option value=\"lbC\" >Cerrado</option>" +
                                     "<option value=\"lbP\" >Próximo</option>" +
                                     "</select>";
                        
            if (estatus==null || estatus.equalsIgnoreCase("")) {
                estatus = rs.getString("estatus");
            }
            
            switch(estatus) {
                case "A" : optionsEstatusPeriodo = optionsEstatusPeriodo.replace("lbA\"", "lbA\" selected");
                break;
                case "C" : optionsEstatusPeriodo = optionsEstatusPeriodo.replace("lbC\"", "lbC\" selected");
                break;
                case "P" : optionsEstatusPeriodo = optionsEstatusPeriodo.replace("lbP\"", "lbP\" selected");
                break;
            }
            
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Estatus:</td> <td align=\"left\"> " + optionsEstatusPeriodo + " </td> </tr>";
            
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Fecha Inicio de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_insc\" name=\"fec_ini_insc\" value=\"" + rs.getString("fec_ini_insc").toString().substring(0,10) + "\" align=\"center\"/></td> </tr>";
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Fecha Fin de Inscripción:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_insc\" name=\"fec_fin_insc\" value=\"" + rs.getString("fec_fin_insc").toString().substring(0,10) + "\" align=\"center\"/></td> </tr>";
            
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Fecha Inicio Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_ini_raa\" name=\"fec_ini_raa\" value=\"" + rs.getString("fec_ini_raa").toString().substring(0,10) + "\" align=\"center\"/></td> </tr>";
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Fecha Fin Retiro Asignaturas:</td> <td align=\"left\"> <input type=\"date\" id=\"fec_fin_raa\" name=\"fec_fin_raa\" value=\"" + rs.getString("fec_fin_raa").toString().substring(0,10) + "\" align=\"center\"/></td> </tr>";
            datosPeriodo =  datosPeriodo + "<tr><td align=\"right\">Horario del Período:</td> <td align=\"left\"> "+BuscarDatosHorariosPeriodo(conexion)+"</td> </tr>";

        }
        
        conexion.close();
        return datosPeriodo;
    }


     /**
     * Método que busca las secciones creadas en un período.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarDatosPeriodoAdministrativo(Connection conexion)  throws SQLException {
        String sql;
        String datosPeriodo = "";
        String optionsDivisasPeriodo = "";
       
        //Buscar las asignaturas inscritas
        sql = "SELECT " +
              "p.\"Id\", " +
              "p.periodo, " +
              "COALESCE(p.monto_sem,0) AS monto_sem, " +
              "COALESCE(p.nro_cuotas,0) AS nro_cuotas, " +
              "COALESCE(p.periodo_divisas,false) AS periodo_divisas, " +
              "COALESCE(p.valor_divisa,0) AS valor_divisa " +
              "FROM periodo p " + 
              "WHERE p.periodo = '" + periodo + "';";
        

        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        
        optionsDivisasPeriodo =  "<select name=\"periodo_divisas\"  onchange=\"form.submit()\" id=\"periodo_divisas\" name=\"periodo_divisas\">" +
                                 "<option value=\"lbN\" >No</option>" +
                                 "<option value=\"lbS\" >Si</option>" +
                                 "</select>";

        if (rs.next()) {
            
            datosPeriodo = "<tr><td align=\"right\">Costo Período:</td> <td align=\"left\"> <input type=\"number\" id=\"monto_sem\" name=\"monto_sem\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"" + rs.getString("monto_sem") + "\" align=\"center\"/></td> </tr>";
            datosPeriodo = datosPeriodo + "<tr><td align=\"right\">Cantidad de Cuotas:</td> <td align=\"left\"> <input type=\"number\" id=\"nro_cuotas\" name=\"nro_cuotas\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"99\" step=\"1\" value=\"" + rs.getString("nro_cuotas") + "\" align=\"center\"/></td> </tr>";
          
            if (periodo_divisas==null || periodo_divisas.equalsIgnoreCase("")) {
                if (rs.getString("periodo_divisas").equals("t")) {
                    periodo_divisas = "S";
                } else {
                    periodo_divisas = "N";
                }                
            }
                        
            switch(periodo_divisas) {
                case "S" : optionsDivisasPeriodo = optionsDivisasPeriodo.replace("lbS\"", "lbS\" selected");
                break;
                default : optionsDivisasPeriodo = optionsDivisasPeriodo.replace("lbN\"", "lbN\" selected"); 
                break;
            }            
            
            datosPeriodo = datosPeriodo + "<tr><td  align=\"right\">Período en Divisas:</td> <td align=\"left\">"+ optionsDivisasPeriodo + "</td> </tr>";
            datosPeriodo = datosPeriodo + "<tr><td align=\"right\">Valor de la Divisa:</td> <td align=\"left\"> <input type=\"number\" id=\"valor_divisa\" name=\"valor_divisa\" pattern=\"[0-9]+([\\.,][0-9]+)?\" min=\"0\" max=\"999999999999999\" step=\"0.01\" value=\"" + rs.getString("valor_divisa") + "\" align=\"center\"/></td> </tr>";
            
        }
        
        conexion.close();
        return datosPeriodo;
    }
    
    
     /**
     * Método que busca las secciones creadas en un período.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public boolean PeriodoExiste(Connection conexion)  throws SQLException {
        String sql;
        boolean existe = false;
        
        //Buscar las asignaturas inscritas
        sql = "SELECT " +
              "p.\"Id\", " +
              "p.periodo, " +
              "p.monto_sem, " +
              "p.nro_cuotas, " +
              "p.periodo_divisas, " +
              "p.valor_divisa " +
              "FROM periodo p " + 
              "WHERE p.periodo = '" + periodo + "';";
        
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();    

        if (rs.next()) {
            existe = true;
        }
        
        conexion.close();
        return existe;
    }
        
    
    public String AdicionarPeriodo(Connection conexion)  throws SQLException {
        String sql;
        
        try {
              sql = "INSERT " +                    
                    "INTO periodo " +
                    "(periodo, fec_ini, fec_fin, descrip, estatus, fec_ini_insc, fec_fin_insc, fec_ini_raa, fec_fin_raa, id_grupo_horario, monto_sem, nro_cuotas, valor_divisa, periodo_divisas) " +
                    "VALUES " +
                    "('" + periodo + "', '" + 
                      fec_ini + "', '" + 
                      fec_fin + "', '" + 
                      descrip + "', '" + 
                      estatus + "', '" + 
                      fec_ini_insc + "', '" + 
                      fec_fin_insc + "', '" + 
                      fec_ini_raa + "', '" + 
                      fec_fin_raa + "', " + 
                      id_grupo_horario + ", " + 
                      monto_sem + ", " + 
                      nro_cuotas + ", " + 
                      valor_divisa + ", ";
                      if (periodo_divisas.equals("S")) {
                         sql = sql + "true"; 
                      } else {
                         sql = sql + "false"; 
                      }
                      sql = sql + ");";
            
              System.out.println("INSERT---->"+sql);
              PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            conexion.close();
            return "Registro Guardado.";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }
    


    public String ActualizarPeriodo(Connection conexion)  throws SQLException {
        String sql;
        
        try {
              sql = "UPDATE " +                    
                    "periodo " +
                    "SET fec_ini = '" + fec_ini + "', " + 
                    "fec_fin = '" + fec_fin +"', " +
                    "descrip = '" + descrip +"', " +
                    "estatus = '" + estatus +"', " +
                    "fec_ini_insc = '" + fec_ini_insc +"', " +
                    "fec_fin_insc = '" + fec_fin_insc +"', " +
                    "fec_ini_raa = '" + fec_ini_raa +"', " +
                    "fec_fin_raa = '" + fec_fin_raa +"', " +
                    "id_grupo_horario = " + id_grupo_horario +", " +
                    "monto_sem = " + monto_sem +", " +
                    "nro_cuotas = " + nro_cuotas +", " +
                    "valor_divisa = " + valor_divisa +", ";
                    if (periodo_divisas.equals("S")) {
                         sql = sql + "periodo_divisas = true "; 
                      } else {
                         sql = sql + "periodo_divisas = false "; 
                      }
                      
                    sql = sql + "WHERE periodo = '" + periodo + "';";
            
              System.out.println("UPDATE---->"+sql);
              PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            conexion.close();
            return "Registro Guardado.";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }



    
         /**
     * Método que busca las secciones creadas en un período.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String BuscarDatosHorariosPeriodo(Connection conexion)  throws SQLException {
        String sql;
        String optionsHorariosPeriodo = "";
        
        //Buscar las asignaturas inscritas


        
        sql = "SELECT distinct \"Id\", " + 
               "descripcion, " + 
                "duracion, " + 
                "tiempo, " + 
                "nombre_lapso, " + 
                "(SELECT periodo " + 
                "FROM periodo p  " + 
                "WHERE p.id_grupo_horario = hg.\"Id\" AND p.periodo='"+periodo+"') AS periodo " + 
                "FROM horario_grupo hg ";
        
              
        System.out.println("sql--->"+sql);
        
        //System.out.println("sql=====>"+sql);         
        PreparedStatement ps;        
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        //System.out.println("rs.getString(\"periodo_divisas\")--->"+rs.getString("periodo_divisas"));

        optionsHorariosPeriodo =  "<select name=\"id_grupo_horario\"  onchange=\"form.submit()\" id=\"id_grupo_horario\"> ";

        
        while (rs.next()) {
            
            if (id_grupo_horario == null || id_grupo_horario == 0) {                    
                //&& rs.getString("periodo")!=null) {
                if (rs.getString("periodo")!=null) {
                   optionsHorariosPeriodo = optionsHorariosPeriodo + "<option value=\""+rs.getString("Id").toString()+"\" selected >"+rs.getString("descripcion")+"</option>";  
                } else {
                    optionsHorariosPeriodo = optionsHorariosPeriodo + "<option value=\""+rs.getString("Id").toString()+"\" >"+rs.getString("descripcion")+"</option>";
                }
            } else {
                if (id_grupo_horario==rs.getInt("Id")) {
                   optionsHorariosPeriodo = optionsHorariosPeriodo + "<option value=\""+rs.getString("Id").toString()+"\" selected >"+rs.getString("descripcion")+"</option>"; 
                } else {
                    optionsHorariosPeriodo = optionsHorariosPeriodo + "<option value=\""+rs.getString("Id").toString()+"\" >"+rs.getString("descripcion")+"</option>";
                } 
            }
            
        }
        
        optionsHorariosPeriodo = optionsHorariosPeriodo + "</select>";
        
        System.out.println(optionsHorariosPeriodo);
        
        conexion.close();
        return optionsHorariosPeriodo;
    }
    
 
    /**
     * Método para eliminar Período
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public String EliminarPeriodo(Connection conexion)  throws SQLException {
        String sql;        
        
        try {
              sql = "DELETE " +
              "FROM " +
              "periodo " +
              "WHERE periodo = '" + periodo + "';";
                        
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.executeUpdate();
        
            conexion.close();
            return "Registro Eliminado.";
            
        } catch (Exception e) {
            conexion.close();
            return e.getMessage();
        }
        
    }
    
    
    
    
}