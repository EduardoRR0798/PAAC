/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.CaMiembro;
import entity.CaMiembroPK;
import entity.CuerpoAcademico;
import entity.Lgac;
import entity.LgacCa;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.LgacJpaController;
import persistence.MiembroJpaController;
import entity.Miembro;
import entity.MiembroLgac;
import java.util.Objects;
import persistence.CaMiembroJpaController;
import persistence.CuerpoAcademicoJpaController;
import persistence.LgacCaJpaController;
import persistence.MiembroLgacJpaController;
import utilidades.UtilidadCadenas;


/**
 * FXML Controller class
 *
 * @author Enrique Ceballos Mtz
 */
public class DatosPersonalesController implements Initializable {

    @FXML
    private Label lblMensaje;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfSNI;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private ComboBox<Lgac> cbLGAC;
    @FXML
    private ComboBox<String> cbPE;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private TextField tfEmail;
    @FXML
    private RadioButton rbPROMEP;
    @FXML
    private ComboBox<CuerpoAcademico> cbCA;
    private final ObservableList<String> pes = FXCollections.observableArrayList("Ingenieria de Software", "Redes y Servicios de Computo", "Estadistica");
    private ObservableList<Lgac> lgacs = FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList("Miembro del cuerpo academico", "Responsable del cuerpo academico");
    private final ObservableList<String> estados = FXCollections.observableArrayList("Activo", "Inactivo");
     private ObservableList<CuerpoAcademico> cuerpos = FXCollections.observableArrayList();
    @FXML
    private TextField tfContrasenia;

    
    
       /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recuperarLGAC();
        recuperarCuerpos();
        cbTipo.getItems().setAll(tipos);
        cbPE.getItems().setAll(pes);
        cbEstado.getItems().setAll(estados);
        llenarDatos();
        
    }  
    
    /**
     * Comprueba que se pueda actualizar el usuario, y lo hace si es posible.
     * @param event Clic en el botom Registrar.
     */
    private void aceptar(ActionEvent event) {
        Respuesta r = new Respuesta();
        r = validarDatos();
        if (r.isError()) {
            lblMensaje.setText(r.getMensaje());
        } else {
            actualizarMiembro();
            lblMensaje.setText("Usuario registrado exitosamente");
            limpiarCampos();
        }
        
        
    }
    /**
     * Cancela la operacion.
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
            //do something
            
        }
    }
    /**
     * recupera todas las LGAC de la base de datos y llena el comboBox con el
     * nombre de cada una de ellas.
     */
    private void recuperarLGAC() {
        LgacJpaController lJpaC = new LgacJpaController();
        List<Lgac> lcs = lJpaC.findLgacEntities();
        for (int i = 0; i < lcs.size(); i++) {
            lgacs.add(lcs.get(i));
        }
        cbLGAC.getItems().setAll(lgacs);
    }
    
   
    
    
      /**
     * Valida que todos los campos esten llenos.
     * @return Respuesta con el error o sin el.
     */
    public Respuesta validarDatos() {
        Respuesta r = new Respuesta();
        if (tfNombre.getText().trim().isEmpty()
                || tfUsuario.getText().trim().isEmpty()
                || tfEmail.getText().trim().isEmpty()
                || tfContrasenia.getText().trim().isEmpty()
                || cbTipo.getSelectionModel().isEmpty()
                || cbEstado.getSelectionModel().isEmpty()
                || cbLGAC.getSelectionModel().isEmpty()
                || cbPE.getSelectionModel().isEmpty()
                || cbCA.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("No puede haber campos vacios.");
            return r;
        }
        if (!comprobarNombre()) {
            r.setError(true);
            r.setErrorcode(2);
            r.setMensaje("Este nombre ya se encuentra registrado.");
            return r;
        }
        if (!comprobarUsuario()) {
            r.setError(true);
            r.setErrorcode(3);
            r.setMensaje("Este usuario ya se encuentra registrado.");
            return r;
        }
        if (!tfSNI.getText().isEmpty()) {
            if (!comprobarSNI()) {
                r.setError(true);
                r.setErrorcode(3);
                r.setMensaje("Este sni ya se encuentra registrado.");
                return r;
            }
        }
        if (!comprobarEmail()) {
            r.setError(true);
            r.setErrorcode(3);
            r.setMensaje("Este correo electronico ya se encuentra registrado.");
            return r;
        }
        r.setError(false);
        r.setErrorcode(0);
        r.setMensaje("Usuario actualizado exitosamente.");
        return r;
    }

    
    
    
    
     /**
     * Actualiza al miembro en la base de datos.
     */
    @FXML
    public void actualizarMiembro() {
        Miembro m = new Miembro();
        UtilidadCadenas uc = new UtilidadCadenas();
        m.setContrasenia(uc.hacerHashAContrasenia(tfContrasenia.getText()));
        m.setNombre(tfNombre.getText().trim());
        m.setUsuario(tfUsuario.getText().trim());
        m.setTipo(cbTipo.getSelectionModel().getSelectedIndex() + 1);
        m.setEstado(cbEstado.getSelectionModel().getSelectedItem());
        m.setEmail(tfEmail.getText().trim());
        if (!tfSNI.getText().isEmpty()) {
            m.setSni(tfSNI.getText().trim());
        }
        if (rbPROMEP.isSelected()) {
            m.setPromep("Si");
        } else {
            m.setPromep("No");
        }
        m.setPeImpacta(cbPE.getSelectionModel().getSelectedItem());
        MiembroJpaController mJpaC = new MiembroJpaController();
        mJpaC.create(m);
        CaMiembroPK cm = new CaMiembroPK();
        cm.setIdMiembro(m.getIdMiembro());
        cm.setIdCuerpoAcademico(cbCA.getSelectionModel().getSelectedItem().getIdCuerpoAcademico());
        CaMiembro cam = new CaMiembro();
        cam.setCaMiembroPK(cm);
        CaMiembroJpaController cmJpaC = new CaMiembroJpaController();
        try {
            cmJpaC.create(cam);
        } catch (Exception ex) {
            lblMensaje.setText("Error al conectar con la base de datos.");
        }
        MiembroLgac ml = new MiembroLgac();
        ml.setLgac(cbLGAC.getSelectionModel().getSelectedItem());
        ml.setMiembro(m);
        MiembroLgacJpaController mlJpaC = new MiembroLgacJpaController();
        try {
            mlJpaC.edit(ml);
        } catch (Exception ex) {
            lblMensaje.setText("Error al conectar con la base de datos.");
        }
        m.setContrasenia(tfContrasenia.getText());
     
    }
    
    
    
    
      
    /**
     * Comprueba que no exista el nombre ingresado en la base de datos.
     * @return 
     */
    public boolean comprobarNombre() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarNombre(tfNombre.getText());
    }
      /**
     * Comprueba que el email no este registrado para otro miembro.
     * @return true si puede registrarlo, false sino.
     */
    public boolean comprobarEmail() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarEmail(tfEmail.getText());
    }
    
    
    /**
     * Comprueba que el usuario no exista en la base de datos.
     * @return 
     */
    public boolean comprobarUsuario() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarUsuario(tfUsuario.getText());
    }
    
    /**
     * Comprueba que el sni no este registrado en la base de datos.
     * @return 
     */
    public boolean comprobarSNI() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarISN(tfSNI.getText().trim());
    }
    
       /**
     * Recupera todas las LGACs en base a un cuerpo academico.
     */
    @FXML
    private void definirLGACs() {
        if (!cbCA.getSelectionModel().isEmpty()) {
            if (!lgacs.isEmpty()) {
                lgacs.removeAll(lgacs);
            }
            CuerpoAcademico ca = cbCA.getSelectionModel().getSelectedItem();
            LgacCaJpaController lcJpaC = new LgacCaJpaController();
            List<LgacCa> lgacs2 = lcJpaC.findByCA(ca);
            LgacJpaController lgacJpaC = new LgacJpaController();
            Lgac l;
            if (!lgacs2.isEmpty()) {
                for (int i = 0; i < lgacs2.size(); i++) {
                    l = lgacJpaC.findLgac(lgacs2.get(i).getLgacCaPK().getIdLGAC());
                    lgacs.add(l);
                }
                cbLGAC.getItems().setAll(lgacs);
                cbLGAC.setDisable(false);
            }
        }
        
    }
        /**
     * Recupera todos los cuerpos academicos de la base de datos y llena el un
     * combobox con ellos.
     */
    private void recuperarCuerpos() {
        CuerpoAcademicoJpaController caJpaC = new CuerpoAcademicoJpaController();
        List<CuerpoAcademico> cs = caJpaC.findCuerpoAcademicoEntities();
        for (int i = 0; i < cs.size(); i++) {
            cuerpos.add(cs.get(i));
        }
        cbCA.setItems(cuerpos);
    }
    
    
    private void llenarDatos(){
        Miembro mi = new Miembro();
        MiembroJpaController mJpaC = new MiembroJpaController();
        CaMiembroPK cm = new CaMiembroPK();
        CaMiembroJpaController cmJpaC = new CaMiembroJpaController();
        List<Miembro> miembro = mJpaC.findMiembroEntities();
         MiembroLgac ml = new MiembroLgac();
         MiembroLgacJpaController mlJpaC = new MiembroLgacJpaController();
        System.out.println(miembro.size());
        for (int i = 0; i<miembro.size(); i++){
            if(Objects.equals(mi.getIdMiembro(), miembro.get(i).getIdMiembro())){
                  mi = miembro.get(i);
            }
              
        }
        
        tfNombre.setText(mi.getNombre());
        tfUsuario.setText(mi.getUsuario());
        tfEmail.setText(mi.getEmail());
        tfContrasenia.setText(mi.getContrasenia());
        tfSNI.setText(mi.getSni());
//        cbTipo.getSelectionModel().select(mi.getTipo());
        cbEstado.getSelectionModel().select(mi.getEstado());
        cbPE.getSelectionModel().select(mi.getPeImpacta());
        cbCA.getSelectionModel().select(cm.getIdCuerpoAcademico());
        cbLGAC.getSelectionModel().select(ml.getLgac());
        
          }
    

      

    
    
    
    
  /**
     * Vacios todos los campos de la interfaz.
     */
    public void limpiarCampos() {
        tfNombre.setText("");
        tfSNI.setText("");
        tfContrasenia.setText("");
        tfUsuario.setText("");
        cbTipo.getSelectionModel().clearSelection();
        cbEstado.getSelectionModel().clearSelection();
        cbLGAC.getSelectionModel().clearSelection();
        cbCA.getSelectionModel().clearSelection();
        cbLGAC.setDisable(true);
        tfEmail.clear();
        cbPE.getSelectionModel().clearSelection();
    }
    
    

}        

