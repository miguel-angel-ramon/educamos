package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Getter;

@Getter
public enum TipoDocumento {
	NIF(1,"NIF","D"),
	PASAPORTE(2,"pasaporte","P"),
	NUM_ID_ESCOLAR(3,"Número de identificación escolar","N"),
	NOSE(null,"desconocido","E");
	
	private Integer codigo;
	private String descripcion;
	private String codPersona;
	
	TipoDocumento(Integer codigo,String descripcion,String codPersona){
		this.codigo=codigo;
		this.descripcion=descripcion;
		this.codPersona=codPersona;
	}
	
	public static TipoDocumento getByCodigo(int codigo){
		for(TipoDocumento tipoDocumento:TipoDocumento.values()) {
			if(tipoDocumento.codigo!=null && tipoDocumento.codigo.equals(codigo)) {
				return tipoDocumento;
			}
		}
		
		throw new IllegalArgumentException("codigo de tipo de documentación no contemplado: "+codigo);
	}

	public static TipoDocumento getByCodPersona(String codigo){
		for(TipoDocumento tipoDocumento:TipoDocumento.values()) {
			if(tipoDocumento.codPersona.equals(codigo)) {
				return tipoDocumento;
			}
		}
		
		throw new IllegalArgumentException("codigo de tipo de documento no contemplado: "+codigo);
		
	}
	
}
