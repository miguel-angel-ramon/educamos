package es.jccm.edu.pdc.application.domain.planActuacion.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(name = "Objetivos específicos", description = "Proyección para rescatar los objetivos específicos")
public interface LineaActuacionProjection {
	
	@Schema(description = "Fecha de creación de la línea de actuación")
	String getFechaCreacion();
	
	@Schema(description = "Id de la línea de actuación")
	Long getIdLinAct();

	@Schema(description = "Título de la línea de actuación")
	String getTitulo();

	@Schema(description = "Descripción de la línea de actuación")
	String getDescripcion();

	@Schema(description = "Fecha de inicio línea de actuación")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date getFechaInicio();

	@Schema(description = "Fecha de fin línea de actuación")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date getFechaFin();

	@Schema(description = "Responsable de la línea de actuación")
	String getResponsable();

	@Schema(description = "Logro de la línea de actuación")
	String getLogro();

	@Schema(description = "Instrumentos de la línea de actuación")
	String getInstrumentos();

	@Schema(description = "Indica si la línea de actuación está activa")
	String getActivo();
	
	@Schema(description = "Indica el estado de la línea de actuación")
	String getEstado();
	
	@Schema(description = "Indica el porcentaje")
	Integer getPorcentaje();
}

