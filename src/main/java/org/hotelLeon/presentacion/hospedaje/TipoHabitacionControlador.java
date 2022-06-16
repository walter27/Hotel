package org.hotelLeon.presentacion.hospedaje;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.hospedaje.TipoHabitacionService;
import org.hotelLeon.dominio.hospedaje.TipoHabitacion;
import org.hotelLeon.dominio.servicios.Servicio;
import org.primefaces.context.RequestContext;

@Named
@SessionScoped
public class TipoHabitacionControlador implements Serializable {

	private TipoHabitacion actualTipoHabitacion;
	private List<TipoHabitacion> listaTipoHabitaciones;
	private List<TipoHabitacion> filteredTipoHabitaciones;
	private TipoHabitacion editarTipoHabitacion;

	@Inject
	private TipoHabitacionService tipoHabitacionService;

	@PostConstruct
	private void init() {

		actualTipoHabitacion = new TipoHabitacion();
		listaTipoHabitaciones = tipoHabitacionService.findAll();

	}

	public TipoHabitacion getActualTipoHabitacion() {
		return actualTipoHabitacion;
	}

	public void setActualTipoHabitacion(TipoHabitacion actualTipoHabitacion) {
		this.actualTipoHabitacion = actualTipoHabitacion;
	}

	public List<TipoHabitacion> getListaTipoHabitaciones() {
		return listaTipoHabitaciones;
	}

	public void setListaTipoHabitaciones(
			List<TipoHabitacion> listaTipoHabitaciones) {
		this.listaTipoHabitaciones = listaTipoHabitaciones;
	}

	public List<TipoHabitacion> getFilteredTipoHabitaciones() {
		return filteredTipoHabitaciones;
	}

	public void setFilteredTipoHabitaciones(
			List<TipoHabitacion> filteredTipoHabitaciones) {
		this.filteredTipoHabitaciones = filteredTipoHabitaciones;
	}

	public TipoHabitacion getEditarTipoHabitacion() {
		return editarTipoHabitacion;
	}

	public void setEditarTipoHabitacion(TipoHabitacion editarTipoHabitacion) {
		this.editarTipoHabitacion = editarTipoHabitacion;
	}

	public void addTipoHabitacion() {

		try {
			tipoHabitacionService.create(actualTipoHabitacion);
			listaTipoHabitaciones.add(actualTipoHabitacion);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							" Tipo habitacion registrado"));
			actualTipoHabitacion = new TipoHabitacion();
			RequestContext.getCurrentInstance().execute(
					"PF('tipohabitacion').hide()");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void eliminarTipoHabitacion(TipoHabitacion tipoHabitacion) {

		try {

			tipoHabitacionService.delete(tipoHabitacion);
			listaTipoHabitaciones.remove(tipoHabitacion);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							" Tipo habitacion eliminado"));
			actualTipoHabitacion = new TipoHabitacion();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							" Tipo habitacion contiene historial"));
			actualTipoHabitacion = new TipoHabitacion();
		}

	}

	public void editarTipoHabitacion() {

		try {
			tipoHabitacionService.update(editarTipoHabitacion);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							" Tipo habitacion editado"));
			editarTipoHabitacion = new TipoHabitacion();
			RequestContext.getCurrentInstance().execute(
					"PF('editartipohabitacion').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public TipoHabitacion getTipoHabitacion(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("no id provided");
		}
		for (TipoHabitacion tipoHabitacion : listaTipoHabitaciones) {
			if (id.equals(tipoHabitacion.getTipoHabitacionId())) {

				return tipoHabitacion;
			}
		}
		return null;
	}

}
