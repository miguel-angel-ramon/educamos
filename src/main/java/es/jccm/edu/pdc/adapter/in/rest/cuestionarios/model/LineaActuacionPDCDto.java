package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(name = "Lineas de actuación", description = "Líneas de actuación asociadas a un objetivo específico")
public class LineaActuacionPDCDto implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;

	String titulo;

	String descripcion;
	
	Date fechaCreacion;

	Date fechaInicio;

	Date fechaFin;

	String responsable;

	String logro;

	String instrumentos;
	
	Integer porcentaje;
	
	Date fechaInicioEjecucion;
	
	Date fechaFinEjecucion;
	
	String tareas;
	
	String valoracion;
	
	String dificultades;
	
	String comentarios;

}
