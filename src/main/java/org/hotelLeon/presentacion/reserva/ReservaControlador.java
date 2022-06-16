package org.hotelLeon.presentacion.reserva;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.hospedaje.HabitacionService;
import org.hotelLeon.aplicacion.reserva.ReservaService;
import org.hotelLeon.dominio.hospedaje.EstadoLimpieza;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.dominio.reserva.Reserva;
import org.hotelLeon.dominio.reserva.ReservaEstado;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@SessionScoped
public class ReservaControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Reserva actualReserva;
	private List<Reserva> listaReservas;
	private List<Habitacion> habitacionesSeleccionadas;
	private ReservaEstado[] estadoReserva;
	private Habitacion habitacionSeleccionada;
	private int casoReserva;
	private List<Habitacion> habitacionesReserva;
	private String buscarCliente;

	@Inject
	private HabitacionService habitacionService;

	@Inject
	private ReservaService reservaService;

	@Inject
	Event<List<Habitacion>> habitacionListener;

	@PostConstruct
	private void init() {

		actualReserva = new Reserva();
		habitacionesSeleccionadas = new ArrayList<Habitacion>();
		habitacionSeleccionada = new Habitacion();
		habitacionesReserva = new ArrayList<Habitacion>();
		listaReservas = new ArrayList<Reserva>();
		cargarReservas();

	}

	public List<Reserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
	}

	public List<Habitacion> getHabitacionesSeleccionadas() {
		return habitacionesSeleccionadas;
	}

	public void setHabitacionesSeleccionadas(
			List<Habitacion> habitacionesSeleccionadas) {
		this.habitacionesSeleccionadas = habitacionesSeleccionadas;
	}

	public Reserva getActualReserva() {
		return actualReserva;
	}

	public void setActualReserva(Reserva actualReserva) {
		this.actualReserva = actualReserva;
	}

	public ReservaEstado[] getEstadoReserva() {
		return estadoReserva = ReservaEstado.values();
	}

	public void setEstadoReserva(ReservaEstado[] estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

	public Habitacion getHabitacionSeleccionada() {
		return habitacionSeleccionada;
	}

	public void setHabitacionSeleccionada(Habitacion habitacionSeleccionada) {
		this.habitacionSeleccionada = habitacionSeleccionada;
	}

	public List<Habitacion> getHabitacionesReserva() {
		return habitacionesReserva;
	}

	public void setHabitacionesReserva(List<Habitacion> habitacionesReserva) {
		this.habitacionesReserva = habitacionesReserva;
	}

	public String getBuscarCliente() {
		return buscarCliente;
	}

	public void setBuscarCliente(String buscarCliente) {
		this.buscarCliente = buscarCliente;
	}

	public void addReserva() {

		if (actualReserva.getClienteReserva() == null
				|| habitacionesSeleccionadas.isEmpty()
				|| actualReserva.getReservaEstado() == null) {

			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Campos requeridos"));

		} else {

			booleanHabitacion();
			cargarHabitaciones();
			listaReservas.add(actualReserva);
			reservaService.create(actualReserva);
			habitacionesSeleccionadas.clear();
			actualReserva = new Reserva();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../hospedaje/planning.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void eliminarReserva() {

		actualizarHabitacionDisponible();
		reservaService.delete(actualReserva);
		listaReservas.remove(actualReserva);
		actualReserva = new Reserva();
		FacesContext.getCurrentInstance().addMessage(
				"messages",
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
						"Reserva eliminada"));

	}

	public void recivirCliente(
			@Observes(notifyObserver = Reception.IF_EXISTS) Cliente cliente) {
		if (casoReserva == 3) {
			actualReserva.setClienteReserva(cliente);
			RequestContext.getCurrentInstance().update("form:cedula");

		}
	}

	public void recivirHabitacion(Habitacion habitacion) {

		casoReserva = 3;
		if (habitacion.getEstadoLimpieza().equals(EstadoLimpieza.LIMPEZA)) {
			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							"Habitacion " + habitacion.getHabitacionCodigo()
									+ " en limpieza"));
			habitacion.setHabitacionboolean(true);
			RequestContext.getCurrentInstance().update("form:habitaciones");

		} else {

			if (habitacion.isHabitacionboolean() == true) {

				habitacionesSeleccionadas.add(habitacion);

			} else {
				Habitacion habitacionDelete = new Habitacion();
				for (Habitacion habitacionEliminar : habitacionesSeleccionadas) {
					if (habitacionEliminar.getHabitacionCodigo().equals(
							habitacion.getHabitacionCodigo())) {
						habitacionDelete = habitacionEliminar;
						System.out.println("correcto"
								+ habitacionDelete.getHabitacionCodigo());

					}
				}
				habitacionesSeleccionadas.remove(habitacionDelete);
				habitacionDelete.setHabitacionboolean(false);
				habitacionService.update(habitacionDelete);
				System.out.println("removerrrrr2" + ""
						+ habitacionDelete.getHabitacionCodigo());

			}
		}
	}

	public void onRowSelect(SelectEvent event) {

		habitacionSeleccionada = new Habitacion();
		habitacionSeleccionada = ((Habitacion) event.getObject());
		System.out.println("slecionado"
				+ habitacionSeleccionada.getHabitacionCodigo());

	}

	public void asignarDias(SelectEvent event) {

		actualReserva.calcularDias();
	}

	public void cargarHabitaciones() {

		System.out.println("11111111");
		actualReserva.setReservaHabitaciones(habitacionesSeleccionadas);
	}

	public void booleanHabitacion() {

		for (Habitacion habitacion : habitacionesSeleccionadas) {
			habitacion.setHabitacionReservas(listaReservas);
			habitacion.setHabitacionboolean(false);
			habitacion.setHabitacionEstado(HabitacionEstado.RESERVADA);
			habitacionService.update(habitacion);
			System.out.println("2222222222");

		}
	}

	public void ajax(AjaxBehaviorEvent e) {
		Habitacion habitacionSelecionada = (Habitacion) e.getComponent()
				.getAttributes().get("habitacion");

		habitacionesSeleccionadas.add(habitacionSelecionada);
	}

	public void cancelarReserva() {

		cancelarHabitaciones();
		habitacionesSeleccionadas.clear();
		actualReserva = new Reserva();

	}

	public void cancelarHabitaciones() {

		for (Habitacion habitacion : habitacionesSeleccionadas) {
			habitacion.setHabitacionboolean(false);
			habitacionService.update(habitacion);
		}

	}

	public void cambiarHabitacionDisponible() {

		for (Habitacion habitacion : habitacionesSeleccionadas) {
			if (habitacion.getHabitacionEstado().equals(
					HabitacionEstado.OCUPADA)
					|| habitacion.getHabitacionEstado().equals(
							HabitacionEstado.RESERVADA)) {

				FacesContext.getCurrentInstance().addMessage(
						"msg",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
								"Habitacion "
										+ habitacion.getHabitacionCodigo()
										+ " "
										+ habitacion.getHabitacionEstado()));
				habitacion.setHabitacionboolean(false);

			} else {
				habitacion.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
				habitacion.setHabitacionboolean(false);
				habitacionService.update(habitacion);

			}
		}

		habitacionesSeleccionadas.clear();

	}

	public void cambiarHabitacionMantenimiento() {

		for (Habitacion habitacion : habitacionesSeleccionadas) {
			if (habitacion.getHabitacionEstado().equals(
					HabitacionEstado.OCUPADA)
					|| habitacion.getHabitacionEstado().equals(
							HabitacionEstado.RESERVADA)) {
				FacesContext.getCurrentInstance().addMessage(
						"msg",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
								"Habitacion "
										+ habitacion.getHabitacionCodigo()
										+ " "
										+ habitacion.getHabitacionEstado()));
				habitacion.setHabitacionboolean(false);

			} else {

				habitacion.setHabitacionEstado(HabitacionEstado.MANTENIMIENTO);
				habitacion.setHabitacionboolean(false);
				habitacionService.update(habitacion);

			}
		}
		habitacionesSeleccionadas.clear();

	}

	public void cargarReserva() throws IOException {

		boolean caso = true;
		for (Habitacion habitacion : habitacionesSeleccionadas) {

			if (habitacion.getHabitacionEstado() != HabitacionEstado.DISPONIBLE) {

				System.out.println("correcto");

				FacesContext.getCurrentInstance().addMessage(
						"msg",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
								"Habitacion "
										+ habitacion.getHabitacionCodigo()
										+ " "+habitacion.getHabitacionEstado()));
				caso = false;

			} else {
				habitacion.setHabitacionboolean(true);
				habitacionService.update(habitacion);
			}

		}

		if (caso) {
			if (habitacionesSeleccionadas.isEmpty()) {

				FacesContext.getCurrentInstance().addMessage(
						"msg",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
								"Seleccione habitaciones"));

			} else {

				actualReserva.setReservaFecha(Calendar.getInstance().getTime());
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../reserva/reserva.xhtml");
				System.out.println("pasar");

			}
		}

	}

	public void removerHabitacion() {

		habitacionesSeleccionadas.remove(habitacionSeleccionada);
		habitacionSeleccionada.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
		habitacionSeleccionada.setHabitacionboolean(false);
		habitacionService.update(habitacionSeleccionada);
		habitacionSeleccionada = new Habitacion();
	}

	public void cargarReservas() {
		listaReservas = reservaService.findAll();

	}

	public void pasarReservaciones() {

		try {
			cargarReservas();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../reserva/reservas.xhtml");
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actualizarHabitacionDisponible() {

		habitacionesReserva = actualReserva.getReservaHabitaciones();
		for (Habitacion habitacion : habitacionesReserva) {
			{
				System.out.println("HBAITACION"
						+ habitacion.getHabitacionCodigo());
				habitacion.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
				habitacionService.update(habitacion);
			}

		}
	}

	public void enviarHabitacionesSelecionadas() {

		boolean limpieza = true;
		if (habitacionesSeleccionadas.isEmpty()) {

			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							"Seleccione habitaciones"));

		} else {
			for (Habitacion habitacion : habitacionesSeleccionadas) {
				if (habitacion.isLimpiezaBoolean() == true) {
					FacesContext.getCurrentInstance().addMessage(
							"msg",
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Info", "Habitacion "
											+ habitacion.getHabitacionCodigo()
											+ " en limpieza"));
					limpieza = false;

				}

			}

			if (limpieza) {
				for (Habitacion habitacion : habitacionesSeleccionadas) {

					habitacion.setHabitacionboolean(true);
					habitacionService.update(habitacion);

				}
				habitacionListener.fire(habitacionesSeleccionadas);

			}

		}
	}

	public void deleteHabitacion(Habitacion habitacion) {

		habitacionesSeleccionadas.remove(habitacion);

	}

	public void inicializarReserva() {

		init();
		System.out.println("RESERVAAAAAAAAAAA");

	}

	public void buscarCliente() {

		if (buscarCliente == null) {

			listaReservas = reservaService.findAll();

		} else {

			List<Reserva> filtList = listaReservas
					.stream()
					.filter(value -> value.getClienteReserva()
							.getPersonaApellidos()
							.matches("(?i).*" + buscarCliente + ".*")
							|| value.getClienteReserva().getPersonaNombres()
									.matches("(?i).*" + buscarCliente + ".*")
							|| value.getReservaHabitaciones().listIterator()
									.next().getHabitacionCodigo()
									.matches("(?i).*" + buscarCliente + ".*")
							|| value.getReservaHabitaciones().listIterator()
									.next().getTipoHabitacion()
									.getTipoHabitacionCategoria()
									.matches("(?i).*" + buscarCliente + ".*")
							|| value.getReservaEstado().toString()
									.matches("(?i).*" + buscarCliente + ".*"))
					.collect(Collectors.toList());
			setListaReservas(filtList);

			/*
			 * String words[] = buscarCliente.split("\\s"); String
			 * capitalizeWord = ""; for (String w : words) { String first =
			 * w.substring(0, 1); String afterfirst = w.substring(1);
			 * capitalizeWord += first.toUpperCase() + afterfirst + " "; }
			 * buscarCliente=capitalizeWord;
			 */
			// listaRecibos = reciboCabeceraService.findAll2(buscarCliente);

		}

	}

}
