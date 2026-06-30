package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Datos centro", description = "Datos centro para generar las actas de evaluacion")
public class DatosCentroActaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Localidad del centro")
	private String localidad;
	
	@Schema(description = "Tipo de centro. Publico/Privado")
	private Integer tipo;
	
	

}
