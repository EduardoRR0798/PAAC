package paac;

import entity.Articulo;
import entity.Miembro;
import entity.Producto;
import entity.ProductoMiembro;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
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
import persistence.ArticuloJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class SeleccionarArticuloController extends ControladorProductos implements Initializable {

    @FXML
    private ListView<Producto> lst;
    @FXML
    private Button btnCancelar;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private Miembro miembro;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lst.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (!productos.isEmpty()) {
                        Producto p = lst.getSelectionModel().getSelectedItem();
                        actualizarArticulo(miembro, p);
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
        //filtro los productos que si sean articulos.
        ArticuloJpaController aJpaController = new ArticuloJpaController();
        //filtrar articulos
        Articulo art;
        for (int i = 0; i < productosTemp.size(); i++) {
            art = aJpaController.encontrarArticuloPorIdProducto(productosTemp.get(i));
            if (!Objects.equals(art, null)) {
                productos.add(productosTemp.get(i));
            }
        }
        lst.getItems().setAll(productos);
    }
    
}
