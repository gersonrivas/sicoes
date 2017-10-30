/*******************************************************************************
 * Clase para leer los parámetros del archivo properties General de configuraciones
 * 
 * Autor: Gerson Rivas
 *******************************************************************************/
package com.iupg.sicoes.propiedades;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ParamConfig {
    private static final String BUNDLE_NAME = "com.iupg.sicoes.propiedades.paramConfig"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private ParamConfig() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
