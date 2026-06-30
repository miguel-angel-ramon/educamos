package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Evaluacion", description = "Evaluacion en base a las lineas de actuación")
public class EvaluacionHomeDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long xSeguiLinAct;
	
	private Integer porcEjec;
	
	private String estado;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date factualizacion;
	
}