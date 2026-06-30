package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "Estado Promocion", description = "Estado Promocion")
public class EstadoPromocionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "Resultado id")
	Long cResultado;

	@Schema(description = "Descripcion")
	String descripcion;

	@Schema(description = "abreviatura")
	String abrev;
	
	@Schema(description = "Id estado")
	Long idEstado;
}
