package es.jccm.edu.simulacion.adapter.in.rest.usuarios.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Docente sustituto list", description = "Rescata la lista de docentes sustitutos")
public class DocenteSustitutoListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "¿Este docente está sustituyendo a otros docentes?")
	private Boolean sustituto;
	
	@Schema(description = "Docentes sustitutos")
	private List<DocenteSustitutoDTO> docentes;
	
}
