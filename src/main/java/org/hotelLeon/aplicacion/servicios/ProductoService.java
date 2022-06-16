package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import org.hotelLeon.dominio.servicios.Producto;

public interface ProductoService {
	public void create(Producto producto);

	public void update(Producto producto);

	public void delete(Producto producto);

	public List<Producto> findAll();
	
	public List<Producto> findServicio(String servicio);


}
