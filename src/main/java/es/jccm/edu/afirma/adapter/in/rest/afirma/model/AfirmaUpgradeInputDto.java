package es.jccm.edu.afirma.adapter.in.rest.afirma.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "AfirmaUpgradeInputDto", description = "")
public class AfirmaUpgradeInputDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "")
	private String idApp;

	@Schema(description = "")
	private String firmaBase64;

	@Schema(description = "")
	private String user;

	@Schema(description = "")
	private String password;
}
