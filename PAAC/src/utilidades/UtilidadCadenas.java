/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import javafx.beans.value.ObservableValue;

/**
 *
 * @author Eduar
 */
public class UtilidadCadenas {
     /**
     * Este metodo impide que el campo de texto sea mayor a un numero de 
     * caracteres fijo.
     * @param tf textField a limitar
     * @param maximo numero maximo de caracteres permitidos.
     */
    public void limitarCampos(javafx.scene.control.TextField tf, int maximo) {
        
        tf.textProperty().addListener((final ObservableValue<? extends 
                String> ov, final String oldValue, final String newValue) -> {
            
            if (tf.getText().length() > maximo) {
                
                String s = tf.getText().substring(0, maximo);
                tf.setText(s);
            }
        });
    }   
}
