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
public class ProductoColaboradorPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idProductoColaborador")
    private int idProductoColaborador;
    @Basic(optional = false)
    @Column(name = "idProducto")
    private int idProducto;
    @Basic(optional = false)
    @Column(name = "idColaborador")
    private int idColaborador;

    public ProductoColaboradorPK() {
    }

    public ProductoColaboradorPK(int idProductoColaborador, int idProducto, int idColaborador) {
        this.idProductoColaborador = idProductoColaborador;
        this.idProducto = idProducto;
        this.idColaborador = idColaborador;
    }

    public int getIdProductoColaborador() {
        return idProductoColaborador;
    }

    public void setIdProductoColaborador(int idProductoColaborador) {
        this.idProductoColaborador = idProductoColaborador;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProductoColaborador;
        hash += (int) idProducto;
        hash += (int) idColaborador;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoColaboradorPK)) {
            return false;
        }
        ProductoColaboradorPK other = (ProductoColaboradorPK) object;
        if (this.idProductoColaborador != other.idProductoColaborador) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.idColaborador != other.idColaborador) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductoColaboradorPK[ idProductoColaborador=" + idProductoColaborador + ", idProducto=" + idProducto + ", idColaborador=" + idColaborador + " ]";
    }
    
}
