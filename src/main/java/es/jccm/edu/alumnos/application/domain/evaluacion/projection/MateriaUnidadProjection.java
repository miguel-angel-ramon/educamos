package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "Materia Unidad", description = "Materia Unidad")
public interface MateriaUnidadProjection {

	@Schema(description = "Id Materia")
	Long getIdMateria();
	
	@Schema(description = "Nombre materia")
	String getNombreMateria();

	@Schema(description = "Abreviatura")
	String getAbreviatura();
	
	@Schema(description = "Id Etapa")
	Long getIdEtapa();
	
	@Schema(description = "Materia")
	Long getIdMateriaOmg();
	
	@Schema(description = "Oferta")
	Long getIdOfertaMatrig();
	
	@Schema(description = "lomloe")
	Long getLomloe();

	@Schema(description = "Curso en el que se encuentra la materia")
	String getCursoMateria();

}
