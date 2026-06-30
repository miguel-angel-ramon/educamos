package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Centro", description = "Descripcion para el modelo de centro")
public class DgcCentroDto extends BaseAuditedDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del centro")
	private Long id;

	@Schema(description = "Codigo del centro")
	private Long codigoCentro;

	// ---------- Relationships -----------
}
