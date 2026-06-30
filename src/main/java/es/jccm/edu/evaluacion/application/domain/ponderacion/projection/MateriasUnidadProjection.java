package es.jccm.edu.evaluacion.application.domain.ponderacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MateriaUnidad", description = "Proyección para rescatar las materias de un profesor y las unidades dónde las imparte")
public interface MateriasUnidadProjection {

	@Schema(description = "Id de la materia")
	Long getIdMateria();

	@Schema(description = "Id del grupo actividad")
	Long getIdGrupoActividad();
	
	@Schema(description = "Nombre de la materia")
	String getTxMateria();
	
	@Schema(description = "Id de la unidad")
	Long getIdUnidad();

	@Schema(description = "Nombre de la materia")
	String getTxUnidad();
	
	@Schema(description = "Id de la modalidad")
	Long getIdModalidad();
	
	@Schema(description = "Nombre de la modalidad")
	String getTxModalidad();

	@Schema(description = "Id del curso")
	Long getIdCurso();

	@Schema(description = "Nombre del curso")
	String getTxCurso();

	@Schema(description = "Id del ciclo")
	Long getIdCiclo();

	@Schema(description = "Nombre del ciclo")
	String getTxCiclo();

	@Schema(description = "Id de la etapa")
	Long getIdEtapa();

	@Schema(description = "Nombre del ciclo")
	String getTxEtapa();
}
