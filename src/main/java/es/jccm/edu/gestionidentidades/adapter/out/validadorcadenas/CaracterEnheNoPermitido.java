package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaracterEnheNoPermitido implements ValidadorCadenas {

	private String error;


	public CaracterEnheNoPermitido(String error) {
		this.error = error;
	}

	@Override
	public List<String> getErrores(String str) {

		boolean conEnheNoPermitido = str.contains("ñ")||str.contains("Ñ");
		//"Se encontraron caracteres no permitidos. "

		if (conEnheNoPermitido) {
			return Arrays.asList(error);
		} else{
			return Collections.emptyList();
		}

	}
}
