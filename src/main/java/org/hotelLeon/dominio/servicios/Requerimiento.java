package org.hotelLeon.dominio.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.beanutils.locale.converters.BigDecimalLocaleConverter;
import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.persona.Cliente;

@Entity
@Table(name = "REQUERIMIENTO")
public class Requerimiento {

	private int requerimientoId;
	private int requerimientoCantidad;
	private BigDecimal requerimientoTotal;
	private Producto requerimientoProducto;
	private Consumo consumo;
	private BigDecimal requerimientoPrecio;
	private boolean eliminarRequerimiento;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "REQ_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getRequerimientoId() {
		return requerimientoId;
	}

	public void setRequerimientoId(int requerimientoId) {
		this.requerimientoId = requerimientoId;
	}

	public int getRequerimientoCantidad() {
		return requerimientoCantidad;
	}

	public void setRequerimientoCantidad(int requerimientoCantidad) {
		this.requerimientoCantidad = requerimientoCantidad;
	}

	public BigDecimal getRequerimientoTotal() {
		return requerimientoTotal;
	}

	public void setRequerimientoTotal(BigDecimal requerimientoTotal) {
		this.requerimientoTotal = requerimientoTotal.setScale(2,
				RoundingMode.HALF_UP);
	}

	@ManyToOne
	@JoinColumn(name = "REQ_PRO_FK", referencedColumnName = "PRO_ID_PR")
	public Producto getRequerimientoProducto() {
		return requerimientoProducto;
	}

	public void setRequerimientoProducto(Producto requerimientoProducto) {
		this.requerimientoProducto = requerimientoProducto;
	}

	@ManyToOne
	@JoinColumn(name = "REQ_CON_FK")
	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	public BigDecimal getRequerimientoPrecio() {
		return requerimientoPrecio;
	}

	public void setRequerimientoPrecio(BigDecimal requerimientoPrecio) {
		this.requerimientoPrecio = requerimientoPrecio.setScale(2,
				RoundingMode.HALF_UP);
	}

	public boolean isEliminarRequerimiento() {
		return eliminarRequerimiento;
	}

	public void setEliminarRequerimiento(boolean eliminarRequerimiento) {
		this.eliminarRequerimiento = eliminarRequerimiento;
	}

	public void calcularTotal() {

		BigDecimal resultRounded = requerimientoPrecio.multiply(
				new BigDecimal(requerimientoCantidad)).setScale(2,
				BigDecimal.ROUND_HALF_DOWN);
		this.requerimientoTotal = resultRounded;

	}

	public Requerimiento() {
	}

}
