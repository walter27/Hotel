package org.hotelLeon.infraestructura.servicios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.servicios.Servicio;
import org.hotelLeon.dominio.servicios.ServicioRepository;

@ApplicationScoped
public class JPAServicioRepository implements ServicioRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Servicio servicio) {

		em.persist(servicio);
	}

	@Override
	public void delete(Servicio servicio) {

		Servicio servicio1 = em.find(Servicio.class, servicio.getServicioId());
		em.remove(servicio1);
		em.flush();
	}

	@Override
	public void update(Servicio servicio) {

		em.merge(servicio);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Servicio> findAll() {

		try {
			List<Servicio> servicios = null;
			servicios = em.createQuery(
					"select p from org.hotelLeon.dominio.servicios.Servicio p")
					.getResultList();
			return servicios;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
