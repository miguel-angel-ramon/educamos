package es.jccm.edu.evaluacion.adapter.in.rest.programacionAula.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "UnidadPorMateriaDTO", description = "DTO Unidad por Materia")
public class UnidadPorMateriaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id de la Unidad")
	private Long idUnidad;

	@Schema(description = "Nombre que el centro da a la unidad")
	private String nombreUnidad;
	
	@Schema(description = "Id del Centro")
	private Long idCentro;
	
	@Schema(description = "Si afecta a todos los alumnos")
	private int afectaTodos = 0;
	
}