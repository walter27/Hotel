package org.hotelLeon.dominio.persona;

import java.util.List;

public interface UsuarioRepository {

	public void create(UsuarioSistema usuario);

	public void update(UsuarioSistema usuario);

	public void delete(UsuarioSistema usuario);

	public List<UsuarioSistema> findAll();

}
