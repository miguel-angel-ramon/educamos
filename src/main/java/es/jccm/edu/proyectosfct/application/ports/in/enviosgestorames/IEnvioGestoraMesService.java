package es.jccm.edu.proyectosfct.application.ports.in.enviosgestorames;

import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.EnviosGestoraMes;

import java.util.List;

public interface IEnvioGestoraMesService {

    List<EnviosGestoraMes> getEnviosGestoraMes(Long idCentro, Integer anno, Integer nuMes);

    EnviosGestoraMes saveEnviosGestoraMesEntity(Long idCentro, Integer anno, Long xUsuarioDelphos, String resultado, boolean isNew, Long idDatosGestoraMes, Integer nuMes);

}
