/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Lgac;
import entity.ProductoProyecto;
import entity.Proyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) {
        if (proyecto.getProductoProyectoList() == null) {
            proyecto.setProductoProyectoList(new ArrayList<ProductoProyecto>());
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
            List<ProductoProyecto> attachedProductoProyectoList = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoListProductoProyectoToAttach : proyecto.getProductoProyectoList()) {
                productoProyectoListProductoProyectoToAttach = em.getReference(productoProyectoListProductoProyectoToAttach.getClass(), productoProyectoListProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoList.add(productoProyectoListProductoProyectoToAttach);
            }
            proyecto.setProductoProyectoList(attachedProductoProyectoList);
            em.persist(proyecto);
            if (idLGACApoyo != null) {
                idLGACApoyo.getProyectoList().add(proyecto);
                idLGACApoyo = em.merge(idLGACApoyo);
            }
            for (ProductoProyecto productoProyectoListProductoProyecto : proyecto.getProductoProyectoList()) {
                Proyecto oldProyectoOfProductoProyectoListProductoProyecto = productoProyectoListProductoProyecto.getProyecto();
                productoProyectoListProductoProyecto.setProyecto(proyecto);
                productoProyectoListProductoProyecto = em.merge(productoProyectoListProductoProyecto);
                if (oldProyectoOfProductoProyectoListProductoProyecto != null) {
                    oldProyectoOfProductoProyectoListProductoProyecto.getProductoProyectoList().remove(productoProyectoListProductoProyecto);
                    oldProyectoOfProductoProyectoListProductoProyecto = em.merge(oldProyectoOfProductoProyectoListProductoProyecto);
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
            List<ProductoProyecto> productoProyectoListOld = persistentProyecto.getProductoProyectoList();
            List<ProductoProyecto> productoProyectoListNew = proyecto.getProductoProyectoList();
            List<String> illegalOrphanMessages = null;
            for (ProductoProyecto productoProyectoListOldProductoProyecto : productoProyectoListOld) {
                if (!productoProyectoListNew.contains(productoProyectoListOldProductoProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoProyecto " + productoProyectoListOldProductoProyecto + " since its proyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idLGACApoyoNew != null) {
                idLGACApoyoNew = em.getReference(idLGACApoyoNew.getClass(), idLGACApoyoNew.getIdlgac());
                proyecto.setIdLGACApoyo(idLGACApoyoNew);
            }
            List<ProductoProyecto> attachedProductoProyectoListNew = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoListNewProductoProyectoToAttach : productoProyectoListNew) {
                productoProyectoListNewProductoProyectoToAttach = em.getReference(productoProyectoListNewProductoProyectoToAttach.getClass(), productoProyectoListNewProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoListNew.add(productoProyectoListNewProductoProyectoToAttach);
            }
            productoProyectoListNew = attachedProductoProyectoListNew;
            proyecto.setProductoProyectoList(productoProyectoListNew);
            proyecto = em.merge(proyecto);
            if (idLGACApoyoOld != null && !idLGACApoyoOld.equals(idLGACApoyoNew)) {
                idLGACApoyoOld.getProyectoList().remove(proyecto);
                idLGACApoyoOld = em.merge(idLGACApoyoOld);
            }
            if (idLGACApoyoNew != null && !idLGACApoyoNew.equals(idLGACApoyoOld)) {
                idLGACApoyoNew.getProyectoList().add(proyecto);
                idLGACApoyoNew = em.merge(idLGACApoyoNew);
            }
            for (ProductoProyecto productoProyectoListNewProductoProyecto : productoProyectoListNew) {
                if (!productoProyectoListOld.contains(productoProyectoListNewProductoProyecto)) {
                    Proyecto oldProyectoOfProductoProyectoListNewProductoProyecto = productoProyectoListNewProductoProyecto.getProyecto();
                    productoProyectoListNewProductoProyecto.setProyecto(proyecto);
                    productoProyectoListNewProductoProyecto = em.merge(productoProyectoListNewProductoProyecto);
                    if (oldProyectoOfProductoProyectoListNewProductoProyecto != null && !oldProyectoOfProductoProyectoListNewProductoProyecto.equals(proyecto)) {
                        oldProyectoOfProductoProyectoListNewProductoProyecto.getProductoProyectoList().remove(productoProyectoListNewProductoProyecto);
                        oldProyectoOfProductoProyectoListNewProductoProyecto = em.merge(oldProyectoOfProductoProyectoListNewProductoProyecto);
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
            List<ProductoProyecto> productoProyectoListOrphanCheck = proyecto.getProductoProyectoList();
            for (ProductoProyecto productoProyectoListOrphanCheckProductoProyecto : productoProyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the ProductoProyecto " + productoProyectoListOrphanCheckProductoProyecto + " in its productoProyectoList field has a non-nullable proyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Lgac idLGACApoyo = proyecto.getIdLGACApoyo();
            if (idLGACApoyo != null) {
                idLGACApoyo.getProyectoList().remove(proyecto);
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
