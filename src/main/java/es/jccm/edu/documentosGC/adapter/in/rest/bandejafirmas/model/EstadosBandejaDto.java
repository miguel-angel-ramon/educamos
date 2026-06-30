package es.jccm.edu.documentosGC.adapter.in.rest.bandejafirmas.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Estados bandeja", description = "Estados documento bandeja de firmas documentos gc")
public class EstadosBandejaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Nombre estado")
	private String nombre;
	
}


