package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Duplicar Programación didáctica", description = "Programación didáctica a duplicar")
public interface EvaCompetenciaEspecificaDidacticaProjection {

	@Schema(description = "Id de la programación didáctica")
	Long getId();
	
	@Schema(description = "Nombre de la materia - abreviatura")
	String getNombre();
	
}
