package org.hotelLeon.aplicacion.persona;

import java.util.List;

import org.hotelLeon.dominio.persona.UsuarioSistema;

public interface UsuarioService {
	
	public void create(UsuarioSistema usuario);

	public void update(UsuarioSistema usuario);

	public void delete(UsuarioSistema usuario);

	public List<UsuarioSistema> findAll();
	

}
