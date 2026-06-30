package es.jccm.edu.alumnos.adapter.out.repository.acenae;

import java.util.List;
import es.jccm.edu.alumnos.application.domain.acneae.NecesidadEducativa;
import es.jccm.edu.alumnos.application.domain.acneae.QNecesidadEducativa;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface NecesidadEducativaRepository extends AbstractRepository<NecesidadEducativa, Long, QNecesidadEducativa> {

		
	List<NecesidadEducativa>findAllByAdaptacion(String adaptacion);
	
	List<NecesidadEducativa>findAllByMatriculaIdMatricula(Long idMatricula);
	
	List<NecesidadEducativa> findAllByAdaptacionAndMatriculaIdMatricula(String adaptacion,Long idMatricula);
	
	
	
	
	
}
