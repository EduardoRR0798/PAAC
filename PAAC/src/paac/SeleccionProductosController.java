package paac;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class SeleccionProductosController implements Initializable {

    @FXML
    private ListView<String> lstProductos;
    @FXML
    private Button btnCancelar;
    private final ObservableList<String> productos = FXCollections.observableArrayList
        ("Articulo", "Capitulo de libro", "Libro", "Memoria", "Patente", "Prototipo", "Tesis");
    
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
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        int seleccion = lstProductos.getSelectionModel().getSelectedIndex();
                        System.out.println(seleccion);
                        aceptar(seleccion);
                    }
                }
            }
        });
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
            System.exit(0);
        }
    }
    
    /**
     * Abre la pantalla correspondiente al producto a registrar.
     * @param seleccion indice del elemento de la lista seleccionado.
     */
    private void aceptar(int seleccion) {
        switch (seleccion) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                abrirVentanaRegistro("RegistrarMemoria.fxml");
                break;
            case 4:
                break;
            case 5:
                abrirVentanaRegistro("RegistrarPrototipo.fxml");
                break;
            case 6:
                break;
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     * @param p id del producto seleccionado.
     */
    private void abrirVentanaRegistro(String pantalla) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(pantalla));
            
            Parent responder = loader.load();
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
            ((Node) (btnCancelar)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
