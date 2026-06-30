package es.jccm.edu.pdc.application.domain.cuestionarios.projection;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Curso académico", description = "Proyección para rescatar los datos del curso académico")
public interface CursoAcademicoProjection {
	@Schema(description = "Fecha inicio curso académico")
	Date getInicio();
	
	@Schema(description = "Fecha fin curso académico")
	Date getFin();

}
