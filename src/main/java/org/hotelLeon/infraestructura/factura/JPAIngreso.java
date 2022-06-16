package org.hotelLeon.infraestructura.factura;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.factura.Ingreso;
import org.hotelLeon.dominio.factura.IngresoRepository;

@ApplicationScoped
public class JPAIngreso implements IngresoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Ingreso ingreso) {

		em.persist(ingreso);
	}

	@Override
	public void delete(Ingreso ingreso) {

		Ingreso ingreso1 = em.find(Ingreso.class, ingreso.getIngresosId());
		em.remove(ingreso1);
		em.flush();
	}

	@Override
	public void update(Ingreso ingreso) {

		em.merge(ingreso);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ingreso> findAll() {

		try {
			List<Ingreso> ingresos = null;
			ingresos = em.createQuery(
					"select p from org.hotelLeon.dominio.factura.Ingreso p")
					.getResultList();
			return ingresos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
