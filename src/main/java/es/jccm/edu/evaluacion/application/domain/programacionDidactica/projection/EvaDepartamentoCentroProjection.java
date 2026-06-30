package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Departamento de un centro", description = "Departamento de un centro")
public interface EvaDepartamentoCentroProjection {

	@Schema(description = "Id de la programación didáctica")
	Long getIdDepartamento();
	
	@Schema(description = "Nombre de la materia - abreviatura")
	String getNombreDepartamento();
	
}
