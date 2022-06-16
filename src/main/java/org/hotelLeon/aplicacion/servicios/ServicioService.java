package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import org.hotelLeon.dominio.servicios.Servicio;

public interface ServicioService {
	
	public void create(Servicio servicio);

	public void update(Servicio servicio);

	public void delete(Servicio servicio);

	public List<Servicio> findAll();

}
