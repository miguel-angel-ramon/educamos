package es.jccm.edu.documentosGC.adapter.in.rest.plazoentrega.model;

import java.util.Date;
import es.jccm.edu.documentosGC.application.domain.tipodoc.entities.TipoDocumentosGC;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Tipo documentos GC", description = "Descripcion para el modelo de Tipos de Documentos Gestión de Centros")
public class PlazosEntregaDto {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private Long id;
	
	@Schema(description = "Anno")
	private Integer cAnno;
	
	@Schema(description = "Fecha de inicio del plazo")	
	private Date fechaInicio;
	
	@Schema(description = "Fecha de fin del plazo")
	private Date fechaFin;
	
	@Schema(description = "Tipo de documento")
	private TipoDocumentosGC tipo;

}
