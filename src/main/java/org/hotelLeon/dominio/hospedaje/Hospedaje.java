package org.hotelLeon.dominio.hospedaje;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotBlank;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.dominio.servicios.Consumo;
import org.hotelLeon.dominio.servicios.Requerimiento;
import org.primefaces.event.SelectEvent;

@Entity
@Table(name = "HOSPEDAJE")
public class Hospedaje {

	private int hospedajeId;
	private Date hospedajeEntrada;
	private Date hospedajeSalida;
	private Cliente cliente;
	private int hospedajeDias = 0;
	private List<Alojamiento> listaAlojamientos;
	private Consumo consumo;
	private boolean hospedajeAgregarConsumos;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "HOS_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getHospedajeId() {
		return hospedajeId;
	}

	public void setHospedajeId(int hospedajeId) {
		this.hospedajeId = hospedajeId;
	}

	public Date getHospedajeEntrada() {
		return hospedajeEntrada;
	}

	public void setHospedajeEntrada(Date hospedajeEntrada) {
		this.hospedajeEntrada = hospedajeEntrada;
	}

	public Date getHospedajeSalida() {
		return hospedajeSalida;
	}

	public void setHospedajeSalida(Date hospedajeSalida) {
		this.hospedajeSalida = hospedajeSalida;
	}

	public int getHospedajeDias() {
		return hospedajeDias;
	}

	public void setHospedajeDias(int hospedajeDias) {
		this.hospedajeDias = hospedajeDias;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "CLI_HOS_FK")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@OneToMany(mappedBy = "alojamientoHospedaje", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<Alojamiento> getListaAlojamientos() {
		return listaAlojamientos;
	}

	public void setListaAlojamientos(List<Alojamiento> listaAlojamientos) {
		this.listaAlojamientos = listaAlojamientos;
	}

	public boolean isHospedajeAgregarConsumos() {
		return hospedajeAgregarConsumos;
	}

	public void setHospedajeAgregarConsumos(boolean hospedajeAgregarConsumos) {
		this.hospedajeAgregarConsumos = hospedajeAgregarConsumos;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "HOS_CON_FK")
	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	public void asignarDias() {

		System.out.println("ENTRADA" + hospedajeEntrada);
		System.out.println("SALIDA" + hospedajeSalida);

		for (Alojamiento alojamiento : listaAlojamientos) {

			if (alojamiento.getAlojamientoHabitacion().getTipoArriendo()
					.equals(Arriendo.DIA)) {
				long diferencia = hospedajeSalida.getTime()
						- hospedajeEntrada.getTime();
				float dias = (diferencia / (1000 * 60 * 60 * 24));
				this.hospedajeDias = Math.round(dias);
				System.out.println("diassss" + hospedajeDias);

			} else {
				if (alojamiento.getAlojamientoHabitacion().getTipoArriendo()
						.equals(Arriendo.HORA)) {

					long diferencia = hospedajeSalida.getTime()
							- hospedajeEntrada.getTime();
					float dias = (diferencia / (1000 * 60 * 60));
					this.hospedajeDias = (Math.round(dias) / alojamiento
							.getAlojamientoHabitacion().getCantidadArriendo());
					System.out.println("HORAS" + hospedajeDias);

				}
			}
		}
	}

	public Hospedaje() {

	}

}
