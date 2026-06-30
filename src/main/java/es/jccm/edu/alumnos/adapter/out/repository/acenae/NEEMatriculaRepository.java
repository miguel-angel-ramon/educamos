package es.jccm.edu.alumnos.adapter.out.repository.acenae;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.acneae.MatriculaNEE;
import es.jccm.edu.alumnos.application.domain.acneae.QMatriculaNEE;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface NEEMatriculaRepository extends AbstractRepository<MatriculaNEE, Long, QMatriculaNEE> {
	
	List <MatriculaNEE> findAllByIdMatricula(Long idMatricula);

}
