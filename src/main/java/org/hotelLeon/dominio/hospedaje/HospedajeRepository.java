package org.hotelLeon.dominio.hospedaje;

import java.util.List;

public interface HospedajeRepository {

	public void create(Hospedaje hospedaje);

	public void update(Hospedaje hospedaje);

	public void delete(Hospedaje hospedaje);

	public  List<Hospedaje> findAll();
	
	public List<Hospedaje> findAll2( String buscarCliente);
		

}
