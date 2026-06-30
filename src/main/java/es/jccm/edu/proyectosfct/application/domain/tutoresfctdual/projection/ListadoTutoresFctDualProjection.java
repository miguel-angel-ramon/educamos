package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Listado tutores", description = "Descripcion para el modelo de tutores de un centro")
public interface ListadoTutoresFctDualProjection {
	
	@Schema(description = "Identificador del identificador del tutor")
	Long getId();
	
	@Schema(description = "Nombre completo del tutor")
	String getNombreCompleto();
	
	@Schema(description = "Identificador del tutor")
	String getDni();
	
	@Schema(description = "Identificador inicio de la tutoría")
	String getFechaInicioTutoria();
	
	@Schema(description = "Identificador fin de la tutoría")
	String getFechaBaja();	

}
