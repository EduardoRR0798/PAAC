/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Eduar
 */
public class ArticuloJpaController implements Serializable {

    public ArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulo articulo) {
        if (articulo.getArticuloIndexadoCollection() == null) {
            articulo.setArticuloIndexadoCollection(new ArrayList<ArticuloIndexado>());
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
            Collection<ArticuloIndexado> attachedArticuloIndexadoCollection = new ArrayList<ArticuloIndexado>();
            for (ArticuloIndexado articuloIndexadoCollectionArticuloIndexadoToAttach : articulo.getArticuloIndexadoCollection()) {
                articuloIndexadoCollectionArticuloIndexadoToAttach = em.getReference(articuloIndexadoCollectionArticuloIndexadoToAttach.getClass(), articuloIndexadoCollectionArticuloIndexadoToAttach.getIdArticuloIndexado());
                attachedArticuloIndexadoCollection.add(articuloIndexadoCollectionArticuloIndexadoToAttach);
            }
            articulo.setArticuloIndexadoCollection(attachedArticuloIndexadoCollection);
            em.persist(articulo);
            if (idProducto != null) {
                idProducto.getArticuloCollection().add(articulo);
                idProducto = em.merge(idProducto);
            }
            for (ArticuloIndexado articuloIndexadoCollectionArticuloIndexado : articulo.getArticuloIndexadoCollection()) {
                Articulo oldIdArticuloOfArticuloIndexadoCollectionArticuloIndexado = articuloIndexadoCollectionArticuloIndexado.getIdArticulo();
                articuloIndexadoCollectionArticuloIndexado.setIdArticulo(articulo);
                articuloIndexadoCollectionArticuloIndexado = em.merge(articuloIndexadoCollectionArticuloIndexado);
                if (oldIdArticuloOfArticuloIndexadoCollectionArticuloIndexado != null) {
                    oldIdArticuloOfArticuloIndexadoCollectionArticuloIndexado.getArticuloIndexadoCollection().remove(articuloIndexadoCollectionArticuloIndexado);
                    oldIdArticuloOfArticuloIndexadoCollectionArticuloIndexado = em.merge(oldIdArticuloOfArticuloIndexadoCollectionArticuloIndexado);
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
            Collection<ArticuloIndexado> articuloIndexadoCollectionOld = persistentArticulo.getArticuloIndexadoCollection();
            Collection<ArticuloIndexado> articuloIndexadoCollectionNew = articulo.getArticuloIndexadoCollection();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                articulo.setIdProducto(idProductoNew);
            }
            Collection<ArticuloIndexado> attachedArticuloIndexadoCollectionNew = new ArrayList<ArticuloIndexado>();
            for (ArticuloIndexado articuloIndexadoCollectionNewArticuloIndexadoToAttach : articuloIndexadoCollectionNew) {
                articuloIndexadoCollectionNewArticuloIndexadoToAttach = em.getReference(articuloIndexadoCollectionNewArticuloIndexadoToAttach.getClass(), articuloIndexadoCollectionNewArticuloIndexadoToAttach.getIdArticuloIndexado());
                attachedArticuloIndexadoCollectionNew.add(articuloIndexadoCollectionNewArticuloIndexadoToAttach);
            }
            articuloIndexadoCollectionNew = attachedArticuloIndexadoCollectionNew;
            articulo.setArticuloIndexadoCollection(articuloIndexadoCollectionNew);
            articulo = em.merge(articulo);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getArticuloCollection().remove(articulo);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getArticuloCollection().add(articulo);
                idProductoNew = em.merge(idProductoNew);
            }
            for (ArticuloIndexado articuloIndexadoCollectionOldArticuloIndexado : articuloIndexadoCollectionOld) {
                if (!articuloIndexadoCollectionNew.contains(articuloIndexadoCollectionOldArticuloIndexado)) {
                    articuloIndexadoCollectionOldArticuloIndexado.setIdArticulo(null);
                    articuloIndexadoCollectionOldArticuloIndexado = em.merge(articuloIndexadoCollectionOldArticuloIndexado);
                }
            }
            for (ArticuloIndexado articuloIndexadoCollectionNewArticuloIndexado : articuloIndexadoCollectionNew) {
                if (!articuloIndexadoCollectionOld.contains(articuloIndexadoCollectionNewArticuloIndexado)) {
                    Articulo oldIdArticuloOfArticuloIndexadoCollectionNewArticuloIndexado = articuloIndexadoCollectionNewArticuloIndexado.getIdArticulo();
                    articuloIndexadoCollectionNewArticuloIndexado.setIdArticulo(articulo);
                    articuloIndexadoCollectionNewArticuloIndexado = em.merge(articuloIndexadoCollectionNewArticuloIndexado);
                    if (oldIdArticuloOfArticuloIndexadoCollectionNewArticuloIndexado != null && !oldIdArticuloOfArticuloIndexadoCollectionNewArticuloIndexado.equals(articulo)) {
                        oldIdArticuloOfArticuloIndexadoCollectionNewArticuloIndexado.getArticuloIndexadoCollection().remove(articuloIndexadoCollectionNewArticuloIndexado);
                        oldIdArticuloOfArticuloIndexadoCollectionNewArticuloIndexado = em.merge(oldIdArticuloOfArticuloIndexadoCollectionNewArticuloIndexado);
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
                idProducto.getArticuloCollection().remove(articulo);
                idProducto = em.merge(idProducto);
            }
            Collection<ArticuloIndexado> articuloIndexadoCollection = articulo.getArticuloIndexadoCollection();
            for (ArticuloIndexado articuloIndexadoCollectionArticuloIndexado : articuloIndexadoCollection) {
                articuloIndexadoCollectionArticuloIndexado.setIdArticulo(null);
                articuloIndexadoCollectionArticuloIndexado = em.merge(articuloIndexadoCollectionArticuloIndexado);
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
    
}
