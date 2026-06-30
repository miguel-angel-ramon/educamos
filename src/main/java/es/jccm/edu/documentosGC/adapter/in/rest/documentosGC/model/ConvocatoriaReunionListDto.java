package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "convocatoria", description = "Convocatoria reuniones")
public class ConvocatoriaReunionListDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Descripcion convocatoria")
	private String DescripcionConvocatoriaReunion;	

}
