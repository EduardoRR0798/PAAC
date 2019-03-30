package persistence;

import entity.Colaborador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.ProductoColaborador;
import java.util.ArrayList;
import java.util.List;
import entity.ColaboradorCuerpoacademico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class ColaboradorJpaController implements Serializable {

    public ColaboradorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Colaborador colaborador) {
        if (colaborador.getProductoColaboradorList() == null) {
            colaborador.setProductoColaboradorList(new ArrayList<ProductoColaborador>());
        }
        if (colaborador.getColaboradorCuerpoacademicoList() == null) {
            colaborador.setColaboradorCuerpoacademicoList(new ArrayList<ColaboradorCuerpoacademico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProductoColaborador> attachedProductoColaboradorList = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorListProductoColaboradorToAttach : colaborador.getProductoColaboradorList()) {
                productoColaboradorListProductoColaboradorToAttach = em.getReference(productoColaboradorListProductoColaboradorToAttach.getClass(), productoColaboradorListProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorList.add(productoColaboradorListProductoColaboradorToAttach);
            }
            colaborador.setProductoColaboradorList(attachedProductoColaboradorList);
            List<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoList = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach : colaborador.getColaboradorCuerpoacademicoList()) {
                colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoList.add(colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach);
            }
            colaborador.setColaboradorCuerpoacademicoList(attachedColaboradorCuerpoacademicoList);
            em.persist(colaborador);
            for (ProductoColaborador productoColaboradorListProductoColaborador : colaborador.getProductoColaboradorList()) {
                Colaborador oldColaboradorOfProductoColaboradorListProductoColaborador = productoColaboradorListProductoColaborador.getColaborador();
                productoColaboradorListProductoColaborador.setColaborador(colaborador);
                productoColaboradorListProductoColaborador = em.merge(productoColaboradorListProductoColaborador);
                if (oldColaboradorOfProductoColaboradorListProductoColaborador != null) {
                    oldColaboradorOfProductoColaboradorListProductoColaborador.getProductoColaboradorList().remove(productoColaboradorListProductoColaborador);
                    oldColaboradorOfProductoColaboradorListProductoColaborador = em.merge(oldColaboradorOfProductoColaboradorListProductoColaborador);
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListColaboradorCuerpoacademico : colaborador.getColaboradorCuerpoacademicoList()) {
                Colaborador oldColaboradorOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico = colaboradorCuerpoacademicoListColaboradorCuerpoacademico.getColaborador();
                colaboradorCuerpoacademicoListColaboradorCuerpoacademico.setColaborador(colaborador);
                colaboradorCuerpoacademicoListColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoListColaboradorCuerpoacademico);
                if (oldColaboradorOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico != null) {
                    oldColaboradorOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademicoListColaboradorCuerpoacademico);
                    oldColaboradorOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico = em.merge(oldColaboradorOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico);
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
            List<ProductoColaborador> productoColaboradorListOld = persistentColaborador.getProductoColaboradorList();
            List<ProductoColaborador> productoColaboradorListNew = colaborador.getProductoColaboradorList();
            List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoListOld = persistentColaborador.getColaboradorCuerpoacademicoList();
            List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoListNew = colaborador.getColaboradorCuerpoacademicoList();
            List<String> illegalOrphanMessages = null;
            for (ProductoColaborador productoColaboradorListOldProductoColaborador : productoColaboradorListOld) {
                if (!productoColaboradorListNew.contains(productoColaboradorListOldProductoColaborador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoColaborador " + productoColaboradorListOldProductoColaborador + " since its colaborador field is not nullable.");
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListOldColaboradorCuerpoacademico : colaboradorCuerpoacademicoListOld) {
                if (!colaboradorCuerpoacademicoListNew.contains(colaboradorCuerpoacademicoListOldColaboradorCuerpoacademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoListOldColaboradorCuerpoacademico + " since its colaborador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ProductoColaborador> attachedProductoColaboradorListNew = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorListNewProductoColaboradorToAttach : productoColaboradorListNew) {
                productoColaboradorListNewProductoColaboradorToAttach = em.getReference(productoColaboradorListNewProductoColaboradorToAttach.getClass(), productoColaboradorListNewProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorListNew.add(productoColaboradorListNewProductoColaboradorToAttach);
            }
            productoColaboradorListNew = attachedProductoColaboradorListNew;
            colaborador.setProductoColaboradorList(productoColaboradorListNew);
            List<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoListNew = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach : colaboradorCuerpoacademicoListNew) {
                colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoListNew.add(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach);
            }
            colaboradorCuerpoacademicoListNew = attachedColaboradorCuerpoacademicoListNew;
            colaborador.setColaboradorCuerpoacademicoList(colaboradorCuerpoacademicoListNew);
            colaborador = em.merge(colaborador);
            for (ProductoColaborador productoColaboradorListNewProductoColaborador : productoColaboradorListNew) {
                if (!productoColaboradorListOld.contains(productoColaboradorListNewProductoColaborador)) {
                    Colaborador oldColaboradorOfProductoColaboradorListNewProductoColaborador = productoColaboradorListNewProductoColaborador.getColaborador();
                    productoColaboradorListNewProductoColaborador.setColaborador(colaborador);
                    productoColaboradorListNewProductoColaborador = em.merge(productoColaboradorListNewProductoColaborador);
                    if (oldColaboradorOfProductoColaboradorListNewProductoColaborador != null && !oldColaboradorOfProductoColaboradorListNewProductoColaborador.equals(colaborador)) {
                        oldColaboradorOfProductoColaboradorListNewProductoColaborador.getProductoColaboradorList().remove(productoColaboradorListNewProductoColaborador);
                        oldColaboradorOfProductoColaboradorListNewProductoColaborador = em.merge(oldColaboradorOfProductoColaboradorListNewProductoColaborador);
                    }
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico : colaboradorCuerpoacademicoListNew) {
                if (!colaboradorCuerpoacademicoListOld.contains(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico)) {
                    Colaborador oldColaboradorOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico = colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.getColaborador();
                    colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.setColaborador(colaborador);
                    colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico);
                    if (oldColaboradorOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico != null && !oldColaboradorOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.equals(colaborador)) {
                        oldColaboradorOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico);
                        oldColaboradorOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico = em.merge(oldColaboradorOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico);
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
            List<ProductoColaborador> productoColaboradorListOrphanCheck = colaborador.getProductoColaboradorList();
            for (ProductoColaborador productoColaboradorListOrphanCheckProductoColaborador : productoColaboradorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Colaborador (" + colaborador + ") cannot be destroyed since the ProductoColaborador " + productoColaboradorListOrphanCheckProductoColaborador + " in its productoColaboradorList field has a non-nullable colaborador field.");
            }
            List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoListOrphanCheck = colaborador.getColaboradorCuerpoacademicoList();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListOrphanCheckColaboradorCuerpoacademico : colaboradorCuerpoacademicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Colaborador (" + colaborador + ") cannot be destroyed since the ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoListOrphanCheckColaboradorCuerpoacademico + " in its colaboradorCuerpoacademicoList field has a non-nullable colaborador field.");
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

    public List<Colaborador> findAll() {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Colaborador.findAll", Colaborador.class);
        List<Colaborador> cs = q.getResultList();
        return cs;
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
