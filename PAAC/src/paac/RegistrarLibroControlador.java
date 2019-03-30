/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.Colaborador;
import entity.Libro;
import entity.Miembro;
import entity.Pais;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.ColaboradorJpaController;
import persistence.LibroJpaController;
import persistence.MiembroJpaController;
import persistence.PaisJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;

/**
 * FXML Controller class
 *
 * @author Foncho
 */
public class RegistrarLibroControlador implements Initializable {

    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private Button agregar;
    @FXML
    private Label errorlbl;
    @FXML
    private Label lblNombreColaborador;
    @FXML
    private TextField titulotxt;
    @FXML
    private TextField aniotxt;
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
    private TextArea propositotxt;
    @FXML
    private ComboBox cbPaises;
    @FXML
    private MenuButton mbMiembros = new MenuButton();
    @FXML
    private MenuButton mbColaboradores = new MenuButton();
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField tfColaborador;
    @FXML
    private Label lblMensaje;
    private Object tfEstadoActual;
    
    
    @FXML
    private void clickCancelar(ActionEvent event) {
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
    
    @FXML
    private void clickAceptar(ActionEvent event) {
        Respuesta r = validarCampos();
        if(r.isError()){
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
        }else{
            errorlbl.setText(r.getMensaje());
            errorlbl.setVisible(true);
        }
    }
    
    @FXML
    private void clickAgregar(ActionEvent event) {
        if (!tfColaborador.isVisible()) {
            lblNombreColaborador.setVisible(true);
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
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ControladorRegistrarMemoria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        tfColaborador.setText("");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        recuperarColaboradores();
        recuperarMiembros();
        recuperarPaises();
    }    
    
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(titulotxt.getText().isEmpty() || aniotxt.getText().isEmpty() 
                || propositotxt.getText().isEmpty() || isbntxt.getText().isEmpty() 
                || editorialtxt.getText().isEmpty() || paginastxt.getText().isEmpty()
                || ediciontxt.getText().isEmpty() || ejemplarestxt.getText().isEmpty()){
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if(titulotxt.getText().length()>10){
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 10 caracteres");
            r.setErrorcode(1);
            return r;
        }
        if(!aniotxt.getText().matches("^(\\d{4})+$")){
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(2);
            return r;
        }
        if(propositotxt.getText().length()>10){
            r.setError(true);
            r.setMensaje("El proposito no puede tener mas de 10 caracteres");
            r.setErrorcode(3);
            return r;
        }
        if(!isbntxt.getText().matches("^(97(8|9))?\\d{9}(\\d|X)$")){
            r.setError(true);
            r.setMensaje("Ingrese un número ISBN válido de 10 o 13 dígitos");
            r.setErrorcode(4);
            return r;
        }
        if(editorialtxt.getText().length()>10){
            r.setError(true);
            r.setMensaje("El nombre de la editorial no puede ser mayor a 10 caracteres y no permite caracteres especiales");
            r.setErrorcode(5);
            return r;
        }
        if(!paginastxt.getText().matches("^[+]?\\d*$") && paginastxt.getText().length()<10){
            r.setError(true);
            r.setMensaje("Solo se permite números en Páginas");
            r.setErrorcode(6);
            return r;
        }
        if(!ediciontxt.getText().matches("^[+]?\\d*$") && ediciontxt.getText().length()<10){
            r.setError(true);
            r.setMensaje("Solo se permite números en Edición");
            r.setErrorcode(7);
            return r;
        }
        if(!ejemplarestxt.getText().matches("^[+]?\\d*$") && ejemplarestxt.getText().length()<10){
            r.setError(true);
            r.setMensaje("Solo se permite números en Ejemplares");
            r.setErrorcode(8);
            return r;
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }
    
    public void registrarProducto(){
        ///memoria
        ///producto
        ///colaborador
        ///
        //datos de la memoria///
        Libro libro = new Libro();
        libro.setIsbn(isbntxt.getText());
        libro.setEditorial(isbntxt.getText());
        libro.setNumPaginas(Integer.valueOf(paginastxt.getText()));
        libro.setEdicion(Integer.valueOf(ediciontxt.getText()));
        libro.setTiraje(Integer.valueOf(ejemplarestxt.getText()));
        
        List<Libro> libros = new ArrayList<>();
        libros.add(libro);
        LibroJpaController ljpac = new LibroJpaController();
        ljpac.create(libro);
        
        ///datos del Producto///
        
        Producto producto = new Producto();
        producto.setAnio(Integer.parseInt(aniotxt.getText()));
        producto.setTitulo(titulotxt.getText());
        producto.setProposito(propositotxt.getText());
        producto.setIdPais((Pais) cbPaises.getSelectionModel().getSelectedItem());
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(producto)) {
           lblMensaje.setText("Error al conectar con la base de datos...");
           lblMensaje.setVisible(true);
        }
        System.out.println(producto.getIdProducto());
        ///datos del producto-colaborador///
        ObservableList<Colaborador> colas = lstColaboradores.getSelectionModel().getSelectedItems();
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
        ObservableList<Miembro> mis = lstAutores.getSelectionModel().getSelectedItems();
        ProductoMiembroJpaController pmJpaC = new ProductoMiembroJpaController();
        
        ProductoMiembro pm;
        for (int i = 0; i < colas.size(); i++) {
            pm = new ProductoMiembro();
            pm.setIdMiembro(mis.get(i));
            pm.setIdProducto(producto);
            pmJpaC.create(pm);
        }
    }
    
    private boolean recuperarPaises() {
        PaisJpaController pJpaC = new PaisJpaController();
        List<Pais> ps;
        ps = pJpaC.findAll();
        for (int i = 0; i < ps.size(); i++) {
            paises.add(ps.get(i));
        }
        if (paises.isEmpty()) {
            return false;
        }
        cbPaises.setItems(paises);
        return true;
    }
    
    /**
     * Recupera a todos los miembros de la base de datos.
     * @return true si pudo recuperar algo, false si no.
     */
    private boolean recuperarMiembros() {
        Miembro m = new Miembro();
        MiembroJpaController mJpaC = new MiembroJpaController();
        List<Miembro> ms = new ArrayList<>();
        ms = mJpaC.findAll();
        
        if (ms.isEmpty()) {
            return false;
        }
        for (int i = 0; i < ms.size(); i++) {
            miembros.add(ms.get(i));
        }
        //lstAutores.setItems(miembros);
        iniciarMiembros();
        return true;
    }
    
    /**
     * Recupera a todos los colaboradores de la base de datos;
     * @return true si pudo recuperar algo, false si no.
     */
    private boolean recuperarColaboradores() {
        Colaborador c = new Colaborador();
        ColaboradorJpaController cJpaC = new ColaboradorJpaController();
        List<Colaborador> ls = new ArrayList<>();
        ls = cJpaC.findAll();
        for (int i = 0; i <ls.size(); i++) {
            colaboradores.add(ls.get(i));
        }
        if (colaboradores.isEmpty()) {
            return false;
        }
        iniciarColaboradores();
        return true;
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
