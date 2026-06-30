package es.jccm.edu.gestionidentidades.application.domain;

import java.util.Date;
import java.util.StringTokenizer;

import lombok.Builder;
import lombok.Getter;

/**
 * 
 * @author jesus
 *
 */
@Builder
@Getter
public class AltaUsuarioRequest {
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nif;
	private Date fechaNacimiento;

	public AltaUsuarioRequest newLimpio() {
		AltaUsuarioRequest usuarioLimpio=
	
		builder()
		.nombre(AltaUsuarioRequest.capitalizaYElimnaEspaciosEntrePalabras(this.getNombre()))
		.apellido1(AltaUsuarioRequest.capitalizaYElimnaEspaciosEntrePalabras(this.getApellido1()))
		.apellido2(this.getApellido2()==null?null:
			AltaUsuarioRequest.capitalizaYElimnaEspaciosEntrePalabras(this
					.getApellido2()))
		.nif(this.getNif())
		.fechaNacimiento(this.getFechaNacimiento())
		.build();
		
		return usuarioLimpio;
	}
	
	
	public static String capitalizaYElimnaEspaciosEntrePalabras(String cadena) {
		StringTokenizer stk = new StringTokenizer(cadena, " ");
		StringBuffer cadenaParseada = new StringBuffer("");
		while (stk.hasMoreElements()) {
			String elemento = stk.nextToken();
			elemento = elemento.toLowerCase();
			String primeraLetra = elemento.substring(0, 1);
			primeraLetra = primeraLetra.toUpperCase();
			elemento = primeraLetra + elemento.substring(1);
			cadenaParseada.append(elemento);
			if (stk.hasMoreElements()) {
				cadenaParseada.append(' ');
			}
		}
	
		return cadenaParseada.toString();
	}
	
}
