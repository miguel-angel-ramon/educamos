package es.jccm.edu.evaluacion.adapter.in.rest.aulaVirtual.model;
import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ContenidoActividadDTO", description = "DTO Contenido Actividad")
public class ContenidoActividadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private List<CalificacionActividadDTO> usergrades;
}