package org.hotelLeon.dominio.persona;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hotelLeon.dominio.servicios.Gobernanta;

@Entity
@DiscriminatorValue(value = "EMPLEADO")
public class Empleado extends Persona {

	private String empleadoCargo;

	// private List<Habitacion> listasHabitaciones;

	public String getEmpleadoCargo() {
		return empleadoCargo;
	}

	public void setEmpleadoCargo(String empleadoCargo) {
		this.empleadoCargo = empleadoCargo;
	}

	/*
	 * public List<Habitacion> getListasHabitaciones() { return
	 * listasHabitaciones; }
	 * 
	 * public void setListasHabitaciones(List<Habitacion> listasHabitaciones) {
	 * this.listasHabitaciones = listasHabitaciones; }
	 */
	public Empleado() {
	}

}
