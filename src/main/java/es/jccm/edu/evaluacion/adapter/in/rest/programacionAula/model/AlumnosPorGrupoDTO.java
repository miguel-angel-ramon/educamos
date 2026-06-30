package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Alumnos por grupo de actividad")
public class AlumnosPorGrupoDTO {

	private Long idGrupo;
	   
	private String nombreGrupo;
	
	private Integer numeroAlumnos;
	
	private Integer numeroAlumnosConProgramacion;
	
	private Integer numeroAlumnosSinProgramacion;
}
