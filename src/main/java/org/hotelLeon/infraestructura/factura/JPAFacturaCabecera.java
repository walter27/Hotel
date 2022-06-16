package org.hotelLeon.infraestructura.factura;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hotelLeon.dominio.factura.FacturaCabecera;
import org.hotelLeon.dominio.factura.FacturaCabeceraRepository;

@ApplicationScoped
public class JPAFacturaCabecera implements FacturaCabeceraRepository{

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(FacturaCabecera facturaCabecera) {

		em.persist(facturaCabecera);
	}

	@Override
	public void delete(FacturaCabecera facturaCabecera) {
		
		FacturaCabecera facturaCabecera1=em.find(FacturaCabecera.class, facturaCabecera.getCabeceraId());
		em.remove(facturaCabecera1);
		em.flush();
	}

	@Override
	public void update(FacturaCabecera facturaCabecera) {

		em.merge(facturaCabecera);
	}

}
