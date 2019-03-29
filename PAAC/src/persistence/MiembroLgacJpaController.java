/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Miembro;
import entity.Lgac;
import entity.MiembroLgac;
import entity.MiembroLgacPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.NonexistentEntityException;
import persistence.exceptions.PreexistingEntityException;

/**
 *
 * @author Eduar
 */
public class MiembroLgacJpaController implements Serializable {

    public MiembroLgacJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MiembroLgac miembroLgac) throws PreexistingEntityException, Exception {
        if (miembroLgac.getMiembroLgacPK() == null) {
            miembroLgac.setMiembroLgacPK(new MiembroLgacPK());
        }
        miembroLgac.getMiembroLgacPK().setIdLGAC(miembroLgac.getLgac().getIdlgac());
        miembroLgac.getMiembroLgacPK().setIdMiembro(miembroLgac.getMiembro().getIdMiembro());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembro miembro = miembroLgac.getMiembro();
            if (miembro != null) {
                miembro = em.getReference(miembro.getClass(), miembro.getIdMiembro());
                miembroLgac.setMiembro(miembro);
            }
            Lgac lgac = miembroLgac.getLgac();
            if (lgac != null) {
                lgac = em.getReference(lgac.getClass(), lgac.getIdlgac());
                miembroLgac.setLgac(lgac);
            }
            em.persist(miembroLgac);
            if (miembro != null) {
                miembro.getMiembroLgacList().add(miembroLgac);
                miembro = em.merge(miembro);
            }
            if (lgac != null) {
                lgac.getMiembroLgacList().add(miembroLgac);
                lgac = em.merge(lgac);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMiembroLgac(miembroLgac.getMiembroLgacPK()) != null) {
                throw new PreexistingEntityException("MiembroLgac " + miembroLgac + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MiembroLgac miembroLgac) throws NonexistentEntityException, Exception {
        miembroLgac.getMiembroLgacPK().setIdLGAC(miembroLgac.getLgac().getIdlgac());
        miembroLgac.getMiembroLgacPK().setIdMiembro(miembroLgac.getMiembro().getIdMiembro());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MiembroLgac persistentMiembroLgac = em.find(MiembroLgac.class, miembroLgac.getMiembroLgacPK());
            Miembro miembroOld = persistentMiembroLgac.getMiembro();
            Miembro miembroNew = miembroLgac.getMiembro();
            Lgac lgacOld = persistentMiembroLgac.getLgac();
            Lgac lgacNew = miembroLgac.getLgac();
            if (miembroNew != null) {
                miembroNew = em.getReference(miembroNew.getClass(), miembroNew.getIdMiembro());
                miembroLgac.setMiembro(miembroNew);
            }
            if (lgacNew != null) {
                lgacNew = em.getReference(lgacNew.getClass(), lgacNew.getIdlgac());
                miembroLgac.setLgac(lgacNew);
            }
            miembroLgac = em.merge(miembroLgac);
            if (miembroOld != null && !miembroOld.equals(miembroNew)) {
                miembroOld.getMiembroLgacList().remove(miembroLgac);
                miembroOld = em.merge(miembroOld);
            }
            if (miembroNew != null && !miembroNew.equals(miembroOld)) {
                miembroNew.getMiembroLgacList().add(miembroLgac);
                miembroNew = em.merge(miembroNew);
            }
            if (lgacOld != null && !lgacOld.equals(lgacNew)) {
                lgacOld.getMiembroLgacList().remove(miembroLgac);
                lgacOld = em.merge(lgacOld);
            }
            if (lgacNew != null && !lgacNew.equals(lgacOld)) {
                lgacNew.getMiembroLgacList().add(miembroLgac);
                lgacNew = em.merge(lgacNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MiembroLgacPK id = miembroLgac.getMiembroLgacPK();
                if (findMiembroLgac(id) == null) {
                    throw new NonexistentEntityException("The miembroLgac with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MiembroLgacPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MiembroLgac miembroLgac;
            try {
                miembroLgac = em.getReference(MiembroLgac.class, id);
                miembroLgac.getMiembroLgacPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The miembroLgac with id " + id + " no longer exists.", enfe);
            }
            Miembro miembro = miembroLgac.getMiembro();
            if (miembro != null) {
                miembro.getMiembroLgacList().remove(miembroLgac);
                miembro = em.merge(miembro);
            }
            Lgac lgac = miembroLgac.getLgac();
            if (lgac != null) {
                lgac.getMiembroLgacList().remove(miembroLgac);
                lgac = em.merge(lgac);
            }
            em.remove(miembroLgac);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MiembroLgac> findMiembroLgacEntities() {
        return findMiembroLgacEntities(true, -1, -1);
    }

    public List<MiembroLgac> findMiembroLgacEntities(int maxResults, int firstResult) {
        return findMiembroLgacEntities(false, maxResults, firstResult);
    }

    private List<MiembroLgac> findMiembroLgacEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MiembroLgac.class));
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

    public MiembroLgac findMiembroLgac(MiembroLgacPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MiembroLgac.class, id);
        } finally {
            em.close();
        }
    }

    public int getMiembroLgacCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MiembroLgac> rt = cq.from(MiembroLgac.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
