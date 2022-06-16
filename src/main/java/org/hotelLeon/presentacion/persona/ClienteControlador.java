package org.hotelLeon.presentacion.persona;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.persona.ClienteService;
import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.dominio.persona.Empleado;
import org.primefaces.context.RequestContext;

@Named
@ApplicationScoped
public class ClienteControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cliente actualCliente;
	private Cliente editarCliente;
	private List<Cliente> listaClientes;
	private List<Cliente> filteredClientes;
	@Inject
	private ClienteService clienteService;

	@PostConstruct
	private void init() {

		actualCliente = new Cliente();
		listaClientes = clienteService.findAll();

	}

	public Cliente getActualCliente() {
		return actualCliente;
	}

	public void setActualCliente(Cliente actualCliente) {
		this.actualCliente = actualCliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Cliente> getFilteredClientes() {
		return filteredClientes;
	}

	public void setFilteredClientes(List<Cliente> filteredClientes) {
		this.filteredClientes = filteredClientes;
	}

	public Cliente getEditarCliente() {
		return editarCliente;
	}

	public void setEditarCliente(Cliente editarCliente) {
		this.editarCliente = editarCliente;
	}

	public void addCliente() {

		try {
			clienteService.create(actualCliente);
			listaClientes.add(actualCliente);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Cliente registrado"));
			actualCliente = new Cliente();
			RequestContext.getCurrentInstance().execute("PF('cliente').hide()");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void eliminarCliente(Cliente cliente) {
		try {
			clienteService.delete(cliente);
			listaClientes.remove(cliente);
			actualCliente = new Cliente();
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Cliente eliminado"));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							"Cliente contiene historial"));
			actualCliente = new Cliente();

		}

	}

	public void editarCliente() {

		try {
			clienteService.update(editarCliente);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Cliente editado"));
			editarCliente = new Cliente();
			actualCliente = new Cliente();
			RequestContext.getCurrentInstance().execute("PF('editarcliente').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
