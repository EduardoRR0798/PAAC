/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entity.Gradoacademico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Pais;
import entity.Miembro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class GradoacademicoJpaController implements Serializable {

    public GradoacademicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gradoacademico gradoacademico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais idPais = gradoacademico.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getIdPais());
                gradoacademico.setIdPais(idPais);
            }
            Miembro idMiembro = gradoacademico.getIdMiembro();
            if (idMiembro != null) {
                idMiembro = em.getReference(idMiembro.getClass(), idMiembro.getIdMiembro());
                gradoacademico.setIdMiembro(idMiembro);
            }
            em.persist(gradoacademico);
            if (idPais != null) {
                idPais.getGradoacademicoList().add(gradoacademico);
                idPais = em.merge(idPais);
            }
            if (idMiembro != null) {
                idMiembro.getGradoacademicoList().add(gradoacademico);
                idMiembro = em.merge(idMiembro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gradoacademico gradoacademico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gradoacademico persistentGradoacademico = em.find(Gradoacademico.class, gradoacademico.getIdGradoAcademico());
            Pais idPaisOld = persistentGradoacademico.getIdPais();
            Pais idPaisNew = gradoacademico.getIdPais();
            Miembro idMiembroOld = persistentGradoacademico.getIdMiembro();
            Miembro idMiembroNew = gradoacademico.getIdMiembro();
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getIdPais());
                gradoacademico.setIdPais(idPaisNew);
            }
            if (idMiembroNew != null) {
                idMiembroNew = em.getReference(idMiembroNew.getClass(), idMiembroNew.getIdMiembro());
                gradoacademico.setIdMiembro(idMiembroNew);
            }
            gradoacademico = em.merge(gradoacademico);
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getGradoacademicoList().remove(gradoacademico);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getGradoacademicoList().add(gradoacademico);
                idPaisNew = em.merge(idPaisNew);
            }
            if (idMiembroOld != null && !idMiembroOld.equals(idMiembroNew)) {
                idMiembroOld.getGradoacademicoList().remove(gradoacademico);
                idMiembroOld = em.merge(idMiembroOld);
            }
            if (idMiembroNew != null && !idMiembroNew.equals(idMiembroOld)) {
                idMiembroNew.getGradoacademicoList().add(gradoacademico);
                idMiembroNew = em.merge(idMiembroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gradoacademico.getIdGradoAcademico();
                if (findGradoacademico(id) == null) {
                    throw new NonexistentEntityException("The gradoacademico with id " + id + " no longer exists.");
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
            Gradoacademico gradoacademico;
            try {
                gradoacademico = em.getReference(Gradoacademico.class, id);
                gradoacademico.getIdGradoAcademico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gradoacademico with id " + id + " no longer exists.", enfe);
            }
            Pais idPais = gradoacademico.getIdPais();
            if (idPais != null) {
                idPais.getGradoacademicoList().remove(gradoacademico);
                idPais = em.merge(idPais);
            }
            Miembro idMiembro = gradoacademico.getIdMiembro();
            if (idMiembro != null) {
                idMiembro.getGradoacademicoList().remove(gradoacademico);
                idMiembro = em.merge(idMiembro);
            }
            em.remove(gradoacademico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Gradoacademico> findGradoacademicoEntities() {
        return findGradoacademicoEntities(true, -1, -1);
    }

    public List<Gradoacademico> findGradoacademicoEntities(int maxResults, int firstResult) {
        return findGradoacademicoEntities(false, maxResults, firstResult);
    }

    private List<Gradoacademico> findGradoacademicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Gradoacademico.class));
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

    public Gradoacademico findGradoacademico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Gradoacademico.class, id);
        } finally {
            em.close();
        }
    }

    public int getGradoacademicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Gradoacademico> rt = cq.from(Gradoacademico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
