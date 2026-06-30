package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Lista Calificacion", description = "Sistema de calificación de un grupo actividad")
public class ListCalificacionesDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Id calificacion")
	private Long idCalifica;
	
	@Schema(description = "Descripcion de calificacion")
	private String calificacion;
	
	@Schema(description = "Abreviatura")
	private String abreviatura;
	
	@Schema(description = "Aprueba materia")
	private String apruebaMateria;
}
