package org.hotelLeon.aplicacion.reserva;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.reserva.Reserva;
import org.hotelLeon.dominio.reserva.ReservaRepository;

@Stateless
public class DefaultReservaService implements ReservaService {

	@Inject
	ReservaRepository reservaRepository;

	@Override
	public void create(Reserva reserva) {

		reservaRepository.create(reserva);
	}

	@Override
	public void delete(Reserva reserva) {

		reservaRepository.delete(reserva);
	}

	@Override
	public void update(Reserva reserva) {

		reservaRepository.update(reserva);
	}

	@Override
	public List<Reserva> findAll() {

		return reservaRepository.findAll();
	}

}
