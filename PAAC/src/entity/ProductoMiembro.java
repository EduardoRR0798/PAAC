/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "producto_miembro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoMiembro.findAll", query = "SELECT p FROM ProductoMiembro p")
    , @NamedQuery(name = "ProductoMiembro.findByIdMiembroProducto", query = "SELECT p FROM ProductoMiembro p WHERE p.idMiembroProducto = :idMiembroProducto")
    , @NamedQuery(name = "ProductoMiembro.findByIdProducto", query = "SELECT p FROM ProductoMiembro p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name =  "ProductoMiembro.findByProductoAndMiembro", query = "SELECT p FROM ProductoMiembro p WHERE p.idMiembro = :idMiembro AND p.idProducto = :idProducto")})
public class ProductoMiembro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMiembroProducto")
    private Integer idMiembroProducto;
    @JoinColumn(name = "idMiembro", referencedColumnName = "idMiembro")
    @ManyToOne
    private Miembro idMiembro;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public ProductoMiembro() {
    }

    public ProductoMiembro(Integer idMiembroProducto) {
        this.idMiembroProducto = idMiembroProducto;
    }

    public Integer getIdMiembroProducto() {
        return idMiembroProducto;
    }

    public void setIdMiembroProducto(Integer idMiembroProducto) {
        this.idMiembroProducto = idMiembroProducto;
    }

    public Miembro getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Miembro idMiembro) {
        this.idMiembro = idMiembro;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMiembroProducto != null ? idMiembroProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoMiembro)) {
            return false;
        }
        ProductoMiembro other = (ProductoMiembro) object;
        if ((this.idMiembroProducto == null && other.idMiembroProducto != null) || (this.idMiembroProducto != null && !this.idMiembroProducto.equals(other.idMiembroProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductoMiembro[ idMiembroProducto=" + idMiembroProducto + " ]";
    }
    
}
