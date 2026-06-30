package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "vista", description = "Vista partes y actas para centro")
public class VistaPartesActasDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "pilotopartes")
	private Integer pilotopartes;
	
	@Schema(description = "pilotoactas")
	private Integer pilotoactas;	

}
