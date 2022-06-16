package org.hotelLeon.dominio.factura;

import java.math.BigDecimal;
import java.math.RoundingMode;
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


@Entity
@Table(name = "FACTURADETALLE")
public class FacturaDetalle {

	private int detalleId;
	private BigDecimal detallePrecio;
	private int detalleCantidad;
	private BigDecimal detalleTotal;
	private BigDecimal detalleDescuento;
	private BigDecimal detalleAdicional;
	private FacturaCabecera cabecera;
	private Hospedaje hospedaje;
	private String detalleDescripcion;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "FAD_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getDetalleId() {
		return detalleId;
	}

	public void setDetalleId(int detalleId) {
		this.detalleId = detalleId;
	}

	public int getDetalleCantidad() {
		return detalleCantidad;
	}

	public void setDetalleCantidad(int detalleCantidad) {
		this.detalleCantidad = detalleCantidad;
	}

	@ManyToOne
	@JoinColumn(name = "FAD_FAC_FK")
	public FacturaCabecera getCabecera() {
		return cabecera;
	}

	public void setCabecera(FacturaCabecera cabecera) {
		this.cabecera = cabecera;
	}

	@ManyToOne
	@JoinColumn(name = "FAD_HOS_FK", referencedColumnName = "HOS_ID_PR")
	public Hospedaje getHospedaje() {
		return hospedaje;
	}

	public void setHospedaje(Hospedaje hospedaje) {
		this.hospedaje = hospedaje;
	}

	public String getDetalleDescripcion() {
		return detalleDescripcion;
	}

	public void setDetalleDescripcion(String detalleDescripcion) {
		this.detalleDescripcion = detalleDescripcion;
	}

	public BigDecimal getDetallePrecio() {
		return detallePrecio;
	}

	public void setDetallePrecio(BigDecimal detallePrecio) {
		this.detallePrecio = detallePrecio.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getDetalleTotal() {
		return detalleTotal;
	}

	public void setDetalleTotal(BigDecimal detalleTotal) {
		this.detalleTotal = detalleTotal.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getDetalleDescuento() {
		return detalleDescuento;
	}

	public void setDetalleDescuento(BigDecimal detalleDescuento) {
		this.detalleDescuento = detalleDescuento.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getDetalleAdicional() {
		return detalleAdicional;
	}

	public void setDetalleAdicional(BigDecimal detalleAdicional) {
		this.detalleAdicional = detalleAdicional.setScale(2, RoundingMode.HALF_UP);
	}

	public void calcularTotal() {

		this.detalleTotal = (((detallePrecio.multiply(new BigDecimal(
				detalleCantidad)))).add(detalleAdicional))
				.subtract(detalleDescuento);
	}

	public FacturaDetalle() {

	}

}
