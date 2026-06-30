package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LongitudMinima implements ValidadorCadenas{

	private int longitud;
	private String error;

	public LongitudMinima(int longitud,String error){
		this.longitud=longitud;
		this.error=error;
	}
	
	@Override
	public List<String> getErrores(String str) {
		if(str.length() < this.longitud) {
			return Arrays.asList(this.error);
		}
		else {
			return Collections.emptyList();
		}
	}

}
