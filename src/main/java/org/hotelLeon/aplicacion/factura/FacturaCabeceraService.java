package org.hotelLeon.aplicacion.factura;

import org.hotelLeon.dominio.factura.FacturaCabecera;

public interface FacturaCabeceraService {
	
	public void create(FacturaCabecera facturaCabecera);

	public void delete(FacturaCabecera facturaCabecera);

	public void update(FacturaCabecera facturaCabecera);

}
