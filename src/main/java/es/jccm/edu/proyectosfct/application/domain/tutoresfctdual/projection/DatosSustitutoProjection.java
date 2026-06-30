package es.jccm.edu.proyectosfct.application.domain.tutoresfctdual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Docente", description = "Descripcion para el modelo de docentes sustituto")
public interface DatosSustitutoProjection {
	
	@Schema(description = "Id del docente (empleado)")
	Long getId();
	
	@Schema(description = "Nombre")
	String getNombre();
	
	@Schema(description = "Id tutor fct sustituido")
	Long getIdTutorFctDual();
	
	@Schema(description = "Id tutor fct sustituto")
	Long getIdTutorFctDualSus();

}
