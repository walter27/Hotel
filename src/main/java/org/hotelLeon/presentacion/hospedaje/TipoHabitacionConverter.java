package org.hotelLeon.presentacion.hospedaje;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hotelLeon.dominio.hospedaje.TipoHabitacion;

@FacesConverter("tipoHabitacionConverter")
public class TipoHabitacionConverter implements Converter {

	public Object getAsObject(FacesContext ctx, UIComponent uiComponent,
			String beerId) {
		ValueExpression vex = ctx
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(ctx.getELContext(),
						"#{tipoHabitacionControlador}",
						TipoHabitacionControlador.class);

		TipoHabitacionControlador beers = (TipoHabitacionControlador) vex
				.getValue(ctx.getELContext());
		return beers.getTipoHabitacion(Integer.valueOf(beerId));
	}

	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object object) {
		if (object != null) {
			return String.valueOf(((TipoHabitacion) object)
					.getTipoHabitacionId());

		} else {
			return null;
		}
	}

}
