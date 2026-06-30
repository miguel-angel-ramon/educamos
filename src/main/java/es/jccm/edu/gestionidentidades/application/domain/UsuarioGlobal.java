package es.jccm.edu.gestionidentidades.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioGlobal {
	private PersonaId personaId;
	private String oid;
	private String login;
	private boolean credencialesUnSoloUso;
}
