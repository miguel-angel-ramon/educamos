package es.jccm.edu.evaluacion.application.domain.convocatoriasEvaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Alumno evaluacion", description = "Proyección para rescatar los alumnos de la agrupación de la evaluación")
public interface AlumnoEvalConvProjection {
	
	@Schema(description = "Id del alumno")
    Long getIdAlumno();

    @Schema(description = "Id de la matrícula")
    Long getIdMatricula();
    
    @Schema(description = "Id del grupo actividad")
    Long getIdGrupoActividad();
    
    @Schema(description = "Id de la oferta de la matrícula")
    Long getIdOfertaMatrig();

    @Schema(description = "Id numero escolar")
    String getNumEscolar();
    
    @Schema(description = "Año académico")
    Long getAnno();

    @Schema(description = "Nombre del alumno")
    String getNombre();

    @Schema(description = "Apellidos del alumno")
    String getApellidos();
    
    @Schema(description = "Nombre del curso")
    String getNombreCurso();
    
    @Schema(description = "Id de la promoción")
    Long getIdPromocion();
    
    @Schema(description = "Id del estado de la promoción")
    Long getIdEstado();
    
    @Schema(description = "Descripción del estado de la promoción")
    String getDescripcionEstado();
    
    @Schema(description = "Fecha de la sesión de evaluacion")
    String getFechaSesion();
    
    @Schema(description = "Resultado de la promoción")
	Long getCResultado();
    
    @Schema(description = "Id de la etapa")
    Long getIdEtapa();
    
    @Schema(description = "Descripción de la etapa")
    String getDescripcionEtapa();

    @Schema(description = "Es ACNEE")
    Integer getAcnee();

    @Schema(description = "Nivel curricular alumno ACNEE")
    String getNivelCurricular();

}
