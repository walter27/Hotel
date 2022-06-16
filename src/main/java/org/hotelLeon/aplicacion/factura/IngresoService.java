package org.hotelLeon.aplicacion.factura;

import java.util.List;

import org.hotelLeon.dominio.factura.Ingreso;

public interface IngresoService {
	public void create(Ingreso ingreso);

	public void update(Ingreso ingreso);

	public void delete(Ingreso ingreso);

	public List<Ingreso> findAll();

}
