package es.jccm.edu.totp.adapter.in.rest.privado;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtTokenContext {

	public Long getOidDeUsuarioDesdeTokenJwt() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth==null) {
			return null;
		}
 
		Object principal = auth.getPrincipal();
		if(principal==null) {
			return null;
		}

		if(!(principal instanceof Jwt)) {
			return null;
		}
		
		Jwt jwt=(Jwt)principal;
				
		String id = jwt.getSubject();
		if(id==null) {
			return null;
		}
		
		try {
			
			return Long.parseLong(id);
		} catch (NumberFormatException e) {
			log.error("no se pudo parsear string de oid a long "+ id);
			return null;
		}
	}
}