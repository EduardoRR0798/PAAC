/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.Colaborador;
import entity.Miembro;
import entity.Pais;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import entity.Tesis;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.ColaboradorJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.TesisJpaController;
import persistence.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author Foncho
 */
public class ActualizarTesisControlador extends ControladorProductos implements Initializable {

    @FXML
    private TextField titulotxt;
    @FXML
    private ComboBox<Pais> paisescb;
    @FXML
    private TextArea propositotxt;
    @FXML
    private TextField registrotxt;
    @FXML
    private TextField descripciontxt;
    @FXML
    private TextField usuariotxt;
    @FXML
    private DatePicker fechadp;
    @FXML
    private TextField gradotxt;
    @FXML
    private ComboBox<String> clasificacioncb;
    @FXML
    private ComboBox<String> estadocb;
    @FXML
    private TextField txt_archivo;
    @FXML
    private Label errorlbl;
    @FXML
    private Label lbl_nombreColaborador;
    @FXML
    private TextField txt_nombreColaborador;
    @FXML
    private Button btn_guardarColaborador;
    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private MenuButton mb_autores;
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private MenuButton mb_colaboradores;
    @FXML
    private Button btnAgregar;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private TextField txt_numHojas;

    ObservableList<String> estados = FXCollections.observableArrayList(
            "Planeado",
            "En proceso",
            "Terminado");
    ObservableList<String> clasif = FXCollections.observableArrayList(
            "Clase 1",
            "Clase 2",
            "Clase 3");
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private ArrayList<Miembro> mInvolucrados = new ArrayList<>();
    private ArrayList<Colaborador> cInvolucrados = new ArrayList<>();
    private File file;
    private Producto p;
    private Tesis t;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clasificacioncb.setItems(clasif);
        estadocb.setItems(estados);
        colaboradores = super.recuperarColaboradores();
        miembros = super.recuperarMiembros();
        paises = recuperarPaises();
        paisescb.setItems(paises);
        iniciarMiembros();
        iniciarColaboradores();
    }

    @FXML
    private void subirArchivo(ActionEvent event) {
        txt_archivo.setEditable(false);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf");
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Selecciona un archivo PDF");
        Stage stage = new Stage();
        File fl = fileChooser.showOpenDialog(stage);
        if (!Objects.equals(fl, null)) {
            if (fl.length() <= 10000000) {
                file = fl;
                txt_archivo.setText(file.getPath());
            } else {
                errorlbl.setText("El archivo debe ser menor a 10 MB.");
                errorlbl.setVisible(true);
            }
        }
    }

    @FXML
    private void clickGuardarColaborador(ActionEvent event) {
        if (!txt_nombreColaborador.isDisabled()) {
            if (!Objects.equals(txt_nombreColaborador.getText().trim(), null)) {
                if (txt_nombreColaborador.getText().trim().length() > 10) {
                    Colaborador c = new Colaborador();
                    c.setNombre(txt_nombreColaborador.getText().trim());
                    ColaboradorJpaController cJpaC = new ColaboradorJpaController();
                    cJpaC.create(c);
                    recuperarColaboradores();
                    lbl_nombreColaborador.setVisible(false);
                    txt_nombreColaborador.setVisible(false);
                    txt_nombreColaborador.setDisable(true);
                    txt_nombreColaborador.setText("");
                    btn_guardarColaborador.setVisible(false);
                    btn_guardarColaborador.setDisable(true);
                }
            } else {
                errorlbl.setText("Escriba el nombre de un Colaborador");
                errorlbl.setVisible(true);
            }
        }
    }

    @FXML
    private void clickCancelar(ActionEvent event) {
        Alert cancelar = new Alert(Alert.AlertType.CONFIRMATION);
        cancelar.setTitle("Cancelar proceso");
        cancelar.setHeaderText(null);
        cancelar.initStyle(StageStyle.UTILITY);
        cancelar.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancelar.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    private void clickAceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if (r.isError()) {
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
        } else {
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
            actualizarProducto();
            regresarAVentanaAnterior();
        }
    }

    @FXML
    private void clickAgregar(ActionEvent event) {
        if (!lbl_nombreColaborador.isVisible()) {
            lbl_nombreColaborador.setVisible(true);
            txt_nombreColaborador.setVisible(true);
            txt_nombreColaborador.setDisable(false);
            btn_guardarColaborador.setVisible(true);
            btn_guardarColaborador.setDisable(false);
        } else {
            lbl_nombreColaborador.setVisible(false);
            txt_nombreColaborador.setVisible(false);
            txt_nombreColaborador.setDisable(true);
            txt_nombreColaborador.setText("");
            btn_guardarColaborador.setVisible(false);
            btn_guardarColaborador.setDisable(true);
        }
    }

    public void recibirParametros(Producto pro) {
        Producto producto;
        ProductoJpaController pJpaC = new ProductoJpaController();
        producto = pJpaC.findProducto(pro.getIdProducto());
        p = producto;
        cInvolucrados = recuperarColaboradoresInvolucrados(p);
        mInvolucrados = recuperarMiembrosInvolucrados(p);
        iniciarMiembros();
        iniciarColaboradores();
        iniciarPantalla();
    }

    private void iniciarMiembros() {
        CheckMenuItem cmi;
        ArrayList<CheckMenuItem> items = new ArrayList<>();
        for (int i = 0; i < miembros.size(); i++) {
            cmi = new CheckMenuItem(miembros.get(i).toString());
            cmi.setUserData(miembros.get(i));
            items.add(cmi);
        }
        mb_autores.getItems().setAll(items);

        for (final CheckMenuItem item : items) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

                if (newValue) {
                    lstAutores.getItems().add((Miembro) item.getUserData());
                } else {
                    lstAutores.getItems().remove((Miembro) item.getUserData());
                }
            });
        }
        for (int i = 0; i < mInvolucrados.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                if (mInvolucrados.get(i).equals(items.get(j).getUserData())) {
                    items.get(j).setSelected(true);
                }
            }
        }
    }

    private void iniciarColaboradores() {
        CheckMenuItem cmi;
        ArrayList<CheckMenuItem> items = new ArrayList<>();
        for (int i = 0; i < colaboradores.size(); i++) {
            cmi = new CheckMenuItem(colaboradores.get(i).toString());
            cmi.setUserData(colaboradores.get(i));
            items.add(cmi);
        }
        mb_colaboradores.getItems().setAll(items);

        for (final CheckMenuItem item : items) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

                if (newValue) {
                    lstColaboradores.getItems().add((Colaborador) item.getUserData());
                } else {
                    lstColaboradores.getItems().remove((Colaborador) item.getUserData());
                }
            });
        }
        for (int i = 0; i < cInvolucrados.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                if (cInvolucrados.get(i).equals(items.get(j).getUserData())) {
                    items.get(j).setSelected(true);
                }
            }
        }
    }

    private void iniciarPantalla() {
        Tesis tesis;
        TesisJpaController tjc = new TesisJpaController();
        tesis = tjc.encontrarTesisPorIdProducto(p);
        t = tesis;
        titulotxt.setText(p.getTitulo());
        propositotxt.setText(p.getProposito());
        gradotxt.setText(t.getGrado());
        registrotxt.setText(t.getNumRegistro().toString());
        descripciontxt.setText(t.getDescripcion());
        usuariotxt.setText(t.getUsuarioDirigido());
        paisescb.getSelectionModel().select(p.getIdPais());
        clasificacioncb.getSelectionModel().select(t.getClasificacionInter());
        estadocb.getSelectionModel().select(p.getEstadoActual());
        txt_numHojas.setText(t.getNumHojas().toString());
        txt_archivo.setText(p.getNombrePDF());
        fechadp.setValue(LocalDate.ofYearDay(p.getAnio(), 100));
        
    }

    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (titulotxt.getText().isEmpty()
                || gradotxt.getText().isEmpty()
                || propositotxt.getText().isEmpty()
                || registrotxt.getText().isEmpty()
                || descripciontxt.getText().isEmpty()
                || usuariotxt.getText().isEmpty()
                || fechadp.getValue() == null
                || paisescb.getSelectionModel().isEmpty()
                || clasificacioncb.getSelectionModel().isEmpty()
                || estadocb.getSelectionModel().isEmpty()
                || txt_numHojas.getText().isEmpty()) {
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if (titulotxt.getText().length() > 100) {
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 100 caracteres");
            r.setErrorcode(2);
            return r;
        }
        if (gradotxt.getText().length() > 150) {
            r.setError(true);
            r.setMensaje("El grado no puede tener mas de 150 caracteres");
            r.setErrorcode(4);
            return r;
        }
        if (propositotxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El proposito no puede tener mas de 255 caracteres");
            r.setErrorcode(5);
            return r;
        }
        if (registrotxt.getText().length() > 10 || !registrotxt.getText().matches("[0-9]*")) {
            r.setError(true);
            r.setMensaje("El registro solo puede ser numeros y no tener mas de 10 caracteres");
            r.setErrorcode(6);
            return r;
        }
        if (descripciontxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("La descripcion no puede tener mas de 255 caracteres");
            r.setErrorcode(7);
            return r;
        }
        if (usuariotxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El usuario no debe ser mayor a 255 caracteres");
            r.setErrorcode(8);
            return r;
        }
        if (Objects.equals(file, null)) {
            r.setError(true);
            r.setMensaje("Seleccione un archivo PDF como evidencia.");
            r.setErrorcode(9);
        }
        if (!txt_numHojas.getText().matches("[0-9]*")) {
            r.setError(true);
            r.setMensaje("Valor no numerico en numero de hojas");
            r.setErrorcode(9);
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }
    
    private void regresarAVentanaAnterior() {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "SeleccionarTesis.fxml"));

            Parent seleccion = loader.load();
            Scene scene = new Scene(seleccion);
            Stage stage = new Stage();
            stage.fullScreenProperty();
            stage.setScene(scene);
            stage.show();
            ((Node) (cancelar)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarProducto() {
        TesisJpaController tjc = new TesisJpaController();
        Tesis te = tjc.encontrarTesisPorIdProducto(p);
        te.setClasificacionInter(clasificacioncb.getSelectionModel().getSelectedItem());
        te.setDescripcion(descripciontxt.getText().trim());
        te.setGrado(gradotxt.getText().trim());
        te.setNumHojas(Integer.valueOf(txt_numHojas.getText()));
        te.setNumRegistro(Integer.valueOf(registrotxt.getText()));
        te.setUsuarioDirigido(usuariotxt.getText());
        
        List<Tesis> lista = new ArrayList();
        lista.add(te);
        
        try {
            tjc.edit(te);
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        p.setAnio(fechadp.getValue().getYear());
        p.setTitulo(titulotxt.getText().trim());
        p.setProposito(propositotxt.getText().trim());
        p.setIdPais(paisescb.getSelectionModel().getSelectedItem());
        p.setEstadoActual(estadocb.getSelectionModel().getSelectedItem());
        p.setTesisList(lista);
        
        if (!Objects.equals(file, null)) {
            byte[] doc;
            try {
                doc = Files.readAllBytes(file.toPath());
                p.setArchivoPDF(doc);
                p.setNombrePDF(file.getName());
            } catch (IOException ex) {
                Logger.getLogger(RegistrarPrototipoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ProductoJpaController prJpaC = new ProductoJpaController();
        try {
            prJpaC.edit(p);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            errorlbl.setText("Error al conectar con la base de datos");
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            errorlbl.setText("Error al conectar con la base de datos");
        }
        
        ///datos del producto-colaborador///
        
        List<Colaborador> colas = lstColaboradores.getItems();
        ProductoColaboradorJpaController pcJpaC = new ProductoColaboradorJpaController();
        List<ProductoColaborador> pcs = pcJpaC.findByIdProducto(p.getIdProducto());
        ProductoColaborador productCol;
        for (int j = 0; j < colas.size(); j++) {
            if (!cInvolucrados.contains(colas.get(j))) {
                productCol =  new ProductoColaborador();
                productCol.setColaborador(colas.get(j));
                productCol.setProducto(p);
                try {
                    pcJpaC.create(productCol);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                cInvolucrados.remove(colas.get(j));
            }
        }
        if (cInvolucrados.size() > 0) {           
            for (int i = 0; i < cInvolucrados.size(); i++) {
                productCol = pcJpaC.findByIdPC(cInvolucrados.get(i).getIdColaborador(), p.getIdProducto());
                try {
                    pcJpaC.destroy(productCol.getProductoColaboradorPK());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //datos del producto-Miembro
        List<Miembro> miems = lstAutores.getItems();
        ProductoMiembroJpaController pmJpaC = new ProductoMiembroJpaController();
        List<ProductoMiembro> pms = pmJpaC.findByIdProducto(p);
        ProductoMiembro productMiem;
        for (int j = 0; j < miems.size(); j++) {
            if (!mInvolucrados.contains(miems.get(j))) {
                productMiem =  new ProductoMiembro();
                productMiem.setIdMiembro(miems.get(j));
                productMiem.setIdProducto(p);
                try {
                    pmJpaC.create(productMiem);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                mInvolucrados.remove(miems.get(j));
            }
        }
        if (mInvolucrados.size() > 0) {           
            for (int i = 0; i < mInvolucrados.size(); i++) {
                productMiem = pmJpaC.findByIdPM(mInvolucrados.get(i), p);
                try {
                    pmJpaC.destroy(productMiem.getIdMiembroProducto());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

