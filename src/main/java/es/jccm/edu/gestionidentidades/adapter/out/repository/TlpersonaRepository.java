package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.jccm.edu.gestionidentidades.application.domain.QTlpersona;
import es.jccm.edu.gestionidentidades.application.domain.Tlpersona;
import es.jccm.edu.gestionidentidades.application.domain.Usuario;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface TlpersonaRepository extends AbstractRepository<Tlpersona, Long, QTlpersona> {
	
	@Query(value = "SELECT * FROM DELPHOS.TLPERSONAS d "
			+ "WHERE d.C_NUMIDE = :identificacion", nativeQuery = true)
	Tlpersona findPersonaByIdentificacion(@Param("identificacion") String identificacion);
	
	@Query(value = "SELECT * FROM DELPHOS.TLPERSONAS d "
			+ "WHERE d.C_NUMIDE = :identificacion", nativeQuery = true)
	List<Tlpersona> findPersonasByIdentificacion(String identificacion);

}
