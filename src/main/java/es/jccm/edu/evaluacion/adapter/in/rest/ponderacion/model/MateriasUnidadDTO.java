package es.jccm.edu.evaluacion.adapter.in.rest.ponderacion.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MateriasUnidad", description = "Materias y unidades dónde se imparten")
public class MateriasUnidadDTO implements Serializable	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id de la materia")
	private Long idMateria;

	@Schema(description = "Id del grupo actividad")
	private Long idGrupoActividad;
	
	@Schema(description = "Nombre de la materia")
	private String txMateria;
	
	@Schema(description = "Id de la unidad")
	private Long idUnidad;
	
	@Schema(description = "Nombre de la materia")
	private String txUnidad;
	
	@Schema(description = "Id de la modalidad")
	private Long idModalidad;
	
	@Schema(description = "Nombre de la modalidad")
	private String txModalidad;

	@Schema(description = "Id del curso")
	private Long idCurso;

	@Schema(description = "Nombre del curso")
	private String txCurso;

	@Schema(description = "Id del ciclo")
	private Long idCiclo;

	@Schema(description = "Nombre del ciclo")
	private String txCiclo;

	@Schema(description = "Id de la etapa")
	private Long idEtapa;

	@Schema(description = "Nombre de la etapa")
	private String txEtapa;

	@Schema(description = "¿Está la materia ponderada?")
	private Boolean estaPonderada;

	@Schema(description = "Define si la materia tiene nivel curricular en BBDD")
	private Boolean existenCompetencias;
}
