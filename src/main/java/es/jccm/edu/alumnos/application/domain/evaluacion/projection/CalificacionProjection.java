package es.jccm.edu.alumnos.application.domain.evaluacion.projection;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Calificacion", description = "Calificaciones de los alumnos")
public interface CalificacionProjection {

	@Schema(description = "Id materia de la matrícula")
	Long getIdMatMatricula();

	@Schema(description = "Id convocatoria del centro")
	Long getIdConvCentroOmc();

	@Schema(description = "Id Materia")
	Long getIdMateria();

	@Schema(description = "Id Materia")
	Long getIdMateriaOmg();
	
	@Schema(description = "Id grupo actividad")
	Long getIdGrupoAct();

	@Schema(description = "Id de la convocatoria")
	Long getIdConvocatoria();

	@Schema(description = "Id estado de la calificacion")
	Long getIdEstado();

	@Schema(description = "Estado abreviado de la calificacion")
	String getNombreEstado();

	@Schema(description = "Nota media de la evaluación")
	String getNota();
	
	@Schema(description = "Descripcion estado")
	String getDescripcionEstado();
	
    @Schema(description = "Id Nota propuesta")
    Long getIdNotaPropuesta();
    
    @Schema(description = "Nota propuesta de la evaluación")
    String getNotaPropuesta();
    
    @Schema(description = "Aprueba materia Nota propuesta")
    String getApruebaMateriaNotaPropuesta();

	@Schema(description = "Aprueba materia Nota ")
	String getAprueba();

	@Schema(description = "la materia es acnee")
	Long getAcnee();

	@Schema(description = "materia de adaptación del nivel acnee")
	String getMateriaAdap();
    
    @Schema(description = "IdUnidad")
	Long getIdUnidad();

	@Schema(description = "Curso de la matricula")
	Long getIdOfertaMatrig();

	@Schema(description = "Nota de la convocatoria ordinaria")
	String getNotaConvocatoriaOrdinaria();

	@Schema(description = "Operador lógico que indica si la materia ya ha sido aprobada")
	Boolean getMateriaAprobada();

}
