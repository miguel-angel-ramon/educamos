package es.jccm.edu.proyectosfct.application.ports.in.enviosgestora;

import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.EnviosGestora;

import java.util.List;

public interface IEnviosGestoraService {
    List<EnviosGestora> getEnviosGestora(Long idCentro, Integer anno);

    EnviosGestora saveEnviosGestoraEntity(Long idCentro, Integer anno, Long xUsuarioDelphos, String resultado, Boolean isNew, Long idDatosGestora);

}
