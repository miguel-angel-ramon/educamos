package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Oferta Matricula Centro", description = "Descripcion para el modelo de ofertas ofrecidas por un centro")
public class DgcOfertaMatriculaCentroDto extends BaseAuditedDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la oferta")
	private Long id;

	@Schema(description = "Oferta vigente")
	private String lVigencia;
	
	// ---------- Relationships -----------

	@Schema(description = "Oferta generica")
	private DgcOfertaMatriculaGenericoDto ofertaMatriculaGenerico;

}
