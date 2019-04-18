package persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Miembro;
import entity.Producto;
import entity.ProductoMiembro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduardo Rosas Rivera.
 */
public class ProductoMiembroJpaController implements Serializable {

    public ProductoMiembroJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoMiembro productoMiembro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembro idMiembro = productoMiembro.getIdMiembro();
            if (idMiembro != null) {
                idMiembro = em.getReference(idMiembro.getClass(), idMiembro.getIdMiembro());
                productoMiembro.setIdMiembro(idMiembro);
            }
            Producto idProducto = productoMiembro.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                productoMiembro.setIdProducto(idProducto);
            }
            em.persist(productoMiembro);
            if (idMiembro != null) {
                idMiembro.getProductoMiembroList().add(productoMiembro);
                idMiembro = em.merge(idMiembro);
            }
            if (idProducto != null) {
                idProducto.getProductoMiembroList().add(productoMiembro);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoMiembro productoMiembro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoMiembro persistentProductoMiembro = em.find(ProductoMiembro.class, productoMiembro.getIdMiembroProducto());
            Miembro idMiembroOld = persistentProductoMiembro.getIdMiembro();
            Miembro idMiembroNew = productoMiembro.getIdMiembro();
            Producto idProductoOld = persistentProductoMiembro.getIdProducto();
            Producto idProductoNew = productoMiembro.getIdProducto();
            if (idMiembroNew != null) {
                idMiembroNew = em.getReference(idMiembroNew.getClass(), idMiembroNew.getIdMiembro());
                productoMiembro.setIdMiembro(idMiembroNew);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                productoMiembro.setIdProducto(idProductoNew);
            }
            productoMiembro = em.merge(productoMiembro);
            if (idMiembroOld != null && !idMiembroOld.equals(idMiembroNew)) {
                idMiembroOld.getProductoMiembroList().remove(productoMiembro);
                idMiembroOld = em.merge(idMiembroOld);
            }
            if (idMiembroNew != null && !idMiembroNew.equals(idMiembroOld)) {
                idMiembroNew.getProductoMiembroList().add(productoMiembro);
                idMiembroNew = em.merge(idMiembroNew);
            }
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getProductoMiembroList().remove(productoMiembro);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getProductoMiembroList().add(productoMiembro);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productoMiembro.getIdMiembroProducto();
                if (findProductoMiembro(id) == null) {
                    throw new NonexistentEntityException("The productoMiembro with id " + id + " no longer exists.");
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
            ProductoMiembro productoMiembro;
            try {
                productoMiembro = em.getReference(ProductoMiembro.class, id);
                productoMiembro.getIdMiembroProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoMiembro with id " + id + " no longer exists.", enfe);
            }
            Miembro idMiembro = productoMiembro.getIdMiembro();
            if (idMiembro != null) {
                idMiembro.getProductoMiembroList().remove(productoMiembro);
                idMiembro = em.merge(idMiembro);
            }
            Producto idProducto = productoMiembro.getIdProducto();
            if (idProducto != null) {
                idProducto.getProductoMiembroList().remove(productoMiembro);
                idProducto = em.merge(idProducto);
            }
            em.remove(productoMiembro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoMiembro> findProductoMiembroEntities() {
        return findProductoMiembroEntities(true, -1, -1);
    }

    public List<ProductoMiembro> findProductoMiembroEntities(int maxResults, int firstResult) {
        return findProductoMiembroEntities(false, maxResults, firstResult);
    }

    private List<ProductoMiembro> findProductoMiembroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoMiembro.class));
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

    public ProductoMiembro findProductoMiembro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoMiembro.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoMiembroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoMiembro> rt = cq.from(ProductoMiembro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Recupera una lista de ProductoMiembro correspondientes a 
     * @param id
     * @return 
     */
    public List<ProductoMiembro> findByIdMiembro(Integer id) {
        List<ProductoMiembro> ls;
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("ProductoMiembro.findByIdMiembroProducto", ProductoMiembro.class).setParameter("idMiembroProducto", id);
        ls = q.getResultList();
        return ls;
    }
    
    /**
     * Recupera TODOS los ProductoMiembro que correspondan a un mismo producto.
     * @param pro Producto.
     * @return Lista de ProductoMiembro de un mismo Producto.
     */
    public List<ProductoMiembro> findByIdProducto(Producto pro) {
        List<ProductoMiembro> ls;
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("ProductoMiembro.findByIdProducto", ProductoMiembro.class).setParameter("idProducto", pro);
        ls = q.getResultList();
        return ls;
    }
    
    /**
     * Recupera un ProductoMiembro en especifico.
     * @param m id del Miembro.
     * @param p id del producto.
     * @return El ProductoMiembro esperado.
     */
    public ProductoMiembro findByIdPM(Integer m, Integer p) {
        EntityManager em = getEntityManager();
        ProductoMiembro pm;
        Query q = em.createNamedQuery("ProductoMiembro.findByProductoAndMiembro", ProductoMiembro.class).setParameter("idMiembro", m).setParameter("idProducto", p);
        pm = (ProductoMiembro) q.getSingleResult();
        return pm;
    }
}