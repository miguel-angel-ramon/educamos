package es.jccm.edu.shared.configuration.rodal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import es.jccm.edu.proyectosfct.application.configuration.rodal.RodalConfig;
import es.jccm.edu.proyectosfct.application.configuration.rodal.handlers.seguridad.ConfigSeguridadWs;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties(prefix = "rodal")
@Data
@Slf4j
public class RodalConfiguration {
	

	
	@Autowired
	private ConfigSeguridadWs configSeguridadWs;

	@Autowired
	private RodalConfig rodalConfig;

	
	@Bean("rodalConfigFCT")
	public RodalConfig getConfigParaRodalFCT() {
		return rodalConfig;
	}
	
	
}

