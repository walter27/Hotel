package org.hotelLeon.dominio.servicios;

import java.util.List;

public interface ConsumoRepository {
	
	public void create(Consumo consumo);

	public void update(Consumo consumo);

	public void delete(Consumo consumo);

	public List<Consumo> findAll();

}
