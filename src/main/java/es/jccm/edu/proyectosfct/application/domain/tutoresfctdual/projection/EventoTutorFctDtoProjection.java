package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EventoTutorFctDual", description = "Descripcion para el modelo de tutores fct o dual")
public interface EventoTutorFctDtoProjection {
	
	@Schema(description = "Nif tutor")
	String getNif();
	
	@Schema(description = "Tip documento tutor")
	String getTipide();

}
