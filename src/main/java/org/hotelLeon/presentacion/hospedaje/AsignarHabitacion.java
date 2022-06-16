package org.hotelLeon.presentacion.hospedaje;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;

import org.hibernate.jpamodelgen.util.StringUtil;
import org.hotelLeon.aplicacion.hospedaje.HabitacionService;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.presentacion.factura.ReciboControlador;
import org.hotelLeon.presentacion.reserva.ReservaControlador;
import org.primefaces.context.RequestContext;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Named
@ApplicationScoped
public class AsignarHabitacion implements Serializable {

	private List<Habitacion> listaHabitaciones;

	@Inject
	Event<Habitacion> habitacionListener;

	@Inject
	private HabitacionService habitacionService;

	@PostConstruct
	private void init() {

		loadHabitaciones();
	}

	public List<Habitacion> getListaHabitaciones() {
		return listaHabitaciones;
	}

	public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
		this.listaHabitaciones = listaHabitaciones;
	}

	public void loadHabitaciones() {

		listaHabitaciones = habitacionService.findAll();

	}

	public void enviarHabitacionSeleccionada(Habitacion habitacion) {

		if (habitacion.getHabitacionEstado()
				.equals(HabitacionEstado.DISPONIBLE)) {
			habitacionListener.fire(habitacion);

		} else {
			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Habitacion " + habitacion.getHabitacionCodigo()
									+ " "+habitacion.getHabitacionEstado()));
		}
	}

	public void cargarHabitaciones() throws IOException {

		loadHabitaciones();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../hospedaje/planning.xhtml");

	}

}
