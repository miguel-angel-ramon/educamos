package es.jccm.edu.proyectosfct.application.domain.alumnoprograma.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnoAux", description = "Descripcion para el modelo de alumnos Aux")
public interface AlumnoProjectionAux {

	@Schema(description = "Fecha inicio")
	String getFini();
	
	@Schema(description = "Fecha fin")
	String getFfin();
	
	@Schema(description = "Horario")
	String getHorario();
	
	
	
}
