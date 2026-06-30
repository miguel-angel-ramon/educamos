package es.jccm.edu.shared.application.services.totp;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.jccm.edu.shared.application.ports.in.totp.generar.GenerarDobleAutenticacionIn;
import es.jccm.edu.shared.application.ports.in.totp.generar.TotpGenErrorIn;
import es.jccm.edu.shared.application.ports.in.totp.generar.TotpGenRequestIn;
import es.jccm.edu.shared.application.ports.in.totp.validar.ValidarCodigoTotpIn;
import es.jccm.edu.shared.application.ports.out.totp.generar.GenerarDobleAutenticacionOut;
import es.jccm.edu.shared.application.ports.out.totp.generar.TotpGenErrorOut;
import es.jccm.edu.shared.application.ports.out.totp.generar.TotpGenRequestOut;
import es.jccm.edu.shared.application.ports.out.totp.validar.ValidarCodigoTotpOut;

@Service
public class TotpService implements GenerarDobleAutenticacionIn, ValidarCodigoTotpIn {

	private GenerarDobleAutenticacionOut generarDobleAutenticacionOut;
	private ValidarCodigoTotpOut validarCodigoTotpOut;
	
	

	public TotpService(GenerarDobleAutenticacionOut generarDobleAutenticacionOut,
			ValidarCodigoTotpOut validarCodigoTotpOut) {
		super();
		this.generarDobleAutenticacionOut = generarDobleAutenticacionOut;
		this.validarCodigoTotpOut = validarCodigoTotpOut;
	}

	@Override
	public boolean validarCodigoTotp(String codigo, long idUsuario) {
		return validarCodigoTotpOut.validarCodigoTotp(codigo, idUsuario);
	}

	@Override
	public Optional<TotpGenErrorIn> nuevaAutenticacion(TotpGenRequestIn event) {

		TotpGenRequestOut req = TotpGenRequestOut.builder().oidUsuario(event.getOidUsuario())
				.conAlgoQuePosees(event.isConAlgoQuePosees()).build();

		Optional<TotpGenErrorOut> res = generarDobleAutenticacionOut.nuevaAutenticacion(req);

		return toIn(res);

	}

	private Optional<TotpGenErrorIn> toIn(Optional<TotpGenErrorOut> res) {
		if (res.isEmpty()) {
			return Optional.empty();
		}

		TotpGenErrorOut error = res.get();

		if (error == TotpGenErrorOut.METODO_AUTENTICACION_INSUFICIENTEMENTE_SEGURO) {
			return Optional.of(TotpGenErrorIn.METODO_AUTENTICACION_INSUFICIENTEMENTE_SEGURO);
		}

		else if (error == TotpGenErrorOut.NO_MAIL) {
			return Optional.of(TotpGenErrorIn.NO_MAIL);
		} else
			throw new IllegalStateException();
	}

}
