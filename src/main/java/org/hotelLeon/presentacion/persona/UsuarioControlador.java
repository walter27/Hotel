package org.hotelLeon.presentacion.persona;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.hotelLeon.aplicacion.login.GrupoService;
import org.hotelLeon.aplicacion.persona.UsuarioService;
import org.hotelLeon.dominio.factura.ReciboCabecera;
import org.hotelLeon.dominio.hospedaje.TipoHabitacion;
import org.hotelLeon.dominio.login.Grupo;
import org.hotelLeon.dominio.login.GrupoUsuario;
import org.hotelLeon.dominio.persona.UsuarioSistema;
import org.primefaces.context.RequestContext;

@Named
@ApplicationScoped
public class UsuarioControlador implements Serializable {

	private UsuarioSistema usuario;
	private Grupo grupo;
	private GrupoUsuario grupoUsuario;
	private List<UsuarioSistema> listaUsuarios;
	private List<UsuarioSistema> filteredUsuarios;
	private UsuarioSistema editarUsuario;
	private List<Grupo> listaGrupos;
	private List<GrupoUsuario> listaGrupoUsuario;

	@Inject
	private UsuarioService usuarioServie;

	@Inject
	private GrupoService grupoServie;

	@PostConstruct
	private void init() {

		usuario = new UsuarioSistema();
		grupo = new Grupo();
		grupoUsuario = new GrupoUsuario();
		listaGrupoUsuario = new ArrayList<GrupoUsuario>();
		listaUsuarios = usuarioServie.findAll();
		listaGrupos = grupoServie.findAll();

	}

	public UsuarioSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioSistema usuario) {
		this.usuario = usuario;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public GrupoUsuario getGrupoUsuario() {
		return grupoUsuario;
	}

	public void setGrupoUsuario(GrupoUsuario grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public List<UsuarioSistema> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UsuarioSistema> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<UsuarioSistema> getFilteredUsuarios() {
		return filteredUsuarios;
	}

	public void setFilteredUsuarios(List<UsuarioSistema> filteredUsuarios) {
		this.filteredUsuarios = filteredUsuarios;
	}

	public UsuarioSistema getEditarUsuario() {
		return editarUsuario;
	}

	public void setEditarUsuario(UsuarioSistema editarUsuario) {
		this.editarUsuario = editarUsuario;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<GrupoUsuario> getListaGrupoUsuario() {
		return listaGrupoUsuario;
	}

	public void setListaGrupoUsuario(List<GrupoUsuario> listaGrupoUsuario) {
		this.listaGrupoUsuario = listaGrupoUsuario;
	}

	public void addUsuario() {

		try {
			grupoUsuario.setGrupo(grupo);
			grupoUsuario.setUsuarioSistema(usuario);
			listaGrupoUsuario.add(grupoUsuario);
			grupo.setGrupoUsuario(listaGrupoUsuario);
			usuario.setGrupoUsuario(listaGrupoUsuario);
			usuarioServie.create(usuario);
			listaUsuarios.add(usuario);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Usuario registrado"));
			usuario = new UsuarioSistema();
			grupo = new Grupo();
			RequestContext.getCurrentInstance().execute("PF('usuario').hide()");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void eliminarUsuario(UsuarioSistema usuario) {

		try {

			usuarioServie.delete(usuario);
			listaUsuarios.remove(usuario);
			usuario = new UsuarioSistema();
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Usuario eliminado"));
		} catch (Exception e) {
			usuario = new UsuarioSistema();
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info",
							"Usuario contien historial"));
		}

	}

	public void editarUsuario() {

		try {
			usuarioServie.update(editarUsuario);
			FacesContext.getCurrentInstance().addMessage(
					"messages",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
							"Usuario editado"));
			usuario = new UsuarioSistema();
			editarUsuario = new UsuarioSistema();
			grupo = new Grupo();
			RequestContext.getCurrentInstance().execute(
					"PF('editarusuario').hide()");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public Grupo getGrupo(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("no id provided");
		}
		for (Grupo grupo : listaGrupos) {
			if (id.equals(grupo.getGrupoId())) {

				return grupo;
			}
		}
		return null;
	}

}
