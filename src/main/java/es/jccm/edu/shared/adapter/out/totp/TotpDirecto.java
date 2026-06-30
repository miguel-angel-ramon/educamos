package es.jccm.edu.shared.adapter.out.totp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import es.jccm.edu.shared.application.ports.out.totp.generar.GenerarDobleAutenticacionOut;
import es.jccm.edu.shared.application.ports.out.totp.generar.TotpGenErrorOut;
import es.jccm.edu.shared.application.ports.out.totp.generar.TotpGenRequestOut;
import es.jccm.edu.shared.application.ports.out.totp.validar.ValidarCodigoTotpOut;
import es.jccm.edu.totp.application.ports.in.ValidarCodigoTotpUC;
import es.jccm.edu.totp.application.ports.in.generar.GenerarDobleAutenticacion;
import es.jccm.edu.totp.application.ports.in.generar.TotpGenError;
import es.jccm.edu.totp.application.ports.in.generar.TotpGenRequest;

@Component
// Implementación que llama directamente al puerto de entrada del otro bc.
// TODO: habría que implementar un servicio rest en totp
// estudiar si no sería mejor pasar todo este meollo al apigateway.
public class TotpDirecto implements GenerarDobleAutenticacionOut,ValidarCodigoTotpOut {

	ValidarCodigoTotpUC validarCodigoTotpUC;
	GenerarDobleAutenticacion generar;
	
	
	
	public TotpDirecto(	@Qualifier("preferenciasTotpUsuarioService")
ValidarCodigoTotpUC validarCodigoTotpUC, GenerarDobleAutenticacion generar) {
		super();
		this.validarCodigoTotpUC = validarCodigoTotpUC;
		this.generar = generar;
	}

	@Override
	public boolean validarCodigoTotp(String codigo, long idUsuario) {
		return validarCodigoTotpUC.validarCodigoTotp(codigo, idUsuario);
	}

	@Override
	public Optional<TotpGenErrorOut> nuevaAutenticacion(TotpGenRequestOut event) {
		Optional<TotpGenError> res=generar.nuevaAutenticacion(TotpGenRequest.builder()
				.oidUsuario(event.getOidUsuario())
				.conAlgoQuePosees(event.isConAlgoQuePosees())
				.build());
		
		if(res.isEmpty()) {
			return Optional.empty();
		}
		
		TotpGenError error=res.get();
		
		if(TotpGenError.NO_MAIL==error) {
			return Optional.of(TotpGenErrorOut.NO_MAIL);
		}
		else if(TotpGenError.METODO_AUTENTICACION_INSUFICIENTEMENTE_SEGURO==error) {
			return Optional.of(TotpGenErrorOut.METODO_AUTENTICACION_INSUFICIENTEMENTE_SEGURO);
		}
		else throw new IllegalStateException();
	}

}
