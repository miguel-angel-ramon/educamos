package es.jccm.edu.shared.application.ports.out.totp.generar;

import java.util.Optional;

public interface GenerarDobleAutenticacionOut {

	Optional<TotpGenErrorOut> nuevaAutenticacion(TotpGenRequestOut event);

}