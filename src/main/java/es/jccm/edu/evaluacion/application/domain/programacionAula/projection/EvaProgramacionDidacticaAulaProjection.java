package es.jccm.edu.evaluacion.application.domain.programacionAula.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Programación didáctica de aula")
public interface EvaProgramacionDidacticaAulaProjection {
	
	@Schema(description = "Id porgramación didáctica")
	Long getId();

	@Schema(description = "")
	Long getMateriaomg();

	@Schema(description = "")
	Long getOfertamatrig();

	@Schema(description = "")
	Long getCentro();

	@Schema(description = "")
	Long getAnno();
	
	@Schema(description = "")
	Long getLAcneae();

	@Schema(description = "")
	Long getCerrada();
	
	@Schema(description = "")
	String getNombreMateria();
	
	@Schema(description = "")
	String getNombreCurso();
}
