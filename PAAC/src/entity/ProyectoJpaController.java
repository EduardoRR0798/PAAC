/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.exceptions.IllegalOrphanException;
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
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) {
        if (proyecto.getProductoProyectoCollection() == null) {
            proyecto.setProductoProyectoCollection(new ArrayList<ProductoProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lgac idLGACApoyo = proyecto.getIdLGACApoyo();
            if (idLGACApoyo != null) {
                idLGACApoyo = em.getReference(idLGACApoyo.getClass(), idLGACApoyo.getIdlgac());
                proyecto.setIdLGACApoyo(idLGACApoyo);
            }
            Collection<ProductoProyecto> attachedProductoProyectoCollection = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoCollectionProductoProyectoToAttach : proyecto.getProductoProyectoCollection()) {
                productoProyectoCollectionProductoProyectoToAttach = em.getReference(productoProyectoCollectionProductoProyectoToAttach.getClass(), productoProyectoCollectionProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoCollection.add(productoProyectoCollectionProductoProyectoToAttach);
            }
            proyecto.setProductoProyectoCollection(attachedProductoProyectoCollection);
            em.persist(proyecto);
            if (idLGACApoyo != null) {
                idLGACApoyo.getProyectoCollection().add(proyecto);
                idLGACApoyo = em.merge(idLGACApoyo);
            }
            for (ProductoProyecto productoProyectoCollectionProductoProyecto : proyecto.getProductoProyectoCollection()) {
                Proyecto oldProyectoOfProductoProyectoCollectionProductoProyecto = productoProyectoCollectionProductoProyecto.getProyecto();
                productoProyectoCollectionProductoProyecto.setProyecto(proyecto);
                productoProyectoCollectionProductoProyecto = em.merge(productoProyectoCollectionProductoProyecto);
                if (oldProyectoOfProductoProyectoCollectionProductoProyecto != null) {
                    oldProyectoOfProductoProyectoCollectionProductoProyecto.getProductoProyectoCollection().remove(productoProyectoCollectionProductoProyecto);
                    oldProyectoOfProductoProyectoCollectionProductoProyecto = em.merge(oldProyectoOfProductoProyectoCollectionProductoProyecto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getIdProyecto());
            Lgac idLGACApoyoOld = persistentProyecto.getIdLGACApoyo();
            Lgac idLGACApoyoNew = proyecto.getIdLGACApoyo();
            Collection<ProductoProyecto> productoProyectoCollectionOld = persistentProyecto.getProductoProyectoCollection();
            Collection<ProductoProyecto> productoProyectoCollectionNew = proyecto.getProductoProyectoCollection();
            List<String> illegalOrphanMessages = null;
            for (ProductoProyecto productoProyectoCollectionOldProductoProyecto : productoProyectoCollectionOld) {
                if (!productoProyectoCollectionNew.contains(productoProyectoCollectionOldProductoProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoProyecto " + productoProyectoCollectionOldProductoProyecto + " since its proyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idLGACApoyoNew != null) {
                idLGACApoyoNew = em.getReference(idLGACApoyoNew.getClass(), idLGACApoyoNew.getIdlgac());
                proyecto.setIdLGACApoyo(idLGACApoyoNew);
            }
            Collection<ProductoProyecto> attachedProductoProyectoCollectionNew = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoCollectionNewProductoProyectoToAttach : productoProyectoCollectionNew) {
                productoProyectoCollectionNewProductoProyectoToAttach = em.getReference(productoProyectoCollectionNewProductoProyectoToAttach.getClass(), productoProyectoCollectionNewProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoCollectionNew.add(productoProyectoCollectionNewProductoProyectoToAttach);
            }
            productoProyectoCollectionNew = attachedProductoProyectoCollectionNew;
            proyecto.setProductoProyectoCollection(productoProyectoCollectionNew);
            proyecto = em.merge(proyecto);
            if (idLGACApoyoOld != null && !idLGACApoyoOld.equals(idLGACApoyoNew)) {
                idLGACApoyoOld.getProyectoCollection().remove(proyecto);
                idLGACApoyoOld = em.merge(idLGACApoyoOld);
            }
            if (idLGACApoyoNew != null && !idLGACApoyoNew.equals(idLGACApoyoOld)) {
                idLGACApoyoNew.getProyectoCollection().add(proyecto);
                idLGACApoyoNew = em.merge(idLGACApoyoNew);
            }
            for (ProductoProyecto productoProyectoCollectionNewProductoProyecto : productoProyectoCollectionNew) {
                if (!productoProyectoCollectionOld.contains(productoProyectoCollectionNewProductoProyecto)) {
                    Proyecto oldProyectoOfProductoProyectoCollectionNewProductoProyecto = productoProyectoCollectionNewProductoProyecto.getProyecto();
                    productoProyectoCollectionNewProductoProyecto.setProyecto(proyecto);
                    productoProyectoCollectionNewProductoProyecto = em.merge(productoProyectoCollectionNewProductoProyecto);
                    if (oldProyectoOfProductoProyectoCollectionNewProductoProyecto != null && !oldProyectoOfProductoProyectoCollectionNewProductoProyecto.equals(proyecto)) {
                        oldProyectoOfProductoProyectoCollectionNewProductoProyecto.getProductoProyectoCollection().remove(productoProyectoCollectionNewProductoProyecto);
                        oldProyectoOfProductoProyectoCollectionNewProductoProyecto = em.merge(oldProyectoOfProductoProyectoCollectionNewProductoProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyecto.getIdProyecto();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getIdProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProductoProyecto> productoProyectoCollectionOrphanCheck = proyecto.getProductoProyectoCollection();
            for (ProductoProyecto productoProyectoCollectionOrphanCheckProductoProyecto : productoProyectoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the ProductoProyecto " + productoProyectoCollectionOrphanCheckProductoProyecto + " in its productoProyectoCollection field has a non-nullable proyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Lgac idLGACApoyo = proyecto.getIdLGACApoyo();
            if (idLGACApoyo != null) {
                idLGACApoyo.getProyectoCollection().remove(proyecto);
                idLGACApoyo = em.merge(idLGACApoyo);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
