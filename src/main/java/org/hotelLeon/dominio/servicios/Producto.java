package org.hotelLeon.dominio.servicios;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PRODUCTO")
public class Producto {

	private int productoId;
	private String productoNombre;
	private String productoDescripcion;
	private BigDecimal productoPrecio;
	private Servicio productoServicio;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "PRO_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	@NotNull(message = "Producto requerido")
	public String getProductoNombre() {
		return productoNombre;
	}

	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	public String getProductoDescripcion() {
		return productoDescripcion;
	}

	public void setProductoDescripcion(String productoDescripcion) {
		this.productoDescripcion = productoDescripcion;
	}

	@ManyToOne
	@JoinColumn(name = "SER_PRO_FK")
	public Servicio getProductoServicio() {
		return productoServicio;
	}

	@NotNull(message = "Precio requerido")
	public BigDecimal getProductoPrecio() {
		return productoPrecio;
	}

	public void setProductoPrecio(BigDecimal productoPrecio) {
		this.productoPrecio = productoPrecio.setScale(2, RoundingMode.HALF_UP);
	}

	public void setProductoServicio(Servicio productoServicio) {
		this.productoServicio = productoServicio;
	}

	public Producto() {

	}
}
