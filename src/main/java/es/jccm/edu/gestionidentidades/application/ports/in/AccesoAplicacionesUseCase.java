package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.PersonaId;

public interface AccesoAplicacionesUseCase {
	boolean tieneAccesoAplicacion(PersonaId personaId, String aplicacion);
	void darAccesoAplicacionSiNoTiene(PersonaId personaId,String codAplicacion);
}
