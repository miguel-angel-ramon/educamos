package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "tlparaus", description = "tlparaus")
public class TlparausProjectionDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "parte")
	private Integer orden;
	
}
