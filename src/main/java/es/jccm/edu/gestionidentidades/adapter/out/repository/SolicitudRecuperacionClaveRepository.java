package es.jccm.edu.gestionidentidades.adapter.out.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.jccm.edu.gestionidentidades.application.domain.QSolicitudRecuperacionClave;
import es.jccm.edu.gestionidentidades.application.domain.SolicitudRecuperacionClave;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface SolicitudRecuperacionClaveRepository extends AbstractRepository<SolicitudRecuperacionClave, Integer, QSolicitudRecuperacionClave>{

	@Query(value = "SELECT * FROM DELPHOS_MODACC.SOL_REC_CLAVE s WHERE s.ID=?", nativeQuery = true)
	List<SolicitudRecuperacionClave> findByOidUsuario(@Param("id") Long id);

}
