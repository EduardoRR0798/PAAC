package persistence;

import entity.CuerpoAcademico;
import entity.LgacCa;
import entity.LgacCaPK;
import java.io.Serializable;
import java.util.ArrayList;
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
public class LgacCaJpaController implements Serializable {

    public LgacCaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LgacCa lgacCa) throws PreexistingEntityException, Exception {
        if (lgacCa.getLgacCaPK() == null) {
            lgacCa.setLgacCaPK(new LgacCaPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lgacCa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLgacCa(lgacCa.getLgacCaPK()) != null) {
                throw new PreexistingEntityException("LgacCa " + lgacCa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LgacCa lgacCa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lgacCa = em.merge(lgacCa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                LgacCaPK id = lgacCa.getLgacCaPK();
                if (findLgacCa(id) == null) {
                    throw new NonexistentEntityException("The lgacCa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(LgacCaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LgacCa lgacCa;
            try {
                lgacCa = em.getReference(LgacCa.class, id);
                lgacCa.getLgacCaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lgacCa with id " + id + " no longer exists.", enfe);
            }
            em.remove(lgacCa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LgacCa> findLgacCaEntities() {
        return findLgacCaEntities(true, -1, -1);
    }

    public List<LgacCa> findLgacCaEntities(int maxResults, int firstResult) {
        return findLgacCaEntities(false, maxResults, firstResult);
    }

    private List<LgacCa> findLgacCaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LgacCa.class));
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

    public LgacCa findLgacCa(LgacCaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LgacCa.class, id);
        } finally {
            em.close();
        }
    }

    public int getLgacCaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LgacCa> rt = cq.from(LgacCa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<LgacCa> findByCA(CuerpoAcademico ca) {
        EntityManager em = getEntityManager();
        List<LgacCa> lgacCas = new ArrayList<>();
        try {
            Query q = em.createNamedQuery("LgacCa.findByIdCA", LgacCa.class)
                    .setParameter("idCA", ca.getIdCuerpoAcademico());
            lgacCas = q.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
            lgacCas = null;
        }
        return lgacCas;
    }
    
}
