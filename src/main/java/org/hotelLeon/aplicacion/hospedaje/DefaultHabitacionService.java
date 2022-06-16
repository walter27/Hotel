package org.hotelLeon.aplicacion.hospedaje;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionRepository;

@Stateless
public class DefaultHabitacionService implements HabitacionService {

	@Inject
	HabitacionRepository habitacionRepository;

	@Override
	public void create(Habitacion habitacion) {

		habitacionRepository.create(habitacion);
	}

	@Override
	public void delete(Habitacion habitacion) {

		habitacionRepository.delete(habitacion);
	}

	@Override
	public void update(Habitacion habitacion) {

		habitacionRepository.update(habitacion);
	}

	@Override
	public List<Habitacion> findAll() {

		return habitacionRepository.findAll();
	}

}
