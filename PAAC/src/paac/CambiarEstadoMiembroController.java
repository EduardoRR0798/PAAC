package paac;

import entity.Miembro;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import persistence.MiembroJpaController;
import persistence.exceptions.NonexistentEntityException;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class CambiarEstadoMiembroController extends ControladorProductos implements Initializable {

    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblMensaje;
    private Miembro responsable;
    private Miembro edit;
    private final ObservableList<String> estadosA = FXCollections.observableArrayList("Activo", "Inactivo");
    private final ObservableList<String> tipos = FXCollections.observableArrayList("Miembro del cuerpo academico", "Responsable del cuerpo academico");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbEstado.setItems(estadosA);
        cbTipo.setItems(tipos);
    }    

    public void recibirParametros(Miembro res, Miembro edit) {
        this.responsable = res;
        this.edit = edit;
        llenarCampos();
    }
    
    @FXML
    private void aceptar(ActionEvent event) {
        editarMiembro();
        abrirMenu(responsable);
        ((Node) btnCancelar).getScene().getWindow().hide();
    }

    private void editarMiembro() {
        edit.setTipo(cbTipo.getSelectionModel().getSelectedIndex() + 1);
        edit.setEstado(cbEstado.getSelectionModel().getSelectedItem());
        MiembroJpaController mJpaC = new MiembroJpaController();
        try {
            mJpaC.edit(edit);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CambiarEstadoMiembroController.class.getName())
                    .log(Level.SEVERE, null, ex);
            lblMensaje.setText(ERRORBD);
        } catch (Exception ex) {
            Logger.getLogger(CambiarEstadoMiembroController.class.getName())
                    .log(Level.SEVERE, null, ex);
            lblMensaje.setText(ERRORBD);
        }
        if (cbTipo.getSelectionModel().getSelectedIndex() == 1) {
            responsable.setTipo(1);
            try {
                mJpaC.edit(responsable);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(CambiarEstadoMiembroController.class.getName()).log(Level.SEVERE, null, ex);
                lblMensaje.setText(ERRORBD);
            } catch (Exception ex) {
                Logger.getLogger(CambiarEstadoMiembroController.class.getName()).log(Level.SEVERE, null, ex);
                lblMensaje.setText(ERRORBD);
            }
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        Alert cancelar = new Alert(Alert.AlertType.CONFIRMATION);
        cancelar.setTitle("Cancelar proceso");
        cancelar.setHeaderText(null);
        cancelar.initStyle(StageStyle.UTILITY);
        cancelar.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancelar.showAndWait();
        if(result.get() == ButtonType.OK) {
            seleccionarMiembros(responsable);
            ((Node) btnCancelar).getScene().getWindow().hide();
        }
    }
    
    public void llenarCampos() {
        cbTipo.getSelectionModel().select(edit.getTipo() - 1);
        cbEstado.getSelectionModel().select(edit.getEstado());
        lblNombre.setText(edit.getNombre());
    }
}
