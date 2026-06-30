package es.jccm.edu.evaluacion.adapter.out.repositories.valoracionCriterios;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.evaluacion.CompetenciaClave;
import es.jccm.edu.evaluacion.application.domain.evaluacion.QCompetenciaClave;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface CompetenciaClaveRepository extends AbstractRepository<CompetenciaClave, Long, QCompetenciaClave> {

}
