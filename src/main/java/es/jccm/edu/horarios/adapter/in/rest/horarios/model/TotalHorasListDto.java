package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TotalHoras", description = "Descripcion para el modelo de horario para la el módulo del escritorio")
public class TotalHorasListDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Horas lectivas de docencia directa (LDD)")
	private String horasLec;
	
	@Schema(description = "Horas lectivas de funciones específicas (LFE)")
	private String horasNoLec;

	@Schema(description = "Complementarias (C)")
	private String horasCompl;

}
