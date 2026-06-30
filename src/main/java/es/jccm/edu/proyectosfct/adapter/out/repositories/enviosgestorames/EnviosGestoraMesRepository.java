package es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestorames;

import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.EnviosGestoraMes;
import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.QEnviosGestoraMes;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnviosGestoraMesRepository extends AbstractRepository<EnviosGestoraMes, Long, QEnviosGestoraMes> {
    List<EnviosGestoraMes> findByIdCentroAndNuAnnoAndNuMesOrderByFechaFin(Long idCentro, Integer anno, Integer nuMes);
}
