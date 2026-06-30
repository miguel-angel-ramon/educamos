package es.jccm.edu.totp.adapter.out;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import de.taimos.totp.TOTP;
import es.jccm.edu.totp.application.ports.out.GoogleAuthenticator;
import es.jccm.edu.totp.application.ports.out.RandomTotpGenerator;

@Service
public class GoogleAuthenticatorTaimos implements RandomTotpGenerator, GoogleAuthenticator {


	/**
	 * Dado un secretkey, genera el código temporal de un solo uso de google
	 * authenticator. Ese código tiene una validez de +-30 segundos.
	 * 
	 * https://medium.com/@ihorsokolyk/two-factor-authentication-with-java-and-google-authenticator-9d7ea15ffee6
	 * 
	 */
	@Override
	public String getTOTPCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

	@Override
	public String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}

	@Override
	public String netNewRandomTotp() {
		return getTOTPCode(generateSecretKey());
	}

}
