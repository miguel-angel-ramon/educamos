package es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Unidades Progrmacion", description = "Calificacion de actividades")
public interface UnidadProgramacionProjection {
	
	 	@Schema(description = "Id de la Unidad de Programación")
	    Long getId();
	    
	    @Schema(description = "Nombre de la Unidad de Programación")
		String getNombre();

	    @Schema(description = "Abreviatura de la Unidad de Programación")
		String getAbreviatura();
		
	    @Schema(description = "Descripción de la Unidad de Programación")
		String getDescripcion();
	    
	    @Schema(description = "Orden de la Unidad de Programación")
		Integer getOrden();
}
