package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.servicios.Gobernanta;
import org.hotelLeon.dominio.servicios.GobernantaRepository;

@Stateless
public class DefaultGobernantaService implements GobernantaService {

	@Inject
	GobernantaRepository gobernantaRepository;

	@Override
	public void create(Gobernanta gobernanta) {

		gobernantaRepository.create(gobernanta);
	}

	@Override
	public void delete(Gobernanta gobernanta) {

		gobernantaRepository.delete(gobernanta);
	}

	@Override
	public void update(Gobernanta gobernanta) {

		gobernantaRepository.update(gobernanta);
	}

	@Override
	public List<Gobernanta> findAll() {

		return gobernantaRepository.findAll();
	}

}
