/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.Lgac;
import entity.Miembro;
import entity.Producto;
import entity.ProductoProyecto;
import entity.Proyecto;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.LgacJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoProyectoJpaController;
import persistence.ProyectoJpaController;

/**
 * FXML Controller class
 *
 * @author Foncho
 */
public class RegistrarProyectoControlador extends ControladorProductos implements Initializable {

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextArea txt_descripcion;
    @FXML
    private TextField txt_tipo;
    @FXML
    private TextField txt_cantidad;
    @FXML
    private DatePicker fechaFindp;
    @FXML
    private DatePicker fechaIniciodp;
    @FXML
    private ComboBox<Lgac> cb_ldgac;
    @FXML
    private Label lbl_error;
    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private MenuButton mb_productos;
    @FXML
    private ListView<Producto> lv_productos;
    private ObservableList<Producto> productos = FXCollections.observableArrayList();
    private ObservableList<Lgac> lgacs = FXCollections.observableArrayList();
    Miembro miembro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.recuperarLGAC();
        recuperarProductos();
    }

    void recibirParametros(Miembro miembro) {
        this.miembro = miembro;
        recuperarProductos();
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        Alert cancela = new Alert(Alert.AlertType.CONFIRMATION);
        cancela.setTitle("Cancelar proceso");
        cancela.setHeaderText(null);
        cancela.initStyle(StageStyle.UTILITY);
        cancela.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancela.showAndWait();
        if (result.get() == ButtonType.OK) {
            abrirMenu(miembro);
            ((Node) cancelar).getScene().getWindow().hide();
        }
    }

    @FXML
    private void clickAceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if (r.isError()) {
            lbl_error.setText(r.getMensaje());
            lbl_error.setVisible(true);
        } else {
            lbl_error.setText(r.getMensaje());
            lbl_error.setVisible(true);
            registrarProyecto();
        }
    }

    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (txt_nombre.getText().isEmpty() || txt_descripcion.getText().isEmpty()
                || txt_tipo.getText().isEmpty() || txt_cantidad.getText().isEmpty()
                || fechaFindp.getValue() == null || fechaIniciodp.getValue() == null
                || cb_ldgac.getValue() == null) {
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if (txt_nombre.getText().length() > 52) {
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 52 caracteres");
            r.setErrorcode(2);
            return r;
        }
        if (fechaIniciodp.getValue().isAfter(fechaFindp.getValue())) {
            r.setError(true);
            r.setMensaje("La fecha inicial no puede ser después de la fecha final");
            r.setErrorcode(3);
            return r;
        }
        if (txt_descripcion.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("La descripcion no puede tener mas de 255 caracteres");
            r.setErrorcode(4);
            return r;
        }
        if (txt_tipo.getText().trim().length()>255) {
            r.setError(true);
            r.setMensaje("El tipo no puede tener mas de 255 caracteres");
            r.setErrorcode(5);
            return r;
        }
        if (!txt_cantidad.getText().matches("^[+]?\\d*$")) {
            r.setError(true);
            r.setMensaje("La cantidad solo acepta numeros");
            r.setErrorcode(6);
            return r;
        }
        if (lv_productos.getItems().isEmpty()) {
            r.setError(true);
            r.setMensaje("Es necesario al menos un producto para poder registrar un proyecto");
            r.setErrorcode(6);
            return r;
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }

    private void recuperarProductos() {
        ProductoJpaController pJpaC = new ProductoJpaController();
        List<Producto> ms = new ArrayList<>();
        ms = pJpaC.findAll();
        for (int i = 0; i < ms.size(); i++) {
            productos.add(ms.get(i));
        }
        iniciarProductos();
    }

    public void iniciarProductos() {
        System.out.println("HOLA desde iniciar");
        CheckMenuItem cmi;
        ArrayList<CheckMenuItem> items = new ArrayList<>();
        for (int i = 0; i < productos.size(); i++) {
            cmi = new CheckMenuItem(productos.get(i).toString());
            cmi.setUserData(productos.get(i));
                items.add(cmi);
        }
        mb_productos.getItems().setAll(items);
        for (final CheckMenuItem item : items) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

                if (newValue) {
                    lv_productos.getItems().add((Producto) item.getUserData());
                } else {
                    lv_productos.getItems().remove((Producto) item.getUserData());
                }
            });
        }
    }

    private void registrarProyecto() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(txt_nombre.getText());
        proyecto.setFechaInicio(Date.from(fechaIniciodp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        proyecto.setFechaFin(Date.from(fechaFindp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        proyecto.setDescripcion(txt_descripcion.getText());
        proyecto.setIdLGACApoyo(cb_ldgac.getSelectionModel().getSelectedItem());
        proyecto.setTipoApoyo(txt_tipo.getText());
        proyecto.setCantidadApoyo(Integer.valueOf(txt_cantidad.getText()));

        List<Proyecto> list = new ArrayList<>();
        list.add(proyecto);
        ProyectoJpaController pjc = new ProyectoJpaController();
        pjc.create(proyecto);

        ObservableList<Producto> colas = lv_productos.getItems();
        ProductoProyectoJpaController ppjc = new ProductoProyectoJpaController();

        ProductoProyecto pp;
        for (int i = 0; i < colas.size(); i++) {
            pp = new ProductoProyecto();
            pp.setProyecto(proyecto);
            pp.setProducto(colas.get(i));
            try {
                ppjc.create(pp);
            } catch (Exception ex) {
                Logger.getLogger(ControladorRegistrarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

        private void recuperarLGAC() {
        LgacJpaController lJpaC = new LgacJpaController();
        List<Lgac> lcs = lJpaC.findLgacEntities();
        for (int i = 0; i < lcs.size(); i++) {
            lgacs.add(lcs.get(i));
        }
        cb_ldgac.getItems().setAll(lgacs);
    }
    
}
