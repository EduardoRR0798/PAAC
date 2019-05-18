package paac;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.PatenteJpaController;
import entity.Patente;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Enrique Ceballos Mtz
 */
public class SeleccionarPatenteController implements Initializable {

    @FXML
    private ListView<Producto> lwPatente;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private Miembro miembro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   recuperarProductos();
        
        lwPatente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if (!productos.isEmpty()) {
                            Integer p = lwPatente.getSelectionModel().getSelectedItems().get(0).getIdProducto();
                            abrirVentanaActualizarPatente(lwPatente.getSelectionModel().getSelectedItem());
                        }
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
            System.exit(0);
        }
    }
    
    /**
     * Recupera todos los productos (Patentes) que pertenezca al miembro.
     */
    private void recuperarProductos() {
        ProductoJpaController pJpaC = new ProductoJpaController();
        ProductoMiembroJpaController pmJpaC = new ProductoMiembroJpaController();
        ArrayList<ProductoMiembro> pcss = new ArrayList<>();
        //recupera todos los producto miembro que cuenten con el id del usuario.
        List<ProductoMiembro> pc = pmJpaC.findProductoMiembroEntities();
        for (int i = 0; i < pc.size(); i++) {
            if (Objects.equals(pc.get(i).getIdMiembro().getIdMiembro(), 1)) {
                pcss.add(pc.get(i));
            }
        }
        //recupero TODOS los productos que tengan que ver con ese miembro.
        Producto p;
        ArrayList<Integer> nums = new ArrayList<>();
        ArrayList<Producto> productosTemp = new ArrayList<>();
        for (int i = 0; i < pcss.size(); i++) {
            p = pJpaC.findProducto(pcss.get(i).getIdProducto().getIdProducto());
            productosTemp.add(p);
        }
        //Se eligen los productos que sean Patentes
        PatenteJpaController  pJpaController = new PatenteJpaController();

        //filtrar patentes
        Patente pat;
        
        for (int i = 0; i < productosTemp.size(); i++) {
            pat = pJpaController.buscarPatenteByIdProducto(productosTemp.get(i));
            if (!Objects.equals(pat, null)) {
                productos.add(productosTemp.get(i));
            }
        }
        lwPatente.getItems().setAll(productos);
    }
    /**
     * Este metodo abre una nueva ventana para editar la patente seleccionada\
     * por el miembro.
     * @param p id del producto seleccionado.
     */
    private void abrirVentanaActualizarPatente (Producto p) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "ActualizarPatente.fxml"));
            
            Parent responder = loader.load();
    ActualizarPatenteController controller = loader.getController();
            
            controller.recibirParametros(p);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
  //       ((Node) (btnCancelar)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionarPatenteController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}


