package es.jccm.edu.gestionidentidades.adapter.out.resttemplate;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import es.jccm.edu.gestionidentidades.application.ports.out.IClientCerberosRestTemplate;
import es.jccm.edu.shared.configuration.common.Constants;

@Component
@Qualifier("clientCerberosRestTemplate")
public class ClientCerberosRestTemplate implements IClientCerberosRestTemplate{

	@Autowired
	@Qualifier("restTemplateCerberos")
	private final RestTemplate restTemplate;
	
	@Value("${cerbero.purgar.cache}")
	private String urlCerberos;
	
	@Value("${spring.security.oauth2.client.registration.default.client-id}")
	private String clientId;
	
	@Value("${spring.security.oauth2.client.registration.default.client-secret}")
	private String clientSecret;
	
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuerUri;
	
	@Override
    public void refrescarUsuarioUsingCredentials(String username) {
		String token = obtenerTokenClientCredentials();
		String url = urlCerberos + username;
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "Bearer " + token);
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
	}
	
	public String obtenerTokenClientCredentials() {
		String accessTokenUrl = issuerUri + Constants.ACCESO_TOKEN_URL;
        String grantType = Constants.GRANT_TYPE_CLIENT_CREDENTIALS;
        
        String clientCredentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(clientCredentials.getBytes());
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);
        
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add("grant_type", grantType);
        
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(bodyParams, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, entity, String.class);

        String responseBody = responseEntity.getBody();
        String accessToken = "";
        int indexStart = responseBody.indexOf("\"access_token\":\"");
        if (indexStart != -1) {
            indexStart += "\"access_token\":\"".length();            
            // Buscar la posición de cierre de las comillas para obtener el valor del token
            int indexEnd = responseBody.indexOf("\"", indexStart);
            if (indexEnd != -1) {
                accessToken = responseBody.substring(indexStart, indexEnd);
            }
        }
        return accessToken;
	}

    public ClientCerberosRestTemplate(@Qualifier("restTemplateCerberos") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
