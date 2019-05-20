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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "datos_laborales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosLaborales.findAll", query = "SELECT d FROM DatosLaborales d")
    , @NamedQuery(name = "DatosLaborales.findByIdDatosLaborales", query = "SELECT d FROM DatosLaborales d WHERE d.idDatosLaborales = :idDatosLaborales")
    , @NamedQuery(name = "DatosLaborales.findByCronologia", query = "SELECT d FROM DatosLaborales d WHERE d.cronologia = :cronologia")
    , @NamedQuery(name = "DatosLaborales.findByDedicacion", query = "SELECT d FROM DatosLaborales d WHERE d.dedicacion = :dedicacion")
    , @NamedQuery(name = "DatosLaborales.findByDependencia", query = "SELECT d FROM DatosLaborales d WHERE d.dependencia = :dependencia")
    , @NamedQuery(name = "DatosLaborales.findByInicioContrato", query = "SELECT d FROM DatosLaborales d WHERE d.inicioContrato = :inicioContrato")
    , @NamedQuery(name = "DatosLaborales.findByFinContrato", query = "SELECT d FROM DatosLaborales d WHERE d.finContrato = :finContrato")
    , @NamedQuery(name = "DatosLaborales.findByIes", query = "SELECT d FROM DatosLaborales d WHERE d.ies = :ies")
    , @NamedQuery(name = "DatosLaborales.findByNombramiento", query = "SELECT d FROM DatosLaborales d WHERE d.nombramiento = :nombramiento")
    , @NamedQuery(name = "DatosLaborales.findByTipo", query = "SELECT d FROM DatosLaborales d WHERE d.tipo = :tipo")
    , @NamedQuery(name = "DatosLaborales.findByUnidadAcademica", query = "SELECT d FROM DatosLaborales d WHERE d.unidadAcademica = :unidadAcademica")
    , @NamedQuery(name = "DatosLaborales.findByIdMiembro", query = "SELECT d FROM DatosLaborales d WHERE d.idMiembro = :idMiembro")})
public class DatosLaborales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDatosLaborales")
    private Integer idDatosLaborales;
    @Column(name = "cronologia")
    private String cronologia;
    @Column(name = "dedicacion")
    private String dedicacion;
    @Column(name = "dependencia")
    private String dependencia;
    @Column(name = "inicioContrato")
    @Temporal(TemporalType.DATE)
    private Date inicioContrato;
    @Column(name = "finContrato")
    @Temporal(TemporalType.DATE)
    private Date finContrato;
    @Column(name = "ies")
    private String ies;
    @Column(name = "nombramiento")
    private String nombramiento;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "unidadAcademica")
    private String unidadAcademica;
    @JoinColumn(name = "idMiembro", referencedColumnName = "idMiembro")
    @ManyToOne
    private Miembro idMiembro;

    public DatosLaborales() {
    }

    public DatosLaborales(Integer idDatosLaborales) {
        this.idDatosLaborales = idDatosLaborales;
    }

    public Integer getIdDatosLaborales() {
        return idDatosLaborales;
    }

    public void setIdDatosLaborales(Integer idDatosLaborales) {
        this.idDatosLaborales = idDatosLaborales;
    }

    public String getCronologia() {
        return cronologia;
    }

    public void setCronologia(String cronologia) {
        this.cronologia = cronologia;
    }

    public String getDedicacion() {
        return dedicacion;
    }

    public void setDedicacion(String dedicacion) {
        this.dedicacion = dedicacion;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public Date getInicioContrato() {
        return inicioContrato;
    }

    public void setInicioContrato(Date inicioContrato) {
        this.inicioContrato = inicioContrato;
    }

    public Date getFinContrato() {
        return finContrato;
    }

    public void setFinContrato(Date finContrato) {
        this.finContrato = finContrato;
    }

    public String getIes() {
        return ies;
    }

    public void setIes(String ies) {
        this.ies = ies;
    }

    public String getNombramiento() {
        return nombramiento;
    }

    public void setNombramiento(String nombramiento) {
        this.nombramiento = nombramiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(String unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    public Miembro getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(Miembro idMiembro) {
        this.idMiembro = idMiembro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDatosLaborales != null ? idDatosLaborales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatosLaborales)) {
            return false;
        }
        DatosLaborales other = (DatosLaborales) object;
        if ((this.idDatosLaborales == null && other.idDatosLaborales != null) || (this.idDatosLaborales != null && !this.idDatosLaborales.equals(other.idDatosLaborales))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombramiento;
    }
    
}
