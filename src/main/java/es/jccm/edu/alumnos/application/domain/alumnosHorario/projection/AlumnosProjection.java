package es.jccm.edu.alumnos.application.domain.alumnosHorario.projection;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Lob;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumnos", description = "Proyección para rescatar los alumnos del componente Mis Almnos")
public interface AlumnosProjection {

	@Schema(description = "Id de la matrícula del alumno")
	Long getIdAlumno();
	
	@Schema(description = "Id escolar del alumno")
	String getIdEscolar();
	
	@Schema(description = "Id de la matrícula del alumno")
	Long getIdMatricula();

	@Schema(description = "Nombre completo del alumno")
	String getNombre();

	@Schema(description = "Unidad del alumno")
	String getUnidad();
	
	@Schema(description = "Fecha de nacimiento del alumno")
	Date getFechaNacimiento();
	
	@Schema(description = "Alumno pertenece a diversifica")
	Boolean getDiversifica();
	
	@Schema(description = "Estado de la matrícula del alumno")
	String getEstadoMatricula();

	@Lob
   	@Schema(description = "Foto")
   	Blob getFoto();

}



