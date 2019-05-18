package persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.ProyectoInvestigacionconjunto;
import entity.Pe;
import entity.Participacion;
import java.util.ArrayList;
import java.util.List;
import entity.Producto;
import entity.ColaboradorCuerpoacademico;
import entity.CuerpoAcademico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Eduardo Rosas Rivera
 */
public class CuerpoAcademicoJpaController implements Serializable {

    public CuerpoAcademicoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("PAACPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuerpoAcademico cuerpoAcademico) {
        if (cuerpoAcademico.getParticipacionList() == null) {
            cuerpoAcademico.setParticipacionList(new ArrayList<Participacion>());
        }
        if (cuerpoAcademico.getProductoList() == null) {
            cuerpoAcademico.setProductoList(new ArrayList<Producto>());
        }
        if (cuerpoAcademico.getColaboradorCuerpoacademicoList() == null) {
            cuerpoAcademico.setColaboradorCuerpoacademicoList(new ArrayList<ColaboradorCuerpoacademico>());
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
            List<Participacion> attachedParticipacionList = new ArrayList<Participacion>();
            for (Participacion participacionListParticipacionToAttach : cuerpoAcademico.getParticipacionList()) {
                participacionListParticipacionToAttach = em.getReference(participacionListParticipacionToAttach.getClass(), participacionListParticipacionToAttach.getIdParticipacion());
                attachedParticipacionList.add(participacionListParticipacionToAttach);
            }
            cuerpoAcademico.setParticipacionList(attachedParticipacionList);
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : cuerpoAcademico.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getIdProducto());
                attachedProductoList.add(productoListProductoToAttach);
            }
            cuerpoAcademico.setProductoList(attachedProductoList);
            List<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoList = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach : cuerpoAcademico.getColaboradorCuerpoacademicoList()) {
                colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoList.add(colaboradorCuerpoacademicoListColaboradorCuerpoacademicoToAttach);
            }
            cuerpoAcademico.setColaboradorCuerpoacademicoList(attachedColaboradorCuerpoacademicoList);
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
            for (Participacion participacionListParticipacion : cuerpoAcademico.getParticipacionList()) {
                CuerpoAcademico oldIdCAOfParticipacionListParticipacion = participacionListParticipacion.getIdCA();
                participacionListParticipacion.setIdCA(cuerpoAcademico);
                participacionListParticipacion = em.merge(participacionListParticipacion);
                if (oldIdCAOfParticipacionListParticipacion != null) {
                    oldIdCAOfParticipacionListParticipacion.getParticipacionList().remove(participacionListParticipacion);
                    oldIdCAOfParticipacionListParticipacion = em.merge(oldIdCAOfParticipacionListParticipacion);
                }
            }
            for (Producto productoListProducto : cuerpoAcademico.getProductoList()) {
                CuerpoAcademico oldIdCuerpoAcademicoOfProductoListProducto = productoListProducto.getIdCuerpoAcademico();
                productoListProducto.setIdCuerpoAcademico(cuerpoAcademico);
                productoListProducto = em.merge(productoListProducto);
                if (oldIdCuerpoAcademicoOfProductoListProducto != null) {
                    oldIdCuerpoAcademicoOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldIdCuerpoAcademicoOfProductoListProducto = em.merge(oldIdCuerpoAcademicoOfProductoListProducto);
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListColaboradorCuerpoacademico : cuerpoAcademico.getColaboradorCuerpoacademicoList()) {
                CuerpoAcademico oldCuerpoAcademicoOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico = colaboradorCuerpoacademicoListColaboradorCuerpoacademico.getCuerpoAcademico();
                colaboradorCuerpoacademicoListColaboradorCuerpoacademico.setCuerpoAcademico(cuerpoAcademico);
                colaboradorCuerpoacademicoListColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoListColaboradorCuerpoacademico);
                if (oldCuerpoAcademicoOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico != null) {
                    oldCuerpoAcademicoOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademicoListColaboradorCuerpoacademico);
                    oldCuerpoAcademicoOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico = em.merge(oldCuerpoAcademicoOfColaboradorCuerpoacademicoListColaboradorCuerpoacademico);
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
            List<Participacion> participacionListOld = persistentCuerpoAcademico.getParticipacionList();
            List<Participacion> participacionListNew = cuerpoAcademico.getParticipacionList();
            List<Producto> productoListOld = persistentCuerpoAcademico.getProductoList();
            List<Producto> productoListNew = cuerpoAcademico.getProductoList();
            List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoListOld = persistentCuerpoAcademico.getColaboradorCuerpoacademicoList();
            List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoListNew = cuerpoAcademico.getColaboradorCuerpoacademicoList();
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
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListOldColaboradorCuerpoacademico : colaboradorCuerpoacademicoListOld) {
                if (!colaboradorCuerpoacademicoListNew.contains(colaboradorCuerpoacademicoListOldColaboradorCuerpoacademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoListOldColaboradorCuerpoacademico + " since its cuerpoAcademico field is not nullable.");
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
            List<Participacion> attachedParticipacionListNew = new ArrayList<Participacion>();
            for (Participacion participacionListNewParticipacionToAttach : participacionListNew) {
                participacionListNewParticipacionToAttach = em.getReference(participacionListNewParticipacionToAttach.getClass(), participacionListNewParticipacionToAttach.getIdParticipacion());
                attachedParticipacionListNew.add(participacionListNewParticipacionToAttach);
            }
            participacionListNew = attachedParticipacionListNew;
            cuerpoAcademico.setParticipacionList(participacionListNew);
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getIdProducto());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            cuerpoAcademico.setProductoList(productoListNew);
            List<ColaboradorCuerpoacademico> attachedColaboradorCuerpoacademicoListNew = new ArrayList<ColaboradorCuerpoacademico>();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach : colaboradorCuerpoacademicoListNew) {
                colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach = em.getReference(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach.getClass(), colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach.getColaboradorCuerpoacademicoPK());
                attachedColaboradorCuerpoacademicoListNew.add(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademicoToAttach);
            }
            colaboradorCuerpoacademicoListNew = attachedColaboradorCuerpoacademicoListNew;
            cuerpoAcademico.setColaboradorCuerpoacademicoList(colaboradorCuerpoacademicoListNew);
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
            for (Participacion participacionListOldParticipacion : participacionListOld) {
                if (!participacionListNew.contains(participacionListOldParticipacion)) {
                    participacionListOldParticipacion.setIdCA(null);
                    participacionListOldParticipacion = em.merge(participacionListOldParticipacion);
                }
            }
            for (Participacion participacionListNewParticipacion : participacionListNew) {
                if (!participacionListOld.contains(participacionListNewParticipacion)) {
                    CuerpoAcademico oldIdCAOfParticipacionListNewParticipacion = participacionListNewParticipacion.getIdCA();
                    participacionListNewParticipacion.setIdCA(cuerpoAcademico);
                    participacionListNewParticipacion = em.merge(participacionListNewParticipacion);
                    if (oldIdCAOfParticipacionListNewParticipacion != null && !oldIdCAOfParticipacionListNewParticipacion.equals(cuerpoAcademico)) {
                        oldIdCAOfParticipacionListNewParticipacion.getParticipacionList().remove(participacionListNewParticipacion);
                        oldIdCAOfParticipacionListNewParticipacion = em.merge(oldIdCAOfParticipacionListNewParticipacion);
                    }
                }
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setIdCuerpoAcademico(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    CuerpoAcademico oldIdCuerpoAcademicoOfProductoListNewProducto = productoListNewProducto.getIdCuerpoAcademico();
                    productoListNewProducto.setIdCuerpoAcademico(cuerpoAcademico);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldIdCuerpoAcademicoOfProductoListNewProducto != null && !oldIdCuerpoAcademicoOfProductoListNewProducto.equals(cuerpoAcademico)) {
                        oldIdCuerpoAcademicoOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldIdCuerpoAcademicoOfProductoListNewProducto = em.merge(oldIdCuerpoAcademicoOfProductoListNewProducto);
                    }
                }
            }
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico : colaboradorCuerpoacademicoListNew) {
                if (!colaboradorCuerpoacademicoListOld.contains(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico)) {
                    CuerpoAcademico oldCuerpoAcademicoOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico = colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.getCuerpoAcademico();
                    colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.setCuerpoAcademico(cuerpoAcademico);
                    colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico = em.merge(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico);
                    if (oldCuerpoAcademicoOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico != null && !oldCuerpoAcademicoOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.equals(cuerpoAcademico)) {
                        oldCuerpoAcademicoOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico.getColaboradorCuerpoacademicoList().remove(colaboradorCuerpoacademicoListNewColaboradorCuerpoacademico);
                        oldCuerpoAcademicoOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico = em.merge(oldCuerpoAcademicoOfColaboradorCuerpoacademicoListNewColaboradorCuerpoacademico);
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
            List<ColaboradorCuerpoacademico> colaboradorCuerpoacademicoListOrphanCheck = cuerpoAcademico.getColaboradorCuerpoacademicoList();
            for (ColaboradorCuerpoacademico colaboradorCuerpoacademicoListOrphanCheckColaboradorCuerpoacademico : colaboradorCuerpoacademicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CuerpoAcademico (" + cuerpoAcademico + ") cannot be destroyed since the ColaboradorCuerpoacademico " + colaboradorCuerpoacademicoListOrphanCheckColaboradorCuerpoacademico + " in its colaboradorCuerpoacademicoList field has a non-nullable cuerpoAcademico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Participacion> participacionList = cuerpoAcademico.getParticipacionList();
            for (Participacion participacionListParticipacion : participacionList) {
                participacionListParticipacion.setIdCA(null);
                participacionListParticipacion = em.merge(participacionListParticipacion);
            }
            List<Producto> productoList = cuerpoAcademico.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setIdCuerpoAcademico(null);
                productoListProducto = em.merge(productoListProducto);
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
