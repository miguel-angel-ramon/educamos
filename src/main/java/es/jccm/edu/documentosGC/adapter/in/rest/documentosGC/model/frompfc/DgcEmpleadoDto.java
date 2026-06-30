package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.frompfc;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Empleado", description = "Descripcion para el modelo de empleado")
public class DgcEmpleadoDto extends BaseAuditedDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del empleado")
	private Long id;

	@Schema(description = "Primer apellido")
	private String apellido1;

	@Schema(description = "Segundo apellido")
	private String apellido2;

	@Schema(description = "Nombre")
	private String nombre;

	@Schema(description = "CIF/NIF del empleado")
	private String dniEmpleado;

	@Schema(description = "Esta activo")
	private String esActivo;

	// ---------- Relationships -----------

}
