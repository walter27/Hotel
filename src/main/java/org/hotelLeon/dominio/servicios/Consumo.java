package org.hotelLeon.dominio.servicios;

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
@Table(name = "CONSUMO")
public class Consumo {

	private int consumoId;
	private Date consumoFecha;
	private Cliente consumoCliente;
	private BigDecimal total;
	private List<Requerimiento> listaRequerimiento;
	private boolean consumoReporte;
	private boolean consumoHospedaje;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CON_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getConsumoId() {
		return consumoId;
	}

	public void setConsumoId(int consumoId) {
		this.consumoId = consumoId;
	}

	public Date getConsumoFecha() {
		return consumoFecha;
	}

	public void setConsumoFecha(Date consumoFecha) {
		this.consumoFecha = consumoFecha;
	}

	@ManyToOne
	@JoinColumn(name = "CON_CLI_FK")
	public Cliente getConsumoCliente() {
		return consumoCliente;
	}

	public void setConsumoCliente(Cliente consumoCliente) {
		this.consumoCliente = consumoCliente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total.setScale(2, RoundingMode.HALF_UP);
	}

	@OneToMany(mappedBy = "consumo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<Requerimiento> getListaRequerimiento() {
		return listaRequerimiento;
	}

	public void setListaRequerimiento(List<Requerimiento> listaRequerimiento) {
		this.listaRequerimiento = listaRequerimiento;
	}

	public boolean isConsumoReporte() {
		return consumoReporte;
	}

	public void setConsumoReporte(boolean consumoReporte) {
		this.consumoReporte = consumoReporte;
	}

	public boolean isConsumoHospedaje() {
		return consumoHospedaje;
	}

	public void setConsumoHospedaje(boolean consumoHospedaje) {
		this.consumoHospedaje = consumoHospedaje;
	}

	public void calcularTotal() {

		this.total = listaRequerimiento.stream()
				.map(Requerimiento::getRequerimientoTotal)
				.reduce(BigDecimal::add).get();

	}

	public Consumo() {
	}

}
