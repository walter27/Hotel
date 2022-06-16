package org.hotelLeon.dominio.factura;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hotelLeon.dominio.hospedaje.Alojamiento;
import org.hotelLeon.dominio.hospedaje.Hospedaje;
import org.hotelLeon.dominio.servicios.Requerimiento;

@Entity
@Table(name = "RECIBODETALLE")
public class ReciboDetalle {

	private int detalleid;
	private int detalleCantidad;
	private BigDecimal detalleTotal;
	private ReciboCabecera reciboCabecera;
	private Alojamiento detalleAlojamiento;
	private BigDecimal detalleTotalIngreso;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "RED_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getDetalleid() {
		return detalleid;
	}

	public void setDetalleid(int detalleid) {
		this.detalleid = detalleid;
	}

	public int getDetalleCantidad() {
		return detalleCantidad;
	}

	public void setDetalleCantidad(int detalleCantidad) {
		this.detalleCantidad = detalleCantidad;
	}

	public BigDecimal getDetalleTotal() {
		return detalleTotal;
	}

	public void setDetalleTotal(BigDecimal detalleTotal) {
		this.detalleTotal = detalleTotal.setScale(2, RoundingMode.HALF_UP);
	}

	@ManyToOne
	public ReciboCabecera getReciboCabecera() {
		return reciboCabecera;
	}

	public void setReciboCabecera(ReciboCabecera reciboCabecera) {
		this.reciboCabecera = reciboCabecera;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public Alojamiento getDetalleAlojamiento() {
		return detalleAlojamiento;
	}

	public void setDetalleAlojamiento(Alojamiento detalleAlojamiento) {
		this.detalleAlojamiento = detalleAlojamiento;
	}

	public BigDecimal getDetalleTotalIngreso() {
		return detalleTotalIngreso;
	}

	public void setDetalleTotalIngreso(BigDecimal detalleTotalIngreso) {

		if (detalleTotalIngreso != null) {
			this.detalleTotalIngreso = detalleTotalIngreso.setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.detalleTotalIngreso = new BigDecimal(0.00);
		}
	}

	public void calcularTotalDetalle() {

		this.detalleCantidad = detalleAlojamiento.getAlojamientoHospedaje()
				.getHospedajeDias();
		System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP"
				+ detalleAlojamiento.getAlojamientoHospedaje()
						.getHospedajeDias());

		if (detalleAlojamiento.getAlojamientoAdicional() == null
				&& detalleAlojamiento.getAlojamientoDescuento() == null) {
			detalleAlojamiento.setAlojamientoAdicional(new BigDecimal(0.00));
			detalleAlojamiento.setAlojamientoDescuento(new BigDecimal(0.00));
			this.detalleTotal = (((detalleAlojamiento
					.getAlojamientoHabitacion().getHabitacionPrecioDia()
					.multiply(new BigDecimal(detalleCantidad)))
					.add(detalleAlojamiento.getAlojamientoAdicional()))
					.subtract(detalleAlojamiento.getAlojamientoDescuento()));
			System.out.println("totalDetalle" + detalleTotal);

		} else {
			if (detalleAlojamiento.getAlojamientoAdicional() == null
					&& detalleAlojamiento.getAlojamientoDescuento() != null) {

				detalleAlojamiento
						.setAlojamientoAdicional(new BigDecimal(0.00));
				this.detalleTotal = (((detalleAlojamiento
						.getAlojamientoHabitacion().getHabitacionPrecioDia()
						.multiply(new BigDecimal(detalleCantidad)))
						.add(detalleAlojamiento.getAlojamientoAdicional()))
						.subtract(detalleAlojamiento.getAlojamientoDescuento()));
				System.out.println("totalDetalle" + detalleTotal);

			} else {
				if (detalleAlojamiento.getAlojamientoAdicional() != null
						&& detalleAlojamiento.getAlojamientoDescuento() == null) {
					detalleAlojamiento.setAlojamientoDescuento(new BigDecimal(
							0.00));
					this.detalleTotal = (((detalleAlojamiento
							.getAlojamientoHabitacion()
							.getHabitacionPrecioDia().multiply(new BigDecimal(
							detalleCantidad))).add(detalleAlojamiento
							.getAlojamientoAdicional()))
							.subtract(detalleAlojamiento
									.getAlojamientoDescuento()));
					System.out.println("totalDetalle" + detalleTotal);
				} else {
					this.detalleTotal = (((detalleAlojamiento
							.getAlojamientoHabitacion()
							.getHabitacionPrecioDia().multiply(new BigDecimal(
							detalleCantidad))).add(detalleAlojamiento
							.getAlojamientoAdicional()))
							.subtract(detalleAlojamiento
									.getAlojamientoDescuento()));
					System.out.println("totalDetalle" + detalleTotal);
				}
			}
		}
	}

	public void calcularTotalIngreso() {
		if (reciboCabecera.getListaAbonos().isEmpty()) {

			setDetalleTotalIngreso(new BigDecimal(0.00));

		} else {

			List<Abono> listaAbonos = reciboCabecera.getListaAbonos();

			this.detalleTotalIngreso = (listaAbonos.stream()
					.filter(value -> value.isAbonoReporte() == false)
					.map(Abono::getAbonoMonto).reduce(BigDecimal::add).get())
					.divide(new BigDecimal(reciboCabecera.getListaDetalles()
							.size()));

		}
	}

	public ReciboDetalle() {
	}

}
