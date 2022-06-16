package org.hotelLeon.dominio.factura;

import java.util.List;

public interface ReciboCabeceraRepository {

	public void create(ReciboCabecera reciboCabecera);

	public void update(ReciboCabecera reciboCabecera);

	public void delete(ReciboCabecera reciboCabecera);

	public List<ReciboCabecera> findAll();
	
	//public List<ReciboCabecera> findAll2(String apellidosCliente);

}
