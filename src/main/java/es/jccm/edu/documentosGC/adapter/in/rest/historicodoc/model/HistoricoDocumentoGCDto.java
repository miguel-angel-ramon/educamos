package es.jccm.edu.documentosGC.adapter.in.rest.historicodoc.model;


import java.util.Date;
import es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model.DocumentosGCDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import es.jccm.edu.documentosGC.adapter.in.rest.flujodoc.model.FlujoDocumentoGCDto;


@Data
@Schema(name = "HistoricoDocumentoGCDto", description = "Descripcion para el modelo del hitorico Documentos de Gestión de Centro")
public class HistoricoDocumentoGCDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Fecha registro")
	private Date registro;
	
	@Schema(description = "Observaciones")
	private String observaciones;
	
	@Schema(description = "Identificador fichero Rodal")
	private String idRodal;	
	
	@Schema(description = "Nombre fichero Rodal")
	private String txFicheroRodal;		
	
	@Schema(description = "Id del usuario")
	private Long usuario;
	
	// ---------- Relationships -----------
	
	@Schema(description = "Id del documento")
	private DocumentosGCDto documento;
	
	@Schema(description = "Flujo del documento")
	private FlujoDocumentoGCDto flujo;
	

}
