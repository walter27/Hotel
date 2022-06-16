package org.hotelLeon.infraestructura.reserva;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.dominio.reserva.Reserva;
import org.hotelLeon.dominio.reserva.ReservaRepository;

@ApplicationScoped
public class JPAReservaRepository implements ReservaRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Reserva reserva) {

		em.persist(reserva);
	}

	@Override
	public void delete(Reserva reserva) {

		Reserva reserva1 = em.find(Reserva.class, reserva.getReservaId());
		em.remove(reserva1);
		em.flush();
	}

	@Override
	public void update(Reserva reserva) {

		em.merge(reserva);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reserva> findAll() {

		try {
			List<Reserva> reservas = null;
			reservas = em.createQuery(
					"select p from org.hotelLeon.dominio.reserva.Reserva p")
					.getResultList();
			HabitacionEstado estado;
			List<Reserva> listaReservasFinal = new ArrayList<Reserva>();
			for (Reserva reservaFinal : reservas) {
				estado = reservaFinal.getReservaHabitaciones().listIterator()
						.next().getHabitacionEstado();
				if (reservaFinal.isCheckInReserva() == false) {
					listaReservasFinal.add(reservaFinal);
				}

			}
			return listaReservasFinal;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
