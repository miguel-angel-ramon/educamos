package es.jccm.edu.afirma.adapter.in.rest.afirma.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(name = "DocumentoFirmadoDto", description = "")
public class DocumentoFirmadoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "DocumentoBase64")
	private String DocumentoBase64;
	@Schema(description = "Resultado")
	private String Resultado;
	
	@Schema(description = "DENERROR")
	private String descripccion;
	
	@Schema(description = "SUBJECT")
	private String subject;
	
	@Schema(description = "CERTIFICADO")
	private String certificado;
	
	@Schema(description = "URITIPOFIRMA")
	private String uritipofirma;
	
	@Schema(description = "URIFORMAVA")
	private String uriformava;

}
