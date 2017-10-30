/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.modelo;
import java.sql.*;


/**
 *
 * @author gerson
 */
public class DaoUsuario extends Usuario {
    public Connection conexion;
    
    /*public DaoUsuario(Connection conexion) {
        super();
        this.conexion = conexion;
    }*/
    public DaoUsuario(String login, String password, String cedula) {
        super(login,password, cedula);
    }
   
    /**
     * Metodo para buscar cuenta del usuario por cuenta de correo.
     * @param conexion
     * @return 
     * @throws SQLException 
     */    
    public boolean CuentaUsuarioRegistrada(Connection conexion) throws SQLException{
        String sql = "SELECT * FROM usuario WHERE ident_correo=lower('"+this.getLogin()+"');";
        System.out.println(sql);       
        PreparedStatement ps;
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        return rs.next();
    }
   
    //Metodo para registrar una cuenta
    public void RegistrarUsuario() throws SQLException{
        String sql = "INSERT INTO usuarios(email,password,name) VALUES ('"+this.getLogin()+"','"+this.getPassword()+"','"+this.getLogin()+"')";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.executeUpdate();
    }
    
    /**
     * Metodo para buscar la cuenta del Usuario por Correo y Contraseña.
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public boolean CuentaUsuarioPorCorreoContrasena(Connection conexion) throws SQLException{
        String sql;
        sql = "SELECT usuario.\"Id\", usuario.ident_correo, categ_usu, estatus, COALESCE(alumno.apellidos, COALESCE(profesor.apellidos,pers_reg_ctrl.apellidos))||' '||COALESCE(alumno.nombres, COALESCE(profesor.nombres,pers_reg_ctrl.nombres)) AS usuario, " +
              "COALESCE(alumno.cedula, COALESCE(profesor.cedula,pers_reg_ctrl.cedula)) AS cedula, " + 
              "COALESCE(alumno.sexo, COALESCE(profesor.sexo,pers_reg_ctrl.sexo)) AS sexo, tiempo_sesion "+
              "FROM usuario " +
                
              "INNER JOIN usuario_categ ON (usuario.categ_usu =  usuario_categ.cod_categ) " +
                
              "LEFT OUTER JOIN alumno ON (usuario.\"Id\" =  alumno.id_usu) " +
              "LEFT OUTER JOIN profesor ON (usuario.\"Id\" =  profesor.id_usu) " +
              "LEFT OUTER JOIN pers_reg_ctrl ON (usuario.\"Id\" =  pers_reg_ctrl.id_usu) " +
              "WHERE usuario.ident_correo=lower('"+this.getLogin()+"') AND contrasena=md5('"+this.getPassword()+"');";  
                       
        //sql = "SELECT \"Id\", nombre FROM usuario WHERE ident_correo='"+this.login+"' AND contrasena=md5('"+this.password+"')";
        PreparedStatement ps;
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            this.setNombreUsu(rs.getString("usuario"));
            this.setIdUsu(Integer.parseInt(rs.getString("Id")));
            this.setTipoUsu(rs.getString("categ_usu"));
            this.setCedula(rs.getString("cedula"));
            this.setEstatus(rs.getString("estatus"));
            this.setSexo(rs.getString("sexo"));
            this.setTiempoSesion(Integer.parseInt(rs.getString("tiempo_sesion")));
            return true;
        } else {
            return false;
        }
    }
    
     /**
     * Metodo para verificar si el usuario está registrado por número de cédula o correo
     * @param conexion
     * @param comparadorLogico 
     * @return
     * @throws SQLException 
     */
    public boolean CuentaUsuarioPorCedulaCorreo(Connection conexion, String comparadorLogico) throws SQLException{
        String sql;
        sql = "SELECT usuario.\"Id\", " +
              " COALESCE(alumno.apellidos, COALESCE(profesor.apellidos,pers_reg_ctrl.apellidos))||' '||COALESCE(alumno.nombres, COALESCE(profesor.nombres,pers_reg_ctrl.nombres)) AS usuario," +
              " COALESCE(alumno.cedula,profesor.cedula) as cedula, categ_usu, estatus, " +
              " COALESCE(alumno.sexo, COALESCE(profesor.sexo,pers_reg_ctrl.sexo)) AS sexo"+
              " FROM usuario " +
              " LEFT OUTER JOIN alumno ON (usuario.\"Id\" = alumno.id_usu)" +
              " LEFT OUTER JOIN profesor ON (usuario.\"Id\" = profesor.id_usu)" +
              " LEFT OUTER JOIN pers_reg_ctrl ON (usuario.\"Id\" = pers_reg_ctrl.id_usu)" +
              " WHERE (alumno.cedula = '"+this.getCedula()+"' OR profesor.cedula = '"+this.getCedula()+"' OR pers_reg_ctrl.cedula = '"+this.getCedula()+"') "+comparadorLogico+" usuario.ident_correo = lower('"+this.getLogin()+"');";
        //sql = "SELECT \"Id\", nombre FROM usuario WHERE ident_correo='"+this.login+"' AND contrasena=md5('"+this.password+"')";
        //System.out.println(sql);
        PreparedStatement ps;
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            this.setNombreUsu(rs.getString("usuario"));
            this.setIdUsu(Integer.parseInt(rs.getString("Id")));
            this.setTipoUsu(rs.getString("categ_usu"));
            this.setCedula(rs.getString("cedula"));
            this.setEstatus(rs.getString("estatus"));
            this.setSexo(rs.getString("sexo"));
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Metodo para buscar el tipo de usuario, Alumno, Profesor o Personal Administrativo.
     * @param conexion
     * @return
     * @throws SQLException 
     */    
    public ResultSet cedulaExite(Connection conexion) throws SQLException{
        String sql;
        sql = "SELECT COALESCE(alumno.\"Id\", COALESCE(profesor.\"Id\", pers_reg_ctrl.\"Id\")) AS Id, " +
              "COALESCE(alumno.cedula, COALESCE(profesor.cedula, pers_reg_ctrl.cedula)) AS cedula, " +
              "COALESCE(alumno.sexo, COALESCE(profesor.sexo, pers_reg_ctrl.sexo)) AS sexo, " +
              "COALESCE(alumno.apellidos ||' '|| alumno.nombres, COALESCE(profesor.apellidos ||' '|| profesor.nombres, pers_reg_ctrl.apellidos ||' '|| pers_reg_ctrl.nombres)) AS nombre, " +
              "CASE WHEN alumno.cedula IS NOT NULL THEN 'A' " +
              " WHEN profesor.cedula IS NOT NULL THEN 'P' " +
              " WHEN pers_reg_ctrl.cedula IS NOT NULL THEN 'R' " +
              "END AS categ_usu " +
              "FROM alumno FULL OUTER JOIN profesor USING (cedula) " +
              "FULL OUTER JOIN pers_reg_ctrl USING (cedula ) " +
              "WHERE alumno.cedula = '"+this.getCedula()+"' OR profesor.cedula = '"+this.getCedula()+"' OR pers_reg_ctrl.cedula = '"+this.getCedula()+"';";      
 
        //System.out.println(sql);
        PreparedStatement ps;
        ps = conexion.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        return rs;
    }
    
     /**
     * Metodo para registrar una cuenta de usuario
     * @param daoConexion
     * @param ident_correo
     * @param contrasena
     * @param categ_usu
     * @param id
     * @throws SQLException 
     */    
    public void registarUsuario(Connection daoConexion, String ident_correo, String contrasena, String categ_usu, String id) throws SQLException{
        String tablaAct="";
        String sql = "INSERT INTO usuario (ident_correo,contrasena,categ_usu) VALUES (lower('"+ident_correo+"'),md5('"+contrasena+"'),'"+categ_usu+"'); ";
        
        switch (categ_usu) {
             case "A": tablaAct="alumno"; break;
             case "P": tablaAct="profesor"; break;   
             case "R": tablaAct="pers_reg_ctrl"; break;   
         }
        
        sql = sql + "UPDATE "+tablaAct+" SET id_usu = (SELECT currval('public.\"usuario_Id_seq\"')) " +
              "WHERE \"Id\" = "+ id + ";";
        
        PreparedStatement ps = daoConexion.prepareStatement(sql);
        //System.out.println(sql);
        ps.executeUpdate();
    }

    /**
     * Metodo para iniciar el estatus de una cuenta
     * @param daoConexion
     * @param ident_correo
     * @param contrasenaActual
     * @throws SQLException 
     */
    public void iniciarContrasenaUsuario(Connection daoConexion, String ident_correo, String contrasenaActual) throws SQLException{
        String sql = "UPDATE usuario SET estatus = 'I', contrasena = md5('"+contrasenaActual+"'), fec_reini = now() WHERE ident_correo = '"+ident_correo+"'; ";
        
        PreparedStatement ps = daoConexion.prepareStatement(sql);
        System.out.println(sql);
        ps.executeUpdate();        
    }    

    /**
     * Metodo para cambiar el estatus activo a una cuenta de usuario.
     * @param daoConexion
     * @param ident_correo
     * @param contrasenaActual
     * @param contrasenaNueva
     * @throws SQLException 
     */
    public void activarUsuario(Connection daoConexion, String ident_correo, String contrasenaActual, String contrasenaNueva) throws SQLException{
        String sql = "UPDATE usuario SET estatus = 'A', contrasena = md5('"+contrasenaNueva+"'), fec_act = now() WHERE ident_correo = lower('"+ident_correo+"') AND contrasena = md5('"+contrasenaActual+"'); ";
        
        PreparedStatement ps = daoConexion.prepareStatement(sql);
        System.out.println(sql);
        ps.executeUpdate();        
    }
    
    
}
