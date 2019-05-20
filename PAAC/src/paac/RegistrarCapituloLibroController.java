/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.CapituloLibro;
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
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.CapituloLibroJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import paac.ControladorProductos;
import persistence.ColaboradorJpaController;
import utilidades.UtilidadCadenas;


/**
 * FXML Controller class
 *
 * @author Enrique Ceballos Mtz
 */
public class RegistrarCapituloLibroController extends ControladorProductos implements Initializable {

    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfNombreCapitulo;
    private TextField tfEstado;
    @FXML
    private TextField tfEditorial;
    @FXML
    private TextField tfEdicion;
    @FXML
    private TextField tfTiraje;
    @FXML
    private TextField tfIsbn;
    @FXML
    private TextField tfAnio;
    private TextField tfPropisuto;
     @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private TextField tfArchivoPDF;
    private TextField tfRangoPag;
    @FXML
    private ComboBox<Pais> cbPais;
    @FXML
    private TextField tfNombreLibro;
     // atributos necesarios
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    private File file;
    @FXML
    private TextField tfPagInicio;
    @FXML
    private TextField tfPagFinal;
    @FXML
    private Label lblNotificacion;
    @FXML
    private TextField tfProposito;
    @FXML
    private TextField tfEstadoActual;
    @FXML
    private MenuButton mbColaboradores;
    @FXML
    private MenuButton mbMiembros;
    @FXML
    private TextField tfColaborador;
    @FXML
    private Button btAgregar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         colaboradores = super.recuperarColaboradores();
        miembros = super.recuperarMiembros();
        paises = recuperarPaises();
        cbPais.setItems(paises);
        cbPais.getSelectionModel().select(116);
        iniciarMiembros();
        iniciarColaboradores();
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.limitarCampos(tfEditorial,40);
        uc.limitarCampos(tfNombreCapitulo, 80);
        uc.limitarCampos(tfIsbn, 30);
        uc.limitarCampos(tfPagInicio, 4);
        uc.limitarCampos(tfPagFinal, 4);
        uc.limitarCampos(tfNombreLibro, 80);
        uc.limitarCampos(tfTiraje, 11);
        uc.limitarCampos(tfTitulo, 140);
        uc.limitarCampos(tfProposito, 140);
        uc.limitarCampos(tfAnio, 4);
 
        uc.limitarCampos(tfColaborador, 100);
        
        
        
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
                tfArchivoPDF.setText(file.getPath());
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
                || tfTiraje.getText().isEmpty()
                || tfTitulo.getText().isEmpty()
                || tfEdicion.getText().isEmpty()
                || tfEditorial.getText().isEmpty()
                || tfNombreCapitulo.getText().isEmpty()
                || tfNombreLibro.getText().isEmpty()
                || cbPais.getSelectionModel().isEmpty()
                || tfEstadoActual.getText().isEmpty()){
            r.setMensaje("No puede haber campos vacíos");
            r.setError(true);
            return r;
       
        } 
        r.setMensaje("Capitulo de libro registrada exitosamente");
        r.setError(false);
        return r;
          }
          
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
            registrarCapituloLibro();
        }
    }
       private void registrarCapituloLibro() {
           CapituloLibro cap = new CapituloLibro();
           cap.setTituloLibro(tfNombreCapitulo.getText().trim());
           cap.setNombreLibro(tfNombreLibro.getText().trim());
           cap.setEdicion(Integer.parseInt(tfEdicion.getText()));
           cap.setRangoPaginas(tfPagInicio.getText().trim() + "-" + tfPagFinal.getText().trim());
           cap.setEdicion(tfEdicion.getText());
           cap.setEditorial(tfEditorial.getText().trim());
           cap.setIsbn(tfIsbn.getText().trim());
           cap.setTiraje(Integer.parseInt(tfTiraje.getText()));
        List<CapituloLibro> capitulos = new ArrayList<>();
        capitulos.add(cap);
           CapituloLibroJpaController cJpaC = new CapituloLibroJpaController();
           cJpaC.create(cap);
    
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
        producto.setCapituloLibroList(capitulos);
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
