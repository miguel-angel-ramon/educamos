package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "AlumnosPorMateriaProjection", description = "Proyección para rescatar los alumnos por materia")
public interface AlumnosPorMateriaProjection {

	@Schema(description = "Id del Alumno")
	Long getIdAlumno();
	
	@Schema(description = "Nombre del Alumno")
	String getNombreAlumno();
	
	@Schema(description = "diversifica/ACNEAE")
	String getEstado();
	
	@Schema(description = "si esta en una programacion de aula")
	Long getProgAula();
	
	Long getIdMatricula();
	
	Long getAula();
	
	Long getIdMatMatricula();
	
	@Schema(description = "Unidad")
	String getUnidad();
    
    @Schema(description = "Unidad")
    Long getIdUnidad();
    
    @Schema(description = "id Usuario Moodle")
    Long getIdUsuarioMoodle();

	@Schema(description = "Indica si tiene valoraciones asociadas")
	Boolean getTieneValoraciones();
}
