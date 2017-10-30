/*
 * Clase que continen los atributos Generales necesarios 
 * para operar el Sistema,
 * para los usuarios de Registro y Control y Administración.
 */
package com.iupg.sicoes.modelo;

/**
 *
 * @author gerson
 */
public class General {
    protected String sede;
    protected String periodo;
    protected String asignatura;
    protected String semestreTrimestre;
    protected String tipoLapsoPeriodo;
    protected String especialidad;
    protected String turno;
    protected String seccion;
    protected String aula;
    protected Integer idMatEspAulSecTur;
    protected String dia;
    protected String hora_ini;
    protected String hora_fin;
    protected String cedula;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getHora_ini() {
        return hora_ini;
    }

    public void setHora_ini(String hora_ini) {
        this.hora_ini = hora_ini;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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

    public String getSemestreTrimestre() {
        return semestreTrimestre;
    }

    public void setSemestreTrimestre(String semestreTrimestre) {
        this.semestreTrimestre = semestreTrimestre;
    }
    
    public String getTipoLapsoPeriodo() {
        return tipoLapsoPeriodo;
    }

    public void setTipoLapsoPeriodo(String tipoLapsoPeriodo) {
        this.tipoLapsoPeriodo = tipoLapsoPeriodo;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
    
    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }
}
