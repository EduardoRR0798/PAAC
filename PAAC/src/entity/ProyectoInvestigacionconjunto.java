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
@Table(name = "proyecto_investigacionconjunto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProyectoInvestigacionconjunto.findAll", query = "SELECT p FROM ProyectoInvestigacionconjunto p")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByIdProyectoInvestigacionConjunto", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.idProyectoInvestigacionConjunto = :idProyectoInvestigacionConjunto")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByActividades", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.actividades = :actividades")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByFechaFin", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.fechaFin = :fechaFin")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByFechaInicio", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByNombre", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByNombrePatrocinador", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.nombrePatrocinador = :nombrePatrocinador")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByTipoPatrocinador", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.tipoPatrocinador = :tipoPatrocinador")
    , @NamedQuery(name = "ProyectoInvestigacionconjunto.findByNombrePDF", query = "SELECT p FROM ProyectoInvestigacionconjunto p WHERE p.nombrePDF = :nombrePDF")})
public class ProyectoInvestigacionconjunto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProyectoInvestigacionConjunto")
    private Integer idProyectoInvestigacionConjunto;
    @Column(name = "actividades")
    private String actividades;
    @Lob
    @Column(name = "archivo_pdf")
    private byte[] archivoPdf;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nombrePatrocinador")
    private String nombrePatrocinador;
    @Column(name = "tipoPatrocinador")
    private String tipoPatrocinador;
    @Column(name = "nombrePDF")
    private String nombrePDF;
    @JoinColumn(name = "idProyectoInvestigacionConjunto", referencedColumnName = "idCuerpoAcademico", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private CuerpoAcademico cuerpoAcademico;

    public ProyectoInvestigacionconjunto() {
    }

    public ProyectoInvestigacionconjunto(Integer idProyectoInvestigacionConjunto) {
        this.idProyectoInvestigacionConjunto = idProyectoInvestigacionConjunto;
    }

    public Integer getIdProyectoInvestigacionConjunto() {
        return idProyectoInvestigacionConjunto;
    }

    public void setIdProyectoInvestigacionConjunto(Integer idProyectoInvestigacionConjunto) {
        this.idProyectoInvestigacionConjunto = idProyectoInvestigacionConjunto;
    }

    public String getActividades() {
        return actividades;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }

    public byte[] getArchivoPdf() {
        return archivoPdf;
    }

    public void setArchivoPdf(byte[] archivoPdf) {
        this.archivoPdf = archivoPdf;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombrePatrocinador() {
        return nombrePatrocinador;
    }

    public void setNombrePatrocinador(String nombrePatrocinador) {
        this.nombrePatrocinador = nombrePatrocinador;
    }

    public String getTipoPatrocinador() {
        return tipoPatrocinador;
    }

    public void setTipoPatrocinador(String tipoPatrocinador) {
        this.tipoPatrocinador = tipoPatrocinador;
    }

    public String getNombrePDF() {
        return nombrePDF;
    }

    public void setNombrePDF(String nombrePDF) {
        this.nombrePDF = nombrePDF;
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
        hash += (idProyectoInvestigacionConjunto != null ? idProyectoInvestigacionConjunto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProyectoInvestigacionconjunto)) {
            return false;
        }
        ProyectoInvestigacionconjunto other = (ProyectoInvestigacionconjunto) object;
        if ((this.idProyectoInvestigacionConjunto == null && other.idProyectoInvestigacionConjunto != null) || (this.idProyectoInvestigacionConjunto != null && !this.idProyectoInvestigacionConjunto.equals(other.idProyectoInvestigacionConjunto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProyectoInvestigacionconjunto[ idProyectoInvestigacionConjunto=" + idProyectoInvestigacionConjunto + " ]";
    }
    
}
