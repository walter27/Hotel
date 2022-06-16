package org.hotelLeon.presentacion.persona;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hotelLeon.dominio.login.Grupo;

@FacesConverter("grupoConverter")
public class GrupoConverter implements Converter {

	public Object getAsObject(FacesContext ctx, UIComponent uiComponent,
			String beerId) {
		ValueExpression vex = ctx
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(ctx.getELContext(),
						"#{usuarioControlador}", UsuarioControlador.class);

		UsuarioControlador beers = (UsuarioControlador) vex.getValue(ctx
				.getELContext());
		return beers.getGrupo(Integer.valueOf(beerId));
	}

	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object object) {
		if (object != null) {
			return String.valueOf(((Grupo) object).getGrupoId());

		} else {
			return null;
		}
	}

}
