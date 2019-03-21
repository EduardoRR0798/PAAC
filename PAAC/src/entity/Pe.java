/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "pe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pe.findAll", query = "SELECT p FROM Pe p")
    , @NamedQuery(name = "Pe.findByIdPE", query = "SELECT p FROM Pe p WHERE p.idPE = :idPE")
    , @NamedQuery(name = "Pe.findByGradoIntervecion", query = "SELECT p FROM Pe p WHERE p.gradoIntervecion = :gradoIntervecion")
    , @NamedQuery(name = "Pe.findByFechaImplementacion", query = "SELECT p FROM Pe p WHERE p.fechaImplementacion = :fechaImplementacion")
    , @NamedQuery(name = "Pe.findByNombrePE", query = "SELECT p FROM Pe p WHERE p.nombrePE = :nombrePE")
    , @NamedQuery(name = "Pe.findByPEAcreditado", query = "SELECT p FROM Pe p WHERE p.pEAcreditado = :pEAcreditado")
    , @NamedQuery(name = "Pe.findByPEDentroEGEL", query = "SELECT p FROM Pe p WHERE p.pEDentroEGEL = :pEDentroEGEL")
    , @NamedQuery(name = "Pe.findByResultadoParticipacion", query = "SELECT p FROM Pe p WHERE p.resultadoParticipacion = :resultadoParticipacion")})
public class Pe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPE")
    private Integer idPE;
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;
    @Column(name = "gradoIntervecion")
    private String gradoIntervecion;
    @Column(name = "fechaImplementacion")
    @Temporal(TemporalType.DATE)
    private Date fechaImplementacion;
    @Column(name = "nombrePE")
    private String nombrePE;
    @Column(name = "PEAcreditado")
    private String pEAcreditado;
    @Column(name = "PEDentroEGEL")
    private String pEDentroEGEL;
    @Column(name = "resultadoParticipacion")
    private String resultadoParticipacion;
    @JoinColumn(name = "idPE", referencedColumnName = "idCuerpoAcademico", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private CuerpoAcademico cuerpoAcademico;

    public Pe() {
    }

    public Pe(Integer idPE) {
        this.idPE = idPE;
    }

    public Integer getIdPE() {
        return idPE;
    }

    public void setIdPE(Integer idPE) {
        this.idPE = idPE;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getGradoIntervecion() {
        return gradoIntervecion;
    }

    public void setGradoIntervecion(String gradoIntervecion) {
        this.gradoIntervecion = gradoIntervecion;
    }

    public Date getFechaImplementacion() {
        return fechaImplementacion;
    }

    public void setFechaImplementacion(Date fechaImplementacion) {
        this.fechaImplementacion = fechaImplementacion;
    }

    public String getNombrePE() {
        return nombrePE;
    }

    public void setNombrePE(String nombrePE) {
        this.nombrePE = nombrePE;
    }

    public String getPEAcreditado() {
        return pEAcreditado;
    }

    public void setPEAcreditado(String pEAcreditado) {
        this.pEAcreditado = pEAcreditado;
    }

    public String getPEDentroEGEL() {
        return pEDentroEGEL;
    }

    public void setPEDentroEGEL(String pEDentroEGEL) {
        this.pEDentroEGEL = pEDentroEGEL;
    }

    public String getResultadoParticipacion() {
        return resultadoParticipacion;
    }

    public void setResultadoParticipacion(String resultadoParticipacion) {
        this.resultadoParticipacion = resultadoParticipacion;
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
        hash += (idPE != null ? idPE.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pe)) {
            return false;
        }
        Pe other = (Pe) object;
        if ((this.idPE == null && other.idPE != null) || (this.idPE != null && !this.idPE.equals(other.idPE))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pe[ idPE=" + idPE + " ]";
    }
    
}
