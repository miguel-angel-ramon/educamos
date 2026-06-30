package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CaracterMayusculaObligatorio implements ValidadorCadenas {

	private String error;

	public CaracterMayusculaObligatorio(String error) {
		this.error = error;
	}

	@Override
	public List<String> getErrores(String str) {

		boolean sinMayuscula = !Pattern.matches(".*[A-Z]+.*", str);

		if (sinMayuscula) {
			return Arrays.asList(error);
		} else {
			return Collections.emptyList();
		}

	}

}
