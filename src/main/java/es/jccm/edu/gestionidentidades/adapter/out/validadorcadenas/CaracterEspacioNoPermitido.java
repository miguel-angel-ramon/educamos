package es.jccm.edu.gestionidentidades.adapter.out.validadorcadenas;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CaracterEspacioNoPermitido implements ValidadorCadenas  {

	private String error;


	public CaracterEspacioNoPermitido(String error) {
		this.error = error;
	}

	@Override
	public List<String> getErrores(String str) {

		boolean conEspacioNoPermitido = str.contains(" ");
		//"Se encontraron caracteres no permitidos. "

		if (conEspacioNoPermitido) {
			return Arrays.asList(error);
		} else{
			return Collections.emptyList();
		}

	}

}