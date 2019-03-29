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
import entity.Pais;
import entity.Memoria;
import java.util.ArrayList;
import java.util.List;
import entity.CapituloLibro;
import entity.ProductoProyecto;
import entity.Prototipo;
import entity.Articulo;
import entity.Tesis;
import entity.Libro;
import entity.ProductoColaborador;
import entity.Patente;
import entity.Producto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistence.exceptions.IllegalOrphanException;
import persistence.exceptions.NonexistentEntityException;

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
        if (producto.getMemoriaList() == null) {
            producto.setMemoriaList(new ArrayList<Memoria>());
        }
        if (producto.getCapituloLibroList() == null) {
            producto.setCapituloLibroList(new ArrayList<CapituloLibro>());
        }
        if (producto.getProductoProyectoList() == null) {
            producto.setProductoProyectoList(new ArrayList<ProductoProyecto>());
        }
        if (producto.getPrototipoList() == null) {
            producto.setPrototipoList(new ArrayList<Prototipo>());
        }
        if (producto.getArticuloList() == null) {
            producto.setArticuloList(new ArrayList<Articulo>());
        }
        if (producto.getTesisList() == null) {
            producto.setTesisList(new ArrayList<Tesis>());
        }
        if (producto.getLibroList() == null) {
            producto.setLibroList(new ArrayList<Libro>());
        }
        if (producto.getProductoColaboradorList() == null) {
            producto.setProductoColaboradorList(new ArrayList<ProductoColaborador>());
        }
        if (producto.getPatenteList() == null) {
            producto.setPatenteList(new ArrayList<Patente>());
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
            List<Memoria> attachedMemoriaList = new ArrayList<Memoria>();
            for (Memoria memoriaListMemoriaToAttach : producto.getMemoriaList()) {
                memoriaListMemoriaToAttach = em.getReference(memoriaListMemoriaToAttach.getClass(), memoriaListMemoriaToAttach.getIdMemoria());
                attachedMemoriaList.add(memoriaListMemoriaToAttach);
            }
            producto.setMemoriaList(attachedMemoriaList);
            List<CapituloLibro> attachedCapituloLibroList = new ArrayList<CapituloLibro>();
            for (CapituloLibro capituloLibroListCapituloLibroToAttach : producto.getCapituloLibroList()) {
                capituloLibroListCapituloLibroToAttach = em.getReference(capituloLibroListCapituloLibroToAttach.getClass(), capituloLibroListCapituloLibroToAttach.getIdCapitulolibro());
                attachedCapituloLibroList.add(capituloLibroListCapituloLibroToAttach);
            }
            producto.setCapituloLibroList(attachedCapituloLibroList);
            List<ProductoProyecto> attachedProductoProyectoList = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoListProductoProyectoToAttach : producto.getProductoProyectoList()) {
                productoProyectoListProductoProyectoToAttach = em.getReference(productoProyectoListProductoProyectoToAttach.getClass(), productoProyectoListProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoList.add(productoProyectoListProductoProyectoToAttach);
            }
            producto.setProductoProyectoList(attachedProductoProyectoList);
            List<Prototipo> attachedPrototipoList = new ArrayList<Prototipo>();
            for (Prototipo prototipoListPrototipoToAttach : producto.getPrototipoList()) {
                prototipoListPrototipoToAttach = em.getReference(prototipoListPrototipoToAttach.getClass(), prototipoListPrototipoToAttach.getIdprototipo());
                attachedPrototipoList.add(prototipoListPrototipoToAttach);
            }
            producto.setPrototipoList(attachedPrototipoList);
            List<Articulo> attachedArticuloList = new ArrayList<Articulo>();
            for (Articulo articuloListArticuloToAttach : producto.getArticuloList()) {
                articuloListArticuloToAttach = em.getReference(articuloListArticuloToAttach.getClass(), articuloListArticuloToAttach.getIdArticulo());
                attachedArticuloList.add(articuloListArticuloToAttach);
            }
            producto.setArticuloList(attachedArticuloList);
            List<Tesis> attachedTesisList = new ArrayList<Tesis>();
            for (Tesis tesisListTesisToAttach : producto.getTesisList()) {
                tesisListTesisToAttach = em.getReference(tesisListTesisToAttach.getClass(), tesisListTesisToAttach.getIdTesis());
                attachedTesisList.add(tesisListTesisToAttach);
            }
            producto.setTesisList(attachedTesisList);
            List<Libro> attachedLibroList = new ArrayList<Libro>();
            for (Libro libroListLibroToAttach : producto.getLibroList()) {
                libroListLibroToAttach = em.getReference(libroListLibroToAttach.getClass(), libroListLibroToAttach.getIdLibro());
                attachedLibroList.add(libroListLibroToAttach);
            }
            producto.setLibroList(attachedLibroList);
            List<ProductoColaborador> attachedProductoColaboradorList = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorListProductoColaboradorToAttach : producto.getProductoColaboradorList()) {
                productoColaboradorListProductoColaboradorToAttach = em.getReference(productoColaboradorListProductoColaboradorToAttach.getClass(), productoColaboradorListProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorList.add(productoColaboradorListProductoColaboradorToAttach);
            }
            producto.setProductoColaboradorList(attachedProductoColaboradorList);
            List<Patente> attachedPatenteList = new ArrayList<Patente>();
            for (Patente patenteListPatenteToAttach : producto.getPatenteList()) {
                patenteListPatenteToAttach = em.getReference(patenteListPatenteToAttach.getClass(), patenteListPatenteToAttach.getIdPatente());
                attachedPatenteList.add(patenteListPatenteToAttach);
            }
            producto.setPatenteList(attachedPatenteList);
            em.persist(producto);
            if (idPais != null) {
                idPais.getProductoList().add(producto);
                idPais = em.merge(idPais);
            }
            for (Memoria memoriaListMemoria : producto.getMemoriaList()) {
                Producto oldIdProductoOfMemoriaListMemoria = memoriaListMemoria.getIdProducto();
                memoriaListMemoria.setIdProducto(producto);
                memoriaListMemoria = em.merge(memoriaListMemoria);
                if (oldIdProductoOfMemoriaListMemoria != null) {
                    oldIdProductoOfMemoriaListMemoria.getMemoriaList().remove(memoriaListMemoria);
                    oldIdProductoOfMemoriaListMemoria = em.merge(oldIdProductoOfMemoriaListMemoria);
                }
            }
            for (CapituloLibro capituloLibroListCapituloLibro : producto.getCapituloLibroList()) {
                Producto oldIdProductoOfCapituloLibroListCapituloLibro = capituloLibroListCapituloLibro.getIdProducto();
                capituloLibroListCapituloLibro.setIdProducto(producto);
                capituloLibroListCapituloLibro = em.merge(capituloLibroListCapituloLibro);
                if (oldIdProductoOfCapituloLibroListCapituloLibro != null) {
                    oldIdProductoOfCapituloLibroListCapituloLibro.getCapituloLibroList().remove(capituloLibroListCapituloLibro);
                    oldIdProductoOfCapituloLibroListCapituloLibro = em.merge(oldIdProductoOfCapituloLibroListCapituloLibro);
                }
            }
            for (ProductoProyecto productoProyectoListProductoProyecto : producto.getProductoProyectoList()) {
                Producto oldProductoOfProductoProyectoListProductoProyecto = productoProyectoListProductoProyecto.getProducto();
                productoProyectoListProductoProyecto.setProducto(producto);
                productoProyectoListProductoProyecto = em.merge(productoProyectoListProductoProyecto);
                if (oldProductoOfProductoProyectoListProductoProyecto != null) {
                    oldProductoOfProductoProyectoListProductoProyecto.getProductoProyectoList().remove(productoProyectoListProductoProyecto);
                    oldProductoOfProductoProyectoListProductoProyecto = em.merge(oldProductoOfProductoProyectoListProductoProyecto);
                }
            }
            for (Prototipo prototipoListPrototipo : producto.getPrototipoList()) {
                Producto oldIdProductoOfPrototipoListPrototipo = prototipoListPrototipo.getIdProducto();
                prototipoListPrototipo.setIdProducto(producto);
                prototipoListPrototipo = em.merge(prototipoListPrototipo);
                if (oldIdProductoOfPrototipoListPrototipo != null) {
                    oldIdProductoOfPrototipoListPrototipo.getPrototipoList().remove(prototipoListPrototipo);
                    oldIdProductoOfPrototipoListPrototipo = em.merge(oldIdProductoOfPrototipoListPrototipo);
                }
            }
            for (Articulo articuloListArticulo : producto.getArticuloList()) {
                Producto oldIdProductoOfArticuloListArticulo = articuloListArticulo.getIdProducto();
                articuloListArticulo.setIdProducto(producto);
                articuloListArticulo = em.merge(articuloListArticulo);
                if (oldIdProductoOfArticuloListArticulo != null) {
                    oldIdProductoOfArticuloListArticulo.getArticuloList().remove(articuloListArticulo);
                    oldIdProductoOfArticuloListArticulo = em.merge(oldIdProductoOfArticuloListArticulo);
                }
            }
            for (Tesis tesisListTesis : producto.getTesisList()) {
                Producto oldIdProductoOfTesisListTesis = tesisListTesis.getIdProducto();
                tesisListTesis.setIdProducto(producto);
                tesisListTesis = em.merge(tesisListTesis);
                if (oldIdProductoOfTesisListTesis != null) {
                    oldIdProductoOfTesisListTesis.getTesisList().remove(tesisListTesis);
                    oldIdProductoOfTesisListTesis = em.merge(oldIdProductoOfTesisListTesis);
                }
            }
            for (Libro libroListLibro : producto.getLibroList()) {
                Producto oldIdProductoOfLibroListLibro = libroListLibro.getIdProducto();
                libroListLibro.setIdProducto(producto);
                libroListLibro = em.merge(libroListLibro);
                if (oldIdProductoOfLibroListLibro != null) {
                    oldIdProductoOfLibroListLibro.getLibroList().remove(libroListLibro);
                    oldIdProductoOfLibroListLibro = em.merge(oldIdProductoOfLibroListLibro);
                }
            }
            for (ProductoColaborador productoColaboradorListProductoColaborador : producto.getProductoColaboradorList()) {
                Producto oldProductoOfProductoColaboradorListProductoColaborador = productoColaboradorListProductoColaborador.getProducto();
                productoColaboradorListProductoColaborador.setProducto(producto);
                productoColaboradorListProductoColaborador = em.merge(productoColaboradorListProductoColaborador);
                if (oldProductoOfProductoColaboradorListProductoColaborador != null) {
                    oldProductoOfProductoColaboradorListProductoColaborador.getProductoColaboradorList().remove(productoColaboradorListProductoColaborador);
                    oldProductoOfProductoColaboradorListProductoColaborador = em.merge(oldProductoOfProductoColaboradorListProductoColaborador);
                }
            }
            for (Patente patenteListPatente : producto.getPatenteList()) {
                Producto oldIdProductoOfPatenteListPatente = patenteListPatente.getIdProducto();
                patenteListPatente.setIdProducto(producto);
                patenteListPatente = em.merge(patenteListPatente);
                if (oldIdProductoOfPatenteListPatente != null) {
                    oldIdProductoOfPatenteListPatente.getPatenteList().remove(patenteListPatente);
                    oldIdProductoOfPatenteListPatente = em.merge(oldIdProductoOfPatenteListPatente);
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
            List<Memoria> memoriaListOld = persistentProducto.getMemoriaList();
            List<Memoria> memoriaListNew = producto.getMemoriaList();
            List<CapituloLibro> capituloLibroListOld = persistentProducto.getCapituloLibroList();
            List<CapituloLibro> capituloLibroListNew = producto.getCapituloLibroList();
            List<ProductoProyecto> productoProyectoListOld = persistentProducto.getProductoProyectoList();
            List<ProductoProyecto> productoProyectoListNew = producto.getProductoProyectoList();
            List<Prototipo> prototipoListOld = persistentProducto.getPrototipoList();
            List<Prototipo> prototipoListNew = producto.getPrototipoList();
            List<Articulo> articuloListOld = persistentProducto.getArticuloList();
            List<Articulo> articuloListNew = producto.getArticuloList();
            List<Tesis> tesisListOld = persistentProducto.getTesisList();
            List<Tesis> tesisListNew = producto.getTesisList();
            List<Libro> libroListOld = persistentProducto.getLibroList();
            List<Libro> libroListNew = producto.getLibroList();
            List<ProductoColaborador> productoColaboradorListOld = persistentProducto.getProductoColaboradorList();
            List<ProductoColaborador> productoColaboradorListNew = producto.getProductoColaboradorList();
            List<Patente> patenteListOld = persistentProducto.getPatenteList();
            List<Patente> patenteListNew = producto.getPatenteList();
            List<String> illegalOrphanMessages = null;
            for (ProductoProyecto productoProyectoListOldProductoProyecto : productoProyectoListOld) {
                if (!productoProyectoListNew.contains(productoProyectoListOldProductoProyecto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoProyecto " + productoProyectoListOldProductoProyecto + " since its producto field is not nullable.");
                }
            }
            for (Prototipo prototipoListOldPrototipo : prototipoListOld) {
                if (!prototipoListNew.contains(prototipoListOldPrototipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Prototipo " + prototipoListOldPrototipo + " since its idProducto field is not nullable.");
                }
            }
            for (ProductoColaborador productoColaboradorListOldProductoColaborador : productoColaboradorListOld) {
                if (!productoColaboradorListNew.contains(productoColaboradorListOldProductoColaborador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoColaborador " + productoColaboradorListOldProductoColaborador + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPaisNew != null) {
                idPaisNew = em.getReference(idPaisNew.getClass(), idPaisNew.getIdPais());
                producto.setIdPais(idPaisNew);
            }
            List<Memoria> attachedMemoriaListNew = new ArrayList<Memoria>();
            for (Memoria memoriaListNewMemoriaToAttach : memoriaListNew) {
                memoriaListNewMemoriaToAttach = em.getReference(memoriaListNewMemoriaToAttach.getClass(), memoriaListNewMemoriaToAttach.getIdMemoria());
                attachedMemoriaListNew.add(memoriaListNewMemoriaToAttach);
            }
            memoriaListNew = attachedMemoriaListNew;
            producto.setMemoriaList(memoriaListNew);
            List<CapituloLibro> attachedCapituloLibroListNew = new ArrayList<CapituloLibro>();
            for (CapituloLibro capituloLibroListNewCapituloLibroToAttach : capituloLibroListNew) {
                capituloLibroListNewCapituloLibroToAttach = em.getReference(capituloLibroListNewCapituloLibroToAttach.getClass(), capituloLibroListNewCapituloLibroToAttach.getIdCapitulolibro());
                attachedCapituloLibroListNew.add(capituloLibroListNewCapituloLibroToAttach);
            }
            capituloLibroListNew = attachedCapituloLibroListNew;
            producto.setCapituloLibroList(capituloLibroListNew);
            List<ProductoProyecto> attachedProductoProyectoListNew = new ArrayList<ProductoProyecto>();
            for (ProductoProyecto productoProyectoListNewProductoProyectoToAttach : productoProyectoListNew) {
                productoProyectoListNewProductoProyectoToAttach = em.getReference(productoProyectoListNewProductoProyectoToAttach.getClass(), productoProyectoListNewProductoProyectoToAttach.getProductoProyectoPK());
                attachedProductoProyectoListNew.add(productoProyectoListNewProductoProyectoToAttach);
            }
            productoProyectoListNew = attachedProductoProyectoListNew;
            producto.setProductoProyectoList(productoProyectoListNew);
            List<Prototipo> attachedPrototipoListNew = new ArrayList<Prototipo>();
            for (Prototipo prototipoListNewPrototipoToAttach : prototipoListNew) {
                prototipoListNewPrototipoToAttach = em.getReference(prototipoListNewPrototipoToAttach.getClass(), prototipoListNewPrototipoToAttach.getIdprototipo());
                attachedPrototipoListNew.add(prototipoListNewPrototipoToAttach);
            }
            prototipoListNew = attachedPrototipoListNew;
            producto.setPrototipoList(prototipoListNew);
            List<Articulo> attachedArticuloListNew = new ArrayList<Articulo>();
            for (Articulo articuloListNewArticuloToAttach : articuloListNew) {
                articuloListNewArticuloToAttach = em.getReference(articuloListNewArticuloToAttach.getClass(), articuloListNewArticuloToAttach.getIdArticulo());
                attachedArticuloListNew.add(articuloListNewArticuloToAttach);
            }
            articuloListNew = attachedArticuloListNew;
            producto.setArticuloList(articuloListNew);
            List<Tesis> attachedTesisListNew = new ArrayList<Tesis>();
            for (Tesis tesisListNewTesisToAttach : tesisListNew) {
                tesisListNewTesisToAttach = em.getReference(tesisListNewTesisToAttach.getClass(), tesisListNewTesisToAttach.getIdTesis());
                attachedTesisListNew.add(tesisListNewTesisToAttach);
            }
            tesisListNew = attachedTesisListNew;
            producto.setTesisList(tesisListNew);
            List<Libro> attachedLibroListNew = new ArrayList<Libro>();
            for (Libro libroListNewLibroToAttach : libroListNew) {
                libroListNewLibroToAttach = em.getReference(libroListNewLibroToAttach.getClass(), libroListNewLibroToAttach.getIdLibro());
                attachedLibroListNew.add(libroListNewLibroToAttach);
            }
            libroListNew = attachedLibroListNew;
            producto.setLibroList(libroListNew);
            List<ProductoColaborador> attachedProductoColaboradorListNew = new ArrayList<ProductoColaborador>();
            for (ProductoColaborador productoColaboradorListNewProductoColaboradorToAttach : productoColaboradorListNew) {
                productoColaboradorListNewProductoColaboradorToAttach = em.getReference(productoColaboradorListNewProductoColaboradorToAttach.getClass(), productoColaboradorListNewProductoColaboradorToAttach.getProductoColaboradorPK());
                attachedProductoColaboradorListNew.add(productoColaboradorListNewProductoColaboradorToAttach);
            }
            productoColaboradorListNew = attachedProductoColaboradorListNew;
            producto.setProductoColaboradorList(productoColaboradorListNew);
            List<Patente> attachedPatenteListNew = new ArrayList<Patente>();
            for (Patente patenteListNewPatenteToAttach : patenteListNew) {
                patenteListNewPatenteToAttach = em.getReference(patenteListNewPatenteToAttach.getClass(), patenteListNewPatenteToAttach.getIdPatente());
                attachedPatenteListNew.add(patenteListNewPatenteToAttach);
            }
            patenteListNew = attachedPatenteListNew;
            producto.setPatenteList(patenteListNew);
            producto = em.merge(producto);
            if (idPaisOld != null && !idPaisOld.equals(idPaisNew)) {
                idPaisOld.getProductoList().remove(producto);
                idPaisOld = em.merge(idPaisOld);
            }
            if (idPaisNew != null && !idPaisNew.equals(idPaisOld)) {
                idPaisNew.getProductoList().add(producto);
                idPaisNew = em.merge(idPaisNew);
            }
            for (Memoria memoriaListOldMemoria : memoriaListOld) {
                if (!memoriaListNew.contains(memoriaListOldMemoria)) {
                    memoriaListOldMemoria.setIdProducto(null);
                    memoriaListOldMemoria = em.merge(memoriaListOldMemoria);
                }
            }
            for (Memoria memoriaListNewMemoria : memoriaListNew) {
                if (!memoriaListOld.contains(memoriaListNewMemoria)) {
                    Producto oldIdProductoOfMemoriaListNewMemoria = memoriaListNewMemoria.getIdProducto();
                    memoriaListNewMemoria.setIdProducto(producto);
                    memoriaListNewMemoria = em.merge(memoriaListNewMemoria);
                    if (oldIdProductoOfMemoriaListNewMemoria != null && !oldIdProductoOfMemoriaListNewMemoria.equals(producto)) {
                        oldIdProductoOfMemoriaListNewMemoria.getMemoriaList().remove(memoriaListNewMemoria);
                        oldIdProductoOfMemoriaListNewMemoria = em.merge(oldIdProductoOfMemoriaListNewMemoria);
                    }
                }
            }
            for (CapituloLibro capituloLibroListOldCapituloLibro : capituloLibroListOld) {
                if (!capituloLibroListNew.contains(capituloLibroListOldCapituloLibro)) {
                    capituloLibroListOldCapituloLibro.setIdProducto(null);
                    capituloLibroListOldCapituloLibro = em.merge(capituloLibroListOldCapituloLibro);
                }
            }
            for (CapituloLibro capituloLibroListNewCapituloLibro : capituloLibroListNew) {
                if (!capituloLibroListOld.contains(capituloLibroListNewCapituloLibro)) {
                    Producto oldIdProductoOfCapituloLibroListNewCapituloLibro = capituloLibroListNewCapituloLibro.getIdProducto();
                    capituloLibroListNewCapituloLibro.setIdProducto(producto);
                    capituloLibroListNewCapituloLibro = em.merge(capituloLibroListNewCapituloLibro);
                    if (oldIdProductoOfCapituloLibroListNewCapituloLibro != null && !oldIdProductoOfCapituloLibroListNewCapituloLibro.equals(producto)) {
                        oldIdProductoOfCapituloLibroListNewCapituloLibro.getCapituloLibroList().remove(capituloLibroListNewCapituloLibro);
                        oldIdProductoOfCapituloLibroListNewCapituloLibro = em.merge(oldIdProductoOfCapituloLibroListNewCapituloLibro);
                    }
                }
            }
            for (ProductoProyecto productoProyectoListNewProductoProyecto : productoProyectoListNew) {
                if (!productoProyectoListOld.contains(productoProyectoListNewProductoProyecto)) {
                    Producto oldProductoOfProductoProyectoListNewProductoProyecto = productoProyectoListNewProductoProyecto.getProducto();
                    productoProyectoListNewProductoProyecto.setProducto(producto);
                    productoProyectoListNewProductoProyecto = em.merge(productoProyectoListNewProductoProyecto);
                    if (oldProductoOfProductoProyectoListNewProductoProyecto != null && !oldProductoOfProductoProyectoListNewProductoProyecto.equals(producto)) {
                        oldProductoOfProductoProyectoListNewProductoProyecto.getProductoProyectoList().remove(productoProyectoListNewProductoProyecto);
                        oldProductoOfProductoProyectoListNewProductoProyecto = em.merge(oldProductoOfProductoProyectoListNewProductoProyecto);
                    }
                }
            }
            for (Prototipo prototipoListNewPrototipo : prototipoListNew) {
                if (!prototipoListOld.contains(prototipoListNewPrototipo)) {
                    Producto oldIdProductoOfPrototipoListNewPrototipo = prototipoListNewPrototipo.getIdProducto();
                    prototipoListNewPrototipo.setIdProducto(producto);
                    prototipoListNewPrototipo = em.merge(prototipoListNewPrototipo);
                    if (oldIdProductoOfPrototipoListNewPrototipo != null && !oldIdProductoOfPrototipoListNewPrototipo.equals(producto)) {
                        oldIdProductoOfPrototipoListNewPrototipo.getPrototipoList().remove(prototipoListNewPrototipo);
                        oldIdProductoOfPrototipoListNewPrototipo = em.merge(oldIdProductoOfPrototipoListNewPrototipo);
                    }
                }
            }
            for (Articulo articuloListOldArticulo : articuloListOld) {
                if (!articuloListNew.contains(articuloListOldArticulo)) {
                    articuloListOldArticulo.setIdProducto(null);
                    articuloListOldArticulo = em.merge(articuloListOldArticulo);
                }
            }
            for (Articulo articuloListNewArticulo : articuloListNew) {
                if (!articuloListOld.contains(articuloListNewArticulo)) {
                    Producto oldIdProductoOfArticuloListNewArticulo = articuloListNewArticulo.getIdProducto();
                    articuloListNewArticulo.setIdProducto(producto);
                    articuloListNewArticulo = em.merge(articuloListNewArticulo);
                    if (oldIdProductoOfArticuloListNewArticulo != null && !oldIdProductoOfArticuloListNewArticulo.equals(producto)) {
                        oldIdProductoOfArticuloListNewArticulo.getArticuloList().remove(articuloListNewArticulo);
                        oldIdProductoOfArticuloListNewArticulo = em.merge(oldIdProductoOfArticuloListNewArticulo);
                    }
                }
            }
            for (Tesis tesisListOldTesis : tesisListOld) {
                if (!tesisListNew.contains(tesisListOldTesis)) {
                    tesisListOldTesis.setIdProducto(null);
                    tesisListOldTesis = em.merge(tesisListOldTesis);
                }
            }
            for (Tesis tesisListNewTesis : tesisListNew) {
                if (!tesisListOld.contains(tesisListNewTesis)) {
                    Producto oldIdProductoOfTesisListNewTesis = tesisListNewTesis.getIdProducto();
                    tesisListNewTesis.setIdProducto(producto);
                    tesisListNewTesis = em.merge(tesisListNewTesis);
                    if (oldIdProductoOfTesisListNewTesis != null && !oldIdProductoOfTesisListNewTesis.equals(producto)) {
                        oldIdProductoOfTesisListNewTesis.getTesisList().remove(tesisListNewTesis);
                        oldIdProductoOfTesisListNewTesis = em.merge(oldIdProductoOfTesisListNewTesis);
                    }
                }
            }
            for (Libro libroListOldLibro : libroListOld) {
                if (!libroListNew.contains(libroListOldLibro)) {
                    libroListOldLibro.setIdProducto(null);
                    libroListOldLibro = em.merge(libroListOldLibro);
                }
            }
            for (Libro libroListNewLibro : libroListNew) {
                if (!libroListOld.contains(libroListNewLibro)) {
                    Producto oldIdProductoOfLibroListNewLibro = libroListNewLibro.getIdProducto();
                    libroListNewLibro.setIdProducto(producto);
                    libroListNewLibro = em.merge(libroListNewLibro);
                    if (oldIdProductoOfLibroListNewLibro != null && !oldIdProductoOfLibroListNewLibro.equals(producto)) {
                        oldIdProductoOfLibroListNewLibro.getLibroList().remove(libroListNewLibro);
                        oldIdProductoOfLibroListNewLibro = em.merge(oldIdProductoOfLibroListNewLibro);
                    }
                }
            }
            for (ProductoColaborador productoColaboradorListNewProductoColaborador : productoColaboradorListNew) {
                if (!productoColaboradorListOld.contains(productoColaboradorListNewProductoColaborador)) {
                    Producto oldProductoOfProductoColaboradorListNewProductoColaborador = productoColaboradorListNewProductoColaborador.getProducto();
                    productoColaboradorListNewProductoColaborador.setProducto(producto);
                    productoColaboradorListNewProductoColaborador = em.merge(productoColaboradorListNewProductoColaborador);
                    if (oldProductoOfProductoColaboradorListNewProductoColaborador != null && !oldProductoOfProductoColaboradorListNewProductoColaborador.equals(producto)) {
                        oldProductoOfProductoColaboradorListNewProductoColaborador.getProductoColaboradorList().remove(productoColaboradorListNewProductoColaborador);
                        oldProductoOfProductoColaboradorListNewProductoColaborador = em.merge(oldProductoOfProductoColaboradorListNewProductoColaborador);
                    }
                }
            }
            for (Patente patenteListOldPatente : patenteListOld) {
                if (!patenteListNew.contains(patenteListOldPatente)) {
                    patenteListOldPatente.setIdProducto(null);
                    patenteListOldPatente = em.merge(patenteListOldPatente);
                }
            }
            for (Patente patenteListNewPatente : patenteListNew) {
                if (!patenteListOld.contains(patenteListNewPatente)) {
                    Producto oldIdProductoOfPatenteListNewPatente = patenteListNewPatente.getIdProducto();
                    patenteListNewPatente.setIdProducto(producto);
                    patenteListNewPatente = em.merge(patenteListNewPatente);
                    if (oldIdProductoOfPatenteListNewPatente != null && !oldIdProductoOfPatenteListNewPatente.equals(producto)) {
                        oldIdProductoOfPatenteListNewPatente.getPatenteList().remove(patenteListNewPatente);
                        oldIdProductoOfPatenteListNewPatente = em.merge(oldIdProductoOfPatenteListNewPatente);
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
            List<ProductoProyecto> productoProyectoListOrphanCheck = producto.getProductoProyectoList();
            for (ProductoProyecto productoProyectoListOrphanCheckProductoProyecto : productoProyectoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoProyecto " + productoProyectoListOrphanCheckProductoProyecto + " in its productoProyectoList field has a non-nullable producto field.");
            }
            List<Prototipo> prototipoListOrphanCheck = producto.getPrototipoList();
            for (Prototipo prototipoListOrphanCheckPrototipo : prototipoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Prototipo " + prototipoListOrphanCheckPrototipo + " in its prototipoList field has a non-nullable idProducto field.");
            }
            List<ProductoColaborador> productoColaboradorListOrphanCheck = producto.getProductoColaboradorList();
            for (ProductoColaborador productoColaboradorListOrphanCheckProductoColaborador : productoColaboradorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoColaborador " + productoColaboradorListOrphanCheckProductoColaborador + " in its productoColaboradorList field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais idPais = producto.getIdPais();
            if (idPais != null) {
                idPais.getProductoList().remove(producto);
                idPais = em.merge(idPais);
            }
            List<Memoria> memoriaList = producto.getMemoriaList();
            for (Memoria memoriaListMemoria : memoriaList) {
                memoriaListMemoria.setIdProducto(null);
                memoriaListMemoria = em.merge(memoriaListMemoria);
            }
            List<CapituloLibro> capituloLibroList = producto.getCapituloLibroList();
            for (CapituloLibro capituloLibroListCapituloLibro : capituloLibroList) {
                capituloLibroListCapituloLibro.setIdProducto(null);
                capituloLibroListCapituloLibro = em.merge(capituloLibroListCapituloLibro);
            }
            List<Articulo> articuloList = producto.getArticuloList();
            for (Articulo articuloListArticulo : articuloList) {
                articuloListArticulo.setIdProducto(null);
                articuloListArticulo = em.merge(articuloListArticulo);
            }
            List<Tesis> tesisList = producto.getTesisList();
            for (Tesis tesisListTesis : tesisList) {
                tesisListTesis.setIdProducto(null);
                tesisListTesis = em.merge(tesisListTesis);
            }
            List<Libro> libroList = producto.getLibroList();
            for (Libro libroListLibro : libroList) {
                libroListLibro.setIdProducto(null);
                libroListLibro = em.merge(libroListLibro);
            }
            List<Patente> patenteList = producto.getPatenteList();
            for (Patente patenteListPatente : patenteList) {
                patenteListPatente.setIdProducto(null);
                patenteListPatente = em.merge(patenteListPatente);
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
