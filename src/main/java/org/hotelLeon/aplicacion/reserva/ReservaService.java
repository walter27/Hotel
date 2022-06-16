package org.hotelLeon.aplicacion.reserva;

import java.util.List;

import org.hotelLeon.dominio.reserva.Reserva;


public interface ReservaService {
	public void create(Reserva reserva);

	public void update(Reserva reserva);

	public void delete(Reserva reserva);

	public List<Reserva> findAll();

}
