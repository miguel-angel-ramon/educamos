package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Nivel Curricular", description = "Proyección para nivel curricular programación aula")
public interface NivelCurricularProgramacionAulaProjection {
	
	@Schema(description = "id materia")
	Long getIdOferMatrigNivelCurricular();
	
	@Schema(description = " descripcion materia")
	String getDescripcionNivelCurricular();
	
	@Schema(description = "si es acneae")
	Long getAcneae();
	
	@Schema(description = "id Programacion didactica")
	Long getIdDidactica();

	@Schema(description = "id Ponderación")
	Long getIdPonderacion();

	@Schema(description = "Abreviatura de la materia")
	String getAbrev();
	
}

