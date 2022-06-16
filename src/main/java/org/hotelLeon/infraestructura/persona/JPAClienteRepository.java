package org.hotelLeon.infraestructura.persona;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.dominio.persona.ClienteRepository;

@ApplicationScoped
public class JPAClienteRepository implements ClienteRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Cliente cliente) {

		em.persist(cliente);
	}

	@Override
	public void delete(Cliente cliente) {

		Cliente cliente1 = em.find(Cliente.class, cliente.getPersonaId());
		em.remove(cliente1);
		em.flush();
	}

	@Override
	public void update(Cliente cliente) {

		em.merge(cliente);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() {

		try {
			List<Cliente> clientes = null;
			clientes = em
					.createQuery(
							"select p from org.hotelLeon.dominio.persona.Cliente p")
					.getResultList();
			return clientes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
