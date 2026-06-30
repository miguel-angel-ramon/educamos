package es.jccm.edu.shared.application.domain.baseAudited;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "BaseAuditedDTO", description = "Datos de auditoria")
public class BaseAuditedDTO {

	@Schema(description = "Usuario de alta del registro")
	private Long idUsuarioCreacion;
	
	@Schema(description = "Fecha de alta del registro")
	private Date fechaCreacion;

	@Schema(description = "Usuario de modificación del registro")
	private Long idUsuarioModificacion;

	@Schema(description = "Fecha de modificación del registro")
	private Date fechaModificacion;
}

