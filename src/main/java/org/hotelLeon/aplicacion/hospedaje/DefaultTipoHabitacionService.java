package org.hotelLeon.aplicacion.hospedaje;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.hotelLeon.dominio.hospedaje.TipoHabitacion;
import org.hotelLeon.dominio.hospedaje.TipoHabitacionRepository;

@Stateless
public class DefaultTipoHabitacionService implements TipoHabitacionService {

	@Inject
	TipoHabitacionRepository tipoHabitacionRepository;

	@Override
	public void create(TipoHabitacion tipoHabitacion) {

		tipoHabitacionRepository.create(tipoHabitacion);
	}

	@Override
	public void delete(TipoHabitacion tipoHabitacion) {

		tipoHabitacionRepository.delete(tipoHabitacion);
	}

	@Override
	public void update(TipoHabitacion tipoHabitacion) {

		tipoHabitacionRepository.update(tipoHabitacion);
	}

	@Override
	public List<TipoHabitacion> findAll() {

		return tipoHabitacionRepository.findAll();
	}

}
