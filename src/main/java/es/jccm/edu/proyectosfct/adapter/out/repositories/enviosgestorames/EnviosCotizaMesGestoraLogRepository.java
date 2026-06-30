package es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestorames;

import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.EnviosCotizaMesGestoraLog;
import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.QEnviosCotizaMesGestoraLog;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnviosCotizaMesGestoraLogRepository
        extends AbstractRepository<EnviosCotizaMesGestoraLog, Long, QEnviosCotizaMesGestoraLog> {

}