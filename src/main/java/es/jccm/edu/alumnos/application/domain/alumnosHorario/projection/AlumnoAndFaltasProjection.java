package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import java.sql.Blob;

import javax.persistence.Lob;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnoAndFaltas", description = "Proyección para rescatar los alumnos de una clase y sus faltas diarias")
public interface AlumnoAndFaltasProjection {

	@Schema(description = "Id de la matrícula del alumno")
	Integer getIdMatricula();

	@Schema(description = "Nombre completo del alumno")
	String getNombreAlumno();

	@Schema(description = "Nombre formateado")
	String getNombreFormateado();

	@Schema(description = "Id del alumno")
	Integer getIdAlumno();

	@Schema(description = "Tipo de falta")
	String getTipoFalta();

	@Schema(description = "Id de la materia")
	Integer getIdMateriaOmg();

	@Schema(description = "Delphos")
	String getDelphos();

	@Schema(description = "Identificador de las notificaciones de faltas de asistencia del alumnado.")
	String getIdNotificacion();

	@Schema(description = "Comentario de la notificación")
	String getComentarioNotificacion();
	
	@Schema(description = "Motivo de la notificación")
	String getMotivoNotificacion();
	
	@Schema(description = "Nombre del usuario de creación de la notificación")
	String getNombreUsuarioNotificacion();

	@Lob
   	@Schema(description = "Foto")
   	Blob getFoto();

}



