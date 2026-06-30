package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CaracterMinusculaObligatorio implements ValidadorCadenas  {

	private String error;

	public CaracterMinusculaObligatorio(String error) {
		this.error = error;
	}

	@Override
	public List<String> getErrores(String str) {

		boolean sinMinuscula = !Pattern.matches(".*[a-z]+.*", str);

		if (sinMinuscula) {
			return Arrays.asList(error);
		} else {
			return Collections.emptyList();
		}

	}

}
