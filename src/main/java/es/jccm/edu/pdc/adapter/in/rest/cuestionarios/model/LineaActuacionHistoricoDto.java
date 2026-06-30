package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "Lineas de actuación", description = "Líneas de actuación asociadas a un objetivo específico")
public class LineaActuacionHistoricoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date fechaCreacion;

	private Long id;

	private String titulo;

	private String descripcion;

	private Date fechaInicio;

	private Date fechaFin;

	private String responsable;

	private String logro;

	private String instrumentos;
	
	private Integer porcentaje;
	
	private Date fechaInicioEjecucion;
	
	private Date fechaFinEjecucion;
	
	private String tareas;
	
	private String valoracion;
	
	private String dificultades;
	
	private String comentarios;
	
	private String descripcionObjetivoEspecifico;
	
	private String estado;

}
