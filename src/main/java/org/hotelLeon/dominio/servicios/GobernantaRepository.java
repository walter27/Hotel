package org.hotelLeon.dominio.servicios;

import java.util.List;

public interface GobernantaRepository {

	public void create(Gobernanta gobernanta);

	public void update(Gobernanta gobernanta);

	public void delete(Gobernanta gobernanta);

	public List<Gobernanta> findAll();
}
