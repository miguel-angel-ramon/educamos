package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.entities;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

public class ResultadoAprendizajeEvaluacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "resultadoId")
	private Long resultadoId;
	
	@Schema(description = "resultadoNombre")
	private String resultadoNombre;

	@Schema(description = "resultadoAbv")
	private String resultadoAbv;

	@Schema(description = "evaluacionId")
	private Long evaluacionId;

	@Schema(description = "calificacionId")
	private Integer calificacionId;
	
	@Schema(description = "motivacion")
	private String motivacion;
	
	@Schema(description = "resultadoOrden")
	private Integer resultadoOrden;

}