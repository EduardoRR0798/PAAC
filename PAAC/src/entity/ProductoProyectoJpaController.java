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
public class ProductoProyectoJpaController implements Serializable {

    public ProductoProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoProyecto productoProyecto) throws PreexistingEntityException, Exception {
        if (productoProyecto.getProductoProyectoPK() == null) {
            productoProyecto.setProductoProyectoPK(new ProductoProyectoPK());
        }
        productoProyecto.getProductoProyectoPK().setIdProyecto(productoProyecto.getProyecto().getIdProyecto());
        productoProyecto.getProductoProyectoPK().setIdProducto(productoProyecto.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto = productoProyecto.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                productoProyecto.setProducto(producto);
            }
            Proyecto proyecto = productoProyecto.getProyecto();
            if (proyecto != null) {
                proyecto = em.getReference(proyecto.getClass(), proyecto.getIdProyecto());
                productoProyecto.setProyecto(proyecto);
            }
            em.persist(productoProyecto);
            if (producto != null) {
                producto.getProductoProyectoCollection().add(productoProyecto);
                producto = em.merge(producto);
            }
            if (proyecto != null) {
                proyecto.getProductoProyectoCollection().add(productoProyecto);
                proyecto = em.merge(proyecto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductoProyecto(productoProyecto.getProductoProyectoPK()) != null) {
                throw new PreexistingEntityException("ProductoProyecto " + productoProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoProyecto productoProyecto) throws NonexistentEntityException, Exception {
        productoProyecto.getProductoProyectoPK().setIdProyecto(productoProyecto.getProyecto().getIdProyecto());
        productoProyecto.getProductoProyectoPK().setIdProducto(productoProyecto.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoProyecto persistentProductoProyecto = em.find(ProductoProyecto.class, productoProyecto.getProductoProyectoPK());
            Producto productoOld = persistentProductoProyecto.getProducto();
            Producto productoNew = productoProyecto.getProducto();
            Proyecto proyectoOld = persistentProductoProyecto.getProyecto();
            Proyecto proyectoNew = productoProyecto.getProyecto();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                productoProyecto.setProducto(productoNew);
            }
            if (proyectoNew != null) {
                proyectoNew = em.getReference(proyectoNew.getClass(), proyectoNew.getIdProyecto());
                productoProyecto.setProyecto(proyectoNew);
            }
            productoProyecto = em.merge(productoProyecto);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProductoProyectoCollection().remove(productoProyecto);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProductoProyectoCollection().add(productoProyecto);
                productoNew = em.merge(productoNew);
            }
            if (proyectoOld != null && !proyectoOld.equals(proyectoNew)) {
                proyectoOld.getProductoProyectoCollection().remove(productoProyecto);
                proyectoOld = em.merge(proyectoOld);
            }
            if (proyectoNew != null && !proyectoNew.equals(proyectoOld)) {
                proyectoNew.getProductoProyectoCollection().add(productoProyecto);
                proyectoNew = em.merge(proyectoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProductoProyectoPK id = productoProyecto.getProductoProyectoPK();
                if (findProductoProyecto(id) == null) {
                    throw new NonexistentEntityException("The productoProyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProductoProyectoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoProyecto productoProyecto;
            try {
                productoProyecto = em.getReference(ProductoProyecto.class, id);
                productoProyecto.getProductoProyectoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoProyecto with id " + id + " no longer exists.", enfe);
            }
            Producto producto = productoProyecto.getProducto();
            if (producto != null) {
                producto.getProductoProyectoCollection().remove(productoProyecto);
                producto = em.merge(producto);
            }
            Proyecto proyecto = productoProyecto.getProyecto();
            if (proyecto != null) {
                proyecto.getProductoProyectoCollection().remove(productoProyecto);
                proyecto = em.merge(proyecto);
            }
            em.remove(productoProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoProyecto> findProductoProyectoEntities() {
        return findProductoProyectoEntities(true, -1, -1);
    }

    public List<ProductoProyecto> findProductoProyectoEntities(int maxResults, int firstResult) {
        return findProductoProyectoEntities(false, maxResults, firstResult);
    }

    private List<ProductoProyecto> findProductoProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoProyecto.class));
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

    public ProductoProyecto findProductoProyecto(ProductoProyectoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoProyecto> rt = cq.from(ProductoProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
