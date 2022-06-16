package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import org.hotelLeon.dominio.servicios.Gobernanta;

public interface GobernantaService {
	public void create(Gobernanta gobernanta);

	public void update(Gobernanta gobernanta);

	public void delete(Gobernanta gobernanta);

	public List<Gobernanta> findAll();

}
