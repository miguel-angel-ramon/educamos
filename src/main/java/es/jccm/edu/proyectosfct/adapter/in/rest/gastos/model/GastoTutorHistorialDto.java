package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Historial gasto tutor", description = "Descripcion para el modelo historial gasto tutor")
public class GastoTutorHistorialDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del historial gasto tutor")
	private Long id;
	
	@Schema(description = "Fecha del registro del usuario")
	private Date fechaRegistro;
	
	@Schema(description = "Observaciones cambio estado")
	private String observaciones;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Idenficador del usuario que registra el historico")
	private Long idUsuario;
	
	@Schema(description = "Gasto del tutor")
	private GastoTutorDto gastoTutor;
	
	@Schema(description = "Flujo del gasto")
	private GastoFlujoDto gastoFlujo;
}
