/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entity.Miembro;
import entity.Patente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class PatenteJpaController implements Serializable {

    public PatenteJpaController() {
         this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Patente patente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = patente.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                patente.setIdProducto(idProducto);
            }
            em.persist(patente);
            if (idProducto != null) {
                idProducto.getPatenteList().add(patente);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Patente patente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Patente persistentPatente = em.find(Patente.class, patente.getIdPatente());
            Producto idProductoOld = persistentPatente.getIdProducto();
            Producto idProductoNew = patente.getIdProducto();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                patente.setIdProducto(idProductoNew);
            }
            patente = em.merge(patente);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getPatenteList().remove(patente);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getPatenteList().add(patente);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = patente.getIdPatente();
                if (findPatente(id) == null) {
                    throw new NonexistentEntityException("The patente with id " + id + " no longer exists.");
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
            Patente patente;
            try {
                patente = em.getReference(Patente.class, id);
                patente.getIdPatente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The patente with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = patente.getIdProducto();
            if (idProducto != null) {
                idProducto.getPatenteList().remove(patente);
                idProducto = em.merge(idProducto);
            }
            em.remove(patente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Patente> findPatenteEntities() {
        return findPatenteEntities(true, -1, -1);
    }

    public List<Patente> findPatenteEntities(int maxResults, int firstResult) {
        return findPatenteEntities(false, maxResults, firstResult);
    }

    private List<Patente> findPatenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Patente.class));
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

    public Patente findPatente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Patente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPatenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Patente> rt = cq.from(Patente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
  public Patente findByIdProducto(Producto p) {
        EntityManager em = getEntityManager();
        Patente pa = em.createNamedQuery("Patente.findByIdProducto", Patente.class).setParameter("idProducto", p).getSingleResult();
        return pa;
    }
       public Patente buscarPatenteByIdProducto(Producto id) {
        Patente pati;
        try {
            EntityManager em = getEntityManager();
            Query q = em.createNamedQuery("Patente.findByIdProducto", Patente.class).setParameter("idProducto", id);
            pati = (Patente) q.getSingleResult();
        } catch (Exception e) {
            pati  = null;
        
        }
        return pati;
    }
}
