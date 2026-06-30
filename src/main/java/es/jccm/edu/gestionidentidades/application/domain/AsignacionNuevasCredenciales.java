package es.jccm.edu.gestionidentidades.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AsignacionNuevasCredenciales {
	private boolean claveAsignada;
	private boolean loginAsignado;
	private String login;
	private String clave;
}
