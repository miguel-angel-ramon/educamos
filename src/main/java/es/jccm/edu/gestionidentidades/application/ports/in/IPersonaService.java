package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.Persona;

public interface IPersonaService {
	
	Persona createPersona(Persona conveniosFct);

	void deletePersona(Long idPersona);

}
