package org.hotelLeon.dominio.persona;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "PERSONA")
@DiscriminatorColumn(name = "TIPO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Persona {

	private int personaId;
	private String personaCedula;
	private String personaApellidos;
	private String personaNombres;
	private String personaDireccion;
	private String personaTelefono;
	private String personaCorreo;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "PER_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getPersonaId() {
		return personaId;
	}

	public void setPersonaId(int personaId) {
		this.personaId = personaId;
	}

	public String getPersonaCedula() {
		return personaCedula;
	}

	public void setPersonaCedula(String personaCedula) {
		this.personaCedula = personaCedula;
	}

	@NotNull(message = "Nombre requerido")
	public String getPersonaApellidos() {
		return personaApellidos;
	}

	public void setPersonaApellidos(String personaApellidos) {
		this.personaApellidos = personaApellidos;
	}

	@NotNull(message = "Apellido requerido")
	public String getPersonaNombres() {
		return personaNombres;
	}

	public void setPersonaNombres(String personaNombres) {
		this.personaNombres = personaNombres;
	}

	public String getPersonaDireccion() {
		return personaDireccion;
	}

	public void setPersonaDireccion(String personaDireccion) {
		this.personaDireccion = personaDireccion;
	}

	public String getPersonaTelefono() {
		return personaTelefono;
	}

	public void setPersonaTelefono(String personaTelefono) {
		this.personaTelefono = personaTelefono;
	}

	@Email(message = "Correo invalido")
	public String getPersonaCorreo() {
		return personaCorreo;
	}

	public void setPersonaCorreo(String personaCorreo) {
		this.personaCorreo = personaCorreo;
	}

	public Persona() {

	}

}
