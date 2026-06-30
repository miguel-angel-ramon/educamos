package es.jccm.edu.documentosGC.application.configuration.rodal.handlers.seguridad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigSeguridadWsDGC {
	
	@Value("${rodal.docgc.configSeguridadWsDGC.usuario}")
	private String usuario;
	
	@Value("${rodal.docgc.configSeguridadWsDGC.password}")
	private String password;
	
	@Value("${rodal.docgc.configSeguridadWsDGC.timeout}")
	private Integer timeout;
	
	@Value("${rodal.docgc.configSeguridadWsDGC.securizacion}")
	private String securizacion;
	
	@Value("${rodal.docgc.configSeguridadWsDGC.urlBase}")
	private String urlBase;
	
	@Value("${rodal.docgc.configSeguridadWsDGC.urlEndpoint}")
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