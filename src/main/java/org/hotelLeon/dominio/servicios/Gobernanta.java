package org.hotelLeon.dominio.servicios;

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

import org.hotelLeon.dominio.hospedaje.Habitacion;
import org.hotelLeon.dominio.persona.Empleado;

@Entity
@Table(name = "GOBERNANTA")
public class Gobernanta {

	private int gobernantaId;
	private Empleado gobernantaEmpleado;
	private Date gobernantaFecha;
	private List<Habitacion> listaHabitaciones;
	private String gobernantaDescripcion;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "GOB_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getGobernantaId() {
		return gobernantaId;
	}

	public void setGobernantaId(int gobernantaId) {
		this.gobernantaId = gobernantaId;
	}

	@ManyToOne
	@JoinColumn(name = "EMP_GOB_FK")
	public Empleado getGobernantaEmpleado() {
		return gobernantaEmpleado;
	}

	public void setGobernantaEmpleado(Empleado gobernantaEmpleado) {
		this.gobernantaEmpleado = gobernantaEmpleado;
	}

	public Date getGobernantaFecha() {
		return gobernantaFecha;
	}

	public void setGobernantaFecha(Date gobernantaFecha) {
		this.gobernantaFecha = gobernantaFecha;
	}

	public String getGobernantaDescripcion() {
		return gobernantaDescripcion;
	}

	public void setGobernantaDescripcion(String gobernantaDescripcion) {
		this.gobernantaDescripcion = gobernantaDescripcion;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "gobernanta_habitacion", joinColumns = { @JoinColumn(name = "IdGobernanata") }, inverseJoinColumns = { @JoinColumn(name = "IdHabitacion") })
	public List<Habitacion> getListaHabitaciones() {
		return listaHabitaciones;
	}

	public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
		this.listaHabitaciones = listaHabitaciones;
	}

	public Gobernanta() {
	}

}
