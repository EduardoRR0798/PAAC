/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.CaMiembro;
import entity.Colaborador;
import entity.CuerpoAcademico;
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
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.TesisJpaController;

/**
 * FXML Controller class
 *
 * @author Foncho
 */
public class RegistrarTesisControlador extends ControladorProductos implements Initializable {

    @FXML
    private TextField titulotxt;
    @FXML
    private ComboBox<Pais> paisescb;
    @FXML
    private ComboBox<String> cb_proposito;
    @FXML
    private TextField registrotxt;
    @FXML
    private TextField descripciontxt;
    @FXML
    private TextField usuariotxt;
    @FXML
    private TextField txt_anio;
    @FXML
    private ComboBox<String> gradocb;
    @FXML
    private ComboBox<String> clasificacioncb;
    @FXML
    private ComboBox<String> estadocb;
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
    private MenuButton mb_colaboradores;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txt_archivo;
    @FXML
    private TextField txt_numHojas;
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private Button btn_subirArchivo;
    // atributos necesarios
    ObservableList<String> clasif = FXCollections.observableArrayList(
            "Teórica",
            "Práctica",
            "Teórico-Práctica",
            "De Investigación documental",
            "De investigación de campo");
    ObservableList<String> grados = FXCollections.observableArrayList(
            "Doctorado",
            "Especialidad",
            "Especialidad médica",
            "Licenciatura",
            "Maestría",
            "Técnico",
            "Técnico superior universitario");
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private File file;
    private Miembro m;
    private ArrayList<CheckMenuItem> itemsColaborador = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cb_proposito.setItems(super.propositos);
        gradocb.setItems(grados);
        clasificacioncb.setItems(clasif);
        estadocb.setItems(super.estados);
        colaboradores = super.recuperarColaboradores();
        paises = recuperarPaises();
        paisescb.setItems(paises);
        iniciarColaboradores();
        paisescb.getSelectionModel().select(116);
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
                    colaboradores.add(c);
                    CheckMenuItem cmi = new CheckMenuItem(c.toString());
                    cmi.setUserData(c);
                    itemsColaborador.add(cmi);
                    mb_colaboradores.getItems().add(cmi);
                    ponerAtributoColaborador(cmi);
                    lbl_nombreColaborador.setVisible(false);
                    txt_nombreColaborador.setVisible(false);
                    txt_nombreColaborador.setDisable(true);
                    txt_nombreColaborador.setText("");
                    btn_guardarColaborador.setVisible(false);
                    btn_guardarColaborador.setDisable(true);
                } else {
                    errorlbl.setText("Escriba un nombre valido");
                    errorlbl.setVisible(true);
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

    public void setMiembro(Miembro miembro) {
        this.m = miembro;
        miembros = super.recuperarMiembros(m);
        iniciarMiembros();
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
            registrarTesis();
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

    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (titulotxt.getText().isEmpty()
                || gradocb.getSelectionModel().isEmpty()
                || cb_proposito.getSelectionModel().isEmpty()
                || registrotxt.getText().isEmpty()
                || descripciontxt.getText().isEmpty()
                || usuariotxt.getText().isEmpty()
                || txt_anio.getText().isEmpty()
                || paisescb.getSelectionModel().isEmpty()
                || clasificacioncb.getSelectionModel().isEmpty()
                || estadocb.getSelectionModel().isEmpty()) {
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
        if (!validarTitulo(titulotxt.getText().trim())) {
            r.setError(true);
            r.setMensaje("El titulo de esta memoria ya se encuentra registrado.");
            r.setErrorcode(3);
            return r;
        }
        if (!txt_anio.getText().matches("^(\\d{4})+$")) {
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(2);
            return r;
        }
        if (Integer.valueOf(txt_anio.getText()) < 1950 || Integer.valueOf(txt_anio.getText()) > LocalDate.now().getYear()) {
            r.setError(true);
            r.setMensaje("Ingrese un año mayor a 1950 y no mayor al actual");
            r.setErrorcode(2);
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
        if (estadocb.getSelectionModel().getSelectedIndex() == 1) {
            if (txt_numHojas.getText().isEmpty()) {
                r.setError(true);
                r.setMensaje("No puede haber campos vacíos");
                r.setErrorcode(1);
                return r;
            }
            if (!txt_numHojas.getText().matches("[0-9]*")) {
                r.setError(true);
                r.setMensaje("Valor no numerico en numero de hojas");
                r.setErrorcode(9);
                return r;
            }
            if (Objects.equals(file, null)) {
                r.setError(true);
                r.setMensaje("Seleccione un archivo PDF como evidencia.");
                r.setErrorcode(9);
                return r;
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
        mb_colaboradores.getItems().setAll(itemsColaborador);

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
        mb_autores.getItems().setAll(items);
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

    private void registrarTesis() {
        Tesis tesis = new Tesis();
        tesis.setGrado(gradocb.getSelectionModel().getSelectedItem());
        tesis.setNumRegistro(Integer.valueOf(registrotxt.getText().trim()));
        tesis.setClasificacionInter((String) clasificacioncb.getValue());
        tesis.setDescripcion(descripciontxt.getText().trim());
        tesis.setUsuarioDirigido(usuariotxt.getText().trim());
        List<Tesis> t = new ArrayList<>();

        ///datos del Producto///
        Producto producto = new Producto();
        if (estadocb.getSelectionModel().getSelectedIndex() == 1) {
            tesis.setNumHojas(Integer.valueOf(txt_numHojas.getText().trim()));
            byte[] doc;
            try {
                doc = Files.readAllBytes(file.toPath());
                producto.setArchivoPDF(doc);
                producto.setNombrePDF(file.getName());
            } catch (IOException ex) {
                Logger.getLogger(RegistrarPrototipoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        t.add(tesis);
        TesisJpaController tj = new TesisJpaController();
        tj.create(tesis);
        producto.setAnio(Integer.valueOf(txt_anio.getText()));
        producto.setTitulo(titulotxt.getText().trim());
        producto.setProposito(cb_proposito.getSelectionModel().getSelectedItem());
        producto.setIdPais(paisescb.getSelectionModel().getSelectedItem());
        producto.setEstadoActual(estadocb.getSelectionModel().getSelectedItem());
        producto.setTesisList(t);
        //Busco el CA del miembro que esta registrando el producto.
        CaMiembroJpaController camJpaC = new CaMiembroJpaController();
        CuerpoAcademicoJpaController caJpaC = new CuerpoAcademicoJpaController();
        CaMiembro cam = camJpaC.findByMiembro(m.getIdMiembro());
        CuerpoAcademico ca = caJpaC.findCuerpoAcademico(cam.getCaMiembroPK().getIdCuerpoAcademico());
        //Fijo el CuerpoAcademico al producto.
        producto.setIdCuerpoAcademico(ca);
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(producto)) {
            errorlbl.setText("Error al conectar con la base de datos...");
        }
        ///datos del producto-colaborador///
        ObservableList<Colaborador> colas = lstColaboradores.getItems();
        ProductoColaboradorJpaController pcJpaC = new ProductoColaboradorJpaController();
        ProductoColaborador pc;
        for (int i = 0; i < colas.size(); i++) {
            pc = new ProductoColaborador();
            pc.setProducto(producto);
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
            pm.setIdProducto(producto);
            pmJpaC.create(pm);
        }
    }

    @FXML
    private void cambiarEstado(ActionEvent event) {
        if (estadocb.getSelectionModel().getSelectedIndex() == 0) {
            txt_numHojas.setDisable(true);
            txt_archivo.setDisable(true);
            btn_subirArchivo.setDisable(true);
        } else {
            txt_numHojas.setDisable(false);
            txt_archivo.setDisable(false);
            btn_subirArchivo.setDisable(false);
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
