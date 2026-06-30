package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DetalleAdjuntosDto ", description = "Descripcion para el modelo de actualizacion adjunto historico")
public class DetalleAdjuntosRequestDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del documento")
	private Long idAdjunto;
	
	@Schema(description = "Identificador del documento")
	private Long idTipoAdjunto;
	
	@Schema(description = "Identificador del documento")
	private String idDocHisRodal;
	
	@Schema(description = "Identificador del documento")
	private String operacion;
	
	@Schema(description = "El documento es firmable")
	private String firmable;

}
