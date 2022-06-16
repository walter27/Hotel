package org.hotelLeon.dominio.hospedaje;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hotelLeon.dominio.reserva.Reserva;
import org.hotelLeon.dominio.servicios.Gobernanta;

@Entity
@Table(name = "HABITACION")
public class Habitacion {

	private int habitacionId;
	private String habitacionCodigo;
	private String habitacionDescripcion;
	private BigDecimal habitacionPrecioDia;
	private HabitacionEdificio habitacionEdificio;
	private HabitacionEstado habitacionEstado;
	private TipoHabitacion tipoHabitacion;
	private boolean habitacionboolean;
	private List<Reserva> habitacionReservas;
	private List<Gobernanta> listaGobernanta;
	private boolean limpiezaBoolean;
	private Arriendo tipoArriendo;
	private int cantidadArriendo = 0;
	private EstadoLimpieza estadoLimpieza;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "HAB_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getHabitacionId() {
		return habitacionId;
	}

	public void setHabitacionId(int habitacionId) {
		this.habitacionId = habitacionId;
	}

	@NotNull(message = "Numero requerido")
	public String getHabitacionCodigo() {
		return habitacionCodigo;
	}

	public void setHabitacionCodigo(String habitacionCodigo) {
		this.habitacionCodigo = habitacionCodigo;
	}

	public String getHabitacionDescripcion() {
		return habitacionDescripcion;
	}

	public void setHabitacionDescripcion(String habitacionDescripcion) {
		this.habitacionDescripcion = habitacionDescripcion;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "HAB_THA_FK")
	public TipoHabitacion getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	@NotNull(message = "Precio requerido")
	public BigDecimal getHabitacionPrecioDia() {
		return habitacionPrecioDia;
	}

	public void setHabitacionPrecioDia(BigDecimal habitacionPrecioDia) {
		this.habitacionPrecioDia = habitacionPrecioDia.setScale(2,
				RoundingMode.HALF_UP);
	}

	public HabitacionEstado getHabitacionEstado() {
		return habitacionEstado;
	}

	public void setHabitacionEstado(HabitacionEstado habitacionEstado) {
		this.habitacionEstado = habitacionEstado;
	}

	@NotNull(message = "Edificio requerido")
	public HabitacionEdificio getHabitacionEdificio() {
		return habitacionEdificio;
	}

	public void setHabitacionEdificio(HabitacionEdificio habitacionEdificio) {
		this.habitacionEdificio = habitacionEdificio;
	}

	public boolean isHabitacionboolean() {
		return habitacionboolean;
	}

	public void setHabitacionboolean(boolean habitacionboolean) {
		this.habitacionboolean = habitacionboolean;
	}

	@ManyToMany(mappedBy = "reservaHabitaciones", cascade = {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	public List<Reserva> getHabitacionReservas() {
		return habitacionReservas;
	}

	public void setHabitacionReservas(List<Reserva> habitacionReservas) {
		this.habitacionReservas = habitacionReservas;
	}

	@ManyToMany(mappedBy = "listaHabitaciones", cascade = { CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	public List<Gobernanta> getListaGobernanta() {
		return listaGobernanta;
	}

	public void setListaGobernanta(List<Gobernanta> listaGobernanta) {
		this.listaGobernanta = listaGobernanta;
	}

	public boolean isLimpiezaBoolean() {
		return limpiezaBoolean;
	}

	public void setLimpiezaBoolean(boolean limpiezaBoolean) {
		this.limpiezaBoolean = limpiezaBoolean;
	}

	@NotNull(message = "Tipo de arriendo requerido")
	public Arriendo getTipoArriendo() {
		return tipoArriendo;
	}

	public void setTipoArriendo(Arriendo tipoArriendo) {
		this.tipoArriendo = tipoArriendo;
	}

	public int getCantidadArriendo() {
		return cantidadArriendo;
	}

	public void setCantidadArriendo(int cantidadArriendo) {
		this.cantidadArriendo = cantidadArriendo;
	}

	public EstadoLimpieza getEstadoLimpieza() {
		return estadoLimpieza;
	}

	public void setEstadoLimpieza(EstadoLimpieza estadoLimpieza) {
		this.estadoLimpieza = estadoLimpieza;
	}

	public Habitacion() {

	}

}
