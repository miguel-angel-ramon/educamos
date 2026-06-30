package es.jccm.edu.afirma.adapter.in.rest.afirma.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@Schema(name = "AfirmaVerifyOutputDto", description = "")
public class AfirmaVerifyOutputDto implements Serializable {
	private static final long serialVersionUID = 1L;

	public AfirmaVerifyOutputDto(String nombre, String nif, String apellido1, String apellido2, String cif,
								 String razonSocial, Map<String,String> datosCerticado, String certificadoBase64,
								 String hostAfirma) {
		this.nombre = nombre;
		this.nif = nif;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.razonSocial = razonSocial;
		this.datosCerticado = datosCerticado;
		this.certificadoBase64 = certificadoBase64;
		this.hostAfirma = hostAfirma;
	}

	@Schema(description = "Nombre")
	private String nombre;

	@Schema(description = "Id documento")
	private String idDoc;

	@Schema(description = "DNI")
	private String nif;

	@Schema(description = "Apellido 1")
	private String apellido1;

	@Schema(description = "Apellido 2")
	private String apellido2;

	@Schema(description = "")
	private String cif;

	@Schema(description = "")
	private String razonSocial;

	@Schema(description = "")
	private Map<String,String> datosCerticado;

	@Schema(description = "")
	private String certificadoBase64;

	@Schema(description = "")
	private String hostAfirma;

}
