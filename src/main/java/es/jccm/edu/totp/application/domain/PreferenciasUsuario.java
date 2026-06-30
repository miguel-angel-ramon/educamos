package es.jccm.edu.totp.application.domain;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreferenciasUsuario {
	
	private long oid;
	
	public MetodoDeAutenticacionDobleFactor getMetodoAutenticacionFavorito() {
		
		//por el momento, deducimos el método en funcion de si tiene los datos configurados o no
		if(mail.isPresent()) {
			return MetodoDeAutenticacionDobleFactor.ENVIO_TOTP_CADUCO_POR_CORREO;
		}
		
		if(cuentaGoogle.isPresent()) {
			return MetodoDeAutenticacionDobleFactor.GOOGLE_AUTHENTICATOR;
		}
		if(movil.isPresent()) {
			return MetodoDeAutenticacionDobleFactor.ENVIO_SMS;
		}
		
		return null;
	}

	public MetodoDeAutenticacionDobleFactor getMetodoAutenticacionFavorito(boolean conAlgoQuePosees) {
		
		//Recuperamos los métodos de autenticación en funcion de la configuración de la anotación @Totp pasándo por parámetro el valor de con AlgoQuePosees
		List<MetodoDeAutenticacionDobleFactor> getMetodos = MetodoDeAutenticacionDobleFactor.obtenerEnumeradosPorValor(conAlgoQuePosees);
		
		if(!getMetodos.isEmpty()) {
			//Por el momento devolvemos el priomer valor de la lista debido a que no se ha implementado por el momento ninguna ordenación
			//ni configuración para el usuario
			return getMetodos.get(0);
		}
		
		return null;
	}
	
	Optional<String> mail;
	Optional<Cuenta2faGoogle> cuentaGoogle;
	
	//TODO: todavía no se contempla el envio por movil
	Optional<String> movil;

	
}
