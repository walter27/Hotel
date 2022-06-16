package org.hotelLeon.dominio.persona;

import java.util.List;

public interface EmpleadoRepository {

	public void create(Empleado empleado);

	public void update(Empleado empleado);

	public void delete(Empleado empleado);

	public List<Empleado> findAll();

}
