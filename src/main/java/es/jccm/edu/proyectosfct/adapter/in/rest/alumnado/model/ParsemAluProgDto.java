package es.jccm.edu.proyectosfct.adapter.in.rest.alumnado.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.proyectosfct.adapter.in.rest.alumnoprograma.model.AlumnoProgramaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Alumno", description = "Descripcion para el modelo de alumno")
public class ParsemAluProgDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la tabla ParsemAluProgDto")
	private Long id;
	
	@Schema(description = "Fecha del inicio de la semana")
	private Date fechaInicioSemana;
	
	@Schema(description = "Observaciones")
	private String observaciones;
	
	@Schema(description = "Identificador rodal parte semanal")
	private String idrodal;
	
	@Schema(description = "Nombre del fichero rodal")
	private String fichero;

	@Schema(description = "Numero de dias de asistencia semanal")
	private Integer nu_dias;
	

	// ---------- Relationships -----------	
	
	@Schema(description = "Alumno convenio progama")
	private AlumnoProgramaDto aluConvProg;

}
