package paac;

import entity.CaMiembro;
import entity.Colaborador;
import entity.CuerpoAcademico;
import entity.Miembro;
import entity.Pais;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import entity.Prototipo;
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
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.PrototipoJpaController;
import persistence.exceptions.NonexistentEntityException;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class ActualizarPrototipoController extends ControladorProductos implements Initializable {

    @FXML
    private Label lblMensaje;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox<String> cbProposito;
    @FXML
    private TextField tfCaracteristicas;
    @FXML
    private TextField tfArchivo;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TextField tfInstitucion;
    @FXML
    private TextField tfColaborador;
    @FXML
    private TextField tfAnio;
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private ComboBox<Pais> cbPais;
    @FXML
    private Button btnAgregarColaborador;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnCargar;
    @FXML
    private MenuButton mbMiembros;
    @FXML
    private MenuButton mbColaboradores;

    private Producto p;
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private ArrayList<Miembro> mInvolucrados = new ArrayList<>();
    private ArrayList<Colaborador> cInvolucrados = new ArrayList<>();
    private File file;
    private Miembro m;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboradores = super.recuperarColaboradores();
        miembros = super.recuperarMiembros(m);
        cbPais.setItems(recuperarPaises());
        cbProposito.setItems(super.propositos);
        cbEstado.setItems(super.estados);
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.limitarCampos(tfNombre, 140);
        uc.limitarCampos(tfAnio, 4);
        uc.limitarCampos(tfCaracteristicas, 150);
        uc.limitarCampos(tfInstitucion, 150);
        uc.limitarCampos(tfColaborador, 100);
    }    

    /**
     * Muestra un campo para agregar un nuevo colaborador.
     * @param event Clic en el boton Agregar Colaborador
     */
    @FXML
    private void mostrarCampoColaborador(ActionEvent event) {
        if (!tfColaborador.isVisible()) {
            tfColaborador.setVisible(true);
            tfColaborador.setDisable(false);
            btnAgregar.setVisible(true);
            btnAgregar.setDisable(false);
        } else {
            tfColaborador.setVisible(false);
            tfColaborador.setDisable(true);
            btnAgregar.setVisible(false);
            btnAgregar.setDisable(true);
        }
    }

    /**
     * Registra un prototipo en la base de datos.
     * @param event Clic en el boton Aceptar.
     */
    @FXML
    private void aceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if (r.isError()) {
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        } else {
            actualizarPrototipo();
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
            abrirMenu(m);
            ((Node) btnCancelar).getScene().getWindow().hide();
        }
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
            seleccionarPrototipo(m);
            ((Node) btnCancelar).getScene().getWindow().hide();
        }
    }

    /**
     * Recibe el id del producto seleccionado anteriormente.
     * @param pro id del producto.
     */
    public void recibirParametros(Producto pro, Miembro m) {
        Producto producto;
        this.m = m;
        ProductoJpaController pJpaC = new ProductoJpaController();
        producto = pJpaC.findProducto(pro.getIdProducto());
        p = producto;
        cInvolucrados = recuperarColaboradoresInvolucrados(p);
        mInvolucrados = recuperarMiembrosInvolucrados(p);
        iniciarMiembros();
        iniciarColaboradores();
        iniciarPantalla();
    }
    
    /**
     * Sirve para agregar a un colaborador a la base de datos y a la lista.
     * @param event clic en el boton +
     */
    @FXML
    private void agregar(ActionEvent event) {
        if (!tfColaborador.isDisabled()) {
            if (!Objects.equals(tfColaborador.getText().trim(), null)) {
                if (tfColaborador.getText().trim().length() > 10) {
                    Colaborador c = new Colaborador();
                    c.setNombre(tfColaborador.getText());
                    ColaboradorJpaController cJpaC = new ColaboradorJpaController();
                    cJpaC.create(c);
                    recuperarColaboradores();
                }
            } else {
                lblMensaje.setText("Escriba el nombre de un Colaborador");
            }
        }
    }

    /**
     * Abre un cuadro de dialogo para seleccionar un archivo PDF para cargarlo
     * en la base de datos.
     * @param event Clic en el boton Cargar.
     */
    @FXML
    private void cargar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.
                ExtensionFilter("Archivos PDF", "*.pdf");
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Selecciona un archivo PDF");
        Stage stage = new Stage();
        File fl = fileChooser.showOpenDialog(stage);
        if(!Objects.equals(fl, null)) {
            if (fl.length() <= 10000000) {
                file = fl;
                tfArchivo.setText(file.getPath());
            } else {
                lblMensaje.setText("El archivo debe ser menor a 10 MB.");
            }
        }
    }
    
    /**
     * Valida que todos los campos cumplan con los requisitos.
     * @return Una respuesta con un mensaje en caso de haber error.
     */
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(tfNombre.getText().isEmpty() 
                || tfAnio.getText().isEmpty() 
                || cbProposito.getSelectionModel().isEmpty() 
                || tfCaracteristicas.getText().isEmpty() 
                || cbEstado.getSelectionModel().isEmpty()
                || (cbEstado.getSelectionModel().isSelected(1) && tfArchivo.getText().isEmpty())
                || tfInstitucion.getText().isEmpty()) {
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        
        if(!validarTituloActualizar(tfNombre.getText().trim(), p.getIdProducto())){
            r.setError(true);
            r.setMensaje("El titulo de este producto ya se encuentra registrado");
            r.setErrorcode(1);
            return r;
        }
        if(!tfAnio.getText().matches("^(\\d{4})+$")){
            r.setError(true);
            r.setMensaje("Ingrese un año valido. Ejem 1984");
            r.setErrorcode(2);
            return r;
        }
        if (!super.verificarAnio(tfAnio.getText())) {
            r.setError(true);
            r.setMensaje("El año no puede ser mayor al año actual o menor a 1900.");
            r.setErrorcode(3);
            return r;
        }
        if(Objects.equals(tfCaracteristicas.getText(), null)){
            r.setError(true);
            r.setMensaje("Ingrese las caracteristicas.");
            r.setErrorcode(4);
            return r;
        }
        if(cbPais.getSelectionModel().isEmpty()){
            r.setError(true);
            r.setMensaje("Seleccione un pais.");
            r.setErrorcode(8);
            return r;
        }
        r.setMensaje("Prototipo actualizado correctamente.");
        r.setError(false);
        return r;
    }
    
    /**
     * Este metodo agrega los checkmenuitem al menubutton pata una multiple seleccion;
     */
    public void iniciarMiembros() {
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
    
    /**
     * Este metodo agregar los checkmenuitem al menubutton para una multiple seleccion.
     */
    public void iniciarColaboradores() {
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
    
    /**
     * Registra el producto, la memoria y todas sus relaciones.
     */
    private void actualizarPrototipo() {
        ///prototipo
        
        PrototipoJpaController pJpaC = new PrototipoJpaController();
        Prototipo prototipo = pJpaC.encontrarPrototipoPorIdProducto(p);
        prototipo.setCaracteristicas(tfCaracteristicas.getText().trim());
        prototipo.setInstitucionCreacion(tfInstitucion.getText().trim());
        List<Prototipo> protos = new ArrayList<>();
        
        try {
            pJpaC.edit(prototipo);
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        protos.add(prototipo);
        p.setAnio(Integer.parseInt(tfAnio.getText()));
        p.setTitulo(tfNombre.getText());
        p.setProposito(cbProposito.getSelectionModel().getSelectedItem());
        p.setIdPais(cbPais.getSelectionModel().getSelectedItem());
        p.setEstadoActual(cbEstado.getSelectionModel().getSelectedItem());
        //Busco el CA del miembro que esta registrando el producto.
        CaMiembroJpaController camJpaC = new CaMiembroJpaController();
        CuerpoAcademicoJpaController caJpaC = new CuerpoAcademicoJpaController();
        CaMiembro cam = camJpaC.findByMiembro(m.getIdMiembro());
        CuerpoAcademico ca = caJpaC.findCuerpoAcademico(cam.getCaMiembroPK().getIdCuerpoAcademico());
        //Fijo el CuerpoAcademico al producto.
        p.setIdCuerpoAcademico(ca);
        p.setPrototipoList(protos);
        if (cbEstado.getSelectionModel().getSelectedIndex() == 1) {
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
        }

        ProductoJpaController prJpaC = new ProductoJpaController();
        try {
            prJpaC.edit(p);
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            lblMensaje.setText(ERRORBD);
        }
        ///datos del producto-colaborador///
        
        List<Colaborador> colas = lstColaboradores.getItems();
        ProductoColaboradorJpaController pcJpaC = new ProductoColaboradorJpaController();
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
        if (!cInvolucrados.isEmpty()) {           
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
        if (!mInvolucrados.isEmpty()) {           
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
    
    /**
     * LLena los campos con los datos del producto seleccionado.
     */
    private void iniciarPantalla() {
        Prototipo proto;
        PrototipoJpaController pJpaC = new PrototipoJpaController();
        proto = pJpaC.encontrarPrototipoPorIdProducto(p);
        
        tfNombre.setText(p.getTitulo());
        tfAnio.setText(p.getAnio().toString());
        cbProposito.getSelectionModel().select(p.getProposito());
        cbEstado.getSelectionModel().select(p.getEstadoActual());
        tfCaracteristicas.setText(proto.getCaracteristicas());
        tfArchivo.setText(p.getNombrePDF());
        tfInstitucion.setText(proto.getInstitucionCreacion());
        cbPais.getSelectionModel().select(p.getIdPais());
    }
    
    /**
     * Habilita el boton de carga de archivo.
     * @param event Seleccion en el estado Terminado.
     */
    @FXML
    private void cambiarEstado(ActionEvent event) {
        if (cbEstado.getSelectionModel().isSelected(1)) {
            tfArchivo.setDisable(false);
            btnCargar.setDisable(false);
        } else {
            tfArchivo.setDisable(true);
            btnCargar.setDisable(true);
        }
    }
}
