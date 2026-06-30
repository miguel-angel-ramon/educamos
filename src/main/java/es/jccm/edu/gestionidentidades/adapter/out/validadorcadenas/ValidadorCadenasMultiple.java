package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.ArrayList;
import java.util.List;

public class ValidadorCadenasMultiple implements ValidadorCadenas {

	private List<ValidadorCadenas> validadores;

	public ValidadorCadenasMultiple(List<ValidadorCadenas> validadores) {
		this.validadores = validadores;
	}

	@Override
	public List<String> getErrores(String str) {

		ArrayList<String> errores = new ArrayList<String>();

		for (ValidadorCadenas validador : validadores) {
			errores.addAll(validador.getErrores(str));
		}

		return errores;
	}
}
