/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;


import entity.DatosLaborales;
import entity.Miembro;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Observable;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import persistence.DatosLaboralesJpaController;
import persistence.MiembroJpaController;

/**
 * FXML Controller class
 *
 * @author Enrique Ceballos Mtz
 */
public class DatosLaboralesController implements Initializable {

    @FXML
    private ComboBox<DatosLaborales> cbNombramiento;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private DatePicker dateInicioContrato;
    @FXML
    private DatePicker dateFinalContrato;
    @FXML
    private TextField txtCronologia;
    @FXML
    private TextField txtUnidadAcademica;
    @FXML
    private TextField txtIES;
    @FXML
    private TextField txtDedicacion;
    @FXML
    private TextField txtDependencia;
    @FXML
    private Button bVolver;
    @FXML
    private Button btAgregar;
    @FXML
    private Button btActualizar;
    @FXML
    private Button btEliminar;
    @FXML
    private Label lblNotificacion;
    @FXML 
    private TextField txtNombramiento;
    @FXML 
    private ObservableList<DatosLaborales> datosLaborales = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> tipoNombramiento = FXCollections.observableArrayList("Tiempo completo","Temporal");
 
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recuperarDatos();
      cbTipo.getItems().setAll(tipoNombramiento);
      
     
       
    }    
    
    
    @FXML 
    private void agregarDatosLabores (ActionEvent e){
        Respuesta r = ValidarCampos();
        if(r.isError()){
            lblNotificacion.setText(r.getMensaje());
        } else {
         DatosLaborales da = new DatosLaborales();
         DatosLaboralesJpaController dJpaC = new DatosLaboralesJpaController();
        da.setNombramiento(txtNombramiento.getText().trim());
        da.setCronologia(txtCronologia.getText().trim());
        da.setDedicacion(txtDedicacion.getText().trim());
       da.setDependencia(txtDependencia.getText().trim());
       da.setIes(txtIES.getText().trim());
       da.setUnidadAcademica(txtUnidadAcademica.getText().trim());
       
       
         LocalDate localDate = dateInicioContrato.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date fechaInicio = Date.from(instant);
        da.setInicioContrato(fechaInicio);
   
    
       LocalDate localDate1 = dateFinalContrato.getValue();
        Instant instant1= Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
        Date fechaFinal = Date.from(instant1);
        da.setFinContrato(fechaFinal);
        
        da.setTipo((String)(cbTipo.getSelectionModel().getSelectedItem()));
   
   try {
     dJpaC.create(da);
     lblNotificacion.setText("Datos laborales agregados");
     limpiarCampos();
     datosLaborales.removeAll(datosLaborales);
     recuperarDatos();
     
   
         
     } catch (Exception ex){
         
         lblNotificacion.setText("Error al agregar grado academico");
         lblNotificacion.setVisible(true);
     }
         
         
             
        }}
        
   
         
            
    public Respuesta ValidarCampos(){
        Respuesta r = new Respuesta();
        if(txtNombramiento.getText().trim().isEmpty()
               || txtCronologia.getText().trim().isEmpty()
               ||txtDedicacion.getText().trim().isEmpty()
               ||txtDependencia.getText().trim().isEmpty()
               ||txtIES.getText().trim().isEmpty()
               ||txtUnidadAcademica.getText().trim().isEmpty()
            
              ||dateInicioContrato.getValue() == null
                ||cbTipo.getSelectionModel().isEmpty()){
            r.setError(true);
               r.setErrorcode(1);
            r.setMensaje("No puede haber campos vacios");
            return r;
      
        }
        if (!txtNombramiento.getText().trim().matches("^.*\\\\d.*$")){
            r.setError(true);
            r.setMensaje("Nombramiento solo puede contener letras");
            r.setErrorcode(4);
            return r;
        }if (!txtCronologia.getText().trim().matches("^.*\\\\d.*$")){
            r.setError(true);
            r.setMensaje("Cronologia solo puede contener letras");
            r.setErrorcode(4);
            return r;
        } if (!txtDependencia.getText().trim().matches("^.*\\\\d.*$")){
            r.setError(true);
            r.setMensaje("Dependecia solo puede contener letras");
            r.setErrorcode(4);
            return r;
        } if (!txtDedicacion.getText().trim().matches("^.*\\\\d.*$")){
            r.setError(true);
            r.setMensaje("Dedicacion solo puede contener letras");
            r.setErrorcode(4);
            return r;
        } if(!txtUnidadAcademica.getText().trim().matches("^.*\\\\d.*$")){
            r.setError(true);
            r.setMensaje("Unidad academica solo puede contener letras");
            r.setErrorcode(4);
            return r;
        }
        
        if(!validarFechas(dateInicioContrato.getValue(), dateFinalContrato.getValue())){
            r.setError(true);
            r.setErrorcode(1);
            r.setMensaje("La fecha de inicio de contrato dede de ser menor a la fecha de finalizacion de contrato");
        return  r;
            } 
   r.setError(false);
   r.setErrorcode(0);
   return r;
   
    }
    public void limpiarCampos(){
    txtCronologia.clear();
    txtDedicacion.clear();
    txtDependencia.clear();
    txtIES.clear();
    txtNombramiento.clear();
    txtUnidadAcademica.clear();
    cbTipo.getSelectionModel().clearSelection();
    cbTipo.getSelectionModel().clearSelection();
    dateInicioContrato.setValue(null);
    dateFinalContrato.setValue(null);
}
      public void recuperarDatos(){
        MiembroJpaController mJpaC = new MiembroJpaController();
        Miembro m = mJpaC.findMiembro(1);
                List<DatosLaborales> Dal;
                DatosLaboralesJpaController dJpaC = new DatosLaboralesJpaController();
                try {
                   
                    Dal = dJpaC.findByDatosLaborales(m);
                 
                    for (int i=0; i<Dal.size(); i++){
                        System.out.println(Dal.get(i));
                        datosLaborales.add(Dal.get(i));
                    }
                    cbNombramiento.setItems(datosLaborales);
                    
                    }catch (Exception e){
                            lblNotificacion.setText("Error con la conexion");
                            lblNotificacion.setVisible(true);
                            e.printStackTrace();
                            }
                }
                   
    
  
    

    
public boolean validarFechas(LocalDate luno, LocalDate ldos) {
        boolean permiso;
        if (luno.isBefore(ldos)) {
            permiso = true;
        } else {
            permiso = false;
        }
        return permiso;
    }

@FXML
      private void LlenarDatos(ActionEvent event){
          if(!cbNombramiento.getSelectionModel().isEmpty()){
              DatosLaborales dl = new DatosLaborales();
            dl = cbNombramiento.getSelectionModel().getSelectedItem();
              txtCronologia.setText(dl.getCronologia());
              txtDedicacion.setText(dl.getDedicacion());
              txtDependencia.setText(dl.getDependencia());
              txtIES.setText(dl.getIes());
              txtNombramiento.setText(dl.getNombramiento());
              txtUnidadAcademica.setText(dl.getUnidadAcademica());
              System.out.println(dl.getInicioContrato());
              ZoneId zi = ZoneId.systemDefault();
              Date date = dl.getInicioContrato();
              Instant ins = date.toInstant();
              LocalDate ld= ins.atZone(zi).toLocalDate();
              dateInicioContrato.setValue(ld);
                     
              ZoneId zi1 = ZoneId.systemDefault();
              Date date1 = dl.getFinContrato();
              Instant ins1 =date.toInstant();
              LocalDate ld1  = ins1.atZone(zi1).toLocalDate();
              dateFinalContrato.setValue(ld1);
              cbTipo.getSelectionModel().getSelectedItem();
              
       
           
           
       }
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
       
    }
