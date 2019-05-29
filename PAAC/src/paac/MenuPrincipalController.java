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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera.
 */
public class MenuPrincipalController extends ControladorProductos implements Initializable {

    @FXML
    private MenuBar mbMenu;
    @FXML
    private MenuItem miRegistrar;
    @FXML
    private MenuItem miActualizar;
    @FXML
    private MenuItem miConsultar;
    @FXML
    private MenuItem miPersonal;
    @FXML
    private MenuItem miLaborales;
    @FXML
    private MenuItem miEscolares;
    @FXML
    private MenuItem miCerrar;
    @FXML
    private MenuItem miMiembro;
    @FXML
    private MenuItem miEstado;
    @FXML
    private Menu mControlCa;
    @FXML
    private ImageView imgUV;
    private Miembro miembro;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //No es necesario hacer nada.
    }    

    /**
     * Recibe el miembro de la ventana que lo invoco.
     * @param miembro Miembro que inicio sesion.
     */
    public void recibirParametros(Miembro miembro) {
        this.miembro = miembro;
        if (miembro.getTipo() == 2) {
            mControlCa.setVisible(true);
        } else {
            mControlCa.setVisible(false);
        }
    }
    
    /**
     * Abre la ventana de registro de nuevos productos.
     * @param event Clic en la opcion de registrar productos.
     */
    @FXML
    private void registrarProductos(ActionEvent event) {
        seleccionarProductos(miembro);
        ((Node) (mbMenu)).getScene().getWindow().hide();
    }

    /**
     * Abre la ventana de actualizacion de productos.
     * @param event Clic en la opcion de actualizar productos dentro del menu.
     */
    @FXML
    private void actualizarProductos(ActionEvent event) {
        seleccionarProductosActualizar(miembro);
        ((Node) mbMenu).getScene().getWindow().hide();
    }

    /**
     * Abre la ventana de visualizacion de productos.
     * @param event Clic en la opcion de Contultar Productos.
     */
    @FXML
    private void consultarProductos(ActionEvent event) {
        
        ((Node) mbMenu).getScene().getWindow().hide();
    }

    /**
     * Abre la opcion de modificacion de datos personales.
     * @param event Clic en la opcion de Datos Personales.
     */
    @FXML
    private void actualizarPersonales(ActionEvent event) {
        
        ((Node) mbMenu).getScene().getWindow().hide();
    }

    /**
     * Abre la opcion de consultar datos laborales.
     * @param event Clic en la opcion de Datos Laborales.
     */
    @FXML
    private void consultarLaborales(ActionEvent event) {
        
        ((Node) mbMenu).getScene().getWindow().hide();
    }

    /**
     * Abre la ventana de datos Escolares.
     * @param event Clic en la opcion de datos laborales.
     */
    @FXML
    private void consultarEscolares(ActionEvent event) {
        
        ((Node) mbMenu).getScene().getWindow().hide();
    }

    /**
     * Abre la ventana de Login.
     * @param event Clic en la opcion Cerrar sesion.
     */
    @FXML
    private void cerrarSesion(ActionEvent event) {
        irAVentana("IniciarSesion.fxml", "Iniciar Sesion");
        ((Node) mbMenu).getScene().getWindow().hide();
    }
    
    /**
     * Abre la ventana para cambiar el estado de un miembro.
     * @param event Clic en la opcion de cambiar estado.
     */
    @FXML
    private void cambiarEstado(ActionEvent event) {
        seleccionarMiembros(miembro);
        ((Node) mbMenu).getScene().getWindow().hide();
    }

    /**
     * Abre la ventana de registro de un nuevo miembro para hacer uso del sistema.
     * @param event Clic en la opcion Registrar miembro.
     */
    @FXML
    private void registrarMiembro(ActionEvent event) {
        registrarNuevoMiembro(miembro);
        ((Node) mbMenu).getScene().getWindow().hide();
    }
    
    /**
     * Abre una nueva ventana.
     */
    private void irAVentana(String fxml, String titulo) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            
            Parent seleccion = loader.load();
            Scene scene = new Scene(seleccion);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.fullScreenProperty();
            stage.setScene(scene);
            stage.show();
            ((Node) (mbMenu)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
