package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DetalleAdjuntosDto ", description = "Descripcion para el modelo de actualizacion adjunto historico")
public class DetalleAdjuntosDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del documento")
	private Long idHistorial;
	
	@Schema(description = "Identificador del documento")
	private Long idCentro;
	
	@Schema(description = "Identificador del documento")
	private Long idAnno;
	
	@Schema(description = "Descripcion tipos")
	private List<DetalleAdjuntosRequestDto> adjuntos;
	
	@Schema(description = "Identificador del empleado")
	private Long idEmpleado;	

}
