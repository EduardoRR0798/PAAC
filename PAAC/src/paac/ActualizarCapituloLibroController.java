/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.Colaborador;
import entity.CapituloLibro;
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
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import persistence.CapituloLibroJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Enrique Ceballos Mtz
 */

  

 

public class ActualizarCapituloLibroController extends ControladorProductos implements Initializable{
      @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfNombreCapitulo;
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
    private TextField tfPropisito;
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
      private ArrayList<Miembro> mInvolucrados = new ArrayList<>();
    private ArrayList<Colaborador> cInvolucrados = new ArrayList<>();
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
    private Producto p;
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   colaboradores = recuperarColaboradores();
        miembros = recuperarMiembros();
        cbPais.setItems((ObservableList<Pais>) recuperarPaises());
        
    }
    
      public ActualizarCapituloLibroController() {
        
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
                tfArchivoPDF.setText(file.getPath());
            } else {
                lblNotificacion.setText("El archivo debe ser menor a 10 MB.");
            }
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
        System.out.print(p.getIdProducto());
        cInvolucrados = recuperarColaboradoresInvolucrados(p);
        mInvolucrados = recuperarMiembrosInvolucrados(p);
        iniciarMiembros();
        iniciarColaboradores();
        iniciarPantalla();
    } 
        /**
     * Valida que todos los datos sean correctos y re.
     * @param event Clic en el boton Aceptar.
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
        }
        
        actualizarProducto();
    }
              public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(tfTitulo.getText().isEmpty() 
                || tfAnio.getText().isEmpty() 
                || tfProposito.getText().isEmpty() 
                || tfTiraje.getText().isEmpty()
                || tfIsbn.getText().isEmpty()
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
              
        r.setMensaje("Capitulo de libro registrada exitosamente");
        r.setError(false);
        return r;
        
          }
    
    /**
     * Cancela el proceso de Actualizacion, devolviendo a la pantalla anterior.
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
     * Registra el producto, la memoria y todas sus relaciones.
     */
    private void actualizarProducto() {
        ///Capitulo de libro

        CapituloLibroJpaController cJpaC = new CapituloLibroJpaController();
        CapituloLibro cap = cJpaC.findByIdProducto(p);
        
 
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
        try {
            cJpaC.edit(cap);
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
         p.setAnio(Integer.parseInt(tfAnio.getText()));
     p.setTitulo(tfTitulo.getText().trim());
    p.setProposito(tfProposito.getText().trim());
      p.setIdPais(cbPais.getSelectionModel().getSelectedItem());
      p.setEstadoActual(tfEstadoActual.getText().trim());
    p.setCapituloLibroList(capitulos);
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
            lblNotificacion.setText("Error al conectar con la base de datos");
        } catch (Exception ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            lblNotificacion.setText("Error al conectar con la base de datos");
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
     * Este metodo agrega los checkmenuitem al menubutton pata una multiple seleccion;
     */
    @FXML
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
     * Oculta el mensaje al darle clic.
     */
    private void ocultarMensaje() {
        lblNotificacion.setText("");
        lblNotificacion.setVisible(false);
    }
    
    /**
     * LLena los campos con los datos del producto seleccionado.
     */
    private void iniciarPantalla() {
        CapituloLibro cap =new CapituloLibro();
        CapituloLibroJpaController cJpaC = new CapituloLibroJpaController();
        List<CapituloLibro> capitulos = cJpaC.findCapituloLibroEntities();
        System.out.println(capitulos.size());
        for (int i = 0; i < capitulos.size(); i++) {
            if (Objects.equals(p.getIdProducto(), capitulos.get(i).getIdProducto().getIdProducto())){
                 cap = capitulos.get(i);
              
            }
        }
        tfTitulo.setText(p.getTitulo());
        tfAnio.setText(p.getAnio().toString());
        tfProposito.setText(p.getProposito());
        tfEstadoActual.setText(p.getEstadoActual());
      
    
    String[] pags = cap.getRangoPaginas().split("-");
      tfPagInicio.setText(pags[0]);
        tfPagFinal.setText(pags[1]); 
        tfTiraje.setText(cap.getTiraje().toString());
    tfArchivoPDF.setText(p.getNombrePDF());
    tfNombreCapitulo.setText(cap.getTituloLibro());
    tfNombreLibro.setText(cap.getNombreLibro());
      
        tfEditorial.setText(cap.getEditorial());
  tfEdicion.setText(cap.getEdicion().toString());
        tfIsbn.setText(cap.getIsbn());
        tfNombreCapitulo.setText(cap.getTituloLibro());
        tfNombreLibro.setText(cap.getNombreLibro());
        tfArchivoPDF.setText(p.getNombrePDF());
        cbPais.getSelectionModel().select(p.getIdPais());
    }
    
    /**
     * Regresa a la ventana Seleccion de Capitulo.
     */
    private void regresarAVentanaAnterior() {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(
                    "SeleccionaCapituloLibro.fxml"));
            
            Parent seleccion = loader.load();
            Scene scene = new Scene(seleccion);
            Stage stage = new Stage();
            stage.fullScreenProperty();
            stage.setScene(scene);
            stage.show();
         
        } catch (IOException ex) {
            Logger.getLogger(ControladorActualizarMemoria.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    

    
}
