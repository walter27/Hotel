package org.hotelLeon.dominio.login;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GRUPO")
public class Grupo {

	private int grupoId;
	private String grupoCodigo;
	private String grupoNombre;
	private List<GrupoUsuario> grupoUsuario;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "GRU_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(int grupoId) {
		this.grupoId = grupoId;
	}

	public String getGrupoCodigo() {
		return grupoCodigo;
	}

	public void setGrupoCodigo(String grupoCodigo) {
		this.grupoCodigo = grupoCodigo;
	}

	public String getGrupoNombre() {
		return grupoNombre;
	}

	public void setGrupoNombre(String grupoNombre) {
		this.grupoNombre = grupoNombre;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo", fetch = FetchType.EAGER)
	public List<GrupoUsuario> getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(List<GrupoUsuario> grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public Grupo() {

	}

}
