/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.Colaborador;
import entity.Miembro;
import entity.Pais;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import entity.Tesis;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.ColaboradorJpaController;
import persistence.MiembroJpaController;
import persistence.PaisJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import persistence.TesisJpaController;

/**
 * FXML Controller class
 *
 * @author Foncho
 */
public class RegistrarTesisControlador implements Initializable {
    @FXML
    private TextField titulotxt;
    @FXML
    private ComboBox<Pais> cbPaises;
    @FXML
    private TextArea propositotxt;
    @FXML
    private TextField registrotxt;
    @FXML
    private TextField descripciontxt;
    @FXML
    private TextField usuariotxt;
    @FXML
    private DatePicker fechadp;
    @FXML
    private TextField gradotxt;
    @FXML
    private TextField clasificaciontxt;
    @FXML
    private TextField estadotxt;
    @FXML
    private Label errorlbl;
    @FXML
    private Label lblNombreColaborador;
    @FXML
    private TextField tfColaborador;
    @FXML
    private Button btnAgregar;
    @FXML
    private Label lblMensaje;
    @FXML
    private Button cancelar;
    @FXML
    private Button aceptar;
    @FXML
    private MenuButton mbMiembros;
    @FXML
    private ListView<Miembro> lstAutores;
    @FXML
    private Button agregar;
    @FXML
    private ListView<Colaborador> lstColaboradores;
    @FXML
    private MenuButton mbColaboradores;
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();

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
            registrarProducto();
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
    
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        if(titulotxt.getText().isEmpty() || gradotxt.getText().isEmpty() 
                || propositotxt.getText().isEmpty() || registrotxt.getText().isEmpty() 
                || descripciontxt.getText().isEmpty() || usuariotxt.getText().isEmpty()
                || fechadp.getValue()==null || cbPaises.getValue() == null ||
                clasificaciontxt.getText() == null || estadotxt.getText() == null){
            r.setError(true);
            r.setMensaje("No puede haber campos vacíos");
            r.setErrorcode(1);
            return r;
        }
        if(titulotxt.getText().length()>100){
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 100 caracteres");
            r.setErrorcode(1);
            return r;
        }
        if(gradotxt.getText().length()>20){
            r.setError(true);
            r.setMensaje("El grado no puede tener mas de 20 caracteres");
            r.setErrorcode(2);
            return r;
        }
        if(propositotxt.getText().length()>10){
            r.setError(true);
            r.setMensaje("El proposito no puede tener mas de 10 caracteres");
            r.setErrorcode(3);
            return r;
        }
        if(registrotxt.getText().length()>20){
            r.setError(true);
            r.setMensaje("El registro no puede tener mas de 20 caracteres");
            r.setErrorcode(4);
            return r;
        }
        if(descripciontxt.getText().length()>10){
            r.setError(true);
            r.setMensaje("La descripcion no puede tener mas de 10 caracteres");
            r.setErrorcode(5);
            return r;
        }
        if(usuariotxt.getText().length()>20){
            r.setError(true);
            r.setMensaje("El usuario no debe ser mayor a 20");
            r.setErrorcode(6);
            return r;
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
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
    
    public void registrarProducto(){
        ///tesis
        ///producto
        ///colaborador
        ///
        //datos de la tesis///
        Tesis tesis = new Tesis();
        tesis.setGrado(gradotxt.getText());
        tesis.setNumRegistro(Integer.valueOf(registrotxt.getText()));
        tesis.setClasificacionInter(clasificaciontxt.getText());
        tesis.setDescripcion(descripciontxt.getText());
        tesis.setUsuarioDirigido(usuariotxt.getText());
        
        TesisJpaController tjc = new TesisJpaController();
        tjc.create(tesis);
        ///datos del Producto///
        
        Producto producto = new Producto();
        producto.setAnio(fechadp.getValue().getYear());
        producto.setTitulo(titulotxt.getText());
        producto.setProposito(propositotxt.getText());
        producto.setEstadoActual(estadotxt.getText());
        producto.setIdPais((Pais) cbPaises.getSelectionModel().getSelectedItem());
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(producto)) {
           lblMensaje.setText("Error al conectar con la base de datos...");
           lblMensaje.setVisible(true);
        }
        System.out.println(producto.getIdProducto());
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
}
