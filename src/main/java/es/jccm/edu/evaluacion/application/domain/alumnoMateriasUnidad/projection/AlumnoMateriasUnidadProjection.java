package es.jccm.edu.evaluacion.application.domain.alumnoMateriasUnidad.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnoMateriasUnidad", description = "Proyección para rescatar las materias de un profesor, los alumnos que están matriculados en ellas y las unidades dónde las imparte")
public interface AlumnoMateriasUnidadProjection {

	@Schema(description = "IdMateria")
	Long getIdMateria();
	
	@Schema(description = "NombreMateria")
	String getNombreMateria();
	
	@Schema(description = "DescripcionMateria")
	String getDescripcionMateria();
	
	@Schema(description = "MateriaAbreviatura")
	String getMateriaAbreviatura();
	
	@Schema(description = "IdUnidad")
	Long getIdUnidad();
	
	@Schema(description = "NombreCurso")
	String getNombreCurso();
	
	@Schema(description = "IdAlumno")
	Long getIdAlumno();
	
	@Schema (description = "Nombre")
	String getNombre();
	
	@Schema (description = "Apellidos del alumno")
	String getApellidos();
}
