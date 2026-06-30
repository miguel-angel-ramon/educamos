package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(name = "Evaluacion", description = "Descripcion para el modelo de evaluacion visión global")
public class EvaluacionVisionGlobalDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idObjetivo;
	
	private Long idCentro;
	
	private Long idObjLinAct;
	
	private Long idSeguimiento;
	
	private String objetivo;

	private Long idCompetencia;

	private String competencia;
	
	private String tituloLinAct;
	
	private String responsable;
	
	private Integer anno;
	
	private Double porcentaje;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFin;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaInicioEjecucion;
	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	private Date fechaFinEjecucion;
	
	

}
