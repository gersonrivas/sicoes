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
public class Register extends Usuario {
    private String confirEmail;

    String mensaje;
    String pagina;
    
    /**
     * Contructor de la Clase Register
     * @param login
     * @param cedula
     * @param confirm 
     */
    
    public Register(String login, String cedula, String confirm) {
        super(login,null,cedula);        
        this.confirEmail = confirm;
    }

    public String getConfirEmail() {
        return confirEmail;
    }

    public void setConfirEmail(String confirEmail) {
        this.confirEmail = confirEmail;
    }
    
    /**
     * Método para validar los campos que se solicitan en el registro de usuario
     * @return 
     */
    public String validaCampos() {
        Validador validador = new Validador();
        if (login.isEmpty() || confirEmail.isEmpty() || cedula.isEmpty()) {
            mensaje = "Todos los campos son requeridos";                   
        } else {
            if (!login.equals(confirEmail)) {
                mensaje = "La confirmación del correo, no son iguales";
            } else {
                //No hay campos vacios, veo que la direccion de email sea válida
                if (validador.ValidarEmail(login)==false) {
                    mensaje = "La direccion de correo no es válida"; 
                } else {
                    mensaje = "";
                }
            }                    
        }
        return mensaje;
    }
}
