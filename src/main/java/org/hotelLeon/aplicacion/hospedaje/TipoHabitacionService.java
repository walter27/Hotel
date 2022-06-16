package org.hotelLeon.aplicacion.hospedaje;

import java.util.List;
import org.hotelLeon.dominio.hospedaje.TipoHabitacion;


public interface TipoHabitacionService {
	
	public void create(TipoHabitacion tipoHabitacion);

	public void update(TipoHabitacion tipoHabitacion);

	public void delete(TipoHabitacion tipoHabitacion);

	public List<TipoHabitacion> findAll();

}
