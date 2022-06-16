package org.hotelLeon.presentacion.servicios;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.factura.ReciboCabeceraService;
import org.hotelLeon.aplicacion.hospedaje.HabitacionService;
import org.hotelLeon.aplicacion.persona.EmpleadoService;
import org.hotelLeon.aplicacion.reserva.ReservaService;
import org.hotelLeon.aplicacion.servicios.GobernantaService;
import org.hotelLeon.aplicacion.servicios.ServicioService;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.ReciboDetalle;
import org.hotelLeon.dominio.hospedaje.Alojamiento;
import org.hotelLeon.dominio.hospedaje.EstadoLimpieza;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.dominio.persona.Empleado;
import org.hotelLeon.dominio.reserva.Reserva;
import org.hotelLeon.dominio.servicios.Gobernanta;
import org.hotelLeon.dominio.servicios.Requerimiento;
import org.hotelLeon.dominio.servicios.Servicio;
import org.hotelLeon.presentacion.reserva.ReservaControlador;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.github.underscore.U;

@Named
@SessionScoped
public class ServicioControlador implements Serializable {

	private Servicio actualServicio;
	private List<Servicio> listaServicios;
	private List<Servicio> filteredServicios;
	private Servicio editarServicio;
	private Gobernanta gobernanta;
	private Empleado gobernantaEmpleado;
	private Habitacion habitacionSelecionada;
	private List<Gobernanta> listaGobernanta;
	private String buscarEmpleado;
	private List<Habitacion> listaHabitaciones;
	private int casoGobernanta;
	private EstadoLimpieza[] estadoLimpiza;
	private List<ReciboCabecera> listaRecibos;
	private List<Reserva> listaReservas;
	private List<Habitacion> filteredHabitaiones;

	@Inject
	private ServicioService servicioService;

	@Inject
	private GobernantaService gobernantaService;

	@Inject
	private HabitacionService habitacionService;

	@Inject
	private ReciboCabeceraService reciboService;
	@Inject
	private ReservaService reservaService;

	@PostConstruct
	private void init() {

		listaHabitaciones = new ArrayList<Habitacion>();
		gobernanta = new Gobernanta();
		actualServicio = new Servicio();
		listaServicios = new ArrayList<Servicio>();
		listaGobernanta = new ArrayList<Gobernanta>();
		habitacionSelecionada = new Habitacion();
		listaRecibos = new ArrayList<ReciboCabecera>();
		listaReservas = new ArrayList<Reserva>();
		filteredHabitaiones = new ArrayList<Habitacion>();
		cargarServicios();
		cargarGobernanta();
	}

	public Servicio getActualServicio() {
		return actualServicio;
	}

	public void setActualServicio(Servicio actualServicio) {
		this.actualServicio = actualServicio;
	}

	public List<Servicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public List<Servicio> getFilteredServicios() {
		return filteredServicios;
	}

	public void setFilteredServicios(List<Servicio> filteredServicios) {
		this.filteredServicios = filteredServicios;
	}

	public Servicio getEditarServicio() {
		return editarServicio;
	}

	public void setEditarServicio(Servicio editarServicio) {
		this.editarServicio = editarServicio;
	}

	public Gobernanta getGobernanta() {
		return gobernanta;
	}

	public void setGobernanta(Gobernanta gobernanta) {
		this.gobernanta = gobernanta;
	}

	public Habitacion getHabitacionSelecionada() {
		return habitacionSelecionada;
	}

	public void setHabitacionSelecionada(Habitacion habitacionSelecionada) {
		this.habitacionSelecionada = habitacionSelecionada;
	}

	public Empleado getGobernantaEmpleado() {
		return gobernantaEmpleado;
	}

	public void setGobernantaEmpleado(Empleado gobernantaEmpleado) {
		this.gobernantaEmpleado = gobernantaEmpleado;
	}

	public String getBuscarEmpleado() {
		return buscarEmpleado;
	}

	public void setBuscarEmpleado(String buscarEmpleado) {
		this.buscarEmpleado = buscarEmpleado;
	}

	public List<Gobernanta> getListaGobernanta() {
		return listaGobernanta;
	}

	public void setListaGobernanta(List<Gobernanta> listaGobernanta) {
		this.listaGobernanta = listaGobernanta;
	}

	public List<Habitacion> getListaHabitaciones() {
		return listaHabitaciones;
	}

	public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
		this.listaHabitaciones = listaHabitaciones;
	}

	public EstadoLimpieza[] getEstadoLimpiza() {
		return estadoLimpiza = EstadoLimpieza.values();
	}

	public void setEstadoLimpiza(EstadoLimpieza[] estadoLimpiza) {
		this.estadoLimpiza = estadoLimpiza;
	}

	public List<Habitacion> getFilteredHabitaiones() {
		return filteredHabitaiones;
	}

	public void setFilteredHabitaiones(List<Habitacion> filteredHabitaiones) {
		this.filteredHabitaiones = filteredHabitaiones;
	}

	public void addServicio() {

		try {
			servicioService.create(actualServicio);
			listaServicios.add(actualServicio);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							" Servicio creado"));
			actualServicio = new Servicio();
			RequestContext.getCurrentInstance()
					.execute("PF('servicio').hide()");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void eliminarServicio(Servicio servicio) {

		try {
			servicioService.delete(servicio);
			listaServicios.remove(servicio);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							" Servicio eliminado"));
			actualServicio = new Servicio();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							" Servicio contiene historial"));
			actualServicio = new Servicio();
		}

	}

	public void editarServicio() {

		try {
			servicioService.update(editarServicio);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							" Servicio editado"));
			editarServicio = new Servicio();
			actualServicio = new Servicio();
			RequestContext.getCurrentInstance().execute(
					"PF('editarServicio').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void cargarServicios() {

		listaServicios = servicioService.findAll();

	}

	public void pasarServicios()

	{
		try {

			cargarServicios();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../servicios/mantenimientoServicios.xhtml");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public Servicio getServicio(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("no id provided");
		}
		for (Servicio servicio : listaServicios) {
			if (id.equals(servicio.getServicioId())) {

				return servicio;
			}
		}
		return null;
	}

	// GOBERNANTA
	public void pasarRegistroGobernanta() {
		try {
			init();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../gobernanta/gobernantas.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cargarGobernanta() {
		listaGobernanta = gobernantaService.findAll();
	}

	public void onRowSelect(SelectEvent event) {
		habitacionSelecionada = ((Habitacion) event.getObject());
		System.out.println("SLECLCION"
				+ habitacionSelecionada.getHabitacionCodigo());

	}

	/*
	 * public void actualizarHabitacionS() {
	 * 
	 * System.out.println("GOBERNANTA" +
	 * habitacionSelecionada.getHabitacionCodigo()); casoGobernanta = 1;
	 * ListIterator<Habitacion> iter = gobernanta.getListaHabitaciones()
	 * .listIterator(); while (iter.hasNext()) { Habitacion habitacion =
	 * (Habitacion) iter.next(); System.out.println("HABITACION" +
	 * habitacion.getHabitacionCodigo()); if (habitacion.getHabitacionCodigo()
	 * == habitacionSelecionada .getHabitacionCodigo()) {
	 * habitacion.setHabitacionboolean(false);
	 * habitacion.setLimpiezaBoolean(false);
	 * habitacionService.update(habitacion); iter.remove(); } }
	 * 
	 * }
	 */

	public void removerHabitacion() {

		if (habitacionSelecionada.getEstadoLimpieza().equals(
				EstadoLimpieza.TERMINADO)) {
			habitacionSelecionada.setHabitacionboolean(false);
			habitacionSelecionada.setLimpiezaBoolean(false);
			habitacionSelecionada
					.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
			habitacionService.update(habitacionSelecionada);
			gobernanta.getListaHabitaciones().remove(habitacionSelecionada);

		} else {

			habitacionSelecionada.setEstadoLimpieza(EstadoLimpieza.TERMINADO);
			habitacionSelecionada.setHabitacionboolean(false);
			habitacionSelecionada.setLimpiezaBoolean(false);
			habitacionSelecionada
					.setHabitacionEstado(HabitacionEstado.LIMPIEZA);
			habitacionService.update(habitacionSelecionada);
			gobernanta.getListaHabitaciones().remove(habitacionSelecionada);

		}
	}

	public void cancelarGobernanta() {

		for (Habitacion habitacion : gobernanta.getListaHabitaciones()) {
			habitacion.setHabitacionboolean(false);
			habitacion.setLimpiezaBoolean(false);
			habitacionService.update(habitacion);
		}
		gobernanta.getListaHabitaciones().clear();
		gobernanta = new Gobernanta();

	}

	public void addGobernanta() {

		if (gobernanta.getGobernantaEmpleado() == null
				|| gobernanta.getListaHabitaciones().isEmpty()) {

			FacesContext.getCurrentInstance().addMessage(
					"msg",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							"Campos requeridos"));
		} else {

			confirmarLimipeza();
			listaGobernanta.add(gobernanta);
			gobernantaService.create(gobernanta);
			gobernanta = new Gobernanta();
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../hospedaje/planning.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void editarGobernanta() {

		editarEstadoLipiezaHabitacion();
		gobernantaService.update(gobernanta);
		gobernanta = new Gobernanta();
	}

	public void eliminarGobernanta() {

		boolean eliminar = true;

		ListIterator<Habitacion> iter = gobernanta.getListaHabitaciones()
				.listIterator();
		while (iter.hasNext()) {
			Habitacion habitacion = (Habitacion) iter.next();
			if (habitacion.getEstadoLimpieza() != EstadoLimpieza.TERMINADO) {
				FacesContext.getCurrentInstance().addMessage(
						"messages",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
								"Habitacion "
										+ habitacion.getHabitacionCodigo()
										+ " en limpieza"));
				eliminar = false;
			}
		}
		if (eliminar) {
			for (Habitacion habitacion : gobernanta.getListaHabitaciones()) {
				habitacion.setLimpiezaBoolean(false);
				habitacionService.update(habitacion);

			}
			gobernantaService.delete(gobernanta);
			listaGobernanta.remove(gobernanta);
			gobernanta = new Gobernanta();

			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Limpieza eliminada"));
		}
	}

	public void cancelarEdicionG() {

		cargarGobernanta();
		gobernanta = new Gobernanta();
	}

	public void pasarGobernanta() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../gobernanta/gobernanta.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void pasarRegistrGobernanta() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../gobernanta/gobernantas.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void recivirHabitaciones(
			@Observes(notifyObserver = Reception.ALWAYS) List<Habitacion> habitacionesSeleccionadas) {
		System.out.println("casoActualizat" + casoGobernanta);
		gobernanta.setGobernantaFecha(Calendar.getInstance().getTime());
		if (casoGobernanta == 1) {
			gobernanta.getListaHabitaciones().addAll(habitacionesSeleccionadas);
			confirmarLimipeza();
			gobernantaService.update(gobernanta);
			pasarRegistrGobernanta();
			gobernanta = new Gobernanta();
			casoGobernanta = 0;
		} else {

			this.listaHabitaciones = habitacionesSeleccionadas;
			gobernanta.setListaHabitaciones(listaHabitaciones);
			pasarGobernanta();
		}
	}

	public void recivirEmpleado(
			@Observes(notifyObserver = Reception.IF_EXISTS) Empleado empleado) {

		this.gobernantaEmpleado = empleado;
		gobernanta.setGobernantaEmpleado(gobernantaEmpleado);
		RequestContext.getCurrentInstance().update("form:txtempleado");

	}

	public void recivirGobernanta() {

		casoGobernanta = 1;

	}

	public void confirmarLimipeza() {
		for (Habitacion habitacion : gobernanta.getListaHabitaciones()) {
			if (habitacion.isLimpiezaBoolean() == false) {
				habitacion.setLimpiezaBoolean(true);
				habitacion.setHabitacionboolean(true);
				habitacion.setEstadoLimpieza(EstadoLimpieza.LIMPEZA);
				habitacion.setHabitacionEstado(HabitacionEstado.LIMPIEZA);
				habitacionService.update(habitacion);
			}
		}

	}

	public void eliminarHabitacionSlecionada() {

		listaReservas = reservaService.findAll();
		listaRecibos = reciboService.findAll();
		List<Habitacion> listaHabitaciones = listaRecibos
				.stream()
				.filter(value -> value.isReciboCabeceraCheckOut() == false)
				.flatMap(recibo -> recibo.getListaDetalles().stream())
				.map(detalle -> detalle.getDetalleAlojamiento()
						.getAlojamientoHabitacion())
				.collect(Collectors.toList());

		List<Habitacion> listaHabitacionesReserva = listaReservas.stream()
				.flatMap(reserva -> reserva.getReservaHabitaciones().stream())
				.collect(Collectors.toList());
		if (casoGobernanta == 1 || casoGobernanta == 0) {

			if (listaHabitaciones.stream().anyMatch(
					habitacion -> habitacion.getHabitacionCodigo().equals(
							habitacionSelecionada.getHabitacionCodigo()))) {

				if (habitacionSelecionada.getEstadoLimpieza().equals(
						EstadoLimpieza.TERMINADO)) {
					habitacionSelecionada.setHabitacionboolean(false);
					habitacionSelecionada
							.setHabitacionEstado(HabitacionEstado.OCUPADA);
					habitacionService.update(habitacionSelecionada);
					gobernanta.getListaHabitaciones().remove(
							habitacionSelecionada);
					gobernantaService.update(gobernanta);

					FacesContext.getCurrentInstance().addMessage(
							"messages",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Info", "Habitacion "
											+ habitacionSelecionada
													.getHabitacionCodigo()
											+ " eliminada"));
				} else {
					habitacionSelecionada
							.setEstadoLimpieza(EstadoLimpieza.TERMINADO);
					habitacionSelecionada.setHabitacionboolean(false);
					habitacionSelecionada
							.setHabitacionEstado(HabitacionEstado.OCUPADA);
					habitacionSelecionada.setLimpiezaBoolean(false);
					habitacionService.update(habitacionSelecionada);
					gobernanta.getListaHabitaciones().remove(
							habitacionSelecionada);
					gobernantaService.update(gobernanta);

					FacesContext.getCurrentInstance().addMessage(
							"messages",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"Info", "Habitacion "
											+ habitacionSelecionada
													.getHabitacionCodigo()
											+ " eliminada"));
				}

			} else {
				if (listaHabitacionesReserva.stream().anyMatch(
						habitacion -> habitacion.getHabitacionCodigo().equals(
								habitacionSelecionada.getHabitacionCodigo()))) {

					if (habitacionSelecionada.getEstadoLimpieza().equals(
							EstadoLimpieza.TERMINADO)) {

						habitacionSelecionada.setHabitacionboolean(false);
						habitacionSelecionada
								.setHabitacionEstado(HabitacionEstado.RESERVADA);

						habitacionService.update(habitacionSelecionada);
						gobernanta.getListaHabitaciones().remove(
								habitacionSelecionada);
						gobernantaService.update(gobernanta);

						FacesContext.getCurrentInstance().addMessage(
								"messages",
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Info", "Habitacion "
												+ habitacionSelecionada
														.getHabitacionCodigo()
												+ " eliminada"));

					} else {
						habitacionSelecionada
								.setEstadoLimpieza(EstadoLimpieza.TERMINADO);
						habitacionSelecionada.setHabitacionboolean(false);
						habitacionSelecionada
								.setHabitacionEstado(HabitacionEstado.RESERVADA);
						habitacionSelecionada.setLimpiezaBoolean(false);

						habitacionService.update(habitacionSelecionada);
						gobernanta.getListaHabitaciones().remove(
								habitacionSelecionada);
						gobernantaService.update(gobernanta);

						FacesContext.getCurrentInstance().addMessage(
								"messages",
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Info", "Habitacion "
												+ habitacionSelecionada
														.getHabitacionCodigo()
												+ " eliminada"));

					}

				} else {

					if (habitacionSelecionada.getEstadoLimpieza().equals(
							EstadoLimpieza.TERMINADO)) {

						habitacionSelecionada.setHabitacionboolean(false);
						habitacionSelecionada
								.setHabitacionEstado(HabitacionEstado.DISPONIBLE);

						habitacionService.update(habitacionSelecionada);
						gobernanta.getListaHabitaciones().remove(
								habitacionSelecionada);
						gobernantaService.update(gobernanta);

						FacesContext.getCurrentInstance().addMessage(
								"messages",
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Info", "Habitacion "
												+ habitacionSelecionada
														.getHabitacionCodigo()
												+ " eliminada"));
					} else {
						habitacionSelecionada
								.setEstadoLimpieza(EstadoLimpieza.TERMINADO);
						habitacionSelecionada.setHabitacionboolean(false);
						habitacionSelecionada
								.setHabitacionEstado(HabitacionEstado.LIMPIEZA);
						habitacionSelecionada.setLimpiezaBoolean(false);

						habitacionService.update(habitacionSelecionada);
						gobernanta.getListaHabitaciones().remove(
								habitacionSelecionada);
						gobernantaService.update(gobernanta);

						FacesContext.getCurrentInstance().addMessage(
								"messages",
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"Info", "Habitacion "
												+ habitacionSelecionada
														.getHabitacionCodigo()
												+ " eliminada"));
					}

				}

			}
		}

	}

	public void editarEstadoLipiezaHabitacion() {

		listaReservas = reservaService.findAll();
		listaRecibos = reciboService.findAll();
		List<Habitacion> listaHabitaciones = listaRecibos
				.stream()
				.filter(value -> value.isReciboCabeceraCheckOut() == false)
				.flatMap(recibo -> recibo.getListaDetalles().stream())
				.map(detalle -> detalle.getDetalleAlojamiento()
						.getAlojamientoHabitacion())
				.collect(Collectors.toList());

		List<Habitacion> listaHabitacionesReserva = listaReservas.stream()
				.flatMap(reserva -> reserva.getReservaHabitaciones().stream())
				.collect(Collectors.toList());

		List<Habitacion> gobernantaHabitaciones = new ArrayList<Habitacion>();
		gobernantaHabitaciones.addAll(gobernanta.getListaHabitaciones());
		ListIterator<Habitacion> iter = gobernantaHabitaciones.listIterator();

		while (iter.hasNext()) {
			Habitacion habitacion = (Habitacion) iter.next();
			for (Habitacion habitacionRecibo : listaHabitaciones) {

				if (habitacion.getHabitacionCodigo().equals(
						habitacionRecibo.getHabitacionCodigo())) {

					if (habitacion.getEstadoLimpieza().equals(
							EstadoLimpieza.TERMINADO)) {
						habitacion.setHabitacionboolean(false);
						habitacion
								.setHabitacionEstado(HabitacionEstado.OCUPADA);
						habitacionService.update(habitacion);
					} else {
						habitacion.setHabitacionboolean(true);
						habitacion.setLimpiezaBoolean(true);
						habitacion
								.setHabitacionEstado(HabitacionEstado.LIMPIEZA);
						habitacionService.update(habitacion);
					}
					iter.remove();

				}

			}
			for (Habitacion habitacionReserva : listaHabitacionesReserva) {
				if (habitacion.getHabitacionCodigo().equals(
						habitacionReserva.getHabitacionCodigo())) {
					if (habitacion.getEstadoLimpieza().equals(
							EstadoLimpieza.TERMINADO)) {

						habitacion.setHabitacionboolean(false);
						habitacion
								.setHabitacionEstado(HabitacionEstado.RESERVADA);

						habitacionService.update(habitacion);

					} else {
						habitacion.setHabitacionboolean(true);
						habitacion
								.setHabitacionEstado(HabitacionEstado.LIMPIEZA);
						habitacion.setLimpiezaBoolean(true);

						habitacionService.update(habitacion);

					}
					iter.remove();

				}

			}

		}

		for (Habitacion habitacion : gobernantaHabitaciones) {
			if (habitacion.getEstadoLimpieza().equals(EstadoLimpieza.TERMINADO)) {

				habitacion.setHabitacionboolean(false);
				habitacion.setHabitacionEstado(HabitacionEstado.DISPONIBLE);

				habitacionService.update(habitacion);

			} else {
				habitacion.setHabitacionboolean(true);
				habitacion.setHabitacionEstado(HabitacionEstado.LIMPIEZA);
				habitacion.setLimpiezaBoolean(true);

				habitacionService.update(habitacion);

			}

		}
	}

	public void buscarEmpleado() {

		if (buscarEmpleado == null) {

			cargarGobernanta();

		} else {

			List<Gobernanta> filtList = listaGobernanta
					.stream()
					.filter(value -> value.getGobernantaEmpleado()
							.getPersonaApellidos()
							.matches("(?i).*" + buscarEmpleado + ".*")
							|| value.getGobernantaEmpleado()
									.getPersonaNombres()
									.matches("(?i).*" + buscarEmpleado + ".*")
							|| value.getListaHabitaciones().listIterator()
									.next().getHabitacionCodigo()
									.matches("(?i).*" + buscarEmpleado + ".*")
							|| value.getListaHabitaciones().listIterator()
									.next().getTipoHabitacion()
									.getTipoHabitacionCategoria()
									.matches("(?i).*" + buscarEmpleado + ".*"))
					.collect(Collectors.toList());
			setListaGobernanta(filtList);

			/*
			 * String words[] = buscarCliente.split("\\s"); String
			 * capitalizeWord = ""; for (String w : words) { String first =
			 * w.substring(0, 1); String afterfirst = w.substring(1);
			 * capitalizeWord += first.toUpperCase() + afterfirst + " "; }
			 * buscarCliente=capitalizeWord;
			 */
			// listaRecibos = reciboCabeceraService.findAll2(buscarCliente);
			System.out.println("clientee" + buscarEmpleado);

		}

	}
}
