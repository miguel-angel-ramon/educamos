package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "partesGenerados", description = "Partes generados DTO")
public class ParteGeneradoDocumentosGCDto {
	
	private static final long serialVersionUID = 1L;
	
	
	@Schema(description = "Id")
private String id;
	
	@Schema(description = "Fecha remision")

	private Date fremision;

	@Schema(description = "Fecha generacion")

	private Date fgenera;
	
	@Schema(description = "Numero de orden")

	private String norden;
	
	@Schema(description = "Numero de mes")

	private String nmes;
	
	@Schema(description = "Año del parte")

	private String canno;
	
	@Schema(description = "Si ya hay un parte generado")

	private String lgenerado;

	
}