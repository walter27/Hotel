package org.hotelLeon.dominio.login;

import java.util.List;

public interface GrupoRepository {

	public void create(Grupo grupo);

	public void update(Grupo grupo);

	public void delete(Grupo grupo);

	public List<Grupo> findAll();

}
