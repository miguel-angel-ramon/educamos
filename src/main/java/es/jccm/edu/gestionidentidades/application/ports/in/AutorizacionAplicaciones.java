package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.PersonaId;
import es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.AutorizacionAplicacionException;

public interface AutorizacionAplicaciones {
	boolean tieneAccesoAplicacion(PersonaId personaId, String aplicacion) throws AutorizacionAplicacionException;
	void darAccesoAplicacion(PersonaId personaId,String codAplicacion) throws AutorizacionAplicacionException;

	void removeAccesoAplicacion(PersonaId personaId, String codigoAplicacion) throws AutorizacionAplicacionException;

}
