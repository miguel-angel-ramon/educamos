package es.jccm.edu.proyectosfct.application.services.enviosgestorames;

import es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestorames.EnviosGestoraMesRepository;
import es.jccm.edu.proyectosfct.application.domain.enviosgestorames.entities.EnviosGestoraMes;
import es.jccm.edu.proyectosfct.application.ports.in.enviosgestorames.IEnvioGestoraMesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioGestoraMesService implements IEnvioGestoraMesService {

    @Autowired
    private EnviosGestoraMesRepository enviosGestoraMesRepository;

    @Override
    public List<EnviosGestoraMes> getEnviosGestoraMes(Long idCentro, Integer anno, Integer nuMes) {
        return enviosGestoraMesRepository.findByIdCentroAndNuAnnoAndNuMesOrderByFechaFin(idCentro, anno, nuMes);
    }

    @Override
    public EnviosGestoraMes saveEnviosGestoraMesEntity(Long idCentro, Integer anno, Long xUsuarioDelphos, String resultado, boolean isNew, Long idDatosGestoraMes, Integer nuMes) {
        if(isNew){
            return createEnviosGestoraMes(idCentro, anno, xUsuarioDelphos, nuMes);
        }else{
            return updateEnviosGestoraMes(idDatosGestoraMes, resultado, nuMes);
        }
    }

    private EnviosGestoraMes updateEnviosGestoraMes(Long idDatosGestoraMes, String resultado, Integer nuMes){

        EnviosGestoraMes enviosGestoraMesOptional = enviosGestoraMesRepository.findById(idDatosGestoraMes).orElse(null);

        if(enviosGestoraMesOptional != null){
            enviosGestoraMesOptional.setFechaFin(new Date());
            enviosGestoraMesOptional.setCdResultado(resultado);
            enviosGestoraMesOptional.setNuMes(nuMes);
            return enviosGestoraMesRepository.save(enviosGestoraMesOptional);
        }else{
            return null;
        }
    }

    private EnviosGestoraMes createEnviosGestoraMes(Long idCentro, Integer anno, Long xUsuarioDelphos, Integer nuMes){

        EnviosGestoraMes enviosGestoraMes = new EnviosGestoraMes();
        enviosGestoraMes.setFechaEnvio(new Date());
        enviosGestoraMes.setIdCentro(idCentro);
        enviosGestoraMes.setNuAnno(anno);
        enviosGestoraMes.setNuMes(nuMes);
        enviosGestoraMes.setIdUsuarioUlt(xUsuarioDelphos);

        return enviosGestoraMesRepository.save(enviosGestoraMes);

    }
}
