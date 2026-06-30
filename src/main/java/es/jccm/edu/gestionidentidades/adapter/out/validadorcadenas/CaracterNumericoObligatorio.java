package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaracterNumericoObligatorio implements ValidadorCadenas{
	
	private String error;
	
	public CaracterNumericoObligatorio(String error){
		this.error=error;
	}
	
	@Override
	public List<String> getErrores(String str) {
		 boolean conNumero = false;
         char[] caracteres = str.toCharArray();
         for (int i = 0; i < caracteres.length && !conNumero; i++) {
             conNumero = Character.isDigit(caracteres[i]);
         }

         if (conNumero) {
        	 return Collections.emptyList();
         }else {
             return Arrays.asList(error);
         }
	}

}
