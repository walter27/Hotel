package org.hotelLeon.dominio.reserva;

import java.util.List;

public interface ReservaRepository {
	
	public void create(Reserva reserva );

	public void update(Reserva reserva);

	public void delete(Reserva reserva);

	public  List<Reserva> findAll();

}
