package es.jccm.edu.proyectosfct.application.domain.gastos.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Datos histórico", description = "Descripcion completa de la tabla historico flujo gastos")
public interface HistoricoFlujoGastosProjection {
	
	@Schema(description = "Nombre")
	String getNombre();	
	
	@Schema(description = "Estado")
	String getEstado();
	
	@Schema(description = "Fecha")
	Date getFecha();	
	
	@Schema(description = "Estado actual")
	String getActual();	
	
	@Schema(description = "Observaciones")
	String getObservaciones();	
	
	@Schema(description = "Estado abreviatura")
	String getAbreviatura();	


}
