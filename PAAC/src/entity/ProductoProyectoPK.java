/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Eduar
 */
@Embeddable
public class ProductoProyectoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idProductoProyecto")
    private int idProductoProyecto;
    @Basic(optional = false)
    @Column(name = "idProducto")
    private int idProducto;
    @Basic(optional = false)
    @Column(name = "idProyecto")
    private int idProyecto;

    public ProductoProyectoPK() {
    }

    public ProductoProyectoPK(int idProductoProyecto, int idProducto, int idProyecto) {
        this.idProductoProyecto = idProductoProyecto;
        this.idProducto = idProducto;
        this.idProyecto = idProyecto;
    }

    public int getIdProductoProyecto() {
        return idProductoProyecto;
    }

    public void setIdProductoProyecto(int idProductoProyecto) {
        this.idProductoProyecto = idProductoProyecto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProductoProyecto;
        hash += (int) idProducto;
        hash += (int) idProyecto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoProyectoPK)) {
            return false;
        }
        ProductoProyectoPK other = (ProductoProyectoPK) object;
        if (this.idProductoProyecto != other.idProductoProyecto) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.idProyecto != other.idProyecto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductoProyectoPK[ idProductoProyecto=" + idProductoProyecto + ", idProducto=" + idProducto + ", idProyecto=" + idProyecto + " ]";
    }
    
}
