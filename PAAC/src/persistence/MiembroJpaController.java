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
import entity.ProductoMiembro;
import java.util.ArrayList;
import java.util.List;
import entity.DatosLaborales;
import entity.MiembroLgac;
import entity.Gradoacademico;
import entity.Miembro;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class MiembroJpaController implements Serializable {

    public MiembroJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Miembro miembro) {
        if (miembro.getProductoMiembroList() == null) {
            miembro.setProductoMiembroList(new ArrayList<ProductoMiembro>());
        }
        if (miembro.getDatosLaboralesList() == null) {
            miembro.setDatosLaboralesList(new ArrayList<DatosLaborales>());
        }
        if (miembro.getMiembroLgacList() == null) {
            miembro.setMiembroLgacList(new ArrayList<MiembroLgac>());
        }
        if (miembro.getGradoacademicoList() == null) {
            miembro.setGradoacademicoList(new ArrayList<Gradoacademico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProductoMiembro> attachedProductoMiembroList = new ArrayList<ProductoMiembro>();
            for (ProductoMiembro productoMiembroListProductoMiembroToAttach : miembro.getProductoMiembroList()) {
                productoMiembroListProductoMiembroToAttach = em.getReference(productoMiembroListProductoMiembroToAttach.getClass(), productoMiembroListProductoMiembroToAttach.getIdMiembroProducto());
                attachedProductoMiembroList.add(productoMiembroListProductoMiembroToAttach);
            }
            miembro.setProductoMiembroList(attachedProductoMiembroList);
            List<DatosLaborales> attachedDatosLaboralesList = new ArrayList<DatosLaborales>();
            for (DatosLaborales datosLaboralesListDatosLaboralesToAttach : miembro.getDatosLaboralesList()) {
                datosLaboralesListDatosLaboralesToAttach = em.getReference(datosLaboralesListDatosLaboralesToAttach.getClass(), datosLaboralesListDatosLaboralesToAttach.getIdDatosLaborales());
                attachedDatosLaboralesList.add(datosLaboralesListDatosLaboralesToAttach);
            }
            miembro.setDatosLaboralesList(attachedDatosLaboralesList);
            List<MiembroLgac> attachedMiembroLgacList = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacListMiembroLgacToAttach : miembro.getMiembroLgacList()) {
                miembroLgacListMiembroLgacToAttach = em.getReference(miembroLgacListMiembroLgacToAttach.getClass(), miembroLgacListMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacList.add(miembroLgacListMiembroLgacToAttach);
            }
            miembro.setMiembroLgacList(attachedMiembroLgacList);
            List<Gradoacademico> attachedGradoacademicoList = new ArrayList<Gradoacademico>();
            for (Gradoacademico gradoacademicoListGradoacademicoToAttach : miembro.getGradoacademicoList()) {
                gradoacademicoListGradoacademicoToAttach = em.getReference(gradoacademicoListGradoacademicoToAttach.getClass(), gradoacademicoListGradoacademicoToAttach.getIdGradoAcademico());
                attachedGradoacademicoList.add(gradoacademicoListGradoacademicoToAttach);
            }
            miembro.setGradoacademicoList(attachedGradoacademicoList);
            em.persist(miembro);
            for (ProductoMiembro productoMiembroListProductoMiembro : miembro.getProductoMiembroList()) {
                Miembro oldIdMiembroOfProductoMiembroListProductoMiembro = productoMiembroListProductoMiembro.getIdMiembro();
                productoMiembroListProductoMiembro.setIdMiembro(miembro);
                productoMiembroListProductoMiembro = em.merge(productoMiembroListProductoMiembro);
                if (oldIdMiembroOfProductoMiembroListProductoMiembro != null) {
                    oldIdMiembroOfProductoMiembroListProductoMiembro.getProductoMiembroList().remove(productoMiembroListProductoMiembro);
                    oldIdMiembroOfProductoMiembroListProductoMiembro = em.merge(oldIdMiembroOfProductoMiembroListProductoMiembro);
                }
            }
            for (DatosLaborales datosLaboralesListDatosLaborales : miembro.getDatosLaboralesList()) {
                Miembro oldIdMiembroOfDatosLaboralesListDatosLaborales = datosLaboralesListDatosLaborales.getIdMiembro();
                datosLaboralesListDatosLaborales.setIdMiembro(miembro);
                datosLaboralesListDatosLaborales = em.merge(datosLaboralesListDatosLaborales);
                if (oldIdMiembroOfDatosLaboralesListDatosLaborales != null) {
                    oldIdMiembroOfDatosLaboralesListDatosLaborales.getDatosLaboralesList().remove(datosLaboralesListDatosLaborales);
                    oldIdMiembroOfDatosLaboralesListDatosLaborales = em.merge(oldIdMiembroOfDatosLaboralesListDatosLaborales);
                }
            }
            for (MiembroLgac miembroLgacListMiembroLgac : miembro.getMiembroLgacList()) {
                Miembro oldMiembroOfMiembroLgacListMiembroLgac = miembroLgacListMiembroLgac.getMiembro();
                miembroLgacListMiembroLgac.setMiembro(miembro);
                miembroLgacListMiembroLgac = em.merge(miembroLgacListMiembroLgac);
                if (oldMiembroOfMiembroLgacListMiembroLgac != null) {
                    oldMiembroOfMiembroLgacListMiembroLgac.getMiembroLgacList().remove(miembroLgacListMiembroLgac);
                    oldMiembroOfMiembroLgacListMiembroLgac = em.merge(oldMiembroOfMiembroLgacListMiembroLgac);
                }
            }
            for (Gradoacademico gradoacademicoListGradoacademico : miembro.getGradoacademicoList()) {
                Miembro oldIdMiembroOfGradoacademicoListGradoacademico = gradoacademicoListGradoacademico.getIdMiembro();
                gradoacademicoListGradoacademico.setIdMiembro(miembro);
                gradoacademicoListGradoacademico = em.merge(gradoacademicoListGradoacademico);
                if (oldIdMiembroOfGradoacademicoListGradoacademico != null) {
                    oldIdMiembroOfGradoacademicoListGradoacademico.getGradoacademicoList().remove(gradoacademicoListGradoacademico);
                    oldIdMiembroOfGradoacademicoListGradoacademico = em.merge(oldIdMiembroOfGradoacademicoListGradoacademico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Miembro miembro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Miembro persistentMiembro = em.find(Miembro.class, miembro.getIdMiembro());
            List<ProductoMiembro> productoMiembroListOld = persistentMiembro.getProductoMiembroList();
            List<ProductoMiembro> productoMiembroListNew = miembro.getProductoMiembroList();
            List<DatosLaborales> datosLaboralesListOld = persistentMiembro.getDatosLaboralesList();
            List<DatosLaborales> datosLaboralesListNew = miembro.getDatosLaboralesList();
            List<MiembroLgac> miembroLgacListOld = persistentMiembro.getMiembroLgacList();
            List<MiembroLgac> miembroLgacListNew = miembro.getMiembroLgacList();
            List<Gradoacademico> gradoacademicoListOld = persistentMiembro.getGradoacademicoList();
            List<Gradoacademico> gradoacademicoListNew = miembro.getGradoacademicoList();
            List<String> illegalOrphanMessages = null;
            for (MiembroLgac miembroLgacListOldMiembroLgac : miembroLgacListOld) {
                if (!miembroLgacListNew.contains(miembroLgacListOldMiembroLgac)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MiembroLgac " + miembroLgacListOldMiembroLgac + " since its miembro field is not nullable.");
                }
            }
            for (Gradoacademico gradoacademicoListOldGradoacademico : gradoacademicoListOld) {
                if (!gradoacademicoListNew.contains(gradoacademicoListOldGradoacademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Gradoacademico " + gradoacademicoListOldGradoacademico + " since its idMiembro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ProductoMiembro> attachedProductoMiembroListNew = new ArrayList<ProductoMiembro>();
            for (ProductoMiembro productoMiembroListNewProductoMiembroToAttach : productoMiembroListNew) {
                productoMiembroListNewProductoMiembroToAttach = em.getReference(productoMiembroListNewProductoMiembroToAttach.getClass(), productoMiembroListNewProductoMiembroToAttach.getIdMiembroProducto());
                attachedProductoMiembroListNew.add(productoMiembroListNewProductoMiembroToAttach);
            }
            productoMiembroListNew = attachedProductoMiembroListNew;
            miembro.setProductoMiembroList(productoMiembroListNew);
            List<DatosLaborales> attachedDatosLaboralesListNew = new ArrayList<DatosLaborales>();
            for (DatosLaborales datosLaboralesListNewDatosLaboralesToAttach : datosLaboralesListNew) {
                datosLaboralesListNewDatosLaboralesToAttach = em.getReference(datosLaboralesListNewDatosLaboralesToAttach.getClass(), datosLaboralesListNewDatosLaboralesToAttach.getIdDatosLaborales());
                attachedDatosLaboralesListNew.add(datosLaboralesListNewDatosLaboralesToAttach);
            }
            datosLaboralesListNew = attachedDatosLaboralesListNew;
            miembro.setDatosLaboralesList(datosLaboralesListNew);
            List<MiembroLgac> attachedMiembroLgacListNew = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacListNewMiembroLgacToAttach : miembroLgacListNew) {
                miembroLgacListNewMiembroLgacToAttach = em.getReference(miembroLgacListNewMiembroLgacToAttach.getClass(), miembroLgacListNewMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacListNew.add(miembroLgacListNewMiembroLgacToAttach);
            }
            miembroLgacListNew = attachedMiembroLgacListNew;
            miembro.setMiembroLgacList(miembroLgacListNew);
            List<Gradoacademico> attachedGradoacademicoListNew = new ArrayList<Gradoacademico>();
            for (Gradoacademico gradoacademicoListNewGradoacademicoToAttach : gradoacademicoListNew) {
                gradoacademicoListNewGradoacademicoToAttach = em.getReference(gradoacademicoListNewGradoacademicoToAttach.getClass(), gradoacademicoListNewGradoacademicoToAttach.getIdGradoAcademico());
                attachedGradoacademicoListNew.add(gradoacademicoListNewGradoacademicoToAttach);
            }
            gradoacademicoListNew = attachedGradoacademicoListNew;
            miembro.setGradoacademicoList(gradoacademicoListNew);
            miembro = em.merge(miembro);
            for (ProductoMiembro productoMiembroListOldProductoMiembro : productoMiembroListOld) {
                if (!productoMiembroListNew.contains(productoMiembroListOldProductoMiembro)) {
                    productoMiembroListOldProductoMiembro.setIdMiembro(null);
                    productoMiembroListOldProductoMiembro = em.merge(productoMiembroListOldProductoMiembro);
                }
            }
            for (ProductoMiembro productoMiembroListNewProductoMiembro : productoMiembroListNew) {
                if (!productoMiembroListOld.contains(productoMiembroListNewProductoMiembro)) {
                    Miembro oldIdMiembroOfProductoMiembroListNewProductoMiembro = productoMiembroListNewProductoMiembro.getIdMiembro();
                    productoMiembroListNewProductoMiembro.setIdMiembro(miembro);
                    productoMiembroListNewProductoMiembro = em.merge(productoMiembroListNewProductoMiembro);
                    if (oldIdMiembroOfProductoMiembroListNewProductoMiembro != null && !oldIdMiembroOfProductoMiembroListNewProductoMiembro.equals(miembro)) {
                        oldIdMiembroOfProductoMiembroListNewProductoMiembro.getProductoMiembroList().remove(productoMiembroListNewProductoMiembro);
                        oldIdMiembroOfProductoMiembroListNewProductoMiembro = em.merge(oldIdMiembroOfProductoMiembroListNewProductoMiembro);
                    }
                }
            }
            for (DatosLaborales datosLaboralesListOldDatosLaborales : datosLaboralesListOld) {
                if (!datosLaboralesListNew.contains(datosLaboralesListOldDatosLaborales)) {
                    datosLaboralesListOldDatosLaborales.setIdMiembro(null);
                    datosLaboralesListOldDatosLaborales = em.merge(datosLaboralesListOldDatosLaborales);
                }
            }
            for (DatosLaborales datosLaboralesListNewDatosLaborales : datosLaboralesListNew) {
                if (!datosLaboralesListOld.contains(datosLaboralesListNewDatosLaborales)) {
                    Miembro oldIdMiembroOfDatosLaboralesListNewDatosLaborales = datosLaboralesListNewDatosLaborales.getIdMiembro();
                    datosLaboralesListNewDatosLaborales.setIdMiembro(miembro);
                    datosLaboralesListNewDatosLaborales = em.merge(datosLaboralesListNewDatosLaborales);
                    if (oldIdMiembroOfDatosLaboralesListNewDatosLaborales != null && !oldIdMiembroOfDatosLaboralesListNewDatosLaborales.equals(miembro)) {
                        oldIdMiembroOfDatosLaboralesListNewDatosLaborales.getDatosLaboralesList().remove(datosLaboralesListNewDatosLaborales);
                        oldIdMiembroOfDatosLaboralesListNewDatosLaborales = em.merge(oldIdMiembroOfDatosLaboralesListNewDatosLaborales);
                    }
                }
            }
            for (MiembroLgac miembroLgacListNewMiembroLgac : miembroLgacListNew) {
                if (!miembroLgacListOld.contains(miembroLgacListNewMiembroLgac)) {
                    Miembro oldMiembroOfMiembroLgacListNewMiembroLgac = miembroLgacListNewMiembroLgac.getMiembro();
                    miembroLgacListNewMiembroLgac.setMiembro(miembro);
                    miembroLgacListNewMiembroLgac = em.merge(miembroLgacListNewMiembroLgac);
                    if (oldMiembroOfMiembroLgacListNewMiembroLgac != null && !oldMiembroOfMiembroLgacListNewMiembroLgac.equals(miembro)) {
                        oldMiembroOfMiembroLgacListNewMiembroLgac.getMiembroLgacList().remove(miembroLgacListNewMiembroLgac);
                        oldMiembroOfMiembroLgacListNewMiembroLgac = em.merge(oldMiembroOfMiembroLgacListNewMiembroLgac);
                    }
                }
            }
            for (Gradoacademico gradoacademicoListNewGradoacademico : gradoacademicoListNew) {
                if (!gradoacademicoListOld.contains(gradoacademicoListNewGradoacademico)) {
                    Miembro oldIdMiembroOfGradoacademicoListNewGradoacademico = gradoacademicoListNewGradoacademico.getIdMiembro();
                    gradoacademicoListNewGradoacademico.setIdMiembro(miembro);
                    gradoacademicoListNewGradoacademico = em.merge(gradoacademicoListNewGradoacademico);
                    if (oldIdMiembroOfGradoacademicoListNewGradoacademico != null && !oldIdMiembroOfGradoacademicoListNewGradoacademico.equals(miembro)) {
                        oldIdMiembroOfGradoacademicoListNewGradoacademico.getGradoacademicoList().remove(gradoacademicoListNewGradoacademico);
                        oldIdMiembroOfGradoacademicoListNewGradoacademico = em.merge(oldIdMiembroOfGradoacademicoListNewGradoacademico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = miembro.getIdMiembro();
                if (findMiembro(id) == null) {
                    throw new NonexistentEntityException("The miembro with id " + id + " no longer exists.");
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
            Miembro miembro;
            try {
                miembro = em.getReference(Miembro.class, id);
                miembro.getIdMiembro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The miembro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<MiembroLgac> miembroLgacListOrphanCheck = miembro.getMiembroLgacList();
            for (MiembroLgac miembroLgacListOrphanCheckMiembroLgac : miembroLgacListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the MiembroLgac " + miembroLgacListOrphanCheckMiembroLgac + " in its miembroLgacList field has a non-nullable miembro field.");
            }
            List<Gradoacademico> gradoacademicoListOrphanCheck = miembro.getGradoacademicoList();
            for (Gradoacademico gradoacademicoListOrphanCheckGradoacademico : gradoacademicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the Gradoacademico " + gradoacademicoListOrphanCheckGradoacademico + " in its gradoacademicoList field has a non-nullable idMiembro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ProductoMiembro> productoMiembroList = miembro.getProductoMiembroList();
            for (ProductoMiembro productoMiembroListProductoMiembro : productoMiembroList) {
                productoMiembroListProductoMiembro.setIdMiembro(null);
                productoMiembroListProductoMiembro = em.merge(productoMiembroListProductoMiembro);
            }
            List<DatosLaborales> datosLaboralesList = miembro.getDatosLaboralesList();
            for (DatosLaborales datosLaboralesListDatosLaborales : datosLaboralesList) {
                datosLaboralesListDatosLaborales.setIdMiembro(null);
                datosLaboralesListDatosLaborales = em.merge(datosLaboralesListDatosLaborales);
            }
            em.remove(miembro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Miembro> findMiembroEntities() {
        return findMiembroEntities(true, -1, -1);
    }

    public List<Miembro> findMiembroEntities(int maxResults, int firstResult) {
        return findMiembroEntities(false, maxResults, firstResult);
    }

    private List<Miembro> findMiembroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Miembro.class));
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

    public Miembro findMiembro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Miembro.class, id);
        } finally {
            em.close();
        }
    }

    public int getMiembroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Miembro> rt = cq.from(Miembro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Recupera todos los miembros de la base de datos.
     * @return una lista de miembros registrados en la base de datos.
     */
    public List<Miembro> findAll() {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Miembro.findAll", Miembro.class);
        List<Miembro> ms = q.getResultList();
        return ms;
    }
}
