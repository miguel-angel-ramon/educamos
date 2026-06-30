package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.PersonaId;
import es.jccm.edu.gestionidentidades.application.domain.UsuarioGlobal;
import es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.CredencialesManagerException;

public interface CredencialesManager {
	public String creaClaveSinCifrar() throws CredencialesManagerException;

	public boolean actualizaCredencialesAcceso(PersonaId personaId, String claveSinCifrar, String login)
			throws CredencialesManagerException;

	boolean loginCumplePolitica(String login);

	String generaLoginPersona(PersonaId personaId) throws CredencialesManagerException;

	String cifrarClave(String claveEnClaro) throws CredencialesManagerException;

	void desbloqueaUsuario(String login);

	UsuarioGlobal creaNuevoUsuario(PersonaId personaId, String modoAlta, int xusuarioPeticionario)
			throws CredencialesManagerException;

	UsuarioGlobal getUsuario(PersonaId personaId) throws CredencialesManagerException;

}
