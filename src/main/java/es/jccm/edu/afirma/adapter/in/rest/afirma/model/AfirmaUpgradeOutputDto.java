package es.jccm.edu.afirma.adapter.in.rest.afirma.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "AfirmaUpgradeOutputDto", description = "")
public class AfirmaUpgradeOutputDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id del documento")
	private Long idDocumento;

}
