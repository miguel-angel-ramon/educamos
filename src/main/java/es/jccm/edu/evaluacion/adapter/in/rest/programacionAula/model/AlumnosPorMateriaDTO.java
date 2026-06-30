package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Alumnos por Materia")
public class AlumnosPorMateriaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Alumno")
    private Long idAlumno;
   
	@Schema(description = "Nombre del Alumno")
	private String nombreAlumno;
	
	@Schema(description = "Id de la Matrícula")
	private Long idMatricula;
	
	@Schema(description = "Estado")
	private String estado;

	@Schema(description = "Unidad")
	private String unidad;
	
	@Schema(description = "Matricula de la materia")
	private Long idMatMatricula;
	
	@Schema(description = "Id de la programación de Aula")
	private Long progAula;
	
	@Schema(description = "Id del Aula")
	private Long aula;
    
	@Schema(description = "Id de la Unidad")
    private Long idUnidad;
	
	@Schema(description = "id Usuario Moodle")
    private Long idUsuarioMoodle;
    
    private Boolean seleccionado = false;

	private Boolean tieneValoraciones = false;
}
