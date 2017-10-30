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
public class Alumno {
    protected Integer idAlumno;
    protected String cedula;
    protected String apellidos;
    protected String nombres;
    protected String nacionalidad;
    protected String ident_correo;
    protected String tel_hab;
    protected String tel_ofc;
    protected String tel_cel;
    protected String edo_civil;    
    protected String sexo;
    protected String fec_nac;
    protected String empresa;
    protected String sede;
    protected String nro_nsi;
    protected String lugar_nac;
    protected String especialidad;
    protected String codEspecialidad;
    protected String direccion;
    protected String turno;
    protected String codTurno;
    protected String periodo;
       
    public Alumno(String cedula) {
        this.cedula = cedula;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
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

    public String getEdo_civil() {
        return edo_civil;
    }

    public void setEdo_civil(String edo_civil) {
        this.edo_civil = edo_civil;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getNro_nsi() {
        return nro_nsi;
    }

    public void setNro_nsi(String nro_nsi) {
        this.nro_nsi = nro_nsi;
    }

    public String getLugar_nac() {
        return lugar_nac;
    }

    public void setLugar_nac(String lugar_nac) {
        this.lugar_nac = lugar_nac;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
    
    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }    
    
    public String getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }

    public String getCodTurno() {
        return codTurno;
    }

    public void setCodTurno(String codTurno) {
        this.codTurno = codTurno;
    }
    
    
}
