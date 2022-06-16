package org.hotelLeon.dominio.login;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hotelLeon.dominio.persona.UsuarioSistema;

@Entity
@Table(name = "GRUPO_USUARIO")
public class GrupoUsuario {

	private int grupoUsuarioId;
	private UsuarioSistema usuarioSistema;
	private Grupo grupo;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "GU_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getGrupoUsuarioId() {
		return grupoUsuarioId;
	}

	public void setGrupoUsuarioId(int grupoUsuarioId) {
		this.grupoUsuarioId = grupoUsuarioId;
	}

	@ManyToOne
	@JoinColumn(name = "GU_FK_USU")
	public UsuarioSistema getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(UsuarioSistema usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

	@ManyToOne
	@JoinColumn(name = "GU_FK_GU")
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public GrupoUsuario() {

	}

}
