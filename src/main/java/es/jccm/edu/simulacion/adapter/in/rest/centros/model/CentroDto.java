package es.jccm.edu.simulacion.adapter.in.rest.centros.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Centro", description = "Datos rescatados del centro para el módulo de simulación de usuarios del escritorio")
public class CentroDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del centro")
	private Long idCentro;
	
	@Schema(description = "Código del centro")
	private Long codCentro;
	
	@Schema(description = "Tipo de centro")
	private String tipo;
	
	@Schema(description = "Denominación genérica")
	private String denominacion;
	
	@Schema(description = "Nombre")
	private String nombre;

}
