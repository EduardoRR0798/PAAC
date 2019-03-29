/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "lgac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lgac.findAll", query = "SELECT l FROM Lgac l")
    , @NamedQuery(name = "Lgac.findByIdlgac", query = "SELECT l FROM Lgac l WHERE l.idlgac = :idlgac")
    , @NamedQuery(name = "Lgac.findByNombre", query = "SELECT l FROM Lgac l WHERE l.nombre = :nombre")})
public class Lgac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlgac")
    private Integer idlgac;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idLGACApoyo")
    private List<Proyecto> proyectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lgac")
    private List<MiembroLgac> miembroLgacList;

    public Lgac() {
    }

    public Lgac(Integer idlgac) {
        this.idlgac = idlgac;
    }

    public Integer getIdlgac() {
        return idlgac;
    }

    public void setIdlgac(Integer idlgac) {
        this.idlgac = idlgac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }

    @XmlTransient
    public List<MiembroLgac> getMiembroLgacList() {
        return miembroLgacList;
    }

    public void setMiembroLgacList(List<MiembroLgac> miembroLgacList) {
        this.miembroLgacList = miembroLgacList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlgac != null ? idlgac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lgac)) {
            return false;
        }
        Lgac other = (Lgac) object;
        if ((this.idlgac == null && other.idlgac != null) || (this.idlgac != null && !this.idlgac.equals(other.idlgac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Lgac[ idlgac=" + idlgac + " ]";
    }
    
}
