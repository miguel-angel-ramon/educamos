package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AlumnoHorario", description = "AlumnosHorario rescatados de la BBDD de comunica")
public class AlumnoHorarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del AlumnoHorario")
	private Long idAlumnoHorario;

	@Schema(description = "Nombre del alumno")
	private String nombre;

	@Schema(description = "Primer apellido del alumno")
	private String apellido1;

	@Schema(description = "Segundo apellido del alumno")
	private String apellido2;

	@Schema(description = "numero escolar")
	private String idescolar;

	@Schema(description = "foto")
	private byte[] foto;

	@Schema(description = "numero ide")
	private String numide;

	@Schema(description = "Direccion del alumno")
	private String direccion;

	@Schema(description = "fecha de nacimiento del alumno")
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaNacimiento;

	@Schema(description = "Correo del alumno")
	private String correo;

	@Schema(description = "Telefono del alumno")
	private String telefono;

	@Schema(description = "Telefono del alumno")
	private String tlefnourg;

	@Schema(description = "Comentario de la notificación")
	private String comentarioNotificacion;

	@Schema(description = "Motivo de la notificación")
	private String motivoNotificacion;

	@Schema(description = "Nombre del usuario de creación de la notificación")
	private String nombreUsuarioNotificacion;
	
	@Schema(description = "Observacion")
	private String observacion;

	@Schema(description = "Nº de expediente")
	private String idExpediente;
	
	
	

}
