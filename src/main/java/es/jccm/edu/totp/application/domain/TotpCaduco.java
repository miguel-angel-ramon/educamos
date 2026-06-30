package es.jccm.edu.totp.application.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

/**
 * Time-based One-time Password que caduca al poco tiempo de haberse generado. Se genera un único por usuario y se manda por algún método de envio (sms, email, etc)
 * 
 * @author jesus
 *
 */
@Getter
@Builder(toBuilder=true)
public class TotpCaduco {
	
	private long oidUsuario;
	private String codigo2FA;
	private Date fechaGeneracionCodigo;
	private int duracion;
	
	public boolean validar(String codigo, Date ahora) {
		boolean resultado = validarSinBorrar(codigo, ahora);
		this.codigo2FA = null;
		return resultado;
	}

	private boolean validarSinBorrar(String codigo, Date ahora) {
		if (getCodigo2FA() == null || !getCodigo2FA().equals(codigo)) {
	        return false;
	    }
		return getFechaGeneracionCodigo() != null && !ahora.after(new Date(getFechaGeneracionCodigo().getTime() + duracion * 60 * 1000));
	}

}
