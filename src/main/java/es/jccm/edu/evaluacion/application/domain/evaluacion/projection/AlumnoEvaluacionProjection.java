package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumno evaluacion", description = "Proyección para rescatar los alumnos de valoración")
public interface AlumnoEvaluacionProjection {

	@Schema(description = "Si es o no acneae")
    Long getDisabled();

    @Schema(description = "Id de la matrícula")
    Long getIdMatricula();
    
    @Schema(description = "Id del alumno")
    Long getIdAlumno();

    @Schema(description = "Id materia matrícula del alumno")
    Long getIdMatMatriAlu();

    @Schema(description = "Nombre del alumno")
    String getNombre();
    
    @Schema(description = "Id numero escolar")
    String getNumEscolar();

    @Schema(description = "Apellidos del alumno")
    String getApellidos();
        
}
