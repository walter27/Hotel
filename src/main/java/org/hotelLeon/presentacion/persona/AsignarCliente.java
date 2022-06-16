package org.hotelLeon.presentacion.persona;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.persona.ClienteService;
import org.hotelLeon.dominio.persona.Cliente;

@Named
@SessionScoped
public class AsignarCliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cliente> listaClientes;
	private List<Cliente> filteredClientes;

	@Inject
	Event<Cliente> clienteListener;

	@Inject
	private ClienteService clienteService;

	@PostConstruct
	private void init() {

		loadClientes();

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

	public void loadClientes() {

		listaClientes = clienteService.findAll();
		System.out.println("CARGARCLIENTES");


	}

	public void enviarClienteSelecionado(Cliente cliente) {

		clienteListener.fire(cliente);
		System.out.println("pppppppppppppp" + cliente.getPersonaNombres());

	}

}
