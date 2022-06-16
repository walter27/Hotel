package org.hotelLeon.infraestructura.hospedaje;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionRepository;

@ApplicationScoped
public class JPAHabitacion implements HabitacionRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Habitacion habitacion) {

		em.persist(habitacion);
	}

	@Override
	public void delete(Habitacion habitacion) {

		Habitacion habitacion1 = em.find(Habitacion.class,
				habitacion.getHabitacionId());
		em.remove(habitacion1);
		em.flush();
		System.out.println("ELIMate");

	}

	@Override
	public void update(Habitacion habitacion) {

		em.merge(habitacion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Habitacion> findAll() {

		try {
			List<Habitacion> habitaciones = null;
			habitaciones = em
					.createQuery(
							"select p from org.hotelLeon.dominio.hospedaje.Habitacion p")
					.getResultList();
			return habitaciones;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
