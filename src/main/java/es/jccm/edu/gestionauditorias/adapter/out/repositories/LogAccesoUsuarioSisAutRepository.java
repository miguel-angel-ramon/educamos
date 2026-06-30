package es.jccm.edu.gestionauditorias.adapter.out.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionauditorias.application.domain.logger.LogAccesoUsuarioSisAut;
import es.jccm.edu.gestionauditorias.application.domain.logger.QLogAccesoUsuarioSisAut;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface LogAccesoUsuarioSisAutRepository extends AbstractRepository<LogAccesoUsuarioSisAut, Long, QLogAccesoUsuarioSisAut>{
	

	Optional<LogAccesoUsuarioSisAut> findByIdUsuarioAndLoginAndSistemaAutenticacion( Long idUsuario, String login,String sistemaAutenticacion);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.LOG_ACCESO_USUARIO_SISAUT t WHERE t.ID_USUARIO =:idUsuario", nativeQuery = true)
	void deleteLogAccesoUsuarioByIdUsuario(Long idUsuario); 
}
