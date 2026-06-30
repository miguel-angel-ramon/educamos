package es.jccm.edu.shared.application.ports.out.resttemplate.accesopapas;

public interface ClientAccesoPapasRestTemplatePortOut {

    <T> T getForObject(String url, Class<T> tClass);

}
