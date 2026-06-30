package es.jccm.edu.evaluacion.adapter.in.rest.valoracionCriterios.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Evaluacion", description = "Evaluacion de alumnos por grupo actividad")
public class AlumnoEvaluacionSelDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id Matricula")
	private Long idMatricula;
	
	@Schema(description = "Id Alumno")
	private Long idAlumno;
	
	@Schema(description = "Id Etapa")
	private Long idEtapa;
	
	@Schema(description = "Id Ciclo")
	private Long idCiclo;
	
	@Schema(description = "Id Unidad")
	private Long idUnidad;

	@Schema(description = "Nombre y apellidos de alumno")
	private String nombreAlumno;
	
	@Schema(description = "Nombre de la etapa")
	private String nombreEtapa;
	
	@Schema(description = "Nombre del ciclo")
	private String nombreCiclo;
	
	@Schema(description = "Nombre de la unidad")
	private String nombreUnidad;
	
	@Schema(description = "Es ACNEE")
	private Integer acnee;
	
	@Schema(description = "Nivel curricular alumno ACNEE")
	private String nivelCurricular;

}
