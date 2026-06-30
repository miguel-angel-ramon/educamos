package es.jccm.edu.shared.adapter.out.resttemplate.accesopapas;

import es.jccm.edu.shared.application.ports.out.resttemplate.accesopapas.ClientAccesoPapasRestTemplatePortOut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Qualifier("clientAccesoPapasRestTemplate")
public class ClientAccesoPapasRestTemplateAdapterOut implements ClientAccesoPapasRestTemplatePortOut {

    private final RestTemplate restTemplate;

    public ClientAccesoPapasRestTemplateAdapterOut(@Qualifier("restTemplateAccesopapas") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T getForObject(String url, Class<T> tClass) {
        return restTemplate.getForObject(url, tClass);
    }

}
