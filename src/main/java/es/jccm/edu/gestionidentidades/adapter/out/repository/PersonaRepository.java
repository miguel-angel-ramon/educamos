package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.Documentaciones;
import es.jccm.edu.gestionidentidades.application.domain.Persona;
import es.jccm.edu.gestionidentidades.application.domain.QPersona;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface PersonaRepository extends AbstractRepository<Persona, Long, QPersona> {

	@Query(value = "select per.*"+
			" from delphos_modacc.personas per, delphos_modacc.documentaciones doc, delphos_modacc.tipos_documentacion tipdoc" +
			" where 1=1 "
			+ " and doc.oid_persona = per.oid " 
			+" and doc.oid_tipo_documentacion = tipdoc.oid " 
			+" and doc.t_identificacion = :identificacion ", nativeQuery = true)
	List<Persona> findPersonasByIdentificacion(String identificacion);
		
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM DELPHOS_MODACC.PERSONAS t WHERE t.OID =:oidPersona", nativeQuery = true)
	void deleteByOidPersona(Long oidPersona);
}
