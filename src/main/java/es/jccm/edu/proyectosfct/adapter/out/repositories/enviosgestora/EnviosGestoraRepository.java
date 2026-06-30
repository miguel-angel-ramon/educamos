package es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestora;

import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.EnviosGestora;
import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.QEnviosGestora;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnviosGestoraRepository extends AbstractRepository<EnviosGestora, Long, QEnviosGestora> {

    @Query(value = " select * from fct_envios_gestora where x_centro = :idCentro and nu_anno = :anno order by f_creacion desc", nativeQuery = true)
    List<EnviosGestora> findByXCentroAndAnno(Long idCentro, Integer anno);

}
