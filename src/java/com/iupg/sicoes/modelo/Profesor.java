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
public class Profesor {
    protected Integer idProfesor;
    protected String cedula;
    protected String apellidos;
    protected String nombres;
    protected String nacionalidad;
    protected String ident_correo;
    protected String tel_hab;
    protected String tel_ofc;
    protected String tel_cel;
    protected String sexo;
    protected String fec_nac;
    protected String fec_ing;
    protected String direccion;
    protected String periodo;
    protected String asignatura;
    protected String seccion;
    protected Integer idMatEspAulSecTur;
    protected String sede;
   
    public Profesor(String cedula) {
        this.cedula = cedula;
    }    
    
    public Integer getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getIdent_correo() {
        return ident_correo;
    }

    public void setIdent_correo(String ident_correo) {
        this.ident_correo = ident_correo;
    }

    public String getTel_hab() {
        return tel_hab;
    }

    public void setTel_hab(String tel_hab) {
        this.tel_hab = tel_hab;
    }

    public String getTel_ofc() {
        return tel_ofc;
    }

    public void setTel_ofc(String tel_ofc) {
        this.tel_ofc = tel_ofc;
    }

    public String getTel_cel() {
        return tel_cel;
    }

    public void setTel_cel(String tel_cel) {
        this.tel_cel = tel_cel;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFec_nac() {
        return fec_nac;
    }

    public void setFec_nac(String fec_nac) {
        this.fec_nac = fec_nac;
    }

    public String getFec_ing() {
        return fec_ing;
    }

    public void setFec_ing(String fec_ing) {
        this.fec_ing = fec_ing;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    public String getAsignatura() {
        return asignatura;
    }
    
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    
    public Integer getIdMatEspAulSecTur() {
        return idMatEspAulSecTur;
    }

    public void setIdMatEspAulSecTur(Integer idMatEspAulSecTur) {
        this.idMatEspAulSecTur = idMatEspAulSecTur;
    }
    
    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }    
    
}
