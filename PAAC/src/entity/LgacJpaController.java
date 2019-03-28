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
public class LgacJpaController implements Serializable {

    public LgacJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lgac lgac) {
        if (lgac.getProyectoCollection() == null) {
            lgac.setProyectoCollection(new ArrayList<Proyecto>());
        }
        if (lgac.getMiembroLgacCollection() == null) {
            lgac.setMiembroLgacCollection(new ArrayList<MiembroLgac>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Proyecto> attachedProyectoCollection = new ArrayList<Proyecto>();
            for (Proyecto proyectoCollectionProyectoToAttach : lgac.getProyectoCollection()) {
                proyectoCollectionProyectoToAttach = em.getReference(proyectoCollectionProyectoToAttach.getClass(), proyectoCollectionProyectoToAttach.getIdProyecto());
                attachedProyectoCollection.add(proyectoCollectionProyectoToAttach);
            }
            lgac.setProyectoCollection(attachedProyectoCollection);
            Collection<MiembroLgac> attachedMiembroLgacCollection = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacCollectionMiembroLgacToAttach : lgac.getMiembroLgacCollection()) {
                miembroLgacCollectionMiembroLgacToAttach = em.getReference(miembroLgacCollectionMiembroLgacToAttach.getClass(), miembroLgacCollectionMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacCollection.add(miembroLgacCollectionMiembroLgacToAttach);
            }
            lgac.setMiembroLgacCollection(attachedMiembroLgacCollection);
            em.persist(lgac);
            for (Proyecto proyectoCollectionProyecto : lgac.getProyectoCollection()) {
                Lgac oldIdLGACApoyoOfProyectoCollectionProyecto = proyectoCollectionProyecto.getIdLGACApoyo();
                proyectoCollectionProyecto.setIdLGACApoyo(lgac);
                proyectoCollectionProyecto = em.merge(proyectoCollectionProyecto);
                if (oldIdLGACApoyoOfProyectoCollectionProyecto != null) {
                    oldIdLGACApoyoOfProyectoCollectionProyecto.getProyectoCollection().remove(proyectoCollectionProyecto);
                    oldIdLGACApoyoOfProyectoCollectionProyecto = em.merge(oldIdLGACApoyoOfProyectoCollectionProyecto);
                }
            }
            for (MiembroLgac miembroLgacCollectionMiembroLgac : lgac.getMiembroLgacCollection()) {
                Lgac oldLgacOfMiembroLgacCollectionMiembroLgac = miembroLgacCollectionMiembroLgac.getLgac();
                miembroLgacCollectionMiembroLgac.setLgac(lgac);
                miembroLgacCollectionMiembroLgac = em.merge(miembroLgacCollectionMiembroLgac);
                if (oldLgacOfMiembroLgacCollectionMiembroLgac != null) {
                    oldLgacOfMiembroLgacCollectionMiembroLgac.getMiembroLgacCollection().remove(miembroLgacCollectionMiembroLgac);
                    oldLgacOfMiembroLgacCollectionMiembroLgac = em.merge(oldLgacOfMiembroLgacCollectionMiembroLgac);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lgac lgac) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lgac persistentLgac = em.find(Lgac.class, lgac.getIdlgac());
            Collection<Proyecto> proyectoCollectionOld = persistentLgac.getProyectoCollection();
            Collection<Proyecto> proyectoCollectionNew = lgac.getProyectoCollection();
            Collection<MiembroLgac> miembroLgacCollectionOld = persistentLgac.getMiembroLgacCollection();
            Collection<MiembroLgac> miembroLgacCollectionNew = lgac.getMiembroLgacCollection();
            List<String> illegalOrphanMessages = null;
            for (MiembroLgac miembroLgacCollectionOldMiembroLgac : miembroLgacCollectionOld) {
                if (!miembroLgacCollectionNew.contains(miembroLgacCollectionOldMiembroLgac)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MiembroLgac " + miembroLgacCollectionOldMiembroLgac + " since its lgac field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proyecto> attachedProyectoCollectionNew = new ArrayList<Proyecto>();
            for (Proyecto proyectoCollectionNewProyectoToAttach : proyectoCollectionNew) {
                proyectoCollectionNewProyectoToAttach = em.getReference(proyectoCollectionNewProyectoToAttach.getClass(), proyectoCollectionNewProyectoToAttach.getIdProyecto());
                attachedProyectoCollectionNew.add(proyectoCollectionNewProyectoToAttach);
            }
            proyectoCollectionNew = attachedProyectoCollectionNew;
            lgac.setProyectoCollection(proyectoCollectionNew);
            Collection<MiembroLgac> attachedMiembroLgacCollectionNew = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacCollectionNewMiembroLgacToAttach : miembroLgacCollectionNew) {
                miembroLgacCollectionNewMiembroLgacToAttach = em.getReference(miembroLgacCollectionNewMiembroLgacToAttach.getClass(), miembroLgacCollectionNewMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacCollectionNew.add(miembroLgacCollectionNewMiembroLgacToAttach);
            }
            miembroLgacCollectionNew = attachedMiembroLgacCollectionNew;
            lgac.setMiembroLgacCollection(miembroLgacCollectionNew);
            lgac = em.merge(lgac);
            for (Proyecto proyectoCollectionOldProyecto : proyectoCollectionOld) {
                if (!proyectoCollectionNew.contains(proyectoCollectionOldProyecto)) {
                    proyectoCollectionOldProyecto.setIdLGACApoyo(null);
                    proyectoCollectionOldProyecto = em.merge(proyectoCollectionOldProyecto);
                }
            }
            for (Proyecto proyectoCollectionNewProyecto : proyectoCollectionNew) {
                if (!proyectoCollectionOld.contains(proyectoCollectionNewProyecto)) {
                    Lgac oldIdLGACApoyoOfProyectoCollectionNewProyecto = proyectoCollectionNewProyecto.getIdLGACApoyo();
                    proyectoCollectionNewProyecto.setIdLGACApoyo(lgac);
                    proyectoCollectionNewProyecto = em.merge(proyectoCollectionNewProyecto);
                    if (oldIdLGACApoyoOfProyectoCollectionNewProyecto != null && !oldIdLGACApoyoOfProyectoCollectionNewProyecto.equals(lgac)) {
                        oldIdLGACApoyoOfProyectoCollectionNewProyecto.getProyectoCollection().remove(proyectoCollectionNewProyecto);
                        oldIdLGACApoyoOfProyectoCollectionNewProyecto = em.merge(oldIdLGACApoyoOfProyectoCollectionNewProyecto);
                    }
                }
            }
            for (MiembroLgac miembroLgacCollectionNewMiembroLgac : miembroLgacCollectionNew) {
                if (!miembroLgacCollectionOld.contains(miembroLgacCollectionNewMiembroLgac)) {
                    Lgac oldLgacOfMiembroLgacCollectionNewMiembroLgac = miembroLgacCollectionNewMiembroLgac.getLgac();
                    miembroLgacCollectionNewMiembroLgac.setLgac(lgac);
                    miembroLgacCollectionNewMiembroLgac = em.merge(miembroLgacCollectionNewMiembroLgac);
                    if (oldLgacOfMiembroLgacCollectionNewMiembroLgac != null && !oldLgacOfMiembroLgacCollectionNewMiembroLgac.equals(lgac)) {
                        oldLgacOfMiembroLgacCollectionNewMiembroLgac.getMiembroLgacCollection().remove(miembroLgacCollectionNewMiembroLgac);
                        oldLgacOfMiembroLgacCollectionNewMiembroLgac = em.merge(oldLgacOfMiembroLgacCollectionNewMiembroLgac);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lgac.getIdlgac();
                if (findLgac(id) == null) {
                    throw new NonexistentEntityException("The lgac with id " + id + " no longer exists.");
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
            Lgac lgac;
            try {
                lgac = em.getReference(Lgac.class, id);
                lgac.getIdlgac();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lgac with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<MiembroLgac> miembroLgacCollectionOrphanCheck = lgac.getMiembroLgacCollection();
            for (MiembroLgac miembroLgacCollectionOrphanCheckMiembroLgac : miembroLgacCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lgac (" + lgac + ") cannot be destroyed since the MiembroLgac " + miembroLgacCollectionOrphanCheckMiembroLgac + " in its miembroLgacCollection field has a non-nullable lgac field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proyecto> proyectoCollection = lgac.getProyectoCollection();
            for (Proyecto proyectoCollectionProyecto : proyectoCollection) {
                proyectoCollectionProyecto.setIdLGACApoyo(null);
                proyectoCollectionProyecto = em.merge(proyectoCollectionProyecto);
            }
            em.remove(lgac);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lgac> findLgacEntities() {
        return findLgacEntities(true, -1, -1);
    }

    public List<Lgac> findLgacEntities(int maxResults, int firstResult) {
        return findLgacEntities(false, maxResults, firstResult);
    }

    private List<Lgac> findLgacEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lgac.class));
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

    public Lgac findLgac(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lgac.class, id);
        } finally {
            em.close();
        }
    }

    public int getLgacCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lgac> rt = cq.from(Lgac.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
