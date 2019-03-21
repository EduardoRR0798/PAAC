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
@Table(name = "cuerpoacademicoexterno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuerpoacademicoexterno.findAll", query = "SELECT c FROM Cuerpoacademicoexterno c")
    , @NamedQuery(name = "Cuerpoacademicoexterno.findByIdCuerpoeExterno", query = "SELECT c FROM Cuerpoacademicoexterno c WHERE c.idCuerpoeExterno = :idCuerpoeExterno")
    , @NamedQuery(name = "Cuerpoacademicoexterno.findByIes", query = "SELECT c FROM Cuerpoacademicoexterno c WHERE c.ies = :ies")
    , @NamedQuery(name = "Cuerpoacademicoexterno.findByNombre", query = "SELECT c FROM Cuerpoacademicoexterno c WHERE c.nombre = :nombre")})
public class Cuerpoacademicoexterno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCuerpoeExterno")
    private Integer idCuerpoeExterno;
    @Column(name = "IES")
    private String ies;
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "idPais", referencedColumnName = "idPais")
    @ManyToOne
    private Pais idPais;
    @JoinColumn(name = "idParticipacion", referencedColumnName = "idParticipacion")
    @ManyToOne
    private Participacion idParticipacion;

    public Cuerpoacademicoexterno() {
    }

    public Cuerpoacademicoexterno(Integer idCuerpoeExterno) {
        this.idCuerpoeExterno = idCuerpoeExterno;
    }

    public Integer getIdCuerpoeExterno() {
        return idCuerpoeExterno;
    }

    public void setIdCuerpoeExterno(Integer idCuerpoeExterno) {
        this.idCuerpoeExterno = idCuerpoeExterno;
    }

    public String getIes() {
        return ies;
    }

    public void setIes(String ies) {
        this.ies = ies;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
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
        hash += (idCuerpoeExterno != null ? idCuerpoeExterno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuerpoacademicoexterno)) {
            return false;
        }
        Cuerpoacademicoexterno other = (Cuerpoacademicoexterno) object;
        if ((this.idCuerpoeExterno == null && other.idCuerpoeExterno != null) || (this.idCuerpoeExterno != null && !this.idCuerpoeExterno.equals(other.idCuerpoeExterno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cuerpoacademicoexterno[ idCuerpoeExterno=" + idCuerpoeExterno + " ]";
    }
    
}
