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
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getMemoriaCollection() == null) {
            producto.setMemoriaCollection(new ArrayList<Memoria>());
        }
        if (producto.getCapituloLibroCollection() == null) {
            producto.setCapituloLibroCollection(new ArrayList<CapituloLibro>());
        }
        if (producto.getProductoProyectoCollection() == null) {
            producto.setProductoProyectoCollection(new ArrayList<ProductoProyecto>());
        }
        if (producto.getPrototipoCollection() == null) {
            producto.setPrototipoCollection(new ArrayList<Prototipo>());
        }
        if (producto.getArticuloCollection() == null) {
            producto.setArticuloCollection(new ArrayList<Articulo>());
        }
        if (producto.getTesisCollection() == null) {
            producto.setTesisCollection(new ArrayList<Tesis>());
        }
        if (producto.getLibroCollection() == null) {
            producto.setLibroCollection(new ArrayList<Libro>());
        }
        if (producto.getProductoColaboradorCollection() == null) {
            producto.setProductoColaboradorCollection(new ArrayList<ProductoColaborador>());
        }
        if (producto.getPatenteCollection() == null) {
            producto.setPatenteCollection(new ArrayList<Patente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais idPais = producto.getIdPais();
            if (idPais != null) {
                idPais = em.getReference(idPais.getClass(), idPais.getIdPais());
                producto.setIdPais(idPais);
            }
            Collection<Memoria> attachedMemoriaCollection = new ArrayList<Memoria>();
            for (Memoria memoriaCollectionMemoriaToAttach : producto.getMemoriaCollection()) {
                memoriaCollectionMemoriaToAttach = em.getReference(memoriaCollectionMemoriaToAttach.getClass(), memoriaCollectionMemoriaToAttach.getIdMemoria());
                attachedMemoriaCollection.add(memoriaCollectionMemoriaToAttach);
            }
            producto.setMemoriaCollection(attachedMemoriaCollection);
            Collection<CapituloLibro> attachedCapituloLibroCollection = new ArrayList<CapituloLibro>();
            for (CapituloLibro capituloLibroCollectionCapituloLibroToAttach : producto.getCapituloLibroCollection()) {
                capituloLibroCollectionCapituloLibroToAttach = em.getReference(capituloLibroCollectionCapituloLibroToAttach.getClass(), capituloLibroCollectionCapituloLibroToAttach.getIdCapitulolibro());
                attachedCapituloLibroCollection.add(capituloLibroCollectionCapituloLibroToAttach);
            }
            producto.setCapituloLibroCollection(attachedCapituloLibroCollection);
            Collection<ProductoProyecto> attachedProductoProyectoCollection = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoCollectionProductoProyectoToAttach : producto.getProductoProyectoCollection()) {
                productoProyectoCollectionProductoProyectoToAttach = em.getReference(productoProyectoCollectionProductoProyectoToAttach.getClass(), productoProyectoCollectionProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoCollection.add(productoProyectoCollectionProductoProyectoToAttach);
            }
            producto.setProductoProyectoCollection(attachedProductoProyectoCollection);
            Collection<Prototipo> attachedPrototipoCollection = new ArrayList<Prototipo>();
            for (Prototipo prototipoCollectionPrototipoToAttach : producto.getPrototipoCollection()) {
                prototipoCollectionPrototipoToAttach = em.getReference(prototipoCollectionPrototipoToAttach.getClass(), prototipoCollectionPrototipoToAttach.getIdprototipo());
                attachedPrototipoCollection.add(prototipoCollectionPrototipoToAttach);
            }
            producto.setPrototipoCollection(attachedPrototipoCollection);
            Collection<Articulo> attachedArticuloCollection = new ArrayList<Articulo>();
            for (Articulo articuloCollectionArticuloToAttach : producto.getArticuloCollection()) {
                articuloCollectionArticuloToAttach = em.getReference(articuloCollectionArticuloToAttach.getClass(), articuloCollectionArticuloToAttach.getIdArticulo());
                attachedArticuloCollection.add(articuloCollectionArticuloToAttach);
            }
            producto.setArticuloCollection(attachedArticuloCollection);
            Collection<Tesis> attachedTesisCollection = new ArrayList<Tesis>();
            for (Tesis tesisCollectionTesisToAttach : producto.getTesisCollection()) {
                tesisCollectionTesisToAttach = em.getReference(tesisCollectionTesisToAttach.getClass(), tesisCollectionTesisToAttach.getIdTesis());
                attachedTesisCollection.add(tesisCollectionTesisToAttach);
            }
            producto.setTesisCollection(attachedTesisCollection);
            Collection<Libro> attachedLibroCollection = new ArrayList<Libro>();
            for (Libro libroCollectionLibroToAttach : producto.getLibroCollection()) {
                libroCollectionLibroToAttach = em.getReference(libroCollectionLibroToAttach.getClass(), libroCollectionLibroToAttach.getIdLibro());
                attachedLibroCollection.add(libroCollectionLibroToAttach);
            }
            producto.setLibroCollection(attachedLibroCollection);
            Collection<ProductoColaborador> attachedProductoColaboradorCollection = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorCollectionProductoColaboradorToAttach : producto.getProductoColaboradorCollection()) {
                productoColaboradorCollectionProductoColaboradorToAttach = em.getReference(productoColaboradorCollectionProductoColaboradorToAttach.getClass(), productoColaboradorCollectionProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorCollection.add(productoColaboradorCollectionProductoColaboradorToAttach);
            }
            producto.setProductoColaboradorCollection(attachedProductoColaboradorCollection);
            Collection<Patente> attachedPatenteCollection = new ArrayList<Patente>();
            for (Patente patenteCollectionPatenteToAttach : producto.getPatenteCollection()) {
                patenteCollectionPatenteToAttach = em.getReference(patenteCollectionPatenteToAttach.getClass(), patenteCollectionPatenteToAttach.getIdPatente());
                attachedPatenteCollection.add(patenteCollectionPatenteToAttach);
            }
            producto.setPatenteCollection(attachedPatenteCollection);
            em.persist(producto);
            if (idPais != null) {
                idPais.getProductoCollection().add(producto);
                idPais = em.merge(idPais);
            }
            for (Memoria memoriaCollectionMemoria : producto.getMemoriaCollection()) {
                Producto oldIdProductoOfMemoriaCollectionMemoria = memoriaCollectionMemoria.getIdProducto();
                memoriaCollectionMemoria.setIdProducto(producto);
                memoriaCollectionMemoria = em.merge(memoriaCollectionMemoria);
                if (oldIdProductoOfMemoriaCollectionMemoria != null) {
                    oldIdProductoOfMemoriaCollectionMemoria.getMemoriaCollection().remove(memoriaCollectionMemoria);
                    oldIdProductoOfMemoriaCollectionMemoria = em.merge(oldIdProductoOfMemoriaCollectionMemoria);
                }
            }
            for (CapituloLibro capituloLibroCollectionCapituloLibro : producto.getCapituloLibroCollection()) {
                Producto oldIdProductoOfCapituloLibroCollectionCapituloLibro = capituloLibroCollectionCapituloLibro.getIdProducto();
                capituloLibroCollectionCapituloLibro.setIdProducto(producto);
                capituloLibroCollectionCapituloLibro = em.merge(capituloLibroCollectionCapituloLibro);
                if (oldIdProductoOfCapituloLibroCollectionCapituloLibro != null) {
                    oldIdProductoOfCapituloLibroCollectionCapituloLibro.getCapituloLibroCollection().remove(capituloLibroCollectionCapituloLibro);
                    oldIdProductoOfCapituloLibroCollectionCapituloLibro = em.merge(oldIdProductoOfCapituloLibroCollectionCapituloLibro);
                }
            }
            for (ProductoProyecto productoProyectoCollectionProductoProyecto : producto.getProductoProyectoCollection()) {
                Producto oldProductoOfProductoProyectoCollectionProductoProyecto = productoProyectoCollectionProductoProyecto.getProducto();
                productoProyectoCollectionProductoProyecto.setProducto(producto);
                productoProyectoCollectionProductoProyecto = em.merge(productoProyectoCollectionProductoProyecto);
                if (oldProductoOfProductoProyectoCollectionProductoProyecto != null) {
                    oldProductoOfProductoProyectoCollectionProductoProyecto.getProductoProyectoCollection().remove(productoProyectoCollectionProductoProyecto);
                    oldProductoOfProductoProyectoCollectionProductoProyecto = em.merge(oldProductoOfProductoProyectoCollectionProductoProyecto);
                }
            }
            for (Prototipo prototipoCollectionPrototipo : producto.getPrototipoCollection()) {
                Producto oldIdProductoOfPrototipoCollectionPrototipo = prototipoCollectionPrototipo.getIdProducto();
                prototipoCollectionPrototipo.setIdProducto(producto);
                prototipoCollectionPrototipo = em.merge(prototipoCollectionPrototipo);
                if (oldIdProductoOfPrototipoCollectionPrototipo != null) {
                    oldIdProductoOfPrototipoCollectionPrototipo.getPrototipoCollection().remove(prototipoCollectionPrototipo);
                    oldIdProductoOfPrototipoCollectionPrototipo = em.merge(oldIdProductoOfPrototipoCollectionPrototipo);
                }
            }
            for (Articulo articuloCollectionArticulo : producto.getArticuloCollection()) {
                Producto oldIdProductoOfArticuloCollectionArticulo = articuloCollectionArticulo.getIdProducto();
                articuloCollectionArticulo.setIdProducto(producto);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
                if (oldIdProductoOfArticuloCollectionArticulo != null) {
                    oldIdProductoOfArticuloCollectionArticulo.getArticuloCollection().remove(articuloCollectionArticulo);
                    oldIdProductoOfArticuloCollectionArticulo = em.merge(oldIdProductoOfArticuloCollectionArticulo);
                }
            }
            for (Tesis tesisCollectionTesis : producto.getTesisCollection()) {
                Producto oldIdProductoOfTesisCollectionTesis = tesisCollectionTesis.getIdProducto();
                tesisCollectionTesis.setIdProducto(producto);
                tesisCollectionTesis = em.merge(tesisCollectionTesis);
                if (oldIdProductoOfTesisCollectionTesis != null) {
                    oldIdProductoOfTesisCollectionTesis.getTesisCollection().remove(tesisCollectionTesis);
                    oldIdProductoOfTesisCollectionTesis = em.merge(oldIdProductoOfTesisCollectionTesis);
                }
            }
            for (Libro libroCollectionLibro : producto.getLibroCollection()) {
                Producto oldIdProductoOfLibroCollectionLibro = libroCollectionLibro.getIdProducto();
                libroCollectionLibro.setIdProducto(producto);
                libroCollectionLibro = em.merge(libroCollectionLibro);
                if (oldIdProductoOfLibroCollectionLibro != null) {
                    oldIdProductoOfLibroCollectionLibro.getLibroCollection().remove(libroCollectionLibro);
                    oldIdProductoOfLibroCollectionLibro = em.merge(oldIdProductoOfLibroCollectionLibro);
                }
            }
            for (ProductoColaborador productoColaboradorCollectionProductoColaborador : producto.getProductoColaboradorCollection()) {
                Producto oldProductoOfProductoColaboradorCollectionProductoColaborador = productoColaboradorCollectionProductoColaborador.getProducto();
                productoColaboradorCollectionProductoColaborador.setProducto(producto);
                productoColaboradorCollectionProductoColaborador = em.merge(productoColaboradorCollectionProductoColaborador);
                if (oldProductoOfProductoColaboradorCollectionProductoColaborador != null) {
                    oldProductoOfProductoColaboradorCollectionProductoColaborador.getProductoColaboradorCollection().remove(productoColaboradorCollectionProductoColaborador);
                    oldProductoOfProductoColaboradorCollectionProductoColaborador = em.merge(oldProductoOfProductoColaboradorCollectionProductoColaborador);
                }
            }
            for (Patente patenteCollectionPatente : producto.getPatenteCollection()) {
                Producto oldIdProductoOfPatenteCollectionPatente = patenteCollectionPatente.getIdProducto();
                patenteCollectionPatente.setIdProducto(producto);
                patenteCollectionPatente = em.merge(patenteCollectionPatente);
                if (oldIdProductoOfPatenteCollectionPatente != null) {
                    oldIdProductoOfPatenteCollectionPatente.getPatenteCollection().remove(patenteCollectionPatente);
                    oldIdProductoOfPatenteCollectionPatente = em.merge(oldIdProductoOfPatenteCollectionPatente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Pais idPaisOld = persistentProducto.getIdPais();
            Pais idPaisNew = producto.getIdPais();
            Collection<Memoria> memoriaCollectionOld = persistentProducto.getMemoriaCollection();
            Collection<Memoria> memoriaCollectionNew = producto.getMemoriaCollection();
            Collection<CapituloLibro> capituloLibroCollectionOld = persistentProducto.getCapituloLibroCollection();
            Collection<CapituloLibro> capituloLibroCollectionNew = producto.getCapituloLibroCollection();
            Collection<ProductoProyecto> productoProyectoCollectionOld = persistentProducto.getProductoProyectoCollection();
            Collection<ProductoProyecto> productoProyectoCollectionNew = producto.getProductoProyectoCollection();
            Collection<Prototipo> prototipoCollectionOld = persistentProducto.getPrototipoCollection();
            Collection<Prototipo> prototipoCollectionNew = producto.getPrototipoCollection();
            Collection<Articulo> articuloCollectionOld = persistentProducto.getArticuloCollection();
            Collection<Articulo> articuloCollectionNew = producto.getArticuloCollection();
            Collection<Tesis> tesisCollectionOld = persistentProducto.getTesisCollection();
            Collection<Tesis> tesisCollectionNew = producto.getTesisCollection();
            Collection<Libro> libroCollectionOld = persistentProducto.getLibroCollection();
            Collection<Libro> libroCollectionNew = producto.getLibroCollection();
            Collection<ProductoColaborador> productoColaboradorCollectionOld = persistentProducto.getProductoColaboradorCollection();
            Collection<ProductoColaborador> productoColaboradorCollectionNew = producto.getProductoColaboradorCollection();
            Collection<Patente> patenteCollectionOld = persistentProducto.getPatenteCollection();
            Collection<Patente> patenteCollectionNew = producto.getPatenteCollection();
            List<String> illegalOrphanMessages = null;
            for (ProductoProyecto productoProyectoCollectionOldProductoProyecto : productoProyectoCollectionOld) {
                if (!productoProyectoCollectionNew.contains(productoProyectoCollectionOldProductoProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoProyecto " + productoProyectoCollectionOldProductoProyecto + " since its producto field is not nullable.");
                }
            }
            for (Prototipo prototipoCollectionOldPrototipo : prototipoCollectionOld) {
                if (!prototipoCollectionNew.contains(prototipoCollectionOldPrototipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prototipo " + prototipoCollectionOldPrototipo + " since its idProducto field is not nullable.");
                }
            }
            for (ProductoColaborador productoColaboradorCollectionOldProductoColaborador : productoColaboradorCollectionOld) {
                if (!productoColaboradorCollectionNew.contains(productoColaboradorCollectionOldProductoColaborador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoColaborador " + productoColaboradorCollectionOldProductoColaborador + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getIdPais());
                producto.setIdPais(idPaisNew);
            }
            Collection<Memoria> attachedMemoriaCollectionNew = new ArrayList<Memoria>();
            for (Memoria memoriaCollectionNewMemoriaToAttach : memoriaCollectionNew) {
                memoriaCollectionNewMemoriaToAttach = em.getReference(memoriaCollectionNewMemoriaToAttach.getClass(), memoriaCollectionNewMemoriaToAttach.getIdMemoria());
                attachedMemoriaCollectionNew.add(memoriaCollectionNewMemoriaToAttach);
            }
            memoriaCollectionNew = attachedMemoriaCollectionNew;
            producto.setMemoriaCollection(memoriaCollectionNew);
            Collection<CapituloLibro> attachedCapituloLibroCollectionNew = new ArrayList<CapituloLibro>();
            for (CapituloLibro capituloLibroCollectionNewCapituloLibroToAttach : capituloLibroCollectionNew) {
                capituloLibroCollectionNewCapituloLibroToAttach = em.getReference(capituloLibroCollectionNewCapituloLibroToAttach.getClass(), capituloLibroCollectionNewCapituloLibroToAttach.getIdCapitulolibro());
                attachedCapituloLibroCollectionNew.add(capituloLibroCollectionNewCapituloLibroToAttach);
            }
            capituloLibroCollectionNew = attachedCapituloLibroCollectionNew;
            producto.setCapituloLibroCollection(capituloLibroCollectionNew);
            Collection<ProductoProyecto> attachedProductoProyectoCollectionNew = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoCollectionNewProductoProyectoToAttach : productoProyectoCollectionNew) {
                productoProyectoCollectionNewProductoProyectoToAttach = em.getReference(productoProyectoCollectionNewProductoProyectoToAttach.getClass(), productoProyectoCollectionNewProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoCollectionNew.add(productoProyectoCollectionNewProductoProyectoToAttach);
            }
            productoProyectoCollectionNew = attachedProductoProyectoCollectionNew;
            producto.setProductoProyectoCollection(productoProyectoCollectionNew);
            Collection<Prototipo> attachedPrototipoCollectionNew = new ArrayList<Prototipo>();
            for (Prototipo prototipoCollectionNewPrototipoToAttach : prototipoCollectionNew) {
                prototipoCollectionNewPrototipoToAttach = em.getReference(prototipoCollectionNewPrototipoToAttach.getClass(), prototipoCollectionNewPrototipoToAttach.getIdprototipo());
                attachedPrototipoCollectionNew.add(prototipoCollectionNewPrototipoToAttach);
            }
            prototipoCollectionNew = attachedPrototipoCollectionNew;
            producto.setPrototipoCollection(prototipoCollectionNew);
            Collection<Articulo> attachedArticuloCollectionNew = new ArrayList<Articulo>();
            for (Articulo articuloCollectionNewArticuloToAttach : articuloCollectionNew) {
                articuloCollectionNewArticuloToAttach = em.getReference(articuloCollectionNewArticuloToAttach.getClass(), articuloCollectionNewArticuloToAttach.getIdArticulo());
                attachedArticuloCollectionNew.add(articuloCollectionNewArticuloToAttach);
            }
            articuloCollectionNew = attachedArticuloCollectionNew;
            producto.setArticuloCollection(articuloCollectionNew);
            Collection<Tesis> attachedTesisCollectionNew = new ArrayList<Tesis>();
            for (Tesis tesisCollectionNewTesisToAttach : tesisCollectionNew) {
                tesisCollectionNewTesisToAttach = em.getReference(tesisCollectionNewTesisToAttach.getClass(), tesisCollectionNewTesisToAttach.getIdTesis());
                attachedTesisCollectionNew.add(tesisCollectionNewTesisToAttach);
            }
            tesisCollectionNew = attachedTesisCollectionNew;
            producto.setTesisCollection(tesisCollectionNew);
            Collection<Libro> attachedLibroCollectionNew = new ArrayList<Libro>();
            for (Libro libroCollectionNewLibroToAttach : libroCollectionNew) {
                libroCollectionNewLibroToAttach = em.getReference(libroCollectionNewLibroToAttach.getClass(), libroCollectionNewLibroToAttach.getIdLibro());
                attachedLibroCollectionNew.add(libroCollectionNewLibroToAttach);
            }
            libroCollectionNew = attachedLibroCollectionNew;
            producto.setLibroCollection(libroCollectionNew);
            Collection<ProductoColaborador> attachedProductoColaboradorCollectionNew = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorCollectionNewProductoColaboradorToAttach : productoColaboradorCollectionNew) {
                productoColaboradorCollectionNewProductoColaboradorToAttach = em.getReference(productoColaboradorCollectionNewProductoColaboradorToAttach.getClass(), productoColaboradorCollectionNewProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorCollectionNew.add(productoColaboradorCollectionNewProductoColaboradorToAttach);
            }
            productoColaboradorCollectionNew = attachedProductoColaboradorCollectionNew;
            producto.setProductoColaboradorCollection(productoColaboradorCollectionNew);
            Collection<Patente> attachedPatenteCollectionNew = new ArrayList<Patente>();
            for (Patente patenteCollectionNewPatenteToAttach : patenteCollectionNew) {
                patenteCollectionNewPatenteToAttach = em.getReference(patenteCollectionNewPatenteToAttach.getClass(), patenteCollectionNewPatenteToAttach.getIdPatente());
                attachedPatenteCollectionNew.add(patenteCollectionNewPatenteToAttach);
            }
            patenteCollectionNew = attachedPatenteCollectionNew;
            producto.setPatenteCollection(patenteCollectionNew);
            producto = em.merge(producto);
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getProductoCollection().remove(producto);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getProductoCollection().add(producto);
                idPaisNew = em.merge(idPaisNew);
            }
            for (Memoria memoriaCollectionOldMemoria : memoriaCollectionOld) {
                if (!memoriaCollectionNew.contains(memoriaCollectionOldMemoria)) {
                    memoriaCollectionOldMemoria.setIdProducto(null);
                    memoriaCollectionOldMemoria = em.merge(memoriaCollectionOldMemoria);
                }
            }
            for (Memoria memoriaCollectionNewMemoria : memoriaCollectionNew) {
                if (!memoriaCollectionOld.contains(memoriaCollectionNewMemoria)) {
                    Producto oldIdProductoOfMemoriaCollectionNewMemoria = memoriaCollectionNewMemoria.getIdProducto();
                    memoriaCollectionNewMemoria.setIdProducto(producto);
                    memoriaCollectionNewMemoria = em.merge(memoriaCollectionNewMemoria);
                    if (oldIdProductoOfMemoriaCollectionNewMemoria != null && !oldIdProductoOfMemoriaCollectionNewMemoria.equals(producto)) {
                        oldIdProductoOfMemoriaCollectionNewMemoria.getMemoriaCollection().remove(memoriaCollectionNewMemoria);
                        oldIdProductoOfMemoriaCollectionNewMemoria = em.merge(oldIdProductoOfMemoriaCollectionNewMemoria);
                    }
                }
            }
            for (CapituloLibro capituloLibroCollectionOldCapituloLibro : capituloLibroCollectionOld) {
                if (!capituloLibroCollectionNew.contains(capituloLibroCollectionOldCapituloLibro)) {
                    capituloLibroCollectionOldCapituloLibro.setIdProducto(null);
                    capituloLibroCollectionOldCapituloLibro = em.merge(capituloLibroCollectionOldCapituloLibro);
                }
            }
            for (CapituloLibro capituloLibroCollectionNewCapituloLibro : capituloLibroCollectionNew) {
                if (!capituloLibroCollectionOld.contains(capituloLibroCollectionNewCapituloLibro)) {
                    Producto oldIdProductoOfCapituloLibroCollectionNewCapituloLibro = capituloLibroCollectionNewCapituloLibro.getIdProducto();
                    capituloLibroCollectionNewCapituloLibro.setIdProducto(producto);
                    capituloLibroCollectionNewCapituloLibro = em.merge(capituloLibroCollectionNewCapituloLibro);
                    if (oldIdProductoOfCapituloLibroCollectionNewCapituloLibro != null && !oldIdProductoOfCapituloLibroCollectionNewCapituloLibro.equals(producto)) {
                        oldIdProductoOfCapituloLibroCollectionNewCapituloLibro.getCapituloLibroCollection().remove(capituloLibroCollectionNewCapituloLibro);
                        oldIdProductoOfCapituloLibroCollectionNewCapituloLibro = em.merge(oldIdProductoOfCapituloLibroCollectionNewCapituloLibro);
                    }
                }
            }
            for (ProductoProyecto productoProyectoCollectionNewProductoProyecto : productoProyectoCollectionNew) {
                if (!productoProyectoCollectionOld.contains(productoProyectoCollectionNewProductoProyecto)) {
                    Producto oldProductoOfProductoProyectoCollectionNewProductoProyecto = productoProyectoCollectionNewProductoProyecto.getProducto();
                    productoProyectoCollectionNewProductoProyecto.setProducto(producto);
                    productoProyectoCollectionNewProductoProyecto = em.merge(productoProyectoCollectionNewProductoProyecto);
                    if (oldProductoOfProductoProyectoCollectionNewProductoProyecto != null && !oldProductoOfProductoProyectoCollectionNewProductoProyecto.equals(producto)) {
                        oldProductoOfProductoProyectoCollectionNewProductoProyecto.getProductoProyectoCollection().remove(productoProyectoCollectionNewProductoProyecto);
                        oldProductoOfProductoProyectoCollectionNewProductoProyecto = em.merge(oldProductoOfProductoProyectoCollectionNewProductoProyecto);
                    }
                }
            }
            for (Prototipo prototipoCollectionNewPrototipo : prototipoCollectionNew) {
                if (!prototipoCollectionOld.contains(prototipoCollectionNewPrototipo)) {
                    Producto oldIdProductoOfPrototipoCollectionNewPrototipo = prototipoCollectionNewPrototipo.getIdProducto();
                    prototipoCollectionNewPrototipo.setIdProducto(producto);
                    prototipoCollectionNewPrototipo = em.merge(prototipoCollectionNewPrototipo);
                    if (oldIdProductoOfPrototipoCollectionNewPrototipo != null && !oldIdProductoOfPrototipoCollectionNewPrototipo.equals(producto)) {
                        oldIdProductoOfPrototipoCollectionNewPrototipo.getPrototipoCollection().remove(prototipoCollectionNewPrototipo);
                        oldIdProductoOfPrototipoCollectionNewPrototipo = em.merge(oldIdProductoOfPrototipoCollectionNewPrototipo);
                    }
                }
            }
            for (Articulo articuloCollectionOldArticulo : articuloCollectionOld) {
                if (!articuloCollectionNew.contains(articuloCollectionOldArticulo)) {
                    articuloCollectionOldArticulo.setIdProducto(null);
                    articuloCollectionOldArticulo = em.merge(articuloCollectionOldArticulo);
                }
            }
            for (Articulo articuloCollectionNewArticulo : articuloCollectionNew) {
                if (!articuloCollectionOld.contains(articuloCollectionNewArticulo)) {
                    Producto oldIdProductoOfArticuloCollectionNewArticulo = articuloCollectionNewArticulo.getIdProducto();
                    articuloCollectionNewArticulo.setIdProducto(producto);
                    articuloCollectionNewArticulo = em.merge(articuloCollectionNewArticulo);
                    if (oldIdProductoOfArticuloCollectionNewArticulo != null && !oldIdProductoOfArticuloCollectionNewArticulo.equals(producto)) {
                        oldIdProductoOfArticuloCollectionNewArticulo.getArticuloCollection().remove(articuloCollectionNewArticulo);
                        oldIdProductoOfArticuloCollectionNewArticulo = em.merge(oldIdProductoOfArticuloCollectionNewArticulo);
                    }
                }
            }
            for (Tesis tesisCollectionOldTesis : tesisCollectionOld) {
                if (!tesisCollectionNew.contains(tesisCollectionOldTesis)) {
                    tesisCollectionOldTesis.setIdProducto(null);
                    tesisCollectionOldTesis = em.merge(tesisCollectionOldTesis);
                }
            }
            for (Tesis tesisCollectionNewTesis : tesisCollectionNew) {
                if (!tesisCollectionOld.contains(tesisCollectionNewTesis)) {
                    Producto oldIdProductoOfTesisCollectionNewTesis = tesisCollectionNewTesis.getIdProducto();
                    tesisCollectionNewTesis.setIdProducto(producto);
                    tesisCollectionNewTesis = em.merge(tesisCollectionNewTesis);
                    if (oldIdProductoOfTesisCollectionNewTesis != null && !oldIdProductoOfTesisCollectionNewTesis.equals(producto)) {
                        oldIdProductoOfTesisCollectionNewTesis.getTesisCollection().remove(tesisCollectionNewTesis);
                        oldIdProductoOfTesisCollectionNewTesis = em.merge(oldIdProductoOfTesisCollectionNewTesis);
                    }
                }
            }
            for (Libro libroCollectionOldLibro : libroCollectionOld) {
                if (!libroCollectionNew.contains(libroCollectionOldLibro)) {
                    libroCollectionOldLibro.setIdProducto(null);
                    libroCollectionOldLibro = em.merge(libroCollectionOldLibro);
                }
            }
            for (Libro libroCollectionNewLibro : libroCollectionNew) {
                if (!libroCollectionOld.contains(libroCollectionNewLibro)) {
                    Producto oldIdProductoOfLibroCollectionNewLibro = libroCollectionNewLibro.getIdProducto();
                    libroCollectionNewLibro.setIdProducto(producto);
                    libroCollectionNewLibro = em.merge(libroCollectionNewLibro);
                    if (oldIdProductoOfLibroCollectionNewLibro != null && !oldIdProductoOfLibroCollectionNewLibro.equals(producto)) {
                        oldIdProductoOfLibroCollectionNewLibro.getLibroCollection().remove(libroCollectionNewLibro);
                        oldIdProductoOfLibroCollectionNewLibro = em.merge(oldIdProductoOfLibroCollectionNewLibro);
                    }
                }
            }
            for (ProductoColaborador productoColaboradorCollectionNewProductoColaborador : productoColaboradorCollectionNew) {
                if (!productoColaboradorCollectionOld.contains(productoColaboradorCollectionNewProductoColaborador)) {
                    Producto oldProductoOfProductoColaboradorCollectionNewProductoColaborador = productoColaboradorCollectionNewProductoColaborador.getProducto();
                    productoColaboradorCollectionNewProductoColaborador.setProducto(producto);
                    productoColaboradorCollectionNewProductoColaborador = em.merge(productoColaboradorCollectionNewProductoColaborador);
                    if (oldProductoOfProductoColaboradorCollectionNewProductoColaborador != null && !oldProductoOfProductoColaboradorCollectionNewProductoColaborador.equals(producto)) {
                        oldProductoOfProductoColaboradorCollectionNewProductoColaborador.getProductoColaboradorCollection().remove(productoColaboradorCollectionNewProductoColaborador);
                        oldProductoOfProductoColaboradorCollectionNewProductoColaborador = em.merge(oldProductoOfProductoColaboradorCollectionNewProductoColaborador);
                    }
                }
            }
            for (Patente patenteCollectionOldPatente : patenteCollectionOld) {
                if (!patenteCollectionNew.contains(patenteCollectionOldPatente)) {
                    patenteCollectionOldPatente.setIdProducto(null);
                    patenteCollectionOldPatente = em.merge(patenteCollectionOldPatente);
                }
            }
            for (Patente patenteCollectionNewPatente : patenteCollectionNew) {
                if (!patenteCollectionOld.contains(patenteCollectionNewPatente)) {
                    Producto oldIdProductoOfPatenteCollectionNewPatente = patenteCollectionNewPatente.getIdProducto();
                    patenteCollectionNewPatente.setIdProducto(producto);
                    patenteCollectionNewPatente = em.merge(patenteCollectionNewPatente);
                    if (oldIdProductoOfPatenteCollectionNewPatente != null && !oldIdProductoOfPatenteCollectionNewPatente.equals(producto)) {
                        oldIdProductoOfPatenteCollectionNewPatente.getPatenteCollection().remove(patenteCollectionNewPatente);
                        oldIdProductoOfPatenteCollectionNewPatente = em.merge(oldIdProductoOfPatenteCollectionNewPatente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ProductoProyecto> productoProyectoCollectionOrphanCheck = producto.getProductoProyectoCollection();
            for (ProductoProyecto productoProyectoCollectionOrphanCheckProductoProyecto : productoProyectoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoProyecto " + productoProyectoCollectionOrphanCheckProductoProyecto + " in its productoProyectoCollection field has a non-nullable producto field.");
            }
            Collection<Prototipo> prototipoCollectionOrphanCheck = producto.getPrototipoCollection();
            for (Prototipo prototipoCollectionOrphanCheckPrototipo : prototipoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Prototipo " + prototipoCollectionOrphanCheckPrototipo + " in its prototipoCollection field has a non-nullable idProducto field.");
            }
            Collection<ProductoColaborador> productoColaboradorCollectionOrphanCheck = producto.getProductoColaboradorCollection();
            for (ProductoColaborador productoColaboradorCollectionOrphanCheckProductoColaborador : productoColaboradorCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoColaborador " + productoColaboradorCollectionOrphanCheckProductoColaborador + " in its productoColaboradorCollection field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais idPais = producto.getIdPais();
            if (idPais != null) {
                idPais.getProductoCollection().remove(producto);
                idPais = em.merge(idPais);
            }
            Collection<Memoria> memoriaCollection = producto.getMemoriaCollection();
            for (Memoria memoriaCollectionMemoria : memoriaCollection) {
                memoriaCollectionMemoria.setIdProducto(null);
                memoriaCollectionMemoria = em.merge(memoriaCollectionMemoria);
            }
            Collection<CapituloLibro> capituloLibroCollection = producto.getCapituloLibroCollection();
            for (CapituloLibro capituloLibroCollectionCapituloLibro : capituloLibroCollection) {
                capituloLibroCollectionCapituloLibro.setIdProducto(null);
                capituloLibroCollectionCapituloLibro = em.merge(capituloLibroCollectionCapituloLibro);
            }
            Collection<Articulo> articuloCollection = producto.getArticuloCollection();
            for (Articulo articuloCollectionArticulo : articuloCollection) {
                articuloCollectionArticulo.setIdProducto(null);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
            }
            Collection<Tesis> tesisCollection = producto.getTesisCollection();
            for (Tesis tesisCollectionTesis : tesisCollection) {
                tesisCollectionTesis.setIdProducto(null);
                tesisCollectionTesis = em.merge(tesisCollectionTesis);
            }
            Collection<Libro> libroCollection = producto.getLibroCollection();
            for (Libro libroCollectionLibro : libroCollection) {
                libroCollectionLibro.setIdProducto(null);
                libroCollectionLibro = em.merge(libroCollectionLibro);
            }
            Collection<Patente> patenteCollection = producto.getPatenteCollection();
            for (Patente patenteCollectionPatente : patenteCollection) {
                patenteCollectionPatente.setIdProducto(null);
                patenteCollectionPatente = em.merge(patenteCollectionPatente);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
