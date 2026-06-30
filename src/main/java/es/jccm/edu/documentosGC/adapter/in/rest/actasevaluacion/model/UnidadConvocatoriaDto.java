package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "unidad convocatoria", description = "Unidad Convocatoria")
public class UnidadConvocatoriaDto implements Serializable {	
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Descripcion curso")
	private String descripcion;	

}
