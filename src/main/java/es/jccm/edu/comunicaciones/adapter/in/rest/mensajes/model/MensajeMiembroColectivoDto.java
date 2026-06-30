package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MensajeMiembroColectivo", description = "Miembros del colectivo rescatados para el módulo de escritorio")
public class MensajeMiembroColectivoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del miembro del colectivo")
	private Long indice;
	
	@Schema(description = "Descripción del miembro del colectivo")
	private String descripcion;

	public Long getId() {
	    return this.indice;
	}

}
