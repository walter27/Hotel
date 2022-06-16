package org.hotelLeon.aplicacion.factura;

import javax.ejb.Stateless;
import javax.inject.Inject;
import org.hotelLeon.dominio.factura.FacturaCabecera;
import org.hotelLeon.dominio.factura.FacturaCabeceraRepository;

@Stateless
public class DefaultFacturaCabeceraService implements FacturaCabeceraService {

	@Inject
	FacturaCabeceraRepository facturaCabeceraRepository;

	@Override
	public void create(FacturaCabecera facturaCabecera) {

		facturaCabeceraRepository.create(facturaCabecera);
	}

	@Override
	public void delete(FacturaCabecera facturaCabecera) {

		facturaCabeceraRepository.delete(facturaCabecera);
	}

	@Override
	public void update(FacturaCabecera facturaCabecera) {

		facturaCabeceraRepository.update(facturaCabecera);
	}

}
