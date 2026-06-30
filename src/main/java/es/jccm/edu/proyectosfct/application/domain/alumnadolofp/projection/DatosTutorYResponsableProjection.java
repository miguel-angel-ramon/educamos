package es.jccm.edu.proyectosfct.application.domain.alumnadolofp.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Datos de encargados del plan", description = "Proyección para la información sobre el tutor")
public interface DatosTutorYResponsableProjection {

	@Schema(description = "Nombre del responsable en la empresa")
	String getNombreResponsable();

	@Schema(description = "Nombre del tutor del plan")
	String getNombreTutor();

}
