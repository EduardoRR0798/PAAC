package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Rosas Rivera
 */
@Entity
@Table(name = "producto_colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoColaborador.findAll", query = "SELECT p FROM ProductoColaborador p")
    , @NamedQuery(name = "ProductoColaborador.findByIdProductoColaborador", query = "SELECT p FROM ProductoColaborador p WHERE p.productoColaboradorPK.idProductoColaborador = :idProductoColaborador")
    , @NamedQuery(name = "ProductoColaborador.findByIdProducto", query = "SELECT p FROM ProductoColaborador p WHERE p.productoColaboradorPK.idProducto = :idProducto")
    , @NamedQuery(name = "ProductoColaborador.findByIdColaborador", query = "SELECT p FROM ProductoColaborador p WHERE p.productoColaboradorPK.idColaborador = :idColaborador")
    , @NamedQuery(name =  "ProductoColaborador.findByProductoAndColaborador", query = "SELECT p FROM ProductoColaborador p WHERE p.productoColaboradorPK.idColaborador = :idColaborador AND p.producto.idProducto = :idProducto")})
public class ProductoColaborador implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoColaboradorPK productoColaboradorPK;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "idColaborador", referencedColumnName = "idColaborador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Colaborador colaborador;

    public ProductoColaborador() {
    }

    public ProductoColaborador(ProductoColaboradorPK productoColaboradorPK) {
        this.productoColaboradorPK = productoColaboradorPK;
    }

    public ProductoColaborador(int idProductoColaborador, int idProducto, int idColaborador) {
        this.productoColaboradorPK = new ProductoColaboradorPK(idProductoColaborador, idProducto, idColaborador);
    }

    public ProductoColaboradorPK getProductoColaboradorPK() {
        return productoColaboradorPK;
    }

    public void setProductoColaboradorPK(ProductoColaboradorPK productoColaboradorPK) {
        this.productoColaboradorPK = productoColaboradorPK;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoColaboradorPK != null ? productoColaboradorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoColaborador)) {
            return false;
        }
        ProductoColaborador other = (ProductoColaborador) object;
        if ((this.productoColaboradorPK == null && other.productoColaboradorPK != null) || (this.productoColaboradorPK != null && !this.productoColaboradorPK.equals(other.productoColaboradorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductoColaborador[ productoColaboradorPK=" + productoColaboradorPK + " ]";
    }
    
}
