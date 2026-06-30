package es.jccm.edu.buzon.application.domain.solicitudCorreoAlumno;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public interface UnidadProjection {

	@Schema(description = "Id de la unidad de los alumnos")
	Long getIdUnidad();

	@Schema(description = "Nombre de la unidad de los alumnos")
	String getNombreUnidad();

}
