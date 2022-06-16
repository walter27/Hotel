package org.hotelLeon.presentacion.servicios;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.servicios.ProductoService;
import org.hotelLeon.aplicacion.servicios.ServicioService;
import org.hotelLeon.dominio.servicios.Producto;
import org.hotelLeon.dominio.servicios.Servicio;
import org.primefaces.context.RequestContext;

@Named
@ApplicationScoped
public class ProductoControlador implements Serializable {

	private Producto actualProducto;
	private List<Producto> listaProductos;
	private List<Producto> filteredProductos;
	private Producto editarProducto;
	private List<Producto> listaProductosServicio;

	@Inject
	private ProductoService productoService;

	@PostConstruct
	private void init() {

		actualProducto = new Producto();
		editarProducto = new Producto();
		listaProductos = new ArrayList<Producto>();
		listaProductosServicio = new ArrayList<Producto>();
		cargarProductos();

	}

	public Producto getActualProducto() {
		return actualProducto;
	}

	public void setActualProducto(Producto actualProducto) {
		this.actualProducto = actualProducto;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<Producto> getFilteredProductos() {
		return filteredProductos;
	}

	public void setFilteredProductos(List<Producto> filteredProductos) {
		this.filteredProductos = filteredProductos;
	}

	public Producto getEditarProducto() {
		return editarProducto;
	}

	public void setEditarProducto(Producto editarProducto) {
		this.editarProducto = editarProducto;
	}

	public List<Producto> getListaProductosServicio() {
		return listaProductosServicio;
	}

	public void setListaProductosServicio(List<Producto> listaProductosServicio) {
		this.listaProductosServicio = listaProductosServicio;
	}

	public void addProducto() {

		try {
			productoService.create(actualProducto);
			listaProductos.add(actualProducto);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							" Producto registrado"));
			actualProducto = new Producto();
			RequestContext.getCurrentInstance().execute(
					"PF('dlgProducto').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void eliminarProducto(Producto producto) {

		try {
			productoService.delete(producto);
			listaProductos.remove(producto);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							" Producto eliminado"));
			actualProducto = new Producto();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							" Producto contiene historial"));
			actualProducto = new Producto();
		}

	}

	public void editarProducto() {

		try {
			productoService.update(editarProducto);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							" Producto editado"));
			editarProducto = new Producto();
			RequestContext.getCurrentInstance().execute(
					"PF('dlgEditarProducto').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void cargarProductos() {

		listaProductos = productoService.findAll();

	}

	public void pasarProductos() {

		try {
			cargarProductos();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../servicios/mantenimientoProducto.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
