package org.hotelLeon.presentacion.hospedaje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.hospedaje.HabitacionService;
import org.hotelLeon.aplicacion.hospedaje.HospedajeService;
import org.hotelLeon.dominio.factura.FacturaCabecera;
import org.hotelLeon.dominio.factura.FacturaDetalle;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.TipoPago;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.persona.Cliente;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ApplicationScoped
public class HospedajeControlador implements Serializable {

	private Hospedaje actualHospedaje;
	private List<Hospedaje> listaHospedajes;
	private Cliente cliente;
	private String buscarCliente;

	@Inject
	private HospedajeService hospedajeService;

	@Inject
	Event<Hospedaje> hospedajeListener;

	@PostConstruct
	private void init() {

		cliente = new Cliente();
		actualHospedaje = new Hospedaje();
		listaHospedajes = hospedajeService.findAll();

	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Hospedaje getActualHospedaje() {
		return actualHospedaje;
	}

	public void setActualHospedaje(Hospedaje actualHospedaje) {
		this.actualHospedaje = actualHospedaje;
	}

	public List<Hospedaje> getListaHospedajes() {
		return listaHospedajes;
	}

	public void setListaHospedajes(List<Hospedaje> listaHospedajes) {
		this.listaHospedajes = listaHospedajes;
	}

	public String getBuscarCliente() {
		return buscarCliente;
	}

	public void setBuscarCliente(String buscarCliente) {
		this.buscarCliente = buscarCliente;
	}

	public void addHospedaje() {

		hospedajeService.create(actualHospedaje);
		listaHospedajes.add(actualHospedaje);
		System.out.println("REGISTRO correcto");
		actualHospedaje = new Hospedaje();

	}

	public void eliminarHospedaje(Hospedaje hospedaje) {

		hospedajeService.delete(hospedaje);
		listaHospedajes.remove(hospedaje);
		actualHospedaje = new Hospedaje();
	}

	public void editarHospedaje(Hospedaje hospedaje) {

		hospedajeService.update(hospedaje);
		actualHospedaje = new Hospedaje();
	}

	public void buscarCliente() {

		if (buscarCliente.equals("")) {

			listaHospedajes = hospedajeService.findAll();
		} else {
			/*
			 * String words[] = buscarCliente.split("\\s"); String
			 * capitalizeWord = ""; for (String w : words) { String first =
			 * w.substring(0, 1); String afterfirst = w.substring(1);
			 * capitalizeWord += first.toUpperCase() + afterfirst + " "; }
			 * buscarCliente=capitalizeWord;
			 */
			listaHospedajes = hospedajeService.findAll2(buscarCliente);
			System.out.println("clientee" + buscarCliente);

		}

	}

}