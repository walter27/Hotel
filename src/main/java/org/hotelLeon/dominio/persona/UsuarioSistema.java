package org.hotelLeon.dominio.persona;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hotelLeon.dominio.login.GrupoUsuario;

@Entity
@DiscriminatorValue(value = "USUARIO")
public class UsuarioSistema extends Persona {

	private String usuarioSistemaUsuario;
	private String usuarioSistemaContrasena;
	private List<GrupoUsuario> grupoUsuario;

	@NotNull(message = "Usuario requerido")
	public String getUsuarioSistemaUsuario() {
		return usuarioSistemaUsuario;
	}

	public void setUsuarioSistemaUsuario(String usuarioSistemaUsuario) {
		this.usuarioSistemaUsuario = usuarioSistemaUsuario;
	}

	@NotNull(message = "Contraseña requerida")
	public String getUsuarioSistemaContrasena() {
		return usuarioSistemaContrasena;
	}

	public void setUsuarioSistemaContrasena(String usuarioSistemaContrasena) {
		this.usuarioSistemaContrasena = usuarioSistemaContrasena;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioSistema", fetch = FetchType.EAGER)
	public List<GrupoUsuario> getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(List<GrupoUsuario> grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public UsuarioSistema() {

	}
}
