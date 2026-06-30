package es.jccm.edu.proyectosfct.adapter.in.rest.alumnadoLOFP.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Periodos plan", description = "Descripcion para el modelo de periodos del seguimiento alumnado")
public class PeriodoLOFPDto {

	@Schema(description = "Número del periodo")
	private String periodoNumero;

	@Schema(description = "Fecha inicio del periodo")
	private String fechaInicioPeriodo;

	@Schema(description = "Fecha fin del periodo")
	private String fechaFinPeriodo;

	@Schema(description = "Flag que indica si el periodo es el actual o no")
	private Integer esPeriodoActual;

	@Schema(description = "Flag que indica si el periodo es el visible o no")
	private Integer periodoVisible;

	private List<MesLOFPDto> meses;

}
