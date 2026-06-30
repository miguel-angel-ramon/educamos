package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumnos Valoración Actividad", description = "Calificacion de actividades")
public class AlumnoValoracionActividadDTO {
	
	@Schema(description = "Id Matricula")
	private Long idMatricula;
	
	@Schema(description = "Id Alumno")
	private Long idAlumno;
	
	@Schema(description = "Id MatMatriAlu")
	private Long idMatMatriAlu;
	
	@Schema(description = "Id Programación Didáctica")
	private Long idProgramacionDidactica;
	
	@Schema(description = "Id Programación de Aula")
	private Long idProgramacionAula;
	
	@Schema(description = "Id interno de la convocatoria")
	private Long idConvCentroOmc;
	
	@Schema(description = "Id de la unidad de programación")
	private Long idUnidadProgramacion;
	
	@Schema(description = "Id interno de la Unidad del centro")
	private Long idUnidadCentro;
	
	@Schema(description = "Id número escolar del alumno")
	private String numEscolar;

	@Schema(description = "Nombre y apellidos de alumno")
	private String nombreAlumno;
	
	@Schema(description = "Nombre de la programación de aula")
	private String nombreProgramacionAula;
	
	@Schema(description = "Nombre de la convocatoria")
	private String nombreConvocatoria;
	
	@Schema(description = "Nombre de la unidad de programación")
	private String nombreUnidadProgramacion;
	
	@Schema(description = "Nombre de la unidad del centro")
	private String nombreUnidadCentro;
	
	@Schema(description = "Foto del alumno")
    private byte[] foto;
	
	@Schema(description = "idUsuario Moodle")
    private Long idUsuario;
	
	@Schema(description = "Listado de criterios de las actividades del alumno con sus calificaciones")
	private List<CriterioAlumnoDTO> criterios;

}
