package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "curso documentos GC", description = "Descripcion para el modelo de meses de curso")
public class CursoProjectionDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "parte")
	private String parte;
	
	@Schema(description = "Número de mes")
	private Integer mes;
	
	@Schema(description = "Año natural")
	private String annonatural;
	
}
