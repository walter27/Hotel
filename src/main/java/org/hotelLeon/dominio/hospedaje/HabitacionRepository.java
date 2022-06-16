package org.hotelLeon.dominio.hospedaje;

import java.util.List;

public interface HabitacionRepository {
	
	public void create(Habitacion habitacion);

	public void update(Habitacion habitacion);

	public void delete(Habitacion habitacion);

	public List<Habitacion> findAll();
	}
