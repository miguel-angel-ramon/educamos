package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.Persona;

public interface GeneraNewLoginUseCase {

	String genera(Persona persona);

}