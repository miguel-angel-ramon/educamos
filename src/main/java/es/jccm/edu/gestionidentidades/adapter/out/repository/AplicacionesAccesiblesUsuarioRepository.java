package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.AplicacionesAccesiblesUsuario;
import es.jccm.edu.gestionidentidades.application.domain.QAplicacionesAccesiblesUsuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface AplicacionesAccesiblesUsuarioRepository extends AbstractRepository<AplicacionesAccesiblesUsuario, Integer, QAplicacionesAccesiblesUsuario>{

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.APL_ACCESIBLES_USUARIO t WHERE t.OID_USUARIO =:oidUsuario", nativeQuery = true)
	void deleteByOidUsuario(Long oidUsuario);
	
	List<AplicacionesAccesiblesUsuario> findByIdUsuario(Long idUsuario);
	
}
