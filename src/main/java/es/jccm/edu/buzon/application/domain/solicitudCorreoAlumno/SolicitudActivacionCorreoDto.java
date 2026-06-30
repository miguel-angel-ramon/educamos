package es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno;

import java.io.Serializable;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SolicitudActivacionCorreo", description = "Solicitud de activacion del correo de los alumnos de un curso")
public class SolicitudActivacionCorreoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Schema(description = "ID de la solicitud de correo alumnado")
	private Long id;

	@Schema(description = "ID de la oferta matrícula")
	private Long idOfertaMatrig;

	@Schema(description = "ID del centro")
	private Long idCentro;

	@Schema(description = "COD del centro")
	private String codCentro;

	@Schema(description = "Fecha de solicitud")
	private Date fechaSolicitud;

	@Schema(description = "Estado de la solicitud")
	private Integer idEstado;

	@Schema(description = "Nombre del curso")
	private String nombreCurso;

	@Schema(description = "Etapa del curso")
	private String etapa;

	@Schema(description = "Nombre completo cel centro")
	private String nombreCompletoCentro;

	@Schema(description = "Título del centro")
	private String titulo;

	@Schema(description = "Número de alumnos")
	private Integer numAlumnos;
	
	@Schema(description = "Tipo de permiso (Primaria+ESO ó Bach+FP)")
	private Integer tipoPermiso;
}
