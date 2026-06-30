package es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaMateriaCursoGenerica;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.QEvaMateriaCursoGenerica;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaMateriaCursoGenericaRepository extends AbstractRepository<EvaMateriaCursoGenerica, Long, QEvaMateriaCursoGenerica> {

}