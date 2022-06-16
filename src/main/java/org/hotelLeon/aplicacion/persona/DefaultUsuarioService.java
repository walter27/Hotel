package org.hotelLeon.aplicacion.persona;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.persona.UsuarioRepository;
import org.hotelLeon.dominio.persona.UsuarioSistema;

@Stateless
public class DefaultUsuarioService implements UsuarioService {

	@Inject
	UsuarioRepository usuarioRepository;

	@Override
	public void create(UsuarioSistema usuario) {

		usuarioRepository.create(usuario);
	}

	@Override
	public void delete(UsuarioSistema usuario) {

		usuarioRepository.delete(usuario);
	}

	@Override
	public void update(UsuarioSistema usuario) {

		usuarioRepository.update(usuario);
	}

	@Override
	public List<UsuarioSistema> findAll() {

		return usuarioRepository.findAll();
	}

}
