package org.hotelLeon.presentacion.hospedaje;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.hospedaje.HabitacionService;
import org.hotelLeon.aplicacion.hospedaje.TipoHabitacionService;
import org.hotelLeon.dominio.factura.ReciboDetalle;
import org.hotelLeon.dominio.hospedaje.Arriendo;
import org.hotelLeon.dominio.hospedaje.EstadoLimpieza;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionEdificio;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.dominio.hospedaje.TipoHabitacion;
import org.primefaces.context.RequestContext;

@Named
@ApplicationScoped
public class HabitacionControlador implements Serializable {

	private Habitacion actualHabitacion;
	private List<Habitacion> listaHabitaciones;
	private HabitacionEdificio[] listaEdificios;
	private HabitacionEstado[] listaEstados;
	private Habitacion habitacionSeleccionada;
	private Habitacion editarHabitacion;
	private List<Habitacion> filteredHabitacion;
	private Arriendo[] listaArriendos;

	@Inject
	private HabitacionService habitacionService;

	@PostConstruct
	private void init() {

		editarHabitacion = new Habitacion();
		actualHabitacion = new Habitacion();
		listaHabitaciones = habitacionService.findAll();

	}

	public Habitacion getActualHabitacion() {
		return actualHabitacion;
	}

	public void setActualHabitacion(Habitacion actualHabitacion) {
		this.actualHabitacion = actualHabitacion;
	}

	public List<Habitacion> getListaHabitaciones() {
		return listaHabitaciones;
	}

	public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
		this.listaHabitaciones = listaHabitaciones;
	}

	public HabitacionEdificio[] getListaEdificios() { 
		return listaEdificios = HabitacionEdificio.values();
	}

	public HabitacionEstado[] getListaEstados() {
		return listaEstados = HabitacionEstado.values();
	}

	public Habitacion getHabitacionSeleccionada() {
		return habitacionSeleccionada;
	}

	public void setHabitacionSeleccionada(Habitacion habitacionSeleccionada) {
		this.habitacionSeleccionada = habitacionSeleccionada;
	}

	public Habitacion getEditarHabitacion() {
		return editarHabitacion;
	}

	public void setEditarHabitacion(Habitacion editarHabitacion) {
		this.editarHabitacion = editarHabitacion;
	}

	public List<Habitacion> getFilteredHabitacion() {
		return filteredHabitacion;
	}

	public void setFilteredHabitacion(List<Habitacion> filteredHabitacion) {
		this.filteredHabitacion = filteredHabitacion;
	}

	public Arriendo[] getListaArriendos() {
		return listaArriendos = Arriendo.values();
	}

	public void addHabitacion() {

		try {
			actualHabitacion.setEstadoLimpieza(EstadoLimpieza.TERMINADO);
			actualHabitacion.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
			habitacionService.create(actualHabitacion);
			listaHabitaciones.add(actualHabitacion);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Habitacion Registrada"));
			actualHabitacion = new Habitacion();
			RequestContext.getCurrentInstance().execute(
					"PF('habitacion').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void eliminarHabitacion(Habitacion habitacion) {

		try {
			habitacionService.delete(habitacion);
			listaHabitaciones.remove(habitacion);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							"Habitacion eliminada"));

			actualHabitacion = new Habitacion();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							"Habitacion contiene historial"));

			actualHabitacion = new Habitacion();
		}

	}

	public void editarHabitacion() {

		try {
			habitacionService.update(editarHabitacion);
			System.out.println("estadoooooo"
					+ editarHabitacion.getHabitacionEstado());
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Habitacion editada"));
			editarHabitacion = new Habitacion();
			actualHabitacion = new Habitacion();
			RequestContext.getCurrentInstance().execute(
					"PF('editarhabitacion').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
