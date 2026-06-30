package es.jccm.edu.horarios.application.domain.horarios.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TotalHoras", description = "Proyección para rescatar el total de horas semanal de un profesor")
public interface TotalHorasProjection {
	
	@Schema(description = "Horas lectivas de docencia directa (LDD)")
	String getHorasLec();
	
	@Schema(description = "Horas lectivas de funciones específicas (LFE)")
	String getHorasNoLec();

	@Schema(description = "Complementarias (C)")
	String getHorasCompl();
	
}

