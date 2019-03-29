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
import javax.persistence.Lob;
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
@Table(name = "prototipo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prototipo.findAll", query = "SELECT p FROM Prototipo p")
    , @NamedQuery(name = "Prototipo.findByIdprototipo", query = "SELECT p FROM Prototipo p WHERE p.idprototipo = :idprototipo")
    , @NamedQuery(name = "Prototipo.findByAutores", query = "SELECT p FROM Prototipo p WHERE p.autores = :autores")
    , @NamedQuery(name = "Prototipo.findByCaracteristicas", query = "SELECT p FROM Prototipo p WHERE p.caracteristicas = :caracteristicas")
    , @NamedQuery(name = "Prototipo.findByInstitucionCreacion", query = "SELECT p FROM Prototipo p WHERE p.institucionCreacion = :institucionCreacion")
    , @NamedQuery(name = "Prototipo.findByObjetivo", query = "SELECT p FROM Prototipo p WHERE p.objetivo = :objetivo")
    , @NamedQuery(name = "Prototipo.findByNombrePDF", query = "SELECT p FROM Prototipo p WHERE p.nombrePDF = :nombrePDF")})
public class Prototipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprototipo")
    private Integer idprototipo;
    @Lob
    @Column(name = "archivoPDF")
    private byte[] archivoPDF;
    @Column(name = "autores")
    private String autores;
    @Column(name = "caracteristicas")
    private String caracteristicas;
    @Column(name = "institucionCreacion")
    private String institucionCreacion;
    @Column(name = "objetivo")
    private String objetivo;
    @Column(name = "nombrePDF")
    private String nombrePDF;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public Prototipo() {
    }

    public Prototipo(Integer idprototipo) {
        this.idprototipo = idprototipo;
    }

    public Integer getIdprototipo() {
        return idprototipo;
    }

    public void setIdprototipo(Integer idprototipo) {
        this.idprototipo = idprototipo;
    }

    public byte[] getArchivoPDF() {
        return archivoPDF;
    }

    public void setArchivoPDF(byte[] archivoPDF) {
        this.archivoPDF = archivoPDF;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getInstitucionCreacion() {
        return institucionCreacion;
    }

    public void setInstitucionCreacion(String institucionCreacion) {
        this.institucionCreacion = institucionCreacion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getNombrePDF() {
        return nombrePDF;
    }

    public void setNombrePDF(String nombrePDF) {
        this.nombrePDF = nombrePDF;
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
        hash += (idprototipo != null ? idprototipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prototipo)) {
            return false;
        }
        Prototipo other = (Prototipo) object;
        if ((this.idprototipo == null && other.idprototipo != null) || (this.idprototipo != null && !this.idprototipo.equals(other.idprototipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Prototipo[ idprototipo=" + idprototipo + " ]";
    }
    
}
