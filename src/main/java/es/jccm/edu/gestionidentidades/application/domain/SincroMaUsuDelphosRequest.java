package es.jccm.edu.gestionidentidades.application.domain;

import es.jccm.edu.gestionidentidades.application.domain.PersonaId;
import es.jccm.edu.gestionidentidades.application.domain.TipoPersonal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SincroMaUsuDelphosRequest {
	private TipoPersonal tipoPersonal;
	private PersonaId personaId;

	private String centro;
	private String correoExterno;
	private int xusuario;
	private boolean centroPerteneceAccesoEducamos;
	private String fTomapos;
}