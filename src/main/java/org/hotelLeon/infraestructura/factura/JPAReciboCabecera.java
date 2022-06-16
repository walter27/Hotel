package org.hotelLeon.infraestructura.factura;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.ReciboCabeceraRepository;

@ApplicationScoped
public class JPAReciboCabecera implements ReciboCabeceraRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(ReciboCabecera reciboCabecera) {

		em.persist(reciboCabecera);
	}

	@Override
	public void delete(ReciboCabecera reciboCabecera) {

		ReciboCabecera reciboCabecera1 = em.find(ReciboCabecera.class,
				reciboCabecera.getReciboCabeceraId());
		em.remove(reciboCabecera1);
		em.flush();
	}

	@Override
	public void update(ReciboCabecera reciboCabecera) {

		em.merge(reciboCabecera);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReciboCabecera> findAll() {

		try {
			List<ReciboCabecera> listaRecibos = null;
			listaRecibos = em.createQuery("select p from ReciboCabecera p")
					.getResultList();
			return listaRecibos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

