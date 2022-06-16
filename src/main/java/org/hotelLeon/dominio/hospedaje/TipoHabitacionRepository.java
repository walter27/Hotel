package org.hotelLeon.dominio.hospedaje;

import java.util.List;

public interface TipoHabitacionRepository {
	
	public void create(TipoHabitacion tipoHabitacion);

	public void update(TipoHabitacion tipoHabitacion);

	public void delete(TipoHabitacion tipoHabitacion);

	public List<TipoHabitacion> findAll();

}
