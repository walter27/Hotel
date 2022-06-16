package org.hotelLeon.dominio.servicios;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SERVICIO")
public class Servicio {

	private int servicioId;
	private String servicioNombre;
	private List<Producto> listaProducto;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "SER_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getServicioId() {
		return servicioId;
	}

	public void setServicioId(int servicioId) {
		this.servicioId = servicioId;
	}

	@NotNull(message="Campo requerido")
	public String getServicioNombre() {
		return servicioNombre;
	}

	public void setServicioNombre(String servicioNombre) {
		this.servicioNombre = servicioNombre;
	}

	@OneToMany(mappedBy = "productoServicio", cascade = CascadeType.ALL)
	public List<Producto> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<Producto> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public Servicio() {

	}

}
