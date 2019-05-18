package entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo Rosas Rivera
 */
@Entity
@Table(name = "ca_miembro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaMiembro.findAll", query = "SELECT c FROM CaMiembro c")
    , @NamedQuery(name = "CaMiembro.findByIdCAMiembro", query = "SELECT c FROM CaMiembro c WHERE c.caMiembroPK.idCAMiembro = :idCAMiembro")
    , @NamedQuery(name = "CaMiembro.findByIdMiembro", query = "SELECT c FROM CaMiembro c WHERE c.caMiembroPK.idMiembro = :idMiembro")
    , @NamedQuery(name = "CaMiembro.findByIdCuerpoAcademico", query = "SELECT c FROM CaMiembro c WHERE c.caMiembroPK.idCuerpoAcademico = :idCuerpoAcademico")})
public class CaMiembro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CaMiembroPK caMiembroPK;

    public CaMiembro() {
    }

    public CaMiembro(CaMiembroPK caMiembroPK) {
        this.caMiembroPK = caMiembroPK;
    }

    public CaMiembro(int idCAMiembro, int idMiembro, int idCuerpoAcademico) {
        this.caMiembroPK = new CaMiembroPK(idCAMiembro, idMiembro, idCuerpoAcademico);
    }

    public CaMiembroPK getCaMiembroPK() {
        return caMiembroPK;
    }

    public void setCaMiembroPK(CaMiembroPK caMiembroPK) {
        this.caMiembroPK = caMiembroPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caMiembroPK != null ? caMiembroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CaMiembro)) {
            return false;
        }
        CaMiembro other = (CaMiembro) object;
        if ((this.caMiembroPK == null && other.caMiembroPK != null) || (this.caMiembroPK != null && !this.caMiembroPK.equals(other.caMiembroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CaMiembro[ caMiembroPK=" + caMiembroPK + " ]";
    }
    
}
