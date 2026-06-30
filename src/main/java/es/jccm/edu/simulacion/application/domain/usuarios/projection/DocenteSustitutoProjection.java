package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Docente sustituto", description = "Rescata los docentes sustitutos")
public interface DocenteSustitutoProjection {
	
	@Schema(description = "Id del empleado de Delphos")
	Long getIdEmpleadoDelphos();
	
	@Schema(description = "Id del empleado de Comunica")
	Long getIdEmpleadoComunica();
	
	@Schema(description = "Fecha de la toma de posesion")
	Date getFechaTomaPosesion();
	
	@Schema(description = "Fecha cese")
	Date getFechaCese();
	
	@Schema(description = "Id del centro")
	Long getIdCentro();
	
	@Schema(description = "Año académico")
	Integer getAnno();
	
	@Schema(description = "oid usuario")
	Long getOidUsuario();

}
