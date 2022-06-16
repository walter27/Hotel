package org.hotelLeon.aplicacion.persona;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.persona.Empleado;
import org.hotelLeon.dominio.persona.EmpleadoRepository;

@Stateless
public class DefaultEmpleadoService implements EmpleadoService {
	

	@Inject
	EmpleadoRepository empleadoRepository;

	@Override
	public void create(Empleado empleado) {

		empleadoRepository.create(empleado);
	}

	@Override
	public void delete(Empleado empleado) {

		empleadoRepository.delete(empleado);
	}

	@Override
	public void update(Empleado empleado) {

		empleadoRepository.update(empleado);
	}

	@Override
	public List<Empleado> findAll() {

		return empleadoRepository.findAll();
	}

}
