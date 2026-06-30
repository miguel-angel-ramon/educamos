package es.jccm.edu.evaluacion.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Descriptor operativo alumno", description = "Proyección para rescatar los descriptores operativos evaluados")
public interface DescriptorOperativoAlumnoProjection {
	
	@Schema(description = "Id descriptor operativo")
	Long getIdDescriptorOperativo();
	
	@Schema(description = "Descripción del descriptor operativo")
	String getDescDescriptorOperativo();
	
	@Schema(description = "Abreviatura del descriptor operativo")
	String getAbrevDescriptorOperativo();
	
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
    
    @Schema(description = "Id interno de la valoración temporal del descriptor operativo del alumno")
    Long getIdValDesOpeAluTemp();
	
}
