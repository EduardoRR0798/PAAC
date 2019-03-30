package paac;

import entity.Colaborador;
import persistence.ColaboradorJpaController;
import entity.Memoria;
import entity.Miembro;
import entity.Pais;
import persistence.PaisJpaController;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.MemoriaJpaController;
import persistence.MiembroJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class ControladorRegistrarMemoria implements Initializable {

    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfProposito = new TextField();
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
    private TextField tfEstadoActual;
    @FXML
    private Label lblMensaje;
    @FXML
    private MenuButton mbMiembros = new MenuButton();
    @FXML
    private MenuButton mbColaboradores = new MenuButton();
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
    private DatePicker dpFecha;
    // atributos necesarios
    private ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
    private ObservableList<Pais> paises = FXCollections.observableArrayList();
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recuperarColaboradores();
        recuperarMiembros();
        recuperarPaises();
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.limitarCampos(tfTitulo, 140);
//        uc.limitarCampos(tfProposito, 140);
        uc.limitarCampos(tfAnio, 4);
        uc.limitarCampos(tfCongreso, 150);
        uc.limitarCampos(tfEstado, 150);
        uc.limitarCampos(tfCiudad, 150);
        uc.limitarCampos(tfRango, 20);
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
        }
        
        registrarProducto();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Alert cancelar = new Alert(AlertType.CONFIRMATION);
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
    
    public Respuesta validarCampos(){
        Respuesta r = new Respuesta();
        /*if(tfTitulo.getText().isEmpty() 
                || tfAnio.getText().isEmpty() 
                || tfProposito.getText().isEmpty() 
                || tfCongreso.getText().isEmpty() 
                || tfCiudad.getText().isEmpty() 
                || tfEstado.getText().isEmpty()
                || tfRango.getText().isEmpty() 
                || dpFecha.getValue() == null 
                || cbPais.getSelectionModel().isEmpty()
                ||tfEstadoActual.getText().isEmpty()){
            r.setMensaje("No puede haber campos vacíos");
            return r;
        }*/
        if(tfTitulo.getText().length()>30){
            r.setError(true);
            r.setMensaje("El titulo no puede tener mas de 30 caracteres");
            r.setErrorcode(1);
            return r;
        }
        if(!tfAnio.getText().matches("^(\\d{4})+$")){
            r.setError(true);
            r.setMensaje("Ingrese un año valido");
            r.setErrorcode(2);
            return r;
        }
        if(Objects.equals(tfProposito.getText().trim(), null)){
            r.setError(true);
            r.setMensaje("El proposito no puede estar vacio o ser tan largo");
            r.setErrorcode(3);
            return r;
        }
        if(Objects.equals(tfCongreso.getText(), null)){
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
        if(Objects.equals(dpFecha.getValue(), null)){
            r.setError(true);
            r.setMensaje("La fecha no puede estar vacia.");
            r.setErrorcode(7);
            return r;
        }
        if(cbPais.getSelectionModel().isEmpty()){
            r.setError(true);
            r.setMensaje("Seleccione un pais.");
            r.setErrorcode(8);
            return r;
        }
        r.setMensaje("Exitoso");
        r.setError(false);
        return r;
    }
    
    private void registrarProducto() {
        ///memoria
        ///producto
        ///colaborador
        ///
        //datos de la memoria///
        Memoria memoria = new Memoria();
        memoria.setCiudad(tfCiudad.getText());
        memoria.setEstado(tfEstado.getText());
        memoria.setNombreCongreso(tfCongreso.getText());
        memoria.setRangoPaginas(tfRango.getText());
        LocalDate local = dpFecha.getValue();
        Instant instant = Instant.from(local.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        memoria.setFechaPublicacion(date);
        List<Memoria> memos = new ArrayList<>();
        memos.add(memoria);
        MemoriaJpaController mJpaC = new MemoriaJpaController();
        mJpaC.create(memoria);
        System.out.println(memoria.getIdMemoria());
        ///datos del Producto///
        
        Producto producto = new Producto();
        producto.setAnio(Integer.parseInt(tfAnio.getText()));
        producto.setTitulo(tfTitulo.getText());
        producto.setProposito(tfProposito.getText());
        producto.setIdPais(cbPais.getSelectionModel().getSelectedItem());
        producto.setEstadoActual(tfEstadoActual.getText());
        producto.setMemoriaList(memos);
        ProductoJpaController prJpaC = new ProductoJpaController();
        if (!prJpaC.create(producto)) {
           lblMensaje.setText("Error al conectar con la base de datos...");
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
    
    /**
     * Recupera a todos los paises de la base de datos.
     * @return true si pudo recuperar alguno, false si no.
     */
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
        cbPais.setItems(paises);
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
    
    @FXML
    private void ocultarMensaje() {
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
    }
}
