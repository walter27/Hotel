package org.hotelLeon.infraestructura.hospedaje;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.hospedaje.HospedajeRepository;

@ApplicationScoped
public class JPAHospedaje implements HospedajeRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Hospedaje hospedaje) {

		em.persist(hospedaje);
	}

	@Override
	public void delete(Hospedaje hospedaje) {

		Hospedaje hospedaje1 = em.find(Hospedaje.class,
				hospedaje.getHospedajeId());
		em.remove(hospedaje1);
		em.flush();
	}

	@Override
	public void update(Hospedaje hospedaje) {

		em.merge(hospedaje);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hospedaje> findAll() {

		try {
			List<Hospedaje> hospedajes = null;
			hospedajes = em
					.createQuery(
							"select p from org.hotelLeon.dominio.hospedaje.Hospedaje p")
					.getResultList();
			return hospedajes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hospedaje> findAll2(String buscarCliente) {
		System.out.println("cliente hospedjae" + buscarCliente);

		try {
			List<Hospedaje> hospedajes = null;
			hospedajes = em
					.createQuery(
							"select p from org.hotelLeon.dominio.hospedaje.Hospedaje p where p.cliente.personaApellidos='"
									+ buscarCliente + "'").getResultList();
			return hospedajes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
