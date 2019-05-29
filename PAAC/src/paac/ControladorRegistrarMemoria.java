package paac;

import entity.CaMiembro;
import entity.Colaborador;
import entity.CuerpoAcademico;
import persistence.ColaboradorJpaController;
import entity.Memoria;
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
import javafx.scene.control.Alert.AlertType;
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
import persistence.CuerpoAcademicoJpaController;
import persistence.MemoriaJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class ControladorRegistrarMemoria extends ControladorProductos implements Initializable {

    @FXML
    private TextField tfTitulo;
    @FXML
    private ComboBox<String> cbProposito;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfCongreso;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfInicio;
    @FXML
    private TextField tfFin;
    @FXML
    private ComboBox<String> cbEstadoActual;
    @FXML
    private Label lblMensaje;
    @FXML
    private MenuButton mbMiembros;
    @FXML
    private MenuButton mbColaboradores;
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAgregarColaborador;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField tfColaborador;
    @FXML
    private ComboBox<Pais> cbPais;
    @FXML
    private TextField tfPDF;
    @FXML
    private Button btnCargar;
    // atributos necesarios
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
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
        ObservableList<Pais> paises;
        paises = recuperarPaises();
        cbPais.setItems(paises);
        cbPais.getSelectionModel().select(116);
        cbProposito.setItems(super.propositos);
        cbEstadoActual.setItems(super.estados);
        iniciarColaboradores();
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.limitarCampos(tfTitulo, 140);
        uc.limitarCampos(tfAnio, 4);
        uc.limitarCampos(tfCongreso, 150);
        uc.limitarCampos(tfEstado, 150);
        uc.limitarCampos(tfCiudad, 150);
        uc.limitarCampos(tfInicio, 4);
        uc.limitarCampos(tfFin, 4);
        uc.limitarCampos(tfColaborador, 100);
    }    

    /**
     * Registra la Memoria tras validar que los datos introducidos por el usuario.
     * @param event Clic en el boton Aceptar
     */
    @FXML
    private void aceptar(ActionEvent event) {
        
        Respuesta r = validarCampos();
        if (r.isError()) {
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        } else {
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
            registrarMemoria();
            abrirMenu(m);
            ((Node) btnCancelar).getScene().getWindow().hide();
        }
    }

    /**
     * Recibe el miembro de la ventana anterior.
     * @param miembro Miembro de la ventana anterior.
     */
    public void setMiembro(Miembro miembro) {
        this.m = miembro;
        miembros = super.recuperarMiembros(m);
        iniciarMiembros();
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
                tfPDF.setText(file.getPath());
            } else {
                lblMensaje.setText("El archivo debe ser menor a 10 MB.");
            }
        }
    }
    
    /**
     * Muestra el cuadro de dialogo de confirmacion para cancelar la operacion.
     * @param event clic en el boton Cancelar
     */
    @FXML
    private void cancelar(ActionEvent event) {
        Alert cancelar = new Alert(AlertType.CONFIRMATION);
        cancelar.setTitle("Cancelar proceso");
        cancelar.setHeaderText(null);
        cancelar.initStyle(StageStyle.UTILITY);
        cancelar.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancelar.showAndWait();
        if(result.get() == ButtonType.OK) {
            seleccionarProductos(m);
            ((Node) btnCancelar).getScene().getWindow().hide();
        }
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
     * Registra un colaborador en la base de datos.
     * @param event Clic en el boton +.
     */
    @FXML
    private void agregarALista(ActionEvent event) {
        if (!tfColaborador.isDisabled()) {
            if (!Objects.equals(tfColaborador.getText().trim(), null)) {
                if (tfColaborador.getText().trim().length() > 10) {
                    Colaborador c = new Colaborador();
                    c.setNombre(tfColaborador.getText());
                    ColaboradorJpaController cJpaC = new ColaboradorJpaController();
                    cJpaC.create(c);
                    colaboradores = recuperarColaboradores();
                    tfColaborador.clear();
                    
                }
            } else {
                lblMensaje.setText("Escriba el nombre de un Colaborador");
            }
        }
    }
    
    /**
     * Este metodo valida que todos los campos cumplan con sus restricciones 
     * y que no sean nulos.
     * @return el mensaje en casi que haya un error.
     */
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(tfTitulo.getText().isEmpty() 
                || tfAnio.getText().isEmpty() 
                || cbProposito.getSelectionModel().isEmpty() 
                || tfCongreso.getText().isEmpty() 
                || tfCiudad.getText().isEmpty()
                || cbEstadoActual.getSelectionModel().isEmpty()
                || (cbEstadoActual.getSelectionModel().getSelectedIndex() == 1 && tfInicio.getText().isEmpty() 
                && tfFin.getText().isEmpty() && tfPDF.getText().isEmpty())
                || tfEstado.getText().isEmpty()
                || cbPais.getSelectionModel().isEmpty()){
            r.setMensaje("No puede haber campos vacíos");
            r.setError(true);
            return r;
        }
        if(!validarTitulo(tfTitulo.getText().trim())){
            r.setError(true);
            r.setMensaje("El titulo de esta memoria ya se encuentra registrado.");
            r.setErrorcode(1);
            return r;
        }
        if(!tfAnio.getText().matches("^(\\d{4})+$")){
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(2);
            return r;
        }
        if (!verificarAnio(tfAnio.getText())) {
            r.setError(true);
            r.setMensaje("El año no puede ser mayor al año actual o menor a 1900.");
            r.setErrorcode(3);
            return r;
        }
        if(tfEstado.getText().trim().matches("^.*\\\\d.*$")){
            r.setError(true);
            r.setMensaje("El estado no puede contener numeros.");
            r.setErrorcode(4);
            return r;
        }
        if(tfCiudad.getText().trim().matches("^.*\\\\d.*$")){
            r.setError(true);
            r.setMensaje("La ciudad no puede contener numeros.");
            r.setErrorcode(4);
            return r;
        }
        if (cbEstadoActual.getSelectionModel().isEmpty()) {
            try {
                Integer inicio = Integer.parseInt(tfInicio.getText().trim());
                Integer fin = Integer.parseInt(tfFin.getText().trim());
                if (!validarPaginas(inicio, fin)) {
                    r.setError(true);
                    r.setMensaje("Las paginas de inicio deben ser menores o iguales a las de fin.");
                    r.setErrorcode(6);
                    return r;
                }
            } catch (Exception e) {
                r.setError(true);
                r.setMensaje("Ingrese solo numeros.");
                r.setErrorcode(6);
                return r;
            }
        }
        if(Objects.equals(file, null)) {
            r.setError(true);
            r.setMensaje("Seleccione un archivo PDF como evidencia.");
            r.setErrorcode(9);
        }
        r.setMensaje("Memoria registrada exitosamente");
        r.setError(false);
        return r;
    }
    
    private void registrarMemoria() {
        Memoria memoria = new Memoria();
        memoria.setCiudad(tfCiudad.getText().trim());
        memoria.setEstado(tfEstado.getText().trim());
        memoria.setNombreCongreso(tfCongreso.getText().trim());
        if (!btnCargar.isDisabled()) {
            String rango = tfInicio.getText().trim() + "-" + tfFin.getText().trim();
            memoria.setRangoPaginas(rango);
        }
        
        List<Memoria> memos = new ArrayList<>();
        memos.add(memoria);
        MemoriaJpaController mJpaC = new MemoriaJpaController();
        mJpaC.create(memoria);
        ///datos del Producto///
        Producto producto = new Producto();
        if (!btnCargar.isDisabled()) {
            byte[] doc;
            try {
                doc = Files.readAllBytes(file.toPath());
                producto.setArchivoPDF(doc);
                producto.setNombrePDF(file.getName());
            } catch (IOException ex) {
                Logger.getLogger(RegistrarPrototipoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        producto.setAnio(Integer.parseInt(tfAnio.getText()));
        producto.setTitulo(tfTitulo.getText().trim());
        producto.setProposito(cbProposito.getSelectionModel().getSelectedItem());
        producto.setIdPais(cbPais.getSelectionModel().getSelectedItem());
        producto.setEstadoActual(cbEstadoActual.getSelectionModel().getSelectedItem());
        //Busco el CA del miembro que esta registrando el producto.
        CaMiembroJpaController camJpaC = new CaMiembroJpaController();
        CuerpoAcademicoJpaController caJpaC = new CuerpoAcademicoJpaController();
        CaMiembro cam = camJpaC.findByMiembro(m.getIdMiembro());
        CuerpoAcademico ca = caJpaC.findCuerpoAcademico(cam.getCaMiembroPK().getIdCuerpoAcademico());
        //Fijo el CuerpoAcademico al producto.
        producto.setIdCuerpoAcademico(ca);
        producto.setMemoriaList(memos);
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(producto)) {
           lblMensaje.setText(ERRORBD);
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
    
    /**
     * Oculta el label del mensaje despies de darle clic.
     */
    @FXML
    private void ocultarMensaje() {
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
    }
    
    /**
     * Habilita los campos necesarios para que correspondan al estado actual.
     * @param event Clic en el estado "Terminado"
     */
    @FXML
    private void cambiarEstado(ActionEvent event) {
        if (cbEstadoActual.getSelectionModel().getSelectedIndex() == 1) {
            tfInicio.setDisable(false);
            tfFin.setDisable(false);
            btnCargar.setDisable(false);
            tfPDF.setDisable(false);
        } else {
            tfInicio.setDisable(true);
            tfFin.setDisable(true);
            btnCargar.setDisable(true);
            tfPDF.setDisable(true);
            tfPDF.clear();
            tfInicio.clear();
            tfFin.clear();
        }
    }
}