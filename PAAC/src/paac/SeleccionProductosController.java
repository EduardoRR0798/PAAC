package paac;

import entity.Miembro;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class SeleccionProductosController extends ControladorProductos implements Initializable {

    @FXML
    private ListView<String> lstProductos;
    @FXML
    private Button btnCancelar;
    private final ObservableList<String> productos = FXCollections.observableArrayList
        ("Articulo", "Capitulo de libro", "Libro", "Memoria", "Patente", "Prototipo", "Tesis");
    private Miembro miembro;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstProductos.setItems(productos);
        
        lstProductos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    int seleccion = lstProductos.getSelectionModel().getSelectedIndex();
                    try {
                        aceptar(seleccion);
                    } catch (IOException ex) {
                        Logger.getLogger(SeleccionProductosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }    
    
    /**
     * Recibe el miembro de la ventana anterior.
     * @param m 
     */
    public void recibirParametros(Miembro m) {
        this.miembro = m;
    }
    
    /**
     * Cancela la operacion volviendo a la pantalla anterior.
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
            abrirMenu(miembro);
            ((Node) btnCancelar).getScene().getWindow().hide();
        }
    }
    
    /**
     * Abre la pantalla correspondiente al producto a registrar.
     * @param seleccion indice del elemento de la lista seleccionado.
     */
    private void aceptar(int seleccion) throws IOException {
        switch (seleccion) {
            case 0:
                super.registrarArticulo(miembro);
                ((Node) btnCancelar).getScene().getWindow().hide();
                break;
            case 1:
                break;
            case 2:
                super.registrarLibro(miembro);
                ((Node) btnCancelar).getScene().getWindow().hide();
                break;
            case 3:
                super.registrarMemoria(miembro);
                ((Node) btnCancelar).getScene().getWindow().hide();
                break;
            case 4:
                break;
            case 5:
                super.registrarPrototipo(miembro);
                ((Node) btnCancelar).getScene().getWindow().hide();
                break;
            case 6:
                super.registrarTesis(miembro);
                ((Node) btnCancelar).getScene().getWindow().hide();
                break;
            default:
                break;
        }
    }
}
