package org.hotelLeon.dominio.persona;

import java.util.List;

public interface ClienteRepository {
	
	public void create(Cliente cliente);

	public void update(Cliente cliente);

	public void delete(Cliente cliente);

	public List<Cliente> findAll();

}
