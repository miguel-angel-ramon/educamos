package es.jccm.edu.buzon.adapter.in.rest.buzon.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import lombok.Data;

@Data
@Schema(name = "SolicitudCorreoAlumno", description = "Solicitudes por correo alumno")
public class SolicitudCorreoUsuarioDTO {
	
	/*@Schema(description = "Id de la solicitud")
    Long idSolicitud;
	
	@Schema(description = "Id del curso")
    Long idCorrAlum;
	
	@Schema(description = "Id del centro")
    Long idCentro;
	
	@Schema(description = "Fecha de la solicitud")
    Date fechaSolicitud;
	
	@Schema(description = "Estado de la solicitud")
    Long estadoSolicitud;
	
	@Schema(description = "Número de alumnos en la solicitud")
    Long numAlumnosT;
	
	@Schema(description = "Centro")
    String centro;*/
	
	@Schema(description = "Id de la solicitud")
    Long idSolicitud;
	
	@Schema(description = "Id del curso")
    Long idOfertamatricCurso;
	
	@Schema(description = "Id del centro")
    Long idCentro;
	
	@Schema(description = "Fecha de la solicitud")
    Date fechaSolicitud;
	
	@Schema(description = "Estado de la solicitud")
    Long estadoSolicitud;

}
