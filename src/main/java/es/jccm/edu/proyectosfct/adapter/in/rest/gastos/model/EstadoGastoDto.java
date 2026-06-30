package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Estado gasto", description = "Descripcion para el modelo de Estado del gasto")
public class EstadoGastoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del tipo gasto")
	private Long id;
	
	@Schema(description = "Abreviatura del tipo gasto")
	private String abrev;
	
	@Schema(description = "Nombre completo del tipo gasto")
	private String nombre;
	
	@Schema(description = "Fecha inicio del tipo gasto")
	private Date fechaInicio;
	
	@Schema(description = "Fecha fin del tipo gasto")
	private Date fechaFin;

	@Schema(description = "Texto que se mostrará en pantalla con consideraciones a tener en cuenta en este estado")
	private String aviso;
}
