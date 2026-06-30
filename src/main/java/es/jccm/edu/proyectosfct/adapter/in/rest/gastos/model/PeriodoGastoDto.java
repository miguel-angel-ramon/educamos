package es.jccm.edu.proyectosfct.adapter.in.rest.gastos.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Periodos gastos", description = "Descripcion para el modelo de periodos gastos")
public class PeriodoGastoDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id del periodo gasto")
	private Long id;
	
	@Schema(description = "Anno del periodo")
	private Integer annoPeriodo;
	
	@Schema(description = "Fecha inicio del periodo")
	private Date fechaInicio;
	
	@Schema(description = "Fecha fin del periodo")
	private Date fechaFin;

}
