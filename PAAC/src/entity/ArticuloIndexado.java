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
 * @author Eduardo Rosas Rivera
 */
@Entity
@Table(name = "articulo_indexado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticuloIndexado.findAll", query = "SELECT a FROM ArticuloIndexado a")
    , @NamedQuery(name = "ArticuloIndexado.findByIdArticuloIndexado", query = "SELECT a FROM ArticuloIndexado a WHERE a.idArticuloIndexado = :idArticuloIndexado")
    , @NamedQuery(name = "ArticuloIndexado.findByDescripcion", query = "SELECT a FROM ArticuloIndexado a WHERE a.descripcion = :descripcion")
    , @NamedQuery(name = "ArticuloIndexado.findByDireccionElectronica", query = "SELECT a FROM ArticuloIndexado a WHERE a.direccionElectronica = :direccionElectronica")
    , @NamedQuery(name = "ArticuloIndexado.findByIndice", query = "SELECT a FROM ArticuloIndexado a WHERE a.indice = :indice")})
public class ArticuloIndexado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArticuloIndexado")
    private Integer idArticuloIndexado;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "direccionElectronica")
    private String direccionElectronica;
    @Column(name = "indice")
    private String indice;
    @JoinColumn(name = "idArticulo", referencedColumnName = "idArticulo")
    @ManyToOne
    private Articulo idArticulo;

    public ArticuloIndexado() {
    }

    public ArticuloIndexado(Integer idArticuloIndexado) {
        this.idArticuloIndexado = idArticuloIndexado;
    }

    public Integer getIdArticuloIndexado() {
        return idArticuloIndexado;
    }

    public void setIdArticuloIndexado(Integer idArticuloIndexado) {
        this.idArticuloIndexado = idArticuloIndexado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccionElectronica() {
        return direccionElectronica;
    }

    public void setDireccionElectronica(String direccionElectronica) {
        this.direccionElectronica = direccionElectronica;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public Articulo getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Articulo idArticulo) {
        this.idArticulo = idArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticuloIndexado != null ? idArticuloIndexado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticuloIndexado)) {
            return false;
        }
        ArticuloIndexado other = (ArticuloIndexado) object;
        if ((this.idArticuloIndexado == null && other.idArticuloIndexado != null) || (this.idArticuloIndexado != null && !this.idArticuloIndexado.equals(other.idArticuloIndexado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ArticuloIndexado[ idArticuloIndexado=" + idArticuloIndexado + " ]";
    }
    
}
