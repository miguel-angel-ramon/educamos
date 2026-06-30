package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Competencias Específicas", description = "Competencias Específicas de la programación didáctica")
public interface EvaDuplicarProgramacionDidacticaProjection {

	@Schema(description = "Id de la competecia")
	Long getId();
	
	@Schema(description = "descripcion")
	String getDescripcion();
	
	@Schema(description = "abreviatura")
	String getAbrev();
	
	@Schema(description = "id del ciclo")
	Long getIdCiclo();

	@Schema(description = "orden")
	Integer getNOrdenPres();
}
