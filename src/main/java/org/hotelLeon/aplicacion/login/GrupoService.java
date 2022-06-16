package org.hotelLeon.aplicacion.login;

import java.util.List;

import org.hotelLeon.dominio.login.Grupo;

public interface GrupoService {
	
	public void create(Grupo grupo);

	public void update(Grupo grupo);

	public void delete(Grupo grupo);

	public List<Grupo> findAll();
	

}
