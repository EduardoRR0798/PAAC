/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paac;

import entity.Colaborador;
import entity.Miembro;
import entity.Producto;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.StageStyle;
import persistence.CuerpoAcademicoJpaController;
import persistence.PaisJpaController;

/**
 * FXML Controller class
 *
 * @author ponch
 */
public class ConsultarProductoController extends ControladorProductos implements Initializable {

    @FXML
    private Label lbl_titulo;
    @FXML
    private TextArea txt_producto;
    @FXML
    private Button btn_descargar;
    @FXML
    private Button btn_regresar;
    private Miembro miembro;
    private Producto producto;
    private File file;
    private int origen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void descargar(ActionEvent event) {

    }

    @FXML
    private void regresar(ActionEvent event) {
        Alert cancela = new Alert(Alert.AlertType.CONFIRMATION);
        cancela.setTitle("Cancelar proceso");
        cancela.setHeaderText(null);
        cancela.initStyle(StageStyle.UTILITY);
        cancela.setContentText("¿Esta seguro de que desea cancelar el proceso?");
        Optional<ButtonType> result = cancela.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (origen == 1) {
                abrirMenu(miembro);
                ((Node) btn_regresar).getScene().getWindow().hide();
            } else {
                generarCurriculum(miembro);
                ((Node) btn_regresar).getScene().getWindow().hide();
            }
        }
    }

    public void recibirParametros(Miembro m, Producto p, int origen) {
        this.miembro = m;
        this.producto = p;
        this.origen = origen;
        lbl_titulo.setText(p.getTitulo());
        txt_producto.setText(sacarInformacionProducto());
        txt_producto.appendText(getMiembrosParticipantes());
        txt_producto.appendText(getColaboradoresParticipantes());
    }

    public String sacarInformacionProducto() {
        PaisJpaController pl = new PaisJpaController();
        CuerpoAcademicoJpaController ca = new CuerpoAcademicoJpaController();
        String todo;
        todo = "ID: " + producto.getIdProducto() + "\n"
                + "Año: " + producto.getAnio() + "\n"
                + "Estado: " + producto.getEstadoActual() + "\n"
                + "País: " + pl.findPais(producto.getIdPais().getIdPais()).getNombre() + "\n"
                + "Propósito: " + producto.getProposito() + "\n"
                //          + "Cuerpo Académico:    " + ca.findCuerpoAcademico(producto.getIdCuerpoAcademico().getIdCuerpoAcademico()).getNombre() + "\n"
                + "-------------------------------------------------------------" + "\n";
        return todo;
    }

    public String getMiembrosParticipantes() {
        String todo;
        todo = "Miembros participantes: " + "\n";
        ArrayList<Miembro> alm = recuperarMiembrosInvolucrados(producto);
        for (int i = 0; i < alm.size(); i++) {
            int n = i + 1;
            todo = todo + n + ">\n";
            todo = todo + alm.get(i).getNombre() + "\n";
        }
        todo = todo + "-------------------------------------------------------------" + "\n";
        return todo;
    }

    public String getColaboradoresParticipantes() {
        String todo;
        todo = "Colaboradores participantes: " + "\n";
        ArrayList<Colaborador> alm = recuperarColaboradoresInvolucrados(producto);
        for (int i = 0; i < alm.size(); i++) {
            int n = i + 1;
            todo = todo + n + ">\n";
            todo = todo + alm.get(i).getNombre() + "\n";
        }
        todo = todo + "-------------------------------------------------------------" + "\n";
        return todo;
    }
}
