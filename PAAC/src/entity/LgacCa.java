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
 * @author Eduar
 */
@Entity
@Table(name = "lgac_ca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LgacCa.findAll", query = "SELECT l FROM LgacCa l")
    , @NamedQuery(name = "LgacCa.findByIdLGACCA", query = "SELECT l FROM LgacCa l WHERE l.lgacCaPK.idLGACCA = :idLGACCA")
    , @NamedQuery(name = "LgacCa.findByIdLGAC", query = "SELECT l FROM LgacCa l WHERE l.lgacCaPK.idLGAC = :idLGAC")
    , @NamedQuery(name = "LgacCa.findByIdCA", query = "SELECT l FROM LgacCa l WHERE l.lgacCaPK.idCA = :idCA")})
public class LgacCa implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LgacCaPK lgacCaPK;

    public LgacCa() {
    }

    public LgacCa(LgacCaPK lgacCaPK) {
        this.lgacCaPK = lgacCaPK;
    }

    public LgacCa(int idLGACCA, int idLGAC, int idCA) {
        this.lgacCaPK = new LgacCaPK(idLGACCA, idLGAC, idCA);
    }

    public LgacCaPK getLgacCaPK() {
        return lgacCaPK;
    }

    public void setLgacCaPK(LgacCaPK lgacCaPK) {
        this.lgacCaPK = lgacCaPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lgacCaPK != null ? lgacCaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LgacCa)) {
            return false;
        }
        LgacCa other = (LgacCa) object;
        if ((this.lgacCaPK == null && other.lgacCaPK != null) || (this.lgacCaPK != null && !this.lgacCaPK.equals(other.lgacCaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LgacCa[ lgacCaPK=" + lgacCaPK + " ]";
    }
    
}
