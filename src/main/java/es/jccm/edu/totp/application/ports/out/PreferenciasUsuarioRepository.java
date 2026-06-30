package es.jccm.edu.totp.application.ports.out;

import java.util.Optional;

import es.jccm.edu.totp.application.domain.PreferenciasUsuario;

public interface PreferenciasUsuarioRepository {

	Optional<PreferenciasUsuario> load(long oid);
	
}
