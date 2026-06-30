package es.jccm.edu.documentosGC.adapter.in.rest.documentosGC.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Contadores inicio", description = "Modelo para los contadores del menu de inicio")
public class ContadoresInicioDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "Id")
	private String idTipo;	
	
	@Schema(description = "Numero total de documentos")
	private Integer nutotal;
	
	@Schema(description = "Numero de documentos obligatorios")
	private Integer nuobl;
	
	@Schema(description = "Numero de documentos pedientes de firmar")
	private Integer nupf;
	
	@Schema(description = "Numero de documentos pedientes de mi firma")
	private Integer numifir;
	

}
