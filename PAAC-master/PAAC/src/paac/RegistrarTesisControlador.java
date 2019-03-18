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
public class RegistrarTesisControlador implements Initializable {

    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private Button agregar;
    @FXML
    private Label errorlbl;
    @FXML
    private TextField titulotxt;
    @FXML
    private TextField gradotxt;
    @FXML
    private TextField registrotxt;
    @FXML
    private TextField descripciontxt;
    @FXML
    private TextField usuariotxt;
    @FXML
    private TextArea propositotxt;
    @FXML
    private ComboBox autorescb;
    @FXML
    private ComboBox colaboradorescb;
    @FXML
    private ComboBox paisescb;
    @FXML
    private ComboBox clasificacioncb;
    @FXML
    private ComboBox estadocb;
    @FXML
    private DatePicker fechadp;

    @FXML
    private void clickCancelar(ActionEvent event) {

    }

    @FXML
    private void clickAceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if (r.isError()) {
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
        } else {
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
        }
    }

    @FXML
    private void clickAgregar(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (titulotxt.getText().isEmpty() || gradotxt.getText().isEmpty()
                || propositotxt.getText().isEmpty() || registrotxt.getText().isEmpty()
                || descripciontxt.getText().isEmpty() || usuariotxt.getText().isEmpty()) {
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if (titulotxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 255 caracteres");
            r.setErrorcode(1);
            return r;
        }
        if (paisescb.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setMensaje("Se debe selecccionar un pais");
            r.setErrorcode(2);
            return r;
        }
        if (fechadp.getValue() == null) {
            r.setError(true);
            r.setMensaje("Se debe selecccionar una fecha");
            r.setErrorcode(2);
            return r;
        }
        if (gradotxt.getText().length() > 150) {
            r.setError(true);
            r.setMensaje("El grado no puede tener mas de 150 caracteres");
            r.setErrorcode(2);
            return r;
        }
        if (propositotxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El proposito no puede tener mas de 255 caracteres");
            r.setErrorcode(3);
            return r;
        }
        if (registrotxt.getText().length() > 20) {
            r.setError(true);
            r.setMensaje("El registro no puede tener mas de 20 caracteres");
            r.setErrorcode(4);
            return r;
        }
        if (clasificacioncb.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setMensaje("Se debe selecccionar una clasificación internacional");
            r.setErrorcode(2);
            return r;
        }
        if (descripciontxt.getText().length() > 10) {
            r.setError(true);
            r.setMensaje("La descripcion no puede tener mas de 10 caracteres");
            r.setErrorcode(5);
            return r;
        }
        if (usuariotxt.getText().length() > 20) {
            r.setError(true);
            r.setMensaje("El usuario no debe ser mayor a 20");
            r.setErrorcode(6);
            return r;
        }
        if (estadocb.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setMensaje("Se debe selecccionar un estado actual");
            r.setErrorcode(2);
            return r;
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }
}
