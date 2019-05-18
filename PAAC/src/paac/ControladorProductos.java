package paac;

import entity.Colaborador;
import entity.Miembro;
import entity.Pais;
import entity.Producto;
import entity.ProductoColaborador;
import entity.ProductoMiembro;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        } catch (Exception e) { }        
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
    protected ObservableList<Miembro> recuperarMiembros() {
        ObservableList<Miembro> mis = FXCollections.observableArrayList();
        Miembro m = new Miembro();
        MiembroJpaController mJpaC = new MiembroJpaController();
        List<Miembro> ms;
        ms = mJpaC.findAll();
        for (int i = 0; i < ms.size(); i++) {
            mis.add(ms.get(i));
        }
        return mis;
    }
    
    /**
     * Recupera a todos los colaboradores de la base de datos;
     * @return lista de todos los colaboradores.
     */
    protected ObservableList<Colaborador> recuperarColaboradores() {
        ObservableList<Colaborador> colaboradores = FXCollections.observableArrayList();
        Colaborador c = new Colaborador();
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
        ProductoMiembro pm;
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
    
    
}