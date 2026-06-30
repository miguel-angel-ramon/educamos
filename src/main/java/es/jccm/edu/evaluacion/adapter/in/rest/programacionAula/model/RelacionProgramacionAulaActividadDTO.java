package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import es.jccm.edu.shared.application.domain.baseAudited.BaseAudited;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RelacionProgramacionAulaActividadDTO", description = "DTO Relacion ProgramacionAula - Actividad")
public class RelacionProgramacionAulaActividadDTO extends BaseAudited implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Relacion ProgramacionAula - Actividad")
	private Long id;

	@Schema(description = "Actividad")
	private ActividadDTO actividad;
}