package paac;

import entity.Lgac;
import entity.Miembro;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.LgacJpaController;
import java.security.SecureRandom;
import java.math.BigInteger;
import persistence.MiembroJpaController;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class RegistrarMiembroController implements Initializable {

    @FXML
    private Label lblMensaje;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfContrasenia;
    @FXML
    private TextField tfSNI;
    @FXML
    private Button btnGenerar;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private CheckBox chkSi;
    @FXML
    private CheckBox chkNo;
    @FXML
    private ComboBox<Lgac> cbLGAC;
    @FXML
    private ComboBox<String> cbPE;
    @FXML
    private ComboBox<String> cbEstado;
    private final ObservableList<String> pes = FXCollections.observableArrayList("Ingenieria de Software", "Redes y Servicios de Computo", "Estadistica");
    private ObservableList<Lgac> lgacs = FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList("Miembro del cuerpo academico", "Responsable del cuerpo academico");
    private final ObservableList<String> estados = FXCollections.observableArrayList("Activo", "Inactivo");
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recuperarLGAC();
        cbTipo.getItems().setAll(tipos);
        cbPE.getItems().setAll(pes);
        cbEstado.getItems().setAll(estados);
        chkSi.setSelected(true);
    }    

    /**
     * Comprueba que se pueda registrar el usuario, y lo hace si es posible.
     * @param event Clic en el botom Registrar.
     */
    @FXML
    private void registrar(ActionEvent event) {
        Respuesta r = new Respuesta();
        r = validarDatos();
        if (r.isError()) {
            lblMensaje.setText(r.getMensaje());
        } else {
            registrarMiembro();
            lblMensaje.setText("Usuario registrado exitosamente");
            limpiarCampos();
        }
        
    }

    /**
     * Cancela la operacion.
     * @param event Clic en el boton Cancelar.
     */
    @FXML
    private void cancelar(ActionEvent event) {
        Alert cancelar = new Alert(Alert.AlertType.CONFIRMATION);
        cancelar.setTitle("Cancelar proceso");
        cancelar.setHeaderText(null);
        cancelar.initStyle(StageStyle.UTILITY);
        cancelar.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancelar.showAndWait();
        if(result.get() == ButtonType.OK) {
            //do something
            
        }
    }
    
    /**
     * LLena el campo de la contrase;a con una contrasenia generada de forma 
     * aleatoria.
     * @param event Clic en el boton Generar.
     */
    @FXML
    private void generar(ActionEvent event) {
        tfContrasenia.setText(generateRandomText());
    }
    
    /**
     * recupera todas las LGAC de la base de datos y llena el comboBox con el
     * nombre de cada una de ellas.
     */
    private void recuperarLGAC() {
        LgacJpaController lJpaC = new LgacJpaController();
        List<Lgac> lcs = lJpaC.findLgacEntities();
        for (int i = 0; i < lcs.size(); i++) {
            lgacs.add(lcs.get(i));
        }
        cbLGAC.getItems().setAll(lgacs);
    }
    
    /**
     * Genera una cadena de caracteres aleatoria para poder 
     * @return 
     */
    public static String generateRandomText() {
        SecureRandom random = new SecureRandom();
        String text = new BigInteger(130, random).toString(32);
        return text;
    }
    
    /**
     * Valida que todos los campos esten llenos.
     * @return Respuesta con el error o sin el.
     */
    public Respuesta validarDatos() {
        Respuesta r = new Respuesta();
        if (tfNombre.getText().trim().isEmpty()
                || tfUsuario.getText().trim().isEmpty()
                || tfContrasenia.getText().trim().isEmpty()
                || cbTipo.getSelectionModel().isEmpty()
                || cbEstado.getSelectionModel().isEmpty()
                || cbLGAC.getSelectionModel().isEmpty()
                || cbPE.getSelectionModel().isEmpty()
                || (!chkSi.isSelected() && !chkNo.isSelected())) {
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("No puede haber campos vacios.");
            return r;
        }
        if (!comprobarNombre()) {
            r.setError(true);
            r.setErrorcode(2);
            r.setMensaje("Este nombre ya se encuentra registrado.");
            return r;
        }
        if (!comprobarUsuario()) {
            r.setError(true);
            r.setErrorcode(3);
            r.setMensaje("Este usuario ya se encuentra registrado.");
            return r;
        }
        if (!comprobarSNI()) {
            r.setError(true);
            r.setErrorcode(3);
            r.setMensaje("Este sni ya se encuentra registrado.");
            return r;
        }
        r.setError(false);
        r.setErrorcode(0);
        r.setMensaje("Usuario actualizado exitosamente.");
        return r;
    }
    
    /**
     * Registra al miembro en la base de datos.
     */
    public void registrarMiembro() {
        Miembro m = new Miembro();
        m.setContrasenia(tfContrasenia.getText());
        m.setNombre(tfNombre.getText().trim());
        m.setUsuario(tfUsuario.getText().trim());
        m.setTipo(cbTipo.getSelectionModel().getSelectedIndex() + 1);
        m.setEstado(cbEstado.getSelectionModel().getSelectedItem());
        m.setSni(tfSNI.getText().trim());
        if (chkSi.isSelected()) {
            m.setPromep("Si");
        } else {
            m.setPromep("No");
        }
        m.setPeImpacta(cbPE.getSelectionModel().getSelectedItem());
        MiembroJpaController mJpaC = new MiembroJpaController();
        mJpaC.create(m);
    }
    
    /**
     * Deselecciona el checkbox Si.
     */
    public void deseleccionarSi() {
        chkSi.setSelected(false);
    }
    
    /**
     * Deselecciona el checkbox No.
     */
    public void deseleccionarNo() {
        chkNo.setSelected(false);
    }
    
    /**
     * Comprueba que no exista el nombre ingresado en la base de datos.
     * @return 
     */
    public boolean comprobarNombre() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarNombre(tfNombre.getText());
    }
    
    /**
     * Comprueba que el usuario no exista en la base de datos.
     * @return 
     */
    public boolean comprobarUsuario() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarUsuario(tfUsuario.getText());
    }
    
    /**
     * Comprueba que el sni no este registrado en la base de datos.
     * @return 
     */
    public boolean comprobarSNI() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarISN(tfSNI.getText().trim());
    }
    
    /**
     * Vacios todos los campos de la interfaz.
     */
    public void limpiarCampos() {
        tfNombre.setText("");
        tfSNI.setText("");
        tfContrasenia.setText("");
        tfUsuario.setText("");
        cbTipo.getSelectionModel().clearSelection();
        cbEstado.getSelectionModel().clearSelection();
        cbLGAC.getSelectionModel().clearSelection();
        
    }
}
