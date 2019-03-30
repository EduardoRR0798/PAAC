/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "cuerpo_academico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuerpoAcademico.findAll", query = "SELECT c FROM CuerpoAcademico c")
    , @NamedQuery(name = "CuerpoAcademico.findByIdCuerpoAcademico", query = "SELECT c FROM CuerpoAcademico c WHERE c.idCuerpoAcademico = :idCuerpoAcademico")
    , @NamedQuery(name = "CuerpoAcademico.findByClave", query = "SELECT c FROM CuerpoAcademico c WHERE c.clave = :clave")
    , @NamedQuery(name = "CuerpoAcademico.findByGradoConsolidacion", query = "SELECT c FROM CuerpoAcademico c WHERE c.gradoConsolidacion = :gradoConsolidacion")
    , @NamedQuery(name = "CuerpoAcademico.findByIes", query = "SELECT c FROM CuerpoAcademico c WHERE c.ies = :ies")
    , @NamedQuery(name = "CuerpoAcademico.findByNombre", query = "SELECT c FROM CuerpoAcademico c WHERE c.nombre = :nombre")})
public class CuerpoAcademico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCuerpoAcademico")
    private Integer idCuerpoAcademico;
    @Column(name = "clave")
    private String clave;
    @Column(name = "gradoConsolidacion")
    private String gradoConsolidacion;
    @Column(name = "ies")
    private String ies;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idCA")
    private List<Participacion> participacionList;
    @OneToMany(mappedBy = "idCuerpoAcademico")
    private List<Producto> productoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cuerpoAcademico")
    private ProyectoInvestigacionconjunto proyectoInvestigacionconjunto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuerpoAcademico")
    private List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cuerpoAcademico")
    private Pe pe;

    public CuerpoAcademico() {
    }

    public CuerpoAcademico(Integer idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public Integer getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(Integer idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getGradoConsolidacion() {
        return gradoConsolidacion;
    }

    public void setGradoConsolidacion(String gradoConsolidacion) {
        this.gradoConsolidacion = gradoConsolidacion;
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

    @XmlTransient
    public List<Participacion> getParticipacionList() {
        return participacionList;
    }

    public void setParticipacionList(List<Participacion> participacionList) {
        this.participacionList = participacionList;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public ProyectoInvestigacionconjunto getProyectoInvestigacionconjunto() {
        return proyectoInvestigacionconjunto;
    }

    public void setProyectoInvestigacionconjunto(ProyectoInvestigacionconjunto proyectoInvestigacionconjunto) {
        this.proyectoInvestigacionconjunto = proyectoInvestigacionconjunto;
    }

    @XmlTransient
    public List<ColaboradorCuerpoacademico> getColaboradorCuerpoacademicoList() {
        return colaboradorCuerpoacademicoList;
    }

    public void setColaboradorCuerpoacademicoList(List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoList) {
        this.colaboradorCuerpoacademicoList = colaboradorCuerpoacademicoList;
    }

    public Pe getPe() {
        return pe;
    }

    public void setPe(Pe pe) {
        this.pe = pe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuerpoAcademico != null ? idCuerpoAcademico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuerpoAcademico)) {
            return false;
        }
        CuerpoAcademico other = (CuerpoAcademico) object;
        if ((this.idCuerpoAcademico == null && other.idCuerpoAcademico != null) || (this.idCuerpoAcademico != null && !this.idCuerpoAcademico.equals(other.idCuerpoAcademico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CuerpoAcademico[ idCuerpoAcademico=" + idCuerpoAcademico + " ]";
    }
    
}
