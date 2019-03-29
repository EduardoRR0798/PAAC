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
import entity.CuerpoAcademico;
import entity.Cuerpoacademicoexterno;
import java.util.ArrayList;
import java.util.List;
import entity.CuerpoAcademicoPromep;
import entity.Participacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

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
        if (participacion.getCuerpoacademicoexternoList() == null) {
            participacion.setCuerpoacademicoexternoList(new ArrayList<Cuerpoacademicoexterno>());
        }
        if (participacion.getCuerpoAcademicoPromepList() == null) {
            participacion.setCuerpoAcademicoPromepList(new ArrayList<CuerpoAcademicoPromep>());
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
            List<Cuerpoacademicoexterno> attachedCuerpoacademicoexternoList = new ArrayList<Cuerpoacademicoexterno>();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoListCuerpoacademicoexternoToAttach : participacion.getCuerpoacademicoexternoList()) {
                cuerpoacademicoexternoListCuerpoacademicoexternoToAttach = em.getReference(cuerpoacademicoexternoListCuerpoacademicoexternoToAttach.getClass(), cuerpoacademicoexternoListCuerpoacademicoexternoToAttach.getIdCuerpoeExterno());
                attachedCuerpoacademicoexternoList.add(cuerpoacademicoexternoListCuerpoacademicoexternoToAttach);
            }
            participacion.setCuerpoacademicoexternoList(attachedCuerpoacademicoexternoList);
            List<CuerpoAcademicoPromep> attachedCuerpoAcademicoPromepList = new ArrayList<CuerpoAcademicoPromep>();
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepListCuerpoAcademicoPromepToAttach : participacion.getCuerpoAcademicoPromepList()) {
                cuerpoAcademicoPromepListCuerpoAcademicoPromepToAttach = em.getReference(cuerpoAcademicoPromepListCuerpoAcademicoPromepToAttach.getClass(), cuerpoAcademicoPromepListCuerpoAcademicoPromepToAttach.getIdPROMEP());
                attachedCuerpoAcademicoPromepList.add(cuerpoAcademicoPromepListCuerpoAcademicoPromepToAttach);
            }
            participacion.setCuerpoAcademicoPromepList(attachedCuerpoAcademicoPromepList);
            em.persist(participacion);
            if (idCA != null) {
                idCA.getParticipacionList().add(participacion);
                idCA = em.merge(idCA);
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoListCuerpoacademicoexterno : participacion.getCuerpoacademicoexternoList()) {
                Participacion oldIdParticipacionOfCuerpoacademicoexternoListCuerpoacademicoexterno = cuerpoacademicoexternoListCuerpoacademicoexterno.getIdParticipacion();
                cuerpoacademicoexternoListCuerpoacademicoexterno.setIdParticipacion(participacion);
                cuerpoacademicoexternoListCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoListCuerpoacademicoexterno);
                if (oldIdParticipacionOfCuerpoacademicoexternoListCuerpoacademicoexterno != null) {
                    oldIdParticipacionOfCuerpoacademicoexternoListCuerpoacademicoexterno.getCuerpoacademicoexternoList().remove(cuerpoacademicoexternoListCuerpoacademicoexterno);
                    oldIdParticipacionOfCuerpoacademicoexternoListCuerpoacademicoexterno = em.merge(oldIdParticipacionOfCuerpoacademicoexternoListCuerpoacademicoexterno);
                }
            }
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepListCuerpoAcademicoPromep : participacion.getCuerpoAcademicoPromepList()) {
                Participacion oldIdParticipacionOfCuerpoAcademicoPromepListCuerpoAcademicoPromep = cuerpoAcademicoPromepListCuerpoAcademicoPromep.getIdParticipacion();
                cuerpoAcademicoPromepListCuerpoAcademicoPromep.setIdParticipacion(participacion);
                cuerpoAcademicoPromepListCuerpoAcademicoPromep = em.merge(cuerpoAcademicoPromepListCuerpoAcademicoPromep);
                if (oldIdParticipacionOfCuerpoAcademicoPromepListCuerpoAcademicoPromep != null) {
                    oldIdParticipacionOfCuerpoAcademicoPromepListCuerpoAcademicoPromep.getCuerpoAcademicoPromepList().remove(cuerpoAcademicoPromepListCuerpoAcademicoPromep);
                    oldIdParticipacionOfCuerpoAcademicoPromepListCuerpoAcademicoPromep = em.merge(oldIdParticipacionOfCuerpoAcademicoPromepListCuerpoAcademicoPromep);
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
            List<Cuerpoacademicoexterno> cuerpoacademicoexternoListOld = persistentParticipacion.getCuerpoacademicoexternoList();
            List<Cuerpoacademicoexterno> cuerpoacademicoexternoListNew = participacion.getCuerpoacademicoexternoList();
            List<CuerpoAcademicoPromep> cuerpoAcademicoPromepListOld = persistentParticipacion.getCuerpoAcademicoPromepList();
            List<CuerpoAcademicoPromep> cuerpoAcademicoPromepListNew = participacion.getCuerpoAcademicoPromepList();
            List<String> illegalOrphanMessages = null;
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepListOldCuerpoAcademicoPromep : cuerpoAcademicoPromepListOld) {
                if (!cuerpoAcademicoPromepListNew.contains(cuerpoAcademicoPromepListOldCuerpoAcademicoPromep)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuerpoAcademicoPromep " + cuerpoAcademicoPromepListOldCuerpoAcademicoPromep + " since its idParticipacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCANew != null) {
                idCANew = em.getReference(idCANew.getClass(), idCANew.getIdCuerpoAcademico());
                participacion.setIdCA(idCANew);
            }
            List<Cuerpoacademicoexterno> attachedCuerpoacademicoexternoListNew = new ArrayList<Cuerpoacademicoexterno>();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoListNewCuerpoacademicoexternoToAttach : cuerpoacademicoexternoListNew) {
                cuerpoacademicoexternoListNewCuerpoacademicoexternoToAttach = em.getReference(cuerpoacademicoexternoListNewCuerpoacademicoexternoToAttach.getClass(), cuerpoacademicoexternoListNewCuerpoacademicoexternoToAttach.getIdCuerpoeExterno());
                attachedCuerpoacademicoexternoListNew.add(cuerpoacademicoexternoListNewCuerpoacademicoexternoToAttach);
            }
            cuerpoacademicoexternoListNew = attachedCuerpoacademicoexternoListNew;
            participacion.setCuerpoacademicoexternoList(cuerpoacademicoexternoListNew);
            List<CuerpoAcademicoPromep> attachedCuerpoAcademicoPromepListNew = new ArrayList<CuerpoAcademicoPromep>();
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepListNewCuerpoAcademicoPromepToAttach : cuerpoAcademicoPromepListNew) {
                cuerpoAcademicoPromepListNewCuerpoAcademicoPromepToAttach = em.getReference(cuerpoAcademicoPromepListNewCuerpoAcademicoPromepToAttach.getClass(), cuerpoAcademicoPromepListNewCuerpoAcademicoPromepToAttach.getIdPROMEP());
                attachedCuerpoAcademicoPromepListNew.add(cuerpoAcademicoPromepListNewCuerpoAcademicoPromepToAttach);
            }
            cuerpoAcademicoPromepListNew = attachedCuerpoAcademicoPromepListNew;
            participacion.setCuerpoAcademicoPromepList(cuerpoAcademicoPromepListNew);
            participacion = em.merge(participacion);
            if (idCAOld != null && !idCAOld.equals(idCANew)) {
                idCAOld.getParticipacionList().remove(participacion);
                idCAOld = em.merge(idCAOld);
            }
            if (idCANew != null && !idCANew.equals(idCAOld)) {
                idCANew.getParticipacionList().add(participacion);
                idCANew = em.merge(idCANew);
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoListOldCuerpoacademicoexterno : cuerpoacademicoexternoListOld) {
                if (!cuerpoacademicoexternoListNew.contains(cuerpoacademicoexternoListOldCuerpoacademicoexterno)) {
                    cuerpoacademicoexternoListOldCuerpoacademicoexterno.setIdParticipacion(null);
                    cuerpoacademicoexternoListOldCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoListOldCuerpoacademicoexterno);
                }
            }
            for (Cuerpoacademicoexterno cuerpoacademicoexternoListNewCuerpoacademicoexterno : cuerpoacademicoexternoListNew) {
                if (!cuerpoacademicoexternoListOld.contains(cuerpoacademicoexternoListNewCuerpoacademicoexterno)) {
                    Participacion oldIdParticipacionOfCuerpoacademicoexternoListNewCuerpoacademicoexterno = cuerpoacademicoexternoListNewCuerpoacademicoexterno.getIdParticipacion();
                    cuerpoacademicoexternoListNewCuerpoacademicoexterno.setIdParticipacion(participacion);
                    cuerpoacademicoexternoListNewCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoListNewCuerpoacademicoexterno);
                    if (oldIdParticipacionOfCuerpoacademicoexternoListNewCuerpoacademicoexterno != null && !oldIdParticipacionOfCuerpoacademicoexternoListNewCuerpoacademicoexterno.equals(participacion)) {
                        oldIdParticipacionOfCuerpoacademicoexternoListNewCuerpoacademicoexterno.getCuerpoacademicoexternoList().remove(cuerpoacademicoexternoListNewCuerpoacademicoexterno);
                        oldIdParticipacionOfCuerpoacademicoexternoListNewCuerpoacademicoexterno = em.merge(oldIdParticipacionOfCuerpoacademicoexternoListNewCuerpoacademicoexterno);
                    }
                }
            }
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepListNewCuerpoAcademicoPromep : cuerpoAcademicoPromepListNew) {
                if (!cuerpoAcademicoPromepListOld.contains(cuerpoAcademicoPromepListNewCuerpoAcademicoPromep)) {
                    Participacion oldIdParticipacionOfCuerpoAcademicoPromepListNewCuerpoAcademicoPromep = cuerpoAcademicoPromepListNewCuerpoAcademicoPromep.getIdParticipacion();
                    cuerpoAcademicoPromepListNewCuerpoAcademicoPromep.setIdParticipacion(participacion);
                    cuerpoAcademicoPromepListNewCuerpoAcademicoPromep = em.merge(cuerpoAcademicoPromepListNewCuerpoAcademicoPromep);
                    if (oldIdParticipacionOfCuerpoAcademicoPromepListNewCuerpoAcademicoPromep != null && !oldIdParticipacionOfCuerpoAcademicoPromepListNewCuerpoAcademicoPromep.equals(participacion)) {
                        oldIdParticipacionOfCuerpoAcademicoPromepListNewCuerpoAcademicoPromep.getCuerpoAcademicoPromepList().remove(cuerpoAcademicoPromepListNewCuerpoAcademicoPromep);
                        oldIdParticipacionOfCuerpoAcademicoPromepListNewCuerpoAcademicoPromep = em.merge(oldIdParticipacionOfCuerpoAcademicoPromepListNewCuerpoAcademicoPromep);
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
            List<CuerpoAcademicoPromep> cuerpoAcademicoPromepListOrphanCheck = participacion.getCuerpoAcademicoPromepList();
            for (CuerpoAcademicoPromep cuerpoAcademicoPromepListOrphanCheckCuerpoAcademicoPromep : cuerpoAcademicoPromepListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Participacion (" + participacion + ") cannot be destroyed since the CuerpoAcademicoPromep " + cuerpoAcademicoPromepListOrphanCheckCuerpoAcademicoPromep + " in its cuerpoAcademicoPromepList field has a non-nullable idParticipacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CuerpoAcademico idCA = participacion.getIdCA();
            if (idCA != null) {
                idCA.getParticipacionList().remove(participacion);
                idCA = em.merge(idCA);
            }
            List<Cuerpoacademicoexterno> cuerpoacademicoexternoList = participacion.getCuerpoacademicoexternoList();
            for (Cuerpoacademicoexterno cuerpoacademicoexternoListCuerpoacademicoexterno : cuerpoacademicoexternoList) {
                cuerpoacademicoexternoListCuerpoacademicoexterno.setIdParticipacion(null);
                cuerpoacademicoexternoListCuerpoacademicoexterno = em.merge(cuerpoacademicoexternoListCuerpoacademicoexterno);
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
