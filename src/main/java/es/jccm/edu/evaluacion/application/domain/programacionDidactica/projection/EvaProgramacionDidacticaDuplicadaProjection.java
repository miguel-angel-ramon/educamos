package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Programaciones Didácticas Duplicadas", description = "Proyección para rescatar las programaciones didácticas duplicadas")
public interface EvaProgramacionDidacticaDuplicadaProjection {

	@Schema(description = "Id de materia de curso genérica")
	Long getIdMateriaOmg();
	
	@Schema(description = "Id de la oferta de la matrícula")
	Long getIdOfertaMatrig();
	
	@Schema(description = "Id del centro")
	Long getIdCentro();
	
	@Schema(description = "Año académico")
	Integer getAnyo();
	
	@Schema(description = "Número de duplicados de la programación didáctica")
	Long getCountDuplicadas();
	
}
