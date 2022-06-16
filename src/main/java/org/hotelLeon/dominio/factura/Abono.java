package org.hotelLeon.dominio.factura;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ABONO")
public class Abono {

	private int abonoId;
	private BigDecimal abonoMonto;
	private Date abonoFecha;
	private ReciboCabecera abonoReciboCabecera;
	private TipoPago abonoTipoPago;
	private boolean abonoEliminar;
	private boolean abonoReporte;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "REC_ID_PR", columnDefinition = "NUMERIC (10,0)")
	public int getAbonoId() {
		return abonoId;
	}

	public void setAbonoId(int abonoId) {
		this.abonoId = abonoId;
	}

	public BigDecimal getAbonoMonto() {
		return abonoMonto;
	}

	public void setAbonoMonto(BigDecimal abonoMonto) {
		if (abonoMonto != null) {
			this.abonoMonto = abonoMonto.setScale(2, RoundingMode.HALF_UP);
		} else {
			abonoMonto = new BigDecimal(0.00);
		}
	}

	public Date getAbonoFecha() {
		return abonoFecha;
	}

	public void setAbonoFecha(Date abonoFecha) {
		this.abonoFecha = abonoFecha;
	}

	@ManyToOne
	@JoinColumn(name = "ABN_REC_FK")
	public ReciboCabecera getAbonoReciboCabecera() {
		return abonoReciboCabecera;
	}

	public void setAbonoReciboCabecera(ReciboCabecera abonoReciboCabecera) {
		this.abonoReciboCabecera = abonoReciboCabecera;
	}

	public TipoPago getAbonoTipoPago() {
		return abonoTipoPago;
	}

	public void setAbonoTipoPago(TipoPago abonoTipoPago) {
		this.abonoTipoPago = abonoTipoPago;
	}

	public boolean isAbonoEliminar() {
		return abonoEliminar;
	}

	public void setAbonoEliminar(boolean abonoEliminar) {
		this.abonoEliminar = abonoEliminar;
	}

	public boolean isAbonoReporte() {
		return abonoReporte;
	}

	public void setAbonoReporte(boolean abonoReporte) {
		this.abonoReporte = abonoReporte;
	}

	public Abono() {

	}

}
