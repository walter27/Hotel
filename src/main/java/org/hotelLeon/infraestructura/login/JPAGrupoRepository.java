package org.hotelLeon.infraestructura.login;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.login.Grupo;
import org.hotelLeon.dominio.login.GrupoRepository;

@ApplicationScoped
public class JPAGrupoRepository implements GrupoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Grupo grupo) {

		em.persist(grupo);
	}

	@Override
	public void delete(Grupo grupo) {

		Grupo grupo1 = em.find(Grupo.class, grupo.getGrupoId());
		em.remove(grupo1);
		em.flush();
	}

	@Override
	public void update(Grupo grupo) {

		em.merge(grupo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grupo> findAll() {

		try {
			List<Grupo> grupos = null;
			grupos = em.createQuery(
					"select p from org.hotelLeon.dominio.login.Grupo p")
					.getResultList();
			return grupos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
