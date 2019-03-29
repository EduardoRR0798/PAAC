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
import entity.Gradoacademico;
import entity.DatosLaborales;
import entity.Miembro;
import java.util.ArrayList;
import java.util.List;
import entity.MiembroLgac;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

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
        if (miembro.getDatosLaboralesList() == null) {
            miembro.setDatosLaboralesList(new ArrayList<DatosLaborales>());
        }
        if (miembro.getMiembroLgacList() == null) {
            miembro.setMiembroLgacList(new ArrayList<MiembroLgac>());
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
            List<DatosLaborales> datosLaboralesListOld = persistentMiembro.getDatosLaboralesList();
            List<DatosLaborales> datosLaboralesListNew = miembro.getDatosLaboralesList();
            List<MiembroLgac> miembroLgacListOld = persistentMiembro.getMiembroLgacList();
            List<MiembroLgac> miembroLgacListNew = miembro.getMiembroLgacList();
            List<String> illegalOrphanMessages = null;
            if (gradoacademicoOld != null && !gradoacademicoOld.equals(gradoacademicoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Gradoacademico " + gradoacademicoOld + " since its miembro field is not nullable.");
            }
            for (MiembroLgac miembroLgacListOldMiembroLgac : miembroLgacListOld) {
                if (!miembroLgacListNew.contains(miembroLgacListOldMiembroLgac)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MiembroLgac " + miembroLgacListOldMiembroLgac + " since its miembro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (gradoacademicoNew != null) {
                gradoacademicoNew = em.getReference(gradoacademicoNew.getClass(), gradoacademicoNew.getIdGradoAcademico());
                miembro.setGradoacademico(gradoacademicoNew);
            }
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
            List<MiembroLgac> miembroLgacListOrphanCheck = miembro.getMiembroLgacList();
            for (MiembroLgac miembroLgacListOrphanCheckMiembroLgac : miembroLgacListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Miembro (" + miembro + ") cannot be destroyed since the MiembroLgac " + miembroLgacListOrphanCheckMiembroLgac + " in its miembroLgacList field has a non-nullable miembro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
    
}
