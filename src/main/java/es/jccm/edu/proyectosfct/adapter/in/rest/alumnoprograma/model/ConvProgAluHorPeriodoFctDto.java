package es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConveniosProgramasFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a programas horario periodo", description = "Descripcion para el modelo de Convenios a programas horario periodo")
public class ConvProgAluHorPeriodoFctDto implements Serializable {
	
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
	
	@Schema(description = "Identificar del convenio programa")
	private ConveniosProgramasFctDto convenioPrograma;
	
	@Schema(description = "Identificar del convenio programa")
	private Long idMatricula;
	
	@Schema(description = "Número de horas periodo")
	private Double nHoras;
	
	@Schema(description = "Tramos horarios asociados")
	private List<ConvProgAluHorTramoFctDto> tramosHorarios;
	
	@Schema(description = "Número horas")
	private Double nhoras;
}
