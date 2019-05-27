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
import entity.Producto;
import entity.Tesis;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class TesisJpaController implements Serializable {

    public TesisJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tesis tesis) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = tesis.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                tesis.setIdProducto(idProducto);
            }
            em.persist(tesis);
            if (idProducto != null) {
                idProducto.getTesisList().add(tesis);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tesis tesis) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tesis persistentTesis = em.find(Tesis.class, tesis.getIdTesis());
            Producto idProductoOld = persistentTesis.getIdProducto();
            Producto idProductoNew = tesis.getIdProducto();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                tesis.setIdProducto(idProductoNew);
            }
            tesis = em.merge(tesis);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getTesisList().remove(tesis);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getTesisList().add(tesis);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tesis.getIdTesis();
                if (findTesis(id) == null) {
                    throw new NonexistentEntityException("The tesis with id " + id + " no longer exists.");
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
            Tesis tesis;
            try {
                tesis = em.getReference(Tesis.class, id);
                tesis.getIdTesis();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tesis with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = tesis.getIdProducto();
            if (idProducto != null) {
                idProducto.getTesisList().remove(tesis);
                idProducto = em.merge(idProducto);
            }
            em.remove(tesis);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tesis> findTesisEntities() {
        return findTesisEntities(true, -1, -1);
    }

    public List<Tesis> findTesisEntities(int maxResults, int firstResult) {
        return findTesisEntities(false, maxResults, firstResult);
    }

    private List<Tesis> findTesisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tesis.class));
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

    public Tesis findTesis(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tesis.class, id);
        } finally {
            em.close();
        }
    }

    public int getTesisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tesis> rt = cq.from(Tesis.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Tesis encontrarTesisPorIdProducto(Producto id) {
        Tesis t;
        try {
            EntityManager em = getEntityManager();
            Query q = em.createNamedQuery("Tesis.findByIdProducto", Tesis.class).setParameter("idProducto", id);
            t = (Tesis) q.getSingleResult();
        } catch (Exception e) {
            t = null;
        }
        return t;
    }
}
