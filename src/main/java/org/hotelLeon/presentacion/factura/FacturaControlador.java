package org.hotelLeon.presentacion.factura;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hotelLeon.aplicacion.factura.FacturaCabeceraService;
import org.hotelLeon.dominio.factura.FacturaCabecera;
import org.hotelLeon.dominio.factura.FacturaDetalle;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.factura.ReciboDetalle;
import org.hotelLeon.dominio.persona.Cliente;
import org.primefaces.context.RequestContext;

import com.ibm.icu.util.Calendar;

@Named
@ApplicationScoped
public class FacturaControlador implements Serializable {

	private FacturaCabecera facturaCabecera;
	private FacturaDetalle facturaDetalle;
	private List<FacturaDetalle> listaDetalles;
	private ReciboCabecera reciboCabecera;
	private int clienteCaso;
	private List<FacturaCabecera> listaFacturas;

	@Inject
	private FacturaCabeceraService facturaCabeceraService;

	@PostConstruct
	private void init() {

		facturaCabecera = new FacturaCabecera();
		facturaDetalle = new FacturaDetalle();
		listaDetalles = new ArrayList<FacturaDetalle>();
		listaFacturas = new ArrayList<FacturaCabecera>();
		facturaCabecera.setCabeceraFecha(Calendar.getInstance().getTime());
	}

	public FacturaCabecera getFacturaCabecera() {
		return facturaCabecera;
	}

	public void setFacturaCabecera(FacturaCabecera facturaCabecera) {
		this.facturaCabecera = facturaCabecera;
	}

	public FacturaDetalle getFacturaDetalle() {
		return facturaDetalle;
	}

	public void setFacturaDetalle(FacturaDetalle facturaDetalle) {
		this.facturaDetalle = facturaDetalle;
	}

	public List<FacturaDetalle> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<FacturaDetalle> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public ReciboCabecera getReciboCabecera() {
		return reciboCabecera;
	}

	public void setReciboCabecera(ReciboCabecera reciboCabecera) {
		this.reciboCabecera = reciboCabecera;
	}

	public List<FacturaCabecera> getListaFacturas() {
		return listaFacturas;
	}

	public void setListaFacturas(List<FacturaCabecera> listaFacturas) {
		this.listaFacturas = listaFacturas;
	}

	public void recivirCliente(
			@Observes(notifyObserver = Reception.IF_EXISTS) Cliente cliente) {

		switch (clienteCaso) {
		case 4: {
			facturaCabecera.setClienteFactura(cliente);
			RequestContext.getCurrentInstance().update("form:clienteFactura");
			break;

		}
		}
	}

	public void addFactura() {

		facturaCabeceraService.create(facturaCabecera);
		init();

	}

	public void generarFactura() {

		clienteCaso = 4;
		List<ReciboDetalle> listaDetalle = reciboCabecera.getListaDetalles();
		for (ReciboDetalle detalle : listaDetalle) {
			System.out.println("CLIENTE"
					+ detalle.getDetalleAlojamiento()
							.getAlojamientoHabitacion().getHabitacionCodigo());
			facturaCabecera.setClienteFactura(detalle.getDetalleAlojamiento()
					.getAlojamientoHospedaje().getCliente());
			facturaDetalle.setDetalleDescripcion(detalle
					.getDetalleAlojamiento().getAlojamientoHabitacion()
					.getHabitacionCodigo()
					+ "-"
					+ detalle.getDetalleAlojamiento()
							.getAlojamientoHabitacion().getTipoHabitacion()
							.getTipoHabitacionCategoria());
			facturaDetalle.setDetallePrecio(detalle.getDetalleAlojamiento()
					.getAlojamientoHabitacion().getHabitacionPrecioDia());
			facturaDetalle.setDetalleCantidad(detalle.getDetalleCantidad());
			facturaDetalle.setDetalleDescuento(detalle.getDetalleAlojamiento()
					.getAlojamientoDescuento());
			facturaDetalle.setDetalleAdicional(detalle.getDetalleAlojamiento()
					.getAlojamientoAdicional());
			facturaDetalle.calcularTotal();
			facturaDetalle.setHospedaje(detalle.getDetalleAlojamiento()
					.getAlojamientoHospedaje());
			facturaDetalle.setCabecera(facturaCabecera);
			listaDetalles.add(facturaDetalle);
			facturaDetalle = new FacturaDetalle();
			facturaCabecera.setListaDetalle(listaDetalles);

		}
		facturaCabecera.calcularTotal();

	}

	public void imprimirFactura(ActionEvent actionEvent) throws Exception {

		/*
		 * Map<String, Object> parametros = new HashMap<String, Object>();
		 * parametros.put("bean", facturaCabecera.getCabeceraNumero());
		 * 
		 * File jasper = new File(FacesContext.getCurrentInstance()
		 * .getExternalContext()
		 * .getRealPath("/WEB-INF/reportes/factura.jasper"));
		 * 
		 * byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(),
		 * null, new JRBeanCollectionDataSource(getListaDetalles()));
		 * HttpServletResponse response = (HttpServletResponse) FacesContext
		 * .getCurrentInstance().getExternalContext().getResponse();
		 * response.setContentType("application/pdf");
		 * response.setContentLength(bytes.length); ServletOutputStream
		 * outStream = response.getOutputStream(); outStream.write(bytes, 0,
		 * bytes.length); outStream.flush(); outStream.close();
		 * 
		 * FacesContext.getCurrentInstance().responseComplete();
		 */
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("txtUsuario", "usuario");

		File jasper = new File(FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRealPath("/WEB-INF/reportes/factura.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(
				jasper.getPath(), null,
				new JRBeanCollectionDataSource(this.getListaDetalles()));

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition",
				"attachment; filename=factura.pdf");
		ServletOutputStream stream = response.getOutputStream();

		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();

	}

	public void cancelarFactura() {

		init();

	}
}
