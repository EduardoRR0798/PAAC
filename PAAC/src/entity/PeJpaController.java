/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.IllegalOrphanException;
import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Eduar
 */
public class PeJpaController implements Serializable {

    public PeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pe pe) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        CuerpoAcademico cuerpoAcademicoOrphanCheck = pe.getCuerpoAcademico();
        if (cuerpoAcademicoOrphanCheck != null) {
            Pe oldPeOfCuerpoAcademico = cuerpoAcademicoOrphanCheck.getPe();
            if (oldPeOfCuerpoAcademico != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The CuerpoAcademico " + cuerpoAcademicoOrphanCheck + " already has an item of type Pe whose cuerpoAcademico column cannot be null. Please make another selection for the cuerpoAcademico field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademico cuerpoAcademico = pe.getCuerpoAcademico();
            if (cuerpoAcademico != null) {
                cuerpoAcademico = em.getReference(cuerpoAcademico.getClass(), cuerpoAcademico.getIdCuerpoAcademico());
                pe.setCuerpoAcademico(cuerpoAcademico);
            }
            em.persist(pe);
            if (cuerpoAcademico != null) {
                cuerpoAcademico.setPe(pe);
                cuerpoAcademico = em.merge(cuerpoAcademico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pe pe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pe persistentPe = em.find(Pe.class, pe.getIdPE());
            CuerpoAcademico cuerpoAcademicoOld = persistentPe.getCuerpoAcademico();
            CuerpoAcademico cuerpoAcademicoNew = pe.getCuerpoAcademico();
            List<String> illegalOrphanMessages = null;
            if (cuerpoAcademicoNew != null && !cuerpoAcademicoNew.equals(cuerpoAcademicoOld)) {
                Pe oldPeOfCuerpoAcademico = cuerpoAcademicoNew.getPe();
                if (oldPeOfCuerpoAcademico != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The CuerpoAcademico " + cuerpoAcademicoNew + " already has an item of type Pe whose cuerpoAcademico column cannot be null. Please make another selection for the cuerpoAcademico field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuerpoAcademicoNew != null) {
                cuerpoAcademicoNew = em.getReference(cuerpoAcademicoNew.getClass(), cuerpoAcademicoNew.getIdCuerpoAcademico());
                pe.setCuerpoAcademico(cuerpoAcademicoNew);
            }
            pe = em.merge(pe);
            if (cuerpoAcademicoOld != null && !cuerpoAcademicoOld.equals(cuerpoAcademicoNew)) {
                cuerpoAcademicoOld.setPe(null);
                cuerpoAcademicoOld = em.merge(cuerpoAcademicoOld);
            }
            if (cuerpoAcademicoNew != null && !cuerpoAcademicoNew.equals(cuerpoAcademicoOld)) {
                cuerpoAcademicoNew.setPe(pe);
                cuerpoAcademicoNew = em.merge(cuerpoAcademicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pe.getIdPE();
                if (findPe(id) == null) {
                    throw new NonexistentEntityException("The pe with id " + id + " no longer exists.");
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
            Pe pe;
            try {
                pe = em.getReference(Pe.class, id);
                pe.getIdPE();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pe with id " + id + " no longer exists.", enfe);
            }
            CuerpoAcademico cuerpoAcademico = pe.getCuerpoAcademico();
            if (cuerpoAcademico != null) {
                cuerpoAcademico.setPe(null);
                cuerpoAcademico = em.merge(cuerpoAcademico);
            }
            em.remove(pe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pe> findPeEntities() {
        return findPeEntities(true, -1, -1);
    }

    public List<Pe> findPeEntities(int maxResults, int firstResult) {
        return findPeEntities(false, maxResults, firstResult);
    }

    private List<Pe> findPeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pe.class));
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

    public Pe findPe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pe.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pe> rt = cq.from(Pe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
