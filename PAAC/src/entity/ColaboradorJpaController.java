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
public class ColaboradorJpaController implements Serializable {

    public ColaboradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Colaborador colaborador) {
        if (colaborador.getProductoColaboradorCollection() == null) {
            colaborador.setProductoColaboradorCollection(new ArrayList<ProductoColaborador>());
        }
        if (colaborador.getColaboradorCuerpoacademicoCollection() == null) {
            colaborador.setColaboradorCuerpoacademicoCollection(new ArrayList<ColaboradorCuerpoacademico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ProductoColaborador> attachedProductoColaboradorCollection = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorCollectionProductoColaboradorToAttach : colaborador.getProductoColaboradorCollection()) {
                productoColaboradorCollectionProductoColaboradorToAttach = em.getReference(productoColaboradorCollectionProductoColaboradorToAttach.getClass(), productoColaboradorCollectionProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorCollection.add(productoColaboradorCollectionProductoColaboradorToAttach);
            }
            colaborador.setProductoColaboradorCollection(attachedProductoColaboradorCollection);
            Collection<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoCollection = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach : colaborador.getColaboradorCuerpoacademicoCollection()) {
                colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoCollection.add(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach);
            }
            colaborador.setColaboradorCuerpoacademicoCollection(attachedColaboradorCuerpoacademicoCollection);
            em.persist(colaborador);
            for (ProductoColaborador productoColaboradorCollectionProductoColaborador : colaborador.getProductoColaboradorCollection()) {
                Colaborador oldColaboradorOfProductoColaboradorCollectionProductoColaborador = productoColaboradorCollectionProductoColaborador.getColaborador();
                productoColaboradorCollectionProductoColaborador.setColaborador(colaborador);
                productoColaboradorCollectionProductoColaborador = em.merge(productoColaboradorCollectionProductoColaborador);
                if (oldColaboradorOfProductoColaboradorCollectionProductoColaborador != null) {
                    oldColaboradorOfProductoColaboradorCollectionProductoColaborador.getProductoColaboradorCollection().remove(productoColaboradorCollectionProductoColaborador);
                    oldColaboradorOfProductoColaboradorCollectionProductoColaborador = em.merge(oldColaboradorOfProductoColaboradorCollectionProductoColaborador);
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico : colaborador.getColaboradorCuerpoacademicoCollection()) {
                Colaborador oldColaboradorOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico = colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico.getColaborador();
                colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico.setColaborador(colaborador);
                colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico);
                if (oldColaboradorOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico != null) {
                    oldColaboradorOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico.getColaboradorCuerpoacademicoCollection().remove(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico);
                    oldColaboradorOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico = em.merge(oldColaboradorOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Colaborador colaborador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Colaborador persistentColaborador = em.find(Colaborador.class, colaborador.getIdColaborador());
            Collection<ProductoColaborador> productoColaboradorCollectionOld = persistentColaborador.getProductoColaboradorCollection();
            Collection<ProductoColaborador> productoColaboradorCollectionNew = colaborador.getProductoColaboradorCollection();
            Collection<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoCollectionOld = persistentColaborador.getColaboradorCuerpoacademicoCollection();
            Collection<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoCollectionNew = colaborador.getColaboradorCuerpoacademicoCollection();
            List<String> illegalOrphanMessages = null;
            for (ProductoColaborador productoColaboradorCollectionOldProductoColaborador : productoColaboradorCollectionOld) {
                if (!productoColaboradorCollectionNew.contains(productoColaboradorCollectionOldProductoColaborador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoColaborador " + productoColaboradorCollectionOldProductoColaborador + " since its colaborador field is not nullable.");
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionOldColaboradorCuerpoacademico : colaboradorCuerpoacademicoCollectionOld) {
                if (!colaboradorCuerpoacademicoCollectionNew.contains(colaboradorCuerpoacademicoCollectionOldColaboradorCuerpoacademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoCollectionOldColaboradorCuerpoacademico + " since its colaborador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ProductoColaborador> attachedProductoColaboradorCollectionNew = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorCollectionNewProductoColaboradorToAttach : productoColaboradorCollectionNew) {
                productoColaboradorCollectionNewProductoColaboradorToAttach = em.getReference(productoColaboradorCollectionNewProductoColaboradorToAttach.getClass(), productoColaboradorCollectionNewProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorCollectionNew.add(productoColaboradorCollectionNewProductoColaboradorToAttach);
            }
            productoColaboradorCollectionNew = attachedProductoColaboradorCollectionNew;
            colaborador.setProductoColaboradorCollection(productoColaboradorCollectionNew);
            Collection<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoCollectionNew = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach : colaboradorCuerpoacademicoCollectionNew) {
                colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoCollectionNew.add(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach);
            }
            colaboradorCuerpoacademicoCollectionNew = attachedColaboradorCuerpoacademicoCollectionNew;
            colaborador.setColaboradorCuerpoacademicoCollection(colaboradorCuerpoacademicoCollectionNew);
            colaborador = em.merge(colaborador);
            for (ProductoColaborador productoColaboradorCollectionNewProductoColaborador : productoColaboradorCollectionNew) {
                if (!productoColaboradorCollectionOld.contains(productoColaboradorCollectionNewProductoColaborador)) {
                    Colaborador oldColaboradorOfProductoColaboradorCollectionNewProductoColaborador = productoColaboradorCollectionNewProductoColaborador.getColaborador();
                    productoColaboradorCollectionNewProductoColaborador.setColaborador(colaborador);
                    productoColaboradorCollectionNewProductoColaborador = em.merge(productoColaboradorCollectionNewProductoColaborador);
                    if (oldColaboradorOfProductoColaboradorCollectionNewProductoColaborador != null && !oldColaboradorOfProductoColaboradorCollectionNewProductoColaborador.equals(colaborador)) {
                        oldColaboradorOfProductoColaboradorCollectionNewProductoColaborador.getProductoColaboradorCollection().remove(productoColaboradorCollectionNewProductoColaborador);
                        oldColaboradorOfProductoColaboradorCollectionNewProductoColaborador = em.merge(oldColaboradorOfProductoColaboradorCollectionNewProductoColaborador);
                    }
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico : colaboradorCuerpoacademicoCollectionNew) {
                if (!colaboradorCuerpoacademicoCollectionOld.contains(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico)) {
                    Colaborador oldColaboradorOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico = colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.getColaborador();
                    colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.setColaborador(colaborador);
                    colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico);
                    if (oldColaboradorOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico != null && !oldColaboradorOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.equals(colaborador)) {
                        oldColaboradorOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.getColaboradorCuerpoacademicoCollection().remove(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico);
                        oldColaboradorOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico = em.merge(oldColaboradorOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = colaborador.getIdColaborador();
                if (findColaborador(id) == null) {
                    throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.");
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
            Colaborador colaborador;
            try {
                colaborador = em.getReference(Colaborador.class, id);
                colaborador.getIdColaborador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProductoColaborador> productoColaboradorCollectionOrphanCheck = colaborador.getProductoColaboradorCollection();
            for (ProductoColaborador productoColaboradorCollectionOrphanCheckProductoColaborador : productoColaboradorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Colaborador (" + colaborador + ") cannot be destroyed since the ProductoColaborador " + productoColaboradorCollectionOrphanCheckProductoColaborador + " in its productoColaboradorCollection field has a non-nullable colaborador field.");
            }
            Collection<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoCollectionOrphanCheck = colaborador.getColaboradorCuerpoacademicoCollection();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionOrphanCheckColaboradorCuerpoacademico : colaboradorCuerpoacademicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Colaborador (" + colaborador + ") cannot be destroyed since the ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoCollectionOrphanCheckColaboradorCuerpoacademico + " in its colaboradorCuerpoacademicoCollection field has a non-nullable colaborador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(colaborador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Colaborador> findColaboradorEntities() {
        return findColaboradorEntities(true, -1, -1);
    }

    public List<Colaborador> findColaboradorEntities(int maxResults, int firstResult) {
        return findColaboradorEntities(false, maxResults, firstResult);
    }

    private List<Colaborador> findColaboradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colaborador.class));
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

    public Colaborador findColaborador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Colaborador.class, id);
        } finally {
            em.close();
        }
    }

    public int getColaboradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colaborador> rt = cq.from(Colaborador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
