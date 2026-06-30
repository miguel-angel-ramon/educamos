package es.jccm.edu.proyectosfct.application.services.enviosgestora;

import es.jccm.edu.proyectosfct.adapter.out.repositories.enviosgestora.EnviosGestoraRepository;
import es.jccm.edu.proyectosfct.application.domain.empresas.Empresa;
import es.jccm.edu.proyectosfct.application.domain.enviosgestora.entities.EnviosGestora;
import es.jccm.edu.proyectosfct.application.ports.in.enviosgestora.IEnviosGestoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EnviosGestoraService implements IEnviosGestoraService {

    @Autowired
    private EnviosGestoraRepository enviosGestoraRepository;


    @Override
    public List<EnviosGestora> getEnviosGestora(Long idCentro, Integer anno) {
        return enviosGestoraRepository.findByXCentroAndAnno(idCentro, anno);
    }

    @Override
    public EnviosGestora saveEnviosGestoraEntity(Long idCentro, Integer anno, Long xUsuarioDelphos, String resultado, Boolean isNew, Long idDatosGestora) {

        if(isNew){
            return createEnviosGestora(idCentro, anno, xUsuarioDelphos);
        }else{
        	return updateEnviosGestora(idDatosGestora, resultado);
        }
        

    }

    private EnviosGestora updateEnviosGestora(Long idDatosGestora,  String resultado) {
    	Optional<EnviosGestora> enviosGestora = enviosGestoraRepository.findById(idDatosGestora);

    	if(enviosGestora.isPresent()) {
            enviosGestora.get().setFechaFin(new Date());
            enviosGestora.get().setCdResultado(resultado);
            return enviosGestoraRepository.save(enviosGestora.get());         
        } else {
        	return null;
        } 	
    	
    }

    private EnviosGestora createEnviosGestora(Long idCentro, Integer anno, Long xUsuarioDelphos) {
        EnviosGestora enviosGestora = new EnviosGestora();
        enviosGestora.setFechaEnvio(new Date());
        enviosGestora.setXCentro(idCentro);
        enviosGestora.setNuAnno(anno);
        enviosGestora.setIdUsuarioUlt(xUsuarioDelphos);

        return enviosGestoraRepository.save(enviosGestora);
        
    }


}
