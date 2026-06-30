package es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno;

import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;

public interface SolicitudActivacionCorreoProjection {

	@Schema(description = "ID de la solicitud de correo alumnado")
	Long getId();

	@Schema(description = "ID de la oferta matricula")
	Long getIdOfertamatrig();
	
	@Schema(description = "ID del centro")
	Long getIdCentro();

	@Schema(description = "COD del centro")
	String getCodCentro();

	@Schema(description = "Fecha de solicitud")
	Date getFechaSolicitud();

	@Schema(description = "Estado de la solicitud")
	Integer getIdEstado();

	@Schema(description = "Nombre del curso")
	String getNombreCurso();

	@Schema(description = "Etapa del curso")
	String getEtapa();

	@Schema(description = "Nombre completo del centro")
	String getNombreCompletoCentro();

	@Schema(description = "Título del centro")
	String getTitulo();

	@Schema(description = "Número de alumnos")
	Integer getNumAlumnos();

	@Schema(description = "Tipo de permiso (Primaria+ESO ó Bach+FP)")
	Long getTipoPermiso();
}
