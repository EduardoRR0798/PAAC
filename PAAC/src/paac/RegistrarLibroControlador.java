/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.CaMiembro;
import entity.Colaborador;
import entity.CuerpoAcademico;
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
import java.util.Objects;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.CaMiembroJpaController;
import persistence.ColaboradorJpaController;
import persistence.CuerpoAcademicoJpaController;
import persistence.LibroJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;

/**
 *
 * @author Foncho
 */
public class RegistrarLibroControlador extends ControladorProductos implements Initializable {

    @FXML
    private TextField titulotxt;
    @FXML
    private TextField aniotxt;
    @FXML
    private ComboBox<Pais> cbPaises;
    @FXML
    private ComboBox<String> cb_proposito;
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
    private ComboBox<String> estadocb;
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
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private File file;
    private Miembro m;
    private ArrayList<CheckMenuItem> itemsColaborador = new ArrayList<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estadocb.setItems(super.estados);
        colaboradores = super.recuperarColaboradores();
        cb_proposito.setItems(super.propositos);
        paises = recuperarPaises();
        cbPaises.setItems(paises);
        iniciarColaboradores();
        cbPaises.getSelectionModel().select(116);
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
                    colaboradores.add(c);
                    CheckMenuItem cmi = new CheckMenuItem(c.toString());
                    cmi.setUserData(c);
                    itemsColaborador.add(cmi);
                    mbColaboradores.getItems().add(cmi);
                    ponerAtributoColaborador(cmi);
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
        Alert cancela = new Alert(Alert.AlertType.CONFIRMATION);
        cancela.setTitle("Cancelar proceso");
        cancela.setHeaderText(null);
        cancela.initStyle(StageStyle.UTILITY);
        cancela.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancela.showAndWait();
        if (result.get() == ButtonType.OK) {
            seleccionarProductos(m);
            ((Node) cancelar).getScene().getWindow().hide();
        }
    }

    @FXML
    private void clickAceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if (r.isError()) {
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
        } else {
            registrarLibro();
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
            abrirMenu(m);
            ((Node) cancelar).getScene().getWindow().hide();
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

    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (titulotxt.getText().isEmpty()
                || aniotxt.getText().isEmpty()
                || cb_proposito.getSelectionModel().isEmpty()
                || isbntxt.getText().isEmpty()
                || editorialtxt.getText().isEmpty()
                || ediciontxt.getText().isEmpty()
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
        if (Integer.valueOf(aniotxt.getText())<1950) {
            r.setError(true);
            r.setMensaje("Ingrese un año mayor a 1950");
            r.setErrorcode(2);
            return r;
        }
        if (!isbntxt.getText().matches("^(97(8|9))?\\d{9}(\\d|X)$")) {
            r.setError(true);
            r.setMensaje("Ingrese un número ISBN válido de 10 o 13 dígitos");
            r.setErrorcode(4);
            return r;
        }
        if (editorialtxt.getText().trim().length() > 255) {
            r.setError(true);
            r.setMensaje("El nombre de la editorial no puede ser mayor a 255 caracteres");
            r.setErrorcode(5);
            return r;
        }
        if (!ediciontxt.getText().matches("[0-9]*") || ediciontxt.getText().length() > 10) {
            r.setError(true);
            r.setMensaje("Solo se permite números en Edición menores a 10 digitos");
            r.setErrorcode(7);
            return r;
        }
        if (estadocb.getSelectionModel().getSelectedIndex() == 1) {
            if (paginastxt.getText().isEmpty() || ejemplarestxt.getText().isEmpty()) {
                r.setError(true);
                r.setMensaje("No puede haber campos vacíos");
                r.setErrorcode(1);
                return r;
            }
            if (!paginastxt.getText().matches("[0-9]*") || paginastxt.getText().length() > 7) {
                r.setError(true);
                r.setMensaje("Solo se permite números en Páginas menores a 7 digitos");
                r.setErrorcode(6);
                return r;
            }
            if (!ejemplarestxt.getText().matches("[0-9]*") || ejemplarestxt.getText().length() > 6) {
                r.setError(true);
                r.setMensaje("Solo se permite números en Ejemplares menores a 6 digitos");
                r.setErrorcode(8);
                return r;
            }
            if (Objects.equals(file, null)) {
                r.setError(true);
                r.setMensaje("Seleccione un archivo PDF como evidencia.");
                r.setErrorcode(10);
            }
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }

    /**
     * Este metodo agregar los checkmenuitem al menubutton para una multiple
     * seleccion.
     */
    public void iniciarColaboradores() {
        CheckMenuItem cmi;
        for (int i = 0; i < colaboradores.size(); i++) {
            cmi = new CheckMenuItem(colaboradores.get(i).toString());
            cmi.setUserData(colaboradores.get(i));
            itemsColaborador.add(cmi);
        }
        mbColaboradores.getItems().setAll(itemsColaborador);

        for (final CheckMenuItem item : itemsColaborador) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

                if (newValue) {
                    lstColaboradores.getItems().add((Colaborador) item.getUserData());
                } else {
                    lstColaboradores.getItems().remove((Colaborador) item.getUserData());
                }
            });
        }
    }

    public void iniciarMiembros() {
        CheckMenuItem cmi;
        ArrayList<CheckMenuItem> items = new ArrayList<>();
        for (int i = 0; i < miembros.size(); i++) {
            cmi = new CheckMenuItem(miembros.get(i).toString());
            cmi.setUserData(miembros.get(i));
            items.add(cmi);
        }
        mbMiembros.getItems().setAll(items);
        lstAutores.getItems().add(m);
        for (final CheckMenuItem item : items) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {

                if (newValue) {
                    lstAutores.getItems().add((Miembro) item.getUserData());
                } else {
                    lstAutores.getItems().remove((Miembro) item.getUserData());
                }
            });
        }
    }

    public void registrarLibro() {
        Libro l = new Libro();
        l.setEdicion(Integer.valueOf(ediciontxt.getText()));
        l.setEditorial(ediciontxt.getText().trim());
        l.setIsbn(isbntxt.getText());
        //datos del producto
        Producto pro = new Producto();
        if (estadocb.getSelectionModel().getSelectedIndex() == 1) {
            l.setNumPaginas(Integer.valueOf(paginastxt.getText()));
            l.setTiraje(Integer.valueOf(ejemplarestxt.getText()));
            byte[] doc;
            try {
                doc = Files.readAllBytes(file.toPath());
                pro.setArchivoPDF(doc);
                pro.setNombrePDF(file.getName());
            } catch (IOException ex) {
                Logger.getLogger(RegistrarPrototipoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Libro> lista = new ArrayList<>();
        lista.add(l);
        LibroJpaController ljpa = new LibroJpaController();
        ljpa.create(l);
        pro.setAnio(Integer.valueOf(aniotxt.getText()));
        pro.setTitulo(titulotxt.getText().trim());
        pro.setProposito(cb_proposito.getSelectionModel().getSelectedItem());
        pro.setIdPais(cbPaises.getSelectionModel().getSelectedItem());
        pro.setEstadoActual(estadocb.getSelectionModel().getSelectedItem());
        pro.setLibroList(lista);
        //Busco el CA del miembro que esta registrando el producto.
        CaMiembroJpaController camJpaC = new CaMiembroJpaController();
        CuerpoAcademicoJpaController caJpaC = new CuerpoAcademicoJpaController();
        CaMiembro cam = camJpaC.findByMiembro(m.getIdMiembro());
        CuerpoAcademico ca = caJpaC.findCuerpoAcademico(cam.getCaMiembroPK().getIdCuerpoAcademico());
        //Fijo el CuerpoAcademico al producto.
        producto.setIdCuerpoAcademico(ca);
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(pro)) {
            errorlbl.setText("Error al conectar con la base de datos...");
        }
        ///datos del producto-colaborador///
        ObservableList<Colaborador> colas = lstColaboradores.getItems();
        ProductoColaboradorJpaController pcJpaC = new ProductoColaboradorJpaController();
        ProductoColaborador pc;
        for (int i = 0; i < colas.size(); i++) {
            pc = new ProductoColaborador();
            pc.setProducto(pro);
            pc.setColaborador(colas.get(i));
            try {
                pcJpaC.create(pc);
            } catch (Exception ex) {
                Logger.getLogger(ControladorRegistrarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ///datos del producto-Miembro
        ObservableList<Miembro> mis = lstAutores.getItems();
        ProductoMiembroJpaController pmJpaC = new ProductoMiembroJpaController();
        ProductoMiembro pm;
        for (int i = 0; i < mis.size(); i++) {
            pm = new ProductoMiembro();
            pm.setIdMiembro(mis.get(i));
            pm.setIdProducto(pro);
            pmJpaC.create(pm);
        }
    }

    public void setMiembro(Miembro miembro) {
        this.m = miembro;
        miembros = super.recuperarMiembros(m);
        iniciarMiembros();
    }

    @FXML
    private void cambiarEstado(ActionEvent event) {
        if (estadocb.getSelectionModel().getSelectedIndex() == 0) {
            ejemplarestxt.setDisable(true);
            txt_archivo.setDisable(true);
            btn_subirArchivo.setDisable(true);
            paginastxt.setDisable(true);
        } else {
            ejemplarestxt.setDisable(false);
            txt_archivo.setDisable(false);
            btn_subirArchivo.setDisable(false);
            paginastxt.setDisable(false);
        }
    }
    
    /**
     * Pone la opcion de que al seleccionarlo se sume a la lista de autores.
     * @param item Un CheckMenuItem que contenga al colaborador.
     */
    private void ponerAtributoColaborador(CheckMenuItem item) {
        item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                
            if (newValue) {
                    lstColaboradores.getItems().add((Colaborador) item.getUserData());
                } else {
                    lstColaboradores.getItems().remove((Colaborador) item.getUserData());
                }
            });
    }
}
