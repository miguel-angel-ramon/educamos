package es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestora;

import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.EnviosAlumnosGestoraLog;
import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.QEnviosAlumnosGestoraLog;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnviosAlumnosGestoraLogRepository extends AbstractRepository<EnviosAlumnosGestoraLog, Long, QEnviosAlumnosGestoraLog> {


}
