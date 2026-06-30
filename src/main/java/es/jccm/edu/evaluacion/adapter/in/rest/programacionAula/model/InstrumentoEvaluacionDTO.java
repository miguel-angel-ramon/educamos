package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "InstrumentoEvaluacionDTO", description = "DTO Instrumento Evaluación")
public class InstrumentoEvaluacionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Instrumento Evaluación")
	private Long id;

	@Schema(description = "Descripción del Instrumento Evaluación")
	private String descripcion;
	
	@Schema(description = "Abreviatura del Instrumento Evaluación")
	private String abreviatura;
}