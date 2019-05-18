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
public class CaMiembroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idCAMiembro")
    private int idCAMiembro;
    @Basic(optional = false)
    @Column(name = "idMiembro")
    private int idMiembro;
    @Basic(optional = false)
    @Column(name = "idCuerpoAcademico")
    private int idCuerpoAcademico;

    public CaMiembroPK() {
    }

    public CaMiembroPK(int idCAMiembro, int idMiembro, int idCuerpoAcademico) {
        this.idCAMiembro = idCAMiembro;
        this.idMiembro = idMiembro;
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public int getIdCAMiembro() {
        return idCAMiembro;
    }

    public void setIdCAMiembro(int idCAMiembro) {
        this.idCAMiembro = idCAMiembro;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(int idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCAMiembro;
        hash += (int) idMiembro;
        hash += (int) idCuerpoAcademico;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaMiembroPK)) {
            return false;
        }
        CaMiembroPK other = (CaMiembroPK) object;
        if (this.idCAMiembro != other.idCAMiembro) {
            return false;
        }
        if (this.idMiembro != other.idMiembro) {
            return false;
        }
        if (this.idCuerpoAcademico != other.idCuerpoAcademico) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CaMiembroPK[ idCAMiembro=" + idCAMiembro + ", idMiembro=" + idMiembro + ", idCuerpoAcademico=" + idCuerpoAcademico + " ]";
    }
    
}
