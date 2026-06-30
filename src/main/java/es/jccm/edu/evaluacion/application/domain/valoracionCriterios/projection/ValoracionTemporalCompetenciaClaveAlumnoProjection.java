package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Valoración temporal competencia clave alumno", description = "Proyección para rescatar las valoraciones temporales de las competencias clave evaluadas")
public interface ValoracionTemporalCompetenciaClaveAlumnoProjection {
	
	@Schema(description = "Id de la valoración temporal del descriptor operativo del alumno")
	Long getId();
	
	@Schema(description = "Id competencia clave")
    Long getIdCompetenciaClave();
	
	@Schema(description = "id de la Matrícula del alumno")
	Long getIdMatricula();
	
	@Schema(description = "Id de la calificación")
    Long getIdCalifica();
	
	@Schema(description = "Nombre abreviado de la competencia clave")
	String getNombreCompetenciaClave();
	
	@Schema(description = "Descripción corta de la nota del alumno")
    String getDescCal();

    @Schema(description = "Nota numérica de la calificación")
    Integer getNota();

    @Schema(description = "Indica si la nota es aprobada o no")
    String getAprueba();
    
    @Schema(description = "Descripción sistema de calificación")
    String getDescDetCal();

}
