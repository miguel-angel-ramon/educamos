package es.jccm.edu.utils;

import java.io.Serializable;

import lombok.Data;

@Data
public class Validations implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void validationLondId(Long paramId) {
		if (paramId == null || paramId <= 0) {
			throw new IllegalArgumentException(
					"No están permitidos los parámetros identificadores (ID) que sean nulos o menores iguales a 0");
		}
	}

	public static void validationObject(Object object) {
		if (object == null) {
			throw new IllegalArgumentException(
					"El parámtero enviado no puede ser Nulo para una creación o actualización");
		}
	}
}
