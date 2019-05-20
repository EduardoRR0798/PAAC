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
@Table(name = "capitulo_libro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CapituloLibro.findAll", query = "SELECT c FROM CapituloLibro c")
    , @NamedQuery(name = "CapituloLibro.findByIdCapitulolibro", query = "SELECT c FROM CapituloLibro c WHERE c.idCapitulolibro = :idCapitulolibro")
    , @NamedQuery(name = "CapituloLibro.findByEditorial", query = "SELECT c FROM CapituloLibro c WHERE c.editorial = :editorial")
    , @NamedQuery(name = "CapituloLibro.findByEdicion", query = "SELECT c FROM CapituloLibro c WHERE c.edicion = :edicion")
    , @NamedQuery(name = "CapituloLibro.findByIsbn", query = "SELECT c FROM CapituloLibro c WHERE c.isbn = :isbn")
    , @NamedQuery(name = "CapituloLibro.findByNombreLibro", query = "SELECT c FROM CapituloLibro c WHERE c.nombreLibro = :nombreLibro")
    , @NamedQuery(name = "CapituloLibro.findByRangoPaginas", query = "SELECT c FROM CapituloLibro c WHERE c.rangoPaginas = :rangoPaginas")
    , @NamedQuery(name = "CapituloLibro.findByTiraje", query = "SELECT c FROM CapituloLibro c WHERE c.tiraje = :tiraje")
    , @NamedQuery(name = "CapituloLibro.findByTituloLibro", query = "SELECT c FROM CapituloLibro c WHERE c.tituloLibro = :tituloLibro")
    , @NamedQuery(name = "CapituloLibro.findByIdProducto", query = "SELECT c FROM CapituloLibro c WHERE c.idProducto = :idProducto")})
   
public class CapituloLibro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCapitulo_libro")
    private Integer idCapitulolibro;
    @Basic(optional = false)
    @Column(name = "editorial")
    private String editorial;
    @Column(name = "edicion")
    private Integer edicion;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "nombre_libro")
    private String nombreLibro;
    @Column(name = "rango_paginas")
    private String rangoPaginas;
    @Column(name = "tiraje")
    private Integer tiraje;
    @Column(name = "titulo_libro")
    private String tituloLibro;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne
    private Producto idProducto;

    public CapituloLibro() {
    }

    public CapituloLibro(Integer idCapitulolibro) {
        this.idCapitulolibro = idCapitulolibro;
    }

    public CapituloLibro(Integer idCapitulolibro, String editorial) {
        this.idCapitulolibro = idCapitulolibro;
        this.editorial = editorial;
    }

    public Integer getIdCapitulolibro() {
        return idCapitulolibro;
    }

    public void setIdCapitulolibro(Integer idCapitulolibro) {
        this.idCapitulolibro = idCapitulolibro;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getEdicion() {
        return edicion;
    }

    public void setEdicion(Integer edicion) {
        this.edicion = edicion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getRangoPaginas() {
        return rangoPaginas;
    }

    public void setRangoPaginas(String rangoPaginas) {
        this.rangoPaginas = rangoPaginas;
    }

    public Integer getTiraje() {
        return tiraje;
    }

    public void setTiraje(Integer tiraje) {
        this.tiraje = tiraje;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
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
        hash += (idCapitulolibro != null ? idCapitulolibro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CapituloLibro)) {
            return false;
        }
        CapituloLibro other = (CapituloLibro) object;
        if ((this.idCapitulolibro == null && other.idCapitulolibro != null) || (this.idCapitulolibro != null && !this.idCapitulolibro.equals(other.idCapitulolibro))) {
            return false;
        }
        return true;
    }

    public void setEdicion(String edicion) {
     
    }

    public void setTiraje(String tiraje) {
     
    }
      @Override
    public String toString() {
        return "entity.CapituloLibro[ idCapituloLibro=" + idCapitulolibro + " ]";
    }
    
}

    

