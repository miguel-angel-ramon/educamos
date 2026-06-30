package es.jccm.edu.totp.application.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder=true)
public class Cuenta2faGoogle {
	
	private long oidUsuario;
	private String login;
	private String secretKey2FA;
	private String issuer;
	
	public String getUrlInstalacion() {
		String secretKey = getSecretKey2FA();
		String account = getLogin();
	
		return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", 
				tourl(issuer), tourl(account),	tourl(secretKey), tourl(issuer));
	}
	
	private String tourl(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("no se puede usar el charset utf-8");
		}
	}

}
