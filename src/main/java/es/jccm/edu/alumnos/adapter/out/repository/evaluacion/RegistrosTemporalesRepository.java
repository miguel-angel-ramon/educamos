package es.jccm.edu.alumnos.adapter.out.repository.evaluacion;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.jccm.edu.alumnos.application.domain.evaluacion.QRegistroTemporal;
import es.jccm.edu.alumnos.application.domain.evaluacion.RegistroTemporal;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface RegistrosTemporalesRepository extends AbstractRepository<RegistroTemporal, Long, QRegistroTemporal>{	

	List<RegistroTemporal> findTopByOrderByIdEjecucion();
	
	@Query(value = "select TLS_RAPXIDENTREG.nextval from dual", nativeQuery = true)
	Long getIdEntreg();
	
	@Query(value = "select TLS_RAPXSECUENCIA.nextval from dual", nativeQuery = true)
	Long getIdEjecucion();
	
}
