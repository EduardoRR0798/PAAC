package persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Producto;
import java.util.ArrayList;
import java.util.List;
import entity.Gradoacademico;
import entity.Pais;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getProductoList() == null) {
            pais.setProductoList(new ArrayList<Producto>());
        }
        if (pais.getGradoacademicoList() == null) {
            pais.setGradoacademicoList(new ArrayList<Gradoacademico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : pais.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getIdProducto());
                attachedProductoList.add(productoListProductoToAttach);
            }
            pais.setProductoList(attachedProductoList);
            List<Gradoacademico> attachedGradoacademicoList = new ArrayList<Gradoacademico>();
            for (Gradoacademico gradoacademicoListGradoacademicoToAttach : pais.getGradoacademicoList()) {
                gradoacademicoListGradoacademicoToAttach = em.getReference(gradoacademicoListGradoacademicoToAttach.getClass(), gradoacademicoListGradoacademicoToAttach.getIdGradoAcademico());
                attachedGradoacademicoList.add(gradoacademicoListGradoacademicoToAttach);
            }
            pais.setGradoacademicoList(attachedGradoacademicoList);
            em.persist(pais);
            for (Producto productoListProducto : pais.getProductoList()) {
                Pais oldIdPaisOfProductoListProducto = productoListProducto.getIdPais();
                productoListProducto.setIdPais(pais);
                productoListProducto = em.merge(productoListProducto);
                if (oldIdPaisOfProductoListProducto != null) {
                    oldIdPaisOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldIdPaisOfProductoListProducto = em.merge(oldIdPaisOfProductoListProducto);
                }
            }
            for (Gradoacademico gradoacademicoListGradoacademico : pais.getGradoacademicoList()) {
                Pais oldIdPaisOfGradoacademicoListGradoacademico = gradoacademicoListGradoacademico.getIdPais();
                gradoacademicoListGradoacademico.setIdPais(pais);
                gradoacademicoListGradoacademico = em.merge(gradoacademicoListGradoacademico);
                if (oldIdPaisOfGradoacademicoListGradoacademico != null) {
                    oldIdPaisOfGradoacademicoListGradoacademico.getGradoacademicoList().remove(gradoacademicoListGradoacademico);
                    oldIdPaisOfGradoacademicoListGradoacademico = em.merge(oldIdPaisOfGradoacademicoListGradoacademico);
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
            List<Producto> productoListOld = persistentPais.getProductoList();
            List<Producto> productoListNew = pais.getProductoList();
            List<Gradoacademico> gradoacademicoListOld = persistentPais.getGradoacademicoList();
            List<Gradoacademico> gradoacademicoListNew = pais.getGradoacademicoList();
            List<String> illegalOrphanMessages = null;
            for (Gradoacademico gradoacademicoListOldGradoacademico : gradoacademicoListOld) {
                if (!gradoacademicoListNew.contains(gradoacademicoListOldGradoacademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gradoacademico " + gradoacademicoListOldGradoacademico + " since its idPais field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getIdProducto());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            pais.setProductoList(productoListNew);
            List<Gradoacademico> attachedGradoacademicoListNew = new ArrayList<Gradoacademico>();
            for (Gradoacademico gradoacademicoListNewGradoacademicoToAttach : gradoacademicoListNew) {
                gradoacademicoListNewGradoacademicoToAttach = em.getReference(gradoacademicoListNewGradoacademicoToAttach.getClass(), gradoacademicoListNewGradoacademicoToAttach.getIdGradoAcademico());
                attachedGradoacademicoListNew.add(gradoacademicoListNewGradoacademicoToAttach);
            }
            gradoacademicoListNew = attachedGradoacademicoListNew;
            pais.setGradoacademicoList(gradoacademicoListNew);
            pais = em.merge(pais);
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setIdPais(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Pais oldIdPaisOfProductoListNewProducto = productoListNewProducto.getIdPais();
                    productoListNewProducto.setIdPais(pais);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldIdPaisOfProductoListNewProducto != null && !oldIdPaisOfProductoListNewProducto.equals(pais)) {
                        oldIdPaisOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldIdPaisOfProductoListNewProducto = em.merge(oldIdPaisOfProductoListNewProducto);
                    }
                }
            }
            for (Gradoacademico gradoacademicoListNewGradoacademico : gradoacademicoListNew) {
                if (!gradoacademicoListOld.contains(gradoacademicoListNewGradoacademico)) {
                    Pais oldIdPaisOfGradoacademicoListNewGradoacademico = gradoacademicoListNewGradoacademico.getIdPais();
                    gradoacademicoListNewGradoacademico.setIdPais(pais);
                    gradoacademicoListNewGradoacademico = em.merge(gradoacademicoListNewGradoacademico);
                    if (oldIdPaisOfGradoacademicoListNewGradoacademico != null && !oldIdPaisOfGradoacademicoListNewGradoacademico.equals(pais)) {
                        oldIdPaisOfGradoacademicoListNewGradoacademico.getGradoacademicoList().remove(gradoacademicoListNewGradoacademico);
                        oldIdPaisOfGradoacademicoListNewGradoacademico = em.merge(oldIdPaisOfGradoacademicoListNewGradoacademico);
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
            List<Gradoacademico> gradoacademicoListOrphanCheck = pais.getGradoacademicoList();
            for (Gradoacademico gradoacademicoListOrphanCheckGradoacademico : gradoacademicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Gradoacademico " + gradoacademicoListOrphanCheckGradoacademico + " in its gradoacademicoList field has a non-nullable idPais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Producto> productoList = pais.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setIdPais(null);
                productoListProducto = em.merge(productoListProducto);
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
    
    /**
     * Recupera TODOS los paises de la base de datos.
     * @return una lista con todos los paises registrados en la base de datos.
     */
    public List<Pais> findAll() {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Pais.findAll", Pais.class);
        List<Pais> ps = q.getResultList();
        return ps;
    }
}
