package es.jccm.edu.evaluacion.application.domain.programacionDidactica.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MateriasCentroProjection", description = "Proyección para rescatar las materias de un centro")
public interface EvaMateriasCentroProjection {

	@Schema(description = "Id de materia de curso genérica")
	Long getIdMateriaOmg();

	@Schema(description = "Nombre de la materia")
	String getNombreMateria();
	
	@Schema(description = "Descripción de la materia")
	String getDescMateria();
	
	@Schema(description = "Nombre del curso")
	String getNombreCurso();

	@Schema(description = "Abreviatura de la Materia")
	String getAbrevMateria();
	
	@Schema(description = "Abreviatura de la Materia")
	String getNivelCurricular();
}
