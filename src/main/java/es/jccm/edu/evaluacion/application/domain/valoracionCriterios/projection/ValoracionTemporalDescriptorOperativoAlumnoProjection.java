package es.jccm.edu.evaluacion.application.domain.valoracionCriterios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Valoración temporal descriptor operativo alumno", description = "Proyección para rescatar las valoraciones temporales de los descriptores operativos evaluados")
public interface ValoracionTemporalDescriptorOperativoAlumnoProjection {

	@Schema(description = "Id de la valoración temporal del descriptor operativo del alumno")
	Long getId();
	
	@Schema(description = "Id del descriptor operativo")
	Long getIdDescriptorOperativo();
	
	@Schema(description = "id de la Matrícula del alumno")
	Long getIdMatricula();
	
	@Schema(description = "id Califica")
	Long getIdCalifica();

	@Schema(description = "nombre del Descriptor Operativo")
	String getNombreDescriptorOperativo();

	@Schema(description = "nota")
	Integer getNota();

	@Schema(description = "Descripción corta de la calificación")
	String getDescCal();

	@Schema(description = "Indica si la nota es aprobada o no")
	String getAprueba();
	
	@Schema(description = "Descripción sistema de calificación")
    String getDescDetCal();
	
}
