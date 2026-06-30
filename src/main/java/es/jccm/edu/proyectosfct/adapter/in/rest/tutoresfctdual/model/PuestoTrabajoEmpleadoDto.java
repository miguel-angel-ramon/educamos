package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Puesto trabajo empleado", description = "Descripcion para el modelo de puesto de trabajo de un empleado")
public class PuestoTrabajoEmpleadoDto extends BaseAuditedDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del empleado")
	private Long idEmpleado;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Europe/Madrid")
	@Schema(description = "Fecha toma pesesion")
	private Date idFechaTomaPosesion;

	// ---------- Relationships -----------

	@Schema(description = "Centro")
	private CentroDto centro;
	
	@Schema(description = "Empleado")
    public EmpleadoDto empleado;

}
