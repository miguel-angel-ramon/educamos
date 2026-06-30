package es.jccm.edu.horarios.application.domain.calendario.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Calendario", description = "Descripcion para el modelo calendario de festivos")
public interface CalendarioProjection {
	
	@Schema(description = "Id del festivo")
	Long getId();
	
	@Schema(description = "Fecha de la fiesta")
	Date getFechaFiesta();

	@Schema(description = "Anyo de la fiesta")
	Long getAnyo();

	@Schema(description = "Tipo de fiesta")
	String getTipoFiesta();
	
	@Schema(description = "Ambito")
	String getAmbito();
	
	@Schema(description = "Usuario creador")
	Long getUsuarioCreacion();
	
	@Schema(description = "Usuario que actualiza")
	Long getUsuarioActualiza();
	
	@Schema(description = "Fecha de actualizacion")
	Date getFechaActualizacion();

}
