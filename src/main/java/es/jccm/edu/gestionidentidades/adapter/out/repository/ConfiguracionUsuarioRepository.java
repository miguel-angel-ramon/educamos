package es.jccm.edu.gestionidentidades.adapter.out.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.ConfiguracionUsuario;
import es.jccm.edu.gestionidentidades.application.domain.QConfiguracionUsuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface ConfiguracionUsuarioRepository extends AbstractRepository<ConfiguracionUsuario, Long, QConfiguracionUsuario>{
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM DELPHOS_MODACC.CONF_USUARIOS t WHERE t.OID_USUARIO =:oidUsuario", nativeQuery = true)
	void deleteUsuarioByOidUsuario(Long oidUsuario);
}
