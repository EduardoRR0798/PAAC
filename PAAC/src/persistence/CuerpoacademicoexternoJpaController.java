/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entity.Cuerpoacademicoexterno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Pais;
import entity.Participacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class CuerpoacademicoexternoJpaController implements Serializable {

    public CuerpoacademicoexternoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuerpoacademicoexterno cuerpoacademicoexterno) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais idPais = cuerpoacademicoexterno.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getIdPais());
                cuerpoacademicoexterno.setIdPais(idPais);
            }
            Participacion idParticipacion = cuerpoacademicoexterno.getIdParticipacion();
            if (idParticipacion != null) {
                idParticipacion = em.getReference(idParticipacion.getClass(), idParticipacion.getIdParticipacion());
                cuerpoacademicoexterno.setIdParticipacion(idParticipacion);
            }
            em.persist(cuerpoacademicoexterno);
            if (idPais != null) {
                idPais.getCuerpoacademicoexternoList().add(cuerpoacademicoexterno);
                idPais = em.merge(idPais);
            }
            if (idParticipacion != null) {
                idParticipacion.getCuerpoacademicoexternoList().add(cuerpoacademicoexterno);
                idParticipacion = em.merge(idParticipacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuerpoacademicoexterno cuerpoacademicoexterno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuerpoacademicoexterno persistentCuerpoacademicoexterno = em.find(Cuerpoacademicoexterno.class, cuerpoacademicoexterno.getIdCuerpoeExterno());
            Pais idPaisOld = persistentCuerpoacademicoexterno.getIdPais();
            Pais idPaisNew = cuerpoacademicoexterno.getIdPais();
            Participacion idParticipacionOld = persistentCuerpoacademicoexterno.getIdParticipacion();
            Participacion idParticipacionNew = cuerpoacademicoexterno.getIdParticipacion();
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getIdPais());
                cuerpoacademicoexterno.setIdPais(idPaisNew);
            }
            if (idParticipacionNew != null) {
                idParticipacionNew = em.getReference(idParticipacionNew.getClass(), idParticipacionNew.getIdParticipacion());
                cuerpoacademicoexterno.setIdParticipacion(idParticipacionNew);
            }
            cuerpoacademicoexterno = em.merge(cuerpoacademicoexterno);
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getCuerpoacademicoexternoList().remove(cuerpoacademicoexterno);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getCuerpoacademicoexternoList().add(cuerpoacademicoexterno);
                idPaisNew = em.merge(idPaisNew);
            }
            if (idParticipacionOld != null && !idParticipacionOld.equals(idParticipacionNew)) {
                idParticipacionOld.getCuerpoacademicoexternoList().remove(cuerpoacademicoexterno);
                idParticipacionOld = em.merge(idParticipacionOld);
            }
            if (idParticipacionNew != null && !idParticipacionNew.equals(idParticipacionOld)) {
                idParticipacionNew.getCuerpoacademicoexternoList().add(cuerpoacademicoexterno);
                idParticipacionNew = em.merge(idParticipacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuerpoacademicoexterno.getIdCuerpoeExterno();
                if (findCuerpoacademicoexterno(id) == null) {
                    throw new NonexistentEntityException("The cuerpoacademicoexterno with id " + id + " no longer exists.");
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
            Cuerpoacademicoexterno cuerpoacademicoexterno;
            try {
                cuerpoacademicoexterno = em.getReference(Cuerpoacademicoexterno.class, id);
                cuerpoacademicoexterno.getIdCuerpoeExterno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuerpoacademicoexterno with id " + id + " no longer exists.", enfe);
            }
            Pais idPais = cuerpoacademicoexterno.getIdPais();
            if (idPais != null) {
                idPais.getCuerpoacademicoexternoList().remove(cuerpoacademicoexterno);
                idPais = em.merge(idPais);
            }
            Participacion idParticipacion = cuerpoacademicoexterno.getIdParticipacion();
            if (idParticipacion != null) {
                idParticipacion.getCuerpoacademicoexternoList().remove(cuerpoacademicoexterno);
                idParticipacion = em.merge(idParticipacion);
            }
            em.remove(cuerpoacademicoexterno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuerpoacademicoexterno> findCuerpoacademicoexternoEntities() {
        return findCuerpoacademicoexternoEntities(true, -1, -1);
    }

    public List<Cuerpoacademicoexterno> findCuerpoacademicoexternoEntities(int maxResults, int firstResult) {
        return findCuerpoacademicoexternoEntities(false, maxResults, firstResult);
    }

    private List<Cuerpoacademicoexterno> findCuerpoacademicoexternoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuerpoacademicoexterno.class));
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

    public Cuerpoacademicoexterno findCuerpoacademicoexterno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuerpoacademicoexterno.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuerpoacademicoexternoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuerpoacademicoexterno> rt = cq.from(Cuerpoacademicoexterno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
