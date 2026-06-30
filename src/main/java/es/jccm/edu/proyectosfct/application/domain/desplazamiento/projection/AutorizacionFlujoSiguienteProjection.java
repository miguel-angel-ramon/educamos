package es.jccm.edu.proyectosfct.application.domain.desplazamiento.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Autorizacion Flujo", description = "Descripcion para el modelo de Autorizacion Estado Flujo")
public interface AutorizacionFlujoSiguienteProjection {
	
	@Schema(description = "Id gasto flujo)")
	Long getId();
	
	@Schema(description = "Descripcion abreviatura")
	String getDsabrev();
	
	@Schema(description = "Descripcion nombre")
	String getDsnombre();
	
	@Schema(description = "Fecha de inicio del estado gasto")
	Date getFhinicio();
	
	@Schema(description = "Fecha de fin del estado gasto")
	Date getFhfin();
	
	@Schema(description = "Aviso")
	String getTxaviso();
	
	@Schema(description = "Requiere adjunto")
	Integer getAdjunto();
	
	@Schema(description = "Id del perfil del flujo")
	Long getIdPerfil();	
}

