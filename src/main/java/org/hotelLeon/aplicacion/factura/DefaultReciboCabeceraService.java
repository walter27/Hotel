package org.hotelLeon.aplicacion.factura;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.ReciboCabeceraRepository;

@Stateless
public class DefaultReciboCabeceraService implements ReciboCabeceraService {

	@Inject
	ReciboCabeceraRepository reciboCabeceraRepository;

	@Override
	public void create(ReciboCabecera reciboCabecera) {

		reciboCabeceraRepository.create(reciboCabecera);
	}

	@Override
	public void delete(ReciboCabecera reciboCabecera) {

		reciboCabeceraRepository.delete(reciboCabecera);
	}

	@Override
	public void update(ReciboCabecera reciboCabecera) {

		reciboCabeceraRepository.update(reciboCabecera);
	}

	@Override
	public List<ReciboCabecera> findAll() {

		return reciboCabeceraRepository.findAll();
	}

	/*@Override
	public List<ReciboCabecera> findAll2(String cedula) {

		return reciboCabeceraRepository.findAll2(cedula);
	}*/

}
