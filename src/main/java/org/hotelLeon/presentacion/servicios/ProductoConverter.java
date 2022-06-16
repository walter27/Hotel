package org.hotelLeon.presentacion.servicios;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.hotelLeon.dominio.servicios.Producto;
import org.hotelLeon.dominio.servicios.Servicio;
import org.hotelLeon.presentacion.factura.ReciboControlador;

@FacesConverter("productoConverter")
public class ProductoConverter implements Converter {

	public Object getAsObject(FacesContext ctx, UIComponent uiComponent,
			String beerId) {
		ValueExpression vex = ctx
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(ctx.getELContext(),
						"#{reciboControlador}", ReciboControlador.class);

		ReciboControlador beers = (ReciboControlador) vex.getValue(ctx
				.getELContext());
		return beers.getProducto(Integer.valueOf(beerId));
	}

	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object object) {
		if (object != null) {
			return String.valueOf(((Producto) object).getProductoId());

		} else {
			return null;
		}
	}
}
