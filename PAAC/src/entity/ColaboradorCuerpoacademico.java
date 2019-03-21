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
@Table(name = "colaborador_cuerpoacademico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ColaboradorCuerpoacademico.findAll", query = "SELECT c FROM ColaboradorCuerpoacademico c")
    , @NamedQuery(name = "ColaboradorCuerpoacademico.findByIdColaboradorca", query = "SELECT c FROM ColaboradorCuerpoacademico c WHERE c.colaboradorCuerpoacademicoPK.idColaboradorca = :idColaboradorca")
    , @NamedQuery(name = "ColaboradorCuerpoacademico.findByIdColaborador", query = "SELECT c FROM ColaboradorCuerpoacademico c WHERE c.colaboradorCuerpoacademicoPK.idColaborador = :idColaborador")
    , @NamedQuery(name = "ColaboradorCuerpoacademico.findByIdCA", query = "SELECT c FROM ColaboradorCuerpoacademico c WHERE c.colaboradorCuerpoacademicoPK.idCA = :idCA")})
public class ColaboradorCuerpoacademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ColaboradorCuerpoacademicoPK colaboradorCuerpoacademicoPK;
    @JoinColumn(name = "idColaborador", referencedColumnName = "idColaborador", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Colaborador colaborador;
    @JoinColumn(name = "idCA", referencedColumnName = "idCuerpoAcademico", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CuerpoAcademico cuerpoAcademico;

    public ColaboradorCuerpoacademico() {
    }

    public ColaboradorCuerpoacademico(ColaboradorCuerpoacademicoPK colaboradorCuerpoacademicoPK) {
        this.colaboradorCuerpoacademicoPK = colaboradorCuerpoacademicoPK;
    }

    public ColaboradorCuerpoacademico(int idColaboradorca, int idColaborador, int idCA) {
        this.colaboradorCuerpoacademicoPK = new ColaboradorCuerpoacademicoPK(idColaboradorca, idColaborador, idCA);
    }

    public ColaboradorCuerpoacademicoPK getColaboradorCuerpoacademicoPK() {
        return colaboradorCuerpoacademicoPK;
    }

    public void setColaboradorCuerpoacademicoPK(ColaboradorCuerpoacademicoPK colaboradorCuerpoacademicoPK) {
        this.colaboradorCuerpoacademicoPK = colaboradorCuerpoacademicoPK;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public CuerpoAcademico getCuerpoAcademico() {
        return cuerpoAcademico;
    }

    public void setCuerpoAcademico(CuerpoAcademico cuerpoAcademico) {
        this.cuerpoAcademico = cuerpoAcademico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colaboradorCuerpoacademicoPK != null ? colaboradorCuerpoacademicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColaboradorCuerpoacademico)) {
            return false;
        }
        ColaboradorCuerpoacademico other = (ColaboradorCuerpoacademico) object;
        if ((this.colaboradorCuerpoacademicoPK == null && other.colaboradorCuerpoacademicoPK != null) || (this.colaboradorCuerpoacademicoPK != null && !this.colaboradorCuerpoacademicoPK.equals(other.colaboradorCuerpoacademicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ColaboradorCuerpoacademico[ colaboradorCuerpoacademicoPK=" + colaboradorCuerpoacademicoPK + " ]";
    }
    
}
