package es.jccm.edu.gestionidentidades.adapter.out.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.QUsuariosReasignaClave;
import es.jccm.edu.gestionidentidades.application.domain.UsuariosReasignaClave;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface UsuariosReasignaClaveRepository extends AbstractRepository<UsuariosReasignaClave, Long, QUsuariosReasignaClave>{

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.USUARIOS_REASIGNA_CLAVE t WHERE t.OID_USUARIO =:oidUsuario", nativeQuery = true)
	void deleteUsuarioReasignaClaveByOidUsuario(Long oidUsuario);
}


