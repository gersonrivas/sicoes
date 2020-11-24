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
    protected String estatus;
    protected String periodo_divisas;
    
    protected String fec_ini;
    protected String fec_fin;
    protected String descrip;
    protected String fec_ini_insc;
    protected String fec_fin_insc;
    protected String fec_ini_raa;
    protected String fec_fin_raa;
    protected Integer id_grupo_horario;
    
    protected double monto_sem;
    protected Integer nro_cuotas;
    protected double valor_divisa;
    
    

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
    
    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getPeriodo_divisas() {
        return periodo_divisas;
    }

    public void setPeriodo_divisas(String periodo_divisas) {
        this.periodo_divisas = periodo_divisas;
    }

     public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getFec_ini_insc() {
        return fec_ini_insc;
    }

    public void setFec_ini_insc(String fec_ini_insc) {
        this.fec_ini_insc = fec_ini_insc;
    }

    public String getFec_fin_insc() {
        return fec_fin_insc;
    }

    public void setFec_fin_insc(String fec_fin_insc) {
        this.fec_fin_insc = fec_fin_insc;
    }

    public String getFec_ini_raa() {
        return fec_ini_raa;
    }

    public void setFec_ini_raa(String fec_ini_raa) {
        this.fec_ini_raa = fec_ini_raa;
    }

    public String getFec_fin_raa() {
        return fec_fin_raa;
    }

    public void setFec_fin_raa(String fec_fin_raa) {
        this.fec_fin_raa = fec_fin_raa;
    }

    public Integer getId_grupo_horario() {
        return id_grupo_horario;
    }

    public void setId_grupo_horario(Integer id_grupo_horario) {
        this.id_grupo_horario = id_grupo_horario;
    }

    
    
    
    
    public double getMonto_sem() {
        return monto_sem;
    }

    public void setMonto_sem(double monto_sem) {
        this.monto_sem = monto_sem;
    }

    public Integer getNro_cuotas() {
        return nro_cuotas;
    }

    public void setNro_cuotas(Integer nro_cuotas) {
        this.nro_cuotas = nro_cuotas;
    }

    public double getValor_divisa() {
        return valor_divisa;
    }

    public void setValor_divisa(double valor_divisa) {
        this.valor_divisa = valor_divisa;
    }
    
    
}
