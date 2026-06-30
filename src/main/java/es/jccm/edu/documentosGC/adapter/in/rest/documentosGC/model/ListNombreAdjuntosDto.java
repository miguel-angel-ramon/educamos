package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Lista de nombre", description = "Descripcion para el modelo nombre de documentos en uso")
public class ListNombreAdjuntosDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Identificador del tipo de documento")
	private Long idTipoDocumento;
	
	@Schema(description = "Identificador del centro")
	private Long idCentro;
	
	@Schema(description = "Lista de nombres de documentos a comprobar")
	private List<String> lsNames;
	

}
