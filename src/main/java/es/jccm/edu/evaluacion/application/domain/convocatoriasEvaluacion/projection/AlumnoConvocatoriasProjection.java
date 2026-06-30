package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumno evaluacion", description = "Proyección para rescatar los alumnos de convocatorias de evaluación")
public interface AlumnoConvocatoriasProjection {

    @Schema(description = "Id del alumno")
    Long getIdAlumno();

    @Schema(description = "Id de la matrícula")
    Long getIdMatricula();

    @Schema(description = "Id materia matrícula del alumno")
    Long getIdMatMatriAlu();

    @Schema(description = "Id numero escolar")
    String getNumEscolar();

    @Schema(description = "Nombre del alumno")
    String getNombre();

    @Schema(description = "Apellidos del alumno")
    String getApellidos();
    
    @Schema(description = "Id de la promoción")
    Long getIdPromocion();
    
    @Schema(description = "Id del estado de la promoción")
    Long getIdEstado();
    
    @Schema(description = "Descripción del estado de la promoción")
    String getDescripcionEstado();
    
    @Schema(description = "Fecha de la sesión de evaluacion")
    String getFechaSesion();
}
