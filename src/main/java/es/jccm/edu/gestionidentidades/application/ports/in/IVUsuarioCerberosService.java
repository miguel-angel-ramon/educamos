package es.jccm.edu.gestionidentidades.application.ports.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

import es.jccm.edu.gestionidentidades.adapter.in.rest.apicerberos.model.VUsuarioCerberosDto;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.InvalidNifException;
import es.jccm.edu.gestionidentidades.application.ports.out.exceptions.ResultSetException;

public interface IVUsuarioCerberosService {

	VUsuarioCerberosDto getDatosUsuarioByLogin(String login) throws ResultSetException;

	VUsuarioCerberosDto getDatosUsuarioByUidLdap(String uidLdap) throws ResultSetException;

	VUsuarioCerberosDto getDatosUsuarioByNif(String nif) throws ResultSetException, InvalidNifException;
	
	boolean validarNif(String nif);

	Page<VUsuarioCerberosDto> getDatosUsuarioByQuerydsl(Predicate predicate, Pageable pageable);


}
