package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;
import java.util.Date;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UnidadCentroDTO", description = "DTO Unidad Centro")
public class UnidadCentroDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Unidad Centro")
	private Long id;

	@Schema(description = "Año Académico de la Unidad Centro")
	private Integer anno;
	
	@Schema(description = "Fecha de toma de posesión de puesto de trabajo")
	private Date fechaTomaPosesion;
	
	@Schema(description = "Periodo")
	private Integer periodo;
	
	@Schema(description = "Número de orden de la Unidad, a efectos de presentación")
	private Integer orden;
	
	@Schema(description = "Tipo de Unidad. ('P' = Pura, 'M' = Mixta.")
	private String tipo;
}