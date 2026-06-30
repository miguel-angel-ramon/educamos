package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TipoCentroDTO", description = "DTO Tipo Centro")
public class TipoCentroDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Tipo Centro")
	private Long id;

	@Schema(description = "Descripción del Tipo Centro")
	private String descripcion;
	
	@Schema(description = "Descripción corta del Tipo Centro")
	private String descripcionCorta;
	
}