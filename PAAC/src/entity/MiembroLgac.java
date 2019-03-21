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
@Table(name = "miembro_lgac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MiembroLgac.findAll", query = "SELECT m FROM MiembroLgac m")
    , @NamedQuery(name = "MiembroLgac.findByIdMiembroLGAC", query = "SELECT m FROM MiembroLgac m WHERE m.miembroLgacPK.idMiembroLGAC = :idMiembroLGAC")
    , @NamedQuery(name = "MiembroLgac.findByIdMiembro", query = "SELECT m FROM MiembroLgac m WHERE m.miembroLgacPK.idMiembro = :idMiembro")
    , @NamedQuery(name = "MiembroLgac.findByIdLGAC", query = "SELECT m FROM MiembroLgac m WHERE m.miembroLgacPK.idLGAC = :idLGAC")})
public class MiembroLgac implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MiembroLgacPK miembroLgacPK;
    @JoinColumn(name = "idMiembro", referencedColumnName = "idMiembro", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Miembro miembro;
    @JoinColumn(name = "idLGAC", referencedColumnName = "idlgac", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lgac lgac;

    public MiembroLgac() {
    }

    public MiembroLgac(MiembroLgacPK miembroLgacPK) {
        this.miembroLgacPK = miembroLgacPK;
    }

    public MiembroLgac(int idMiembroLGAC, int idMiembro, int idLGAC) {
        this.miembroLgacPK = new MiembroLgacPK(idMiembroLGAC, idMiembro, idLGAC);
    }

    public MiembroLgacPK getMiembroLgacPK() {
        return miembroLgacPK;
    }

    public void setMiembroLgacPK(MiembroLgacPK miembroLgacPK) {
        this.miembroLgacPK = miembroLgacPK;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public Lgac getLgac() {
        return lgac;
    }

    public void setLgac(Lgac lgac) {
        this.lgac = lgac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (miembroLgacPK != null ? miembroLgacPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiembroLgac)) {
            return false;
        }
        MiembroLgac other = (MiembroLgac) object;
        if ((this.miembroLgacPK == null && other.miembroLgacPK != null) || (this.miembroLgacPK != null && !this.miembroLgacPK.equals(other.miembroLgacPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MiembroLgac[ miembroLgacPK=" + miembroLgacPK + " ]";
    }
    
}
