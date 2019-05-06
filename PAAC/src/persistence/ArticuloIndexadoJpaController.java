package persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Articulo;
import entity.ArticuloIndexado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduardo Rosas Rivera
 */
public class ArticuloIndexadoJpaController implements Serializable {

    public ArticuloIndexadoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArticuloIndexado articuloIndexado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulo idArticulo = articuloIndexado.getIdArticulo();
            if (idArticulo != null) {
                idArticulo = em.getReference(idArticulo.getClass(), idArticulo.getIdArticulo());
                articuloIndexado.setIdArticulo(idArticulo);
            }
            em.persist(articuloIndexado);
            if (idArticulo != null) {
                idArticulo.getArticuloIndexadoList().add(articuloIndexado);
                idArticulo = em.merge(idArticulo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ArticuloIndexado articuloIndexado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArticuloIndexado persistentArticuloIndexado = em.find(ArticuloIndexado.class, articuloIndexado.getIdArticuloIndexado());
            Articulo idArticuloOld = persistentArticuloIndexado.getIdArticulo();
            Articulo idArticuloNew = articuloIndexado.getIdArticulo();
            if (idArticuloNew != null) {
                idArticuloNew = em.getReference(idArticuloNew.getClass(), idArticuloNew.getIdArticulo());
                articuloIndexado.setIdArticulo(idArticuloNew);
            }
            articuloIndexado = em.merge(articuloIndexado);
            if (idArticuloOld != null && !idArticuloOld.equals(idArticuloNew)) {
                idArticuloOld.getArticuloIndexadoList().remove(articuloIndexado);
                idArticuloOld = em.merge(idArticuloOld);
            }
            if (idArticuloNew != null && !idArticuloNew.equals(idArticuloOld)) {
                idArticuloNew.getArticuloIndexadoList().add(articuloIndexado);
                idArticuloNew = em.merge(idArticuloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = articuloIndexado.getIdArticuloIndexado();
                if (findArticuloIndexado(id) == null) {
                    throw new NonexistentEntityException("The articuloIndexado with id " + id + " no longer exists.");
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
            ArticuloIndexado articuloIndexado;
            try {
                articuloIndexado = em.getReference(ArticuloIndexado.class, id);
                articuloIndexado.getIdArticuloIndexado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articuloIndexado with id " + id + " no longer exists.", enfe);
            }
            Articulo idArticulo = articuloIndexado.getIdArticulo();
            if (idArticulo != null) {
                idArticulo.getArticuloIndexadoList().remove(articuloIndexado);
                idArticulo = em.merge(idArticulo);
            }
            em.remove(articuloIndexado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ArticuloIndexado> findArticuloIndexadoEntities() {
        return findArticuloIndexadoEntities(true, -1, -1);
    }

    public List<ArticuloIndexado> findArticuloIndexadoEntities(int maxResults, int firstResult) {
        return findArticuloIndexadoEntities(false, maxResults, firstResult);
    }

    private List<ArticuloIndexado> findArticuloIndexadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArticuloIndexado.class));
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

    public ArticuloIndexado findArticuloIndexado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArticuloIndexado.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloIndexadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArticuloIndexado> rt = cq.from(ArticuloIndexado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
