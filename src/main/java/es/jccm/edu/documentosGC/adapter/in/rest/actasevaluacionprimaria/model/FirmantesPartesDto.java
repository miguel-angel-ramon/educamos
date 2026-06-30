package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacionprimaria.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "firmantes partes", description = "firmantes partes ausencias ")
public class FirmantesPartesDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id director")
	private Long idDirector;
	
	@Schema(description = "Id secretario")
	private Long idSecretario;
	
	@Schema(description = "Id jefe")
	private Long idJefe;
	
	@Schema(description = "Id inspector")
	private Long idInspector;

}
