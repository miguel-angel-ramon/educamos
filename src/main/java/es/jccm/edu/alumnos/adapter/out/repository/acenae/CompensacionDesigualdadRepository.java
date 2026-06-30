package es.jccm.edu.alumnos.adapter.out.repository.acenae;

import java.util.List;

import es.jccm.edu.alumnos.application.domain.acneae.CompensacionDesigualdad;
import es.jccm.edu.alumnos.application.domain.acneae.QCompensacionDesigualdad;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

public interface CompensacionDesigualdadRepository extends AbstractRepository<CompensacionDesigualdad,Long, QCompensacionDesigualdad> {

		List <CompensacionDesigualdad> findAllByMatriculaIdMatricula(Long idMatricula);
}
