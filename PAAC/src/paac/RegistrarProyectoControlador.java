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
 * FXML Controller class
 *
 * @author Foncho
 */
public class RegistrarProyectoControlador implements Initializable {
    @FXML
    private TextField nombretxt;
    @FXML
    private TextArea propositotxt;
    @FXML
    private DatePicker fechaFindp;
    @FXML
    private DatePicker fechaIniciodp;
    @FXML
    private ComboBox<?> ldgaccb;
    @FXML
    private Label errorlbl;
    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private ComboBox<?> cacb;
    @FXML
    private ComboBox<?> productoscb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickCancelar(ActionEvent event) {
    }

    @FXML
    private void clickAceptar(ActionEvent event) {
    }
    
}
