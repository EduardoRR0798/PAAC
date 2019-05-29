/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.Colaborador;
import entity.Libro;
import entity.Miembro;
import entity.Pais;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.ColaboradorJpaController;
import persistence.LibroJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author Foncho
 */
public class ActualizarLibroControlador extends ControladorProductos implements Initializable {

    @FXML
    private TextField titulotxt;
    @FXML
    private TextField aniotxt;
    @FXML
    private ComboBox<Pais> cbPaises;
    @FXML
    private TextArea propositotxt;
    @FXML
    private TextField isbntxt;
    @FXML
    private TextField editorialtxt;
    @FXML
    private TextField paginastxt;
    @FXML
    private TextField ediciontxt;
    @FXML
    private TextField ejemplarestxt;
    @FXML
    private TextField txt_archivo;
    @FXML
    private Button btn_subirArchivo;
    @FXML
    private Label errorlbl;
    @FXML
    private TextField txt_nombreColaborador;
    @FXML
    private Label lbl_nombreColaborador;
    @FXML
    private Button btn_guardar;
    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private MenuButton mbMiembros;
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private MenuButton mbColaboradores;
    @FXML
    private Button agregar;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private ComboBox<String> estadocb;
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private ArrayList<Miembro> mInvolucrados = new ArrayList<>();
    private ArrayList<Colaborador> cInvolucrados = new ArrayList<>();
    private File file;
    private Producto p;
    private Libro l;
    ObservableList<String> estados = FXCollections.observableArrayList(
            "Planeado",
            "En proceso",
            "Terminado");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        estadocb.setItems(estados);
        colaboradores = super.recuperarColaboradores();
        miembros = super.recuperarMiembros();
        paises = recuperarPaises();
        cbPaises.setItems(paises);
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
    private void guardarColaborador(ActionEvent event) {
        if (!txt_nombreColaborador.isDisabled()) {
            if (!Objects.equals(txt_nombreColaborador.getText().trim(), null)) {
                if (txt_nombreColaborador.getText().trim().length() > 10) {
                    Colaborador c = new Colaborador();
                    c.setNombre(txt_nombreColaborador.getText().trim());
                    ColaboradorJpaController cJpaC = new ColaboradorJpaController();
                    cJpaC.create(c);
                    lbl_nombreColaborador.setVisible(false);
                    txt_nombreColaborador.setVisible(false);
                    txt_nombreColaborador.setDisable(true);
                    txt_nombreColaborador.setText("");
                    btn_guardar.setVisible(false);
                    btn_guardar.setDisable(true);
                    recuperarColaboradores();
                    iniciarColaboradores();
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
            btn_guardar.setVisible(true);
            btn_guardar.setDisable(false);
        } else {
            lbl_nombreColaborador.setVisible(false);
            txt_nombreColaborador.setVisible(false);
            txt_nombreColaborador.setDisable(true);
            txt_nombreColaborador.setText("");
            btn_guardar.setVisible(false);
            btn_guardar.setDisable(true);
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
        mbMiembros.getItems().setAll(items);

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
        mbColaboradores.getItems().setAll(items);

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

    public void iniciarPantalla() {
        Libro libro;
        LibroJpaController ljc = new LibroJpaController();
        libro = ljc.encontrarLibroPorIdProducto(p);
        l = libro;
        titulotxt.setText(p.getTitulo());
        cbPaises.getSelectionModel().select(p.getIdPais());
        aniotxt.setText(p.getAnio().toString());
        propositotxt.setText(p.getProposito());
        isbntxt.setText(l.getIsbn());
        editorialtxt.setText(l.getEditorial());
        paginastxt.setText(String.valueOf(l.getNumPaginas()));
        ediciontxt.setText(String.valueOf(l.getEdicion()));
        ejemplarestxt.setText(String.valueOf(l.getTiraje()));
        txt_archivo.setText(p.getNombrePDF());
        estadocb.getSelectionModel().select(p.getEstadoActual());

    }

    private void regresarAVentanaAnterior() {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "SeleccionarLibro.fxml"));

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

    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (titulotxt.getText().isEmpty()
                || aniotxt.getText().isEmpty()
                || propositotxt.getText().isEmpty()
                || isbntxt.getText().isEmpty()
                || editorialtxt.getText().isEmpty()
                || paginastxt.getText().isEmpty()
                || ediciontxt.getText().isEmpty()
                || ejemplarestxt.getText().isEmpty()
                || cbPaises.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if (titulotxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 255` caracteres");
            r.setErrorcode(1);
            return r;
        }
        if (!aniotxt.getText().matches("^(\\d{4})+$")) {
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(2);
            return r;
        }
        if (propositotxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El proposito no puede tener mas de 255 caracteres");
            r.setErrorcode(3);
            return r;
        }
        if (!isbntxt.getText().matches("^(97(8|9))?\\d{9}(\\d|X)$")) {
            r.setError(true);
            r.setMensaje("Ingrese un número ISBN válido de 10 o 13 dígitos");
            r.setErrorcode(4);
            return r;
        }
        if (editorialtxt.getText().length() > 255) {
            r.setError(true);
            r.setMensaje("El nombre de la editorial no puede ser mayor a 255 caracteres");
            r.setErrorcode(5);
            return r;
        }
        if (!paginastxt.getText().matches("[0-9]*") || paginastxt.getText().length() > 10) {
            r.setError(true);
            r.setMensaje("Solo se permite números en Páginas menores a 10 digitos");
            r.setErrorcode(6);
            return r;
        }
        if (!ediciontxt.getText().matches("[0-9]*") || ediciontxt.getText().length() > 10) {
            r.setError(true);
            r.setMensaje("Solo se permite números en Edición menores a 10 digitos");
            r.setErrorcode(7);
            return r;
        }
        if (!ejemplarestxt.getText().matches("[0-9]*") || ejemplarestxt.getText().length() > 10) {
            r.setError(true);
            r.setMensaje("Solo se permite números en Ejemplares menores a 10 digitos");
            r.setErrorcode(8);
            return r;
        }
        if (txt_archivo.equals("")) {
            r.setError(true);
            r.setMensaje("Debe seleccionar un archivo");
            r.setErrorcode(9);
            return r;
        }
        if (Objects.equals(file, null)) {
            r.setError(true);
            r.setMensaje("Seleccione un archivo PDF como evidencia.");
            r.setErrorcode(10);
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }

    private void actualizarProducto() {
        LibroJpaController tjc = new LibroJpaController();
        Libro l = tjc.encontrarLibroPorIdProducto(p);
        l.setEdicion(Integer.valueOf(ediciontxt.getText()));
        l.setEditorial(ediciontxt.getText().trim());
        l.setIsbn(isbntxt.getText());
        l.setNumPaginas(Integer.valueOf(paginastxt.getText()));
        l.setTiraje(Integer.valueOf(ejemplarestxt.getText()));

        List<Libro> lista = new ArrayList();
        lista.add(l);

        try {
            tjc.edit(l);
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }

        p.setAnio(Integer.valueOf(aniotxt.getText()));
        p.setTitulo(titulotxt.getText().trim());
        p.setProposito(propositotxt.getText().trim());
        p.setIdPais(cbPaises.getSelectionModel().getSelectedItem());
        p.setEstadoActual(estadocb.getSelectionModel().getSelectedItem());
        p.setLibroList(lista);

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
                productCol = new ProductoColaborador();
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
                productMiem = new ProductoMiembro();
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
