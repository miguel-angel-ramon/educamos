package es.jccm.edu.buzon.application.domain.buzon.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CursoSolicitudProjection", description = "Proyección para rescatar los cursos de los centros con los datos necesarios")
public interface CursoSolicitudProjection {
	
	@Schema(description = "Id de la etapa")
	Long getIdEtapa();
	
	@Schema(description = "Nombre de la etapa")
	String getNombreEtapa();
	
	@Schema(description = "Id del curso")
	Long getIdCurso();
	
	@Schema(description = "Nombre del curso")
	String getNombreCurso();
	
	@Schema(description = "Estado de la solicitud")
	String getEstadoSolicitud();
	
	@Schema(description = "Número de alumnos con correo habilitado")
	Long getNumAlumNoHab();
	
	@Schema(description = "Número de alumnos con correo habilitado")
	Long getNumAlumHab();
	
	@Schema(description = "Fecha de la última solicitud")
	Date getFechaSolicitud();

}
