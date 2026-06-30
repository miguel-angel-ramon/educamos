package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Evaluacion", description = "Evaluacion de alumnos por grupo actividad")
public class EvaluacionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id Matricula")
	private Long idMatricula;
	
	@Schema(description = "Id Alumno")
	private Long idAlumno;

	@Schema(description = "Nombre y apellidos de alumno")
	private String nombreAlumno;

	@Schema(description = "Calificaciones del alumno")
	private List<CalificacionDto> calificaciones;

	@Schema(description = "Foto alumno")
	private byte[] foto;
	
	@Schema(description = "Id estado de la convocatoria del centro")
	private Long idEstadoConvocatoria;
	
	@Schema(description = "Estado de la convocatoria")
	private String nombreEstadoConvocatoria;

	@Schema(description = "Indica si el alumno es o no acnee")
	private Long acnee;

	@Schema(description = "Indica el nivel curricular del alumno acnee")
	private String nivelCurricular;

}
