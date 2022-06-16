package org.hotelLeon.dominio.persona;

import java.util.List;

import javax.persistence.*;

import org.hotelLeon.dominio.servicios.Requerimiento;

@Entity
@DiscriminatorValue(value = "CLIENTE")
public class Cliente extends Persona {

	private String clienteNacionalidad;

	public String getClienteNacionalidad() {
		return clienteNacionalidad;
	}

	public void setClienteNacionalidad(String clienteNacionalidad) {
		this.clienteNacionalidad = clienteNacionalidad;
	}

	public Cliente() {

	}

}
