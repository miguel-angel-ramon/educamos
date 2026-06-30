package es.jccm.edu.totp.application.ports.in.generar;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotpGenRequest {
	private long oidUsuario;
	private boolean conAlgoQuePosees; //true, false = no hace falta
}
