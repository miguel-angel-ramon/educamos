package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaracterAlfaNumericoObligatorio implements ValidadorCadenas {

	private String error;

	public CaracterAlfaNumericoObligatorio(String error) {
		this.error = error;
	}

	@Override
	public List<String> getErrores(String str) {
		boolean conLetra = false;
		char[] caracteres = str.toCharArray();
		for (int i = 0; i < caracteres.length && !conLetra; i++) {
			conLetra = Character.isLetter(caracteres[i]);
		}

		if (conLetra) {
			return Collections.emptyList();
		} else {
			return Arrays.asList(error);
		}
	}

}
