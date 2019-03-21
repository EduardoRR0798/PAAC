/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name = "Producto.findByAnio", query = "SELECT p FROM Producto p WHERE p.anio = :anio")
    , @NamedQuery(name = "Producto.findByEstadoActual", query = "SELECT p FROM Producto p WHERE p.estadoActual = :estadoActual")
    , @NamedQuery(name = "Producto.findByProposito", query = "SELECT p FROM Producto p WHERE p.proposito = :proposito")
    , @NamedQuery(name = "Producto.findByTitulo", query = "SELECT p FROM Producto p WHERE p.titulo = :titulo")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProducto")
    private Integer idProducto;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "estado_actual")
    private String estadoActual;
    @Column(name = "proposito")
    private String proposito;
    @Column(name = "titulo")
    private String titulo;
    @OneToMany(mappedBy = "idProducto")
    private Collection<Memoria> memoriaCollection;
    @JoinColumn(name = "idPais", referencedColumnName = "idPais")
    @ManyToOne
    private Pais idPais;
    @OneToMany(mappedBy = "idProducto")
    private Collection<CapituloLibro> capituloLibroCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<ProductoProyecto> productoProyectoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProducto")
    private Collection<Prototipo> prototipoCollection;
    @OneToMany(mappedBy = "idProducto")
    private Collection<Articulo> articuloCollection;
    @OneToMany(mappedBy = "idProducto")
    private Collection<Tesis> tesisCollection;
    @OneToMany(mappedBy = "idProducto")
    private Collection<Libro> libroCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<ProductoColaborador> productoColaboradorCollection;
    @OneToMany(mappedBy = "idProducto")
    private Collection<Patente> patenteCollection;

    public Producto() {
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @XmlTransient
    public Collection<Memoria> getMemoriaCollection() {
        return memoriaCollection;
    }

    public void setMemoriaCollection(Collection<Memoria> memoriaCollection) {
        this.memoriaCollection = memoriaCollection;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    @XmlTransient
    public Collection<CapituloLibro> getCapituloLibroCollection() {
        return capituloLibroCollection;
    }

    public void setCapituloLibroCollection(Collection<CapituloLibro> capituloLibroCollection) {
        this.capituloLibroCollection = capituloLibroCollection;
    }

    @XmlTransient
    public Collection<ProductoProyecto> getProductoProyectoCollection() {
        return productoProyectoCollection;
    }

    public void setProductoProyectoCollection(Collection<ProductoProyecto> productoProyectoCollection) {
        this.productoProyectoCollection = productoProyectoCollection;
    }

    @XmlTransient
    public Collection<Prototipo> getPrototipoCollection() {
        return prototipoCollection;
    }

    public void setPrototipoCollection(Collection<Prototipo> prototipoCollection) {
        this.prototipoCollection = prototipoCollection;
    }

    @XmlTransient
    public Collection<Articulo> getArticuloCollection() {
        return articuloCollection;
    }

    public void setArticuloCollection(Collection<Articulo> articuloCollection) {
        this.articuloCollection = articuloCollection;
    }

    @XmlTransient
    public Collection<Tesis> getTesisCollection() {
        return tesisCollection;
    }

    public void setTesisCollection(Collection<Tesis> tesisCollection) {
        this.tesisCollection = tesisCollection;
    }

    @XmlTransient
    public Collection<Libro> getLibroCollection() {
        return libroCollection;
    }

    public void setLibroCollection(Collection<Libro> libroCollection) {
        this.libroCollection = libroCollection;
    }

    @XmlTransient
    public Collection<ProductoColaborador> getProductoColaboradorCollection() {
        return productoColaboradorCollection;
    }

    public void setProductoColaboradorCollection(Collection<ProductoColaborador> productoColaboradorCollection) {
        this.productoColaboradorCollection = productoColaboradorCollection;
    }

    @XmlTransient
    public Collection<Patente> getPatenteCollection() {
        return patenteCollection;
    }

    public void setPatenteCollection(Collection<Patente> patenteCollection) {
        this.patenteCollection = patenteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Producto[ idProducto=" + idProducto + " ]";
    }
    
}
