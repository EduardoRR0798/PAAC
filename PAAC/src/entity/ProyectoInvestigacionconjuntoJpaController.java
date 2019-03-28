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
public class ProyectoInvestigacionconjuntoJpaController implements Serializable {

    public ProyectoInvestigacionconjuntoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProyectoInvestigacionconjunto proyectoInvestigacionconjunto) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        CuerpoAcademico cuerpoAcademicoOrphanCheck = proyectoInvestigacionconjunto.getCuerpoAcademico();
        if (cuerpoAcademicoOrphanCheck != null) {
            ProyectoInvestigacionconjunto oldProyectoInvestigacionconjuntoOfCuerpoAcademico = cuerpoAcademicoOrphanCheck.getProyectoInvestigacionconjunto();
            if (oldProyectoInvestigacionconjuntoOfCuerpoAcademico != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The CuerpoAcademico " + cuerpoAcademicoOrphanCheck + " already has an item of type ProyectoInvestigacionconjunto whose cuerpoAcademico column cannot be null. Please make another selection for the cuerpoAcademico field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademico cuerpoAcademico = proyectoInvestigacionconjunto.getCuerpoAcademico();
            if (cuerpoAcademico != null) {
                cuerpoAcademico = em.getReference(cuerpoAcademico.getClass(), cuerpoAcademico.getIdCuerpoAcademico());
                proyectoInvestigacionconjunto.setCuerpoAcademico(cuerpoAcademico);
            }
            em.persist(proyectoInvestigacionconjunto);
            if (cuerpoAcademico != null) {
                cuerpoAcademico.setProyectoInvestigacionconjunto(proyectoInvestigacionconjunto);
                cuerpoAcademico = em.merge(cuerpoAcademico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProyectoInvestigacionconjunto proyectoInvestigacionconjunto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProyectoInvestigacionconjunto persistentProyectoInvestigacionconjunto = em.find(ProyectoInvestigacionconjunto.class, proyectoInvestigacionconjunto.getIdProyectoInvestigacionConjunto());
            CuerpoAcademico cuerpoAcademicoOld = persistentProyectoInvestigacionconjunto.getCuerpoAcademico();
            CuerpoAcademico cuerpoAcademicoNew = proyectoInvestigacionconjunto.getCuerpoAcademico();
            List<String> illegalOrphanMessages = null;
            if (cuerpoAcademicoNew != null && !cuerpoAcademicoNew.equals(cuerpoAcademicoOld)) {
                ProyectoInvestigacionconjunto oldProyectoInvestigacionconjuntoOfCuerpoAcademico = cuerpoAcademicoNew.getProyectoInvestigacionconjunto();
                if (oldProyectoInvestigacionconjuntoOfCuerpoAcademico != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The CuerpoAcademico " + cuerpoAcademicoNew + " already has an item of type ProyectoInvestigacionconjunto whose cuerpoAcademico column cannot be null. Please make another selection for the cuerpoAcademico field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuerpoAcademicoNew != null) {
                cuerpoAcademicoNew = em.getReference(cuerpoAcademicoNew.getClass(), cuerpoAcademicoNew.getIdCuerpoAcademico());
                proyectoInvestigacionconjunto.setCuerpoAcademico(cuerpoAcademicoNew);
            }
            proyectoInvestigacionconjunto = em.merge(proyectoInvestigacionconjunto);
            if (cuerpoAcademicoOld != null && !cuerpoAcademicoOld.equals(cuerpoAcademicoNew)) {
                cuerpoAcademicoOld.setProyectoInvestigacionconjunto(null);
                cuerpoAcademicoOld = em.merge(cuerpoAcademicoOld);
            }
            if (cuerpoAcademicoNew != null && !cuerpoAcademicoNew.equals(cuerpoAcademicoOld)) {
                cuerpoAcademicoNew.setProyectoInvestigacionconjunto(proyectoInvestigacionconjunto);
                cuerpoAcademicoNew = em.merge(cuerpoAcademicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyectoInvestigacionconjunto.getIdProyectoInvestigacionConjunto();
                if (findProyectoInvestigacionconjunto(id) == null) {
                    throw new NonexistentEntityException("The proyectoInvestigacionconjunto with id " + id + " no longer exists.");
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
            ProyectoInvestigacionconjunto proyectoInvestigacionconjunto;
            try {
                proyectoInvestigacionconjunto = em.getReference(ProyectoInvestigacionconjunto.class, id);
                proyectoInvestigacionconjunto.getIdProyectoInvestigacionConjunto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyectoInvestigacionconjunto with id " + id + " no longer exists.", enfe);
            }
            CuerpoAcademico cuerpoAcademico = proyectoInvestigacionconjunto.getCuerpoAcademico();
            if (cuerpoAcademico != null) {
                cuerpoAcademico.setProyectoInvestigacionconjunto(null);
                cuerpoAcademico = em.merge(cuerpoAcademico);
            }
            em.remove(proyectoInvestigacionconjunto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProyectoInvestigacionconjunto> findProyectoInvestigacionconjuntoEntities() {
        return findProyectoInvestigacionconjuntoEntities(true, -1, -1);
    }

    public List<ProyectoInvestigacionconjunto> findProyectoInvestigacionconjuntoEntities(int maxResults, int firstResult) {
        return findProyectoInvestigacionconjuntoEntities(false, maxResults, firstResult);
    }

    private List<ProyectoInvestigacionconjunto> findProyectoInvestigacionconjuntoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProyectoInvestigacionconjunto.class));
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

    public ProyectoInvestigacionconjunto findProyectoInvestigacionconjunto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProyectoInvestigacionconjunto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoInvestigacionconjuntoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProyectoInvestigacionconjunto> rt = cq.from(ProyectoInvestigacionconjunto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
