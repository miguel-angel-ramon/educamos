package es.jccm.edu.alumnos.adapter.in.rest.evaluacion.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Promocion", description = "Promocion")
public class PromocionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id Promocion")
	private Long idPromocion;

	@Schema(description = "Id estado")
	private Long idEstado;
	
	@Schema(description = "estado")
	private String estado;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Schema(description = "fecha")
	private String fechaSesion;
}
