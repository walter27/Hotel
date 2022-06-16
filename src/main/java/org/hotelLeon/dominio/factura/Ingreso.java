package org.hotelLeon.dominio.factura;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hotelLeon.dominio.persona.UsuarioSistema;
import org.hotelLeon.dominio.servicios.Consumo;
import org.hotelLeon.dominio.servicios.Requerimiento;

@Entity
@Table(name = "INGRESOS")
public class Ingreso {

	private int ingresosId;
	private Date ingresosFecha;
	private UsuarioSistema ingresosUsario;
	private List<Requerimiento> listaRequerimiento;
	private List<ReciboDetalle> listaDetalles;
	private BigDecimal ingresosTotalConsumos;
	private BigDecimal ingresosTotalDetalles;
	private BigDecimal ingresoTotal;
	private BigDecimal ingresosGastos;
	private BigDecimal ingresosTarjeta;
	private BigDecimal ingresosTotalFinal;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "RED_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getIngresosId() {
		return ingresosId;
	}

	public void setIngresosId(int ingresosId) {
		this.ingresosId = ingresosId;
	}

	public Date getIngresosFecha() {
		return ingresosFecha;
	}

	public void setIngresosFecha(Date ingresosFecha) {
		this.ingresosFecha = ingresosFecha;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "ING_USU_FK")
	public UsuarioSistema getIngresosUsario() {
		return ingresosUsario;
	}

	public void setIngresosUsario(UsuarioSistema ingresosUsario) {
		this.ingresosUsario = ingresosUsario;
	}

	@OneToMany
	@JoinColumn(name = "ING_REQ_FK")
	public List<Requerimiento> getListaRequerimiento() {
		return listaRequerimiento;
	}

	public void setListaRequerimiento(List<Requerimiento> listaRequerimiento) {
		this.listaRequerimiento = listaRequerimiento;
	}

	@OneToMany
	@JoinColumn(name = "ING_DET_FK")
	public List<ReciboDetalle> getListaDetalles() {
		return listaDetalles;
	}

	public void setListaDetalles(List<ReciboDetalle> listaDetalles) {
		this.listaDetalles = listaDetalles;
	}

	public BigDecimal getIngresosTotalConsumos() {
		return ingresosTotalConsumos;
	}

	public void setIngresosTotalConsumos(BigDecimal ingresosTotalConsumos) {

		if (ingresosTotalConsumos != null) {
			this.ingresosTotalConsumos = ingresosTotalConsumos.setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.ingresosTotalConsumos = new BigDecimal(0.00);
		}
	}

	public BigDecimal getIngresoTotal() {
		return ingresoTotal;
	}

	public void setIngresoTotal(BigDecimal ingresoTotal) {

		if (ingresoTotal != null) {
			this.ingresoTotal = ingresoTotal.setScale(2, RoundingMode.HALF_UP);
		} else {
			this.ingresoTotal = new BigDecimal(0.00);
		}
	}

	public BigDecimal getIngresosGastos() {
		return ingresosGastos;
	}

	public void setIngresosGastos(BigDecimal ingresosGastos) {

		if (ingresosGastos != null) {
			this.ingresosGastos = ingresosGastos.setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.ingresosGastos = new BigDecimal(0.00);
		}
	}

	public BigDecimal getIngresosTarjeta() {
		return ingresosTarjeta;
	}

	public void setIngresosTarjeta(BigDecimal ingresosTarjeta) {

		if (ingresosTarjeta != null) {
			this.ingresosTarjeta = ingresosTarjeta.setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.ingresosTarjeta = new BigDecimal(0.00);
		}
	}

	public BigDecimal getIngresosTotalFinal() {
		return ingresosTotalFinal;
	}

	public void setIngresosTotalFinal(BigDecimal ingresosTotalFinal) {

		if (ingresosTotalFinal != null) {
			this.ingresosTotalFinal = ingresosTotalFinal.setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.ingresosTotalFinal = new BigDecimal(0.00);
		}
	}

	public BigDecimal getIngresosTotalDetalles() {
		return ingresosTotalDetalles;
	}

	public void setIngresosTotalDetalles(BigDecimal ingresosTotalDetalles) {

		if (ingresosTotalDetalles != null) {
			this.ingresosTotalDetalles = ingresosTotalDetalles.setScale(2,
					RoundingMode.HALF_UP);
		} else {
			this.ingresosTotalDetalles = new BigDecimal(0.00);
		}
	}

	public void calcularTotalAlojamientos() {

		if (listaDetalles.isEmpty()) {

			this.setIngresosTotalDetalles(new BigDecimal(0.00));
		} else {

			this.ingresosTotalDetalles = listaDetalles.stream()
					.filter(value -> value.getDetalleTotalIngreso() != null)
					.map(ReciboDetalle::getDetalleTotalIngreso)
					.reduce(BigDecimal::add).get();
		}
	}

	public void calcularTotalConsumos() {

		if (listaRequerimiento.isEmpty()) {
			this.setIngresosTotalConsumos(new BigDecimal(0.00));
		} else {

			this.ingresosTotalConsumos = listaRequerimiento.stream()
					.map(Requerimiento::getRequerimientoTotal)
					.reduce(BigDecimal::add).get();
		}
	}

	public void calcularTotal() {

		this.ingresoTotal = ingresosTotalConsumos.add(ingresosTotalDetalles);
	}

	public void calcularTotalFinal() {

		if (ingresosGastos == null && ingresosTarjeta == null) {

			setIngresosGastos(new BigDecimal(0.00));
			setIngresosTarjeta(new BigDecimal(0.00));

			this.ingresosTotalFinal = ingresoTotal.subtract((ingresosGastos
					.add(ingresosTarjeta)));
		} else {
			if (ingresosGastos != null && ingresosTarjeta == null) {
				setIngresosTarjeta(new BigDecimal(0.00));
				this.ingresosTotalFinal = ingresoTotal.subtract((ingresosGastos
						.add(ingresosTarjeta)));

			} else {
				if (ingresosTarjeta != null && ingresosGastos == null) {
					setIngresosGastos(new BigDecimal(0.00));
					this.ingresosTotalFinal = ingresoTotal
							.subtract((ingresosGastos.add(ingresosTarjeta)));
				} else {
					this.ingresosTotalFinal = ingresoTotal
							.subtract((ingresosGastos.add(ingresosTarjeta)));
					System.out.println("TOTAL" + ingresosTotalFinal);
				}

			}

		}

	}

	public Ingreso() {
	}

}
