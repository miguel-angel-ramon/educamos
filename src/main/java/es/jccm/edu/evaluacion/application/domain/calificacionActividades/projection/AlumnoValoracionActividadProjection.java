package es.jccm.edu.evaluacion.application.domain.calificacionActividades.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumnos Valoración Actividad", description = "Calificacion de actividades")
public interface AlumnoValoracionActividadProjection {

	@Schema(description = "Id Matricula")
	Long getIdMatricula();
	
	@Schema(description = "Id Alumno")
	Long getIdAlumno();
	
	@Schema(description = "Id MatMatriAlu")
	Long getIdMatMatriAlu();
	
	@Schema(description = "Id Programación Didáctica")
	Long getIdProgramacionDidactica();
	
	@Schema(description = "Id Programación de Aula")
	Long getIdProgramacionAula();
	
	@Schema(description = "Id interno de la convocatoria")
	Long getIdConvCentroOmc();
	
	@Schema(description = "Id de la unidad de programación")
	Long getIdUnidadProgramacion();
	
	@Schema(description = "Id interno de la Unidad del centro")
	Long getIdUnidadCentro();
	
	@Schema(description = "Id número escolar del alumno")
	String getNumEscolar();

	@Schema(description = "Nombre y apellidos de alumno")
	String getNombreAlumno();
	
	@Schema(description = "Nombre de la programación de aula")
	String getNombreProgramacionAula();
	
	@Schema(description = "Nombre de la convocatoria")
	String getNombreConvocatoria();
	
	@Schema(description = "Nombre de la unidad de programación")
	String getNombreUnidadProgramacion();
	
	@Schema(description = "Nombre de la unidad del centro")
	String getNombreUnidadCentro();
	
}
