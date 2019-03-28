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
public class GradoacademicoJpaController implements Serializable {

    public GradoacademicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Gradoacademico gradoacademico) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Miembro miembroOrphanCheck = gradoacademico.getMiembro();
        if (miembroOrphanCheck != null) {
            Gradoacademico oldGradoacademicoOfMiembro = miembroOrphanCheck.getGradoacademico();
            if (oldGradoacademicoOfMiembro != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Miembro " + miembroOrphanCheck + " already has an item of type Gradoacademico whose miembro column cannot be null. Please make another selection for the miembro field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais idPais = gradoacademico.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getIdPais());
                gradoacademico.setIdPais(idPais);
            }
            Miembro miembro = gradoacademico.getMiembro();
            if (miembro != null) {
                miembro = em.getReference(miembro.getClass(), miembro.getIdMiembro());
                gradoacademico.setMiembro(miembro);
            }
            em.persist(gradoacademico);
            if (idPais != null) {
                idPais.getGradoacademicoCollection().add(gradoacademico);
                idPais = em.merge(idPais);
            }
            if (miembro != null) {
                miembro.setGradoacademico(gradoacademico);
                miembro = em.merge(miembro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Gradoacademico gradoacademico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gradoacademico persistentGradoacademico = em.find(Gradoacademico.class, gradoacademico.getIdGradoAcademico());
            Pais idPaisOld = persistentGradoacademico.getIdPais();
            Pais idPaisNew = gradoacademico.getIdPais();
            Miembro miembroOld = persistentGradoacademico.getMiembro();
            Miembro miembroNew = gradoacademico.getMiembro();
            List<String> illegalOrphanMessages = null;
            if (miembroNew != null && !miembroNew.equals(miembroOld)) {
                Gradoacademico oldGradoacademicoOfMiembro = miembroNew.getGradoacademico();
                if (oldGradoacademicoOfMiembro != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Miembro " + miembroNew + " already has an item of type Gradoacademico whose miembro column cannot be null. Please make another selection for the miembro field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getIdPais());
                gradoacademico.setIdPais(idPaisNew);
            }
            if (miembroNew != null) {
                miembroNew = em.getReference(miembroNew.getClass(), miembroNew.getIdMiembro());
                gradoacademico.setMiembro(miembroNew);
            }
            gradoacademico = em.merge(gradoacademico);
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getGradoacademicoCollection().remove(gradoacademico);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getGradoacademicoCollection().add(gradoacademico);
                idPaisNew = em.merge(idPaisNew);
            }
            if (miembroOld != null && !miembroOld.equals(miembroNew)) {
                miembroOld.setGradoacademico(null);
                miembroOld = em.merge(miembroOld);
            }
            if (miembroNew != null && !miembroNew.equals(miembroOld)) {
                miembroNew.setGradoacademico(gradoacademico);
                miembroNew = em.merge(miembroNew);
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
                idPais.getGradoacademicoCollection().remove(gradoacademico);
                idPais = em.merge(idPais);
            }
            Miembro miembro = gradoacademico.getMiembro();
            if (miembro != null) {
                miembro.setGradoacademico(null);
                miembro = em.merge(miembro);
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
