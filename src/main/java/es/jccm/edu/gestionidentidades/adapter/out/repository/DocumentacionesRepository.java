package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.Documentaciones;
import es.jccm.edu.gestionidentidades.application.domain.QDocumentaciones;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface DocumentacionesRepository extends AbstractRepository<Documentaciones, Integer, QDocumentaciones>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM DELPHOS_MODACC.DOCUMENTACIONES t WHERE t.OID_PERSONA =:oidPersona", nativeQuery = true)
	void deleteByOidPersona(Long oidPersona);
	
	@Query(value = "SELECT * FROM DELPHOS_MODACC.DOCUMENTACIONES d WHERE d.OID=?", nativeQuery = true)
	List<Documentaciones> findByIdDocumentacion(@Param("id") Long id);

	@Query(value = "SELECT * FROM DELPHOS_MODACC.DOCUMENTACIONES d WHERE d.T_IDENTIFICACION=:nif", nativeQuery = true)
	Documentaciones findByNif(String nif);
}
