package es.jccm.edu.gestionidentidades.application.domain.enums;

public enum AplicacionesPorDefecto {
	APP_SECRETARIA_VIRTUAL ("SECRETARIA_VIRTUAL",17L),
	APP_MODULO_ACCESO ("MODULO_ACCESO",1L);
	
	public final String codigo;
	public final Long id;

	AplicacionesPorDefecto(String codigo, Long id) {
		this.codigo = codigo;
		this.id = id;
	}
}