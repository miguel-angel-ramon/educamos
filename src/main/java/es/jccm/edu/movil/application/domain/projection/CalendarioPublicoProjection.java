package es.jccm.edu.movil.application.domain.projection;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Date;

@Schema(name = "Calendario", description = "Descripcion para el modelo calendario de festivos")
public interface CalendarioPublicoProjection {

	@Schema(description = "Identificador de la fiesta")
	Long getId();
	@Schema(description = "Fecha de la fiesta")
	LocalDate getFechaFiesta();

	@Schema(description = "Descripción de la fiesta")
	String getDescFiesta();

	@Schema(description = "Ámbito de la fiesta")
	String getAmbito();

	@Schema(description = "Indica si la fiesta afecta a los docentes")
	String getAfectaDocente();

	@Schema(description = "Indica si la fiesta no afecta a los docentes")
	String getNoAfectaDocente();

	@Schema(description = "anyo")
	String getAnyo();
}
