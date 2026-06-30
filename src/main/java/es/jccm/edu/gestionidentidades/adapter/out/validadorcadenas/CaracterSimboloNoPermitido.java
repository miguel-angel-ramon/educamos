package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CaracterSimboloNoPermitido implements ValidadorCadenas  {

	private String error;
	
	private final static String patternSimbolos = "\\!\\@#~\\$%&/\\(\\)=\\[\\]\\{\\}_\\-\\?";

	public CaracterSimboloNoPermitido(String error) {
		this.error = error;
	}

	@Override
	public List<String> getErrores(String str) {

		boolean conSimboloNoPermitido = !Pattern.matches("[a-zA-Z\\d" + patternSimbolos + "]+", str);
		//"Se encontraron caracteres no permitidos. "

		if (conSimboloNoPermitido) {
			return Arrays.asList(error);
		} else{
			return Collections.emptyList();
		}

	}

}