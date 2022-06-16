package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.servicios.Consumo;
import org.hotelLeon.dominio.servicios.ConsumoRepository;

@Stateless
public class DefaultConsumoService implements ConsumoService {

	@Inject
	ConsumoRepository consumoRepository;

	@Override
	public void create(Consumo consumo) {

		consumoRepository.create(consumo);
	}

	@Override
	public void delete(Consumo consumo) {

		consumoRepository.delete(consumo);
	}

	@Override
	public void update(Consumo consumo) {

		consumoRepository.update(consumo);
	}

	@Override
	public List<Consumo> findAll() {

		return consumoRepository.findAll();
	}

}
