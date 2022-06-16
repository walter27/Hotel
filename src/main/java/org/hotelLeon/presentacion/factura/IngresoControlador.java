package org.hotelLeon.presentacion.factura;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hotelLeon.aplicacion.factura.FacturaCabeceraService;
import org.hotelLeon.aplicacion.factura.IngresoService;
import org.hotelLeon.aplicacion.factura.ReciboCabeceraService;
import org.hotelLeon.aplicacion.persona.UsuarioService;
import org.hotelLeon.aplicacion.servicios.ConsumoService;
import org.hotelLeon.aplicacion.servicios.ServicioService;
import org.hotelLeon.dominio.factura.Abono;
import org.hotelLeon.dominio.factura.FacturaCabecera;
import org.hotelLeon.dominio.factura.FacturaDetalle;
import org.hotelLeon.dominio.factura.Ingreso;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.ReciboDetalle;
import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.persona.UsuarioSistema;
import org.hotelLeon.dominio.servicios.*;

@Named
@ApplicationScoped
public class IngresoControlador implements Serializable {

	private UsuarioSistema usuario;
	private List<Consumo> listaConsumos;
	private List<Requerimiento> listaRequerimiento;
	private List<ReciboDetalle> listaDetalles;
	private Ingreso actualIngreso;
	private List<ReciboCabecera> listaReecibos;
	private List<Ingreso> listaIngreso;
	private List<UsuarioSistema> listaUsuarios;
	private List<Servicio> listaServicios;
	private List<Servicio> listaServicioSlecionados;
	private List<Requerimiento> filteredRequerimiento;
	private List<ReciboDetalle> filteredDetalles;

	@Inject
	private IngresoService ingresoService;

	@Inject
	private ReciboCabeceraService reciboCabeceraService;

	@Inject
	private ConsumoService consumoService;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private ServicioService servicioService;

	@PostConstruct
	private void init() {

		actualIngreso = new Ingreso();
		listaConsumos = new ArrayList<Consumo>();
		listaDetalles = new ArrayList<ReciboDetalle>();
		listaReecibos = new ArrayList<ReciboCabecera>();
		listaRequerimiento = new ArrayList<Requerimiento>();
		listaIngreso = new ArrayList<Ingreso>();
		usuario = new UsuarioSistema();
		listaServicioSlecionados = new ArrayList<Servicio>();
		listaUsuarios = usuarioService.findAll();
		listaServicios = servicioService.findAll();

	}

	public UsuarioSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public List<Consumo> getListaConsumos() {
		return listaConsumos;
	}

	public void setListaConsumos(List<Consumo> listaConsumos) {
		this.listaConsumos = listaConsumos;
	}

	public List<ReciboDetalle> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<ReciboDetalle> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public Ingreso getActualIngreso() {
		return actualIngreso;
	}

	public void setActualIngreso(Ingreso actualIngreso) {
		this.actualIngreso = actualIngreso;
	}

	public List<ReciboCabecera> getListaReecibos() {
		return listaReecibos;
	}

	public void setListaReecibos(List<ReciboCabecera> listaReecibos) {
		this.listaReecibos = listaReecibos;
	}

	public List<Requerimiento> getListaRequerimiento() {
		return listaRequerimiento;
	}

	public void setListaRequerimiento(List<Requerimiento> listaRequerimiento) {
		this.listaRequerimiento = listaRequerimiento;
	}

	public List<Ingreso> getListaIngreso() {
		return listaIngreso;
	}

	public void setListaIngreso(List<Ingreso> listaIngreso) {
		this.listaIngreso = listaIngreso;
	}

	public List<UsuarioSistema> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UsuarioSistema> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Servicio> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<Servicio> listaServicios) {
		this.listaServicios = listaServicios;
	}

	public List<Servicio> getListaServicioSlecionados() {
		return listaServicioSlecionados;
	}

	public void setListaServicioSlecionados(
			List<Servicio> listaServicioSlecionados) {
		this.listaServicioSlecionados = listaServicioSlecionados;
	}

	public List<Requerimiento> getFilteredRequerimiento() {
		return filteredRequerimiento;
	}

	public void setFilteredRequerimiento(
			List<Requerimiento> filteredRequerimiento) {
		this.filteredRequerimiento = filteredRequerimiento;
	}

	public List<ReciboDetalle> getFilteredDetalles() {
		return filteredDetalles;
	}

	public void setFilteredDetalles(List<ReciboDetalle> filteredDetalles) {
		this.filteredDetalles = filteredDetalles;
	}

	public void listarReporte() {

		actualIngreso.setIngresosFecha(Calendar.getInstance().getTime());
		listaReecibos = reciboCabeceraService.findAll();
		List<ReciboCabecera> filtList = listaReecibos
				.stream()
				.filter(value -> value.isReciboReporte() == false
						&& value.getListaDetalles().listIterator().next()
								.getDetalleTotalIngreso()
								.compareTo(BigDecimal.ZERO) > 0)
				.collect(Collectors.toList());

		for (ReciboCabecera recibo : filtList) {

			listaDetalles.addAll(recibo.getListaDetalles());
		}

		actualIngreso.setListaRequerimiento(listaRequerimiento);
		actualIngreso.setListaDetalles(listaDetalles);
		actualIngreso.calcularTotalAlojamientos();
		actualIngreso.calcularTotalConsumos();
		actualIngreso.calcularTotal();
		actualIngreso.calcularTotalFinal();
		listaDetalles = new ArrayList<ReciboDetalle>();
		listaReecibos = new ArrayList<ReciboCabecera>();

	}

	public void listarConsumos() {

		actualIngreso.setListaRequerimiento(listaRequerimiento);
		boolean caso = true;
		listaConsumos = consumoService.findAll();
		ListIterator<Servicio> iter = listaServicioSlecionados.listIterator();
		for (Servicio servicio2 : listaServicios) {
			for (Servicio servicio : listaServicioSlecionados) {

				if (servicio2.equals(servicio)) {

					List<Consumo> filtListC = listaConsumos
							.stream()
							.filter(value -> value.getListaRequerimiento()
									.listIterator().next()
									.getRequerimientoProducto()
									.getProductoServicio().getServicioNombre()
									.equals(servicio.getServicioNombre())
									&& value.isConsumoReporte() == false)
							.collect(Collectors.toList());

					for (Consumo consumo : filtListC) {

						System.out.println("AGREGAR"
								+ servicio2.getServicioNombre()
								+ servicio.getServicioNombre());
						listaRequerimiento.addAll(consumo
								.getListaRequerimiento());

					}
					caso = false;

				}

			}
			if (caso) {
				List<Consumo> filtListC = listaConsumos
						.stream()
						.filter(value -> value.getListaRequerimiento()
								.listIterator().next()
								.getRequerimientoProducto()
								.getProductoServicio().getServicioNombre()
								.equals(servicio2.getServicioNombre())
								&& value.isConsumoReporte() == false)
						.collect(Collectors.toList());

				for (Consumo consumo : filtListC) {
					System.out.println("REMOVER");

					actualIngreso.getListaRequerimiento().removeIf(
							c -> consumo
									.getListaRequerimiento()
									.stream()
									.map(Requerimiento::getRequerimientoId)
									.anyMatch(
											n -> n.equals(c
													.getRequerimientoId())));

				}

			}
		}

		actualIngreso.calcularTotalAlojamientos();
		actualIngreso.calcularTotalConsumos();
		actualIngreso.calcularTotal();
		actualIngreso.calcularTotalFinal();
		listaIngreso.add(actualIngreso);
		listaRequerimiento = new ArrayList<Requerimiento>();
		listaConsumos = new ArrayList<Consumo>();

	}

	public void agregarGastos() {
		actualIngreso.calcularTotalFinal();

	}

	public void pasarCierreCaja() {

		try {
			getUser();
			listarReporte();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../ingreso/ingresos.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getUser() {
		Principal principal = FacesContext.getCurrentInstance()
				.getExternalContext().getUserPrincipal();
		System.out.println("USUAROOOOOOOOOOO" + principal.getName());

		if (principal != null) {

			List<UsuarioSistema> filtList = listaUsuarios
					.stream()
					.filter(value -> value.getUsuarioSistemaUsuario().matches(
							"(?i).*" + principal.getName() + ".*"))
					.collect(Collectors.toList());

			setUsuario(filtList.get(0));
			actualIngreso.setIngresosUsario(usuario);
		}
	}

	public void imprimirIngresos(ActionEvent actionEvent) throws Exception {

		if (actualIngreso.getListaRequerimiento().isEmpty()
				|| actualIngreso.getListaDetalles().isEmpty()) {

			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"No existen registros"));

		} else {

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("txtUsuario", "usuario");

			File jasper = new File(FacesContext.getCurrentInstance()
					.getExternalContext()
					.getRealPath("/WEB-INF/reportes/ingresos.jasper"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasper.getPath(), null,
					new JRBeanCollectionDataSource(this.getListaIngreso()));

			HttpServletResponse response = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			response.addHeader("Content-disposition",
					"attachment; filename=ingresos.pdf");
			ServletOutputStream stream = response.getOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();

		}
	}

	public void cerrarCaja() {

		for (ReciboDetalle detalle : actualIngreso.getListaDetalles()) {

			detalle.setDetalleTotalIngreso(new java.math.BigDecimal(0.00));
			if (detalle.getReciboCabecera().getReciboCabeceraCobrar()
					.intValue() == 0) {
				detalle.getReciboCabecera().setReciboReporte(true);
				reciboCabeceraService.update(detalle.getReciboCabecera());
			}
			for (Abono abono : detalle.getReciboCabecera().getListaAbonos()) {

				abono.setAbonoReporte(true);
				reciboCabeceraService.update(detalle.getReciboCabecera());

			}

		}

		for (Requerimiento requerimiento : actualIngreso
				.getListaRequerimiento()) {

			requerimiento.getConsumo().setConsumoReporte(true);
			consumoService.update(requerimiento.getConsumo());

		}

		ingresoService.create(actualIngreso);
		init();
	}

}
