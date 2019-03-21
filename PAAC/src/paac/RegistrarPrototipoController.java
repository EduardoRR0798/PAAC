package paac;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduar
 */
public class RegistrarPrototipoController implements Initializable {

    @FXML
    private Label lblMensaje;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfProposito;
    @FXML
    private TextField tfCaracteristicas;
    @FXML
    private TextField tfArchivo;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfInstitucion;
    @FXML
    private TextField tfColaborador;
    @FXML
    private TextField tfAnio;
    @FXML
    private ListView<?> lstAutores;
    @FXML
    private ListView<?> lstColaboradores;
    @FXML
    private ComboBox<?> cbPais;
    @FXML
    private ComboBox<?> cbAutores;
    @FXML
    private ComboBox<?> cbColaboradores;
    @FXML
    private Button btnAgregarColaborador;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCargar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.limitarCampos(tfNombre, 30);
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
    private void aceptar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    @FXML
    private void agregar(ActionEvent event) {
    }

    @FXML
    private void cargar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.
                ExtensionFilter("Archivos PDF", "*.pdf");
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Selecciona un archivo PDF");
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
    }
    
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(tfNombre.getText().isEmpty() || tfAnio.getText().isEmpty() 
                || tfProposito.getText().isEmpty() || tfCaracteristicas.getText().isEmpty() 
                || tfArchivo.getText().isEmpty() || tfEstado.getText().isEmpty()
                || tfInstitucion.getText().isEmpty()) {
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if(Objects.equals(tfNombre.getText(), null)){
            r.setError(true);
            r.setMensaje("El Nombre no puede estar vacio");
            r.setErrorcode(1);
            return r;
        }
        if(!tfAnio.getText().matches("^(\\d{4})+$")){
            r.setError(true);
            r.setMensaje("Ingrese un año valido. Ejem 1984");
            r.setErrorcode(2);
            return r;
        }
        if(Objects.equals(tfProposito.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El proposito no puede estar vacio");
            r.setErrorcode(3);
            return r;
        }
        if(Objects.equals(tfCaracteristicas.getText(), null)){
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