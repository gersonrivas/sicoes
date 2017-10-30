/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.servicio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Clase que obtiene las propiedades de conexión a la base de datos.
 * @author Gerson Rivas
 * @version 1.0
 */

@Configuration
@PropertySource(value = "classpath:com/iupg/sicoes/propiedades/paramConfig.properties")
public class ParamBaseDatos {
    @Value( "${jdbc.driverClassName}" )
    String jdbc_driverClassName;
    @Value( "${jdbc.url}" )
    String jdbc_url;
    @Value( "${jdbc.hostDB}" )
    String jdbc_hostDB;
    @Value( "${jdbc.port}" )
    String jdbc_port;
    @Value( "${jdbc.namedb}" )
    String jdbc_namedb;
    @Value( "${jdbc.username}" )
    String jdbc_username;
    @Value( "${jdbc.password}" )
    String jdbc_password;
    @Value( "${host.name}" )
    String host_name;
    @Value( "${host.port}" )
    String host_port;

    public String getJdbc_driverClassName() {
        return jdbc_driverClassName;
    }

    public void setJdbc_driverClassName(String jdbc_driverClassName) {
        this.jdbc_driverClassName = jdbc_driverClassName;
    }

    public String getJdbc_url() {
        return jdbc_url;
    }

    public void setJdbc_url(String jdbc_url) {
        this.jdbc_url = jdbc_url;
    }

    public String getJdbc_hostDB() {
        return jdbc_hostDB;
    }

    public void setJdbc_hostDB(String jdbc_hostDB) {
        this.jdbc_hostDB = jdbc_hostDB;
    }

    public String getJdbc_port() {
        return jdbc_port;
    }

    public void setJdbc_port(String jdbc_port) {
        this.jdbc_port = jdbc_port;
    }

    public String getJdbc_namedb() {
        return jdbc_namedb;
    }

    public void setJdbc_namedb(String jdbc_namedb) {
        this.jdbc_namedb = jdbc_namedb;
    }

    public String getJdbc_username() {
        return jdbc_username;
    }

    public void setJdbc_username(String jdbc_username) {
        this.jdbc_username = jdbc_username;
    }

    public String getJdbc_password() {
        return jdbc_password;
    }

    public void setJdbc_password(String jdbc_password) {
        this.jdbc_password = jdbc_password;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getHost_port() {
        return host_port;
    }

    public void setHost_port(String host_port) {
        this.host_port = host_port;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }    
}

