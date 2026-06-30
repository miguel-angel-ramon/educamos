package es.jccm.edu.evaluacion.application.domain.aulaVirtual.projection;

import io.swagger.v3.oas.annotations.media.Schema;

public interface RelacionAlumnoOidProjection {

	@Schema(description = "Id Alumno")
	Long getIdAlumno();
	
	@Schema(description = "OID del Alumno")
	Long getOid();
	
	@Schema(description = "Id. Matrícula del Alumno")
	Long getIdMatricula();
	
}
