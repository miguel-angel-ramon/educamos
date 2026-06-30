package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CaracterSimboloPermitidoObligatorio implements ValidadorCadenas {

	private String error;

	private final static String patternSimbolos = "\\!\\@#~\\$%&/\\(\\)=\\[\\]\\{\\}_\\-\\?";

	public CaracterSimboloPermitidoObligatorio(String error) {
		this.error = error;
	}

	@Override
	public List<String> getErrores(String str) {

		boolean sinSimbolo = !Pattern.matches(".*[!" + patternSimbolos + "]+.*", str);
		// "No se encontró ningún símbolo. "

		if (sinSimbolo) {
			return Arrays.asList(error);
		} else {
			return Collections.emptyList();
		}

	}

}