package es.jccm.edu.shared.application.ports.in.totp.generar;

import java.util.Optional;

public interface GenerarDobleAutenticacionIn {

	Optional<TotpGenErrorIn> nuevaAutenticacion(TotpGenRequestIn event);

}