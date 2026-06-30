package es.jccm.edu.gestionidentidades.application.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.jccm.edu.gestionidentidades.adapter.out.repository.EmpleadoDelphosRepository;
import es.jccm.edu.gestionidentidades.application.domain.AltaLdapResponse;
import es.jccm.edu.gestionidentidades.application.domain.AltaUsuarioGlobalRequest;
import es.jccm.edu.gestionidentidades.application.domain.EmpleadoDelphos;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.gestionidentidades.application.ports.in.IEmpleadoDelphosService;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class EmpleadoDelphosService implements IEmpleadoDelphosService{
	
	@Autowired
	private EmpleadoDelphosRepository empleadoDelphosRepository;

	@Override
	public void actualizaCorreoEnDelphos(AltaLdapResponse event, AltaUsuarioGlobalRequest request) {
		log.debug("llamado a onUsuarioSincronizadoEnLdap");
		List<EmpleadoDelphos> empleadosEnDelphos = empleadoDelphosRepository.findEmpleadoDelphosByDocumento(request.getIdentificacion());
		if(!empleadosEnDelphos.isEmpty()) {
			EmpleadoDelphos empleadoEnDelphos=empleadosEnDelphos.get(0);
			EmpleadoDelphos empleadoDelphosConEmailLdap=empleadoEnDelphos
					 .toBuilder()
					 	.correoLdap(event.getEmail())
					 	.build();
			 
			empleadoDelphosRepository.save(empleadoDelphosConEmailLdap);
		}		
	}
	
	@Override
	public List<EmpleadoDelphos> findEmpleadoDelphosByDocumentoExtended(String documento, Integer xCentro, Date fTomaPos) {
		return empleadoDelphosRepository.findEmpleadoDelphosByDocumentoExtended(documento, xCentro, fTomaPos);
	}
	
}
