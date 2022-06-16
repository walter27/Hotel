package org.hotelLeon.dominio.factura;

import java.util.List;

public interface IngresoRepository {

	public void create(Ingreso ingreso);

	public void update(Ingreso ingreso);

	public void delete(Ingreso ingreso);

	public List<Ingreso> findAll();

}
