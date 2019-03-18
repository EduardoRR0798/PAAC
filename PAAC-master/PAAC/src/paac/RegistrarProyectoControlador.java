/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Foncho
 */
public class RegistrarProyectoControlador implements Initializable {
    
    @FXML
    private Label errorlbl;
    @FXML
    private TextField nombretxt;
    @FXML
    private TextField tipotxt;
    @FXML
    private TextField cantidadtxt;
    @FXML
    private DatePicker fechaIniciodp;
    @FXML
    private DatePicker fechaFindp;
    @FXML
    private TextArea propositotxt;
    @FXML
    private ComboBox ldgaccb;
    @FXML
    private ComboBox cacb;
    @FXML
    private ComboBox productoscb;
    @FXML
    private Button aceptar;
    @FXML
    private Button cancelar;
            
            
    @FXML
    private void clickCancelar(ActionEvent event) {
        
    }
    
    @FXML
    private void clickAceptar(ActionEvent event) {
        Respuesta r = new Respuesta();
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(nombretxt.getText().isEmpty() || tipotxt.getText().isEmpty()
                || cantidadtxt.getText().isEmpty() || propositotxt.getText().isEmpty()){
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        return r;
    }
}
