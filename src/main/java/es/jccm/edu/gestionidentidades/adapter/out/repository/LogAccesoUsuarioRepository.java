package es.jccm.edu.gestionidentidades.adapter.out.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.gestionidentidades.application.domain.LogAccesoUsuario;
import es.jccm.edu.gestionidentidades.application.domain.QLogAccesoUsuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface LogAccesoUsuarioRepository extends AbstractRepository<LogAccesoUsuario, Long, QLogAccesoUsuario>{
	
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.LOG_ACCESO_USUARIO t WHERE t.OID_USUARIO =:oidUsuario", nativeQuery = true)
	void deleteLogAccesoUsuarioByOidUsuario(Long oidUsuario);
	
	
}
