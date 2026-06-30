package es.jccm.edu.sms.adapter.in.rest.sms.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "sms", description = "sms")
public class SmsFirmaDTO implements Serializable {

	private static final long serialVersionUID = 1L;


	private String codigoVerificacion;
	private List<String> documentosBase64;



}
