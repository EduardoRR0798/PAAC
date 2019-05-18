package paac;

import entity.CaMiembro;
import entity.CaMiembroPK;
import entity.CuerpoAcademico;
import entity.Lgac;
import entity.LgacCa;
import entity.Miembro;
import entity.MiembroLgac;
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
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.LgacJpaController;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import persistence.CaMiembroJpaController;
import persistence.CuerpoAcademicoJpaController;
import persistence.LgacCaJpaController;
import persistence.MiembroJpaController;
import persistence.MiembroLgacJpaController;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;
import utilidades.UtilidadCadenas;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class RegistrarMiembroController extends ControladorProductos implements Initializable {

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
    private PasswordField tfContrasenia;
    @FXML
    private TextField tfSNI;
    @FXML
    private Button btnGenerar;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private RadioButton rbPROMEP;
    @FXML
    private TextField tfEmail;
    @FXML
    private ComboBox<Lgac> cbLGAC;
    @FXML
    private ComboBox<String> cbPE;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private ComboBox<CuerpoAcademico> cbCA;
    
    private final ObservableList<String> pes = FXCollections.observableArrayList(
            "Ingenieria de Software", 
            "Redes y Servicios de Computo", 
            "Estadistica");
    private ObservableList<Lgac> lgacs = FXCollections.observableArrayList();
    private final ObservableList<String> tipos = FXCollections.observableArrayList("Miembro del cuerpo academico", "Responsable del cuerpo academico");
    private final ObservableList<String> estados = FXCollections.observableArrayList("Activo", "Inactivo");
    private ObservableList<CuerpoAcademico> cuerpos = FXCollections.observableArrayList();
    private Miembro m;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recuperarCuerpos();
        cbTipo.getItems().setAll(tipos);
        cbPE.getItems().setAll(pes);
        cbEstado.getItems().setAll(estados);
        UtilidadCadenas uc = new UtilidadCadenas();
        uc.excluirEspacios(tfUsuario);
        uc.excluirEspacios(tfSNI);
        uc.excluirEspacios(tfEmail);
        
    }    

    /**
     * Recibe el miembro de la ventana que la invoco.
     * @param miembro Miembro que inicio sesion en el sistema.
     */
    public void recibirParametros(Miembro miembro) {
        this.m = miembro;
    }
    
    /**
     * Comprueba que se pueda registrar el usuario, y lo hace si es posible.
     * @param event Clic en el botom Registrar.
     */
    @FXML
    private void registrar(ActionEvent event) {
        Respuesta r;
        r = validarDatos();
        if (r.isError()) {
            lblMensaje.setText(r.getMensaje());
        } else {
            registrarMiembro();
            lblMensaje.setText("Usuario registrado exitosamente");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControladorActualizarArticulo.class.getName()).log(Level.SEVERE, null, ex);
            }
            limpiarCampos();
            abrirMenu(m);
            ((Node)btnCancelar).getScene().getWindow().hide();
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
            abrirMenu(m);
            ((Node) btnGenerar).getScene().getWindow().hide();
        }
    }
    
    /**
     * LLena el campo de la contrase;a con una contrasenia generada de forma 
     * aleatoria.
     * @param event Clic en el boton Generar.
     */
    @FXML
    private void generar(ActionEvent event) {
        tfContrasenia.setText(generateRandomText());
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
     * Genera una cadena de caracteres aleatoria para poder 
     * @return 
     */
    public static String generateRandomText() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
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
     * Registra al miembro en la base de datos.
     */
    public void registrarMiembro() {
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
            mlJpaC.create(ml);
        } catch (Exception ex) {
            lblMensaje.setText("Error al conectar con la base de datos.");
        }
        m.setContrasenia(tfContrasenia.getText());
        enviarMensaje(m);
    }
    
    /**
     * Comprueba que no exista el nombre ingresado en la base de datos.
     * @return true si puede registrarlo, false sino.
     */
    public boolean comprobarNombre() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarNombre(tfNombre.getText());
    }
    
    /**
     * Comprueba que el usuario no exista en la base de datos.
     * @return true si puede registrarlo, false sino.
     */
    public boolean comprobarUsuario() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarUsuario(tfUsuario.getText());
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
     * Comprueba que el sni no este registrado en la base de datos.
     * @return 
     */
    public boolean comprobarSNI() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        return mJpaC.comprobarISN(tfSNI.getText().trim());
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
    
    public boolean enviarMensaje(Miembro usuario) {
        boolean permiso = true;
        String deCorreo = "juego.preguntantas@gmail.com";
        try {
            final String contrasenia = "pr3gunt0n";
            Properties properties = crearProperties();
            Authenticator auth = new Authenticator() {

                @Override
                public PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication(deCorreo, contrasenia);
                }
            };
            Session sesion = Session.getInstance(properties, auth);
            Message mensaje = crearMensaje(sesion, usuario);
            mostrarRegistroExito(mensaje, usuario);
        } catch (Exception e) {
            
        } finally {
            limpiarCampos();
        }

        return permiso;
    }
    
        /**
     * Este metodo es para hacer todos los put que necesitan las properties
     * @return El el properties para la sesion
     */
    private Properties crearProperties() {
        
        Properties properties = new Properties();
        String host = "smtp.gmail.com";
        String puerto = "587";
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", puerto);
        properties.put("mail.smtp.debug", "true");
        return properties;
    }
    
    /**
     * Este metodo es para crear el mensaje que se va a enviar por correo
     * @param sesion Session para enviar mensaje
     * @param nuevousuario Cuenta de invitado con los datos para el mensaje
     * @return El mensaje que se enviara por correo.
     */    
    private Message crearMensaje(Session sesion, Miembro nuevousuario) {
        
        Message mensaje = new MimeMessage(sesion);
        try {
            
            InternetAddress[] address = { 
                new InternetAddress(nuevousuario.getEmail())
            };
            mensaje.setRecipients(Message.RecipientType.TO, address);
            mensaje.setSubject("Registro al sistema PAAC");
            String saludo = "Estimado(a), " + nuevousuario.getNombre();
            String cuerpo = " Por medio del presente correo le informamos que "
                    + "ha sido registrado(a) en el sistema de control de "
                    + "actividades y productos academicos. Su contraseña de registro es: " 
                    + nuevousuario.getContrasenia() + " y su usuario es: " 
                    + nuevousuario.getUsuario();
            
            String contenidoCorreo = saludo + cuerpo;
            mensaje.setSentDate(new Date());
            mensaje.setText(contenidoCorreo);
        } catch (AddressException ex) {
            Logger.getLogger(RegistrarMiembroController.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(RegistrarMiembroController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return mensaje;
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
    
    /**
     * Este metodo es para mostrar una ventana en caso de exito
     * @param mensaje Message que se va a enviar por correo
     * @param nuevoUsuario que se guardara en BD
     */ 
    private void mostrarRegistroExito(Message mensaje,
            Miembro nuevoUsuario) {
        try {

            Transport.send(mensaje);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion de envio.");
            alert.setHeaderText(null);
            alert.setContentText("Miembro registrado exitosamente.");
            alert.showAndWait();
        } catch (MessagingException ex) {
            lblMensaje.setText("Error al enviar el correo electronico.");
            MiembroJpaController mJpaC = new MiembroJpaController();
            try {
                mJpaC.destroy(nuevoUsuario.getIdMiembro());
            } catch (IllegalOrphanException ex1) {
                Logger.getLogger(RegistrarMiembroController.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (NonexistentEntityException ex1) {
                Logger.getLogger(RegistrarMiembroController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
