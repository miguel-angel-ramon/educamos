package es.jccm.edu.pdc.application.domain.planActuacion.projection;



import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Objetivos específicos ", description = "Proyección para rescatar los objetivos específicos")
public interface ObjetivoEspecificoProjection {
	
	@Schema(description = "x_competencia al que pertenece el objetivo")
	Integer getIdAmbito();
	
	@Schema(description = "Id del objetivo general")
	Long getIdObjetivo();

	@Schema(description = "Id del objetivo específico")
	Long getIdObjEsp();

	@Schema(description = "Descripción del objetivo específico")
	String getDescripcion();

	@Schema(description = "Indica si el objetivo específico está activo")
	String getActivo();
	
}

