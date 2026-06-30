package es.jccm.edu.documentosGC.adapter.in.rest.flujodoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;
import es.jccm.edu.documentosGC.adapter.in.rest.estadodoc.model.EstadoDocumentoGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.perfiles.model.PerfilGCDto;
import es.jccm.edu.documentosGC.adapter.in.rest.tipodoc.model.TipoDocumentoGCDto;

@Data
@Schema(name = "FlujoDocDto", description = "Descripcion para el modelo del flujo Documentos de Gestión de Centro")
public class FlujoDocumentoGCDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Requiere adjunto 0/1")
	private Integer adjunto;	
	
	@Schema(description = "Esta borrado 0/1")
	private Integer borrado;	
	
	@Schema(description = "Fecha borrado")
	private Date fechaBorrado;	
	
	@Schema(description = "Usuario que borra el registro")
	private Long usuBorrado;
	
	// ---------- Relationships -----------

	@Schema(description = "Id del perfil")
	private PerfilGCDto perfil;
	
	@Schema(description = "Id estado origen")
	EstadoDocumentoGCDto origen;
	
	@Schema(description = "Id estado destino")
	EstadoDocumentoGCDto destino;
	
	@Schema(description = "Id tipo")
	TipoDocumentoGCDto tipo;

}
