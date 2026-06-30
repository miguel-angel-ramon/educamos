package es.jccm.edu.gestionidentidades.application.domain;

import lombok.Getter;

@Getter
public enum Sexo {

	H("Hombre"),
	M("Mujer"),
	I("Indefinido");
	
	private String codigo;
	
	Sexo(String codigo){
		this.codigo=codigo;
	}
	
	public static Sexo getByCodigo(String codigo){
		for(Sexo sexo:Sexo.values()) {
			if(sexo.getCodigo().equals(codigo)) {
				return sexo;
			}
		}
		throw new IllegalArgumentException("codigo de sexo no válido: "+codigo);
	}
}
