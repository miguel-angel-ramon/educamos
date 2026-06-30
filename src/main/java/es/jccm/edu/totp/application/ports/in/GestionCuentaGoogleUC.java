package es.jccm.edu.totp.application.ports.in;

public interface GestionCuentaGoogleUC {

	// TODO: esto no debería de mandarse por email. Es poco seguro porque el email te lo pueden pillar y se queda de por vida
	// es mejor que se genere en el momento preciso y se le presente por aplicación directamente en pantalla al usuario
	// el código qr a generar es simplemente el link de activación
	// Se podría generar desde javascript
	boolean enviarQrActivacionGoogle(long idUsuario, String email);
	
}