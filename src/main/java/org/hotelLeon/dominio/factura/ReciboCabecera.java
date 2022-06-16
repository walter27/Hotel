package org.hotelLeon.dominio.factura;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.dominio.servicios.Requerimiento;

@Entity
@Table(name = "RECIBOCABECERA")
public class ReciboCabecera {

	private int reciboCabeceraId;
	private int reciboNumero;
	private List<ReciboDetalle> listaDetalles;
	private Cliente clienteRecibo;
	private List<Abono> listaAbonos;
	private boolean reciboCabeceraCheckOut;
	private BigDecimal reciboCabeceraCobrar;
	private BigDecimal reciboCabeceraMontoTotal;
	private BigDecimal reciboCabeceraAlojamientosTotal;
	private BigDecimal reciboCabeceraTotal;
	private boolean reciboReporte;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "REC_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getReciboCabeceraId() {
		return reciboCabeceraId;
	}

	public void setReciboCabeceraId(int reciboCabeceraId) {
		this.reciboCabeceraId = reciboCabeceraId;
	}

	@OneToMany(mappedBy = "reciboCabecera", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<ReciboDetalle> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<ReciboDetalle> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public int getReciboNumero() {
		return reciboNumero;
	}

	public void setReciboNumero(int reciboNumero) {
		this.reciboNumero = reciboNumero;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "REC_CLI_FK")
	public Cliente getClienteRecibo() {
		return clienteRecibo;
	}

	public void setClienteRecibo(Cliente clienteRecibo) {
		this.clienteRecibo = clienteRecibo;
	}

	@OneToMany(mappedBy = "abonoReciboCabecera", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<Abono> getListaAbonos() {
		return listaAbonos;
	}

	public void setListaAbonos(List<Abono> listaAbonos) {
		this.listaAbonos = listaAbonos;
	}

	public BigDecimal getReciboCabeceraAlojamientosTotal() {
		return reciboCabeceraAlojamientosTotal;
	}

	public void setReciboCabeceraAlojamientosTotal(
			BigDecimal reciboCabeceraAlojamientosTotal) {
		this.reciboCabeceraAlojamientosTotal = reciboCabeceraAlojamientosTotal
				.setScale(2, RoundingMode.HALF_UP);
	}

	public boolean isReciboCabeceraCheckOut() {
		return reciboCabeceraCheckOut;
	}

	public void setReciboCabeceraCheckOut(boolean reciboCabeceraCheckOut) {
		this.reciboCabeceraCheckOut = reciboCabeceraCheckOut;
	}

	public boolean isReciboReporte() {
		return reciboReporte;
	}

	public void setReciboReporte(boolean reciboReporte) {
		this.reciboReporte = reciboReporte;
	}

	public BigDecimal getReciboCabeceraCobrar() {
		return reciboCabeceraCobrar;
	}

	public void setReciboCabeceraCobrar(BigDecimal reciboCabeceraCobrar) {
		this.reciboCabeceraCobrar = reciboCabeceraCobrar.setScale(2,
				RoundingMode.HALF_UP);
	}

	public BigDecimal getReciboCabeceraMontoTotal() {
		return reciboCabeceraMontoTotal;
	}

	public void setReciboCabeceraMontoTotal(BigDecimal reciboCabeceraMontoTotal) {
		this.reciboCabeceraMontoTotal = reciboCabeceraMontoTotal.setScale(2,
				RoundingMode.HALF_UP);
	}

	public BigDecimal getReciboCabeceraTotal() {
		return reciboCabeceraTotal;
	}

	public void setReciboCabeceraTotal(BigDecimal reciboCabeceraTotal) {
		this.reciboCabeceraTotal = reciboCabeceraTotal.setScale(2,
				RoundingMode.HALF_UP);
	}

	public void calcularMontoTotal() {
		if (listaAbonos.isEmpty()) {
			this.reciboCabeceraMontoTotal = new BigDecimal(0.00);

		} else {

			this.reciboCabeceraMontoTotal = listaAbonos.stream()
					.map(Abono::getAbonoMonto).reduce(BigDecimal::add).get();
		}
	}

	public void calcularCobrar() {

		this.reciboCabeceraCobrar = reciboCabeceraTotal
				.subtract(reciboCabeceraMontoTotal);
		System.out.println("cobrar" + reciboCabeceraCobrar);

	}

	public void cobrarPrimero(BigDecimal abono) {

		this.reciboCabeceraCobrar = reciboCabeceraTotal.subtract(abono);
		System.out.println("cobrar2222" + reciboCabeceraCobrar);

	}

	public void calcularAlojamientosTotal() {
		if (listaDetalles.isEmpty()) {

			this.reciboCabeceraAlojamientosTotal = new BigDecimal(0.00);
		} else {

			this.reciboCabeceraAlojamientosTotal = listaDetalles.stream()
					.map(ReciboDetalle::getDetalleTotal)
					.reduce(BigDecimal::add).get();

		}
	}

	public void calcularTotal() {

		if (listaDetalles.isEmpty()) {
			this.reciboCabeceraAlojamientosTotal = new BigDecimal(0.00);
			this.reciboCabeceraTotal = reciboCabeceraAlojamientosTotal;
		} else {

			this.reciboCabeceraTotal = reciboCabeceraAlojamientosTotal;
			System.out.println("REPORTE" + reciboCabeceraTotal);

		}
	}

	public ReciboCabecera() {

	}

}
