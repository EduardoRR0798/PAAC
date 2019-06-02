/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;

/**
 * FXML Controller class
 *
 * @author ponch
 */
public class SeleccionarProductoConsultaController extends ControladorProductos implements Initializable {

    @FXML
    private Button btn_cancelar;
    @FXML
    private ListView<Producto> lst;
    private Miembro miembro;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private int origen = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lst.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if (!productos.isEmpty()) {
                            Producto p = lst.getSelectionModel().getSelectedItem();
                            consultarProducto(miembro, p, origen);
                            ((Node) btn_cancelar).getScene().getWindow().hide();
                        }
                    }
                }
            }
        });
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        Alert cancela = new Alert(Alert.AlertType.CONFIRMATION);
        cancela.setTitle("Cancelar proceso");
        cancela.setHeaderText(null);
        cancela.initStyle(StageStyle.UTILITY);
        cancela.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancela.showAndWait();
        if (result.get() == ButtonType.OK) {
            abrirMenu(miembro);
            ((Node) btn_cancelar).getScene().getWindow().hide();
        }
    }

    public void recibirParametros(Miembro m) {
        this.miembro = m;
        recuperarProductos();
    }
    
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
        for (int i = 0; i < pcss.size(); i++) {
            p = pJpaC.findProducto(pcss.get(i).getIdProducto().getIdProducto());
            if(p!=null){
                productos.add(p);
            }
        }
        lst.getItems().setAll(productos);
    }
}
