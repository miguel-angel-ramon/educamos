package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "Lineas de actuación", description = "Líneas de actuación asociadas a un objetivo específico")
public class LineaActuacionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fechaCreacion;

	private Long idLinAct;
	
	private Long idObjEsp;

	private String titulo;

	private String descripcion;

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;

	private String responsable;

	private String logro;

	private String instrumentos;

	private String activo;
	
	private String estado;
	
	private Integer porcentaje;

}
