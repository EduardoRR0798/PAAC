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
@Table(name = "patente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patente.findAll", query = "SELECT p FROM Patente p")
    , @NamedQuery(name = "Patente.findByIdPatente", query = "SELECT p FROM Patente p WHERE p.idPatente = :idPatente")
    , @NamedQuery(name = "Patente.findByClasifIntlPatentes", query = "SELECT p FROM Patente p WHERE p.clasifIntlPatentes = :clasifIntlPatentes")
    , @NamedQuery(name = "Patente.findByDescripcion", query = "SELECT p FROM Patente p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Patente.findByTipo", query = "SELECT p FROM Patente p WHERE p.tipo = :tipo")})
public class Patente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPatente")
    private Integer idPatente;
    @Column(name = "clasif_intl_patentes")
    private String clasifIntlPatentes;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public Patente() {
    }

    public Patente(Integer idPatente) {
        this.idPatente = idPatente;
    }

    public Integer getIdPatente() {
        return idPatente;
    }

    public void setIdPatente(Integer idPatente) {
        this.idPatente = idPatente;
    }

    public String getClasifIntlPatentes() {
        return clasifIntlPatentes;
    }

    public void setClasifIntlPatentes(String clasifIntlPatentes) {
        this.clasifIntlPatentes = clasifIntlPatentes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        hash += (idPatente != null ? idPatente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patente)) {
            return false;
        }
        Patente other = (Patente) object;
        if ((this.idPatente == null && other.idPatente != null) || (this.idPatente != null && !this.idPatente.equals(other.idPatente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Patente[ idPatente=" + idPatente + " ]";
    }
    
}
