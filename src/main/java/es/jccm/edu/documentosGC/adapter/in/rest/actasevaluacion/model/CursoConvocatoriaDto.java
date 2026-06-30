package es.jccm.edu.documentosGC.adapter.in.rest.actasevaluacion.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "curso convocatoria", description = "Curso Convocatoria")
public class CursoConvocatoriaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Descripcion curso")
	private String descripcion;	

}
