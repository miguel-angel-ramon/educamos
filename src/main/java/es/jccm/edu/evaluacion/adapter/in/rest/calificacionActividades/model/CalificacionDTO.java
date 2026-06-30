package es.jccm.edu.evaluacion.adapter.in.rest.calificacionActividades.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CalificacionDTO", description = "DTO Calificación")
public class CalificacionDTO {
	
	@Schema(description = "Id del Actividad")
	private Long id;

	@Schema(description = "Código de la calificación usada en Senu, en función del Sitema de Calificación de SENU")
	private String codigoSenu;

	@Schema(description = "Descripción larga de Calificación")
	private String descripcion;
	
	@Schema(description = "Indica si con esta Calificación se aprueba")
	private String lAprueba;
	
	@Schema(description = "Indica si la matrícula está cerrada")
	private String lMatriculaCerrada;
	
	@Schema(description = "Indica si renuncia")
	private String lRenuncia;
	
	@Schema(description = "Expresión numérica de la Calificación")
	private Integer numero;
	
	@Schema(description = "Orden ocupado en la escala de Calificación.")
	private Integer orden;

	@Schema(description = "Descripción corta de la Calificación")
	private String descripcionCorta;
	
	@Schema(description = "Abreviatura de Calificación.")
	private String abreviatura;

	// ---------- Relationships -----------

	@Schema(description = "Identificador del Sistema de Calificación")
	private Long idSistema;

}