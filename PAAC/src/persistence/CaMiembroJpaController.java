package persistence;

import entity.CaMiembro;
import entity.CaMiembroPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistence.exceptions.NonexistentEntityException;
import persistence.exceptions.PreexistingEntityException;

/**
 *
 * @author Eduardo Rosas Rivera
 */
public class CaMiembroJpaController implements Serializable {

    public CaMiembroJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CaMiembro caMiembro) throws PreexistingEntityException, Exception {
        if (caMiembro.getCaMiembroPK() == null) {
            caMiembro.setCaMiembroPK(new CaMiembroPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(caMiembro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCaMiembro(caMiembro.getCaMiembroPK()) != null) {
                throw new PreexistingEntityException("CaMiembro " + caMiembro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CaMiembro caMiembro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            caMiembro = em.merge(caMiembro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CaMiembroPK id = caMiembro.getCaMiembroPK();
                if (findCaMiembro(id) == null) {
                    throw new NonexistentEntityException("The caMiembro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CaMiembroPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CaMiembro caMiembro;
            try {
                caMiembro = em.getReference(CaMiembro.class, id);
                caMiembro.getCaMiembroPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caMiembro with id " + id + " no longer exists.", enfe);
            }
            em.remove(caMiembro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CaMiembro> findCaMiembroEntities() {
        return findCaMiembroEntities(true, -1, -1);
    }

    public List<CaMiembro> findCaMiembroEntities(int maxResults, int firstResult) {
        return findCaMiembroEntities(false, maxResults, firstResult);
    }

    private List<CaMiembro> findCaMiembroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CaMiembro.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CaMiembro findCaMiembro(CaMiembroPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CaMiembro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCaMiembroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CaMiembro> rt = cq.from(CaMiembro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    /**
     * Recupera un CaMiembro segun el id del miembro solicitado.
     * @param m id del Miembro.
     * @return CaMiembro que corresponde al id del Miembro.
     */
    public CaMiembro findByMiembro(Integer m) {
        EntityManager em = getEntityManager();
        CaMiembro cam;
        try {
            Query q = em.createNamedQuery("CaMiembro.findByIdMiembro", 
                    CaMiembro.class).setParameter("idMiembro", m);
            cam = (CaMiembro) q.getSingleResult();
        } catch (Exception e) {
            cam = null;
        }
        return cam;
    }
}
