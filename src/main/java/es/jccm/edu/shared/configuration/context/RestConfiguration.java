package es.jccm.edu.shared.configuration.context;

import java.io.IOException;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Autowired
    private EntityManager entityManager;
    
    @Value("${comunica.api.token-header}")
	private String comunicaTokenHeader;

    @Value("${comunica.api.token-value}")
	private String comunicaTokenValue;

    @Bean(name = "restTemplateComunica")
    public RestTemplate getRestTemplateComunica() {
    	RestTemplate restTemplate=new RestTemplate();
    	restTemplate.getInterceptors().add(new AddHeaderInterceptor(comunicaTokenHeader,comunicaTokenValue));
    	
    	return restTemplate;
    }
    
    //-----------------------------------
    
    @Value("${accesopapas.api.token-header}")
    private String accesopapasTokenHeader;

    @Value("${accesopapas.api.token-value}")
    private String accesopapasTokenValue;

    @Value("${accesopapas.api.path}")
    private String accesopapasPath;

    
    @Bean(name = "restTemplateAccesopapas")
    public RestTemplate getRestTemplateAccesopapas() {
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(accesopapasPath));
        restTemplate.getInterceptors().add(
                new AddHeaderInterceptor(accesopapasTokenHeader,accesopapasTokenValue));
        
        return restTemplate;
    }
    
    @Bean(name = "restTemplateCerberos")
    public RestTemplate restTemplateCerberos() {
        return new RestTemplate();
    }
    
    @Bean(name = "restTemplateSegSocial")
    public RestTemplate restTemplateSegSocial() {
        return new RestTemplate();
    }
    
	private static class AddHeaderInterceptor implements ClientHttpRequestInterceptor{

		private String headerName;
		private String headerValue;
		
		public AddHeaderInterceptor(String headerName, String headerValue) {
			super();
			this.headerName = headerName;
			this.headerValue = headerValue;
		}

		@Override
		public ClientHttpResponse intercept(org.springframework.http.HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			request.getHeaders().set(headerName, headerValue);//Set the header for each request
            return execution.execute(request, body);
		}
    } 
}