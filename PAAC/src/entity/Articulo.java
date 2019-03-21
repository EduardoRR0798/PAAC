/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "articulo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a")
    , @NamedQuery(name = "Articulo.findByIdArticulo", query = "SELECT a FROM Articulo a WHERE a.idArticulo = :idArticulo")
    , @NamedQuery(name = "Articulo.findByEditorial", query = "SELECT a FROM Articulo a WHERE a.editorial = :editorial")
    , @NamedQuery(name = "Articulo.findByIssn", query = "SELECT a FROM Articulo a WHERE a.issn = :issn")
    , @NamedQuery(name = "Articulo.findByNombreRevista", query = "SELECT a FROM Articulo a WHERE a.nombreRevista = :nombreRevista")
    , @NamedQuery(name = "Articulo.findByRangoPaginas", query = "SELECT a FROM Articulo a WHERE a.rangoPaginas = :rangoPaginas")
    , @NamedQuery(name = "Articulo.findByVolumen", query = "SELECT a FROM Articulo a WHERE a.volumen = :volumen")})
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArticulo")
    private Integer idArticulo;
    @Lob
    @Column(name = "autores")
    private String autores;
    @Column(name = "editorial")
    private String editorial;
    @Column(name = "issn")
    private String issn;
    @Column(name = "nombre_revista")
    private String nombreRevista;
    @Column(name = "rango_paginas")
    private String rangoPaginas;
    @Column(name = "volumen")
    private String volumen;
    @OneToMany(mappedBy = "idArticulo")
    private Collection<ArticuloIndexado> articuloIndexadoCollection;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public Articulo() {
    }

    public Articulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getNombreRevista() {
        return nombreRevista;
    }

    public void setNombreRevista(String nombreRevista) {
        this.nombreRevista = nombreRevista;
    }

    public String getRangoPaginas() {
        return rangoPaginas;
    }

    public void setRangoPaginas(String rangoPaginas) {
        this.rangoPaginas = rangoPaginas;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    @XmlTransient
    public Collection<ArticuloIndexado> getArticuloIndexadoCollection() {
        return articuloIndexadoCollection;
    }

    public void setArticuloIndexadoCollection(Collection<ArticuloIndexado> articuloIndexadoCollection) {
        this.articuloIndexadoCollection = articuloIndexadoCollection;
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
        hash += (idArticulo != null ? idArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        if ((this.idArticulo == null && other.idArticulo != null) || (this.idArticulo != null && !this.idArticulo.equals(other.idArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Articulo[ idArticulo=" + idArticulo + " ]";
    }
    
}
