package paac;

import entity.Colaborador;
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
import persistence.MemoriaJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.exceptions.NonexistentEntityException;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class ControladorActualizarMemoria extends ControladorProductos implements Initializable {

    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfProposito;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfCongreso;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfRango;
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
    
    private Producto p;
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private ArrayList<Miembro> mInvolucrados = new ArrayList<>();
    private ArrayList<Colaborador> cInvolucrados = new ArrayList<>();
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
        uc.limitarCampos(tfTitulo, 140);
        uc.limitarCampos(tfProposito, 140);
        uc.limitarCampos(tfAnio, 4);
        uc.limitarCampos(tfCongreso, 150);
        uc.limitarCampos(tfEstado, 150);
        uc.limitarCampos(tfCiudad, 150);
        uc.limitarCampos(tfRango, 20);
        uc.limitarCampos(tfColaborador, 100);
    }    
    
    public ControladorActualizarMemoria() {
        
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
        if (fl.length() <= 10000000) {
            file = fl;
            tfPDF.setText(file.getPath());
        } else {
            lblMensaje.setText("El archivo debe ser menor a 10 MB.");
        }
        
    }
    
    /**
     * Recibe el id del producto seleccionado anteriormente.
     * @param pro id del producto.
     */
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
    
    /**
     * Valida que todos los datos sean correctos y registra la memoria.
     * @param event Clic en el boton Aceptar.
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
        }
        
        actualizarProducto();
    }

    /**
     * Cancela el proceso de registro, devolviendo a la pantalla anterior.
     * @param event Clic en el boton cancelar.
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
            regresarAVentanaAnterior();
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
     * Sirve para agregar a un colaborador a la base de datos y a la lista.
     * @param event clic en el boton +
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
     * Este metodo valida que todos los campos cumplan con sus restricciones 
     * y que no sean nulos.
     * @return el mensaje en casi que haya un error.
     */
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(tfTitulo.getText().isEmpty() 
                || tfAnio.getText().isEmpty() 
                || tfProposito.getText().isEmpty() 
                || tfCongreso.getText().isEmpty() 
                || tfCiudad.getText().isEmpty() 
                || tfEstado.getText().isEmpty()
                || tfRango.getText().isEmpty() 
                || cbPais.getSelectionModel().isEmpty()
                ||tfEstadoActual.getText().isEmpty()){
            r.setMensaje("No puede haber campos vacíos");
            r.setError(true);
            return r;
        }
        if (!validarTituloActualizar(tfTitulo.getText().trim(), p.getIdProducto())) {
            r.setError(true);
            r.setMensaje("Este titulo ya se encuentra registrado");
            r.setErrorcode(1);
            return r;
        }
        if(!tfAnio.getText().matches("^(\\d{4})+$")){
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(2);
            return r;
        }
        if (!super.verificarAnio(tfAnio.getText())) {
            r.setError(true);
            r.setMensaje("El año no puede ser mayor al año actual o menor a 1900.");
            r.setErrorcode(3);
            return r;
        }
        if(Objects.equals(tfCongreso.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El congreso no puede estar vacio.");
            r.setErrorcode(4);
            return r;
        }
        if(Objects.equals(tfEstado.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El nombre de la editorial no puede ser mayor a 10 caracteres y no permite caracteres especiales");
            r.setErrorcode(5);
            return r;
        }
        if(!tfRango.getText().matches("^((0|[1-9]+[0-9]*)-(0|[1-9]+[0-9]*);|(0|[1-9]+[0-9]*);)*?((0|[1-9]+[0-9]*)-(0|[1-9]+[0-9]*)|(0|[1-9]+[0-9]*)){1}$")){
            r.setError(true);
            r.setMensaje("Ingrese un formato valido. Ejem. 1-2");
            r.setErrorcode(6);
            return r;
        }
        if(cbPais.getSelectionModel().isEmpty()){
            r.setError(true);
            r.setMensaje("Seleccione un pais.");
            r.setErrorcode(8);
            return r;
        }
        r.setMensaje("Memoria actualizada exitosamente");
        r.setError(false);
        return r;
    }
    
    /**
     * Registra el producto, la memoria y todas sus relaciones.
     */
    private void actualizarProducto() {
        ///memoria
        MemoriaJpaController mJpaC = new MemoriaJpaController();
        Memoria memoria = mJpaC.findByIdProducto(p);
        memoria.setCiudad(tfCiudad.getText().trim());
        memoria.setEstado(tfEstado.getText().trim());
        memoria.setNombreCongreso(tfCongreso.getText());
        memoria.setRangoPaginas(tfRango.getText());
        List<Memoria> memos = new ArrayList<>();
        memos.add(memoria);
        
        try {
            mJpaC.edit(memoria);
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        p.setAnio(Integer.parseInt(tfAnio.getText()));
        p.setTitulo(tfTitulo.getText());
        p.setProposito(tfProposito.getText());
        p.setIdPais(cbPais.getSelectionModel().getSelectedItem());
        p.setEstadoActual(tfEstadoActual.getText());
        p.setMemoriaList(memos);
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
            lblMensaje.setText("Error al conectar con la base de datos");
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            lblMensaje.setText("Error al conectar con la base de datos");
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
                productMiem = pmJpaC.findByIdPM(mInvolucrados.get(i).getIdMiembro(), p.getIdProducto());
                try {
                    pmJpaC.destroy(productMiem.getIdMiembroProducto());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
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
     * Oculta el mensaje al darle clic.
     */
    @FXML
    private void ocultarMensaje() {
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
    }
    
    /**
     * LLena los campos con los datos del producto seleccionado.
     */
    private void iniciarPantalla() {
        Memoria memo = new Memoria();
        MemoriaJpaController mJpaC = new MemoriaJpaController();
        List<Memoria> ms = mJpaC.findMemoriaEntities();
        for (int i = 0; i < ms.size(); i++) {
            if (Objects.equals(p.getIdProducto(), ms.get(i).getIdProducto().getIdProducto())) {
                memo = ms.get(i);
            }
        }
        tfTitulo.setText(p.getTitulo());
        tfAnio.setText(p.getAnio().toString());
        tfProposito.setText(p.getProposito());
        tfCongreso.setText(memo.getNombreCongreso());
        tfEstado.setText(memo.getEstado());
        tfEstadoActual.setText(p.getEstadoActual());
        tfCiudad.setText(memo.getCiudad());
        tfRango.setText(memo.getRangoPaginas());
        tfPDF.setText(p.getNombrePDF());
        cbPais.getSelectionModel().select(p.getIdPais());
    }
    
    /**
     * Regresa a la ventana Seleccion de Memoria.
     */
    private void regresarAVentanaAnterior() {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "SeleccionarMemoria.fxml"));
            
            Parent seleccion = loader.load();
            Scene scene = new Scene(seleccion);
            Stage stage = new Stage();
            stage.fullScreenProperty();
            stage.setScene(scene);
            stage.show();
             ((Node) (btnCancelar)).getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}