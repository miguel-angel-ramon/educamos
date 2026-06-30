package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Flujo del gasto", description = "Descripcion para el modelo de gasto flujo")
public class GastoFlujoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del flujo gasto")
	private Long id;
	
	@Schema(description = "Requiere adjunto")
	private Boolean requiereAdjunto;
	
	@Schema(description = "Flag para un borrado lógico del gasto")
	private Boolean borrado;
	
	@Schema(description = "Fecha del borrado lógico del gasto")
	private Date fechaBorrado;
	
	@Schema(description = "Idenficador del usuario del borrado lógico")
	private Long usuarioBorrado;
	
	// ---------- Relationships -----------	
	
	@Schema(description = "Tipo gasto")
	private TipoGastoDto tipoGasto;
	
	@Schema(description = "Id Perfil")
	private Long idPerfil;
	
	@Schema(description = "Estado origen")
	private EstadoGastoDto estadoOrigen;
	
	@Schema(description = "Estado destino")
	private EstadoGastoDto estadoDestino;
}
