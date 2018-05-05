/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.modelo;

/**
 *
 * @author gerson
 */

public class Usuario {
    protected String login;
    protected String password;
    protected String password2;
    protected String nombreUsu;
    protected int idUsu;
    protected String tipoUsu;
    protected String cedula;
    protected String estatus;
    protected String sexo;
    protected int tiempoSesion;

    public Usuario(String login, String password, String cedula) {
        this.login = login;
        this.password = password;
        this.cedula = cedula;
    }            

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }   
    
    public String getNombreUsu() {
        return nombreUsu;
    }

    public void setNombreUsu(String nombreUsu) {
        this.nombreUsu = nombreUsu;
    }
    
    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }
    
    public String getTipoUsu() {
        return tipoUsu;
    }

    public void setTipoUsu(String tipoUsu) {
        this.tipoUsu = tipoUsu;
    }
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }    
    
    public int getTiempoSesion() {
        return tiempoSesion;
    }

    public void setTiempoSesion(int tiempoSesion) {
        this.tiempoSesion = tiempoSesion;
    }    
              
}
