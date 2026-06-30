package es.jccm.edu.shared.application.ports.out.resttemplate.comunica;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public interface ClientComunicaRestTemplatePortOut {

    <T> ResponseEntity<T> getForEntity(String url, Class<T> tClass);

    <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> tClass);

}
