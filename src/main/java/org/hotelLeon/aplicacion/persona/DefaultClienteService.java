package org.hotelLeon.aplicacion.persona;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hotelLeon.dominio.persona.Cliente;
import org.hotelLeon.dominio.persona.ClienteRepository;

@Stateless
public class DefaultClienteService implements ClienteService {

	@Inject
	ClienteRepository clienteRepository;

	@Override
	public void create(Cliente cliente) {

		clienteRepository.create(cliente);
	}

	@Override
	public void delete(Cliente cliente) {

		clienteRepository.delete(cliente);
	}

	@Override
	public void update(Cliente cliente) {

		clienteRepository.update(cliente);
	}

	@Override
	public List<Cliente> findAll() {

		return clienteRepository.findAll();
	}

}
