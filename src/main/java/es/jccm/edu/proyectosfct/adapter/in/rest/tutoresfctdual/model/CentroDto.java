package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Centro", description = "Descripcion para el modelo de centro")
public class CentroDto extends BaseAuditedDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del centro")
	private Long id;

	@Schema(description = "Codigo del centro")
	private Long codigoCentro;

	// ---------- Relationships -----------
}
