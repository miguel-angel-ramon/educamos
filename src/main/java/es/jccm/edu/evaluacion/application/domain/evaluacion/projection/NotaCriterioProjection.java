package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Notas criterios", description = "Proyección para rescatar las notas de los criterios")
public interface NotaCriterioProjection {

    @Schema(description = "Id criterio del alumno")
    Long getIdCriAlu();
    
    @Schema(description = "Peso del criterio en la actividad")
    Integer getPeso();
    
    @Schema(description = "Id criterio ")
    Long getIdCriterio();

    @Schema(description = "Id de la calificación")
    Long getIdCalifica();
    
    @Schema(description = "Id de la unidad de programacion")
    Long getIdUnidadProgramacion();

    @Schema(description = "Nota del criterio por alumno")
    String getNota();

    @Schema(description = "Descripción de la nota del alumno")
    String getDescCal();

    @Schema(description = "Indica si la nota es aprobada o no")
    String getAprueba();

    @Schema(description = "Id de la actividad")
    Long getIdActividad();

    @Schema(description = "Id de la actividad")
    Integer getLprocedeMoodle();
    
    @Schema(description = "Id de la actividad")
    String getAbreviatura();
    
    @Schema(description = "Id de la actividad")
    Long getUsuCreacion();

}
