package org.hotelLeon.presentacion.hospedaje;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.hospedaje.TipoHabitacionService;
import org.hotelLeon.dominio.hospedaje.TipoHabitacion;

@Named
@RequestScoped
public class AsignarTipoHabitacion implements Serializable {

	private List<TipoHabitacion> listaTipoHabitaciones;

	@Inject
	Event<TipoHabitacion> tipoHabitacionListener;

	@Inject
	private TipoHabitacionService tipoHabitacionService;

	@PostConstruct
	private void init() {

		loadTipoHabitaciones();

	}

	public List<TipoHabitacion> getListaTipoHabitaciones() {
		return listaTipoHabitaciones;
	}

	public void setListaTipoHabitaciones(
			List<TipoHabitacion> listaTipoHabitaciones) {
		this.listaTipoHabitaciones = listaTipoHabitaciones;
	}

	public void loadTipoHabitaciones() {

		listaTipoHabitaciones = tipoHabitacionService.findAll();
	}

	public void enviarTipoHabitacionSelecionada(TipoHabitacion tipoHabitacion) {

		tipoHabitacionListener.fire(tipoHabitacion);

	}

}
