package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Fechas", description = "Fechas convocatoria evaluacion")
public class FechasConvocatoriaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio convocatoria")
	private Date inicio;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "es-ES", timezone = "Europe/Madrid")
	@Schema(description = "Fecha inicio convocatoria")
	private Date fin;
	
	@Schema(description = "¿Es convocatoria extraordinaria?")
	private String extraordinaria;


}
