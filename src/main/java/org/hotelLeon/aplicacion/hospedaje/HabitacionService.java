package org.hotelLeon.aplicacion.hospedaje;

import java.util.List;

import org.hotelLeon.dominio.hospedaje.Habitacion;

public interface HabitacionService {

	public void create(Habitacion habitacion);

	public void update(Habitacion habitacion);

	public void delete(Habitacion habitacion);

	public List<Habitacion> findAll();
	
}
