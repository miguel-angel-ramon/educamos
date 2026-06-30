package es.jccm.edu.simulacion.application.domain.usuarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DatosUsuario", description = "Proyección para rescatar los datos de un usuario")
public interface CentroUsuarioProjection {
	
	@Schema(description = "Código del centro")
	Long getCodCentro();
	
	@Schema(description = "Id del centro")
	Long getIdCentro();
	
	@Schema(description = "Denominación del centro")
	String getDenominacionCentro();
	
	@Schema(description = "Nombre del centro")
	String getNombreCentro();
	
	@Schema(description = "Id del perfil")
	Long getIdPerfil();
	
	@Schema(description = "Fecha de toma de posesión")
	Date getFechaTomaPosesion();
	
}

