package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import es.jccm.edu.proyectosfct.adapter.in.rest.conveniosprogramas.model.ConvProgHorPeriodoFctDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a programas horario tramo", description = "Descripcion para el modelo de Convenios a proyectos horario tramo")
public class ConvProyHorTramoFctDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5705530794004369745L;
	
	@Schema(description = "Id del convenio a proyectos")
	private Long id;
	
	@Schema(description = "Fecha inicio del convenio al programa")
	private Integer ordenTramo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Schema(description = "Fecha fin del convenio al programa")
	private Integer diaSemana;
	
	@Schema(description = "Identificar del convenio asociado")
	private Integer horaInicio;
	
	@Schema(description = "Identificar de los programas asociados al convenio")
	private Integer horaFin;
	
	@Schema(description = "Representante de la empresa que firma el convenio asociado al programa")
	private ConvProyHorPeriodoFctDto periodoHorario;

}
