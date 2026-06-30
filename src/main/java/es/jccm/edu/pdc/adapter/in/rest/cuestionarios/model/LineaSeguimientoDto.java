package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "Lineas de seguimiento", description = "Líneas de seguimiento asociadas a un objetivo específico")
public class LineaSeguimientoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idSeguiLinAct;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioEjecucion;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinEjecucion;
	
	private Integer porcentaje;

	private String tareas;
	
	private String valoracion;
	
	private String dificultades_acciones;
	
	private String comentarios;
	
	private Long idLinAct;
	
	private Long idUsuCreacion;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaCreacion;

	private Long idUsuActualiza;
	 
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaActualizacion;

}
