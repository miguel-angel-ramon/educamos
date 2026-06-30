package es.jccm.edu.shared.annotations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetTokenJwt {

	public Long getOidDeUsuarioDesdeTokenJwt() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}

		Object principal = auth.getPrincipal();
		if (principal == null) {
			return null;
		}

		if (!(principal instanceof Jwt)) {
			return null;
		}

		Jwt jwt = (Jwt) principal;

		String id = null;
		
		JSONObject usuarioSimulador = (JSONObject) jwt.getClaim("usuarioSimulador");
		
		if(usuarioSimulador == null || "".equals(usuarioSimulador)) {
			log.debug("*********** TOKEN getOidDeUsuarioDesdeTokenJwt AUDITORIA: "+ jwt.getTokenValue());
			
			id = jwt.getClaim("oid_username")!=null?jwt.getClaim("oid_username").toString():jwt.getClaim("preferred_username");//jwt.getSubject();			
		}else {
			//JSONObject jsonObject = new JSONObject(usuarioSimulador);
			id = usuarioSimulador.get("oid_username").toString();
		}
		
		if (id == null) {
			return null;
		}
		

		try {

			return Long.parseLong(id);
		} catch (NumberFormatException e) {
			log.error("no se pudo parsear string de oid a long " + id);
			return null;
		}
	}
}
