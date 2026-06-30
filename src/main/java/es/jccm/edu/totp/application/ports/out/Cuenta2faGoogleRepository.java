package es.jccm.edu.totp.application.ports.out;

import java.util.Optional;

import es.jccm.edu.totp.application.domain.Cuenta2faGoogle;

public interface Cuenta2faGoogleRepository {

	Optional<Cuenta2faGoogle> findByOid(long oid);

	void update(Cuenta2faGoogle cuenta2fa);

}