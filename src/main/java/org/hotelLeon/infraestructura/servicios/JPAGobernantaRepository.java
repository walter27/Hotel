package org.hotelLeon.infraestructura.servicios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.servicios.Gobernanta;
import org.hotelLeon.dominio.servicios.GobernantaRepository;

@ApplicationScoped
public class JPAGobernantaRepository implements GobernantaRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Gobernanta gobernanta) {

		em.persist(gobernanta);
	}

	@Override
	public void delete(Gobernanta gobernanta) {

		Gobernanta gobernanta1 = em.find(Gobernanta.class,
				gobernanta.getGobernantaId());
		em.remove(gobernanta1);
		em.flush();
	}

	@Override
	public void update(Gobernanta gobernanta) {

		em.merge(gobernanta);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gobernanta> findAll() {

		try {
			List<Gobernanta> gobernanta = null;
			gobernanta = em
					.createQuery(
							"select p from org.hotelLeon.dominio.servicios.Gobernanta p")
					.getResultList();
			return gobernanta;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
