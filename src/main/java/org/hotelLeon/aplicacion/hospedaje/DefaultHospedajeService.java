package org.hotelLeon.aplicacion.hospedaje;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.hospedaje.HospedajeRepository;

@Stateless
public class DefaultHospedajeService implements HospedajeService {

	@Inject
	HospedajeRepository hospedajeRepository;

	@Override
	public void create(Hospedaje hospedaje) {

		hospedajeRepository.create(hospedaje);
	}

	@Override
	public void delete(Hospedaje hospedaje) {

		hospedajeRepository.delete(hospedaje);
	}

	@Override
	public void update(Hospedaje hospedaje) {

		hospedajeRepository.update(hospedaje);
	}

	@Override
	public List<Hospedaje> findAll() {

		return hospedajeRepository.findAll();
	}
	
	@Override
	public List<Hospedaje> findAll2(String buscarCliente) {

		return hospedajeRepository.findAll2(buscarCliente);
	}

}
