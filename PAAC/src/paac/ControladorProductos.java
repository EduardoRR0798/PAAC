package paac;

import entity.Colaborador;
import entity.Miembro;
import entity.Pais;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import persistence.ColaboradorJpaController;
import persistence.MiembroJpaController;
import persistence.PaisJpaController;
import persistence.ProductoColaboradorJpaController;
import persistence.ProductoJpaController;
import persistence.ProductoMiembroJpaController;

/**
 *
 * @author Eduardo Rosas Rivera
 */
public abstract class ControladorProductos {
    protected final ObservableList<String> propositos = 
            FXCollections.observableArrayList(
                    "Asimilación de tecnologia", 
                    "Creacion", 
                    "Desarrollo tecnológico", 
                    "Difusión", 
                    "Generacion de conocimiento", 
                    "Investigación aplicada", 
                    "Transferencia de tecnología");
    protected final ObservableList<String> estados = 
            FXCollections.observableArrayList(
            "En proceso",
            "Terminado");
    protected Producto producto;
    protected static final String ERRORBD = "Error al conectar con la base de datos";
    
    /**
     * VErifica que el anio introducido sea menor o igual al a;o acutual.
     * @param txtAnio
     * @return true si es valido, false si no es valido.
     */
    protected boolean verificarAnio(String txtAnio) {
        try {
            int anio = Integer.parseInt(txtAnio);
            Calendar cal= Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            if (anio <= year && anio > 1900) {
                return true;
            }
        } catch (Exception e) { 
            return false;
        }        
        return false;
    }
    
    /**
     * Recupera a todos los paises de la base de datos.
     * @return lista con todos los paises.
     */
    protected ObservableList<Pais> recuperarPaises() {
        ObservableList<Pais> pas = FXCollections.observableArrayList();
        PaisJpaController pJpaC = new PaisJpaController();
        List<Pais> ps;
        ps = pJpaC.findAll();
        for (int i = 0; i < ps.size(); i++) {
            pas.add(ps.get(i));
        }
        return pas;
    }
    
    /**
     * Recupera a todos los miembros de la base de datos.
     * @return true si pudo recuperar algo, false si no.
     */
    protected ObservableList<Miembro> recuperarMiembros(Miembro m) {
        ObservableList<Miembro> mis = FXCollections.observableArrayList();
        MiembroJpaController mJpaC = new MiembroJpaController();
        List<Miembro> ms;
        ms = mJpaC.findAll();
        for (int i = 0; i < ms.size(); i++) {
            if (!Objects.equals(ms.get(i).getIdMiembro(), m.getIdMiembro())) {
                mis.add(ms.get(i));
            }
        }
        return mis;
    }
    
    /**
     * Recupera a todos los colaboradores de la base de datos;
     * @return lista de todos los colaboradores.
     */
    protected ObservableList<Colaborador> recuperarColaboradores() {
        ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
        ColaboradorJpaController cJpaC = new ColaboradorJpaController();
        List<Colaborador> ls;
        ls = cJpaC.findAll();
        for (int i = 0; i <ls.size(); i++) {
            colaboradores.add(ls.get(i));
        }
        return colaboradores;
    }
    
    /**
     * Valida que el titulo no pertenezca a ningun otro producto.
     * @param titulo titulo del producto.
     * @return true si se puede usar, false si no.
     */
    protected boolean validarTitulo(String titulo) {
        ProductoJpaController pJpaC = new ProductoJpaController();
        return pJpaC.verificarNombre(titulo);
    }
    
    /**
     * Recupera todos los miembros que esten involucrados en la memoria.
     * @param pro 
     * @return  
     */
    protected ArrayList<Miembro> recuperarMiembrosInvolucrados(Producto pro) {
        ProductoMiembroJpaController pmJpaC = new ProductoMiembroJpaController();
        List<ProductoMiembro> ms = pmJpaC.findByIdProducto(pro);
        Miembro m;
        MiembroJpaController mJpaC = new MiembroJpaController();
        ArrayList<Miembro> miem = new ArrayList<>();
        for (int i = 0; i < ms.size(); i++) {
            m = mJpaC.findMiembro(ms.get(i).getIdMiembro().getIdMiembro());
            miem.add(m);
        }
        return miem;
    }
    
    /**
     * Sirve para recuperar a todos los colaboradores que hayan participado en el producto.
     * @param p Producto a buscar.
     * @return lista de los colaboradores.
     */
    protected ArrayList<Colaborador> recuperarColaboradoresInvolucrados(Producto p) {
        ProductoColaboradorJpaController pcJpaC = new ProductoColaboradorJpaController();
        List<ProductoColaborador> cs = pcJpaC.findByIdProducto(p.getIdProducto());
        Colaborador c;
        ColaboradorJpaController cJpaC = new ColaboradorJpaController();
        ArrayList<Colaborador> colab = new ArrayList<>();
        for (int i = 0; i < cs.size(); i++) {
            c = cJpaC.findColaborador(cs.get(i).getColaborador().getIdColaborador());
            colab.add(c);
        }
        return colab;
    }
    
    /**
     * Verifica si es posible cambiar el titulo de un producto.
     * @param titulo titulo del producto.
     * @param p id del producto.
     * @return true si existe un 
     */
    protected boolean validarTituloActualizar(String titulo, Integer p) {
        ProductoJpaController pJpaC = new ProductoJpaController();
        return pJpaC.verificarTitulo(titulo, p);
    }
    
    /**
     * Valida que las paginas iniciales sean menores a las finales;
     * @param inicio (int) numero de pagina de inicio.
     * @param fin (int) numero de pagina de fin.
     * @return true si cumple, false si no.
     */
    protected boolean validarPaginas(int inicio, int fin) {
        boolean permiso = false;
        try {
            if (inicio <= fin) {
                permiso = true;
            }
        } catch (Exception e) {
            permiso = false;
        }
        return permiso;
    }
    
    /**
     * Abre el menu principal.
     */
    protected void abrirMenu(Miembro m) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MenuPrincipal.fxml"));
            
            Parent responder = loader.load();
            MenuPrincipalController controller = loader.getController();
            controller.recibirParametros(m);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Menu Principal");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControladorProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un prototipo.
     * por el miembro.
     */
    protected void registrarPrototipo(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RegistrarPrototipo.fxml"));
            
            Parent responder = loader.load();
            RegistrarPrototipoController crm = loader.getController();
            crm.setMiembro(miembro);

            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Registrar prototipo");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControladorProductos.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void registrarMemoria(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RegistrarMemoria.fxml"));
            Parent responder = loader.load();
            ControladorRegistrarMemoria crm = loader.getController();
            crm.setMiembro(miembro);            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Registrar memoria");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ControladorProductos.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void registrarArticulo(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RegistrarArticulo.fxml"));
            
            Parent responder = loader.load();
            ControladorRegistrarArticulo cra = loader.getController();
            cra.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de productos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void registrarSeleccionarMemoria(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionarMemoria.fxml"));
            
            Parent responder = loader.load();
            SeleccionarMemoriaController smc = loader.getController();
            smc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de memorias");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void registrarSeleccionarPrototipo(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionarPrototipo.fxml"));
            
            Parent responder = loader.load();
            SeleccionarPrototipoController spc = loader.getController();
            spc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de prototipos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void registrarSeleccionarArticulo(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionarArticulo.fxml"));
            
            Parent responder = loader.load();
            SeleccionarArticuloController sac = loader.getController();
            sac.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de articulos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void actualizarArticulo(Miembro miembro, Producto producto) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ActualizarArticulo.fxml"));
            
            Parent responder = loader.load();
            ControladorActualizarArticulo sac = loader.getController();
            sac.recibirParametros(producto, miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Actualizar Articulo");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void actualizarMemoria(Miembro miembro, Producto producto) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ActualizarMemoria.fxml"));
            
            Parent responder = loader.load();
            ControladorActualizarMemoria cam = loader.getController();
            cam.recibirParametros(producto,miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Actualizar memoria");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void actualizarPrototipo(Miembro miembro, Producto producto) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ActualizarPrototipo.fxml"));
            
            Parent responder = loader.load();
            ActualizarPrototipoController apc = loader.getController();
            apc.recibirParametros(producto, miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Actualizar prototipo");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void seleccionarProductos(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionProductos.fxml"));
            
            Parent responder = loader.load();
            SeleccionProductosController spc = loader.getController();
            spc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de productos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un miembro
     * por el miembro.
     */
    protected void registrarNuevoMiembro(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Registrar miembro.fxml"));
            
            Parent responder = loader.load();
            RegistrarMiembroController rmc = loader.getController();
            rmc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Registrar nuevo miembro");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void seleccionarMemoria(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionarMemoria.fxml"));
            
            Parent responder = loader.load();
            SeleccionarMemoriaController smc = loader.getController();
            smc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de memorias");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void seleccionarPrototipo(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionarPrototipo.fxml"));
            
            Parent responder = loader.load();
            SeleccionarPrototipoController spc = loader.getController();
            spc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de prototipos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void seleccionarArticulo(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionarArticulo.fxml"));
            
            Parent responder = loader.load();
            SeleccionarArticuloController sac = loader.getController();
            sac.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de articulo");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre una nueva ventana para registrar un producto
     * por el miembro.
     */
    protected void seleccionarProductosActualizar(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionActualizarProductos.fxml"));
            
            Parent responder = loader.load();
            SeleccionActualizarProductosController sapc = loader.getController();
            sapc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de productos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre la ventana para seleccionar los miembros a modificar.
     * @param miembro Miembro que inicio sesion en el sistema.
     */
    protected void seleccionarMiembros(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("SeleccionarMiembro.fxml"));
            
            Parent responder = loader.load();
            SeleccionarMiembroController smc = loader.getController();
            smc.recibirParametros(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de miembro");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metodo abre la ventana para seleccionar los miembros a modificar.
     * @param miembro Miembro que inicio sesion en el sistema.
     */
    protected void cambiarEstadoMiembro(Miembro responsable, Miembro edit) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CambiarEstadoMiembro.fxml"));
            
            Parent responder = loader.load();
            CambiarEstadoMiembroController smc = loader.getController();
            smc.recibirParametros(responsable, edit);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Cambiar el estado del miembro");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    void registrarTesis(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RegistrarTesis.fxml"));
            
            Parent responder = loader.load();
            RegistrarTesisControlador cra = loader.getController();
            cra.setMiembro(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de productos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    void registrarLibro(Miembro miembro) {
        try {
            Locale.setDefault(new Locale("es"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RegistrarLibro.fxml"));
            
            Parent responder = loader.load();
            RegistrarLibroControlador cra = loader.getController();
            cra.setMiembro(miembro);
            
            Scene scene = new Scene(responder);
            Stage stage = new Stage();
            stage.setTitle("Seleccion de productos");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionProductosController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

}