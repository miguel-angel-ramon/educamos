package es.jccm.edu.shared.application.ports.out.totp.generar;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TotpGenRequestOut {
	private long oidUsuario;
	private boolean conAlgoQuePosees; //true, false = no hace falta
}
