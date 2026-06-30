package es.jccm.edu.totp.application.ports.out;

import java.util.Optional;

import es.jccm.edu.totp.application.domain.TotpCaduco;

public interface TotpCaducoRepository {

	Optional<TotpCaduco> findByOid(long oid);

	void update(TotpCaduco clave2fa);

}