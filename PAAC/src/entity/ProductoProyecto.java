/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Eduar
 */
@Entity
@Table(name = "producto_proyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoProyecto.findAll", query = "SELECT p FROM ProductoProyecto p")
    , @NamedQuery(name = "ProductoProyecto.findByIdProductoProyecto", query = "SELECT p FROM ProductoProyecto p WHERE p.productoProyectoPK.idProductoProyecto = :idProductoProyecto")
    , @NamedQuery(name = "ProductoProyecto.findByIdProducto", query = "SELECT p FROM ProductoProyecto p WHERE p.productoProyectoPK.idProducto = :idProducto")
    , @NamedQuery(name = "ProductoProyecto.findByIdProyecto", query = "SELECT p FROM ProductoProyecto p WHERE p.productoProyectoPK.idProyecto = :idProyecto")})
public class ProductoProyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoProyectoPK productoProyectoPK;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "idProyecto", referencedColumnName = "idProyecto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proyecto proyecto;

    public ProductoProyecto() {
    }

    public ProductoProyecto(ProductoProyectoPK productoProyectoPK) {
        this.productoProyectoPK = productoProyectoPK;
    }

    public ProductoProyecto(int idProductoProyecto, int idProducto, int idProyecto) {
        this.productoProyectoPK = new ProductoProyectoPK(idProductoProyecto, idProducto, idProyecto);
    }

    public ProductoProyectoPK getProductoProyectoPK() {
        return productoProyectoPK;
    }

    public void setProductoProyectoPK(ProductoProyectoPK productoProyectoPK) {
        this.productoProyectoPK = productoProyectoPK;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoProyectoPK != null ? productoProyectoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoProyecto)) {
            return false;
        }
        ProductoProyecto other = (ProductoProyecto) object;
        if ((this.productoProyectoPK == null && other.productoProyectoPK != null) || (this.productoProyectoPK != null && !this.productoProyectoPK.equals(other.productoProyectoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductoProyecto[ productoProyectoPK=" + productoProyectoPK + " ]";
    }
    
}
