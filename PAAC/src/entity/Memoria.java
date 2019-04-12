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
@Table(name = "memoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoria.findAll", query = "SELECT m FROM Memoria m")
    , @NamedQuery(name = "Memoria.findByIdMemoria", query = "SELECT m FROM Memoria m WHERE m.idMemoria = :idMemoria")
    , @NamedQuery(name = "Memoria.findByCiudad", query = "SELECT m FROM Memoria m WHERE m.ciudad = :ciudad")
    , @NamedQuery(name = "Memoria.findByEstado", query = "SELECT m FROM Memoria m WHERE m.estado = :estado")
    , @NamedQuery(name = "Memoria.findByRangoPaginas", query = "SELECT m FROM Memoria m WHERE m.rangoPaginas = :rangoPaginas")
    , @NamedQuery(name = "Memoria.findByNombreCongreso", query = "SELECT m FROM Memoria m WHERE m.nombreCongreso = :nombreCongreso")})
public class Memoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMemoria")
    private Integer idMemoria;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "estado")
    private String estado;
    @Column(name = "rangoPaginas")
    private String rangoPaginas;
    @Column(name = "nombreCongreso")
    private String nombreCongreso;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public Memoria() {
    }

    public Memoria(Integer idMemoria) {
        this.idMemoria = idMemoria;
    }

    public Integer getIdMemoria() {
        return idMemoria;
    }

    public void setIdMemoria(Integer idMemoria) {
        this.idMemoria = idMemoria;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRangoPaginas() {
        return rangoPaginas;
    }

    public void setRangoPaginas(String rangoPaginas) {
        this.rangoPaginas = rangoPaginas;
    }

    public String getNombreCongreso() {
        return nombreCongreso;
    }

    public void setNombreCongreso(String nombreCongreso) {
        this.nombreCongreso = nombreCongreso;
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
        hash += (idMemoria != null ? idMemoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoria)) {
            return false;
        }
        Memoria other = (Memoria) object;
        if ((this.idMemoria == null && other.idMemoria != null) || (this.idMemoria != null && !this.idMemoria.equals(other.idMemoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Memoria[ idMemoria=" + idMemoria + " ]";
    }
    
}
