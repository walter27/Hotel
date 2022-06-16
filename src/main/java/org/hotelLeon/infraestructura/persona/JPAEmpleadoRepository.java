package org.hotelLeon.infraestructura.persona;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.persona.Empleado;
import org.hotelLeon.dominio.persona.EmpleadoRepository;

@ApplicationScoped
public class JPAEmpleadoRepository implements EmpleadoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Empleado empleado) {

		em.persist(empleado);
	}

	@Override
	public void delete(Empleado empleado) {

		Empleado empleado1 = em.find(Empleado.class, empleado.getPersonaId());
		em.remove(empleado1);
		em.flush();
	}

	@Override
	public void update(Empleado empleado) {

		em.merge(empleado);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empleado> findAll() {

		try {
			List<Empleado> empleados = null;
			empleados = em.createQuery(
					"select p from org.hotelLeon.dominio.persona.Empleado p")
					.getResultList();
			return empleados;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
