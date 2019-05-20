package paac;

import entity.DatosLaborales;
import entity.Gradoacademico;
import entity.Miembro;
import entity.Pais;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import persistence.GradoacademicoJpaController;
import persistence.MiembroJpaController;
import persistence.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author Enrique Ceballos
 */
public class InformacionGeneralController extends ControladorProductos implements Initializable {

    @FXML
    private DatePicker dcFechaInicio;
    @FXML
    private DatePicker dcFechaFinal;
    @FXML
    private DatePicker dcFechaTitulacion;
    @FXML
    private ComboBox<Gradoacademico> cbTema;
    @FXML
    private ComboBox<String> cbNivel;
    @FXML
    private ComboBox<Pais> cbPais;
    @FXML
    private TextField tfAreaDisciplinar;
    @FXML
    private TextField tfInstitucion;
    @FXML
    private TextField tfInstitucionNoConsiderada;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Label lblMensaje;
    @FXML
    private TextField tfTema;
    private ObservableList<Gradoacademico> grados = FXCollections.observableArrayList();
    private ObservableList<String> niveles = FXCollections.observableArrayList("Licenciatura", "Maestria", "Doctorado");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recuperarGrados();
        
        cbPais.setItems(recuperarPaises());
        cbNivel.setItems(niveles);
       
    }    

    /**
     * Cancela el proceso de actualizacion.
     * @param event Clic en el boton Volver.
     */
    @FXML
    private void volver(ActionEvent event) {
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
private void agregarGrado (ActionEvent event) {
        Respuesta r = validarCampos();
        if (!r.isError()) {
            Gradoacademico ga = new Gradoacademico();
            GradoacademicoJpaController gJpaC = new GradoacademicoJpaController();
            ga.setTema(tfTema.getText().trim());
            ga.setAreaDisciplinar(tfAreaDisciplinar.getText().trim());
            ga.setInstitucion(tfInstitucion.getText().trim());
            System.out.println(cbPais.getSelectionModel().getSelectedItem());
            System.out.println(cbPais.getSelectionModel().getSelectedItem().getIdPais());
            ga.setIdPais(cbPais.getSelectionModel().getSelectedItem());
            
            if (tfInstitucionNoConsiderada.getText().isEmpty()) {
                ga.setInstitucionNoConsiderada("NO HAY");
            } else {
                ga.setInstitucionNoConsiderada(tfInstitucionNoConsiderada.getText().trim());
            }
            LocalDate localDate = dcFechaFinal.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date fechaFinal = Date.from(instant);
            ga.setFechaFin(fechaFinal);
            LocalDate localDate1 = dcFechaInicio.getValue();
            Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            Date fechaInicial = Date.from(instant1);
            ga.setFechaInicio(fechaInicial);
            LocalDate localDate2 = dcFechaTitulacion.getValue();
            Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
            Date fechaTitulacion = Date.from(instant2);
            ga.setFechatitulacion(fechaTitulacion);
            ga.setNivel(cbNivel.getSelectionModel().getSelectedItem());
            MiembroJpaController mJpaC = new MiembroJpaController();
            ga.setIdMiembro(mJpaC.findMiembro(1));
            try {
               
                gJpaC.create(ga);
                lblMensaje.setText("Grado academico registrado exitosamente.");
                limpiarCampos();
                grados.removeAll(grados);
                recuperarGrados();
            } catch (Exception ex) {
                lblMensaje.setText("Error actualizando el grado academico.");
                lblMensaje.setVisible(true);
            }
        } else {
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        }
    }
    /**
     * Actualiza un grado academico.
     * @param event Clic en el boton Actualizar.
     */
    @FXML
    private void actualizar(ActionEvent event) {
        Respuesta r = validarCampos();
        if (!r.isError()) {
            Gradoacademico ga = cbTema.getSelectionModel().getSelectedItem();
           //Gradoacademico ga = new Gradoacademico();
            GradoacademicoJpaController gJpaC = new GradoacademicoJpaController();
            ga.setTema(tfTema.getText().trim());
            ga.setAreaDisciplinar(tfAreaDisciplinar.getText().trim());
            ga.setInstitucion(tfInstitucion.getText().trim());
            System.out.println(cbPais.getSelectionModel().getSelectedItem());
            System.out.println(cbPais.getSelectionModel().getSelectedItem().getIdPais());
            ga.setIdPais(cbPais.getSelectionModel().getSelectedItem());
            
            if (tfInstitucionNoConsiderada.getText().isEmpty()) {
                ga.setInstitucionNoConsiderada("NO HAY");
            } else {
                ga.setInstitucionNoConsiderada(tfInstitucionNoConsiderada.getText().trim());
            }
            LocalDate localDate = dcFechaFinal.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date fechaFinal = Date.from(instant);
            ga.setFechaFin(fechaFinal);
            LocalDate localDate1 = dcFechaInicio.getValue();
            Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            Date fechaInicial = Date.from(instant1);
            ga.setFechaInicio(fechaInicial);
            LocalDate localDate2 = dcFechaTitulacion.getValue();
            Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
            Date fechaTitulacion = Date.from(instant2);
            ga.setFechatitulacion(fechaTitulacion);
            ga.setNivel(cbNivel.getSelectionModel().getSelectedItem());
            MiembroJpaController mJpaC = new MiembroJpaController();
            ga.setIdMiembro(mJpaC.findMiembro(1));
            try {
                //gJpaC.create(ga);
                gJpaC.create(ga);
                lblMensaje.setText("Grado academico actualizado exitosamente.");
                limpiarCampos();
                grados.removeAll(grados);
                recuperarGrados();
            } catch (Exception ex) {
                lblMensaje.setText("Error actualizando el grado academico.");
                lblMensaje.setVisible(true);
            }
        } else {
            lblMensaje.setText(r.getMensaje());
            lblMensaje.setVisible(true);
        }
    }
    

    /**
     * Elimina un grado academico de la lista.
     * @param event Clic en el boton Eliminar.
     */
    @FXML
    private void eliminar(ActionEvent event) {
        if (!cbTema.getSelectionModel().isEmpty()) {
            Gradoacademico ga = cbTema.getSelectionModel().getSelectedItem();
            GradoacademicoJpaController gJpaC = new GradoacademicoJpaController();
            try {
                gJpaC.destroy(ga.getIdGradoAcademico());
                grados.remove(ga);
                limpiarCampos();
            } catch (NonexistentEntityException ex) {
                lblMensaje.setText("Error al conectar con la base de datos.");
                lblMensaje.setVisible(true);
            }
        }
    }

    /**
     * Oculta el mensaje.
     * @param event Clic en el mensaje.
     */
    @FXML
    private void ocultarMensaje(MouseEvent event) {
        lblMensaje.setText("");
        lblMensaje.setVisible(false);
    }
    
    /**
     * Llena los campos de la interfaz.
     * @param event Seleccion de un elemento del combobox de temas.
     */
    @FXML
    private void llenarDatos(ActionEvent event) {
        if (!cbTema.getSelectionModel().isEmpty()) {
            Gradoacademico ga = cbTema.getSelectionModel().getSelectedItem();
            cbTema.getSelectionModel().getSelectedIndex();
            tfTema.setText(ga.getTema());
            tfAreaDisciplinar.setText(ga.getAreaDisciplinar());
            tfInstitucion.setText(ga.getInstitucion());
            tfInstitucionNoConsiderada.setText(ga.getInstitucionNoConsiderada());
            cbNivel.getSelectionModel().select(ga.getNivel());
            ZoneId zi = ZoneId.systemDefault();
            
            Date date = ga.getFechaInicio();
            Instant ins = date.toInstant();
            LocalDate ld = ins.atZone(zi).toLocalDate();
            dcFechaInicio.setValue(ld);
            
            ZoneId zi1 = ZoneId.systemDefault();
            Date date1 = ga.getFechatitulacion();
            Instant ins1 = date1.toInstant();
            LocalDate ld1 = ins1.atZone(zi1).toLocalDate();
            dcFechaTitulacion.setValue(ld1);
            
            ZoneId zi2 = ZoneId.systemDefault();
            Date date2 = ga.getFechatitulacion();
            Instant ins2 = date2.toInstant();
            LocalDate ld2 = ins2.atZone(zi2).toLocalDate();
            dcFechaFinal.setValue(ld2);
            cbPais.getSelectionModel().select(ga.getIdPais());
        }
    }
    
    /**
     * Recupera todos los grados academicos de un miembro y los almacena en un
     * combobox.
     */
    public void recuperarGrados() {
        MiembroJpaController mJpaC = new MiembroJpaController();
        Miembro m = mJpaC.findMiembro(1);
        List<Gradoacademico> gs;
        GradoacademicoJpaController gJpaC = new GradoacademicoJpaController();
        try {
            gs = gJpaC.findByGradoAcademico(m);
            
            for (int i = 0; i < gs.size(); i++) {
                System.out.println(gs.get(i).getIdPais());
                grados.add(gs.get(i));
            }
            cbTema.setItems(grados);
        } catch (Exception e) {
            lblMensaje.setText("Error al conectar con la base de datos.");
            lblMensaje.setVisible(true);
        }
    }
  
    
    /**
     * Valida que todos los campos cumplan con los requerimientos requeridos.
     * @return Respuesta.
     */
    public Respuesta validarCampos() {
        Respuesta r = new Respuesta();
        if (tfInstitucion.getText().trim().isEmpty()
                || tfTema.getText().trim().isEmpty()
                || dcFechaInicio.getValue() == null
                || dcFechaFinal.getValue() == null
                || dcFechaTitulacion.getValue() == null
                || tfAreaDisciplinar.getText().trim().isEmpty()
                || cbPais.getSelectionModel().isEmpty()) {
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("No puede haber campos vacios.");
            return r;
        }
        if (!validarFechas(dcFechaInicio.getValue(), dcFechaFinal.getValue())) {
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("La fecha de inicio debe ser menor a la fecha de finalizacion.");
            return r;
        }
        if (!validarFechas(dcFechaInicio.getValue(), dcFechaTitulacion.getValue())) {
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("La fecha de inicio debe ser menor a la fecha de titulacion.");
            return r;
        }
        
        r.setError(false);
        r.setErrorcode(0);
        return r;
    }
    
    /**
     * Vacia todos los campos de la interfaz.
     */
    public void limpiarCampos() {
        tfTema.clear();
        tfInstitucion.clear();
        tfInstitucionNoConsiderada.clear();
        tfAreaDisciplinar.clear();
        dcFechaFinal.setValue(null);
        dcFechaTitulacion.setValue(null);
        dcFechaInicio.setValue(null);
        cbPais.getSelectionModel().clearSelection();
        cbTema.getSelectionModel().clearSelection();
        cbNivel.getSelectionModel().clearSelection();
    }
    
    /**
     * Valida que una fecha haya pasado despues de otra.
     * @param luno fecha que debe pasar primero.
     * @param ldos fecha que debe pasar despues.
     * @return true si la fecha uno paso antes que la fecha dos.
     */
    public boolean validarFechas(LocalDate luno, LocalDate ldos) {
        boolean permiso;
        if (luno.isBefore(ldos)) {
            permiso = true;
        } else {
            permiso = false;
        }
        return permiso;
    }
}
