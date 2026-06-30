package es.jccm.edu.proyectosfct.application.configuration.rodal.handlers.seguridad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigSeguridadWs {
    
    @Value("${rodal.fct.configSeguridadWs.usuario}")
	private String usuario;
	
    @Value("${rodal.fct.configSeguridadWs.password}")
	private String password;
    
    @Value("${rodal.fct.configSeguridadWs.timeout}")
	private Integer timeout;
    
    @Value("${rodal.fct.configSeguridadWs.securizacion}")
	private String securizacion;
    
    @Value("${rodal.fct.configSeguridadWs.urlBase}")
	private String urlBase;
    
    @Value("${rodal.fct.configSeguridadWs.urlEndpoint}")
	private String urlEndpoint;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getSecurizacion() {
		return securizacion;
	}

	public void setSecurizacion(String securizacion) {
		this.securizacion = securizacion;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getUrlEndpoint() {
		return urlEndpoint;
	}

	public void setUrlEndpoint(String urlEndpoint) {
		this.urlEndpoint = urlEndpoint;
	}
}