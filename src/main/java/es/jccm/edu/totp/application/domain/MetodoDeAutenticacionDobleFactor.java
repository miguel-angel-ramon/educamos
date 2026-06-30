package es.jccm.edu.totp.application.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public enum MetodoDeAutenticacionDobleFactor {
	ENVIO_TOTP_CADUCO_POR_CORREO(false,true), //el correo no es algo que se posee
	GOOGLE_AUTHENTICATOR(true,false), // se considera que el secret que genera los códigos lo instalará en su movil personal.

	//OTROS (para el futuro)....
	ENVIO_SMS(true,true), 
	NOTIFICACION_APP(true,true); //en vez de envio de sms que es caro, se envia notificación push a la app que tiene instalada el usuario 
	
	// Indica si el método es realmente con seguridad de doble factor al tener "algo físico que posees"
	private boolean conAlgoQuePosees;
	
	//indica si este método necesita generar y enviar un código caduco
	private boolean conCodigoCaduco;  
	
	MetodoDeAutenticacionDobleFactor(boolean conAlgoQuePosees, boolean conCodigoCaduco) {
		this.conAlgoQuePosees=conAlgoQuePosees;
		this.conCodigoCaduco=conCodigoCaduco;
	}
	
	public static List<MetodoDeAutenticacionDobleFactor> obtenerEnumeradosPorValor(boolean valor) {
        List<MetodoDeAutenticacionDobleFactor> enumeradosPorValor = new ArrayList<>();

        for (MetodoDeAutenticacionDobleFactor metodoDF : MetodoDeAutenticacionDobleFactor.values()) {
            if (metodoDF.conAlgoQuePosees == valor) {
                enumeradosPorValor.add(metodoDF);
            }
        }

        return enumeradosPorValor;
    }
}
