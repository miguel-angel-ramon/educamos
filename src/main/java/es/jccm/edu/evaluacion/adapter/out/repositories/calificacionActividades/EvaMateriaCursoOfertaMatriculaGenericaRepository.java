package es.jccm.edu.evaluacion.adapter.out.repositories.calificacionActividades;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.EvaMateriaCursoOfertaMatriculaGenerica;
import es.jccm.edu.evaluacion.application.domain.calificacionActividades.entidades.QEvaMateriaCursoOfertaMatriculaGenerica;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaMateriaCursoOfertaMatriculaGenericaRepository extends AbstractRepository<EvaMateriaCursoOfertaMatriculaGenerica, Long, QEvaMateriaCursoOfertaMatriculaGenerica> {

}