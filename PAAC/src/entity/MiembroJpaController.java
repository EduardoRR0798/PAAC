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
public class MiembroJpaController implements Serializable {

    public MiembroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Miembro miembro) {
        if (miembro.getDatosLaboralesCollection() == null) {
            miembro.setDatosLaboralesCollection(new ArrayList<DatosLaborales>());
        }
        if (miembro.getMiembroLgacCollection() == null) {
            miembro.setMiembroLgacCollection(new ArrayList<MiembroLgac>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Gradoacademico gradoacademico = miembro.getGradoacademico();
            if (gradoacademico != null) {
                gradoacademico = em.getReference(gradoacademico.getClass(), gradoacademico.getIdGradoAcademico());
                miembro.setGradoacademico(gradoacademico);
            }
            Collection<DatosLaborales> attachedDatosLaboralesCollection = new ArrayList<DatosLaborales>();
            for (DatosLaborales datosLaboralesCollectionDatosLaboralesToAttach : miembro.getDatosLaboralesCollection()) {
                datosLaboralesCollectionDatosLaboralesToAttach = em.getReference(datosLaboralesCollectionDatosLaboralesToAttach.getClass(), datosLaboralesCollectionDatosLaboralesToAttach.getIdDatosLaborales());
                attachedDatosLaboralesCollection.add(datosLaboralesCollectionDatosLaboralesToAttach);
            }
            miembro.setDatosLaboralesCollection(attachedDatosLaboralesCollection);
            Collection<MiembroLgac> attachedMiembroLgacCollection = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacCollectionMiembroLgacToAttach : miembro.getMiembroLgacCollection()) {
                miembroLgacCollectionMiembroLgacToAttach = em.getReference(miembroLgacCollectionMiembroLgacToAttach.getClass(), miembroLgacCollectionMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacCollection.add(miembroLgacCollectionMiembroLgacToAttach);
            }
            miembro.setMiembroLgacCollection(attachedMiembroLgacCollection);
            em.persist(miembro);
            if (gradoacademico != null) {
                Miembro oldMiembroOfGradoacademico = gradoacademico.getMiembro();
                if (oldMiembroOfGradoacademico != null) {
                    oldMiembroOfGradoacademico.setGradoacademico(null);
                    oldMiembroOfGradoacademico = em.merge(oldMiembroOfGradoacademico);
                }
                gradoacademico.setMiembro(miembro);
                gradoacademico = em.merge(gradoacademico);
            }
            for (DatosLaborales datosLaboralesCollectionDatosLaborales : miembro.getDatosLaboralesCollection()) {
                Miembro oldIdMiembroOfDatosLaboralesCollectionDatosLaborales = datosLaboralesCollectionDatosLaborales.getIdMiembro();
                datosLaboralesCollectionDatosLaborales.setIdMiembro(miembro);
                datosLaboralesCollectionDatosLaborales = em.merge(datosLaboralesCollectionDatosLaborales);
                if (oldIdMiembroOfDatosLaboralesCollectionDatosLaborales != null) {
                    oldIdMiembroOfDatosLaboralesCollectionDatosLaborales.getDatosLaboralesCollection().remove(datosLaboralesCollectionDatosLaborales);
                    oldIdMiembroOfDatosLaboralesCollectionDatosLaborales = em.merge(oldIdMiembroOfDatosLaboralesCollectionDatosLaborales);
                }
            }
            for (MiembroLgac miembroLgacCollectionMiembroLgac : miembro.getMiembroLgacCollection()) {
                Miembro oldMiembroOfMiembroLgacCollectionMiembroLgac = miembroLgacCollectionMiembroLgac.getMiembro();
                miembroLgacCollectionMiembroLgac.setMiembro(miembro);
                miembroLgacCollectionMiembroLgac = em.merge(miembroLgacCollectionMiembroLgac);
                if (oldMiembroOfMiembroLgacCollectionMiembroLgac != null) {
                    oldMiembroOfMiembroLgacCollectionMiembroLgac.getMiembroLgacCollection().remove(miembroLgacCollectionMiembroLgac);
                    oldMiembroOfMiembroLgacCollectionMiembroLgac = em.merge(oldMiembroOfMiembroLgacCollectionMiembroLgac);
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
            Gradoacademico gradoacademicoOld = persistentMiembro.getGradoacademico();
            Gradoacademico gradoacademicoNew = miembro.getGradoacademico();
            Collection<DatosLaborales> datosLaboralesCollectionOld = persistentMiembro.getDatosLaboralesCollection();
            Collection<DatosLaborales> datosLaboralesCollectionNew = miembro.getDatosLaboralesCollection();
            Collection<MiembroLgac> miembroLgacCollectionOld = persistentMiembro.getMiembroLgacCollection();
            Collection<MiembroLgac> miembroLgacCollectionNew = miembro.getMiembroLgacCollection();
            List<String> illegalOrphanMessages = null;
            if (gradoacademicoOld != null && !gradoacademicoOld.equals(gradoacademicoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Gradoacademico " + gradoacademicoOld + " since its miembro field is not nullable.");
            }
            for (MiembroLgac miembroLgacCollectionOldMiembroLgac : miembroLgacCollectionOld) {
                if (!miembroLgacCollectionNew.contains(miembroLgacCollectionOldMiembroLgac)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MiembroLgac " + miembroLgacCollectionOldMiembroLgac + " since its miembro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (gradoacademicoNew != null) {
                gradoacademicoNew = em.getReference(gradoacademicoNew.getClass(), gradoacademicoNew.getIdGradoAcademico());
                miembro.setGradoacademico(gradoacademicoNew);
            }
            Collection<DatosLaborales> attachedDatosLaboralesCollectionNew = new ArrayList<DatosLaborales>();
            for (DatosLaborales datosLaboralesCollectionNewDatosLaboralesToAttach : datosLaboralesCollectionNew) {
                datosLaboralesCollectionNewDatosLaboralesToAttach = em.getReference(datosLaboralesCollectionNewDatosLaboralesToAttach.getClass(), datosLaboralesCollectionNewDatosLaboralesToAttach.getIdDatosLaborales());
                attachedDatosLaboralesCollectionNew.add(datosLaboralesCollectionNewDatosLaboralesToAttach);
            }
            datosLaboralesCollectionNew = attachedDatosLaboralesCollectionNew;
            miembro.setDatosLaboralesCollection(datosLaboralesCollectionNew);
            Collection<MiembroLgac> attachedMiembroLgacCollectionNew = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacCollectionNewMiembroLgacToAttach : miembroLgacCollectionNew) {
                miembroLgacCollectionNewMiembroLgacToAttach = em.getReference(miembroLgacCollectionNewMiembroLgacToAttach.getClass(), miembroLgacCollectionNewMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacCollectionNew.add(miembroLgacCollectionNewMiembroLgacToAttach);
            }
            miembroLgacCollectionNew = attachedMiembroLgacCollectionNew;
            miembro.setMiembroLgacCollection(miembroLgacCollectionNew);
            miembro = em.merge(miembro);
            if (gradoacademicoNew != null && !gradoacademicoNew.equals(gradoacademicoOld)) {
                Miembro oldMiembroOfGradoacademico = gradoacademicoNew.getMiembro();
                if (oldMiembroOfGradoacademico != null) {
                    oldMiembroOfGradoacademico.setGradoacademico(null);
                    oldMiembroOfGradoacademico = em.merge(oldMiembroOfGradoacademico);
                }
                gradoacademicoNew.setMiembro(miembro);
                gradoacademicoNew = em.merge(gradoacademicoNew);
            }
            for (DatosLaborales datosLaboralesCollectionOldDatosLaborales : datosLaboralesCollectionOld) {
                if (!datosLaboralesCollectionNew.contains(datosLaboralesCollectionOldDatosLaborales)) {
                    datosLaboralesCollectionOldDatosLaborales.setIdMiembro(null);
                    datosLaboralesCollectionOldDatosLaborales = em.merge(datosLaboralesCollectionOldDatosLaborales);
                }
            }
            for (DatosLaborales datosLaboralesCollectionNewDatosLaborales : datosLaboralesCollectionNew) {
                if (!datosLaboralesCollectionOld.contains(datosLaboralesCollectionNewDatosLaborales)) {
                    Miembro oldIdMiembroOfDatosLaboralesCollectionNewDatosLaborales = datosLaboralesCollectionNewDatosLaborales.getIdMiembro();
                    datosLaboralesCollectionNewDatosLaborales.setIdMiembro(miembro);
                    datosLaboralesCollectionNewDatosLaborales = em.merge(datosLaboralesCollectionNewDatosLaborales);
                    if (oldIdMiembroOfDatosLaboralesCollectionNewDatosLaborales != null && !oldIdMiembroOfDatosLaboralesCollectionNewDatosLaborales.equals(miembro)) {
                        oldIdMiembroOfDatosLaboralesCollectionNewDatosLaborales.getDatosLaboralesCollection().remove(datosLaboralesCollectionNewDatosLaborales);
                        oldIdMiembroOfDatosLaboralesCollectionNewDatosLaborales = em.merge(oldIdMiembroOfDatosLaboralesCollectionNewDatosLaborales);
                    }
                }
            }
            for (MiembroLgac miembroLgacCollectionNewMiembroLgac : miembroLgacCollectionNew) {
                if (!miembroLgacCollectionOld.contains(miembroLgacCollectionNewMiembroLgac)) {
                    Miembro oldMiembroOfMiembroLgacCollectionNewMiembroLgac = miembroLgacCollectionNewMiembroLgac.getMiembro();
                    miembroLgacCollectionNewMiembroLgac.setMiembro(miembro);
                    miembroLgacCollectionNewMiembroLgac = em.merge(miembroLgacCollectionNewMiembroLgac);
                    if (oldMiembroOfMiembroLgacCollectionNewMiembroLgac != null && !oldMiembroOfMiembroLgacCollectionNewMiembroLgac.equals(miembro)) {
                        oldMiembroOfMiembroLgacCollectionNewMiembroLgac.getMiembroLgacCollection().remove(miembroLgacCollectionNewMiembroLgac);
                        oldMiembroOfMiembroLgacCollectionNewMiembroLgac = em.merge(oldMiembroOfMiembroLgacCollectionNewMiembroLgac);
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
            Gradoacademico gradoacademicoOrphanCheck = miembro.getGradoacademico();
            if (gradoacademicoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the Gradoacademico " + gradoacademicoOrphanCheck + " in its gradoacademico field has a non-nullable miembro field.");
            }
            Collection<MiembroLgac> miembroLgacCollectionOrphanCheck = miembro.getMiembroLgacCollection();
            for (MiembroLgac miembroLgacCollectionOrphanCheckMiembroLgac : miembroLgacCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the MiembroLgac " + miembroLgacCollectionOrphanCheckMiembroLgac + " in its miembroLgacCollection field has a non-nullable miembro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<DatosLaborales> datosLaboralesCollection = miembro.getDatosLaboralesCollection();
            for (DatosLaborales datosLaboralesCollectionDatosLaborales : datosLaboralesCollection) {
                datosLaboralesCollectionDatosLaborales.setIdMiembro(null);
                datosLaboralesCollectionDatosLaborales = em.merge(datosLaboralesCollectionDatosLaborales);
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
    
}
