package es.jccm.edu.proyectosfct.application.domain.alumnado.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumno", description = "Descripcion para el modelo de alumnos")
public interface FechaSemanaProjection {
	
	@Schema(description = "Primer dia")
	Date getPrimerDia();
	
	@Schema(description = "Semana")
	String getSemana();
	
	@Schema(description = "Flag is fecha actual")
	String getActual();
	
}

