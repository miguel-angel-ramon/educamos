package es.jccm.edu.gestionidentidades.application.ports.in;

import es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model.VUsuarioCerberosDto;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.InvalidNifException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.ResultSetException;

public interface IUsuarioCerberosService {

	VUsuarioCerberosDto getDatosUsuarioByLogin(String login) throws ResultSetException;

	VUsuarioCerberosDto getDatosUsuarioByUidLdap(String uidLdap) throws ResultSetException;

	VUsuarioCerberosDto getDatosUsuarioByNif(String nif) throws ResultSetException, InvalidNifException;
	
	VUsuarioCerberosDto getDatosUsuarioByIdentificador(String identificador);

	VUsuarioCerberosDto getDatosUsuarioByIdentificador2(String identificador);

}
