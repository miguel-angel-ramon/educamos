package es.jccm.edu.proyectosfct.application.domain.gastos.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cabecera anexo VI tutores", description = "Descripcion para el modelo del anexo VI-tutores")
public interface DatosCabeceraAnexoVITutorProjection {
	
	@Schema(description = "Nombre centro")
	String getCentro();	
	
	@Schema(description = "Localidad centro")
	String getLocalidad();
	
	@Schema(description = "Código centro")
	String getCodigo();	
	
	
	@Schema(description = "Periodo facturacion")
	String getPeriodo();
	
	@Schema(description = "Director centro")
	String getDirector();	
	

}
