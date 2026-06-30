package es.jccm.edu.horarios.adapter.in.rest.dependencias.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "Dependencia", description = "Entidad para rescatar las dependencias de un profesor")
public class TipoDependenciaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Nombre")
	private String nombre;
	

}
