package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Valoración descriptor operativo alumno", description = "Proyección para rescatar las valoraciones de los descriptores operativos evaluados")
public interface ValoracionDescriptorOperativoAlumnoProjection {
	
	@Schema(description = "Id descriptor operativo")
	Long getIdDescriptorOperativo();
	
	@Schema(description = "Nombre abreviado del descriptor operativo")
	String getNombreDescriptorOperativo();
	
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
    
    @Schema(description = "Id de la valoración de adquisición del descriptor operativo del alumno")
	Long getId();
    
}
