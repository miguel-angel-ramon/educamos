package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Calificacion", description = "Calificaciones de los alumnos")
public class CalificacionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id materia de la matrícula")
	Long idMatMatricula;

	@Schema(description = "Id materia de la matrícula")
	Long idMateriaOmg;

	@Schema(description = "Id materia llave de la matrícula")
	Long idMateriaOmgLlave;

	@Schema(description = "la materia es materia llave")
	Boolean isMateriaLlave;

	@Schema(description = "Id convocatoria del centro")
	Long idConvCentroOmc;

	@Schema(description = "Id grupo actividad")
	Long idGrupoAct;
	
	@Schema(description = "Id Materia")
	Long idMateria;

	@Schema(description = "Id de la convocatoria")
	Long idConvocatoria;

	@Schema(description = "Id estado de la calificacion")
	Long idEstado;

	@Schema(description = "Estado abreviado de la calificacion")
	String nombreEstado;

	@Schema(description = "Nota media de la evaluación")
	String nota;
	
    @Schema(description = "Descripcion Estado")
    String descripcionEstado;
    
    @Schema(description = "Id Nota propuesta")
    Long idNotaPropuesta;
    
    @Schema(description = "Nota propuesta de la evaluación")
    String notaPropuesta;
    
    @Schema(description = "Aprueba materia Nota propuesta")
    String apruebaMateriaNotaPropuesta;

	@Schema(description = "Aprueba materia Nota ")
	String aprueba;

	@Schema(description = "acnee")
	Long acnee;

	@Schema(description = "materia de adaptacion acnee")
	String materiaAdap;
    
    @Schema(description = "Id unidad")
    Long idUnidad;

	@Schema(description = "Curso de la matricula")
	Long idOfertaMatrig;

	@Schema(description = "Nota de la convocatoria ordinaria")
	String notaConvocatoriaOrdinaria;

	@Schema(description = "Operador lógico que indica si la materia ya ha sido aprobada")
	Boolean materiaAprobada;

}
