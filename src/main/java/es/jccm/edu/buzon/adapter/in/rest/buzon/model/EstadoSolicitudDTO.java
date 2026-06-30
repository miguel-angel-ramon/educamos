package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "EstadoSolicitudCorreoAlumno", description = "Estado de las Solicitudes del correo alumno")
public class EstadoSolicitudDTO {
	
	@Schema(description = "Id del Estado")
    Long idEstado;
	
	@Schema(description = "Descripcion del Estado")
    String descripcionEstado;
}

