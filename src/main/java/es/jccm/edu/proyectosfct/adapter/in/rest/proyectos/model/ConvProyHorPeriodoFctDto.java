package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorPeriodoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorTramoFctDto;
import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Empresas a proyectos horario tramo", description = "Descripcion para el modelo de Empresas a convenios horario tramo")
public class ConvProyHorPeriodoFctDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5705530794004369745L;

	@Schema(description = "Id del convenio a programa")
	private Long id;
	
	@Schema(description = "Anno del periodo")
	private Integer annoPeriodo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha inicio del periodo")
	private Date fechaInicio;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha fin del periodo")
	private Date fechaFin;
	
	@Schema(description = "Número de horas")
	private Double nHoras;
	
	@Schema(description = "Identificar del convenio proyecto")
	private ConveniosProyectoDto convenioProyecto;
	
	@Schema(description = "Tramos horarios asociados")
	private List<ConvProyHorTramoFctDto> tramosHorarios;

}
