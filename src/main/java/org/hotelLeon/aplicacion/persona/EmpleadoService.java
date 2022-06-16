package org.hotelLeon.aplicacion.persona;

import java.util.List;
import org.hotelLeon.dominio.persona.Empleado;


public interface EmpleadoService {
	
	public void create(Empleado empleado);

	public void update(Empleado empleado);

	public void delete(Empleado empleado);

	public List<Empleado> findAll();

}
