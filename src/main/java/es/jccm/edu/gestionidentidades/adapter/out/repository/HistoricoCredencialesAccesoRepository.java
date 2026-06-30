package es.jccm.edu.gestionidentidades.adapter.out.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import es.jccm.edu.gestionidentidades.application.domain.HistoricoCredencialesAcceso;
import es.jccm.edu.gestionidentidades.application.domain.QHistoricoCredencialesAcceso;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface HistoricoCredencialesAccesoRepository extends AbstractRepository<HistoricoCredencialesAcceso, Long, QHistoricoCredencialesAcceso>{


	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.HISTORICO_CREDENCIALES_ACCESO t WHERE t.OID_USUARIO =:oidUsuario", nativeQuery = true)
	void deleteHistoricoCredencialesAccesoByOidUsuario(Long oidUsuario);
}
