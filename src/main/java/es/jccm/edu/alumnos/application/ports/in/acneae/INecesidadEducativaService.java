package es.jccm.edu.alumnos.application.ports.in.acneae;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.acneae.NecesidadEducativa;

public interface INecesidadEducativaService {
	
	
	List<NecesidadEducativa> getAllNEE();
	
	List<NecesidadEducativa> getAllNEEByAdaptacion(String adaptacion);
	
	List <NecesidadEducativa> getNEEByMatricula(Long idMatricula);
	
	List<NecesidadEducativa> getNEEByAdaptacionAndMatricula(String adaptacion, Long idMatricula);
	
	

	

}
