package org.hotelLeon.infraestructura.servicios;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.servicios.Producto;
import org.hotelLeon.dominio.servicios.ProductoRepository;

@ApplicationScoped
public class JPAProductoRepository implements ProductoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Producto producto) {

		em.persist(producto);
	}

	@Override
	public void delete(Producto producto) {

		Producto producto1 = em.find(Producto.class, producto.getProductoId());
		em.remove(producto1);
		em.flush();
	}

	@Override
	public void update(Producto producto) {

		em.merge(producto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findAll() {

		try {
			List<Producto> productos = null;
			productos = em.createQuery(
					"select p from org.hotelLeon.dominio.servicios.Producto p")
					.getResultList();
			return productos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findServicio(String servicio) {

		try {
			List<Producto> productos = null;
			productos = em
					.createQuery(
							"select p from org.hotelLeon.dominio.servicios.Producto p where p.productoServicio.servicioNombre='"
									+ servicio + "'").getResultList();
			return productos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
