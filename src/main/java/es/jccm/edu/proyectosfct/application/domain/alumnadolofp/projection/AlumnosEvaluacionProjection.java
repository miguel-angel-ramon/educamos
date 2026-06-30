package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlumnosEvaluacionProjection", description = "Proyección para obtener la lista de alumnos con evaluación LOFP")
public interface AlumnosEvaluacionProjection {

    @Schema(description = "Nombre completo del alumno")
    String getNombreAlumno();

    @Schema(description = "Matrícula del alumno")
    Long getXMatricula();
}
