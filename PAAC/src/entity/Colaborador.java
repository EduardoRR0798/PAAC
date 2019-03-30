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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eduar
 */
@Entity
@Table(name = "colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colaborador.findAll", query = "SELECT c FROM Colaborador c")
    , @NamedQuery(name = "Colaborador.findByIdColaborador", query = "SELECT c FROM Colaborador c WHERE c.idColaborador = :idColaborador")
    , @NamedQuery(name = "Colaborador.findByNombre", query = "SELECT c FROM Colaborador c WHERE c.nombre = :nombre")})
public class Colaborador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idColaborador")
    private Integer idColaborador;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colaborador")
    private List<ProductoColaborador> productoColaboradorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colaborador")
    private List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoList;

    public Colaborador() {
    }

    public Colaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<ProductoColaborador> getProductoColaboradorList() {
        return productoColaboradorList;
    }

    public void setProductoColaboradorList(List<ProductoColaborador> productoColaboradorList) {
        this.productoColaboradorList = productoColaboradorList;
    }

    @XmlTransient
    public List<ColaboradorCuerpoacademico> getColaboradorCuerpoacademicoList() {
        return colaboradorCuerpoacademicoList;
    }

    public void setColaboradorCuerpoacademicoList(List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoList) {
        this.colaboradorCuerpoacademicoList = colaboradorCuerpoacademicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColaborador != null ? idColaborador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colaborador)) {
            return false;
        }
        Colaborador other = (Colaborador) object;
        if ((this.idColaborador == null && other.idColaborador != null) || (this.idColaborador != null && !this.idColaborador.equals(other.idColaborador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
