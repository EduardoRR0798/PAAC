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
import entity.Colaborador;
import entity.ColaboradorCuerpoacademico;
import entity.ColaboradorCuerpoacademicoPK;
import entity.CuerpoAcademico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.NonexistentEntityException;
import persistence.exceptions.PreexistingEntityException;

/**
 *
 * @author Eduar
 */
public class ColaboradorCuerpoacademicoJpaController implements Serializable {

    public ColaboradorCuerpoacademicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ColaboradorCuerpoacademico colaboradorCuerpoacademico) throws PreexistingEntityException, Exception {
        if (colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK() == null) {
            colaboradorCuerpoacademico.setColaboradorCuerpoacademicoPK(new ColaboradorCuerpoacademicoPK());
        }
        colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK().setIdCA(colaboradorCuerpoacademico.getCuerpoAcademico().getIdCuerpoAcademico());
        colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK().setIdColaborador(colaboradorCuerpoacademico.getColaborador().getIdColaborador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Colaborador colaborador = colaboradorCuerpoacademico.getColaborador();
            if (colaborador != null) {
                colaborador = em.getReference(colaborador.getClass(), colaborador.getIdColaborador());
                colaboradorCuerpoacademico.setColaborador(colaborador);
            }
            CuerpoAcademico cuerpoAcademico = colaboradorCuerpoacademico.getCuerpoAcademico();
            if (cuerpoAcademico != null) {
                cuerpoAcademico = em.getReference(cuerpoAcademico.getClass(), cuerpoAcademico.getIdCuerpoAcademico());
                colaboradorCuerpoacademico.setCuerpoAcademico(cuerpoAcademico);
            }
            em.persist(colaboradorCuerpoacademico);
            if (colaborador != null) {
                colaborador.getColaboradorCuerpoacademicoList().add(colaboradorCuerpoacademico);
                colaborador = em.merge(colaborador);
            }
            if (cuerpoAcademico != null) {
                cuerpoAcademico.getColaboradorCuerpoacademicoList().add(colaboradorCuerpoacademico);
                cuerpoAcademico = em.merge(cuerpoAcademico);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findColaboradorCuerpoacademico(colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK()) != null) {
                throw new PreexistingEntityException("ColaboradorCuerpoacademico " + colaboradorCuerpoacademico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ColaboradorCuerpoacademico colaboradorCuerpoacademico) throws NonexistentEntityException, Exception {
        colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK().setIdCA(colaboradorCuerpoacademico.getCuerpoAcademico().getIdCuerpoAcademico());
        colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK().setIdColaborador(colaboradorCuerpoacademico.getColaborador().getIdColaborador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ColaboradorCuerpoacademico persistentColaboradorCuerpoacademico = em.find(ColaboradorCuerpoacademico.class, colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK());
            Colaborador colaboradorOld = persistentColaboradorCuerpoacademico.getColaborador();
            Colaborador colaboradorNew = colaboradorCuerpoacademico.getColaborador();
            CuerpoAcademico cuerpoAcademicoOld = persistentColaboradorCuerpoacademico.getCuerpoAcademico();
            CuerpoAcademico cuerpoAcademicoNew = colaboradorCuerpoacademico.getCuerpoAcademico();
            if (colaboradorNew != null) {
                colaboradorNew = em.getReference(colaboradorNew.getClass(), colaboradorNew.getIdColaborador());
                colaboradorCuerpoacademico.setColaborador(colaboradorNew);
            }
            if (cuerpoAcademicoNew != null) {
                cuerpoAcademicoNew = em.getReference(cuerpoAcademicoNew.getClass(), cuerpoAcademicoNew.getIdCuerpoAcademico());
                colaboradorCuerpoacademico.setCuerpoAcademico(cuerpoAcademicoNew);
            }
            colaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademico);
            if (colaboradorOld != null && !colaboradorOld.equals(colaboradorNew)) {
                colaboradorOld.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademico);
                colaboradorOld = em.merge(colaboradorOld);
            }
            if (colaboradorNew != null && !colaboradorNew.equals(colaboradorOld)) {
                colaboradorNew.getColaboradorCuerpoacademicoList().add(colaboradorCuerpoacademico);
                colaboradorNew = em.merge(colaboradorNew);
            }
            if (cuerpoAcademicoOld != null && !cuerpoAcademicoOld.equals(cuerpoAcademicoNew)) {
                cuerpoAcademicoOld.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademico);
                cuerpoAcademicoOld = em.merge(cuerpoAcademicoOld);
            }
            if (cuerpoAcademicoNew != null && !cuerpoAcademicoNew.equals(cuerpoAcademicoOld)) {
                cuerpoAcademicoNew.getColaboradorCuerpoacademicoList().add(colaboradorCuerpoacademico);
                cuerpoAcademicoNew = em.merge(cuerpoAcademicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ColaboradorCuerpoacademicoPK id = colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK();
                if (findColaboradorCuerpoacademico(id) == null) {
                    throw new NonexistentEntityException("The colaboradorCuerpoacademico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ColaboradorCuerpoacademicoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ColaboradorCuerpoacademico colaboradorCuerpoacademico;
            try {
                colaboradorCuerpoacademico = em.getReference(ColaboradorCuerpoacademico.class, id);
                colaboradorCuerpoacademico.getColaboradorCuerpoacademicoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colaboradorCuerpoacademico with id " + id + " no longer exists.", enfe);
            }
            Colaborador colaborador = colaboradorCuerpoacademico.getColaborador();
            if (colaborador != null) {
                colaborador.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademico);
                colaborador = em.merge(colaborador);
            }
            CuerpoAcademico cuerpoAcademico = colaboradorCuerpoacademico.getCuerpoAcademico();
            if (cuerpoAcademico != null) {
                cuerpoAcademico.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademico);
                cuerpoAcademico = em.merge(cuerpoAcademico);
            }
            em.remove(colaboradorCuerpoacademico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ColaboradorCuerpoacademico> findColaboradorCuerpoacademicoEntities() {
        return findColaboradorCuerpoacademicoEntities(true, -1, -1);
    }

    public List<ColaboradorCuerpoacademico> findColaboradorCuerpoacademicoEntities(int maxResults, int firstResult) {
        return findColaboradorCuerpoacademicoEntities(false, maxResults, firstResult);
    }

    private List<ColaboradorCuerpoacademico> findColaboradorCuerpoacademicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ColaboradorCuerpoacademico.class));
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

    public ColaboradorCuerpoacademico findColaboradorCuerpoacademico(ColaboradorCuerpoacademicoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ColaboradorCuerpoacademico.class, id);
        } finally {
            em.close();
        }
    }

    public int getColaboradorCuerpoacademicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ColaboradorCuerpoacademico> rt = cq.from(ColaboradorCuerpoacademico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
