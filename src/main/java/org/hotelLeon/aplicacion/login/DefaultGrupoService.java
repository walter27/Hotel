package org.hotelLeon.aplicacion.login;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.login.Grupo;
import org.hotelLeon.dominio.login.GrupoRepository;



@Stateless
public class DefaultGrupoService implements GrupoService {
	
	@Inject
	GrupoRepository grupoRepository;

	@Override
	public void create(Grupo grupo) {

		grupoRepository.create(grupo);
	}

	@Override
	public void delete(Grupo grupo) {

		grupoRepository.delete(grupo);
	}

	@Override
	public void update(Grupo grupo) {

		grupoRepository.update(grupo);
	}

	@Override
	public List<Grupo> findAll() {

		return grupoRepository.findAll();
	}

}
