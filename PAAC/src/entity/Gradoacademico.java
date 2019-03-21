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
@Table(name = "gradoacademico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gradoacademico.findAll", query = "SELECT g FROM Gradoacademico g")
    , @NamedQuery(name = "Gradoacademico.findByIdGradoAcademico", query = "SELECT g FROM Gradoacademico g WHERE g.idGradoAcademico = :idGradoAcademico")
    , @NamedQuery(name = "Gradoacademico.findByAreaDisciplinar", query = "SELECT g FROM Gradoacademico g WHERE g.areaDisciplinar = :areaDisciplinar")
    , @NamedQuery(name = "Gradoacademico.findByFechaInicio", query = "SELECT g FROM Gradoacademico g WHERE g.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Gradoacademico.findByFechatitulacion", query = "SELECT g FROM Gradoacademico g WHERE g.fechatitulacion = :fechatitulacion")
    , @NamedQuery(name = "Gradoacademico.findByInstitucion", query = "SELECT g FROM Gradoacademico g WHERE g.institucion = :institucion")
    , @NamedQuery(name = "Gradoacademico.findByInstitucionNoConsiderada", query = "SELECT g FROM Gradoacademico g WHERE g.institucionNoConsiderada = :institucionNoConsiderada")
    , @NamedQuery(name = "Gradoacademico.findByNivel", query = "SELECT g FROM Gradoacademico g WHERE g.nivel = :nivel")
    , @NamedQuery(name = "Gradoacademico.findByTema", query = "SELECT g FROM Gradoacademico g WHERE g.tema = :tema")})
public class Gradoacademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGradoAcademico")
    private Integer idGradoAcademico;
    @Column(name = "areaDisciplinar")
    private String areaDisciplinar;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechatitulacion")
    @Temporal(TemporalType.DATE)
    private Date fechatitulacion;
    @Column(name = "institucion")
    private String institucion;
    @Column(name = "institucionNoConsiderada")
    private String institucionNoConsiderada;
    @Column(name = "nivel")
    private String nivel;
    @Column(name = "tema")
    private String tema;
    @JoinColumn(name = "idPais", referencedColumnName = "idPais")
    @ManyToOne(optional = false)
    private Pais idPais;
    @JoinColumn(name = "idGradoAcademico", referencedColumnName = "idMiembro", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Miembro miembro;

    public Gradoacademico() {
    }

    public Gradoacademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public String getAreaDisciplinar() {
        return areaDisciplinar;
    }

    public void setAreaDisciplinar(String areaDisciplinar) {
        this.areaDisciplinar = areaDisciplinar;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechatitulacion() {
        return fechatitulacion;
    }

    public void setFechatitulacion(Date fechatitulacion) {
        this.fechatitulacion = fechatitulacion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getInstitucionNoConsiderada() {
        return institucionNoConsiderada;
    }

    public void setInstitucionNoConsiderada(String institucionNoConsiderada) {
        this.institucionNoConsiderada = institucionNoConsiderada;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGradoAcademico != null ? idGradoAcademico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gradoacademico)) {
            return false;
        }
        Gradoacademico other = (Gradoacademico) object;
        if ((this.idGradoAcademico == null && other.idGradoAcademico != null) || (this.idGradoAcademico != null && !this.idGradoAcademico.equals(other.idGradoAcademico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Gradoacademico[ idGradoAcademico=" + idGradoAcademico + " ]";
    }
    
}