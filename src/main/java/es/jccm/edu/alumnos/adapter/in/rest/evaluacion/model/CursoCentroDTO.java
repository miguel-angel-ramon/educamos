package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;


import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Curso centro", description = "Curso centro")
public class CursoCentroDTO implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Schema(description = "id curso")
	private Long idOfermatrig;

	@Schema(description = "descripcion")
	private String descripcionCorta;
	
	@Schema(description = "tipo")
	private String tipo;
	
	@Schema(description = "orden")
	private Long orden;
}
