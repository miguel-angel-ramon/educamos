package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Valoración competencia clave alumno", description = "Proyección para rescatar las valoraciones de las competencias clave evaluadas")
public interface ValoracionCompetenciaClaveAlumnoProjection {
	
	@Schema(description = "Id competencia clave")
    Long getIdCompetenciaClave();
	
	@Schema(description = "Nombre abreviado de la competencia clave")
	String getNombreCompetenciaClave();
	
	@Schema(description = "Id de la calificación")
    Long getIdCalifica();
	
	@Schema(description = "Descripción de la nota del alumno")
    String getDescCal();

    @Schema(description = "Nota numérica de la calificación")
    Long getNota();

    @Schema(description = "Indica si la nota es aprobada o no")
    String getAprueba();
    
    @Schema(description = "Descripción sistema de calificación")
    String getDescDetCal();
    
    @Schema(description = "Id de la valoración de adquisición de la competencia clave del alumno")
	Long getId();
    
}
