package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Docente ponderación", description = "Listado de docentes mismo centro y misma materia")
public interface DocentePonderacionProjection {

	@Schema(description = "Id del empleado")
	Long getIdEmpleado();

	@Schema(description = "Nombre del docente")
	String getNombre();
	
	@Schema(description = "Apellidos del docente")
	String getApellidos();
	
}