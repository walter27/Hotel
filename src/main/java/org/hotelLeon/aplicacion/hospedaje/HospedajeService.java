package org.hotelLeon.aplicacion.hospedaje;

import java.util.List;
import org.hotelLeon.dominio.hospedaje.Hospedaje;


public interface HospedajeService {
	
	public void create(Hospedaje hospedaje);

	public void update(Hospedaje hospedaje);

	public void delete(Hospedaje hospedaje);

	public List<Hospedaje> findAll();
	
	public List<Hospedaje> findAll2(String buscarCliente);


}
