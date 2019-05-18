package paac;

import entity.Miembro;
import entity.Producto;
import entity.ProductoMiembro;
import entity.Prototipo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
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
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.PrototipoJpaController;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class SeleccionarPrototipoController extends ControladorProductos implements Initializable {

    @FXML
    private ListView<Producto> lst;
    @FXML
    private Button btnCancelar;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private Miembro miembro;
    
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lst.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (!productos.isEmpty()) {
                        Producto p = lst.getSelectionModel().getSelectedItem();
                        actualizarPrototipo(miembro, p);
                        ((Node) btnCancelar).getScene().getWindow().hide();
                    }
                }
            }
        });
    }    

    /**
     * Recibe el miembro de la ventana anterior.
     * @param miembro 
     */
    public void recibirParametros(Miembro miembro) {
        this.miembro = miembro;
        recuperarProductos();
    }
    
    /**
     * Cancela una operacion.
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
     * Recupera todos los productos asociados a una memoria que pertenezca al miembro.
     */
    private void recuperarProductos() {
        ProductoJpaController pJpaC = new ProductoJpaController();
        ProductoMiembroJpaController pmJpaC = new ProductoMiembroJpaController();
        ArrayList<ProductoMiembro> pcss = new ArrayList<>();
        //recupero todos los producto miembro que cuenten con el id del usuario.
        List<ProductoMiembro> pc = pmJpaC.findProductoMiembroEntities();
        for (int i = 0; i < pc.size(); i++) {
            if (Objects.equals(pc.get(i).getIdMiembro().getIdMiembro(), miembro.getIdMiembro())) {
                pcss.add(pc.get(i));
            }
        }
        //recupero TODOS los productos que tengan que ver con ese miembro.
        Producto p;
        ArrayList<Producto> productosTemp = new ArrayList<>();
        for (int i = 0; i < pcss.size(); i++) {
            p = pJpaC.findProducto(pcss.get(i).getIdProducto().getIdProducto());
            productosTemp.add(p);
        }
        //filtro los productos que si sean prototipos.
        PrototipoJpaController pJpaController = new PrototipoJpaController();
        //filtrar prototipos
        Prototipo art;
        for (int i = 0; i < productosTemp.size(); i++) {
            art = pJpaController.encontrarPrototipoPorIdProducto(productosTemp.get(i));
            if (!Objects.equals(art, null)) {
                productos.add(productosTemp.get(i));
            }
        }
        lst.getItems().setAll(productos);
    }
    
    /**
     * Este metodo abre una nueva ventana para editar el articulo seleccionado 
     * por el miembro.
     * @param p id del producto seleccionado.
     */
    private void abrirVentanaEditarMemoria(Producto p) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "ActualizarPrototipo.fxml"));
            
            Parent responder = loader.load();
            ActualizarPrototipoController controller = loader.getController();
            
            Miembro m = new Miembro();
            controller.recibirParametros(p, m);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
             ((Node) (btnCancelar)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionarMemoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
