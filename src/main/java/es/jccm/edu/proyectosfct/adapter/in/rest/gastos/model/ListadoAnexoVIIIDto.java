package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Listado gasto alumnado", description = "Descripcion para el modelo listado gasto alumnado")
public class ListadoAnexoVIIIDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Importe total tutores")
	private String totalT;
	
	@Schema(description = "Importe total alumnado")
	private String totalA;
	
	@Schema(description = "Localidad")
	private String localidad;
	
	@Schema(description = "Centro")
	private String denominacion;
	
}
