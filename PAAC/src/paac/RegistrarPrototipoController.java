package paac;

import entity.Colaborador;
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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.ColaboradorJpaController;
import persistence.MiembroJpaController;
import persistence.PaisJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.PrototipoJpaController;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduar
 */
public class RegistrarPrototipoController extends ControladorProductos implements Initializable {

    @FXML
    private Label lblMensaje;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfProposito;
    @FXML
    private TextField tfCaracteristicas;
    @FXML
    private TextField tfArchivo;
    @FXML
    private TextField tfEstado;
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
    private MenuButton mbMiembros = new MenuButton();
    @FXML
    private MenuButton mbColaboradores = new MenuButton();
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
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private File file;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboradores = recuperarColaboradores();
        miembros = recuperarMiembros();
        cbPais.setItems((ObservableList<Pais>) recuperarPaises());
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.limitarCampos(tfNombre, 140);
        uc.limitarCampos(tfProposito, 140);
        uc.limitarCampos(tfAnio, 4);
        uc.limitarCampos(tfCaracteristicas, 150);
        uc.limitarCampos(tfEstado, 150);
        uc.limitarCampos(tfInstitucion, 150);
        uc.limitarCampos(tfColaborador, 100);
        uc.limitarCampos(tfEstado, 70);
    }    

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
            registrarPrototipo();
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        }
    }

    /**
     * Cancela la operacion, muestra un mensaje de confirmacion para saber si el
     * miembro desea cancelar la operacion. Si presiona Cancelar lo regresara a 
     * la pagina de registro de productos.
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
            abrirVentanaSeleccion();
        }
    }

    /**
     * Registra un colaborador en la base de datos.
     * @param event Clic en el boton +.
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
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControladorRegistrarMemoria.class.getName()).log(Level.SEVERE, null, ex);
                }
                lblMensaje.setText("");
            }
        }
        tfColaborador.setText("");
    }
    
    /**
     * Abre una ventana de dialogo para seleccionar un archivo PDF.
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
        
        if (fl.length() <= 10000000) {
            file = fl;
            tfArchivo.setText(file.getPath());
        } else {
            lblMensaje.setText("El archivo debe ser menor a 10 MB.");
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
                || tfProposito.getText().isEmpty() 
                || tfCaracteristicas.getText().isEmpty() 
                || tfArchivo.getText().isEmpty() 
                || tfEstado.getText().isEmpty()
                || tfInstitucion.getText().isEmpty()) {
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if(!validarTitulo(tfNombre.getText().trim())){
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
        if(Objects.equals(tfProposito.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El proposito no puede estar vacio");
            r.setErrorcode(3);
            return r;
        }
        if(Objects.equals(tfCaracteristicas.getText(), null)){
            r.setError(true);
            r.setMensaje("Ingrese las caracteristicas.");
            r.setErrorcode(4);
            return r;
        }
        if(Objects.equals(tfEstado.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El estado actual no puede estar vacio.");
            r.setErrorcode(5);
            return r;
        }
        if(cbPais.getSelectionModel().isEmpty()){
            r.setError(true);
            r.setMensaje("Seleccione un pais.");
            r.setErrorcode(8);
            return r;
        }
        if(Objects.equals(file, null)) {
            r.setError(true);
            r.setMensaje("Seleccione un archivo PDF como evidencia.");
            r.setErrorcode(9);
        }
        r.setMensaje("Prototipo actualizado correctamente.");
        r.setError(false);
        return r;
    }
    
    /**
     * Registra un prototipo en la base de datos junto con sus relaciones.
     */
    private void registrarPrototipo() {
        Prototipo proto = new Prototipo();
        proto.setCaracteristicas(tfCaracteristicas.getText().trim());
        proto.setInstitucionCreacion(tfInstitucion.getText().trim());
        List<Prototipo> protos = new ArrayList<>();
        
        PrototipoJpaController prJpaC = new PrototipoJpaController();
        prJpaC.create(proto);
        protos.add(proto);
        ///datos del Producto///
        Producto producto = new Producto();
        byte[] doc;
        try {
            doc = Files.readAllBytes(file.toPath());
            producto.setArchivoPDF(doc);
            producto.setNombrePDF(file.getName());
        } catch(IOException ex) {
            Logger.getLogger(RegistrarPrototipoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        producto.setAnio(Integer.parseInt(tfAnio.getText().trim()));
        producto.setTitulo(tfNombre.getText().trim());
        producto.setProposito(tfProposito.getText().trim());
        producto.setIdPais(cbPais.getSelectionModel().getSelectedItem());
        producto.setEstadoActual(tfEstado.getText().trim());
        producto.setPrototipoList(protos);
        ProductoJpaController pJpaC = new ProductoJpaController();
        if (!pJpaC.create(producto)) {
           lblMensaje.setText("Error al conectar con la base de datos...");
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
        System.out.println(mis.size());
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
     * Oculta el label que muestra un mensaje dando un clic en el.
     */
    @FXML
    private void ocultarMensaje() {
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
    }
    
    /**
     * Abre la ventana de seleccion de productos a registrar.
     */
    private void abrirVentanaSeleccion() {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionProductos.fxml"));
            
            Parent responder = loader.load();
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.show();
            ((Node) (btnCancelar)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}