/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "participacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participacion.findAll", query = "SELECT p FROM Participacion p")
    , @NamedQuery(name = "Participacion.findByIdParticipacion", query = "SELECT p FROM Participacion p WHERE p.idParticipacion = :idParticipacion")
    , @NamedQuery(name = "Participacion.findByDescripcionColaboracion", query = "SELECT p FROM Participacion p WHERE p.descripcionColaboracion = :descripcionColaboracion")
    , @NamedQuery(name = "Participacion.findByDescripcionCooperacion", query = "SELECT p FROM Participacion p WHERE p.descripcionCooperacion = :descripcionCooperacion")
    , @NamedQuery(name = "Participacion.findByFechaFin", query = "SELECT p FROM Participacion p WHERE p.fechaFin = :fechaFin")
    , @NamedQuery(name = "Participacion.findByFechaInicio", query = "SELECT p FROM Participacion p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Participacion.findByGrupo", query = "SELECT p FROM Participacion p WHERE p.grupo = :grupo")
    , @NamedQuery(name = "Participacion.findByObjetivoGrupo", query = "SELECT p FROM Participacion p WHERE p.objetivoGrupo = :objetivoGrupo")
    , @NamedQuery(name = "Participacion.findByTipo", query = "SELECT p FROM Participacion p WHERE p.tipo = :tipo")})
public class Participacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idParticipacion")
    private Integer idParticipacion;
    @Column(name = "descripcionColaboracion")
    private String descripcionColaboracion;
    @Column(name = "descripcionCooperacion")
    private String descripcionCooperacion;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "grupo")
    private String grupo;
    @Column(name = "objetivoGrupo")
    private String objetivoGrupo;
    @Column(name = "tipo")
    private Integer tipo;
    @JoinColumn(name = "idCA", referencedColumnName = "idCuerpoAcademico")
    @ManyToOne
    private CuerpoAcademico idCA;
    @OneToMany(mappedBy = "idParticipacion")
    private Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idParticipacion")
    private Collection<CuerpoAcademicoPromep> cuerpoAcademicoPromepCollection;

    public Participacion() {
    }

    public Participacion(Integer idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public Integer getIdParticipacion() {
        return idParticipacion;
    }

    public void setIdParticipacion(Integer idParticipacion) {
        this.idParticipacion = idParticipacion;
    }

    public String getDescripcionColaboracion() {
        return descripcionColaboracion;
    }

    public void setDescripcionColaboracion(String descripcionColaboracion) {
        this.descripcionColaboracion = descripcionColaboracion;
    }

    public String getDescripcionCooperacion() {
        return descripcionCooperacion;
    }

    public void setDescripcionCooperacion(String descripcionCooperacion) {
        this.descripcionCooperacion = descripcionCooperacion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getObjetivoGrupo() {
        return objetivoGrupo;
    }

    public void setObjetivoGrupo(String objetivoGrupo) {
        this.objetivoGrupo = objetivoGrupo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public CuerpoAcademico getIdCA() {
        return idCA;
    }

    public void setIdCA(CuerpoAcademico idCA) {
        this.idCA = idCA;
    }

    @XmlTransient
    public Collection<Cuerpoacademicoexterno> getCuerpoacademicoexternoCollection() {
        return cuerpoacademicoexternoCollection;
    }

    public void setCuerpoacademicoexternoCollection(Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollection) {
        this.cuerpoacademicoexternoCollection = cuerpoacademicoexternoCollection;
    }

    @XmlTransient
    public Collection<CuerpoAcademicoPromep> getCuerpoAcademicoPromepCollection() {
        return cuerpoAcademicoPromepCollection;
    }

    public void setCuerpoAcademicoPromepCollection(Collection<CuerpoAcademicoPromep> cuerpoAcademicoPromepCollection) {
        this.cuerpoAcademicoPromepCollection = cuerpoAcademicoPromepCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParticipacion != null ? idParticipacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participacion)) {
            return false;
        }
        Participacion other = (Participacion) object;
        if ((this.idParticipacion == null && other.idParticipacion != null) || (this.idParticipacion != null && !this.idParticipacion.equals(other.idParticipacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Participacion[ idParticipacion=" + idParticipacion + " ]";
    }
    
}
