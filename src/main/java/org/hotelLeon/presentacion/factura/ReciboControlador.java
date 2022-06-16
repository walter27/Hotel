package org.hotelLeon.presentacion.factura;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.hotelLeon.aplicacion.factura.ReciboCabeceraService;
import org.hotelLeon.aplicacion.hospedaje.HabitacionService;
import org.hotelLeon.aplicacion.persona.ClienteService;
import org.hotelLeon.aplicacion.persona.UsuarioService;
import org.hotelLeon.aplicacion.reserva.ReservaService;
import org.hotelLeon.aplicacion.servicios.ConsumoService;
import org.hotelLeon.aplicacion.servicios.ProductoService;
import org.hotelLeon.aplicacion.servicios.ServicioService;
import org.hotelLeon.dominio.factura.Abono;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.ReciboDetalle;
import org.hotelLeon.dominio.factura.TipoPago;
import org.hotelLeon.dominio.hospedaje.Alojamiento;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.dominio.persona.UsuarioSistema;
import org.hotelLeon.dominio.reserva.Reserva;
import org.hotelLeon.dominio.reserva.ReservaEstado;
import org.hotelLeon.dominio.servicios.Consumo;
import org.hotelLeon.dominio.servicios.Requerimiento;
import org.hotelLeon.dominio.servicios.Producto;
import org.hotelLeon.dominio.servicios.Servicio;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

@Named
@ApplicationScoped
public class ReciboControlador implements Serializable {

	private ReciboCabecera actualReciboCabecera;
	private ReciboCabecera editarReciboCabecera;
	private List<ReciboCabecera> listaRecibos;
	private TipoPago[] listaTipoPago;
	private Hospedaje reciboHospedaje;
	private ReciboDetalle reciboDetalle;
	private Cliente clienteRecibo;
	private List<ReciboDetalle> listaDetalles;
	private Habitacion reciboHabitacion;
	private Alojamiento alojamiento;
	private List<Alojamiento> listaAlojamientos;
	private Abono monto;
	private List<Abono> listaMontos;
	private Abono montoSeleccionado;
	private int clienteCaso;
	private ReciboDetalle detalleSeleccionado;
	private Habitacion habitacionSleccionada;
	private Producto productoServicio;
	private List<Producto> listaProductos;
	private Requerimiento requerimiento;
	private List<Requerimiento> listaRequerimiento;
	private Consumo consumo;
	private Servicio servicioSeleccionado;
	private Requerimiento requerimientoSeleccionado;
	private List<Servicio> listaServicios;
	private List<ReciboCabecera> filteredRecibos;
	private String buscarCliente;
	private Habitacion cambioHabitacion;
	private Habitacion habitacionAnterior;
	private Cliente huespedSeleccionado;
	private Reserva reserva;
	private ReservaEstado[] estadoReserva;
	private int casoReserva;
	private UsuarioSistema usuario;
	private List<UsuarioSistema> listaUsuarios;
	private List<Habitacion> listaHabitaciones;
	private String buscarTipoHabitacion = "";

	@Inject
	private HabitacionService habitacionService;

	@Inject
	private ReciboCabeceraService reciboCabeceraService;

	@Inject
	private ProductoService productotoService;

	@Inject
	private ClienteService clienteService;

	@Inject
	private ReservaService reservaService;

	@Inject
	private ConsumoService consumoService;

	@Inject
	private UsuarioService usuarioService;

	@PostConstruct
	private void init() {

		actualReciboCabecera = new ReciboCabecera();
		reciboHospedaje = new Hospedaje();
		clienteRecibo = new Cliente();
		listaRecibos = new ArrayList<ReciboCabecera>();
		listaDetalles = new ArrayList<ReciboDetalle>();
		listaAlojamientos = new ArrayList<Alojamiento>();
		reciboDetalle = new ReciboDetalle();
		alojamiento = new Alojamiento();
		monto = new Abono();
		montoSeleccionado = new Abono();
		listaMontos = new ArrayList<Abono>();
		detalleSeleccionado = new ReciboDetalle();
		habitacionSleccionada = new Habitacion();
		listaRequerimiento = new ArrayList<Requerimiento>();
		productoServicio = new Producto();
		listaProductos = new ArrayList<Producto>();
		requerimiento = new Requerimiento();
		servicioSeleccionado = new Servicio();
		requerimientoSeleccionado = new Requerimiento();
		consumo = new Consumo();
		listaServicios = new ArrayList<Servicio>();
		cambioHabitacion = new Habitacion();
		huespedSeleccionado = new Cliente();
		reserva = new Reserva();
		System.out.println("ininiiiiii");
		clienteCaso = 0;
		casoReserva = 0;
		cargar();
		usuario = new UsuarioSistema();
		listaUsuarios = usuarioService.findAll();
		consumo.setConsumoFecha(Calendar.getInstance().getTime());
		monto.setAbonoFecha(Calendar.getInstance().getTime());
		listaHabitaciones = new ArrayList<Habitacion>();
		listaHabitaciones = habitacionService.findAll();

	}

	public ReciboCabecera getReciboCabecera() {
		return actualReciboCabecera;
	}

	public void setReciboCabecera(ReciboCabecera reciboCabecera) {
		this.actualReciboCabecera = reciboCabecera;
	}

	public ReciboCabecera getEditarReciboCabecera() {
		return editarReciboCabecera;
	}

	public void setEditarReciboCabecera(ReciboCabecera editarReciboCabecera) {
		this.editarReciboCabecera = editarReciboCabecera;
	}

	public List<ReciboCabecera> getListaRecibos() {
		return listaRecibos;
	}

	public void setListaRecibos(List<ReciboCabecera> listaRecibos) {
		this.listaRecibos = listaRecibos;
	}

	public TipoPago[] getListaTipoPago() {
		return listaTipoPago = TipoPago.values();
	}

	public void setListaTipoPago(TipoPago[] listaTipoPago) {
		this.listaTipoPago = listaTipoPago;
	}

	public Hospedaje getReciboHospedaje() {
		return reciboHospedaje;
	}

	public void setReciboHospedaje(Hospedaje reciboHospedaje) {
		this.reciboHospedaje = reciboHospedaje;
	}

	public ReciboDetalle getReciboDetalle() {
		return reciboDetalle;
	}

	public void setReciboDetalle(ReciboDetalle reciboDetalle) {
		this.reciboDetalle = reciboDetalle;
	}

	public ReciboCabecera getActualReciboCabecera() {
		return actualReciboCabecera;
	}

	public void setActualReciboCabecera(ReciboCabecera actualReciboCabecera) {
		this.actualReciboCabecera = actualReciboCabecera;
	}

	public Cliente getClienteRecibo() {
		return clienteRecibo;
	}

	public void setClienteRecibo(Cliente clienteRecibo) {
		this.clienteRecibo = clienteRecibo;
	}

	public List<ReciboDetalle> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<ReciboDetalle> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public Habitacion getReciboHabitacion() {
		return reciboHabitacion;
	}

	public void setReciboHabitacion(Habitacion reciboHabitacion) {
		this.reciboHabitacion = reciboHabitacion;
	}

	public Alojamiento getAlojamiento() {
		return alojamiento;
	}

	public void setAlojamiento(Alojamiento alojamiento) {
		this.alojamiento = alojamiento;
	}

	public Abono getMonto() {
		return monto;
	}

	public void setMonto(Abono monto) {
		this.monto = monto;
	}

	public ReciboDetalle getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(ReciboDetalle detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}

	public List<Alojamiento> getListaAlojamientos() {
		return listaAlojamientos;
	}

	public void setListaAlojamientos(List<Alojamiento> listaAlojamientos) {
		this.listaAlojamientos = listaAlojamientos;
	}

	public List<Abono> getListaMontos() {
		return listaMontos;
	}

	public void setListaMontos(List<Abono> listaMontos) {
		this.listaMontos = listaMontos;
	}

	public Habitacion getHabitacionSleccionada() {
		return habitacionSleccionada;
	}

	public void setHabitacionSleccionada(Habitacion habitacionSleccionada) {
		this.habitacionSleccionada = habitacionSleccionada;
	}

	public Producto getProductoServicio() {
		return productoServicio;
	}

	public void setProductoServicio(Producto productoServicio) {
		this.productoServicio = productoServicio;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public Servicio getServicioSeleccionado() {
		return servicioSeleccionado;
	}

	public void setServicioSeleccionado(Servicio servicioSeleccionado) {
		this.servicioSeleccionado = servicioSeleccionado;
	}

	public List<Servicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public Requerimiento getRequerimiento() {
		return requerimiento;
	}

	public void setRequerimiento(Requerimiento requerimiento) {
		this.requerimiento = requerimiento;
	}

	public List<Requerimiento> getListaRequerimiento() {
		return listaRequerimiento;
	}

	public void setListaRequerimiento(List<Requerimiento> listaRequerimiento) {
		this.listaRequerimiento = listaRequerimiento;
	}

	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	public Requerimiento getRequerimientoSeleccionado() {
		return requerimientoSeleccionado;
	}

	public void setRequerimientoSeleccionado(
			Requerimiento requerimientoSeleccionado) {
		this.requerimientoSeleccionado = requerimientoSeleccionado;
	}

	public String getBuscarCliente() {
		return buscarCliente;
	}

	public void setBuscarCliente(String buscarCliente) {
		this.buscarCliente = buscarCliente;
	}

	public Habitacion getCambioHabitacion() {
		return cambioHabitacion;
	}

	public void setCambioHabitacion(Habitacion cambioHabitacion) {
		this.cambioHabitacion = cambioHabitacion;
	}

	public Habitacion getHabitacionAnterior() {
		return habitacionAnterior;
	}

	public void setHabitacionAnterior(Habitacion habitacionAnterior) {
		this.habitacionAnterior = habitacionAnterior;
	}

	public List<ReciboCabecera> getFilteredRecibos() {
		return filteredRecibos;
	}

	public void setFilteredRecibos(List<ReciboCabecera> filteredRecibos) {
		this.filteredRecibos = filteredRecibos;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public ReservaEstado[] getEstadoReserva() {
		return estadoReserva = ReservaEstado.values();
	}

	public void setEstadoReserva(ReservaEstado[] estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

	public Abono getMontoSeleccionado() {
		return montoSeleccionado;
	}

	public void setMontoSeleccionado(Abono montoSeleccionado) {
		this.montoSeleccionado = montoSeleccionado;
	}

	public UsuarioSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioSistema> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UsuarioSistema> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Habitacion> getListaHabitaciones() {
		return listaHabitaciones;
	}

	public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
		this.listaHabitaciones = listaHabitaciones;
	}

	public String getBuscarTipoHabitacion() {
		return buscarTipoHabitacion;
	}

	public void setBuscarTipoHabitacion(String buscarTipoHabitacion) {
		this.buscarTipoHabitacion = buscarTipoHabitacion;
	}

	public void addRecibo() {

		System.out.println("RESEVRA" + casoReserva);

		if (casoReserva == 5) {

			reserva.setCheckInReserva(true);
			reservaService.update(reserva);
			actualizarHabitacion();
			reciboHospedaje.setListaAlojamientos(listaAlojamientos);
			eliminarMonto();
			actualReciboCabecera.calcularMontoTotal();
			reciboCabeceraService.create(actualReciboCabecera);
			listaRecibos.add(actualReciboCabecera);
			listaDetalles.clear();
			init();
			getUser();
			try {
				listaHabitaciones = habitacionService.findAll();
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../hospedaje/planning.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			if (reciboHospedaje.getCliente() == null || listaDetalles.isEmpty()) {

				FacesContext.getCurrentInstance().addMessage(
						"messages",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
								"Campos requeridos"));

			} else {

				actualizarHabitacion();
				eliminarMonto();
				actualReciboCabecera.calcularMontoTotal();
				reciboCabeceraService.create(actualReciboCabecera);
				listaRecibos.add(actualReciboCabecera);
				listaDetalles.clear();
				init();
				getUser();
				try {

					listaHabitaciones = habitacionService.findAll();
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("../hospedaje/planning.xhtml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public void eliminarRecibo() {

		actualizarHabitacionDisponible();
		reciboCabeceraService.delete(actualReciboCabecera);
		listaRecibos.remove(actualReciboCabecera);
		System.out.println("ELIMINAR"
				+ actualReciboCabecera.getClienteRecibo().getPersonaNombres());
		actualReciboCabecera = new ReciboCabecera();
		FacesContext.getCurrentInstance().addMessage(
				"messages",
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
						"Hospedaje eliminado"));

	}

	public void editarRecibo() {

		if (actualReciboCabecera.getReciboCabeceraCobrar().compareTo(
				BigDecimal.ZERO) > 0) {
			actualReciboCabecera.setReciboReporte(false);
			eliminarMonto();
			reciboCabeceraService.update(actualReciboCabecera);
			actualReciboCabecera = new ReciboCabecera();
			reciboHospedaje = new Hospedaje();
			clienteCaso = 0;
			listaDetalles.clear();
			cargar();
		} else {
			eliminarMonto();
			reciboCabeceraService.update(actualReciboCabecera);
			actualReciboCabecera = new ReciboCabecera();
			reciboHospedaje = new Hospedaje();
			clienteCaso = 0;
			listaDetalles.clear();
			cargar();
		}
	}

	public void recivirCliente(
			@Observes(notifyObserver = Reception.IF_EXISTS) Cliente cliente) {

		switch (clienteCaso) {
		case 0: {
			alojamiento.setAlojamientoCliente(cliente);
			RequestContext.getCurrentInstance().update(
					"form3:clienteAlojamiento");
			break;

		}

		case 1: {
			this.clienteRecibo = cliente;
			actualReciboCabecera.setClienteRecibo(clienteRecibo);
			reciboHospedaje.setCliente(clienteRecibo);
			RequestContext.getCurrentInstance().update("form:clienteHospedaje");
			break;

		}
		case 2: {

			this.huespedSeleccionado = cliente;
			detalleSeleccionado.getDetalleAlojamiento().setAlojamientoCliente(
					huespedSeleccionado);
			RequestContext.getCurrentInstance().update("form2");
			detalleSeleccionado = new ReciboDetalle();
			break;

		}
		case 3: {

			this.clienteRecibo = cliente;
			consumo.setConsumoCliente(clienteRecibo);
			RequestContext.getCurrentInstance().update("form:clienteConsumo");

		}

		}

	}

	public void recivirHabitacion(
			@Observes(notifyObserver = Reception.IF_EXISTS) Habitacion habitacion)
			throws IOException {

		RequestContext context = RequestContext.getCurrentInstance();
		System.out.println("casssssssssss" + clienteCaso);

		if (clienteCaso == 0) {
			context.execute("PF('alojamientoDialog').show()");
			this.reciboHabitacion = habitacion;
			System.out.println("edificio00000"
					+ habitacion.getHabitacionEdificio());
			alojamiento.setAlojamientoHabitacion(habitacion);
			RequestContext.getCurrentInstance().update("form4");
		} else {
			RequestContext context2 = RequestContext.getCurrentInstance();
			if (clienteCaso == 2) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../hospedaje/checkOutHospedaje.xhtml");
				this.cambioHabitacion = habitacion;
				detalleSeleccionado.getDetalleAlojamiento()
						.setAlojamientoHabitacion(cambioHabitacion);
				detalleSeleccionado.getDetalleAlojamiento()
						.setAlojamientoDescuento(new BigDecimal(0.00));
				detalleSeleccionado.calcularTotalDetalle();
				actualReciboCabecera.calcularAlojamientosTotal();
				actualReciboCabecera.calcularTotal();
				actualReciboCabecera.calcularCobrar();
				actualReciboCabecera.calcularMontoTotal();
				actualizarHabitacionAnterior();
				actualizarCambioHabitacion();
				RequestContext.getCurrentInstance().update("form2");
				detalleSeleccionado = new ReciboDetalle();

			}
		}
	}

	public void addDetalle() throws IOException {

		if (alojamiento.getAlojamientoCliente() == null) {

			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Seleccione huesped"));

		} else {

			reciboHabitacion.setHabitacionEstado(HabitacionEstado.ALOJAMINETO);
			habitacionService.update(reciboHabitacion);
			alojamiento.setAlojamientoHospedaje(reciboHospedaje);
			reciboHospedaje.setListaAlojamientos(listaAlojamientos);
			listaAlojamientos.add(alojamiento);
			reciboDetalle.setDetalleAlojamiento(alojamiento);
			reciboDetalle.calcularTotalDetalle();
			listaDetalles.add(reciboDetalle);
			reciboDetalle.setReciboCabecera(actualReciboCabecera);
			actualReciboCabecera.setListaAbonos(listaMontos);
			actualReciboCabecera.setListaDetalles(listaDetalles);
			actualReciboCabecera.calcularAlojamientosTotal();
			actualReciboCabecera.calcularTotal();
			actualReciboCabecera.calcularMontoTotal();
			actualReciboCabecera.calcularCobrar();
			reciboHospedaje.setHospedajeEntrada(Calendar.getInstance()
					.getTime());
			pasarCheckIn();
			clienteCaso = 1;
			reciboDetalle = new ReciboDetalle();
			alojamiento = new Alojamiento();
			reciboHabitacion = new Habitacion();
			detalleSeleccionado = new ReciboDetalle();
		}
	}

	public void crearNuevoAlojamiento() {

		clienteCaso = 0;
	}

	public void actualizarHabitacion() {

		for (Alojamiento alojamiento : listaAlojamientos) {
			reciboHabitacion = alojamiento.getAlojamientoHabitacion();
			System.out.println("NUMERO"
					+ alojamiento.getAlojamientoHabitacion()
							.getHabitacionCodigo());
			reciboHabitacion.setHabitacionEstado(HabitacionEstado.OCUPADA);
			habitacionService.update(reciboHabitacion);
		}
	}

	public void calcularDias(SelectEvent event) {

		reciboHospedaje.asignarDias();
		for (ReciboDetalle detalle : listaDetalles) {
			detalle.calcularTotalDetalle();
			actualReciboCabecera.calcularAlojamientosTotal();
			actualReciboCabecera.calcularTotal();
			actualReciboCabecera.calcularMontoTotal();
			actualReciboCabecera.calcularCobrar();

		}
	}

	public void cargar() {

		clienteService.findAll();
		listaRecibos = reciboCabeceraService.findAll();
		List<ReciboCabecera> filtList = listaRecibos.stream()
				.filter(value -> value.isReciboCabeceraCheckOut() == false)
				.collect(Collectors.toList());
		setListaRecibos(filtList);

	}

	public void cancelar() {

		if (casoReserva == 5) {
			reservaService.findAll();
			actualizarHabitacionDisponible();
			init();
			getUser();

		} else {

			actualizarHabitacionDisponible();
			init();
			getUser();
		}

	}

	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		BigDecimal valor = (BigDecimal) newValue;
		DataTable table = (DataTable) event.getSource();
		if (table.isRowAvailable()) {
			ReciboDetalle detalleCelda = (ReciboDetalle) table.getRowData();
			if (event.getColumn().getColumnKey().endsWith("descuento")) {
				detalleCelda.getDetalleAlojamiento().setAlojamientoDescuento(
						valor);
				System.out.println("DESCUENTOOO");
				detalleCelda.calcularTotalDetalle();
				actualReciboCabecera.calcularAlojamientosTotal();
				actualReciboCabecera.calcularTotal();
				actualReciboCabecera.calcularMontoTotal();
				actualReciboCabecera.calcularCobrar();

			} else {

				System.out.println("noooooo");

				if (event.getColumn().getColumnKey().endsWith("adicional")) {

					detalleCelda.getDetalleAlojamiento()
							.setAlojamientoAdicional(valor);
					detalleCelda.calcularTotalDetalle();
					actualReciboCabecera.calcularAlojamientosTotal();
					actualReciboCabecera.calcularTotal();
					actualReciboCabecera.calcularMontoTotal();
					actualReciboCabecera.calcularCobrar();

				}

			}

		} else {
			table.reset();
			System.out.println("ERRROR");

		}

	}

	public void removerAlojamiento() {

		listaDetalles.remove(detalleSeleccionado);
		habitacionSleccionada = detalleSeleccionado.getDetalleAlojamiento()
				.getAlojamientoHabitacion();
		habitacionSleccionada.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
		habitacionService.update(habitacionSleccionada);
		actualReciboCabecera.calcularAlojamientosTotal();
		actualReciboCabecera.calcularTotal();
		actualReciboCabecera.calcularMontoTotal();
		actualReciboCabecera.calcularCobrar();
		detalleSeleccionado = new ReciboDetalle();
		habitacionSleccionada = new Habitacion();

	}

	public void cancelarAlojamiento() {

		alojamiento = new Alojamiento();
		reciboHabitacion = new Habitacion();

	}

	public void onRowSelect(SelectEvent event) {
		detalleSeleccionado = (ReciboDetalle) event.getObject();

	}

	public void actualizarHabitacionDisponible() {

		for (ReciboDetalle detalle : listaDetalles) {
			reciboHabitacion = detalle.getDetalleAlojamiento()
					.getAlojamientoHabitacion();
			if (reciboHabitacion.getHabitacionEstado() == HabitacionEstado.ALOJAMINETO) {
				reciboHabitacion
						.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
				habitacionService.update(reciboHabitacion);
			} else {
				if (reciboHabitacion.getHabitacionEstado() == HabitacionEstado.OCUPADA) {
					reciboHabitacion
							.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
					habitacionService.update(reciboHabitacion);
				} else {

					if (reciboHabitacion.getHabitacionEstado() == HabitacionEstado.RESERVADA
							&& casoReserva != 5) {
						reciboHabitacion
								.setHabitacionEstado(HabitacionEstado.DISPONIBLE);
						habitacionService.update(reciboHabitacion);
					}

				}

			}
		}

	}

	public void addCosumos() {

		if (consumo.getConsumoCliente() == null
				|| listaRequerimiento.isEmpty()
				|| consumo.getListaRequerimiento().listIterator().next()
						.getRequerimientoCantidad() == 0) {

			FacesContext.getCurrentInstance().addMessage(
					"message",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Campos requeridos"));
		} else {

			if (listaDetalles.isEmpty()) {
				eliminarConusmos();
				consumoService.create(consumo);
				init();
				getUser();

			} else {
				eliminarConusmos();
				System.out.println("CONSUMO" + consumo.getConsumoFecha());
				reciboHospedaje.setHospedajeAgregarConsumos(true);
				actualReciboCabecera.calcularTotal();
				actualReciboCabecera.calcularMontoTotal();
				actualReciboCabecera.calcularCobrar();
				reciboCabeceraService.update(actualReciboCabecera);
				init();
				getUser();

			}
		}
	}

	public void agregarConsumo() {

		if (productoServicio == null) {
			FacesContext.getCurrentInstance().addMessage(
					"message",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							"Seleccione producto"));
		} else {

			if (listaDetalles.isEmpty()) {
				System.out.println("CLIENTE");
				requerimiento.setEliminarRequerimiento(true);
				requerimiento.setRequerimientoProducto(productoServicio);
				requerimiento.setRequerimientoPrecio(productoServicio
						.getProductoPrecio());
				requerimiento.calcularTotal();
				requerimiento.setConsumo(consumo);
				listaRequerimiento.add(requerimiento);
				consumo.setListaRequerimiento(listaRequerimiento);
				consumo.calcularTotal();
				requerimiento = new Requerimiento();
				productoServicio = new Producto();
				servicioSeleccionado = new Servicio();
				productoServicio = new Producto();
			} else {
				requerimiento.setEliminarRequerimiento(true);
				requerimiento.setRequerimientoProducto(productoServicio);
				requerimiento.setRequerimientoPrecio(productoServicio
						.getProductoPrecio());
				requerimiento.calcularTotal();
				listaRequerimiento.add(requerimiento);
				requerimiento.setConsumo(consumo);
				consumo.setListaRequerimiento(listaRequerimiento);
				consumo.calcularTotal();
				consumo.setConsumoHospedaje(true);
				reciboHospedaje.setConsumo(consumo);
				requerimiento = new Requerimiento();
				productoServicio = new Producto();
				servicioSeleccionado = new Servicio();
				productoServicio = new Producto();

			}
		}

	}

	public void eliminarConusmos() {
		for (Requerimiento requerimiento : listaRequerimiento) {
			requerimiento.setEliminarRequerimiento(false);
		}
	}

	public void removerConsumo() {

		if (requerimientoSeleccionado.isEliminarRequerimiento() == true) {
			listaRequerimiento.remove(requerimientoSeleccionado);
			requerimientoSeleccionado.calcularTotal();
			consumo.calcularTotal();
			requerimientoSeleccionado = new Requerimiento();
			productoServicio = new Producto();
			servicioSeleccionado = new Servicio();

		} else {
			FacesContext.getCurrentInstance().addMessage(
					"message",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Consumo anteriormente registrado"));
			requerimientoSeleccionado = new Requerimiento();
			productoServicio = new Producto();
			servicioSeleccionado = new Servicio();

		}
	}

	public void onCellEditServicio(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		int cantidad = (int) newValue;
		int cantidad2 = (int) oldValue;
		DataTable table = (DataTable) event.getSource();
		if (table.isRowAvailable()) {
			Requerimiento requerimiento = (Requerimiento) table.getRowData();
			if (requerimiento.isEliminarRequerimiento() == true) {
				requerimiento.setRequerimientoCantidad(cantidad);
				requerimiento.calcularTotal();

			} else {
				requerimiento.setRequerimientoCantidad(cantidad2);
			}
			if (listaDetalles.isEmpty()) {

				requerimiento.getConsumo().calcularTotal();
			} else {

				requerimiento.getConsumo().calcularTotal();
				actualReciboCabecera.calcularCobrar();
			}

		} else {
			table.reset();

		}

	}

	public void ListarProductos() {

		listaProductos = productotoService.findServicio(servicioSeleccionado
				.getServicioNombre());

	}

	public Producto getProducto(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("no id provided");
		}
		for (Producto producto : listaProductos) {
			if (id.equals(producto.getProductoId())) {
				return producto;
			}
		}
		return null;
	}

	public void pasarServicios() {
		try {
			init();
			getUser();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../ventaServicios/servicios.xhtml");
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void mostrarDialogos() {

		if (reciboHospedaje.isHospedajeAgregarConsumos() == true) {
			init();
			getUser();
			RequestContext.getCurrentInstance().execute(
					"PF('dlgHospedaje').show()");

		} else {
			init();
			getUser();
			clienteCaso = 3;
			RequestContext.getCurrentInstance().execute(
					"PF('dlgCliente').show()");

		}
	}

	public void onRowSelectServicio(SelectEvent event) {
		requerimientoSeleccionado = ((Requerimiento) event.getObject());

	}

	public void pasarCheckIn() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../hospedaje/checkInHospedaje.xhtml");
	}

	// CHECK-OUT

	public void pasarCheckOut() throws IOException {

		cargar();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../hospedaje/checkOutHospedaje.xhtml");
	}

	public void recivirCliente() {
		this.clienteRecibo = reciboHospedaje.getCliente();
		consumo.setConsumoCliente(clienteRecibo);

	}

	public void recivirHospedaje() {

		this.reciboHospedaje = actualReciboCabecera.getListaDetalles()
				.listIterator().next().getDetalleAlojamiento()
				.getAlojamientoHospedaje();
		this.listaDetalles = actualReciboCabecera.getListaDetalles();
		actualReciboCabecera.calcularAlojamientosTotal();

	}

	public void recivirAbonos() {

		this.listaMontos = actualReciboCabecera.getListaAbonos();
		actualReciboCabecera.setListaAbonos(listaMontos);

	}

	public void recivirConsumos() {

		if (reciboHospedaje.getConsumo() == null) {
			FacesContext.getCurrentInstance().addMessage(
					"message",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"No tiene registro de consumos"));

		} else {
			this.consumo = reciboHospedaje.getConsumo();
			this.listaRequerimiento = consumo.getListaRequerimiento();
		}

	}

	public void agregarHospedaje() {

		reciboHospedaje.setHospedajeAgregarConsumos(true);

	}

	public void nuevoRecibo() {

		recivirHospedaje();
		recivirCliente();
		recivirAbonos();
		recivirConsumos();
		consumo.setConsumoCliente(clienteRecibo);

	}

	// Abonos

	public void calcularDetalleIngreso() {

		for (ReciboDetalle detalle : listaDetalles) {

			detalle.calcularTotalIngreso();

		}

	}

	public void onRowSelectAbono(SelectEvent event) {
		montoSeleccionado = ((Abono) event.getObject());

	}

	public void removerAbono() {
		if (montoSeleccionado.isAbonoEliminar() == true) {
			listaMontos.remove(montoSeleccionado);
			actualReciboCabecera.calcularMontoTotal();
			actualReciboCabecera.calcularCobrar();
			calcularDetalleIngreso();
			montoSeleccionado = new Abono();
			monto = new Abono();
			monto.setAbonoFecha(Calendar.getInstance().getTime());
			FacesContext.getCurrentInstance().addMessage(
					"messagess",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Monto eliminado"));

		} else {
			FacesContext.getCurrentInstance().addMessage(
					"messagess",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Monto anteriormente registrado"));
			montoSeleccionado = new Abono();
			monto = new Abono();
			monto.setAbonoFecha(Calendar.getInstance().getTime());

		}

	}

	public void eliminarMonto() {
		for (Abono monto : listaMontos) {

			monto.setAbonoEliminar(false);
		}
	}

	public void addNuevoMonto() {

		System.out.println("NULO"
				+ actualReciboCabecera.getReciboCabeceraCobrar());

		if (monto.getAbonoMonto() == null
				|| monto.getAbonoMonto().compareTo(BigDecimal.ZERO) == 0) {
			System.out.println("NULO");
			FacesContext.getCurrentInstance().addMessage(
					"messagess",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Valor nulo"));
			monto.setAbonoFecha(Calendar.getInstance().getTime());

		} else {

			if (actualReciboCabecera.getReciboCabeceraCobrar().compareTo(
					BigDecimal.ZERO) == 0) {

				FacesContext.getCurrentInstance().addMessage(
						"messagess",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
								"Pago completado"));
				monto.setAbonoFecha(Calendar.getInstance().getTime());

			}
			if (actualReciboCabecera.getReciboCabeceraCobrar().compareTo(
					monto.getAbonoMonto()) < 0) {
				FacesContext.getCurrentInstance().addMessage(
						"messagess",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
								"Monto superado"));
				monto.setAbonoFecha(Calendar.getInstance().getTime());

			}
			if (monto.getAbonoTipoPago() == null) {

				FacesContext.getCurrentInstance().addMessage(
						"messagess",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
								"Seleccione tipo pago"));
			}
			if (monto.getAbonoTipoPago() != null
					&& monto.getAbonoMonto().compareTo(
							actualReciboCabecera.getReciboCabeceraCobrar()) < 0
					|| monto.getAbonoMonto().compareTo(
							actualReciboCabecera.getReciboCabeceraCobrar()) == 0) {

				System.out.println("SIIIIIIII");
				monto.setAbonoEliminar(true);
				monto.setAbonoReciboCabecera(actualReciboCabecera);
				listaMontos.add(monto);
				actualReciboCabecera.calcularMontoTotal();
				actualReciboCabecera.calcularCobrar();
				calcularDetalleIngreso();
				monto = new Abono();
				monto.setAbonoFecha(Calendar.getInstance().getTime());
				FacesContext.getCurrentInstance().addMessage(
						"messagess",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
								"Monto registrado"));
			}
		}
	}

	public void casoHabitacion() {

		clienteCaso = 2;
		this.habitacionAnterior = detalleSeleccionado.getDetalleAlojamiento()
				.getAlojamientoHabitacion();
		System.out.println("estado" + habitacionAnterior.getHabitacionCodigo());

	}

	public void casoHuesped() {
		clienteCaso = 2;
	}

	public void actualizarHabitacionAnterior() {

		habitacionAnterior.setHabitacionEstado(HabitacionEstado.MANTENIMIENTO);
		habitacionService.update(habitacionAnterior);
		cambioHabitacion = new Habitacion();
	}

	public void actualizarCambioHabitacion() {

		for (ReciboDetalle detalle : listaDetalles) {
			cambioHabitacion = detalle.getDetalleAlojamiento()
					.getAlojamientoHabitacion();
			cambioHabitacion.setHabitacionEstado(HabitacionEstado.OCUPADA);
			habitacionService.update(cambioHabitacion);
		}
		reciboCabeceraService.update(actualReciboCabecera);
	}

	public void cancelarEdicion() {

		clienteCaso = 0;
		init();
		getUser();
		RequestContext.getCurrentInstance().execute("PF('dlgRecibo').hide()");
		cargar();
	}

	public void inicializar() {
		System.out.println("REScibooooooooo");

		init();
		getUser();
	}

	public void generarFactura() {
		pasarFactura();
		realizarCheckOut();

	}

	public void mostraDialogoCkechOut() {
		if (actualReciboCabecera.getReciboCabeceraCobrar().compareTo(
				BigDecimal.ZERO) == 0) {
			RequestContext.getCurrentInstance()
					.execute("PF('cfdialog').show()");

		} else {
			FacesContext.getCurrentInstance().addMessage(
					"message",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							"Pendiente cobrar "
									+ actualReciboCabecera
											.getReciboCabeceraCobrar()));

		}

	}

	public void realizarCheckOut() {
		Habitacion habitacion;
		for (ReciboDetalle detalle : listaDetalles) {
			habitacion = detalle.getDetalleAlojamiento()
					.getAlojamientoHabitacion();
			habitacion.setHabitacionEstado(HabitacionEstado.LIMPIEZA);
			habitacionService.update(habitacion);

		}
		actualReciboCabecera.setReciboCabeceraCheckOut(true);
		reciboCabeceraService.update(actualReciboCabecera);
		RequestContext.getCurrentInstance().execute("PF('cfdialog').hide()");
		RequestContext.getCurrentInstance().execute("PF('dlgRecibo').hide()");
	}

	// RESERVA

	public void editarDias(SelectEvent event) {

		reserva.calcularDias();
	}

	public void editarReserva() {

		reservaService.update(reserva);
		FacesContext.getCurrentInstance().addMessage(
				"messages",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
						"Reserva Editada"));
		reserva = new Reserva();
	}

	public void cancelarEdicionReserva() {

		reservaService.findAll();
		reserva = new Reserva();

	}

	public void realizarCheckInReserva() {

		if (reserva.getReservaEstado() == ReservaEstado.PENDIENTE
				|| reserva.getReservaEstado() == null) {

			FacesContext.getCurrentInstance().addMessage(
					"messager",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
							"Reserva por confirmar "));

		} else {

			if (reserva.getReservaEstado().equals(ReservaEstado.CONFIRMADO)) {
				casoReserva = 5;
				reciboHospedaje.setListaAlojamientos(listaAlojamientos);
				reciboHospedaje
						.setHospedajeEntrada(reserva.getReservaEntrada());
				reciboHospedaje.setHospedajeSalida(reserva.getReservaSalida());
				reciboHospedaje.setCliente(reserva.getClienteReserva());
				reciboHospedaje.setCliente(reserva.getClienteReserva());
				actualReciboCabecera.setClienteRecibo(reserva
						.getClienteReserva());
				List<Habitacion> listaHabitaciones = reserva
						.getReservaHabitaciones();
				for (Habitacion habitacion : listaHabitaciones) {

					alojamiento.setAlojamientoHabitacion(habitacion);
					alojamiento.setAlojamientoCliente(reserva
							.getClienteReserva());
					alojamiento.setAlojamientoHospedaje(reciboHospedaje);
					listaAlojamientos.add(alojamiento);
					reciboDetalle.setDetalleAlojamiento(alojamiento);
					reciboDetalle.setReciboCabecera(actualReciboCabecera);
					reciboHospedaje.asignarDias();
					reciboDetalle.calcularTotalDetalle();
					listaDetalles.add(reciboDetalle);
					reciboDetalle = new ReciboDetalle();
					alojamiento = new Alojamiento();

				}
				actualReciboCabecera.setListaDetalles(listaDetalles);
				actualReciboCabecera.setListaAbonos(listaMontos);
				actualReciboCabecera.calcularAlojamientosTotal();
				actualReciboCabecera.calcularTotal();
				actualReciboCabecera.calcularMontoTotal();
				actualReciboCabecera.calcularCobrar();

				try {
					pasarCheckIn();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	// FACTURA
	public void pasarFactura() {

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../factura/factura.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// USUARIO
	public void getUser() {
		Principal principal = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal();
		System.out.println("USUAROOOOOOOOOOO" + principal.getName());

		if (principal != null && usuario != null) {

			List<UsuarioSistema> filtList = listaUsuarios
					.stream()
					.filter(value -> value.getUsuarioSistemaUsuario().matches(
							"(?i).*" + principal.getName() + ".*"))
					.collect(Collectors.toList());

			setUsuario(filtList.get(0));
		}
	}

	public void ingresarSistema() throws IOException {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			request.login(usuario.getUsuarioSistemaUsuario(),
					usuario.getUsuarioSistemaContrasena());
			if (request.isUserInRole("admin")) {

				getUser();
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("admin/hospedaje/planning.xhtml");
			}
			if (request.isUserInRole("usuario")) {

				getUser();
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("user/hospedaje/planning.xhtml");

			}
		} catch (ServletException e) {

			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Usuario o contraseña invalidos"));

		}
	}

	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		try {
			request.logout();
			usuario = new UsuarioSistema();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/AppHotelLeon/login.xhtml");
		} catch (ServletException e) {

			context.addMessage(null, new FacesMessage("Logout failed."));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void buscarHabitacion() {

		if (buscarTipoHabitacion == null) {

			listaHabitaciones = habitacionService.findAll();
		} else {
			List<Habitacion> filtList = listaHabitaciones
					.stream()
					.filter(value -> value.getTipoHabitacion()
							.getTipoHabitacionCategoria()
							.matches("(?i).*" + buscarTipoHabitacion + ".*")
							|| value.getHabitacionEstado()
									.toString()
									.matches(
											"(?i).*" + buscarTipoHabitacion
													+ ".*")
							|| value.getHabitacionEdificio()
									.toString()
									.matches(
											"(?i).*" + buscarTipoHabitacion
													+ ".*")
							|| value.getHabitacionCodigo().matches(
									"(?i).*" + buscarTipoHabitacion + ".*")
							|| value.getHabitacionPrecioDia()
									.toString()
									.matches(
											"(?i).*" + buscarTipoHabitacion
													+ ".*")
							|| value.getTipoArriendo()
									.toString()
									.matches(
											"(?i).*" + buscarTipoHabitacion
													+ ".*")
							|| value.getEstadoLimpieza()
									.toString()
									.matches(
											"(?i).*" + buscarTipoHabitacion
													+ ".*"))
					.collect(Collectors.toList());
			setListaHabitaciones(filtList);

		}
	}

	public void buscarCliente() {

		if (buscarCliente == null) {

			cargar();

		} else {

			List<ReciboCabecera> filtList = listaRecibos
					.stream()
					.filter(value -> value.getClienteRecibo()
							.getPersonaApellidos()
							.matches("(?i).*" + buscarCliente + ".*")
							|| value.getClienteRecibo().getPersonaNombres()
									.matches("(?i).*" + buscarCliente + ".*")
							|| value.getListaDetalles().listIterator().next()
									.getDetalleAlojamiento()
									.getAlojamientoHabitacion()
									.getHabitacionCodigo()
									.matches("(?i).*" + buscarCliente + ".*")
							|| value.getListaDetalles().listIterator().next()
									.getDetalleAlojamiento()
									.getAlojamientoHabitacion()
									.getTipoHabitacion()
									.getTipoHabitacionCategoria()
									.matches("(?i).*" + buscarCliente + ".*"))
					.collect(Collectors.toList());
			setListaRecibos(filtList);

			/*
			 * String words[] = buscarCliente.split("\\s"); String
			 * capitalizeWord = ""; for (String w : words) { String first =
			 * w.substring(0, 1); String afterfirst = w.substring(1);
			 * capitalizeWord += first.toUpperCase() + afterfirst + " "; }
			 * buscarCliente=capitalizeWord;
			 */
			// listaRecibos = reciboCabeceraService.findAll2(buscarCliente);
			System.out.println("clientee" + buscarCliente);

		}

	}

}
