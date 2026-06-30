package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluaciondeportiva.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "otros firmantes", description = "firmantes director y tutor ")
public class OtrosFirmantesDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id director")
	private Long idDirector;
	
	@Schema(description = "Id director")
	private Long idTutor;

}
