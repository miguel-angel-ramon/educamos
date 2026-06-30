package es.jccm.edu.alumnos.application.domain.unidadesTutor.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UnidadesTutor", description = "Proyección para rescatar los tutores de una unidad y los tutores sustitutos con sus unidades")
public interface UnidadesTutorProjection {

	@Schema(description = "Id de la unidad")
	Long getIdUnidad();
	
	@Schema(description = "Descripción de la unidad")
	String getNombreUnidad();
	
	@Schema(description = "Fecha de la toma de posesion")
	Date getFechaTomaPosesion();

	@Schema(description = "Fecha cese")
	Date getFechaCese();

	@Schema(description = "Indica con 'S'")
	String getSustituye();

	@Schema(description = "Codigo centro")
	String getCodigoCentro();

}



