package org.hotelLeon.infraestructura.persona;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.persona.UsuarioRepository;
import org.hotelLeon.dominio.persona.UsuarioSistema;

@ApplicationScoped
public class JPAUsuarioRepository implements UsuarioRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(UsuarioSistema usuario) {

		em.persist(usuario);
	}

	@Override
	public void delete(UsuarioSistema usuario) {

		UsuarioSistema usuario1 = em.find(UsuarioSistema.class,
				usuario.getPersonaId());
		em.remove(usuario1);
		em.flush();
	}

	@Override
	public void update(UsuarioSistema usuario) {

		em.merge(usuario);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioSistema> findAll() {

		try {
			List<UsuarioSistema> usuarios = null;
			usuarios = em
					.createQuery(
							"select p from org.hotelLeon.dominio.persona.UsuarioSistema p")
					.getResultList();
			return usuarios;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
