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
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getProductoCollection() == null) {
            pais.setProductoCollection(new ArrayList<Producto>());
        }
        if (pais.getCuerpoacademicoexternoCollection() == null) {
            pais.setCuerpoacademicoexternoCollection(new ArrayList<Cuerpoacademicoexterno>());
        }
        if (pais.getGradoacademicoCollection() == null) {
            pais.setGradoacademicoCollection(new ArrayList<Gradoacademico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : pais.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getIdProducto());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            pais.setProductoCollection(attachedProductoCollection);
            Collection<Cuerpoacademicoexterno> attachedCuerpoacademicoexternoCollection = new ArrayList<Cuerpoacademicoexterno>();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach : pais.getCuerpoacademicoexternoCollection()) {
                cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach = em.getReference(cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach.getClass(), cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach.getIdCuerpoeExterno());
                attachedCuerpoacademicoexternoCollection.add(cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach);
            }
            pais.setCuerpoacademicoexternoCollection(attachedCuerpoacademicoexternoCollection);
            Collection<Gradoacademico> attachedGradoacademicoCollection = new ArrayList<Gradoacademico>();
            for (Gradoacademico gradoacademicoCollectionGradoacademicoToAttach : pais.getGradoacademicoCollection()) {
                gradoacademicoCollectionGradoacademicoToAttach = em.getReference(gradoacademicoCollectionGradoacademicoToAttach.getClass(), gradoacademicoCollectionGradoacademicoToAttach.getIdGradoAcademico());
                attachedGradoacademicoCollection.add(gradoacademicoCollectionGradoacademicoToAttach);
            }
            pais.setGradoacademicoCollection(attachedGradoacademicoCollection);
            em.persist(pais);
            for (Producto productoCollectionProducto : pais.getProductoCollection()) {
                Pais oldIdPaisOfProductoCollectionProducto = productoCollectionProducto.getIdPais();
                productoCollectionProducto.setIdPais(pais);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldIdPaisOfProductoCollectionProducto != null) {
                    oldIdPaisOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldIdPaisOfProductoCollectionProducto = em.merge(oldIdPaisOfProductoCollectionProducto);
                }
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionCuerpoacademicoexterno : pais.getCuerpoacademicoexternoCollection()) {
                Pais oldIdPaisOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno = cuerpoacademicoexternoCollectionCuerpoacademicoexterno.getIdPais();
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno.setIdPais(pais);
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionCuerpoacademicoexterno);
                if (oldIdPaisOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno != null) {
                    oldIdPaisOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno.getCuerpoacademicoexternoCollection().remove(cuerpoacademicoexternoCollectionCuerpoacademicoexterno);
                    oldIdPaisOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno = em.merge(oldIdPaisOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno);
                }
            }
            for (Gradoacademico gradoacademicoCollectionGradoacademico : pais.getGradoacademicoCollection()) {
                Pais oldIdPaisOfGradoacademicoCollectionGradoacademico = gradoacademicoCollectionGradoacademico.getIdPais();
                gradoacademicoCollectionGradoacademico.setIdPais(pais);
                gradoacademicoCollectionGradoacademico = em.merge(gradoacademicoCollectionGradoacademico);
                if (oldIdPaisOfGradoacademicoCollectionGradoacademico != null) {
                    oldIdPaisOfGradoacademicoCollectionGradoacademico.getGradoacademicoCollection().remove(gradoacademicoCollectionGradoacademico);
                    oldIdPaisOfGradoacademicoCollectionGradoacademico = em.merge(oldIdPaisOfGradoacademicoCollectionGradoacademico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdPais());
            Collection<Producto> productoCollectionOld = persistentPais.getProductoCollection();
            Collection<Producto> productoCollectionNew = pais.getProductoCollection();
            Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollectionOld = persistentPais.getCuerpoacademicoexternoCollection();
            Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollectionNew = pais.getCuerpoacademicoexternoCollection();
            Collection<Gradoacademico> gradoacademicoCollectionOld = persistentPais.getGradoacademicoCollection();
            Collection<Gradoacademico> gradoacademicoCollectionNew = pais.getGradoacademicoCollection();
            List<String> illegalOrphanMessages = null;
            for (Gradoacademico gradoacademicoCollectionOldGradoacademico : gradoacademicoCollectionOld) {
                if (!gradoacademicoCollectionNew.contains(gradoacademicoCollectionOldGradoacademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gradoacademico " + gradoacademicoCollectionOldGradoacademico + " since its idPais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getIdProducto());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            pais.setProductoCollection(productoCollectionNew);
            Collection<Cuerpoacademicoexterno> attachedCuerpoacademicoexternoCollectionNew = new ArrayList<Cuerpoacademicoexterno>();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach : cuerpoacademicoexternoCollectionNew) {
                cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach = em.getReference(cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach.getClass(), cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach.getIdCuerpoeExterno());
                attachedCuerpoacademicoexternoCollectionNew.add(cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach);
            }
            cuerpoacademicoexternoCollectionNew = attachedCuerpoacademicoexternoCollectionNew;
            pais.setCuerpoacademicoexternoCollection(cuerpoacademicoexternoCollectionNew);
            Collection<Gradoacademico> attachedGradoacademicoCollectionNew = new ArrayList<Gradoacademico>();
            for (Gradoacademico gradoacademicoCollectionNewGradoacademicoToAttach : gradoacademicoCollectionNew) {
                gradoacademicoCollectionNewGradoacademicoToAttach = em.getReference(gradoacademicoCollectionNewGradoacademicoToAttach.getClass(), gradoacademicoCollectionNewGradoacademicoToAttach.getIdGradoAcademico());
                attachedGradoacademicoCollectionNew.add(gradoacademicoCollectionNewGradoacademicoToAttach);
            }
            gradoacademicoCollectionNew = attachedGradoacademicoCollectionNew;
            pais.setGradoacademicoCollection(gradoacademicoCollectionNew);
            pais = em.merge(pais);
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    productoCollectionOldProducto.setIdPais(null);
                    productoCollectionOldProducto = em.merge(productoCollectionOldProducto);
                }
            }
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Pais oldIdPaisOfProductoCollectionNewProducto = productoCollectionNewProducto.getIdPais();
                    productoCollectionNewProducto.setIdPais(pais);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldIdPaisOfProductoCollectionNewProducto != null && !oldIdPaisOfProductoCollectionNewProducto.equals(pais)) {
                        oldIdPaisOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldIdPaisOfProductoCollectionNewProducto = em.merge(oldIdPaisOfProductoCollectionNewProducto);
                    }
                }
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno : cuerpoacademicoexternoCollectionOld) {
                if (!cuerpoacademicoexternoCollectionNew.contains(cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno)) {
                    cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno.setIdPais(null);
                    cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno);
                }
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno : cuerpoacademicoexternoCollectionNew) {
                if (!cuerpoacademicoexternoCollectionOld.contains(cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno)) {
                    Pais oldIdPaisOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno = cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.getIdPais();
                    cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.setIdPais(pais);
                    cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno);
                    if (oldIdPaisOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno != null && !oldIdPaisOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.equals(pais)) {
                        oldIdPaisOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.getCuerpoacademicoexternoCollection().remove(cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno);
                        oldIdPaisOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno = em.merge(oldIdPaisOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno);
                    }
                }
            }
            for (Gradoacademico gradoacademicoCollectionNewGradoacademico : gradoacademicoCollectionNew) {
                if (!gradoacademicoCollectionOld.contains(gradoacademicoCollectionNewGradoacademico)) {
                    Pais oldIdPaisOfGradoacademicoCollectionNewGradoacademico = gradoacademicoCollectionNewGradoacademico.getIdPais();
                    gradoacademicoCollectionNewGradoacademico.setIdPais(pais);
                    gradoacademicoCollectionNewGradoacademico = em.merge(gradoacademicoCollectionNewGradoacademico);
                    if (oldIdPaisOfGradoacademicoCollectionNewGradoacademico != null && !oldIdPaisOfGradoacademicoCollectionNewGradoacademico.equals(pais)) {
                        oldIdPaisOfGradoacademicoCollectionNewGradoacademico.getGradoacademicoCollection().remove(gradoacademicoCollectionNewGradoacademico);
                        oldIdPaisOfGradoacademicoCollectionNewGradoacademico = em.merge(oldIdPaisOfGradoacademicoCollectionNewGradoacademico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getIdPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Gradoacademico> gradoacademicoCollectionOrphanCheck = pais.getGradoacademicoCollection();
            for (Gradoacademico gradoacademicoCollectionOrphanCheckGradoacademico : gradoacademicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Gradoacademico " + gradoacademicoCollectionOrphanCheckGradoacademico + " in its gradoacademicoCollection field has a non-nullable idPais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Producto> productoCollection = pais.getProductoCollection();
            for (Producto productoCollectionProducto : productoCollection) {
                productoCollectionProducto.setIdPais(null);
                productoCollectionProducto = em.merge(productoCollectionProducto);
            }
            Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollection = pais.getCuerpoacademicoexternoCollection();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionCuerpoacademicoexterno : cuerpoacademicoexternoCollection) {
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno.setIdPais(null);
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionCuerpoacademicoexterno);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
