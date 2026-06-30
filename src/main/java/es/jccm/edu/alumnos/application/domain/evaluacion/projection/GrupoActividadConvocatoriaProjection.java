package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Grupo Actividad Convocatoria", description = "Proyección para rescatar los datos de los Grupos de Actividad")
public interface GrupoActividadConvocatoriaProjection {

	@Schema(description = "Id Grupo Actividad")
	Long getIdGrupoActividad();
	
	@Schema(description = "nombre Grupo Actividad")
	String getNombre();
	
	@Schema(description = "abreviatura Grupo Actividad")
	String getAbreviatura();
	
	@Schema(description = "estado Grupo Actividad")
	String getEstado();
	
	@Schema(description = "IdUnidad")
	String getIdUnidad();
	
	@Schema(description = "idEtapa")
	String getIdEtapa();
	
	@Schema(description = "unidad")
	String getunidad();
	
	@Schema(description = "Materia")
	Long getIdMateriaOmg();
	
	@Schema(description = "unidad")
	Long getIdOfertaMatrig();
	
	@Schema(description = "lomloe")
	Long getLomloe();
	
	@Schema(description = "Id ConvUnidad")
	Long getIdConvUnidad();

	@Schema(description = "Curso")
	String getCurso();
	@Schema(description = "si el grupo actividad tiene varios materiaOmg")
	Long getCra();
}
