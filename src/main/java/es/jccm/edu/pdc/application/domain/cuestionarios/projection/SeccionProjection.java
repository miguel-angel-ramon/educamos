package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Seccion", description = "Proyección para rescatar los datos de una Seccion")
public interface SeccionProjection {
	
	@Schema(description = "Id del Seccion")
	Long getIdSeccion();
	
	@Schema(description = "Nombre de la Seccion")
	String getNombre();
	
	@Schema(description = "Descripción de la Seccion")
	String getDescripcion();

	@Schema(description = "Orden de la Seccion")
	Integer getOrden();
	
}

