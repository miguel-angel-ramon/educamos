package es.jccm.edu.totp.application.ports.in;

import java.util.Optional;

import es.jccm.edu.totp.application.domain.PreferenciasUsuario;

public interface GetPreferenciasUsuarioUC {
	Optional<PreferenciasUsuario> getPreferenciasUsuario(long oid);
}
