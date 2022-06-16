package org.hotelLeon.dominio.hospedaje;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hotelLeon.dominio.persona.Cliente;

@Entity
@Table(name = "ALOJAMIENTO")
public class Alojamiento {

	private int alojamientoId;
	private Cliente alojamientoCliente;
	private Habitacion alojamientoHabitacion;
	private Hospedaje alojamientoHospedaje;
	private BigDecimal alojamientoDescuento;
	private BigDecimal alojamientoAdicional;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ALJ_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getAlojamientoId() {
		return alojamientoId;
	}

	public void setAlojamientoId(int alojamientoId) {
		this.alojamientoId = alojamientoId;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "CLI_ALJ_FK")
	public Cliente getAlojamientoCliente() {
		return alojamientoCliente;
	}

	public void setAlojamientoCliente(Cliente alojamientoCliente) {
		this.alojamientoCliente = alojamientoCliente;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "HAB_ALJ_FK")
	public Habitacion getAlojamientoHabitacion() {
		return alojamientoHabitacion;
	}

	public void setAlojamientoHabitacion(Habitacion alojamientoHabitacion) {
		this.alojamientoHabitacion = alojamientoHabitacion;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "HOS_ALJ_FK")
	public Hospedaje getAlojamientoHospedaje() {
		return alojamientoHospedaje;
	}

	public void setAlojamientoHospedaje(Hospedaje alojamientoHospedaje) {
		this.alojamientoHospedaje = alojamientoHospedaje;
	}

	public BigDecimal getAlojamientoDescuento() {
		return alojamientoDescuento;
	}

	public void setAlojamientoDescuento(BigDecimal alojamientoDescuento) {

		if (alojamientoDescuento != null) {
			this.alojamientoDescuento = alojamientoDescuento.setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.alojamientoDescuento = new BigDecimal(0.00);

		}
	}

	public BigDecimal getAlojamientoAdicional() {
		return alojamientoAdicional;
	}

	public void setAlojamientoAdicional(BigDecimal alojamientoAdicional) {
		if (alojamientoAdicional != null) {
			this.alojamientoAdicional = alojamientoAdicional.setScale(2,
					RoundingMode.HALF_UP);
		} else {

			this.alojamientoAdicional = new BigDecimal(0.00);

		}
	}

	public Alojamiento() {

	}

}
