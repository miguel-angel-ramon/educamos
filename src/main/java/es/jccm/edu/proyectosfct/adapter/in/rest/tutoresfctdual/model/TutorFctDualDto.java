package es.jccm.edu.proyectosfct.adapter.in.rest.tutoresfctdual.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TutorFctDual", description = "Descripcion para el modelo de tutores fct o dual")
public class TutorFctDualDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del tutor")
	private Long id;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha de finalización de la tutoria")
	private Date fechaBaja;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha de inicio de la tutoria")
	private Date fechaInicioTutoria;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Empleado relacionado")
	private PuestoTrabajoEmpleadoDto puestoTrabajoEmpleado;
	
}
