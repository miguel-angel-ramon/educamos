package es.jccm.edu.documentosGC.adapter.in.rest.actaevaluacionespa.model;

import java.io.Serializable;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "acta evaluacion", description = "Acta evaluacion")
public class RegSelDocESPADto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "Id")
	private Long idIdentificador;
	
	@Schema(description = "Id Clave 1")
	private Long idClave1;
	
	@Schema(description = "Codigo clave 1")
	private String codigoClave1;
	
	@Schema(description = "Id clave 2")
	private Long idClave2;
	
	@Schema(description = "Codigo clave 2")
	private String codigoClave2;
	
	@Schema(description = "Fecha clave 2")
	private Date fechaClave2;
	
	@Schema(description = "Rango desde")
	private Long rangoDesde;
	
	@Schema(description = "Rango hasta")
	private Long rangoHasta;
	
	@Schema(description = "Fecha Rango Desde")
	private Date fechaRangoDesde;

	@Schema(description = "Fecha Rango Hasta")
	private Date fechaRangoHasta;	
	
	@Schema(description = "Codigo adicional 1")
	private String codigoDatoAdicional1;		
	
	@Schema(description = "Codigo adicional 2")
	private String codigoDatoAdicional2;		
	
	@Schema(description = "Codigo adicional 3")
	private String codigoDatoAdicional3;	

}
