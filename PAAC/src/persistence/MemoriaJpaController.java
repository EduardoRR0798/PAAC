package persistence;

import entity.Memoria;
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
 * @author Eduardo Rosas Rvera
 */
public class MemoriaJpaController implements Serializable {

    public MemoriaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Memoria memoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = memoria.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                memoria.setIdProducto(idProducto);
            }
            em.persist(memoria);
            if (idProducto != null) {
                idProducto.getMemoriaList().add(memoria);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Memoria memoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Memoria persistentMemoria = em.find(Memoria.class, memoria.getIdMemoria());
            Producto idProductoOld = persistentMemoria.getIdProducto();
            Producto idProductoNew = memoria.getIdProducto();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                memoria.setIdProducto(idProductoNew);
            }
            memoria = em.merge(memoria);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getMemoriaList().remove(memoria);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getMemoriaList().add(memoria);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = memoria.getIdMemoria();
                if (findMemoria(id) == null) {
                    throw new NonexistentEntityException("The memoria with id " + id + " no longer exists.");
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
            Memoria memoria;
            try {
                memoria = em.getReference(Memoria.class, id);
                memoria.getIdMemoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The memoria with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = memoria.getIdProducto();
            if (idProducto != null) {
                idProducto.getMemoriaList().remove(memoria);
                idProducto = em.merge(idProducto);
            }
            em.remove(memoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Memoria> findMemoriaEntities() {
        return findMemoriaEntities(true, -1, -1);
    }

    public List<Memoria> findMemoriaEntities(int maxResults, int firstResult) {
        return findMemoriaEntities(false, maxResults, firstResult);
    }

    private List<Memoria> findMemoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Memoria.class));
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

    public Memoria findMemoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Memoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getMemoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Memoria> rt = cq.from(Memoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Memoria findByIdProducto(Producto p) {
        EntityManager em = getEntityManager();
        Memoria m = em.createNamedQuery("Memoria.findByIdProducto", Memoria.class).setParameter("idProducto", p).getSingleResult();
        return m;
    }
    
    /**
     * Recupera una memoria por el id del producto.
     * @param id (int) id del producto.
     * @return Memoria correspondiente al id del producto.
     */
    public Memoria encontrarMemoriaPorIdProducto(Producto id) {
        Memoria memo;
        try {
            EntityManager em = getEntityManager();
            Query q = em.createNamedQuery("Memoria.findByIdProducto", Memoria.class).setParameter("idProducto", id);
            memo = (Memoria) q.getSingleResult();
        } catch (Exception e) {
            memo = null;
        }
        return memo;
    }
}
