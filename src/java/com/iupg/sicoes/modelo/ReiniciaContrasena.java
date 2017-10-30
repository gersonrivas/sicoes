/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iupg.sicoes.modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gerson
 */
public class ReiniciaContrasena extends Usuario {
    String password1;
    String password2; 
    
    String mensaje;
    String pagina;
    
    public ReiniciaContrasena(String login, String password, String password1, String password2) {
        super(login,password,null);
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public String validaCampos() {
        Pattern p = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        Matcher m = p.matcher(this.getLogin());
        Validador v = new Validador();
        if (this.getLogin().isEmpty() || this.getPassword().isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            mensaje = "Todos los campos son requeridos";                   
        } else {
            if (!password1.equals(password2)) {
                mensaje = "La nueva contraseña y la confirmación, no coinciden.";
            } else {                    
                //No hay campos vacios, veo que la direccion de email sea válida
                if (!m.find()) {
                   mensaje = "La direccion de correo no es válida"; 
                } else {
                    //Validando la complejidad de la contraseña
                    if (!v.isUsernameOrPasswordValid(password1)) {
                        mensaje = "La contraseña no es válida.  \n\nDebe contener mínimo seis caracteres, \n\nuna letra minúscula, una mayúscula \n\n y un caracter especial como @#=?+*_%/.>:";
                    } else {
                        mensaje = "";
                    }
                }
            }                                
        }
        return mensaje;
    }     
}
