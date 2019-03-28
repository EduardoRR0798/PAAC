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
public class ParticipacionJpaController implements Serializable {

    public ParticipacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Participacion participacion) {
        if (participacion.getCuerpoacademicoexternoCollection() == null) {
            participacion.setCuerpoacademicoexternoCollection(new ArrayList<Cuerpoacademicoexterno>());
        }
        if (participacion.getCuerpoAcademicoPromepCollection() == null) {
            participacion.setCuerpoAcademicoPromepCollection(new ArrayList<CuerpoAcademicoPromep>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademico idCA = participacion.getIdCA();
            if (idCA != null) {
                idCA = em.getReference(idCA.getClass(), idCA.getIdCuerpoAcademico());
                participacion.setIdCA(idCA);
            }
            Collection<Cuerpoacademicoexterno> attachedCuerpoacademicoexternoCollection = new ArrayList<Cuerpoacademicoexterno>();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach : participacion.getCuerpoacademicoexternoCollection()) {
                cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach = em.getReference(cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach.getClass(), cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach.getIdCuerpoeExterno());
                attachedCuerpoacademicoexternoCollection.add(cuerpoacademicoexternoCollectionCuerpoacademicoexternoToAttach);
            }
            participacion.setCuerpoacademicoexternoCollection(attachedCuerpoacademicoexternoCollection);
            Collection<CuerpoAcademicoPromep> attachedCuerpoAcademicoPromepCollection = new ArrayList<CuerpoAcademicoPromep>();
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepCollectionCuerpoAcademicoPromepToAttach : participacion.getCuerpoAcademicoPromepCollection()) {
                cuerpoAcademicoPromepCollectionCuerpoAcademicoPromepToAttach = em.getReference(cuerpoAcademicoPromepCollectionCuerpoAcademicoPromepToAttach.getClass(), cuerpoAcademicoPromepCollectionCuerpoAcademicoPromepToAttach.getIdPROMEP());
                attachedCuerpoAcademicoPromepCollection.add(cuerpoAcademicoPromepCollectionCuerpoAcademicoPromepToAttach);
            }
            participacion.setCuerpoAcademicoPromepCollection(attachedCuerpoAcademicoPromepCollection);
            em.persist(participacion);
            if (idCA != null) {
                idCA.getParticipacionCollection().add(participacion);
                idCA = em.merge(idCA);
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionCuerpoacademicoexterno : participacion.getCuerpoacademicoexternoCollection()) {
                Participacion oldIdParticipacionOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno = cuerpoacademicoexternoCollectionCuerpoacademicoexterno.getIdParticipacion();
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno.setIdParticipacion(participacion);
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionCuerpoacademicoexterno);
                if (oldIdParticipacionOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno != null) {
                    oldIdParticipacionOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno.getCuerpoacademicoexternoCollection().remove(cuerpoacademicoexternoCollectionCuerpoacademicoexterno);
                    oldIdParticipacionOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno = em.merge(oldIdParticipacionOfCuerpoacademicoexternoCollectionCuerpoacademicoexterno);
                }
            }
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepCollectionCuerpoAcademicoPromep : participacion.getCuerpoAcademicoPromepCollection()) {
                Participacion oldIdParticipacionOfCuerpoAcademicoPromepCollectionCuerpoAcademicoPromep = cuerpoAcademicoPromepCollectionCuerpoAcademicoPromep.getIdParticipacion();
                cuerpoAcademicoPromepCollectionCuerpoAcademicoPromep.setIdParticipacion(participacion);
                cuerpoAcademicoPromepCollectionCuerpoAcademicoPromep = em.merge(cuerpoAcademicoPromepCollectionCuerpoAcademicoPromep);
                if (oldIdParticipacionOfCuerpoAcademicoPromepCollectionCuerpoAcademicoPromep != null) {
                    oldIdParticipacionOfCuerpoAcademicoPromepCollectionCuerpoAcademicoPromep.getCuerpoAcademicoPromepCollection().remove(cuerpoAcademicoPromepCollectionCuerpoAcademicoPromep);
                    oldIdParticipacionOfCuerpoAcademicoPromepCollectionCuerpoAcademicoPromep = em.merge(oldIdParticipacionOfCuerpoAcademicoPromepCollectionCuerpoAcademicoPromep);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Participacion participacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Participacion persistentParticipacion = em.find(Participacion.class, participacion.getIdParticipacion());
            CuerpoAcademico idCAOld = persistentParticipacion.getIdCA();
            CuerpoAcademico idCANew = participacion.getIdCA();
            Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollectionOld = persistentParticipacion.getCuerpoacademicoexternoCollection();
            Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollectionNew = participacion.getCuerpoacademicoexternoCollection();
            Collection<CuerpoAcademicoPromep> cuerpoAcademicoPromepCollectionOld = persistentParticipacion.getCuerpoAcademicoPromepCollection();
            Collection<CuerpoAcademicoPromep> cuerpoAcademicoPromepCollectionNew = participacion.getCuerpoAcademicoPromepCollection();
            List<String> illegalOrphanMessages = null;
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepCollectionOldCuerpoAcademicoPromep : cuerpoAcademicoPromepCollectionOld) {
                if (!cuerpoAcademicoPromepCollectionNew.contains(cuerpoAcademicoPromepCollectionOldCuerpoAcademicoPromep)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuerpoAcademicoPromep " + cuerpoAcademicoPromepCollectionOldCuerpoAcademicoPromep + " since its idParticipacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCANew != null) {
                idCANew = em.getReference(idCANew.getClass(), idCANew.getIdCuerpoAcademico());
                participacion.setIdCA(idCANew);
            }
            Collection<Cuerpoacademicoexterno> attachedCuerpoacademicoexternoCollectionNew = new ArrayList<Cuerpoacademicoexterno>();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach : cuerpoacademicoexternoCollectionNew) {
                cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach = em.getReference(cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach.getClass(), cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach.getIdCuerpoeExterno());
                attachedCuerpoacademicoexternoCollectionNew.add(cuerpoacademicoexternoCollectionNewCuerpoacademicoexternoToAttach);
            }
            cuerpoacademicoexternoCollectionNew = attachedCuerpoacademicoexternoCollectionNew;
            participacion.setCuerpoacademicoexternoCollection(cuerpoacademicoexternoCollectionNew);
            Collection<CuerpoAcademicoPromep> attachedCuerpoAcademicoPromepCollectionNew = new ArrayList<CuerpoAcademicoPromep>();
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromepToAttach : cuerpoAcademicoPromepCollectionNew) {
                cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromepToAttach = em.getReference(cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromepToAttach.getClass(), cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromepToAttach.getIdPROMEP());
                attachedCuerpoAcademicoPromepCollectionNew.add(cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromepToAttach);
            }
            cuerpoAcademicoPromepCollectionNew = attachedCuerpoAcademicoPromepCollectionNew;
            participacion.setCuerpoAcademicoPromepCollection(cuerpoAcademicoPromepCollectionNew);
            participacion = em.merge(participacion);
            if (idCAOld != null && !idCAOld.equals(idCANew)) {
                idCAOld.getParticipacionCollection().remove(participacion);
                idCAOld = em.merge(idCAOld);
            }
            if (idCANew != null && !idCANew.equals(idCAOld)) {
                idCANew.getParticipacionCollection().add(participacion);
                idCANew = em.merge(idCANew);
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno : cuerpoacademicoexternoCollectionOld) {
                if (!cuerpoacademicoexternoCollectionNew.contains(cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno)) {
                    cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno.setIdParticipacion(null);
                    cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionOldCuerpoacademicoexterno);
                }
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno : cuerpoacademicoexternoCollectionNew) {
                if (!cuerpoacademicoexternoCollectionOld.contains(cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno)) {
                    Participacion oldIdParticipacionOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno = cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.getIdParticipacion();
                    cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.setIdParticipacion(participacion);
                    cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno);
                    if (oldIdParticipacionOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno != null && !oldIdParticipacionOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.equals(participacion)) {
                        oldIdParticipacionOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno.getCuerpoacademicoexternoCollection().remove(cuerpoacademicoexternoCollectionNewCuerpoacademicoexterno);
                        oldIdParticipacionOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno = em.merge(oldIdParticipacionOfCuerpoacademicoexternoCollectionNewCuerpoacademicoexterno);
                    }
                }
            }
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep : cuerpoAcademicoPromepCollectionNew) {
                if (!cuerpoAcademicoPromepCollectionOld.contains(cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep)) {
                    Participacion oldIdParticipacionOfCuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep = cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep.getIdParticipacion();
                    cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep.setIdParticipacion(participacion);
                    cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep = em.merge(cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep);
                    if (oldIdParticipacionOfCuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep != null && !oldIdParticipacionOfCuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep.equals(participacion)) {
                        oldIdParticipacionOfCuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep.getCuerpoAcademicoPromepCollection().remove(cuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep);
                        oldIdParticipacionOfCuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep = em.merge(oldIdParticipacionOfCuerpoAcademicoPromepCollectionNewCuerpoAcademicoPromep);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = participacion.getIdParticipacion();
                if (findParticipacion(id) == null) {
                    throw new NonexistentEntityException("The participacion with id " + id + " no longer exists.");
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
            Participacion participacion;
            try {
                participacion = em.getReference(Participacion.class, id);
                participacion.getIdParticipacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The participacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CuerpoAcademicoPromep> cuerpoAcademicoPromepCollectionOrphanCheck = participacion.getCuerpoAcademicoPromepCollection();
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepCollectionOrphanCheckCuerpoAcademicoPromep : cuerpoAcademicoPromepCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participacion (" + participacion + ") cannot be destroyed since the CuerpoAcademicoPromep " + cuerpoAcademicoPromepCollectionOrphanCheckCuerpoAcademicoPromep + " in its cuerpoAcademicoPromepCollection field has a non-nullable idParticipacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CuerpoAcademico idCA = participacion.getIdCA();
            if (idCA != null) {
                idCA.getParticipacionCollection().remove(participacion);
                idCA = em.merge(idCA);
            }
            Collection<Cuerpoacademicoexterno> cuerpoacademicoexternoCollection = participacion.getCuerpoacademicoexternoCollection();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoCollectionCuerpoacademicoexterno : cuerpoacademicoexternoCollection) {
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno.setIdParticipacion(null);
                cuerpoacademicoexternoCollectionCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoCollectionCuerpoacademicoexterno);
            }
            em.remove(participacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Participacion> findParticipacionEntities() {
        return findParticipacionEntities(true, -1, -1);
    }

    public List<Participacion> findParticipacionEntities(int maxResults, int firstResult) {
        return findParticipacionEntities(false, maxResults, firstResult);
    }

    private List<Participacion> findParticipacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Participacion.class));
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

    public Participacion findParticipacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Participacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticipacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Participacion> rt = cq.from(Participacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
