package es.jccm.edu.evaluacion.adapter.in.rest.programacionDidactica.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TipoOperacionDTO", description = "DTO Tipo Operación")
public class TipoOperacionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del Tipo de Operación")
	private Long id;

	@Schema(description = "Descripción del Tipo de Operación")
	private String descripcion;
}