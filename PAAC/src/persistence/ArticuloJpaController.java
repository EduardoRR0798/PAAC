package persistence;

import entity.Articulo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Producto;
import entity.ArticuloIndexado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduardo Rosas Rivera
 */
public class ArticuloJpaController implements Serializable {

    public ArticuloJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulo articulo) {
        if (articulo.getArticuloIndexadoList() == null) {
            articulo.setArticuloIndexadoList(new ArrayList<ArticuloIndexado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = articulo.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                articulo.setIdProducto(idProducto);
            }
            List<ArticuloIndexado> attachedArticuloIndexadoList = new ArrayList<ArticuloIndexado>();
            for (ArticuloIndexado articuloIndexadoListArticuloIndexadoToAttach : articulo.getArticuloIndexadoList()) {
                articuloIndexadoListArticuloIndexadoToAttach = em.getReference(articuloIndexadoListArticuloIndexadoToAttach.getClass(), articuloIndexadoListArticuloIndexadoToAttach.getIdArticuloIndexado());
                attachedArticuloIndexadoList.add(articuloIndexadoListArticuloIndexadoToAttach);
            }
            articulo.setArticuloIndexadoList(attachedArticuloIndexadoList);
            em.persist(articulo);
            if (idProducto != null) {
                idProducto.getArticuloList().add(articulo);
                idProducto = em.merge(idProducto);
            }
            for (ArticuloIndexado articuloIndexadoListArticuloIndexado : articulo.getArticuloIndexadoList()) {
                Articulo oldIdArticuloOfArticuloIndexadoListArticuloIndexado = articuloIndexadoListArticuloIndexado.getIdArticulo();
                articuloIndexadoListArticuloIndexado.setIdArticulo(articulo);
                articuloIndexadoListArticuloIndexado = em.merge(articuloIndexadoListArticuloIndexado);
                if (oldIdArticuloOfArticuloIndexadoListArticuloIndexado != null) {
                    oldIdArticuloOfArticuloIndexadoListArticuloIndexado.getArticuloIndexadoList().remove(articuloIndexadoListArticuloIndexado);
                    oldIdArticuloOfArticuloIndexadoListArticuloIndexado = em.merge(oldIdArticuloOfArticuloIndexadoListArticuloIndexado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulo articulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulo persistentArticulo = em.find(Articulo.class, articulo.getIdArticulo());
            Producto idProductoOld = persistentArticulo.getIdProducto();
            Producto idProductoNew = articulo.getIdProducto();
            List<ArticuloIndexado> articuloIndexadoListOld = persistentArticulo.getArticuloIndexadoList();
            List<ArticuloIndexado> articuloIndexadoListNew = articulo.getArticuloIndexadoList();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                articulo.setIdProducto(idProductoNew);
            }
            List<ArticuloIndexado> attachedArticuloIndexadoListNew = new ArrayList<ArticuloIndexado>();
            for (ArticuloIndexado articuloIndexadoListNewArticuloIndexadoToAttach : articuloIndexadoListNew) {
                articuloIndexadoListNewArticuloIndexadoToAttach = em.getReference(articuloIndexadoListNewArticuloIndexadoToAttach.getClass(), articuloIndexadoListNewArticuloIndexadoToAttach.getIdArticuloIndexado());
                attachedArticuloIndexadoListNew.add(articuloIndexadoListNewArticuloIndexadoToAttach);
            }
            articuloIndexadoListNew = attachedArticuloIndexadoListNew;
            articulo.setArticuloIndexadoList(articuloIndexadoListNew);
            articulo = em.merge(articulo);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getArticuloList().remove(articulo);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getArticuloList().add(articulo);
                idProductoNew = em.merge(idProductoNew);
            }
            for (ArticuloIndexado articuloIndexadoListOldArticuloIndexado : articuloIndexadoListOld) {
                if (!articuloIndexadoListNew.contains(articuloIndexadoListOldArticuloIndexado)) {
                    articuloIndexadoListOldArticuloIndexado.setIdArticulo(null);
                    articuloIndexadoListOldArticuloIndexado = em.merge(articuloIndexadoListOldArticuloIndexado);
                }
            }
            for (ArticuloIndexado articuloIndexadoListNewArticuloIndexado : articuloIndexadoListNew) {
                if (!articuloIndexadoListOld.contains(articuloIndexadoListNewArticuloIndexado)) {
                    Articulo oldIdArticuloOfArticuloIndexadoListNewArticuloIndexado = articuloIndexadoListNewArticuloIndexado.getIdArticulo();
                    articuloIndexadoListNewArticuloIndexado.setIdArticulo(articulo);
                    articuloIndexadoListNewArticuloIndexado = em.merge(articuloIndexadoListNewArticuloIndexado);
                    if (oldIdArticuloOfArticuloIndexadoListNewArticuloIndexado != null && !oldIdArticuloOfArticuloIndexadoListNewArticuloIndexado.equals(articulo)) {
                        oldIdArticuloOfArticuloIndexadoListNewArticuloIndexado.getArticuloIndexadoList().remove(articuloIndexadoListNewArticuloIndexado);
                        oldIdArticuloOfArticuloIndexadoListNewArticuloIndexado = em.merge(oldIdArticuloOfArticuloIndexadoListNewArticuloIndexado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = articulo.getIdArticulo();
                if (findArticulo(id) == null) {
                    throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.");
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
            Articulo articulo;
            try {
                articulo = em.getReference(Articulo.class, id);
                articulo.getIdArticulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = articulo.getIdProducto();
            if (idProducto != null) {
                idProducto.getArticuloList().remove(articulo);
                idProducto = em.merge(idProducto);
            }
            List<ArticuloIndexado> articuloIndexadoList = articulo.getArticuloIndexadoList();
            for (ArticuloIndexado articuloIndexadoListArticuloIndexado : articuloIndexadoList) {
                articuloIndexadoListArticuloIndexado.setIdArticulo(null);
                articuloIndexadoListArticuloIndexado = em.merge(articuloIndexadoListArticuloIndexado);
            }
            em.remove(articulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulo> findArticuloEntities() {
        return findArticuloEntities(true, -1, -1);
    }

    public List<Articulo> findArticuloEntities(int maxResults, int firstResult) {
        return findArticuloEntities(false, maxResults, firstResult);
    }

    private List<Articulo> findArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulo.class));
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

    public Articulo findArticulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulo> rt = cq.from(Articulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Recupera un articulo por el id del producto.
     * @param id (int) id del producto.
     * @return Articulo correspondiente al id del producto.
     */
    public Articulo encontrarArticuloPorIdProducto(Producto id) {
        Articulo art;
        try {
            EntityManager em = getEntityManager();
            Query q = em.createNamedQuery("Articulo.findByIdProducto", Articulo.class).setParameter("idProducto", id);
            art = (Articulo) q.getSingleResult();
        } catch (Exception e) {
            
            art = null;
        }
        return art;
    }
    
}
