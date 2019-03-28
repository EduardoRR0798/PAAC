/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.NonexistentEntityException;
import entity.exceptions.PreexistingEntityException;
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
public class ProductoColaboradorJpaController implements Serializable {

    public ProductoColaboradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoColaborador productoColaborador) throws PreexistingEntityException, Exception {
        if (productoColaborador.getProductoColaboradorPK() == null) {
            productoColaborador.setProductoColaboradorPK(new ProductoColaboradorPK());
        }
        productoColaborador.getProductoColaboradorPK().setIdProducto(productoColaborador.getProducto().getIdProducto());
        productoColaborador.getProductoColaboradorPK().setIdColaborador(productoColaborador.getColaborador().getIdColaborador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = productoColaborador.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                productoColaborador.setProducto(producto);
            }
            Colaborador colaborador = productoColaborador.getColaborador();
            if (colaborador != null) {
                colaborador = em.getReference(colaborador.getClass(), colaborador.getIdColaborador());
                productoColaborador.setColaborador(colaborador);
            }
            em.persist(productoColaborador);
            if (producto != null) {
                producto.getProductoColaboradorCollection().add(productoColaborador);
                producto = em.merge(producto);
            }
            if (colaborador != null) {
                colaborador.getProductoColaboradorCollection().add(productoColaborador);
                colaborador = em.merge(colaborador);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductoColaborador(productoColaborador.getProductoColaboradorPK()) != null) {
                throw new PreexistingEntityException("ProductoColaborador " + productoColaborador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoColaborador productoColaborador) throws NonexistentEntityException, Exception {
        productoColaborador.getProductoColaboradorPK().setIdProducto(productoColaborador.getProducto().getIdProducto());
        productoColaborador.getProductoColaboradorPK().setIdColaborador(productoColaborador.getColaborador().getIdColaborador());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoColaborador persistentProductoColaborador = em.find(ProductoColaborador.class, productoColaborador.getProductoColaboradorPK());
            Producto productoOld = persistentProductoColaborador.getProducto();
            Producto productoNew = productoColaborador.getProducto();
            Colaborador colaboradorOld = persistentProductoColaborador.getColaborador();
            Colaborador colaboradorNew = productoColaborador.getColaborador();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                productoColaborador.setProducto(productoNew);
            }
            if (colaboradorNew != null) {
                colaboradorNew = em.getReference(colaboradorNew.getClass(), colaboradorNew.getIdColaborador());
                productoColaborador.setColaborador(colaboradorNew);
            }
            productoColaborador = em.merge(productoColaborador);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProductoColaboradorCollection().remove(productoColaborador);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProductoColaboradorCollection().add(productoColaborador);
                productoNew = em.merge(productoNew);
            }
            if (colaboradorOld != null && !colaboradorOld.equals(colaboradorNew)) {
                colaboradorOld.getProductoColaboradorCollection().remove(productoColaborador);
                colaboradorOld = em.merge(colaboradorOld);
            }
            if (colaboradorNew != null && !colaboradorNew.equals(colaboradorOld)) {
                colaboradorNew.getProductoColaboradorCollection().add(productoColaborador);
                colaboradorNew = em.merge(colaboradorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProductoColaboradorPK id = productoColaborador.getProductoColaboradorPK();
                if (findProductoColaborador(id) == null) {
                    throw new NonexistentEntityException("The productoColaborador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProductoColaboradorPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoColaborador productoColaborador;
            try {
                productoColaborador = em.getReference(ProductoColaborador.class, id);
                productoColaborador.getProductoColaboradorPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoColaborador with id " + id + " no longer exists.", enfe);
            }
            Producto producto = productoColaborador.getProducto();
            if (producto != null) {
                producto.getProductoColaboradorCollection().remove(productoColaborador);
                producto = em.merge(producto);
            }
            Colaborador colaborador = productoColaborador.getColaborador();
            if (colaborador != null) {
                colaborador.getProductoColaboradorCollection().remove(productoColaborador);
                colaborador = em.merge(colaborador);
            }
            em.remove(productoColaborador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoColaborador> findProductoColaboradorEntities() {
        return findProductoColaboradorEntities(true, -1, -1);
    }

    public List<ProductoColaborador> findProductoColaboradorEntities(int maxResults, int firstResult) {
        return findProductoColaboradorEntities(false, maxResults, firstResult);
    }

    private List<ProductoColaborador> findProductoColaboradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoColaborador.class));
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

    public ProductoColaborador findProductoColaborador(ProductoColaboradorPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoColaborador.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoColaboradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoColaborador> rt = cq.from(ProductoColaborador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
