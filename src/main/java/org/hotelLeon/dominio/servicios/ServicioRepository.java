package org.hotelLeon.dominio.servicios;

import java.util.List;

public interface ServicioRepository {
	public void create(Servicio servicio);

	public void update(Servicio servicio);

	public void delete(Servicio servicio);

	public List<Servicio> findAll();

}
