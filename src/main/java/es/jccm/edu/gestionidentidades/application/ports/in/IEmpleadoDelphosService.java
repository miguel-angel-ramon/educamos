package es.jccm.edu.gestionidentidades.application.ports.in;

import java.util.Date;
import java.util.List;

import es.jccm.edu.gestionidentidades.application.domain.AltaLdapResponse;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.EmpleadoDelphos;

public interface IEmpleadoDelphosService {

	void actualizaCorreoEnDelphos(AltaLdapResponse event, AltaUsuarioGlobalRequest request);

	List<EmpleadoDelphos> findEmpleadoDelphosByDocumentoExtended(String documento, Integer xCentro, Date fTomaPos);

}
