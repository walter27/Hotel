package org.hotelLeon.aplicacion.factura;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.factura.Ingreso;
import org.hotelLeon.dominio.factura.IngresoRepository;



@Stateless
public class DefaultIngresoService  implements IngresoService{
	
	@Inject
	IngresoRepository ingresoRepository;

	@Override
	public void create(Ingreso ingreso) {

		ingresoRepository.create(ingreso);
	}

	@Override
	public void delete(Ingreso ingreso) {

		ingresoRepository.delete(ingreso);
	}

	@Override
	public void update(Ingreso ingreso) {

		ingresoRepository.update(ingreso);
	}

	@Override
	public List<Ingreso> findAll() {

		return ingresoRepository.findAll();
	}
	
	

}
