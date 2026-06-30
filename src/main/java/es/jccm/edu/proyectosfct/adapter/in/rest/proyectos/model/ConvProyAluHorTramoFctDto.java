package es.jccm.edu.proyectosfct.adapter.in.rest.proyectos.model;

import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Convenios a programas horario tramo alumno", description = "Descripcion para el modelo de Convenios a programas horario tramo alumno")
public class ConvProyAluHorTramoFctDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5705530794004369745L;

	@Schema(description = "Id del convenio a programa")
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
	private ConvProyAluHorPeriodoFctDto periodoAlumnoHorario;

}
