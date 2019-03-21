/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "cuerpo_academico_promep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuerpoAcademicoPromep.findAll", query = "SELECT c FROM CuerpoAcademicoPromep c")
    , @NamedQuery(name = "CuerpoAcademicoPromep.findByIdPROMEP", query = "SELECT c FROM CuerpoAcademicoPromep c WHERE c.idPROMEP = :idPROMEP")
    , @NamedQuery(name = "CuerpoAcademicoPromep.findByDes", query = "SELECT c FROM CuerpoAcademicoPromep c WHERE c.des = :des")
    , @NamedQuery(name = "CuerpoAcademicoPromep.findByNombre", query = "SELECT c FROM CuerpoAcademicoPromep c WHERE c.nombre = :nombre")})
public class CuerpoAcademicoPromep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPROMEP")
    private Integer idPROMEP;
    @Basic(optional = false)
    @Column(name = "DES")
    private String des;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "idParticipacion", referencedColumnName = "idParticipacion")
    @ManyToOne(optional = false)
    private Participacion idParticipacion;

    public CuerpoAcademicoPromep() {
    }

    public CuerpoAcademicoPromep(Integer idPROMEP) {
        this.idPROMEP = idPROMEP;
    }

    public CuerpoAcademicoPromep(Integer idPROMEP, String des, String nombre) {
        this.idPROMEP = idPROMEP;
        this.des = des;
        this.nombre = nombre;
    }

    public Integer getIdPROMEP() {
        return idPROMEP;
    }

    public void setIdPROMEP(Integer idPROMEP) {
        this.idPROMEP = idPROMEP;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Participacion getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Participacion idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPROMEP != null ? idPROMEP.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuerpoAcademicoPromep)) {
            return false;
        }
        CuerpoAcademicoPromep other = (CuerpoAcademicoPromep) object;
        if ((this.idPROMEP == null && other.idPROMEP != null) || (this.idPROMEP != null && !this.idPROMEP.equals(other.idPROMEP))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CuerpoAcademicoPromep[ idPROMEP=" + idPROMEP + " ]";
    }
    
}
