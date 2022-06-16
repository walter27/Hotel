package org.hotelLeon.aplicacion.persona;

import java.util.List;
import org.hotelLeon.dominio.persona.Cliente;

public interface ClienteService {
	
	public void create(Cliente cliente);

	public void update(Cliente cliente);

	public void delete(Cliente cliente);

	public List<Cliente> findAll();

}
