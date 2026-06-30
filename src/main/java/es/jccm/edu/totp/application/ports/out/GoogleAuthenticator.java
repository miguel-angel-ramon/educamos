package es.jccm.edu.totp.application.ports.out;

public interface GoogleAuthenticator {

	/**
	 * Dado un secretkey, genera el código temporal de un solo uso de google
	 * authenticator. Ese código tiene una validez de +-30 segundos.
	 * 
	 * https://medium.com/@ihorsokolyk/two-factor-authentication-with-java-and-google-authenticator-9d7ea15ffee6
	 * 
	 */
	String getTOTPCode(String secretKey);

	String generateSecretKey();

}