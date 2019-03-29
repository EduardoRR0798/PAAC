/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entity.CapituloLibro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class CapituloLibroJpaController implements Serializable {

    public CapituloLibroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CapituloLibro capituloLibro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = capituloLibro.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                capituloLibro.setIdProducto(idProducto);
            }
            em.persist(capituloLibro);
            if (idProducto != null) {
                idProducto.getCapituloLibroList().add(capituloLibro);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CapituloLibro capituloLibro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CapituloLibro persistentCapituloLibro = em.find(CapituloLibro.class, capituloLibro.getIdCapitulolibro());
            Producto idProductoOld = persistentCapituloLibro.getIdProducto();
            Producto idProductoNew = capituloLibro.getIdProducto();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                capituloLibro.setIdProducto(idProductoNew);
            }
            capituloLibro = em.merge(capituloLibro);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getCapituloLibroList().remove(capituloLibro);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getCapituloLibroList().add(capituloLibro);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = capituloLibro.getIdCapitulolibro();
                if (findCapituloLibro(id) == null) {
                    throw new NonexistentEntityException("The capituloLibro with id " + id + " no longer exists.");
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
            CapituloLibro capituloLibro;
            try {
                capituloLibro = em.getReference(CapituloLibro.class, id);
                capituloLibro.getIdCapitulolibro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The capituloLibro with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = capituloLibro.getIdProducto();
            if (idProducto != null) {
                idProducto.getCapituloLibroList().remove(capituloLibro);
                idProducto = em.merge(idProducto);
            }
            em.remove(capituloLibro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CapituloLibro> findCapituloLibroEntities() {
        return findCapituloLibroEntities(true, -1, -1);
    }

    public List<CapituloLibro> findCapituloLibroEntities(int maxResults, int firstResult) {
        return findCapituloLibroEntities(false, maxResults, firstResult);
    }

    private List<CapituloLibro> findCapituloLibroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CapituloLibro.class));
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

    public CapituloLibro findCapituloLibro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CapituloLibro.class, id);
        } finally {
            em.close();
        }
    }

    public int getCapituloLibroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CapituloLibro> rt = cq.from(CapituloLibro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
