package org.hotelLeon.aplicacion.factura;

import java.util.List;

import org.hotelLeon.dominio.factura.ReciboCabecera;

public interface ReciboCabeceraService {

	public void create(ReciboCabecera reciboCabecera);

	public void update(ReciboCabecera reciboCabecera);

	public void delete(ReciboCabecera reciboCabecera);

	public List<ReciboCabecera> findAll();
	
	//public List<ReciboCabecera> findAll2(String apellidosCliente);


}
