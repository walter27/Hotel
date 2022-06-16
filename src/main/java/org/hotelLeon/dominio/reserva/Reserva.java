package org.hotelLeon.dominio.reserva;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;

import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.persona.Cliente;

@Entity
@Table(name = "RESERVA")
public class Reserva {

	private int reservaId;
	private int reservaNumero;
	private Date reservaFecha;
	private Date reservaEntrada;
	private Date reservaSalida;
	private ReservaEstado reservaEstado;
	private List<Habitacion> reservaHabitaciones;
	private Cliente clienteReserva;
	private int reservaDias;
	private boolean checkInReserva;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "RES_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getReservaId() {
		return reservaId;
	}

	public void setReservaId(int reservaId) {
		this.reservaId = reservaId;
	}

	public int getReservaNumero() {
		return reservaNumero;
	}

	public void setReservaNumero(int reservaNumero) {
		this.reservaNumero = reservaNumero;
	}

	public Date getReservaEntrada() {
		return reservaEntrada;
	}

	public void setReservaEntrada(Date reservaEntrada) {
		this.reservaEntrada = reservaEntrada;
	}

	@Future(message = "Fecha incorreta")
	public Date getReservaSalida() {
		return reservaSalida;
	}

	public void setReservaSalida(Date reservaSalida) {
		this.reservaSalida = reservaSalida;
	}

	public ReservaEstado getReservaEstado() {
		return reservaEstado;
	}

	public void setReservaEstado(ReservaEstado reservaEstado) {
		this.reservaEstado = reservaEstado;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "reserva_habitacion", joinColumns = { @JoinColumn(name = "IdReserva") }, inverseJoinColumns = { @JoinColumn(name = "IdHabitacion") })
	public List<Habitacion> getReservaHabitaciones() {
		return reservaHabitaciones;
	}

	public void setReservaHabitaciones(List<Habitacion> reservaHabitaciones) {
		this.reservaHabitaciones = reservaHabitaciones;
	}

	public Date getReservaFecha() {
		return reservaFecha;
	}

	@ManyToOne
	@JoinColumn(name = "CLI_RES_FK")
	public Cliente getClienteReserva() {
		return clienteReserva;
	}

	public void setClienteReserva(Cliente clienteReserva) {
		this.clienteReserva = clienteReserva;
	}

	public void setReservaFecha(Date reservaFecha) {
		this.reservaFecha = reservaFecha;
	}

	public int getReservaDias() {
		return reservaDias;
	}

	public void setReservaDias(int reservaDias) {
		this.reservaDias = reservaDias;
	}

	public boolean isCheckInReserva() {
		return checkInReserva;
	}

	public void setCheckInReserva(boolean checkInReserva) {
		this.checkInReserva = checkInReserva;
	}

	public void calcularDias() {

		long diferencia = reservaSalida.getTime() - reservaEntrada.getTime();
		float dias = (diferencia / (1000 * 60 * 60 * 24));
		this.reservaDias = Math.round(dias);
		System.out.println("diassss" + reservaDias);

	}

	public Reserva() {

	}

}
