package es.jccm.edu.shared.adapter.out.resttemplate.comunica;

import es.jccm.edu.shared.application.ports.out.resttemplate.comunica.ClientComunicaRestTemplatePortOut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Qualifier("clientComunicaRestTemplate")
public class ClientComunicaRestTemplateAdapterOut implements ClientComunicaRestTemplatePortOut {

    private final RestTemplate restTemplate;

    public ClientComunicaRestTemplateAdapterOut(@Qualifier("restTemplateComunica") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> tClass) {
        return restTemplate.getForEntity(url, tClass);
    }

    public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> tClass) {
        return restTemplate.postForEntity(url, request, tClass);
    }

}
