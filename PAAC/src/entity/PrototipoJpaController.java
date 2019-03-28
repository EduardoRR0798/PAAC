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
public class PrototipoJpaController implements Serializable {

    public PrototipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Prototipo prototipo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = prototipo.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                prototipo.setIdProducto(idProducto);
            }
            em.persist(prototipo);
            if (idProducto != null) {
                idProducto.getPrototipoCollection().add(prototipo);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Prototipo prototipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Prototipo persistentPrototipo = em.find(Prototipo.class, prototipo.getIdprototipo());
            Producto idProductoOld = persistentPrototipo.getIdProducto();
            Producto idProductoNew = prototipo.getIdProducto();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                prototipo.setIdProducto(idProductoNew);
            }
            prototipo = em.merge(prototipo);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getPrototipoCollection().remove(prototipo);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getPrototipoCollection().add(prototipo);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = prototipo.getIdprototipo();
                if (findPrototipo(id) == null) {
                    throw new NonexistentEntityException("The prototipo with id " + id + " no longer exists.");
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
            Prototipo prototipo;
            try {
                prototipo = em.getReference(Prototipo.class, id);
                prototipo.getIdprototipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prototipo with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = prototipo.getIdProducto();
            if (idProducto != null) {
                idProducto.getPrototipoCollection().remove(prototipo);
                idProducto = em.merge(idProducto);
            }
            em.remove(prototipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Prototipo> findPrototipoEntities() {
        return findPrototipoEntities(true, -1, -1);
    }

    public List<Prototipo> findPrototipoEntities(int maxResults, int firstResult) {
        return findPrototipoEntities(false, maxResults, firstResult);
    }

    private List<Prototipo> findPrototipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Prototipo.class));
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

    public Prototipo findPrototipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Prototipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrototipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Prototipo> rt = cq.from(Prototipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
