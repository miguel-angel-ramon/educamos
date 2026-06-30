package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.AsignacionNuevasCredenciales;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.sincromadelphos.CredencialesManagerException;

public interface GenerarNuevasCredencialesUseCase {

	AsignacionNuevasCredenciales generarNuevasCredenciales(Usuario user,AltaUsuarioGlobalRequest request);
	
	boolean esUsuarioEnUsuariost(String documentacion);
	
	public boolean loginCumplePolitica(String login);
	
	String generaLoginPersonaS(Persona persona) throws CredencialesManagerException;
	
	String generaLoginPersona(Persona persona) throws CredencialesManagerException;
	
	String generaLoginALeatorio(Persona persona) throws CredencialesManagerException;
	
	String generaLoginALeatorioNombre(Persona persona) throws CredencialesManagerException;

	String generaCredencialesAleatoria(Persona persona) throws CredencialesManagerException;

}