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
import org.hotelLeon.dominio.persona.Cliente;

@Entity
@Table(name = "FACTURA")
public class FacturaCabecera {

	private int cabeceraId;
	private int cabeceraNumero;
	private Date cabeceraFecha;
	private BigDecimal cabeceraTotal;
	private List<FacturaDetalle> listaDetalle;
	private Cliente clienteFactura;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "FAC_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getCabeceraId() {
		return cabeceraId;
	}

	public void setCabeceraId(int cabeceraId) {
		this.cabeceraId = cabeceraId;
	}

	public int getCabeceraNumero() {
		return cabeceraNumero;
	}

	public void setCabeceraNumero(int cabeceraNumero) {
		this.cabeceraNumero = cabeceraNumero;
	}

	public Date getCabeceraFecha() {
		return cabeceraFecha;
	}

	public void setCabeceraFecha(Date cabeceraFecha) {
		this.cabeceraFecha = cabeceraFecha;
	}

	public BigDecimal getCabeceraTotal() {
		return cabeceraTotal;
	}

	public void setCabeceraTotal(BigDecimal cabeceraTotal) {
		this.cabeceraTotal = cabeceraTotal.setScale(2, RoundingMode.HALF_UP);
	}

	@OneToMany(mappedBy = "cabecera", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<FacturaDetalle> getListaDetalle() {
		return listaDetalle;
	}

	public void setListaDetalle(List<FacturaDetalle> listaDetalle) {
		this.listaDetalle = listaDetalle;
	}

	@ManyToOne
	@JoinColumn(name = "FAC_CLI_FK")
	public Cliente getClienteFactura() {
		return clienteFactura;
	}

	public void setClienteFactura(Cliente clienteFactura) {
		this.clienteFactura = clienteFactura;
	}

	public void calcularTotal() {

		this.cabeceraTotal = listaDetalle.stream()
				.map(FacturaDetalle::getDetalleTotal).reduce(BigDecimal::add)
				.get();

	}

	public FacturaCabecera() {

	}

}
