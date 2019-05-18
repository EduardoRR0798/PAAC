package paac;

import entity.Colaborador;
import entity.Miembro;
import entity.Pais;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import persistence.PatenteJpaController;
import entity.Patente;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.stage.StageStyle;
import persistence.ColaboradorJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
/**
 * FXML Controller class
 *
 * @author Enrique Ceballos Mtz
 */
    public class RegistrarPatenteController extends ControladorProductos implements Initializable {

    @FXML
    private ComboBox<Pais> cbPais;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfEstadoActual;
    @FXML
    private TextField tfDescripcion;
    @FXML
    private TextField tfClasificacion;
    @FXML
    private TextField tfTipo;
    @FXML
    private TextField tfPdf;
    @FXML
    private Label lblNotificacion;
    private ListView<Miembro> lwMiembros;
    private File file;
    @FXML
    private TextField tfNombreEvidencia;
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfProposito;
    @FXML
    private MenuButton mbAutores;
    @FXML
    private MenuButton mbColaboradores;
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private Button btAgregar;
    @FXML
    private TextField tfColaborador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         colaboradores = super.recuperarColaboradores();
        miembros = super.recuperarMiembros();
        paises = recuperarPaises();
        cbPais.setItems(paises);
        iniciarMiembros();
        iniciarColaboradores();
    }    
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
                tfPdf.setText(file.getPath());
            } else {
                lblNotificacion.setText("El archivo debe ser menor a 10 MB.");
            }
        }
    }
             public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
  
        if(tfTitulo.getText().isEmpty() 
                || tfAnio.getText().isEmpty() 
                || tfProposito.getText().isEmpty()
                || tfClasificacion.getText().isEmpty()
                || tfDescripcion.getText().isEmpty()
               || tfNombreEvidencia.getText().isEmpty()
                || tfPdf.getText().isEmpty()
                || tfTipo.getText().isEmpty()
                || cbPais.getSelectionModel().isEmpty()
                || tfEstadoActual.getText().isEmpty()){
            r.setMensaje("No puede haber campos vacíos");
            r.setError(true);
            return r;
            
       
        } 
        r.setMensaje("Patente registrada exitosamente");
        r.setError(false);
        return r;
          }
          
              //    || tfNombre.getText().isEmpty()
             
             
      /**
     * Registra Capitulo de libro tras validar que los datos introducidos por el usuario.
     * @param event Clic en el boton Aceptar
     */
    @FXML
    private void aceptar(ActionEvent event) {
        
        Respuesta r = validarCampos();
        if (r.isError()) {
            lblNotificacion.setText(r.getMensaje());
            lblNotificacion.setVisible(true);
        } else {
            lblNotificacion.setText(r.getMensaje());
            lblNotificacion.setVisible(true);
            registrarPatente();
        }
    }
       private void registrarPatente() {
           Patente pa = new Patente();
           pa.setTipo(tfTipo.getText().trim());
           pa.setDescripcion(tfDescripcion.getText().trim());
           pa.setClasifIntlPatentes(tfClasificacion.getText().trim());
  
          List<Patente> patentes =new ArrayList<>();
          patentes.add(pa);
          PatenteJpaController pJpaC = new PatenteJpaController();
          pJpaC.create(pa);
    
        ///datos del Producto///
        
        Producto producto = new Producto();
       byte [] doc;
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
        producto.setPatenteList(patentes);
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(producto)) {
           lblNotificacion.setText("Error al conectar con la base de datos...");
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
        System.out.println(mis);
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
        mbAutores.getItems().setAll(items);
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
     * Muestra un campo para agregar un nuevo colaborador.
     * @param event Clic en el boton Agregar Colaborador
     */
    @FXML
    private void mostrarCampoColaborador(ActionEvent event) {
        if (!tfColaborador.isVisible()) {
            tfColaborador.setVisible(true);
            tfColaborador.setDisable(false);
            btAgregar.setVisible(true);
            btAgregar.setDisable(false);
        } else {
            tfColaborador.setVisible(false);
            tfColaborador.setDisable(true);
            btAgregar.setVisible(false);
            btAgregar.setDisable(true);
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
                lblNotificacion.setText("Escriba el nombre de un Colaborador");
            }
        }
    }
    
       /**
     * Muestra el cuadro de dialogo de confirmacion para cancelar la operacion.
     * @param event clic en el boton Cancelar
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
            System.exit(0);
        }
    }
    
             
}
