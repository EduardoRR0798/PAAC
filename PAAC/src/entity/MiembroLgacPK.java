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
public class MiembroLgacPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idMiembroLGAC")
    private int idMiembroLGAC;
    @Basic(optional = false)
    @Column(name = "idMiembro")
    private int idMiembro;
    @Basic(optional = false)
    @Column(name = "idLGAC")
    private int idLGAC;

    public MiembroLgacPK() {
    }

    public MiembroLgacPK(int idMiembroLGAC, int idMiembro, int idLGAC) {
        this.idMiembroLGAC = idMiembroLGAC;
        this.idMiembro = idMiembro;
        this.idLGAC = idLGAC;
    }

    public int getIdMiembroLGAC() {
        return idMiembroLGAC;
    }

    public void setIdMiembroLGAC(int idMiembroLGAC) {
        this.idMiembroLGAC = idMiembroLGAC;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idMiembroLGAC;
        hash += (int) idMiembro;
        hash += (int) idLGAC;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MiembroLgacPK)) {
            return false;
        }
        MiembroLgacPK other = (MiembroLgacPK) object;
        if (this.idMiembroLGAC != other.idMiembroLGAC) {
            return false;
        }
        if (this.idMiembro != other.idMiembro) {
            return false;
        }
        if (this.idLGAC != other.idLGAC) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MiembroLgacPK[ idMiembroLGAC=" + idMiembroLGAC + ", idMiembro=" + idMiembro + ", idLGAC=" + idLGAC + " ]";
    }
    
}
