package org.hotelLeon.dominio.hospedaje;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TIPOHABITACION")
public class TipoHabitacion {

	private int tipoHabitacionId;
	private String tipoHabitacionCategoria;

	// private List<Habitacion> listaHabitaciones;

	/*------------------GET Y SET-------------------------*/
	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "THAB_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getTipoHabitacionId() {
		return tipoHabitacionId;
	}

	public void setTipoHabitacionId(int tipoHabitacionId) {
		this.tipoHabitacionId = tipoHabitacionId;
	}

	@NotNull(message = "Camo requerido")
	public String getTipoHabitacionCategoria() {
		return tipoHabitacionCategoria;
	}

	public void setTipoHabitacionCategoria(String tipoHabitacionCategoria) {
		this.tipoHabitacionCategoria = tipoHabitacionCategoria;
	}

	/*
	 * @OneToMany(mappedBy = "tipoHabitacion", cascade = CascadeType.ALL, fetch
	 * = FetchType.EAGER) public List<Habitacion> getListaHabitaciones() {
	 * return listaHabitaciones; }
	 * 
	 * public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
	 * this.listaHabitaciones = listaHabitaciones; }
	 */

	public TipoHabitacion() {

	}

}
