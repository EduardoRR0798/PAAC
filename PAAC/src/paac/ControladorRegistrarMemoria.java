package paac;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Eduar
 */
public class ControladorRegistrarMemoria implements Initializable {

    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfProposito;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfCongreso;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfRango;
    @FXML
    private Label lblMensaje;
    @FXML
    private ComboBox<?> cbAutores;
    @FXML
    private ComboBox<?> cbColaboradores;
    @FXML
    private ListView<?> lstAutores;
    @FXML
    private ListView<?> lstColaboradores;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregarColaborador;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField tfColaborador;
    @FXML
    private ComboBox<?> cbPais;
    @FXML
    private DatePicker dpFecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void aceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if(r.isError()){
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        }else{
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void mostrarCampoColaborador(ActionEvent event) {
        if (!tfColaborador.isVisible()) {
            tfColaborador.setVisible(true);
            tfColaborador.setDisable(false);
            btnAgregar.setVisible(true);
            btnAgregar.setDisable(false);
        } else {
            tfColaborador.setVisible(false);
            tfColaborador.setDisable(true);
            btnAgregar.setVisible(false);
            btnAgregar.setDisable(true);
        }
    }

    @FXML
    private void agregarALista(ActionEvent event) {
    }
    
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(tfTitulo.getText().isEmpty() || tfAnio.getText().isEmpty() 
                || tfProposito.getText().isEmpty() || tfCongreso.getText().isEmpty() 
                || tfCiudad.getText().isEmpty() || tfEstado.getText().isEmpty()
                || tfRango.getText().isEmpty() || dpFecha.getValue() == null 
                || cbPais.getSelectionModel().isEmpty()){
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if(tfTitulo.getText().length()>30){
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 10 caracteres");
            r.setErrorcode(1);
            return r;
        }
        if(!tfAnio.getText().matches("^(\\d{4})+$")){
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(2);
            return r;
        }
        if(Objects.equals(tfProposito.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El proposito no puede estar vacio o ser tan largo");
            r.setErrorcode(3);
            return r;
        }
        if(Objects.equals(tfCongreso.getText(), null)){
            r.setError(true);
            r.setMensaje("El congreso no puede estar vacio.");
            r.setErrorcode(4);
            return r;
        }
        if(Objects.equals(tfEstado.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El nombre de la editorial no puede ser mayor a 10 caracteres y no permite caracteres especiales");
            r.setErrorcode(5);
            return r;
        }
        if(!tfRango.getText().matches("^((0|[1-9]+[0-9]*)-(0|[1-9]+[0-9]*);|(0|[1-9]+[0-9]*);)*?((0|[1-9]+[0-9]*)-(0|[1-9]+[0-9]*)|(0|[1-9]+[0-9]*)){1}$")){
            r.setError(true);
            r.setMensaje("Ingrese un formato valido. Ejem. 1-2");
            r.setErrorcode(6);
            return r;
        }
        if(Objects.equals(dpFecha.getValue(), null)){
            r.setError(true);
            r.setMensaje("La fecha no puede estar vacia.");
            r.setErrorcode(7);
            return r;
        }
        if(cbPais.getSelectionModel().isEmpty()){
            r.setError(true);
            r.setMensaje("Seleccione un pais.");
            r.setErrorcode(8);
            return r;
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }
}