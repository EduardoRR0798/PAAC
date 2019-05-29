package paac;

import entity.CaMiembro;
import entity.CuerpoAcademico;
import entity.Miembro;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import persistence.CaMiembroJpaController;
import persistence.CuerpoAcademicoJpaController;
import persistence.MiembroJpaController;

/**
 * FXML Controller class
 *
 * @author Eduardo Rosas Rivera
 */
public class SeleccionarMiembroController extends ControladorProductos implements Initializable {

    @FXML
    private ListView<Miembro> lstMiembros;
    @FXML
    private Button btnCancelar;
    private Miembro miembro;
    private ObservableList<Miembro> miembros = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstMiembros.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (!miembros.isEmpty()) {
                        Miembro m = lstMiembros.getSelectionModel().getSelectedItem();
                        cambiarEstadoMiembro(miembro, m);
                        ((Node) btnCancelar).getScene().getWindow().hide();
                    }
                }
            }
        });
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        abrirMenu(miembro);
        ((Node) btnCancelar).getScene().getWindow().hide();
    }
    
    public void recibirParametros(Miembro m) {
        miembro = m;
        recuperarMiembro();
    }
    
    /**
     * Llena la lista de miembros que pertenecen al CA.
     */
    private void recuperarMiembro() {
        CaMiembroJpaController camJpaC = new CaMiembroJpaController();
        CuerpoAcademicoJpaController caJpaC = new CuerpoAcademicoJpaController();
        CaMiembro cam = camJpaC.findByMiembro(miembro.getIdMiembro());
        CuerpoAcademico ca = caJpaC.findCuerpoAcademico(cam.getCaMiembroPK().getIdCuerpoAcademico());
        List<CaMiembro> cams = camJpaC.findByIdCA(ca.getIdCuerpoAcademico());
        MiembroJpaController mJpaC = new MiembroJpaController();
        Miembro m;
        for (int i = 0; i < cams.size(); i++) {
            m = mJpaC.findMiembro(cams.get(i).getCaMiembroPK().getIdMiembro());
            if (!Objects.equals(m.getIdMiembro(), miembro.getIdMiembro())) {
                miembros.add(m);
            }
        }
        lstMiembros.setItems(miembros);
    }
}
