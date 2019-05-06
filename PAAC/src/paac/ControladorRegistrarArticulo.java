package paac;

import entity.Articulo;
import entity.ArticuloIndexado;
import entity.Colaborador;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.ArticuloIndexadoJpaController;
import persistence.ArticuloJpaController;
import persistence.ColaboradorJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduar
 */
public class ControladorRegistrarArticulo extends ControladorProductos implements Initializable {

    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfRevista;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfInicio;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfVolumen;
    @FXML
    private TextField tfISSN;
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
    private Label lblMensaje;
    @FXML
    private MenuButton mbMiembros;
    @FXML
    private MenuButton mbColaboradores;
    @FXML
    private TextField tfEstadoActual;
    @FXML
    private TextField tfPDF;
    @FXML
    private Button btnCargar;
    @FXML
    private TextField tfFin;
    @FXML
    private TextField tfProposito;
    @FXML
    private CheckBox chkIndexado;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfIndice;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblDireccion;
    @FXML
    private Label lblIndice;
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private File file;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colaboradores = super.recuperarColaboradores();
        miembros = super.recuperarMiembros();
        cbPais.setItems(recuperarPaises());
        iniciarMiembros();
        iniciarColaboradores();
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.limitarCampos(tfTitulo, 140);
        uc.limitarCampos(tfProposito, 140);
        uc.limitarCampos(tfAnio, 4);
        uc.limitarCampos(tfEditorial, 150);
        uc.limitarCampos(tfEstadoActual, 150);
        uc.limitarCampos(tfISSN, 150);
        uc.limitarCampos(tfRevista, 80);
        uc.limitarCampos(tfInicio, 4);
        uc.limitarCampos(tfFin, 4);
        uc.limitarCampos(tfVolumen, 50);
        uc.limitarCampos(tfDescripcion, 255);
        uc.limitarCampos(tfDireccion, 255);
        uc.limitarCampos(tfColaborador, 100);
        uc.limitarCampos(tfIndice, 255);
    }    

    @FXML
    private void aceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if (r.isError()) {
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        } else {
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
            registrarArticulo();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Alert cancelar = new Alert(Alert.AlertType.CONFIRMATION);
        cancelar.setTitle("Cancelar proceso");
        cancelar.setHeaderText(null);
        cancelar.initStyle(StageStyle.UTILITY);
        cancelar.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancelar.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.exit(0);
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
                    recuperarColaboradores();
                }
            } else {
                lblMensaje.setText("Escriba el nombre de un Colaborador");
            }
        }
    }

    /**
     * Oculta el label del mensaje despies de darle clic.
     */
    @FXML
    private void ocultarMensaje(MouseEvent event) {
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
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

    @FXML
    private void crearIndexado(ActionEvent event) {
        if (chkIndexado.isSelected()) {
            lblDescripcion.setVisible(true);
            lblDireccion.setVisible(true);
            lblIndice.setVisible(true);
            tfDescripcion.setVisible(true);
            tfDireccion.setVisible(true);
            tfIndice.setVisible(true);
        } else {
            lblDescripcion.setVisible(false);
            lblDireccion.setVisible(false);
            lblIndice.setVisible(false);
            tfDescripcion.setVisible(false);
            tfDireccion.setVisible(false);
            tfIndice.setVisible(false);
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
                || tfProposito.getText().isEmpty() 
                || tfEditorial.getText().isEmpty() 
                || tfEstadoActual.getText().isEmpty() 
                || tfISSN.getText().isEmpty()
                || tfRevista.getText().isEmpty() 
                || cbPais.getSelectionModel().isEmpty()
                || tfInicio.getText().isEmpty()
                || tfFin.getText().isEmpty()
                || tfVolumen.getText().isEmpty()){
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
        if(cbPais.getSelectionModel().isEmpty()){
            r.setError(true);
            r.setMensaje("Seleccione un pais.");
            r.setErrorcode(8);
            return r;
        }//"^.*\\d.*$"
        try{
            Integer pag1 = Integer.parseInt(tfInicio.getText().trim());
            Integer pag2 = Integer.parseInt(tfFin.getText().trim());
            if (!validarPaginas(pag1, pag2)) {
                r.setError(true);
                r.setMensaje("Las paginas iniciales deben ser menores a las finales.");
                r.setErrorcode(8);
                return r;
            }
        } catch (Exception e) {
            r.setError(true);
            r.setMensaje("Ingrese un numero valido para las paginas.");
            r.setErrorcode(8);
            return r;
        }
        if(Objects.equals(file, null)) {
            r.setError(true);
            r.setMensaje("Seleccione un archivo PDF como evidencia.");
            r.setErrorcode(9);
            return r;
        }
        if (chkIndexado.isSelected()) {
            if (tfIndice.getText().isEmpty()
                    ||tfDireccion.getText().isEmpty()
                    || tfDescripcion.getText().isEmpty()){  
                r.setError(true);
                r.setMensaje("Los datos del articulo indexado no pueden estar vacios.");
                r.setErrorcode(10);
                return r;
            }
            if (tfDireccion.getText().trim().matches("^((http:\\/\\/www\\.)|(www\\.)|(http:\\/\\/))[a-zA-Z0-9._-]+\\.[a-zA-Z.]{2,5}$")) {
                r.setError(true);
                r.setMensaje("Ingrese una direccion web valida.");
                r.setErrorcode(10);
                return r;
            }
        }
        r.setMensaje("Articulo registrado exitosamente");
        r.setError(false);
        return r;
    }
    
    /**
     * Registra un articulo y sus relaciones.
     */
    private void registrarArticulo() {
        List<ArticuloIndexado> artsIn = new ArrayList<>();
        if (chkIndexado.isSelected()) {
            ArticuloIndexado ai = new ArticuloIndexado();
            ai.setDescripcion(tfDescripcion.getText().trim());
            ai.setDireccionElectronica(tfDireccion.getText().trim());
            ai.setIndice(tfIndice.getText().trim());
            artsIn.add(ai);
            ArticuloIndexadoJpaController aiJpaC = new ArticuloIndexadoJpaController();
            aiJpaC.create(ai);
        }
        Articulo articulo = new Articulo();
        articulo.setEditorial(tfEditorial.getText().trim());
        articulo.setIssn(tfISSN.getText().trim());
        articulo.setNombreRevista(tfRevista.getText().trim());
        String rango = tfInicio.getText().trim() + "-" + tfFin.getText().trim();
        articulo.setRangoPaginas(rango);
        articulo.setVolumen(tfVolumen.getText().trim());
        if (artsIn.size() > 0) {
            articulo.setArticuloIndexadoList(artsIn);
        }
        List<Articulo> artics = new ArrayList<>();
        artics.add(articulo);
        ArticuloJpaController aJpaC = new ArticuloJpaController();
        aJpaC.create(articulo);
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
        producto.setAnio(Integer.parseInt(tfAnio.getText()));
        producto.setTitulo(tfTitulo.getText().trim());
        producto.setProposito(tfProposito.getText().trim());
        producto.setIdPais(cbPais.getSelectionModel().getSelectedItem());
        producto.setEstadoActual(tfEstadoActual.getText().trim());
        producto.setArticuloList(artics);
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(producto)) {
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
    
    
}
