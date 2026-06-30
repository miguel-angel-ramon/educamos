package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.gestionidentidades.application.domain.MotivoBloqueoRegistroPersona;
import es.jccm.edu.gestionidentidades.application.domain.QMotivoBloqueoRegistroPersona;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface MotivoBloqueoRegistroPersonaRepository extends AbstractRepository<MotivoBloqueoRegistroPersona, Long, QMotivoBloqueoRegistroPersona> {

	@Query(value = "SELECT * FROM DELPHOS.TLMOTBLOREGPER "
			+ "WHERE X_PERSONA = :xPersona ", nativeQuery = true)
	List<MotivoBloqueoRegistroPersona> findMotivoBloqueoRegistroPersonaByXPersona(Long xPersona);

}
