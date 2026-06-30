package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Evaluacion", description = "Descripcion para el modelo de evaluacion")
public class ObjetivoEspecificoEvaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idObjEsp;
	
	private Long idObjetivo;
	
	private Long idCentro;
	
	private String descripcion;
	
	private Integer anno;

}
