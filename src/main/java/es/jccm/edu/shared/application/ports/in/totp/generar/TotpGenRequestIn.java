package es.jccm.edu.shared.application.ports.in.totp.generar;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotpGenRequestIn {
	private long oidUsuario;
	private boolean conAlgoQuePosees; //true, false = no hace falta
}
