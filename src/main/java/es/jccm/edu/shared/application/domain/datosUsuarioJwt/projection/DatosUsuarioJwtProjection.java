package es.jccm.edu.shared.application.domain.datosUsuarioJwt.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosUsuarioJwt", description = "Proyección para rescatar los datos de un usuario a partir de su jwt")
public interface DatosUsuarioJwtProjection {
	
	@Schema(description = "ID rownum de la consulta")
	Long getId();
	
	@Schema(description = "X_USUARIO del esquema de delphos")
	Long getXUsuarioDelphos();
	
	@Schema(description = "X_USUARIO del esquema de comunica")
	Long getXUsuarioComunica();
	
	@Schema(description = "USUARIO del esquema de delphos")
	String getUsuarioDelphos();
	
	@Schema(description = "USUARIO del esquema de comunica")
	String getUsuarioComunica();
	
	@Schema(description = "X_EMPLEADO del esquema de delphos")
	Long getIdEmpleadoDelphos();
	
	@Schema(description = "X_EMPLEADO del esquema de comunica")
	Long getIdEmpleadoComunica();
	
	@Schema(description = "NIF del usuario")
	String getNif();

	@Schema(description = "email del usuario")
	String getEmail();

	@Schema(description = "fecha del usuario")
	Date getFNacimiento();
}

