package org.hotelLeon.presentacion.persona;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.persona.EmpleadoService;
import org.hotelLeon.dominio.persona.Empleado;

@Named
@SessionScoped
public class DlgEmpleado implements Serializable {

	private List<Empleado> listaEmpleados;
	private List<Empleado> filteredEmpleado;

	@Inject
	Event<Empleado> empleadoListener;

	@Inject
	private EmpleadoService empleadoService;

	@PostConstruct
	private void init() {
		loadEmpleados();

	}

	public List<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public List<Empleado> getFilteredEmpleado() {
		return filteredEmpleado;
	}

	public void setFilteredEmpleado(List<Empleado> filteredEmpleado) {
		this.filteredEmpleado = filteredEmpleado;
	}

	public void loadEmpleados() {

		listaEmpleados = empleadoService.findAll();
		System.out.println("CARGAREMPLEADOS");

	}

	public void enviarEmpleadoSelecionado(Empleado empleado) {

		empleadoListener.fire(empleado);

	}

}
