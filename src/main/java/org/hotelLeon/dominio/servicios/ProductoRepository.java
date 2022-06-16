package org.hotelLeon.dominio.servicios;

import java.util.List;

public interface ProductoRepository {
	public void create(Producto producto);

	public void update(Producto produto);

	public void delete(Producto producto);

	public List<Producto> findAll();

	public List<Producto> findServicio(String servicio);

}
