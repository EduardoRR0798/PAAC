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
public class ColaboradorCuerpoacademicoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idColaborador_ca")
    private int idColaboradorca;
    @Basic(optional = false)
    @Column(name = "idColaborador")
    private int idColaborador;
    @Basic(optional = false)
    @Column(name = "idCA")
    private int idCA;

    public ColaboradorCuerpoacademicoPK() {
    }

    public ColaboradorCuerpoacademicoPK(int idColaboradorca, int idColaborador, int idCA) {
        this.idColaboradorca = idColaboradorca;
        this.idColaborador = idColaborador;
        this.idCA = idCA;
    }

    public int getIdColaboradorca() {
        return idColaboradorca;
    }

    public void setIdColaboradorca(int idColaboradorca) {
        this.idColaboradorca = idColaboradorca;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
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
        hash += (int) idColaboradorca;
        hash += (int) idColaborador;
        hash += (int) idCA;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColaboradorCuerpoacademicoPK)) {
            return false;
        }
        ColaboradorCuerpoacademicoPK other = (ColaboradorCuerpoacademicoPK) object;
        if (this.idColaboradorca != other.idColaboradorca) {
            return false;
        }
        if (this.idColaborador != other.idColaborador) {
            return false;
        }
        if (this.idCA != other.idCA) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ColaboradorCuerpoacademicoPK[ idColaboradorca=" + idColaboradorca + ", idColaborador=" + idColaborador + ", idCA=" + idCA + " ]";
    }
    
}
