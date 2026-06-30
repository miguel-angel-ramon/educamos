package es.jccm.edu.pdc.application.domain.cuestionarios.projection;


import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Cuestionario", description = "Proyección para rescatar los datos de un cuestionario")
public interface CuestionarioProjection {
	@Schema(description = "Indica el año del cuestionario")
	Integer getAnio();
	
	@Schema(description = "Indica el id del cuestionario del usuario")
	Long getIdCuePubUsu();

	@Schema(description = "Indica el id de cuepub")
	Long getIdCuePub();

	@Schema(description = "Id del cuestionario")
	Long getIdCuestionario();
	
	@Schema(description = "Nombre del cuestionario")
	String getNombre();
	
	@Schema(description = "Descripción del cuestionario")
	String getDescripcion();

	@Schema(description = "Indica si el cuestionario está activo")
	boolean isActivo();

	@Schema(description = "Indica si el cuestionario ha sido presentado")
	boolean isPresentado();
	
	@Schema(description = "Indica la fecha de inicio de las respuestas")
	Date getFInicioRespuestas();
	
	@Schema(description = "Indica la fecha fin de las respuestas")
	Date getFFinRespuestas();
}

