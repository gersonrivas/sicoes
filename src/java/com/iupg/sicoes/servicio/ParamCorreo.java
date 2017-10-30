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
 * Clase que obtiene las propiedades del correo que se envía al usuario.
 * @author Gerson Rivas
 * @version 1.0
 */
@Configuration
@PropertySource("classpath:com/iupg/sicoes/propiedades/paramConfig.properties")
public class ParamCorreo {
    @Value( "${correo.imgCabecera}" )
    String correo_imgCabecera;
    @Value( "${correo.imgSrc}" )
    String correo_imgSrc;
    @Value( "${correo.remitente}" )
    String correo_remitente;
    @Value( "${correo.rutaArcAdj}" )
    String correo_rutaArcAdj;

    public String getCorreo_imgCabecera() {
        return correo_imgCabecera;
    }

    public void setCorreo_rutaImg(String correo_imgCabecera) {
        this.correo_imgCabecera = correo_imgCabecera;
    }

    public String getCorreo_imgSrc() {
        return correo_imgSrc;
    }

    public void setCorreo_imgSrc(String correo_imgSrc) {
        this.correo_imgSrc = correo_imgSrc;
    }

    public String getCorreo_remitente() {
        return correo_remitente;
    }

    public void setCorreo_remitente(String correo_remitente) {
        this.correo_remitente = correo_remitente;
    }
    
    public String getCorreo_rutaArcAdj() {
        return correo_rutaArcAdj;
    }

    public void setCorreo_rutaArcAdj(String correo_rutaArcAdj) {
        this.correo_rutaArcAdj = correo_rutaArcAdj;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }        
}
