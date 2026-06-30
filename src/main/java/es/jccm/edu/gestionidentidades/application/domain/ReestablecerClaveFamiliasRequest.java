package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReestablecerClaveFamiliasRequest {
	private Long oid;
	private String email;
}
