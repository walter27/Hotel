package org.hotelLeon.infraestructura.servicios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.servicios.Consumo;
import org.hotelLeon.dominio.servicios.ConsumoRepository;

@ApplicationScoped
public class JPAConsumoRepository implements ConsumoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Consumo consumo) {

		em.persist(consumo);
	}

	@Override
	public void delete(Consumo consumo) {

		Consumo consumo1 = em.find(Consumo.class, consumo.getConsumoId());
		em.remove(consumo1);
		em.flush();
	}

	@Override
	public void update(Consumo consumo) {

		em.merge(consumo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Consumo> findAll() {

		try {
			List<Consumo> consumos = null;
			consumos = em.createQuery(
					"select p from org.hotelLeon.dominio.servicios.Consumo p")
					.getResultList();
			return consumos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
