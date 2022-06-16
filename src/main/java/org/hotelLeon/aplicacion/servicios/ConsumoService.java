package org.hotelLeon.aplicacion.servicios;

import java.util.List;

import org.hotelLeon.dominio.servicios.Consumo;

public interface ConsumoService {
	public void create(Consumo consumo);

	public void update(Consumo consumo);

	public void delete(Consumo consumo);

	public List<Consumo> findAll();

}
