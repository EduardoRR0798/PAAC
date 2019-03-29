/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entity.CuerpoAcademicoPromep;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Participacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class CuerpoAcademicoPromepJpaController implements Serializable {

    public CuerpoAcademicoPromepJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuerpoAcademicoPromep cuerpoAcademicoPromep) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Participacion idParticipacion = cuerpoAcademicoPromep.getIdParticipacion();
            if (idParticipacion != null) {
                idParticipacion = em.getReference(idParticipacion.getClass(), idParticipacion.getIdParticipacion());
                cuerpoAcademicoPromep.setIdParticipacion(idParticipacion);
            }
            em.persist(cuerpoAcademicoPromep);
            if (idParticipacion != null) {
                idParticipacion.getCuerpoAcademicoPromepList().add(cuerpoAcademicoPromep);
                idParticipacion = em.merge(idParticipacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuerpoAcademicoPromep cuerpoAcademicoPromep) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademicoPromep persistentCuerpoAcademicoPromep = em.find(CuerpoAcademicoPromep.class, cuerpoAcademicoPromep.getIdPROMEP());
            Participacion idParticipacionOld = persistentCuerpoAcademicoPromep.getIdParticipacion();
            Participacion idParticipacionNew = cuerpoAcademicoPromep.getIdParticipacion();
            if (idParticipacionNew != null) {
                idParticipacionNew = em.getReference(idParticipacionNew.getClass(), idParticipacionNew.getIdParticipacion());
                cuerpoAcademicoPromep.setIdParticipacion(idParticipacionNew);
            }
            cuerpoAcademicoPromep = em.merge(cuerpoAcademicoPromep);
            if (idParticipacionOld != null && !idParticipacionOld.equals(idParticipacionNew)) {
                idParticipacionOld.getCuerpoAcademicoPromepList().remove(cuerpoAcademicoPromep);
                idParticipacionOld = em.merge(idParticipacionOld);
            }
            if (idParticipacionNew != null && !idParticipacionNew.equals(idParticipacionOld)) {
                idParticipacionNew.getCuerpoAcademicoPromepList().add(cuerpoAcademicoPromep);
                idParticipacionNew = em.merge(idParticipacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuerpoAcademicoPromep.getIdPROMEP();
                if (findCuerpoAcademicoPromep(id) == null) {
                    throw new NonexistentEntityException("The cuerpoAcademicoPromep with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademicoPromep cuerpoAcademicoPromep;
            try {
                cuerpoAcademicoPromep = em.getReference(CuerpoAcademicoPromep.class, id);
                cuerpoAcademicoPromep.getIdPROMEP();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuerpoAcademicoPromep with id " + id + " no longer exists.", enfe);
            }
            Participacion idParticipacion = cuerpoAcademicoPromep.getIdParticipacion();
            if (idParticipacion != null) {
                idParticipacion.getCuerpoAcademicoPromepList().remove(cuerpoAcademicoPromep);
                idParticipacion = em.merge(idParticipacion);
            }
            em.remove(cuerpoAcademicoPromep);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuerpoAcademicoPromep> findCuerpoAcademicoPromepEntities() {
        return findCuerpoAcademicoPromepEntities(true, -1, -1);
    }

    public List<CuerpoAcademicoPromep> findCuerpoAcademicoPromepEntities(int maxResults, int firstResult) {
        return findCuerpoAcademicoPromepEntities(false, maxResults, firstResult);
    }

    private List<CuerpoAcademicoPromep> findCuerpoAcademicoPromepEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuerpoAcademicoPromep.class));
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

    public CuerpoAcademicoPromep findCuerpoAcademicoPromep(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuerpoAcademicoPromep.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuerpoAcademicoPromepCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuerpoAcademicoPromep> rt = cq.from(CuerpoAcademicoPromep.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
