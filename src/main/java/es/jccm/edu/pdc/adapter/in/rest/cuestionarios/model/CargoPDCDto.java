package es.jccm.edu.pdc.adapter.in.rest.cuestionarios.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Cargo", description = "Descripcion para el modelo de cargo")
public class CargoPDCDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codCargo;

	private String descripcionCargo;
	
}
