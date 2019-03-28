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
public class CuerpoAcademicoJpaController implements Serializable {

    public CuerpoAcademicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuerpoAcademico cuerpoAcademico) {
        if (cuerpoAcademico.getParticipacionCollection() == null) {
            cuerpoAcademico.setParticipacionCollection(new ArrayList<Participacion>());
        }
        if (cuerpoAcademico.getColaboradorCuerpoacademicoCollection() == null) {
            cuerpoAcademico.setColaboradorCuerpoacademicoCollection(new ArrayList<ColaboradorCuerpoacademico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProyectoInvestigacionconjunto proyectoInvestigacionconjunto = cuerpoAcademico.getProyectoInvestigacionconjunto();
            if (proyectoInvestigacionconjunto != null) {
                proyectoInvestigacionconjunto = em.getReference(proyectoInvestigacionconjunto.getClass(), proyectoInvestigacionconjunto.getIdProyectoInvestigacionConjunto());
                cuerpoAcademico.setProyectoInvestigacionconjunto(proyectoInvestigacionconjunto);
            }
            Pe pe = cuerpoAcademico.getPe();
            if (pe != null) {
                pe = em.getReference(pe.getClass(), pe.getIdPE());
                cuerpoAcademico.setPe(pe);
            }
            Collection<Participacion> attachedParticipacionCollection = new ArrayList<Participacion>();
            for (Participacion participacionCollectionParticipacionToAttach : cuerpoAcademico.getParticipacionCollection()) {
                participacionCollectionParticipacionToAttach = em.getReference(participacionCollectionParticipacionToAttach.getClass(), participacionCollectionParticipacionToAttach.getIdParticipacion());
                attachedParticipacionCollection.add(participacionCollectionParticipacionToAttach);
            }
            cuerpoAcademico.setParticipacionCollection(attachedParticipacionCollection);
            Collection<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoCollection = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach : cuerpoAcademico.getColaboradorCuerpoacademicoCollection()) {
                colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoCollection.add(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademicoToAttach);
            }
            cuerpoAcademico.setColaboradorCuerpoacademicoCollection(attachedColaboradorCuerpoacademicoCollection);
            em.persist(cuerpoAcademico);
            if (proyectoInvestigacionconjunto != null) {
                CuerpoAcademico oldCuerpoAcademicoOfProyectoInvestigacionconjunto = proyectoInvestigacionconjunto.getCuerpoAcademico();
                if (oldCuerpoAcademicoOfProyectoInvestigacionconjunto != null) {
                    oldCuerpoAcademicoOfProyectoInvestigacionconjunto.setProyectoInvestigacionconjunto(null);
                    oldCuerpoAcademicoOfProyectoInvestigacionconjunto = em.merge(oldCuerpoAcademicoOfProyectoInvestigacionconjunto);
                }
                proyectoInvestigacionconjunto.setCuerpoAcademico(cuerpoAcademico);
                proyectoInvestigacionconjunto = em.merge(proyectoInvestigacionconjunto);
            }
            if (pe != null) {
                CuerpoAcademico oldCuerpoAcademicoOfPe = pe.getCuerpoAcademico();
                if (oldCuerpoAcademicoOfPe != null) {
                    oldCuerpoAcademicoOfPe.setPe(null);
                    oldCuerpoAcademicoOfPe = em.merge(oldCuerpoAcademicoOfPe);
                }
                pe.setCuerpoAcademico(cuerpoAcademico);
                pe = em.merge(pe);
            }
            for (Participacion participacionCollectionParticipacion : cuerpoAcademico.getParticipacionCollection()) {
                CuerpoAcademico oldIdCAOfParticipacionCollectionParticipacion = participacionCollectionParticipacion.getIdCA();
                participacionCollectionParticipacion.setIdCA(cuerpoAcademico);
                participacionCollectionParticipacion = em.merge(participacionCollectionParticipacion);
                if (oldIdCAOfParticipacionCollectionParticipacion != null) {
                    oldIdCAOfParticipacionCollectionParticipacion.getParticipacionCollection().remove(participacionCollectionParticipacion);
                    oldIdCAOfParticipacionCollectionParticipacion = em.merge(oldIdCAOfParticipacionCollectionParticipacion);
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico : cuerpoAcademico.getColaboradorCuerpoacademicoCollection()) {
                CuerpoAcademico oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico = colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico.getCuerpoAcademico();
                colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico.setCuerpoAcademico(cuerpoAcademico);
                colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico);
                if (oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico != null) {
                    oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico.getColaboradorCuerpoacademicoCollection().remove(colaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico);
                    oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico = em.merge(oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionColaboradorCuerpoacademico);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuerpoAcademico cuerpoAcademico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CuerpoAcademico persistentCuerpoAcademico = em.find(CuerpoAcademico.class, cuerpoAcademico.getIdCuerpoAcademico());
            ProyectoInvestigacionconjunto proyectoInvestigacionconjuntoOld = persistentCuerpoAcademico.getProyectoInvestigacionconjunto();
            ProyectoInvestigacionconjunto proyectoInvestigacionconjuntoNew = cuerpoAcademico.getProyectoInvestigacionconjunto();
            Pe peOld = persistentCuerpoAcademico.getPe();
            Pe peNew = cuerpoAcademico.getPe();
            Collection<Participacion> participacionCollectionOld = persistentCuerpoAcademico.getParticipacionCollection();
            Collection<Participacion> participacionCollectionNew = cuerpoAcademico.getParticipacionCollection();
            Collection<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoCollectionOld = persistentCuerpoAcademico.getColaboradorCuerpoacademicoCollection();
            Collection<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoCollectionNew = cuerpoAcademico.getColaboradorCuerpoacademicoCollection();
            List<String> illegalOrphanMessages = null;
            if (proyectoInvestigacionconjuntoOld != null && !proyectoInvestigacionconjuntoOld.equals(proyectoInvestigacionconjuntoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ProyectoInvestigacionconjunto " + proyectoInvestigacionconjuntoOld + " since its cuerpoAcademico field is not nullable.");
            }
            if (peOld != null && !peOld.equals(peNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pe " + peOld + " since its cuerpoAcademico field is not nullable.");
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionOldColaboradorCuerpoacademico : colaboradorCuerpoacademicoCollectionOld) {
                if (!colaboradorCuerpoacademicoCollectionNew.contains(colaboradorCuerpoacademicoCollectionOldColaboradorCuerpoacademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoCollectionOldColaboradorCuerpoacademico + " since its cuerpoAcademico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proyectoInvestigacionconjuntoNew != null) {
                proyectoInvestigacionconjuntoNew = em.getReference(proyectoInvestigacionconjuntoNew.getClass(), proyectoInvestigacionconjuntoNew.getIdProyectoInvestigacionConjunto());
                cuerpoAcademico.setProyectoInvestigacionconjunto(proyectoInvestigacionconjuntoNew);
            }
            if (peNew != null) {
                peNew = em.getReference(peNew.getClass(), peNew.getIdPE());
                cuerpoAcademico.setPe(peNew);
            }
            Collection<Participacion> attachedParticipacionCollectionNew = new ArrayList<Participacion>();
            for (Participacion participacionCollectionNewParticipacionToAttach : participacionCollectionNew) {
                participacionCollectionNewParticipacionToAttach = em.getReference(participacionCollectionNewParticipacionToAttach.getClass(), participacionCollectionNewParticipacionToAttach.getIdParticipacion());
                attachedParticipacionCollectionNew.add(participacionCollectionNewParticipacionToAttach);
            }
            participacionCollectionNew = attachedParticipacionCollectionNew;
            cuerpoAcademico.setParticipacionCollection(participacionCollectionNew);
            Collection<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoCollectionNew = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach : colaboradorCuerpoacademicoCollectionNew) {
                colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoCollectionNew.add(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademicoToAttach);
            }
            colaboradorCuerpoacademicoCollectionNew = attachedColaboradorCuerpoacademicoCollectionNew;
            cuerpoAcademico.setColaboradorCuerpoacademicoCollection(colaboradorCuerpoacademicoCollectionNew);
            cuerpoAcademico = em.merge(cuerpoAcademico);
            if (proyectoInvestigacionconjuntoNew != null && !proyectoInvestigacionconjuntoNew.equals(proyectoInvestigacionconjuntoOld)) {
                CuerpoAcademico oldCuerpoAcademicoOfProyectoInvestigacionconjunto = proyectoInvestigacionconjuntoNew.getCuerpoAcademico();
                if (oldCuerpoAcademicoOfProyectoInvestigacionconjunto != null) {
                    oldCuerpoAcademicoOfProyectoInvestigacionconjunto.setProyectoInvestigacionconjunto(null);
                    oldCuerpoAcademicoOfProyectoInvestigacionconjunto = em.merge(oldCuerpoAcademicoOfProyectoInvestigacionconjunto);
                }
                proyectoInvestigacionconjuntoNew.setCuerpoAcademico(cuerpoAcademico);
                proyectoInvestigacionconjuntoNew = em.merge(proyectoInvestigacionconjuntoNew);
            }
            if (peNew != null && !peNew.equals(peOld)) {
                CuerpoAcademico oldCuerpoAcademicoOfPe = peNew.getCuerpoAcademico();
                if (oldCuerpoAcademicoOfPe != null) {
                    oldCuerpoAcademicoOfPe.setPe(null);
                    oldCuerpoAcademicoOfPe = em.merge(oldCuerpoAcademicoOfPe);
                }
                peNew.setCuerpoAcademico(cuerpoAcademico);
                peNew = em.merge(peNew);
            }
            for (Participacion participacionCollectionOldParticipacion : participacionCollectionOld) {
                if (!participacionCollectionNew.contains(participacionCollectionOldParticipacion)) {
                    participacionCollectionOldParticipacion.setIdCA(null);
                    participacionCollectionOldParticipacion = em.merge(participacionCollectionOldParticipacion);
                }
            }
            for (Participacion participacionCollectionNewParticipacion : participacionCollectionNew) {
                if (!participacionCollectionOld.contains(participacionCollectionNewParticipacion)) {
                    CuerpoAcademico oldIdCAOfParticipacionCollectionNewParticipacion = participacionCollectionNewParticipacion.getIdCA();
                    participacionCollectionNewParticipacion.setIdCA(cuerpoAcademico);
                    participacionCollectionNewParticipacion = em.merge(participacionCollectionNewParticipacion);
                    if (oldIdCAOfParticipacionCollectionNewParticipacion != null && !oldIdCAOfParticipacionCollectionNewParticipacion.equals(cuerpoAcademico)) {
                        oldIdCAOfParticipacionCollectionNewParticipacion.getParticipacionCollection().remove(participacionCollectionNewParticipacion);
                        oldIdCAOfParticipacionCollectionNewParticipacion = em.merge(oldIdCAOfParticipacionCollectionNewParticipacion);
                    }
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico : colaboradorCuerpoacademicoCollectionNew) {
                if (!colaboradorCuerpoacademicoCollectionOld.contains(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico)) {
                    CuerpoAcademico oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico = colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.getCuerpoAcademico();
                    colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.setCuerpoAcademico(cuerpoAcademico);
                    colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico);
                    if (oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico != null && !oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.equals(cuerpoAcademico)) {
                        oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico.getColaboradorCuerpoacademicoCollection().remove(colaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico);
                        oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico = em.merge(oldCuerpoAcademicoOfColaboradorCuerpoacademicoCollectionNewColaboradorCuerpoacademico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuerpoAcademico.getIdCuerpoAcademico();
                if (findCuerpoAcademico(id) == null) {
                    throw new NonexistentEntityException("The cuerpoAcademico with id " + id + " no longer exists.");
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
            CuerpoAcademico cuerpoAcademico;
            try {
                cuerpoAcademico = em.getReference(CuerpoAcademico.class, id);
                cuerpoAcademico.getIdCuerpoAcademico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuerpoAcademico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ProyectoInvestigacionconjunto proyectoInvestigacionconjuntoOrphanCheck = cuerpoAcademico.getProyectoInvestigacionconjunto();
            if (proyectoInvestigacionconjuntoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CuerpoAcademico (" + cuerpoAcademico + ") cannot be destroyed since the ProyectoInvestigacionconjunto " + proyectoInvestigacionconjuntoOrphanCheck + " in its proyectoInvestigacionconjunto field has a non-nullable cuerpoAcademico field.");
            }
            Pe peOrphanCheck = cuerpoAcademico.getPe();
            if (peOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CuerpoAcademico (" + cuerpoAcademico + ") cannot be destroyed since the Pe " + peOrphanCheck + " in its pe field has a non-nullable cuerpoAcademico field.");
            }
            Collection<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoCollectionOrphanCheck = cuerpoAcademico.getColaboradorCuerpoacademicoCollection();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoCollectionOrphanCheckColaboradorCuerpoacademico : colaboradorCuerpoacademicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CuerpoAcademico (" + cuerpoAcademico + ") cannot be destroyed since the ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoCollectionOrphanCheckColaboradorCuerpoacademico + " in its colaboradorCuerpoacademicoCollection field has a non-nullable cuerpoAcademico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Participacion> participacionCollection = cuerpoAcademico.getParticipacionCollection();
            for (Participacion participacionCollectionParticipacion : participacionCollection) {
                participacionCollectionParticipacion.setIdCA(null);
                participacionCollectionParticipacion = em.merge(participacionCollectionParticipacion);
            }
            em.remove(cuerpoAcademico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuerpoAcademico> findCuerpoAcademicoEntities() {
        return findCuerpoAcademicoEntities(true, -1, -1);
    }

    public List<CuerpoAcademico> findCuerpoAcademicoEntities(int maxResults, int firstResult) {
        return findCuerpoAcademicoEntities(false, maxResults, firstResult);
    }

    private List<CuerpoAcademico> findCuerpoAcademicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuerpoAcademico.class));
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

    public CuerpoAcademico findCuerpoAcademico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuerpoAcademico.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuerpoAcademicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuerpoAcademico> rt = cq.from(CuerpoAcademico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
