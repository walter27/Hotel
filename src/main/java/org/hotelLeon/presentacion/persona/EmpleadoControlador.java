package org.hotelLeon.presentacion.persona;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.persona.EmpleadoService;
import org.hotelLeon.dominio.persona.Empleado;
import org.primefaces.context.RequestContext;

@Named
@SessionScoped
public class EmpleadoControlador implements Serializable {

	private Empleado actualEmpleado;
	private Empleado editarEmpleado;
	private List<Empleado> listaEmpleados;
	private List<Empleado> filteredEmpleado;
	@Inject
	private EmpleadoService empleadoService;

	@PostConstruct
	private void init() {

		actualEmpleado = new Empleado();
		listaEmpleados = empleadoService.findAll();

	}

	public Empleado getActualEmpleado() {
		return actualEmpleado;
	}

	public void setActualEmpleado(Empleado actualEmpleado) {
		this.actualEmpleado = actualEmpleado;
	}

	public Empleado getEditarEmpleado() {
		return editarEmpleado;
	}

	public void setEditarEmpleado(Empleado editarEmpleado) {
		this.editarEmpleado = editarEmpleado;
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

	public void addEmpleado() {

		try {
			empleadoService.create(actualEmpleado);
			listaEmpleados.add(actualEmpleado);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Empleado registrado"));
			actualEmpleado = new Empleado();
			RequestContext.getCurrentInstance()
					.execute("PF('empleado').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void eliminarEmpleado(Empleado empleado) {

		try {
			empleadoService.delete(empleado);
			listaEmpleados.remove(empleado);
			actualEmpleado = new Empleado();
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Empleado eliminado"));
			actualEmpleado = new Empleado();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							"Empleado contiene historial"));
			actualEmpleado = new Empleado();
		}

	}

	public void editarEmpleado() {

		try {
			empleadoService.update(editarEmpleado);
			editarEmpleado = new Empleado();
			actualEmpleado = new Empleado();
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Empleado editado"));
			RequestContext.getCurrentInstance().execute(
					"PF('editarempleado').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
