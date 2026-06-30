package es.jccm.edu.proyectosfct.application.configuration.rodal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RodalConfig {
	
	@Value("${rodal.fct.rodalConfig.mtomEnabled}")
	private boolean mtomEnabled;
	
	@Value("${rodal.fct.rodalConfig.sistema}")
	private String sistema;
	
	@Value("${rodal.fct.rodalConfig.unidadFuncional}")
	private String unidadFuncional;
	
	@Value("${rodal.fct.rodalConfig.expediente}")
	private String expediente;

	public boolean isMtomEnabled() {
		return mtomEnabled;
	}
	public void setMtomEnabled(boolean mtomEnabled) {
		this.mtomEnabled = mtomEnabled;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getUnidadFuncional() {
		return unidadFuncional;
	}
	public void setUnidadFuncional(String unidadFuncional) {
		this.unidadFuncional = unidadFuncional;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	
	
}
