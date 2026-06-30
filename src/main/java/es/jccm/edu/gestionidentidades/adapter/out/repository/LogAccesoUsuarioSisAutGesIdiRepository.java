package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.LogAccesoUsuarioSisAutGesIdi;
import es.jccm.edu.gestionidentidades.application.domain.QLogAccesoUsuarioSisAutGesIdi;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface LogAccesoUsuarioSisAutGesIdiRepository extends AbstractRepository<LogAccesoUsuarioSisAutGesIdi, Long, QLogAccesoUsuarioSisAutGesIdi>{
	

	Optional<LogAccesoUsuarioSisAutGesIdi> findByIdUsuarioAndLoginAndSistemaAutenticacion( Long idUsuario, String login,String sistemaAutenticacion);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.LOG_ACCESO_USUARIO_SISAUT t WHERE t.ID_USUARIO =:idUsuario", nativeQuery = true)
	void deleteLogAccesoUsuarioByIdUsuario(Long idUsuario); 
}
