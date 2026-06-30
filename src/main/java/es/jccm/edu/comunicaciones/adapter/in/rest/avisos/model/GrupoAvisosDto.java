package es.jccm.edu.comunicaciones.adapter.in.rest.avisos.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "GrupoAvisos", description = "Grupo de avisos rescatados de BBDD en función de un perfil y nivel educativo")
public class GrupoAvisosDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del grupo de avisos")
	private Integer idGrupoAvisos;
	
	@Schema(description = "Contenido del aviso")
	private String clave;

}
