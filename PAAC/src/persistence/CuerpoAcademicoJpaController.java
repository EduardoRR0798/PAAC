package persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import entity.Producto;
import entity.CuerpoAcademico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduardo Rosas Rivera
 */
public class CuerpoAcademicoJpaController implements Serializable {

    public CuerpoAcademicoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuerpoAcademico cuerpoAcademico) {
        if (cuerpoAcademico.getProductoList() == null) {
            cuerpoAcademico.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : cuerpoAcademico.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getIdProducto());
                attachedProductoList.add(productoListProductoToAttach);
            }
            cuerpoAcademico.setProductoList(attachedProductoList);
            em.persist(cuerpoAcademico);
            for (Producto productoListProducto : cuerpoAcademico.getProductoList()) {
                CuerpoAcademico oldIdCuerpoAcademicoOfProductoListProducto = productoListProducto.getIdCuerpoAcademico();
                productoListProducto.setIdCuerpoAcademico(cuerpoAcademico);
                productoListProducto = em.merge(productoListProducto);
                if (oldIdCuerpoAcademicoOfProductoListProducto != null) {
                    oldIdCuerpoAcademicoOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldIdCuerpoAcademicoOfProductoListProducto = em.merge(oldIdCuerpoAcademicoOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuerpoAcademico cuerpoAcademico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademico persistentCuerpoAcademico = em.find(CuerpoAcademico.class, cuerpoAcademico.getIdCuerpoAcademico());
            List<Producto> productoListOld = persistentCuerpoAcademico.getProductoList();
            List<Producto> productoListNew = cuerpoAcademico.getProductoList();
            List<String> illegalOrphanMessages = null;
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getIdProducto());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            cuerpoAcademico.setProductoList(productoListNew);
            cuerpoAcademico = em.merge(cuerpoAcademico);
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setIdCuerpoAcademico(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    CuerpoAcademico oldIdCuerpoAcademicoOfProductoListNewProducto = productoListNewProducto.getIdCuerpoAcademico();
                    productoListNewProducto.setIdCuerpoAcademico(cuerpoAcademico);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldIdCuerpoAcademicoOfProductoListNewProducto != null && !oldIdCuerpoAcademicoOfProductoListNewProducto.equals(cuerpoAcademico)) {
                        oldIdCuerpoAcademicoOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldIdCuerpoAcademicoOfProductoListNewProducto = em.merge(oldIdCuerpoAcademicoOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuerpoAcademico.getIdCuerpoAcademico();
                if (findCuerpoAcademico(id) == null) {
                    throw new NonexistentEntityException("The cuerpoAcademico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademico cuerpoAcademico;
            try {
                cuerpoAcademico = em.getReference(CuerpoAcademico.class, id);
                cuerpoAcademico.getIdCuerpoAcademico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuerpoAcademico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Producto> productoList = cuerpoAcademico.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setIdCuerpoAcademico(null);
                productoListProducto = em.merge(productoListProducto);
            }
            em.remove(cuerpoAcademico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuerpoAcademico> findCuerpoAcademicoEntities() {
        return findCuerpoAcademicoEntities(true, -1, -1);
    }

    public List<CuerpoAcademico> findCuerpoAcademicoEntities(int maxResults, int firstResult) {
        return findCuerpoAcademicoEntities(false, maxResults, firstResult);
    }

    private List<CuerpoAcademico> findCuerpoAcademicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuerpoAcademico.class));
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

    public CuerpoAcademico findCuerpoAcademico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuerpoAcademico.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuerpoAcademicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuerpoAcademico> rt = cq.from(CuerpoAcademico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
