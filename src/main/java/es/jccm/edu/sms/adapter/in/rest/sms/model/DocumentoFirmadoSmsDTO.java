package es.jccm.edu.sms.adapter.in.rest.sms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "DocumentoFirmadoDto", description = "")
public class DocumentoFirmadoSmsDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	@Schema(description = "DocumentoBase64")
	private String DocumentoBase64;

}
