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
public class Graduados {

    protected Integer idGraduado;
    protected String cedula;
    protected String apellidos;
    protected String nombres;
    protected String codEspecialidad;    
    protected String especialidad;
    protected String codTurno;
    protected String turno;
    protected String fecGraduacion;
    protected String libro;
    protected String tomo;
    protected String folio;
    protected String numero;
    protected String resolucion;
    protected String fecActa;
    protected String fecFirma;
    protected String regGrado;
    protected String Aranceles;
    protected String identCorreo;
    protected String sede;
    
    
    public Graduados(String cedula) {
        this.cedula = cedula;
    }

    public Integer getIdGraduado() {
        return idGraduado;
    }

    public void setIdGraduado(Integer idGraduado) {
        this.idGraduado = idGraduado;
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

    
    
    public String getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(String codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodTurno() {
        return codTurno;
    }

    public void setCodTurno(String codTurno) {
        this.codTurno = codTurno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getFecGraduacion() {
        return fecGraduacion;
    }

    public void setFecGraduacion(String fecGraduacion) {
        this.fecGraduacion = fecGraduacion;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getTomo() {
        return tomo;
    }

    public void setTomo(String tomo) {
        this.tomo = tomo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getFecActa() {
        return fecActa;
    }

    public void setFecActa(String fecActa) {
        this.fecActa = fecActa;
    }

    public String getFecFirma() {
        return fecFirma;
    }

    public void setFecFirma(String fecFirma) {
        this.fecFirma = fecFirma;
    }

    public String getRegGrado() {
        return regGrado;
    }

    public void setRegGrado(String regGrado) {
        this.regGrado = regGrado;
    }

    public String getAranceles() {
        return Aranceles;
    }

    public void setAranceles(String Aranceles) {
        this.Aranceles = Aranceles;
    }

    public String getIdentCorreo() {
        return identCorreo;
    }

    public void setIdentCorreo(String identCorreo) {
        this.identCorreo = identCorreo;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }



}
