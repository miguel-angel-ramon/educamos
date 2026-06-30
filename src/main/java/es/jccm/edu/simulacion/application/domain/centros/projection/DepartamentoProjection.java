package es.jccm.edu.simulacion.application.domain.centros.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Departamento", description = "Departamento")
public interface DepartamentoProjection {
	
	@Schema(description = "Id Departamento")
	Long getIdDepartamento();
	
	@Schema(description = "Nombre Departamento")
	String getNombreDepartamento();
	
}

