/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.modelo;

import com.iupg.sicoes.servicio.ParamBaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



/**
 * Clase de conexión a la Base de Datos.
 * @author gerson
 * @version 1.0
 */
public class DaoConexion {
    // Definición de variables.    
    public Connection conexion;
    ApplicationContext ctx = new AnnotationConfigApplicationContext(ParamBaseDatos.class);
    ParamBaseDatos pv = ctx.getBean(ParamBaseDatos.class);    
    
    String driverDb = pv.getJdbc_driverClassName();
    String urlDb = pv.getJdbc_url();
    String userDb = pv.getJdbc_username();
    String passDb = pv.getJdbc_password();
    String nameDb = pv.getJdbc_namedb();
    String portDb = pv.getJdbc_port();
    String hostDb = pv.getJdbc_hostDB();
    
    //((AnnotationConfigApplicationContext)ctx);
   
    //Conectar a la Base de datos
    /**
     * Método de conexión
     * @return retorna la conexión a la Base de Datos para trabajar con ella.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */

    public Connection ConexionBD() throws SQLException,ClassNotFoundException{
        try {
        //
        Class.forName(driverDb);        
        
         conexion=DriverManager.getConnection(urlDb+hostDb+":"+portDb+"/"+nameDb,userDb, passDb);
         return conexion;
        } catch (ExceptionInInitializerError e) {
            System.out.println("Error al conectar a la Base de Datos..."+e.getMessage());
            return null;
        }
    }
    /**
     * 
     * @return Retorna un bollean para saber si hubo o no éxito en la desconexión.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public boolean Desconectar() throws SQLException, ClassNotFoundException{
        try {
            conexion.close();
            return true;
        } catch (Exception e) {
            System.out.println("Desconectando..."+e.getLocalizedMessage());
            return false;
        }        
    } 
}
