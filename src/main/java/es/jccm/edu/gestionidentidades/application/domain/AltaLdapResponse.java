package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AltaLdapResponse {
	private String uid;
	private String email;
}
