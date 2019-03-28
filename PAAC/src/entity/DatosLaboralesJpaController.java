/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Eduar
 */
public class DatosLaboralesJpaController implements Serializable {

    public DatosLaboralesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatosLaborales datosLaborales) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembro idMiembro = datosLaborales.getIdMiembro();
            if (idMiembro != null) {
                idMiembro = em.getReference(idMiembro.getClass(), idMiembro.getIdMiembro());
                datosLaborales.setIdMiembro(idMiembro);
            }
            em.persist(datosLaborales);
            if (idMiembro != null) {
                idMiembro.getDatosLaboralesCollection().add(datosLaborales);
                idMiembro = em.merge(idMiembro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatosLaborales datosLaborales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DatosLaborales persistentDatosLaborales = em.find(DatosLaborales.class, datosLaborales.getIdDatosLaborales());
            Miembro idMiembroOld = persistentDatosLaborales.getIdMiembro();
            Miembro idMiembroNew = datosLaborales.getIdMiembro();
            if (idMiembroNew != null) {
                idMiembroNew = em.getReference(idMiembroNew.getClass(), idMiembroNew.getIdMiembro());
                datosLaborales.setIdMiembro(idMiembroNew);
            }
            datosLaborales = em.merge(datosLaborales);
            if (idMiembroOld != null && !idMiembroOld.equals(idMiembroNew)) {
                idMiembroOld.getDatosLaboralesCollection().remove(datosLaborales);
                idMiembroOld = em.merge(idMiembroOld);
            }
            if (idMiembroNew != null && !idMiembroNew.equals(idMiembroOld)) {
                idMiembroNew.getDatosLaboralesCollection().add(datosLaborales);
                idMiembroNew = em.merge(idMiembroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datosLaborales.getIdDatosLaborales();
                if (findDatosLaborales(id) == null) {
                    throw new NonexistentEntityException("The datosLaborales with id " + id + " no longer exists.");
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
            DatosLaborales datosLaborales;
            try {
                datosLaborales = em.getReference(DatosLaborales.class, id);
                datosLaborales.getIdDatosLaborales();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datosLaborales with id " + id + " no longer exists.", enfe);
            }
            Miembro idMiembro = datosLaborales.getIdMiembro();
            if (idMiembro != null) {
                idMiembro.getDatosLaboralesCollection().remove(datosLaborales);
                idMiembro = em.merge(idMiembro);
            }
            em.remove(datosLaborales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatosLaborales> findDatosLaboralesEntities() {
        return findDatosLaboralesEntities(true, -1, -1);
    }

    public List<DatosLaborales> findDatosLaboralesEntities(int maxResults, int firstResult) {
        return findDatosLaboralesEntities(false, maxResults, firstResult);
    }

    private List<DatosLaborales> findDatosLaboralesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatosLaborales.class));
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

    public DatosLaborales findDatosLaborales(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosLaborales.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatosLaboralesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatosLaborales> rt = cq.from(DatosLaborales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
