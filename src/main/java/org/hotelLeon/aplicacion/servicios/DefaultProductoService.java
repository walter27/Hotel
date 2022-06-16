package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.servicios.Producto;
import org.hotelLeon.dominio.servicios.ProductoRepository;

@Stateless
public class DefaultProductoService implements ProductoService {

	@Inject
	ProductoRepository productoRepository;

	@Override
	public void create(Producto producto) {

		productoRepository.create(producto);
	}

	@Override
	public void delete(Producto producto) {

		productoRepository.delete(producto);
	}

	@Override
	public void update(Producto producto) {

		productoRepository.update(producto);
	}

	@Override
	public List<Producto> findAll() {

		return productoRepository.findAll();
	}

	@Override
	public List<Producto> findServicio(String servicio) {

		return productoRepository.findServicio(servicio);
	}

}
