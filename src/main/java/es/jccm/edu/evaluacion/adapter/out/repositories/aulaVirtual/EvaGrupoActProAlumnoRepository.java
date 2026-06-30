package es.jccm.edu.evaluacion.adapter.out.repositories.aulaVirtual;

import org.springframework.stereotype.Repository;

import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.EvaGrupoActProAlumno;
import es.jccm.edu.evaluacion.application.domain.aulaVirtual.entidades.QEvaGrupoActProAlumno;
import es.jccm.edu.shared.adapter.out.repositories.AbstractRepository;

@Repository
public interface EvaGrupoActProAlumnoRepository extends AbstractRepository<EvaGrupoActProAlumno, Long, QEvaGrupoActProAlumno> {
}