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
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class GenerarCurriculumController extends ControladorProductos implements Initializable {

    @FXML
    private ComboBox<Integer> cb_anioInicio;
    @FXML
    private ComboBox<Integer> cb_anioFin;
    @FXML
    private Button btn_generar;
    @FXML
    private Button btn_regresar;
    @FXML
    private ListView<Producto> lst;
    @FXML
    private Label lbl_error;
    //otros
    private ObservableList<Integer> anios = FXCollections.observableArrayList();
    private Miembro miembro;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private int origen = 2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }

    @FXML
    private void generar(ActionEvent event) {
        Respuesta r = validarDatos();
        if(r.isError()){
            lbl_error.setText(r.getMensaje());
            lbl_error.setVisible(true);
        }else{
            lbl_error.setVisible(false);
            recuperarProductos();
            lst.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        if (!productos.isEmpty()) {
                            Producto p = lst.getSelectionModel().getSelectedItem();
                            consultarProducto(miembro, p, origen);
                            ((Node) btn_generar).getScene().getWindow().hide();
                        }
                    }
                }
            }
        });
        }
    }

    @FXML
    private void regresar(ActionEvent event) {
        Alert cancela = new Alert(Alert.AlertType.CONFIRMATION);
        cancela.setTitle("Cancelar proceso");
        cancela.setHeaderText(null);
        cancela.initStyle(StageStyle.UTILITY);
        cancela.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancela.showAndWait();
        if (result.get() == ButtonType.OK) {
            abrirMenu(miembro);
            ((Node) btn_regresar).getScene().getWindow().hide();
        }
    }

    private Respuesta validarDatos() {
        Respuesta r = new Respuesta();
        if (cb_anioInicio.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("Elige un año inicial");
            return r;
        }
        if (cb_anioFin.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setErrorcode(2);
            r.setMensaje("Elige un año final");
            return r;
        }
        if (cb_anioInicio.getSelectionModel().getSelectedItem() > cb_anioFin.getSelectionModel().getSelectedItem()) {
            r.setError(true);
            r.setErrorcode(3);
            r.setMensaje("El año inicial debe ser menor al año final");
            return r;
        }
        r.setError(false);
        r.setErrorcode(0);
        r.setMensaje("Correcto");
        return r;
    }
    
    public void recibirParametros(Miembro m) {
        this.miembro = m;
        for (int i = LocalDate.now().getYear(); i > 1970; i--) {
            anios.add(i);
        }
        cb_anioInicio.setItems(anios);
        cb_anioFin.setItems(anios);
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
        //recupero TODOS los productos que tengan que ver con ese miembro y esten dentro del lapso de tiempo.
        Producto p;
        for (int i = 0; i < pcss.size(); i++) {
            p = pJpaC.findProducto(pcss.get(i).getIdProducto().getIdProducto());
            if(p!=null){
                if(p.getAnio()>=cb_anioInicio.getSelectionModel().getSelectedItem()){
                    if(p.getAnio()<=cb_anioFin.getSelectionModel().getSelectedItem()){
                        productos.add(p);
                    }
                }
            }
        }
        lst.getItems().setAll(productos);
    }

}
