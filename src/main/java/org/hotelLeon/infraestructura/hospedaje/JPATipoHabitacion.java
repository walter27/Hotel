package org.hotelLeon.infraestructura.hospedaje;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.hospedaje.TipoHabitacion;
import org.hotelLeon.dominio.hospedaje.TipoHabitacionRepository;

@ApplicationScoped
public class JPATipoHabitacion implements TipoHabitacionRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(TipoHabitacion tipoHabitacion) {

		em.persist(tipoHabitacion);
	}

	@Override
	public void delete(TipoHabitacion tipoHabitacion) {

		TipoHabitacion tipoHabitacion1 = em.find(TipoHabitacion.class,
				tipoHabitacion.getTipoHabitacionId());
		em.remove(tipoHabitacion1);
		em.flush();
		System.out.println("ELIMNAAAAAADO");
	}

	@Override
	public void update(TipoHabitacion tipoHabitacion) {

		em.merge(tipoHabitacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoHabitacion> findAll() {

		try {
			List<TipoHabitacion> tipoHabitaciones = null;
			tipoHabitaciones = em
					.createQuery(
							"select p from org.hotelLeon.dominio.hospedaje.TipoHabitacion p")
					.getResultList();
			return tipoHabitaciones;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
