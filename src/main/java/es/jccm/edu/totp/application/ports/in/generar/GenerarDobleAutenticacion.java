package es.jccm.edu.totp.application.ports.in.generar;

import java.util.Optional;

public interface GenerarDobleAutenticacion {

	Optional<TotpGenError> nuevaAutenticacion(TotpGenRequest event);

}