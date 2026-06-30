package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoAndFaltas", description = "Entidad para rescatar el listado de alumnos de una clase con sus faltas")
public class AlumnoAndFaltasListDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la matrícula del alumno")
	private Integer idMatricula;

	@Schema(description = "Nombre del alumno")
	private String nombreAlumno;

	@Schema(description = "Nombre del alumno formateado")
	private String nombreFormateado;

	@Schema(description = "Id del alumno")
	private Integer idAlumno;

	@Schema(description = "Tipo de falta")
	private String tipoFalta;

	@Schema(description = "Id de la materia")
	private Integer idMateriaOmg;

	@Schema(description = "Delphos")
	private String delphos;

	@Schema(description = "Id de la notificación de falta de asistencia")
	private String idNotificacion;

	@Schema(description = "Comentario de la notificación")
	private String comentarioNotificacion;

	@Schema(description = "Motivo de la notificación")
	private String motivoNotificacion;

	@Schema(description = "Nombre del usuario que ha puesto la notificación")
	private String nombreUsuarioNotificacion;

	@Schema(description = "Foto del alumno")
	private byte[] foto;

}
