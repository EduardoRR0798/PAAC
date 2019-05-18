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
public class LgacCaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idLGACCA")
    private int idLGACCA;
    @Basic(optional = false)
    @Column(name = "idLGAC")
    private int idLGAC;
    @Basic(optional = false)
    @Column(name = "idCA")
    private int idCA;

    public LgacCaPK() {
    }

    public LgacCaPK(int idLGACCA, int idLGAC, int idCA) {
        this.idLGACCA = idLGACCA;
        this.idLGAC = idLGAC;
        this.idCA = idCA;
    }

    public int getIdLGACCA() {
        return idLGACCA;
    }

    public void setIdLGACCA(int idLGACCA) {
        this.idLGACCA = idLGACCA;
    }

    public int getIdLGAC() {
        return idLGAC;
    }

    public void setIdLGAC(int idLGAC) {
        this.idLGAC = idLGAC;
    }

    public int getIdCA() {
        return idCA;
    }

    public void setIdCA(int idCA) {
        this.idCA = idCA;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idLGACCA;
        hash += (int) idLGAC;
        hash += (int) idCA;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LgacCaPK)) {
            return false;
        }
        LgacCaPK other = (LgacCaPK) object;
        if (this.idLGACCA != other.idLGACCA) {
            return false;
        }
        if (this.idLGAC != other.idLGAC) {
            return false;
        }
        if (this.idCA != other.idCA) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LgacCaPK[ idLGACCA=" + idLGACCA + ", idLGAC=" + idLGAC + ", idCA=" + idCA + " ]";
    }
    
}
