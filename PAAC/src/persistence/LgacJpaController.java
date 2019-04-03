/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import entity.Lgac;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.Proyecto;
import java.util.ArrayList;
import java.util.List;
import entity.MiembroLgac;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduar
 */
public class LgacJpaController implements Serializable {

    public LgacJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lgac lgac) {
        if (lgac.getProyectoList() == null) {
            lgac.setProyectoList(new ArrayList<Proyecto>());
        }
        if (lgac.getMiembroLgacList() == null) {
            lgac.setMiembroLgacList(new ArrayList<MiembroLgac>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Proyecto> attachedProyectoList = new ArrayList<Proyecto>();
            for (Proyecto proyectoListProyectoToAttach : lgac.getProyectoList()) {
                proyectoListProyectoToAttach = em.getReference(proyectoListProyectoToAttach.getClass(), proyectoListProyectoToAttach.getIdProyecto());
                attachedProyectoList.add(proyectoListProyectoToAttach);
            }
            lgac.setProyectoList(attachedProyectoList);
            List<MiembroLgac> attachedMiembroLgacList = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacListMiembroLgacToAttach : lgac.getMiembroLgacList()) {
                miembroLgacListMiembroLgacToAttach = em.getReference(miembroLgacListMiembroLgacToAttach.getClass(), miembroLgacListMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacList.add(miembroLgacListMiembroLgacToAttach);
            }
            lgac.setMiembroLgacList(attachedMiembroLgacList);
            em.persist(lgac);
            for (Proyecto proyectoListProyecto : lgac.getProyectoList()) {
                Lgac oldIdLGACApoyoOfProyectoListProyecto = proyectoListProyecto.getIdLGACApoyo();
                proyectoListProyecto.setIdLGACApoyo(lgac);
                proyectoListProyecto = em.merge(proyectoListProyecto);
                if (oldIdLGACApoyoOfProyectoListProyecto != null) {
                    oldIdLGACApoyoOfProyectoListProyecto.getProyectoList().remove(proyectoListProyecto);
                    oldIdLGACApoyoOfProyectoListProyecto = em.merge(oldIdLGACApoyoOfProyectoListProyecto);
                }
            }
            for (MiembroLgac miembroLgacListMiembroLgac : lgac.getMiembroLgacList()) {
                Lgac oldLgacOfMiembroLgacListMiembroLgac = miembroLgacListMiembroLgac.getLgac();
                miembroLgacListMiembroLgac.setLgac(lgac);
                miembroLgacListMiembroLgac = em.merge(miembroLgacListMiembroLgac);
                if (oldLgacOfMiembroLgacListMiembroLgac != null) {
                    oldLgacOfMiembroLgacListMiembroLgac.getMiembroLgacList().remove(miembroLgacListMiembroLgac);
                    oldLgacOfMiembroLgacListMiembroLgac = em.merge(oldLgacOfMiembroLgacListMiembroLgac);
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
            List<Proyecto> proyectoListOld = persistentLgac.getProyectoList();
            List<Proyecto> proyectoListNew = lgac.getProyectoList();
            List<MiembroLgac> miembroLgacListOld = persistentLgac.getMiembroLgacList();
            List<MiembroLgac> miembroLgacListNew = lgac.getMiembroLgacList();
            List<String> illegalOrphanMessages = null;
            for (MiembroLgac miembroLgacListOldMiembroLgac : miembroLgacListOld) {
                if (!miembroLgacListNew.contains(miembroLgacListOldMiembroLgac)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MiembroLgac " + miembroLgacListOldMiembroLgac + " since its lgac field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Proyecto> attachedProyectoListNew = new ArrayList<Proyecto>();
            for (Proyecto proyectoListNewProyectoToAttach : proyectoListNew) {
                proyectoListNewProyectoToAttach = em.getReference(proyectoListNewProyectoToAttach.getClass(), proyectoListNewProyectoToAttach.getIdProyecto());
                attachedProyectoListNew.add(proyectoListNewProyectoToAttach);
            }
            proyectoListNew = attachedProyectoListNew;
            lgac.setProyectoList(proyectoListNew);
            List<MiembroLgac> attachedMiembroLgacListNew = new ArrayList<MiembroLgac>();
            for (MiembroLgac miembroLgacListNewMiembroLgacToAttach : miembroLgacListNew) {
                miembroLgacListNewMiembroLgacToAttach = em.getReference(miembroLgacListNewMiembroLgacToAttach.getClass(), miembroLgacListNewMiembroLgacToAttach.getMiembroLgacPK());
                attachedMiembroLgacListNew.add(miembroLgacListNewMiembroLgacToAttach);
            }
            miembroLgacListNew = attachedMiembroLgacListNew;
            lgac.setMiembroLgacList(miembroLgacListNew);
            lgac = em.merge(lgac);
            for (Proyecto proyectoListOldProyecto : proyectoListOld) {
                if (!proyectoListNew.contains(proyectoListOldProyecto)) {
                    proyectoListOldProyecto.setIdLGACApoyo(null);
                    proyectoListOldProyecto = em.merge(proyectoListOldProyecto);
                }
            }
            for (Proyecto proyectoListNewProyecto : proyectoListNew) {
                if (!proyectoListOld.contains(proyectoListNewProyecto)) {
                    Lgac oldIdLGACApoyoOfProyectoListNewProyecto = proyectoListNewProyecto.getIdLGACApoyo();
                    proyectoListNewProyecto.setIdLGACApoyo(lgac);
                    proyectoListNewProyecto = em.merge(proyectoListNewProyecto);
                    if (oldIdLGACApoyoOfProyectoListNewProyecto != null && !oldIdLGACApoyoOfProyectoListNewProyecto.equals(lgac)) {
                        oldIdLGACApoyoOfProyectoListNewProyecto.getProyectoList().remove(proyectoListNewProyecto);
                        oldIdLGACApoyoOfProyectoListNewProyecto = em.merge(oldIdLGACApoyoOfProyectoListNewProyecto);
                    }
                }
            }
            for (MiembroLgac miembroLgacListNewMiembroLgac : miembroLgacListNew) {
                if (!miembroLgacListOld.contains(miembroLgacListNewMiembroLgac)) {
                    Lgac oldLgacOfMiembroLgacListNewMiembroLgac = miembroLgacListNewMiembroLgac.getLgac();
                    miembroLgacListNewMiembroLgac.setLgac(lgac);
                    miembroLgacListNewMiembroLgac = em.merge(miembroLgacListNewMiembroLgac);
                    if (oldLgacOfMiembroLgacListNewMiembroLgac != null && !oldLgacOfMiembroLgacListNewMiembroLgac.equals(lgac)) {
                        oldLgacOfMiembroLgacListNewMiembroLgac.getMiembroLgacList().remove(miembroLgacListNewMiembroLgac);
                        oldLgacOfMiembroLgacListNewMiembroLgac = em.merge(oldLgacOfMiembroLgacListNewMiembroLgac);
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
            List<MiembroLgac> miembroLgacListOrphanCheck = lgac.getMiembroLgacList();
            for (MiembroLgac miembroLgacListOrphanCheckMiembroLgac : miembroLgacListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lgac (" + lgac + ") cannot be destroyed since the MiembroLgac " + miembroLgacListOrphanCheckMiembroLgac + " in its miembroLgacList field has a non-nullable lgac field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Proyecto> proyectoList = lgac.getProyectoList();
            for (Proyecto proyectoListProyecto : proyectoList) {
                proyectoListProyecto.setIdLGACApoyo(null);
                proyectoListProyecto = em.merge(proyectoListProyecto);
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

    public List<Lgac> findAll() {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Lgac.findAll", Lgac.class);
        List<Lgac> ms = q.getResultList();
        return ms;
    }
    
}
