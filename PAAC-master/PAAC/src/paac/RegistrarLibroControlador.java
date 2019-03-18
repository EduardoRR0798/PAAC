/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Foncho
 */
public class RegistrarLibroControlador implements Initializable {

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
    private TextField aniotxt;
    @FXML
    private TextField isbntxt;
    @FXML
    private TextField editorialtxt;
    @FXML
    private TextField paginastxt;
    @FXML
    private TextField ediciontxt;
    @FXML
    private TextField ejemplarestxt;
    @FXML
    private TextArea propositotxt;
    @FXML
    private ComboBox autorescb;
    @FXML
    private ComboBox colaboradorescb;
    @FXML
    private ComboBox<Pais> paisescb;

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
        llenarCBPais();
    }

    public void llenarCBPais() {
        ObservableList<Pais> listaPaises = FXCollections.observableArrayList();
        ArrayList<Pais> arrayPais = new ArrayList<>();
        
        arrayPais.stream().forEach((p) -> {
            listaPaises.add(p);
        });

        paisescb.setItems(listaPaises);
        paisescb.setVisibleRowCount(10);
    }

    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (titulotxt.getText().isEmpty() || aniotxt.getText().isEmpty()
                || propositotxt.getText().isEmpty() || isbntxt.getText().isEmpty()
                || editorialtxt.getText().isEmpty() || paginastxt.getText().isEmpty()
                || ediciontxt.getText().isEmpty() || ejemplarestxt.getText().isEmpty()) {
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
        if (!aniotxt.getText().matches("^(\\d{4})+$")) {
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(3);
            return r;
        }
        if (propositotxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El proposito no puede tener mas de 255 caracteres");
            r.setErrorcode(4);
            return r;
        }
        if (!isbntxt.getText().matches("^(97(8|9))?\\d{9}(\\d|X)$")) {
            r.setError(true);
            r.setMensaje("Ingrese un número ISBN válido de 10 o 13 dígitos");
            r.setErrorcode(5);
            return r;
        }
        if (editorialtxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El nombre de la editorial no puede ser mayor a 255 caracteres");
            r.setErrorcode(6);
            return r;
        }
        if (!paginastxt.getText().matches("^[+]?\\d*$") && paginastxt.getText().length() < 10) {
            r.setError(true);
            r.setMensaje("Solo se permite números en Páginas");
            r.setErrorcode(7);
            return r;
        }
        if (!ediciontxt.getText().matches("^[+]?\\d*$") && ediciontxt.getText().length() < 10) {
            r.setError(true);
            r.setMensaje("Solo se permite números en Edición");
            r.setErrorcode(8);
            return r;
        }
        if (!ejemplarestxt.getText().matches("^[+]?\\d*$") && ejemplarestxt.getText().length() < 10) {
            r.setError(true);
            r.setMensaje("Solo se permite números en Ejemplares");
            r.setErrorcode(9);
            return r;
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }
}
