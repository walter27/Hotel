package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.servicios.Servicio;
import org.hotelLeon.dominio.servicios.ServicioRepository;

@Stateless
public class DefaultServicioService implements ServicioService {
	
	@Inject
	ServicioRepository servicioRepository;

	@Override
	public void create(Servicio servicio) {

		servicioRepository.create(servicio);
	}

	@Override
	public void delete(Servicio servicio) {

		servicioRepository.delete(servicio);
	}

	@Override
	public void update(Servicio servicio) {

		servicioRepository.update(servicio);
	}

	@Override
	public List<Servicio> findAll() {

		return servicioRepository.findAll();
	}

}
