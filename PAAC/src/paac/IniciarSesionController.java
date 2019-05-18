package paac;

import entity.Miembro;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import persistence.MiembroJpaController;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera.
 */
public class IniciarSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfContrasenia;
    @FXML
    private Button btnIniciar;
    @FXML
    private Label lblMensaje;
    private Miembro miembro;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.excluirEspacios(tfUsuario);
        uc.excluirEspacios(tfContrasenia);
    }    

    /**
     * Verifica si es posible acceder al sistema.
     * @param event Clic en el boton Iniciar Sesion.
     */
    @FXML
    private void iniciar(ActionEvent event) {
        Respuesta r = validarDatos();
        if (r.isError()) {
            lblMensaje.setText(r.getMensaje());
        } else {
            lblMensaje.setText("Iniciando sesion.");
            abrirMenu();
        }
    }
    
    /**
     * Valida que los datos ingresados sean correctos.
     * @return Respuesta de tipo error o exito.
     */
    private Respuesta validarDatos() {
        Respuesta r = new Respuesta();
        if (tfUsuario.getText().trim().isEmpty() 
                || tfContrasenia.getText().trim().isEmpty()) {
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("No puede haber campos vacios.");
            return r;
        }
        if (!verificarNombre()) {
            r.setError(true);
            r.setErrorcode(2);
            r.setMensaje("Usuario o contraseña invalidos.");
            return r;
        }
        r.setError(false);
        r.setErrorcode(0);
        return r;
    }
    
    /**
     * Verifica que el nombre no exista en la base de datos.
     * @return true si el nombre y contrase;a existen en la base de datos
     * false si no.
     */
    public boolean verificarNombre() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        UtilidadCadenas uc = new UtilidadCadenas();
        String cont = uc.hacerHashAContrasenia(tfContrasenia.getText());
        int id = mJpaC.comprobarLog(tfUsuario.getText().trim(), cont);
        if (id != 0) {
            miembro = mJpaC.findMiembro(id);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Regresa el miembro que esta accediendo al sistema.
     * @return 
     */
    public Miembro getMiembro() {
        return miembro;
    }
    
    /**
     * Abre el menu principal.
     */
    public void abrirMenu() {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MenuPrincipal.fxml"));
            
            Parent responder = loader.load();
            MenuPrincipalController controller = loader.getController();
            
            controller.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
            ((Node) (btnIniciar)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
