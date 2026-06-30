package es.jccm.edu.sms.adapter.in.rest.sms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "DocumentoPendienteFirmaSmsDTO", description = "")
public class DocumentoPendienteFirmaSmsDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@Schema(description = "DocumentoBase64")
	private String documentoBase64;
	
	@Schema(description = "idDocumento")
	private Long idDocumento;
	
	@Schema(description = "tipodocumento")
	private String tipodocumento;

}
