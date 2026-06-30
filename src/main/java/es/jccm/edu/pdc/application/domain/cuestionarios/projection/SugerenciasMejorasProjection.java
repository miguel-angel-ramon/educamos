package es.jccm.edu.pdc.application.domain.cuestionarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Sugerencias y Mejoras", description = "Proyección para rescatar los valores del sugerencias y mejoras")
public interface SugerenciasMejorasProjection {



	@Schema(description = "x_cupere")
	Long getIdCuepre();

	@Schema(description = "x_cueopc")
	Long getIdCueopc();

	@Schema(description = "d_sugerencia")
	String getDescSugerencia();

	@Schema(description = "d_comomejorar")
	String getDescComomejorar();




}