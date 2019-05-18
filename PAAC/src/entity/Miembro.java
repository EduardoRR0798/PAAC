package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "miembro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miembro.findAll", query = "SELECT m FROM Miembro m")
    , @NamedQuery(name = "Miembro.findByIdMiembro", query = "SELECT m FROM Miembro m WHERE m.idMiembro = :idMiembro")
    , @NamedQuery(name = "Miembro.findByEstado", query = "SELECT m FROM Miembro m WHERE m.estado = :estado")
    , @NamedQuery(name = "Miembro.findByPeImpacta", query = "SELECT m FROM Miembro m WHERE m.peImpacta = :peImpacta")
    , @NamedQuery(name = "Miembro.findByPromep", query = "SELECT m FROM Miembro m WHERE m.promep = :promep")
    , @NamedQuery(name = "Miembro.findBySni", query = "SELECT m FROM Miembro m WHERE m.sni = :sni")
    , @NamedQuery(name = "Miembro.findByTipo", query = "SELECT m FROM Miembro m WHERE m.tipo = :tipo")
    , @NamedQuery(name = "Miembro.findByNombre", query = "SELECT m FROM Miembro m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Miembro.findByUsuario", query = "SELECT m FROM Miembro m WHERE m.usuario = :usuario")
    , @NamedQuery(name = "Miembro.findByContrasenia", query = "SELECT m FROM Miembro m WHERE m.contrasenia = :contrasenia")
    , @NamedQuery(name = "Miembro.Login", query = "SELECT m FROM Miembro m WHERE m.usuario = :usuario AND m.contrasenia = :contrasenia")})
public class Miembro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMiembro")
    private Integer idMiembro;
    @Column(name = "estado")
    private String estado;
    @Column(name = "pe_impacta")
    private String peImpacta;
    @Column(name = "promep")
    private String promep;
    @Column(name = "sni")
    private String sni;
    @Column(name = "tipo")
    private Integer tipo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contrasenia")
    private String contrasenia;
    @OneToMany(mappedBy = "idMiembro")
    private List<ProductoMiembro> productoMiembroList;
    @OneToMany(mappedBy = "idMiembro")
    private List<DatosLaborales> datosLaboralesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "miembro")
    private List<MiembroLgac> miembroLgacList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMiembro")
    private List<Gradoacademico> gradoacademicoList;

    public Miembro() {
    }

    public Miembro(Integer idMiembro) {
        this.idMiembro = idMiembro;
    }

    public Integer getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Integer idMiembro) {
        this.idMiembro = idMiembro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPeImpacta() {
        return peImpacta;
    }

    public void setPeImpacta(String peImpacta) {
        this.peImpacta = peImpacta;
    }

    public String getPromep() {
        return promep;
    }

    public void setPromep(String promep) {
        this.promep = promep;
    }

    public String getSni() {
        return sni;
    }

    public void setSni(String sni) {
        this.sni = sni;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @XmlTransient
    public List<ProductoMiembro> getProductoMiembroList() {
        return productoMiembroList;
    }

    public void setProductoMiembroList(List<ProductoMiembro> productoMiembroList) {
        this.productoMiembroList = productoMiembroList;
    }

    @XmlTransient
    public List<DatosLaborales> getDatosLaboralesList() {
        return datosLaboralesList;
    }

    public void setDatosLaboralesList(List<DatosLaborales> datosLaboralesList) {
        this.datosLaboralesList = datosLaboralesList;
    }

    @XmlTransient
    public List<MiembroLgac> getMiembroLgacList() {
        return miembroLgacList;
    }

    public void setMiembroLgacList(List<MiembroLgac> miembroLgacList) {
        this.miembroLgacList = miembroLgacList;
    }

    @XmlTransient
    public List<Gradoacademico> getGradoacademicoList() {
        return gradoacademicoList;
    }

    public void setGradoacademicoList(List<Gradoacademico> gradoacademicoList) {
        this.gradoacademicoList = gradoacademicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMiembro != null ? idMiembro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miembro)) {
            return false;
        }
        Miembro other = (Miembro) object;
        if ((this.idMiembro == null && other.idMiembro != null) || (this.idMiembro != null && !this.idMiembro.equals(other.idMiembro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
