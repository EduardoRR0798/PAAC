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
@Table(name = "tesis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tesis.findAll", query = "SELECT t FROM Tesis t")
    , @NamedQuery(name = "Tesis.findByIdTesis", query = "SELECT t FROM Tesis t WHERE t.idTesis = :idTesis")
    , @NamedQuery(name = "Tesis.findByGrado", query = "SELECT t FROM Tesis t WHERE t.grado = :grado")
    , @NamedQuery(name = "Tesis.findByNumRegistro", query = "SELECT t FROM Tesis t WHERE t.numRegistro = :numRegistro")
    , @NamedQuery(name = "Tesis.findByClasificacionInter", query = "SELECT t FROM Tesis t WHERE t.clasificacionInter = :clasificacionInter")
    , @NamedQuery(name = "Tesis.findByDescripcion", query = "SELECT t FROM Tesis t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Tesis.findByNumHojas", query = "SELECT t FROM Tesis t WHERE t.numHojas = :numHojas")
    , @NamedQuery(name = "Tesis.findByUsuarioDirigido", query = "SELECT t FROM Tesis t WHERE t.usuarioDirigido = :usuarioDirigido")})
public class Tesis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTesis")
    private Integer idTesis;
    @Column(name = "grado")
    private String grado;
    @Column(name = "numRegistro")
    private Integer numRegistro;
    @Column(name = "clasificacionInter")
    private String clasificacionInter;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "numHojas")
    private Integer numHojas;
    @Column(name = "usuarioDirigido")
    private String usuarioDirigido;
    @JoinColumn(name = "IdProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public Tesis() {
    }

    public Tesis(Integer idTesis) {
        this.idTesis = idTesis;
    }

    public Integer getIdTesis() {
        return idTesis;
    }

    public void setIdTesis(Integer idTesis) {
        this.idTesis = idTesis;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public Integer getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(Integer numRegistro) {
        this.numRegistro = numRegistro;
    }

    public String getClasificacionInter() {
        return clasificacionInter;
    }

    public void setClasificacionInter(String clasificacionInter) {
        this.clasificacionInter = clasificacionInter;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNumHojas() {
        return numHojas;
    }

    public void setNumHojas(Integer numHojas) {
        this.numHojas = numHojas;
    }

    public String getUsuarioDirigido() {
        return usuarioDirigido;
    }

    public void setUsuarioDirigido(String usuarioDirigido) {
        this.usuarioDirigido = usuarioDirigido;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTesis != null ? idTesis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tesis)) {
            return false;
        }
        Tesis other = (Tesis) object;
        if ((this.idTesis == null && other.idTesis != null) || (this.idTesis != null && !this.idTesis.equals(other.idTesis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tesis[ idTesis=" + idTesis + " ]";
    }
    
}
