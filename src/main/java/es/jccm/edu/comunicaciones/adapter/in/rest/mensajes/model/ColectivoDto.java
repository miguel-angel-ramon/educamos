package es.jccm.edu.comunicaciones.adapter.in.rest.mensajes.model;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Colectivo", description = "Colectivos rescatados")
public class ColectivoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id del colectivo")
	private Long id;
	
	@Schema(description = "Código del colectivo")
	private String codigoColectivo;
	
	@Schema(description = "Descripción corta del colectivo")
	private String descripcionCortaColectivo;
	
	@Schema(description = "Descripción larga del colectivo")
	private String descripcionLargaColectivo;
	
	@Schema(description = "Permite filtrar al colectivo")
	private String permiteFiltrar;
	
	@Schema(description = "Permite respuesta al colectivo")
	private String permiteRespuesta;
	
	@Schema(description = "Perfil")
	private Long xPerfil;
	
}
