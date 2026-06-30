package es.jccm.edu.horarios.adapter.in.rest.horarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "AtributosPlanificacionSemanal", description = "Proyección para rescatar los atributos necesarios para la planificación semanal")
public class AtributosPlanificacionSemanalDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador")
	private Long identificador;
	
	@Schema(description = "Fecha")
	@JsonFormat(pattern = "yyyy/MM/dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fecha;
	
	@Schema(description = "Descripción")
	private String descripcion;

	@Schema(description = "Tipo")
	private String tipo;

}
