package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.List;

import es.jccm.edu.gestionidentidades.application.ports.in.login.ProblemaLogin;

public interface IGetProblemasService {
	
	List<ProblemaLogin> getProblemasAccesoUsuarioByNif(String nif);

}
