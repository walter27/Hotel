package org.hotelLeon.presentacion.factura;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.hotelLeon.aplicacion.factura.ReciboCabeceraService;
import org.hotelLeon.aplicacion.hospedaje.HabitacionService;
import org.hotelLeon.aplicacion.hospedaje.HospedajeService;
import org.hotelLeon.dominio.factura.Abono;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.ReciboDetalle;
import org.hotelLeon.dominio.factura.TipoPago;
import org.hotelLeon.dominio.hospedaje.Alojamiento;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.hospedaje.HabitacionEstado;
import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.presentacion.persona.AsignarCliente;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

@Named
@SessionScoped
public class DlgRecibo implements Serializable {

	private List<ReciboCabecera> listaRecibos;
	private TipoPago[] listaTipoPago;
	private Hospedaje reciboHospedaje;
	private Cliente clienteRecibo;
	private List<ReciboDetalle> listaDetalles;
	private ReciboCabecera reciboSeleccionado;
	private String buscarCliente;
	private int dias;
	private List<Abono> listaMontos;
	private Abono monto;
	private ReciboDetalle detalleSeleccionado;
	private Habitacion cambioHabitacion;
	private BigDecimal cambioDescuento;
	private int casoHabitacion;
	private Habitacion anteriorHabitacion;

	@Inject
	private HabitacionService habitacionService;

	@Inject
	private HospedajeService hospedajeService;

	@Inject
	private ReciboCabeceraService reciboCabeceraService;

	@PostConstruct
	private void init() {

		reciboSeleccionado = new ReciboCabecera();
		reciboHospedaje = new Hospedaje();
		listaRecibos = new ArrayList<ReciboCabecera>();
		listaDetalles = new ArrayList<ReciboDetalle>();
		listaMontos = new ArrayList<Abono>();
		monto = new Abono();
		cambioHabitacion = new Habitacion();
		cargar();
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

	public ReciboCabecera getReciboSeleccionado() {
		return reciboSeleccionado;
	}

	public void setReciboSeleccionado(ReciboCabecera reciboSeleccionado) {
		this.reciboSeleccionado = reciboSeleccionado;
	}

	public String getBuscarCliente() {
		return buscarCliente;
	}

	public void setBuscarCliente(String buscarCliente) {
		this.buscarCliente = buscarCliente;
	}

	public List<Abono> getListaMontos() {
		return listaMontos;
	}

	public void setListaMontos(List<Abono> listaMontos) {
		this.listaMontos = listaMontos;
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

	public void addRecibo() {

		actualizarHabitacion();
		hospedajeService.update(reciboHospedaje);
		reciboCabeceraService.update(reciboSeleccionado);
		listaRecibos.add(reciboSeleccionado);
		reciboSeleccionado = new ReciboCabecera();
		reciboHospedaje = new Hospedaje();
		casoHabitacion = 0;
		listaDetalles.clear();
		cargar();

	}

	public void recivirCliente() {
		this.clienteRecibo = reciboSeleccionado.getClienteRecibo();

	}

	public void recivirHospedaje() {

		this.reciboHospedaje = reciboSeleccionado.getListaDetalles()
				.listIterator().next().getDetalleAlojamiento()
				.getAlojamientoHospedaje();
		this.listaDetalles = reciboSeleccionado.getListaDetalles();
	}

	public void recivirAbonos() {

		this.listaMontos = reciboSeleccionado.getListaAbonos();
		for (Abono abonos : listaMontos) {
			System.out.println("abono" + abonos.getAbonoMonto());
		}

	}

	public void addNuevoMonto() {

		if (monto.getAbonoMonto() == null) {
			monto.setAbonoMonto( new BigDecimal(0.00));

		}
		if (monto.getAbonoMonto() == new BigDecimal(0.00)) {

			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Valor nulo"));
			monto = new Abono();

		} else {
			if (reciboSeleccionado.getReciboCabeceraCobrar() == new BigDecimal(0.00)) {
				FacesContext.getCurrentInstance().addMessage(
						"messages",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Info",
								"Pago completado: "
										+ reciboSeleccionado
												.getReciboCabeceraCobrar()));
				monto = new Abono();

			} else {
				listaMontos.add(monto);
				monto.setAbonoReciboCabecera(reciboSeleccionado);
				System.out.println("abono" + monto.getAbonoMonto());
				monto = new Abono();

			}

		}

	}

	public void calcularMontoTotal() {

		reciboSeleccionado.calcularMontoTotal();
	}

	public void onRowSelect(SelectEvent event) {
		detalleSeleccionado = ((ReciboDetalle) event.getObject());
		System.out.println("slecionado"
				+ detalleSeleccionado.getDetalleAlojamiento()
						.getAlojamientoHabitacion().getHabitacionCodigo());

	}

	public void cambiarHabitacion(
			@Observes(notifyObserver = Reception.IF_EXISTS) Habitacion habitacion) {

		if (casoHabitacion == 1) {
			this.cambioHabitacion = habitacion;
			detalleSeleccionado.getDetalleAlojamiento()
					.setAlojamientoHabitacion(cambioHabitacion);
			detalleSeleccionado.getDetalleAlojamiento()
					.setAlojamientoDescuento(new BigDecimal(0.00));
			//detalleSeleccionado
					//.total(cambioHabitacion.getHabitacionPrecioDia());
			reciboSeleccionado.calcularMontoTotal();
			actualizarHabitacionAnterior();
			RequestContext.getCurrentInstance().update("form2");
		}
		casoHabitacion = 0;
	}

	public void casoHabitacion() {

		casoHabitacion = 1;
		this.anteriorHabitacion = detalleSeleccionado.getDetalleAlojamiento()
				.getAlojamientoHabitacion();
		System.out.println("estado" + anteriorHabitacion.getHabitacionCodigo());

	}

	public void actualizarHabitacionAnterior() {

		anteriorHabitacion.setHabitacionEstado(HabitacionEstado.MANTENIMIENTO);
		habitacionService.update(anteriorHabitacion);
		cambioHabitacion = new Habitacion();
	}

	public void actualizarHabitacion() {

		for (ReciboDetalle detalle : listaDetalles) {
			cambioHabitacion = detalle.getDetalleAlojamiento()
					.getAlojamientoHabitacion();
			cambioHabitacion.setHabitacionEstado(HabitacionEstado.OCUPADA);
			habitacionService.update(cambioHabitacion);
		}
	}

	public void calcularDias(SelectEvent event) {

		reciboHospedaje.asignarDias();
		asignarCantidad();
		reciboSeleccionado.calcularTotal();

	}

	public void asignarCantidad() {

		for (ReciboDetalle detalle : listaDetalles) {
			if (detalle.getDetalleAlojamiento().getAlojamientoDescuento() == null) {
				detalle.getDetalleAlojamiento().setAlojamientoDescuento(new BigDecimal(0.00));
				detalle.setDetalleCantidad(reciboHospedaje.getHospedajeDias());
			//	detalle.total(detalle.getDetalleAlojamiento()
					//	.getAlojamientoHabitacion().getHabitacionPrecioDia());
				System.out.println("detallle1111"
						+ detalle.getDetalleAlojamiento()
								.getAlojamientoDescuento());
			} else {
				detalle.setDetalleCantidad(reciboHospedaje.getHospedajeDias());
				//detalle.total(detalle.getDetalleAlojamiento()
					//	.getAlojamientoHabitacion().getHabitacionPrecioDia());
				System.out.println("detallle2222"
						+ detalle.getDetalleAlojamiento()
								.getAlojamientoDescuento());
			}

		}

	}

	public void onCellEdit(CellEditEvent event) {

		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		int indice = event.getRowIndex();
		System.out.println("indice" + indice);
		for (ReciboDetalle detalle : listaDetalles) {
			cambioDescuento = (BigDecimal) newValue;
			if (indice == listaDetalles.indexOf(detalle)) {
				detalle.getDetalleAlojamiento().setAlojamientoDescuento(
					cambioDescuento);
				reciboSeleccionado.calcularMontoTotal();
			}
		}
	}

	public void nuevoRecibo() {

		recivirCliente();
		recivirHospedaje();
		recivirAbonos();
	}

	public void cargar() {

		listaRecibos = reciboCabeceraService.findAll();

	}

	public void buscarCliente() {

		if (buscarCliente.equals("")) {

			cargar();

		}
	}

	public void cancelar() {

		listaRecibos = reciboCabeceraService.findAll();
	}

	public void cargarHospedajes() throws IOException {

		cargar();
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("/AppHotelLeon/hospedaje/checkOutHospedaje.xhtml");
	}

}
