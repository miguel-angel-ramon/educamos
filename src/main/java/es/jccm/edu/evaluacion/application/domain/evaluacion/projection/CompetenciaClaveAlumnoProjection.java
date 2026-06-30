package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Competencia clave alumno", description = "Proyección para rescatar las competencias clave evaluadas")
public interface CompetenciaClaveAlumnoProjection {

	@Schema(description = "Id competencia clave")
    Long getIdCompetenciaClave();
	
	@Schema(description = "Descripción de la competencia clave")
	String getDescCompetenciaClave();
	
	@Schema(description = "Abreviatura de la competencia clave")
	String getAbrevCompetenciaClave();
	
	@Schema(description = "Id de la calificación")
    Long getIdCalifica();
	
	@Schema(description = "Descripción de la nota del alumno")
    String getDescCal();

    @Schema(description = "Nota numérica de la calificación")
    Long getNota();

    @Schema(description = "Indica si la nota es aprobada o no")
    String getAprueba();
    
    @Schema(description = "Id de la matrícula")
    Long getIdMatricula();
    
    @Schema(description = "Id interno de la convocatoria")
	Long getIdConvCentroOmc();
    
    @Schema(description = "Id interno de la valoración temporal de la competencia clave del alumno")
    Long getIdValComClaAluTemp();
}
