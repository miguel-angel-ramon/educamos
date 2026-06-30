package es.jccm.edu.documentosGC.application.domain.firmantesdigital.entities;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo empleado firma", description = "Tipo empleado firma")
public class FirmanteRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Indica el tipo de firmante")
	private String tipo;	
	
	

}
