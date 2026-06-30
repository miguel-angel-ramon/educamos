package es.jccm.edu.alumnos.adapter.in.rest.alumnosHorario.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumnos", description = "Entidad para rescatar el listado de mis alumnos")
public class AlumnosDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del alumno")
	private Long idAlumno;
	
	@Schema(description = "Id escolar del alumno")
	private String idEscolar;
	
	@Schema(description = "Id de la matrícula del alumno")
	private Long idMatricula;
	
	@Schema(description = "Nombre del alumno")
	private String nombre;
	
	@Schema(description = "Unidad del alumno")
	private String unidad;
	
	@JsonFormat(pattern = "dd/MM/yyyy", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha de nacimiento")
	private Date fechaNacimiento;
	
	@Schema(description = "Alumno pertenece a diversifica")
	private boolean diversifica;
	
	@Schema(description = "Estado de la matrícula del alumno")
	private String estadoMatricula;

	@Schema(description = "Foto del alumno")
	private byte[] foto;

}
