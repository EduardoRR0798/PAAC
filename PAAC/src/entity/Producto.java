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
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name = "Producto.findByAnio", query = "SELECT p FROM Producto p WHERE p.anio = :anio")
    , @NamedQuery(name = "Producto.findByEstadoActual", query = "SELECT p FROM Producto p WHERE p.estadoActual = :estadoActual")
    , @NamedQuery(name = "Producto.findByProposito", query = "SELECT p FROM Producto p WHERE p.proposito = :proposito")
    , @NamedQuery(name = "Producto.findByTitulo", query = "SELECT p FROM Producto p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Producto.findByNombrePDF", query = "SELECT p FROM Producto p WHERE p.nombrePDF = :nombrePDF")})
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
    @Lob
    @Column(name = "archivoPDF")
    private byte[] archivoPDF;
    @Column(name = "nombrePDF")
    private String nombrePDF;
    @OneToMany(mappedBy = "idProducto")
    private List<Memoria> memoriaList;
    @OneToMany(mappedBy = "idProducto")
    private List<ProductoMiembro> productoMiembroList;
    @JoinColumn(name = "idPais", referencedColumnName = "idPais")
    @ManyToOne
    private Pais idPais;
    @JoinColumn(name = "idCuerpoAcademico", referencedColumnName = "idCuerpoAcademico")
    @ManyToOne
    private CuerpoAcademico idCuerpoAcademico;
    @OneToMany(mappedBy = "idProducto")
    private List<CapituloLibro> capituloLibroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<ProductoProyecto> productoProyectoList;
    @OneToMany(mappedBy = "idProducto")
    private List<Prototipo> prototipoList;
    @OneToMany(mappedBy = "idProducto")
    private List<Articulo> articuloList;
    @OneToMany(mappedBy = "idProducto")
    private List<Tesis> tesisList;
    @OneToMany(mappedBy = "idProducto")
    private List<Libro> libroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<ProductoColaborador> productoColaboradorList;
    @OneToMany(mappedBy = "idProducto")
    private List<Patente> patenteList;

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

    public byte[] getArchivoPDF() {
        return archivoPDF;
    }

    public void setArchivoPDF(byte[] archivoPDF) {
        this.archivoPDF = archivoPDF;
    }

    public String getNombrePDF() {
        return nombrePDF;
    }

    public void setNombrePDF(String nombrePDF) {
        this.nombrePDF = nombrePDF;
    }

    @XmlTransient
    public List<Memoria> getMemoriaList() {
        return memoriaList;
    }

    public void setMemoriaList(List<Memoria> memoriaList) {
        this.memoriaList = memoriaList;
    }

    @XmlTransient
    public List<ProductoMiembro> getProductoMiembroList() {
        return productoMiembroList;
    }

    public void setProductoMiembroList(List<ProductoMiembro> productoMiembroList) {
        this.productoMiembroList = productoMiembroList;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    public CuerpoAcademico getIdCuerpoAcademico() {
        return idCuerpoAcademico;
    }

    public void setIdCuerpoAcademico(CuerpoAcademico idCuerpoAcademico) {
        this.idCuerpoAcademico = idCuerpoAcademico;
    }

    @XmlTransient
    public List<CapituloLibro> getCapituloLibroList() {
        return capituloLibroList;
    }

    public void setCapituloLibroList(List<CapituloLibro> capituloLibroList) {
        this.capituloLibroList = capituloLibroList;
    }

    @XmlTransient
    public List<ProductoProyecto> getProductoProyectoList() {
        return productoProyectoList;
    }

    public void setProductoProyectoList(List<ProductoProyecto> productoProyectoList) {
        this.productoProyectoList = productoProyectoList;
    }

    @XmlTransient
    public List<Prototipo> getPrototipoList() {
        return prototipoList;
    }

    public void setPrototipoList(List<Prototipo> prototipoList) {
        this.prototipoList = prototipoList;
    }

    @XmlTransient
    public List<Articulo> getArticuloList() {
        return articuloList;
    }

    public void setArticuloList(List<Articulo> articuloList) {
        this.articuloList = articuloList;
    }

    @XmlTransient
    public List<Tesis> getTesisList() {
        return tesisList;
    }

    public void setTesisList(List<Tesis> tesisList) {
        this.tesisList = tesisList;
    }

    @XmlTransient
    public List<Libro> getLibroList() {
        return libroList;
    }

    public void setLibroList(List<Libro> libroList) {
        this.libroList = libroList;
    }

    @XmlTransient
    public List<ProductoColaborador> getProductoColaboradorList() {
        return productoColaboradorList;
    }

    public void setProductoColaboradorList(List<ProductoColaborador> productoColaboradorList) {
        this.productoColaboradorList = productoColaboradorList;
    }

    @XmlTransient
    public List<Patente> getPatenteList() {
        return patenteList;
    }

    public void setPatenteList(List<Patente> patenteList) {
        this.patenteList = patenteList;
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
        return titulo;
    }
    
}
