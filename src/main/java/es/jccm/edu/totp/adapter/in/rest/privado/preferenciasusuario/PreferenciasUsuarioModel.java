package es.jccm.edu.totp.adapter.in.rest.privado.preferenciasusuario;

import es.jccm.edu.totp.application.domain.MetodoDeAutenticacionDobleFactor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PreferenciasUsuarioModel {
	private String oid;
	private String mailEnvioDobleFactor;
	private MetodoDeAutenticacionDobleFactor metodoDeAutenticacionDobleFactor;
}
